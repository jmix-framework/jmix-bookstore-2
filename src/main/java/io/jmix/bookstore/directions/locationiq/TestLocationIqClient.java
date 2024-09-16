package io.jmix.bookstore.directions.locationiq;

import io.jmix.bookstore.JmixBookstoreApplication;
import io.jmix.bookstore.directions.CalculatedRoute;
import io.jmix.bookstore.directions.RouteAccuracy;
import io.jmix.maps.utils.GeometryUtils;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * LocationIqClient mock for performance tests.
 */
@Component("test_bookstore_Geocoding")
@Profile(JmixBookstoreApplication.PERFORMANCE_TESTS_PROFILE)
@Primary
public class TestLocationIqClient extends LocationIqClient {
    private static final Logger log = LoggerFactory.getLogger(TestLocationIqClient.class);

    public TestLocationIqClient(LocationIqProperties locationIqProperties, RestTemplate restTemplate) {
        super(locationIqProperties, restTemplate);
    }


    /**
     * @return a test route consists of single line between start and end point.
     */
    @Override
    public Optional<CalculatedRoute> calculateRoute(Point start, Point end, RouteAccuracy accuracy) {
        log.info("Calculating route between start: '{}' and end: '{}'. Disabled for tests, single line will be returned", start, end);
        return CalculatedRoute.fromValues(
                GeometryUtils.createLineString(
                        new Coordinate[]{start.getCoordinate(), end.getCoordinate()}), 1, 1);
    }
}
