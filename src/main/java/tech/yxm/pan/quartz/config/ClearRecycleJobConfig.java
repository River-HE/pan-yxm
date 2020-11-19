package tech.yxm.pan.quartz.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.yxm.pan.quartz.job.ClearRecycleJob;

/**
 * @author river
 * @date 2020/11/18 00:05:58
 * @description 清理recycle文件夹的任务的配置
 */

@Configuration
@Slf4j
public class ClearRecycleJobConfig {

    @Value("${pan.quartz-jobs.clear-recycle-cron-schedule}")
    private String clearRecycleCronSchedule;

    /**
     * 生成JobDetail，即配置任务运行的详细信息，相当于注册一个任务
     *
     * @return
     */
    @Bean
    public JobDetail jobDetail() {
        log.info("JobDetail: clear_recycle_job");
        return JobBuilder.newJob(ClearRecycleJob.class)
                .withIdentity("clear_recycle_job")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger() {
        log.info("Trigger: clear_recycle_trigger");
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("clear_recycle_trigger")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(clearRecycleCronSchedule))
                .build();
    }
}
