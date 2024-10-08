package io.jmix.bookstore.util;

import java.util.Random;

public final class RandomUtils {

    public static int generateNormalDistributionDurationHalfHour() {
        double mean = 60;
        double stdDev = 30;
        double duration = new Random().nextGaussian() * stdDev + mean;

        int durationInMinutes = (int) (Math.round(duration / 30.0) * 30);

        return Math.min(Math.max(durationInMinutes, 30), 1440);
    }
}
