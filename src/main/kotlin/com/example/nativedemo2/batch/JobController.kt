package com.example.nativedemo2.batch

import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.configuration.JobLocator
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.*

/**
 * Controller that allows (re-)running jobs using their name.
 */
@RestController
class JobController(private val jobLocator: JobLocator, private val jobLauncher: JobLauncher) {
    @PutMapping("/job/{jobName}")
    fun launchJob(@PathVariable jobName: String): Mono<Long> = try {
        val job = jobLocator.getJob(jobName)
        // FIXME: Somehow we can't use a LocalDate(Time) here because there is no converter...?
        val currentDateParameters = JobParametersBuilder().addDate("date", Date()).toJobParameters()
        val execution = jobLauncher.run(job, currentDateParameters)
        Mono.just(execution.jobInstance.instanceId)
    } catch (alreadyExists: JobInstanceAlreadyExistsException) {
        Mono.empty()
    }
}
