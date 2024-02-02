package config

import (
	"github.com/spf13/viper"
	"time"
)

// Config представляет структуру конфигурации.
type Config struct {
	Server     ServerConfig
	API        APIConfig
	Redis      RedisConfig
	Prometheus PrometheusConfig
}

type APIConfig struct {
	BaseURI   string            `mapstructure:"base_uri"`
	Endpoints map[string]string `mapstructure:"endpoints"`
}

// ServerConfig представляет конфигурацию сервера.
type ServerConfig struct {
	Port int
}

// RedisConfig представляет конфигурацию для подключения к Redis.
type RedisConfig struct {
	Address  string
	Password string
	DB       int
	Timeout  time.Duration
}

// PrometheusConfig представляет конфигурацию для Prometheus.
type PrometheusConfig struct {
	Port int
}

// InitConfig инициализирует конфигурацию из файла.
func InitConfig() (*Config, error) {
	viper.SetConfigName("config")
	viper.SetConfigType("yaml")
	viper.AddConfigPath(".")
	viper.SetDefault("Server.Port", 8090)
	viper.SetDefault("Redis.Timeout", time.Second*5)
	viper.SetDefault("Prometheus.Port", 9090)

	if err := viper.ReadInConfig(); err != nil {
		return nil, err
	}

	var config Config
	if err := viper.Unmarshal(&config); err != nil {
		return nil, err
	}

	return &config, nil
}
