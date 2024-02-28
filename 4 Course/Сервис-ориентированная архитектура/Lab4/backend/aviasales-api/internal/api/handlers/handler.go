package handlers

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/prometheus/client_golang/prometheus"
	"github.com/prometheus/client_golang/prometheus/promhttp"
	"github.com/whiteforestever/aviasales-api/internal/api/middlewares"
	"github.com/whiteforestever/aviasales-api/metrics"
	"github.com/whiteforestever/aviasales-api/pkg/parser"
	"github.com/whiteforestever/aviasales-api/storage/redis"
	"log"
	"net/http"
)

const (
	specialOffersEndpoint = "special_offers"
)

type Coordinator struct {
	storage redis.Storage
	parser  parser.DataParser
}

func NewCoordinator(redisClient *redis.RedisClient, parser parser.DataParser) Coordinator {
	coordinator := Coordinator{
		storage: redisClient,
		parser:  parser,
	}

	return coordinator
}

// SetupRoutes sets up the API routes and handlers.
func (c *Coordinator) SetupRoutes() *gin.Engine {
	router := gin.Default()
	reg := prometheus.NewRegistry()
	metrics.RegisterMetrics(reg)

	router.Use(middlewares.LoggingMiddleware())
	router.Use(middlewares.MetricMiddlewares())

	router.GET("/ping", c.PingHandler())
	router.GET("/metrics", gin.WrapH(promhttp.HandlerFor(reg, promhttp.HandlerOpts{Registry: reg})))
	router.GET("/routes/:route", c.GetRouteInfoHandler())
	router.GET("/special_offers", c.GetSpecialOffersInfoHandler())
	router.StaticFile("/favicon.ico", "assets/favicon.ico")

	go func() {
		log.Fatal(http.ListenAndServe(":9090", router))
	}()

	return router
}

// PingHandler handles the /ping endpoint.
func (c *Coordinator) PingHandler() gin.HandlerFunc {
	return func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"message": "Pong"})
	}
}

// GetRouteInfoHandler handles the /routes/{route} endpoint.
func (c *Coordinator) GetRouteInfoHandler() gin.HandlerFunc {
	return func(gc *gin.Context) {
		key := gc.Param("route")
		routeInfo, err := c.storage.GetData(key)
		if err != nil {
			gc.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to get data from Redis"})
			return
		}

		gc.JSON(http.StatusOK, gin.H{
			"message": fmt.Sprintf("Successfully get info about %s", key),
			"payload": routeInfo,
		})
	}
}

// GetSpecialOffersInfoHandler handles the /special_offers endpoint.
func (c *Coordinator) GetSpecialOffersInfoHandler() gin.HandlerFunc {
	return func(gc *gin.Context) {
		origin := gc.DefaultQuery("origin", "LED")
		destination := gc.DefaultQuery("destination", "MOW")
		currency := gc.DefaultQuery("currency", "rub")
		locale := gc.DefaultQuery("locale", "ru")
		queryParams := map[string]string{
			"origin":      origin,
			"destination": destination,
			"currency":    currency,
			"locale":      locale,
		}

		data, err := c.parser.ParseData(specialOffersEndpoint, queryParams)
		if err != nil {
			gc.JSON(http.StatusInternalServerError, gin.H{
				"message": fmt.Sprintf("Failed to get get special offers for origin=%s, destination=%s", origin, destination),
				"error":   err.Error(),
			})
			return
		}

		for _, route := range data.Routes {
			c.storage.SaveData(gc, origin+"-"+destination, route)
		}

		gc.JSON(http.StatusOK, gin.H{
			"message": fmt.Sprintf("Successfully get special offers for origin=%s, destination=%s", origin, destination),
			"payload": data,
		})
	}
}
