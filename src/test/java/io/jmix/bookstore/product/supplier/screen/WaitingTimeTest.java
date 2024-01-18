//package io.jmix.bookstore.product.supplier.screen;
//
//import io.jmix.bookstore.product.supplier.screen.supplierorder.WaitingTime;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.time.Duration;
//import java.util.stream.Stream;
//
//import static io.jmix.bookstore.entity.Assertions.assertThat;
//
//
//class WaitingTimeTest {
//
//    public static Stream<Arguments> waitingTimeExamples() {
//        return Stream.of(
//                Arguments.of(duration(1, 10), "1m 10s"),
//                Arguments.of(duration(1, 57), "2m"),
//                Arguments.of(duration(2, 5),"2m 5s"),
//                Arguments.of(duration(3, 5),"3m 5s"),
//                Arguments.of(duration(3, 2),"3m 5s"),
//                Arguments.of(duration(0, 15),"15s"),
//                Arguments.of(duration(0, 13),"15s"),
//                Arguments.of(duration(0, 0),"")
//        );
//    }
//
//    private static Duration duration(int minutes, int seconds) {
//        return Duration.ofMinutes(minutes).plusSeconds(seconds);
//    }
//
//    @ParameterizedTest
//    @MethodSource("waitingTimeExamples")
//    void waitingTime_isCorrectlyRendered(
//            Duration duration,
//            String expectedPrettyPrint
//    ) {
//
//        WaitingTime waitingTime = new WaitingTime(duration, 5);
//
//        assertThat(waitingTime.prettyPrint())
//                .isEqualTo(expectedPrettyPrint);
//    }
//}
