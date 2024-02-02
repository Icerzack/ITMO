package main

import (
	"context"
	"fmt"
	"github.com/whiteforestever/aviasales-api/config"
	"github.com/whiteforestever/aviasales-api/internal/api/handlers"
	"github.com/whiteforestever/aviasales-api/pkg/parser"
	"github.com/whiteforestever/aviasales-api/storage/redis"
	"go.uber.org/zap"
	"log"
	"net/http"
	"os"
)

func main() {
	cfg, err := config.InitConfig()
	if err != nil {
		fmt.Printf("Error initializing configuration: %v\n", err)
		return
	}
	cfgLogger := zap.NewProductionConfig()
	logger, err := cfgLogger.Build()
	if err != nil {
		log.Fatal("failed to create logger", err)
	}

	token := os.Getenv("TOKEN")
	dataParser := parser.NewDataParser(token, cfg.API.BaseURI, cfg.API.Endpoints, logger)

	redisClient := redis.NewRedisClient(context.Background(), cfg.Redis.Address, cfg.Redis.Password, cfg.Redis.DB, cfg.Redis.Timeout, logger)
	if err != nil {
		fmt.Printf("Error initializing Redis: %v\n", err)
		return
	}

	coordinator := handlers.NewCoordinator(&redisClient, dataParser)

	router := coordinator.SetupRoutes()

	// Start the server
	port := cfg.Server.Port
	addr := fmt.Sprintf(":%d", port)
	fmt.Printf("Server is running on http://localhost:%d\n", port)
	if err := http.ListenAndServe(addr, router); err != nil {
		fmt.Printf("Error starting the server: %v\n", err)
	}
}
