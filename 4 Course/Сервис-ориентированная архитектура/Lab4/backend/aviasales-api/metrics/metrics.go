package metrics

import (
	"github.com/prometheus/client_golang/prometheus"
	"github.com/prometheus/client_golang/prometheus/promauto"
)

type Metrics struct {
	RequestsTotal *prometheus.CounterVec
}

var (
	// RequestsTotal represents the total number of HTTP requests.
	RequestsTotal = promauto.NewCounterVec(
		prometheus.CounterOpts{
			Name: "http_requests_total",
			Help: "Total number of HTTP requests.",
		},
		[]string{"code", "method"},
	)
)

func RegisterMetrics(reg prometheus.Registerer) {
	reg.MustRegister(RequestsTotal)
}
