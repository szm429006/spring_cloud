package com.photonshalo.core.weather.config;

import com.photonshalo.core.weather.job.WeatherDataSyncJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

    private static final int TIME = 1800; // 更新频率

    // JobDetail
    @Bean
    public JobDetail weatherDataSyncJobDetail() {
        return JobBuilder.newJob(WeatherDataSyncJob.class).withIdentity("weatherDataDetail")
                .storeDurably().build();
    }

    // Trigger
    @Bean
    public Trigger weatherDataSyncTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(TIME).repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(weatherDataSyncJobDetail())
                .withIdentity("weatherDataTrigger")
                .withSchedule(scheduleBuilder).build();
    }
}
