package com.example.nativedemo2.batch

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager


// Simple batch job that tests the integration of Spring Batch inside this project.
@Configuration(proxyBeanMethods = false)
class FirstJobConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
) {
    @Bean
    fun firstJob(firstStep: Step): Job = JobBuilder("firstJob", jobRepository)
        .start(firstStep)
        .build()

    @Bean
    fun firstStep(): Step = StepBuilder("firstStep", jobRepository)
        .tasklet({ _, _ ->
            log.info("Hello, world!")
            RepeatStatus.FINISHED
        }, platformTransactionManager)
        .build()

    private companion object {
        @JvmStatic
        private val log: Logger = LoggerFactory.getLogger(this::class.java.enclosingClass)
    }
}
