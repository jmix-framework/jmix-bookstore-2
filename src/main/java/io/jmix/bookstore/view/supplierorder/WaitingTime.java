package io.jmix.bookstore.view.supplierorder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record WaitingTime(Duration duration, int roundToSeconds) {
    public static WaitingTime fromNow(LocalDateTime now, LocalDateTime toPointInTime) {
        return new WaitingTime(Duration.between(now, toPointInTime), 5);
    }

    public String prettyPrint() {

        return parts().stream()
                .filter(WaitingTimeParts::isPresent)
                .map(WaitingTimeParts::prettyPrint)
                .collect(Collectors.joining(" "));
    }

    private List<WaitingTimeParts> parts() {

        return List.of(
                minutesPart(),
                secondsPart()
        );
    }


    private WaitingTimeParts minutesPart() {
        if (roundedUpSecondsIsOneMinute()) {
            return new WaitingTimeParts(absoluteDuration().toMinutesPart() + 1, "m");
        }

        return new WaitingTimeParts(absoluteDuration().toMinutesPart(), "m");
    }

    private boolean roundedUpSecondsIsOneMinute() {
        return roundedUpSeconds() == 60;
    }

    private int roundedUpSeconds() {
        int secondsPart = absoluteDuration().toSecondsPart();

        if (secondsPart % roundToSeconds == 0) {
            return secondsPart;
        }

        return secondsPart + (roundToSeconds - secondsPart % roundToSeconds);
    }
    private WaitingTimeParts secondsPart() {

        if (roundedUpSecondsIsOneMinute()) {
            return new WaitingTimeParts(0, "s");
        }

        return new WaitingTimeParts(roundedUpSeconds(), "s");
    }

    private Duration absoluteDuration() {
        return duration.abs();
    }

    public boolean isHigh() {
        return minutesPart().amount() > 3;
    }

    public boolean isMedium() {
        return minutesPart().amount() < 3 && minutesPart().amount() >= 1;
    }

    public boolean isLow() {
        return minutesPart().amount() < 1;
    }

    private record WaitingTimeParts(int amount, String suffix) {
        public boolean isPresent() {
            return amount > 0;
        }

        public String prettyPrint() {
            return String.format("%s%s", amount, suffix);
        }
    }
}
