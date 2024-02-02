package parser

import (
	"encoding/json"
	"errors"
	"fmt"
	"github.com/mitchellh/mapstructure"
	"github.com/whiteforestever/aviasales-api/model"
	"go.uber.org/zap"
	"net/http"
	"time"
)

const (
	defaultTimeout = 10 * time.Second
)

// DataParser represents the data parsing functionality.
type DataParser struct {
	client http.Client
	token  string

	host string
	urls map[string]string // map[method]path, ex. [prices_calendar]: prices/calendar

	logger *zap.Logger
}

// NewDataParser creates a new instance of DataParser with the provided token and site map.
func NewDataParser(token string, host string, urls map[string]string, logger *zap.Logger) DataParser {
	return DataParser{
		client: http.Client{
			Timeout: defaultTimeout,
		},
		token:  token,
		host:   host,
		urls:   urls,
		logger: logger,
	}
}

// ParseData parses data from an external API by input url.
func (p *DataParser) ParseData(endpointName string, queryParams map[string]string) (model.Routes, error) {
	path, ok := p.urls[endpointName]
	if !ok {
		p.logger.Error("invalid parser method", zap.String("endpointName", endpointName))
	}

	req, err := http.NewRequest("GET", p.host+path, nil)
	if err != nil {
		p.logger.Error("can't create New Request", zap.Error(err), zap.String("path", path))
		return model.Routes{}, err
	}

	q := req.URL.Query()
	q.Add("token", p.token)
	for k, v := range queryParams {
		q.Add(k, v)
	}
	req.URL.RawQuery = q.Encode()

	response, err := p.client.Get(req.URL.String())
	if err != nil {
		p.logger.Error("failed to fetch data from API", zap.Error(err), zap.String("url", req.URL.String()))
		return model.Routes{}, err
	}
	defer response.Body.Close()

	if response.StatusCode != http.StatusOK {
		p.logger.Error("API returned non-OK status", zap.Error(err), zap.String("url", req.URL.String()))
		return model.Routes{}, errors.New(fmt.Sprintf("API returned non-OK status %d", response.StatusCode))
	}

	var data map[string]interface{}
	err = json.NewDecoder(response.Body).Decode(&data)
	if err != nil {
		p.logger.Error("failed to decode JSON response", zap.Error(err), zap.String("url", req.URL.String()))
		return model.Routes{}, err
	}

	var routes model.Routes
	for _, v := range data["data"].([]interface{}) {
		var route model.Route
		mapstructure.Decode(v.(map[string]interface{}), &route)
		routes.Routes = append(routes.Routes, route)
	}

	return routes, nil
}
