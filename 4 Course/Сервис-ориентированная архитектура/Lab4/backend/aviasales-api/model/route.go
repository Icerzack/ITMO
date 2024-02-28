package model

// Routes содержит информацию по routes
type Routes struct {
	Routes []Route `mapstructure:"routes" json:"routes"`
}

// Route представляет информацию о маршруте.
type Route struct {
	Origin             string `mapstructure:"origin" json:"origin"`                                     // An IATA code of a city of the origin
	OriginAirport      string `mapstructure:"origin_airport" json:"origin_airport,omitempty"`           // An IATA code of an airport of the origin
	Destination        string `mapstructure:"destination" json:"destination"`                           // An IATA code of a city of the destination
	DestinationAirport string `mapstructure:"destination_airport" json:"destination_airport,omitempty"` // An IATA code of an airport of the destination

	Airline      string `mapstructure:"airline" json:"airline,omitempty"`             // The code of an airline
	AirlineTitle string `mapstructure:"airline_title" json:"airline_title,omitempty"` // The name of an airline
	FlightNumber string `mapstructure:"flight_number" json:"flight_number,omitempty"` // The number of a flight

	ReturnTransfers int    `mapstructure:"return_transfers" json:"return_transfers,omitempty"` // Number of transfers from destination point to an origin
	Transfers       int    `mapstructure:"transfers" json:"transfers,omitempty"`               // Number of transfers from an origin point to a destination
	Duration        int    `mapstructure:"duration" json:"duration,omitempty"`                 // Flight duration in minutes
	DepartureAt     string `mapstructure:"departure_at" json:"departure_at,omitempty"`         // A date of departure (RFC3339 format)
	ReturnAt        string `mapstructure:"return_at" json:"return_at,omitempty"`               // A date of return departure (RFC3339 format)

	FoundAt  string  `mapstructure:"found_at" json:"found_at,omitempty"` // A date what which the ticket was cloud (RFC3339 format)
	Link     string  `mapstructure:"link" json:"link,omitempty"`         // A link on the flight ticket
	Currency string  `mapstructure:"currency" json:"currency"`           // Ticket price currency
	Price    float32 `mapstructure:"price" json:"price,omitempty"`       // Ticket price
}

// NewRoute создает новый объект Route с заданными параметрами.
func NewRoute(data map[string]interface{}) *Route {
	return &Route{
		Origin:             getString(data, "origin"),
		OriginAirport:      getString(data, "origin_airport"),
		Destination:        getString(data, "destination"),
		DestinationAirport: getString(data, "destination_airport"),
		Airline:            getString(data, "airline"),
		AirlineTitle:       getString(data, "airline_title"),
		FlightNumber:       getString(data, "flight_number"),
		ReturnTransfers:    getInt(data, "return_transfers"),
		Transfers:          getInt(data, "transfers"),
		Duration:           getInt(data, "duration"),
		DepartureAt:        getString(data, "departure_at"),
		ReturnAt:           getString(data, "return_at"),
		FoundAt:            getString(data, "found_at"),
		Link:               getString(data, "link"),
		Currency:           getString(data, "currency"),
		Price:              getFloat32(data, "price"),
	}
}

func getString(data map[string]interface{}, key string) string {
	if value, ok := data[key].(string); ok {
		return value
	}
	return ""
}

func getInt(data map[string]interface{}, key string) int {
	if value, ok := data[key].(int); ok {
		return value
	}
	return 0
}

func getFloat32(data map[string]interface{}, key string) float32 {
	if value, ok := data[key].(float32); ok {
		return value
	}
	return 0.0
}
