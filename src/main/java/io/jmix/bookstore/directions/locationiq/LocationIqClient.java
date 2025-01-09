package io.jmix.bookstore.directions.locationiq;

import com.fasterxml.jackson.databind.JsonNode;
import io.jmix.bookstore.directions.AddressInformation;
import io.jmix.bookstore.directions.CalculatedRoute;
import io.jmix.bookstore.directions.Geocoding;
import io.jmix.bookstore.directions.RouteAccuracy;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * A custom HTTP API Client to interact with the LocationIQ API Service.
 * It is possible to create a free account for personal / experimental usage.
 * See: <a href="https://locationiq.com/">LocationIQ</a>
 */
@Component(Geocoding.NAME)
public class LocationIqClient implements Geocoding {
    private static final Logger log = LoggerFactory.getLogger(LocationIqClient.class);

    private final LocationIqProperties locationIqProperties;
    private final RestTemplate restTemplate;
    public LocationIqClient(LocationIqProperties locationIqProperties, RestTemplate restTemplate) {
        this.locationIqProperties = locationIqProperties;
        this.restTemplate = restTemplate;
    }

    /**
     * Calculating a route based on two points against the LocationIQ API.
     * See: <a href="https://locationiq.com/docs-html/index.html#directions">LocationIQ - Directions API</a>
     *
     * @param start the starting point of the route
     * @param end the end point of the route
     * @return a CalculatedRoute representing the route if possible to calculate
     */
    @Override
    public Optional<CalculatedRoute> calculateRoute(Point start, Point end) {
        return calculateRoute(start, end, RouteAccuracy.HIGH_ACCURACY);
    }

    /**
     * Calculating a route based on two points against the LocationIQ API.
     * See: <a href="https://locationiq.com/docs-html/index.html#directions">LocationIQ - Directions API</a>
     *
     * @param start the starting point of the route
     * @param end the end point of the route
     * @param accuracy the calculation accuracy which is considered when calculating the route
     * @return a CalculatedRoute representing the route if possible to calculate
     */
    @Override
    public Optional<CalculatedRoute> calculateRoute(Point start, Point end, RouteAccuracy accuracy) {

        log.info("Calculating route between start: '{}' and end: '{}' via LocationIQ API", start, end);

        try {
            DirectionsApiResponse response = restTemplate.getForObject(
                    directionsApiUrl(start, end, accuracy),
                    DirectionsApiResponse.class
            );

            if (CollectionUtils.isEmpty(response.routes())) {
                return Optional.empty();
            }

            DirectionsApiResponse.Route route = response.routes.get(0);

            return CalculatedRoute.fromValues(
                    route.toLineString(),
                    route.duration(),
                    route.distance()
            );

        } catch (Exception e) {
            log.warn("Error loading route information from API", e);
            return Optional.empty();
        }
    }



    /**
     * Performs a Forward Geocoding Operation (Address -> Point).
     * See: <a href="https://locationiq.com/docs-html/index.html#search-forward-geocoding">LocationIQ - Forward Geocoding API</a>
     * @param addressInformation the address information to search for
     * @return the Point of the address, if found
     */
    @Override
    public Optional<Point> forwardGeocoding(AddressInformation addressInformation) {

        log.info("Forward Geocoding for Address Information: '{}' via LocationIQ API", addressInformation);

        try {
            ResponseEntity<List<ForwardGeocodingPlace>> response = restTemplate.exchange(
                    forwardGeocodingApiUrl(addressInformation),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            return toOptionalPoint(response);
        } catch (Exception e) {
            log.warn("Error loading route information from API", e);
            return Optional.empty();
        }
    }

    private Optional<Point> toOptionalPoint(ResponseEntity<List<ForwardGeocodingPlace>> response) {
        if (CollectionUtils.isEmpty(response.getBody())) {
            return Optional.empty();
        }

        ForwardGeocodingPlace forwardGeocodingPlace = response.getBody().get(0);
        return Optional.ofNullable(forwardGeocodingPlace.point());
    }

    private Optional<LineString> toOptionalLineString(DirectionsApiResponse response) throws ParseException {
        if (CollectionUtils.isEmpty(response.routes())) {
            return Optional.empty();
        }

        String json = response.routes.get(0).geometry().toString();
        return Optional.of((LineString) new GeoJsonReader().read(json));
    }

    private URI directionsApiUrl(Point start, Point end, RouteAccuracy accuracy) {
        return locationIqBaseUrl()
                .path("/v1/directions/driving/{coordinates}")
                .queryParam("geometries", "geojson")
                .queryParam("overview", toDirectionsApiOverviewAttribute(accuracy))
                .buildAndExpand(coordinatesQueryParameter(start, end))
                .toUri();
    }

    private String toDirectionsApiOverviewAttribute(RouteAccuracy accuracy) {
        return switch (accuracy) {
            case HIGH_ACCURACY -> "full";
            case LOW_ACCURACY -> "simplified";
        };
    }

    private URI forwardGeocodingApiUrl(AddressInformation addressInformation) {
        return locationIqBaseUrl()
                .path("/v1/search.php")
                .queryParam("street", addressInformation.street())
                .queryParam("city", addressInformation.city())
                .queryParam("state", addressInformation.state())
                .queryParam("postalcode", addressInformation.postalCode())
                .queryParam("country", addressInformation.country())
                .queryParam("format", "json")
                .build()
                .toUri();
    }

    private UriComponentsBuilder locationIqBaseUrl() {
        return UriComponentsBuilder.fromUriString(locationIqProperties.getBaseUrl())
                .queryParam("key", locationIqProperties.getApiKey());
    }

    private String coordinatesQueryParameter(Point start, Point end) {
        return String.join(";", List.of(toPointString(start), toPointString(end)));
    }

    private  String toPointString(Point position) {
        return String.join(",", "" + position.getX(), "" + position.getY());
    }

    /**
     * Record to represent the JSON response of the LocationIQ Directions API with geojson geometry format.
     * Example JSON response (displayed only relevant parts)
     * <pre>
     * {
     *   "code": "Ok",
     *   "routes": [
     *     {
     *       "geometry": {
     *         "coordinates": [
     *           [
     *             -0.161136,
     *             51.523832
     *           ],
     *           [
     *             -0.160984,
     *             51.523516
     *           ],
     *           [
     *             -0.161606,
     *             51.522557
     *           ]
     *         ],
     *         "type": "LineString"
     *       },
     *       "duration": 138.8,
     *       "distance": 844.5
     *     }
     *   ]
     * }
     * </pre>
     */
    record DirectionsApiResponse(List<Route> routes) {
        record Route(JsonNode geometry, float duration, float distance) {

            public LineString toLineString() throws ParseException {
                return (LineString) new GeoJsonReader().read(
                        this.geometry().toString()
                );
            }
        }
    }

    /**
     * Record to represent the JSON response of the LocationIQ Directions API that allows converting addresses, 
     * into geographic coordinates (latitude and longitude).
     * Example JSON response (displayed only relevant parts)
     * <pre>
     *   [
     *       {
     *           "lat": "48.8588897",
     *           "lon": "2.3200410217200766",
     *       },
     *   ]
     * </pre>
     */
    record ForwardGeocodingPlace(double lat, double lon) {
        public Point point() {
            GeometryFactory geometryFactory = new GeometryFactory();
            return geometryFactory.createPoint(new Coordinate(lon, lat));
        }
    }

}
