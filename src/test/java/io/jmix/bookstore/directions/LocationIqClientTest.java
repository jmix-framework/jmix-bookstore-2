package io.jmix.bookstore.directions;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseActions;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {
                "bookstore.locationiq.base-url=" + LocationIqClientTest.BASE_URL,
                "bookstore.locationiq.api-key=" + LocationIqClientTest.API_KEY,
        }
)
@ActiveProfiles("integration-test")
class LocationIqClientTest {

    public static final String BASE_URL = "https://locationiq-dummy.com";
    public static final String API_KEY = "api-key-123";
    @Autowired
    private Geocoding locationIqClient;
    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final Point fifthAvenue = geometryFactory.createPoint(new Coordinate(-73.9849336, 40.7487727));
    private final Point empireStateBuilding = geometryFactory.createPoint(new Coordinate(-73.9905353, 40.7484404));

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Nested
    class HappyPath {
        @Test
        void given_validDirectionsRequest_expect_validResponseIsCorrectlyConverted() {

            // given:
            expectDirectionsRequestFor(fifthAvenue, empireStateBuilding)
                    .andRespond(withSuccess()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("""
                                    {
                                       "routes": [
                                         {
                                           "geometry": {
                                             "coordinates": [
                                               [ -0.161136, 51.523832 ],
                                               [ -0.160984, 51.523516 ],
                                               [ -0.161606, 51.522557 ]
                                             ],
                                             "type": "LineString"
                                           }
                                         }
                                       ]
                                     }
                                    """));


            // when:
            Optional<CalculatedRoute> possibleLineString = locationIqClient.calculateRoute(fifthAvenue, empireStateBuilding);

            // then:
            assertThat(possibleLineString)
                    .isPresent();

            // and:
            assertThat(possibleLineString.get().lineString().getCoordinates())
                    .containsExactly(
                            new Coordinate( -0.161136, 51.523832),
                            new Coordinate( -0.160984, 51.523516),
                            new Coordinate( -0.161606, 51.522557)
                    );

            // and:
            mockServer.verify();
        }
    }

    @NotNull
    private ResponseActions expectDirectionsRequestFor(Point start, Point end) {
        return mockServer.expect(requestToUriTemplate(
                        BASE_URL + "/v1/{service}/{profile}/{coordinates}?key={key}&geometries={geometries}&overview={overview}",
                        "directions",
                        "driving",
                        "%s,%s;%s,%s".formatted(start.getX(), start.getY(), end.getX(), end.getY()),
                        API_KEY,
                        "geojson",
                        "full"
                ))
                .andExpect(method(HttpMethod.GET));
    }

    @Nested
    class OnFailureBehaviour {


        @Test
        void given_noDirectionReturned_expect_resultIsEmpty() {

            // given:
            expectDirectionsRequestFor(fifthAvenue, empireStateBuilding)
                    .andRespond(withSuccess()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("""
                                    {
                                       "routes": []
                                     }
                                    """));

            // expect:
            assertThat(locationIqClient.calculateRoute(fifthAvenue, empireStateBuilding))
                    .isEmpty();

            // and:
            mockServer.verify();
        }


        @Test
        void given_apiReturnsUnauthorized_expect_noResult() {

            // given:
            expectDirectionsRequestFor(fifthAvenue, empireStateBuilding)
                    .andRespond(withStatus(HttpStatus.UNAUTHORIZED));

            // expect:
            assertThat(locationIqClient.calculateRoute(fifthAvenue, empireStateBuilding))
                    .isEmpty();

            // and:
            mockServer.verify();
        }

        @Test
        void given_apiReturnsInternalServerError_expect_noResult() {

            // given:
            expectDirectionsRequestFor(fifthAvenue, empireStateBuilding)
                    .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

            // expect:
            assertThat(locationIqClient.calculateRoute(fifthAvenue, empireStateBuilding))
                    .isEmpty();

            // and:
            mockServer.verify();
        }

        @Test
        void given_apiReturnsBadRequest_expect_noResult() {

            // given:
            expectDirectionsRequestFor(fifthAvenue, empireStateBuilding)
                    .andRespond(withStatus(HttpStatus.BAD_REQUEST));

            // expect:
            assertThat(locationIqClient.calculateRoute(fifthAvenue, empireStateBuilding))
                    .isEmpty();

            // and:
            mockServer.verify();
        }
    }
}
