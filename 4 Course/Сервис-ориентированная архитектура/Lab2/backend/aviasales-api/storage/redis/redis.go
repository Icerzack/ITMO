package redis

import (
	"context"
	"encoding/json"
	"github.com/redis/go-redis/v9"
	"github.com/whiteforestever/aviasales-api/model"
	"time"

	"go.uber.org/zap"
)

type Storage interface {
	SaveData(ctx context.Context, key string, route model.Route) error
	GetData(key string) (interface{}, error)
}

// RedisClient представляет собой клиент хранилища.
type RedisClient struct {
	redisClient *redis.Client

	logger *zap.Logger
}

// NewRedisClient initialize connection to the Storage.
func NewRedisClient(ctx context.Context, address string, password string, db int, timeout time.Duration, logger *zap.Logger) RedisClient {
	redisClientWithOpts := redis.NewClient(&redis.Options{
		Addr:         address,
		Password:     password,
		DB:           db,
		ReadTimeout:  timeout,
		WriteTimeout: timeout,
	})

	redisClient := RedisClient{
		redisClient: redisClientWithOpts,
		logger:      logger,
	}

	_, err := redisClient.redisClient.Ping(ctx).Result()
	if err != nil {
		redisClient.logger.Error("failed to ping Redis", zap.Error(err))
		return RedisClient{}
	}

	return redisClient
}

// SaveData save data to redis storage
func (s *RedisClient) SaveData(ctx context.Context, key string, route model.Route) error {
	var routeMap map[string]interface{}
	data, _ := json.Marshal(route)
	json.Unmarshal(data, &routeMap)
	err := s.redisClient.HSet(ctx, key, routeMap).Err()
	if err != nil {
		s.logger.Error("failed to save data to Redis", zap.Error(err))
		return err
	}
	return nil
}

// GetData извлекает данные из Storage
func (s *RedisClient) GetData(key string) (interface{}, error) {
	dataType, err := s.redisClient.Type(context.Background(), key).Result()
	if err != nil {
		if err == redis.Nil {
			s.logger.Error("can't find data in Redis", zap.String("key", key), zap.Error(err))
			return nil, err
		}
		s.logger.Error("can't check with type of key in Redis", zap.String("key", key), zap.Error(err))
		return nil, err
	}

	if dataType != "hash" {
		s.logger.Error("undefined type for key in Redis", zap.String("key", key), zap.Error(err))
		return nil, err
	}

	result, err := s.redisClient.HGetAll(context.Background(), key).Result()
	if err != nil {
		s.logger.Error("can't get data from Redis", zap.String("key", key), zap.Error(err))
		return nil, err
	}

	return result, nil
}
