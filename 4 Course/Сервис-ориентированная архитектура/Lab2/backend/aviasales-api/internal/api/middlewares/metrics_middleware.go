package middlewares

import (
	"github.com/gin-gonic/gin"
	"github.com/whiteforestever/aviasales-api/metrics"
	"strconv"
)

// MetricMiddlewares - middleware для сбора метрик по коду ответа
func MetricMiddlewares() gin.HandlerFunc {
	return func(c *gin.Context) {
		c.Next()
		if c.Request.URL.Path == "/favicon.ico" {
			return
		}
		statusCode := strconv.Itoa(c.Writer.Status())
		metrics.RequestsTotal.WithLabelValues(statusCode, c.Request.Method).Inc()
	}
}
