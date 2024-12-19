package io.jmix.bookstore.product.supplier.creation;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Configuration
@Profile("!integration-test")
public class SupplierOrderDraftCreationConfig {

    public static final int INTERVAL_MIN = 2;

    @Bean
    JobDetail supplierOrderDraft() {
        return JobBuilder.newJob()
                .ofType(SupplierOrderDraftCreationJob.class)
                .storeDurably()
                .withIdentity("supplierOrderDraftCreation")
                .build();
    }

    @Bean
    Trigger supplierOrderDraftTrigger(JobDetail supplierOrderDraft) {
        return TriggerBuilder.newTrigger()
                .forJob(supplierOrderDraft)
                .withIdentity("supplierOrderDraftTrigger")
                .startNow()
                .withSchedule(
                        cronSchedule("0 0/%d * * * ?".formatted(INTERVAL_MIN))
                )
                .build();
    }
}
