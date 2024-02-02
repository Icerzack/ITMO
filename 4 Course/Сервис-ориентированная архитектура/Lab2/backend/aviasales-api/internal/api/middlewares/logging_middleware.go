package middlewares

import (
	"fmt"
	"time"

	"github.com/gin-gonic/gin"
)

// LoggingMiddleware is a middlewares that logs incoming requests.
func LoggingMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		startTime := time.Now()
		c.Next()
		duration := time.Since(startTime)
		fmt.Printf("[%s] %s %s %v\n", c.Request.Method, c.Request.URL.Path, c.Request.RemoteAddr, duration)
	}
}
