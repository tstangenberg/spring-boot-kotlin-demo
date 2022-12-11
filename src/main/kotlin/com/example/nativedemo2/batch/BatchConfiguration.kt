package com.example.nativedemo2.batch

import org.springframework.aop.SpringProxy
import org.springframework.aop.framework.Advised
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor
import org.springframework.batch.core.launch.JobOperator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportRuntimeHints
import org.springframework.core.DecoratingProxy

/**
 * Spring Batch configuration
 */
@ImportRuntimeHints(SpringBatchRuntimeHints::class)
@Configuration(proxyBeanMethods = false)
class BatchConfiguration {
    // Eagerly discover all jobs inside the JobRepository, so they can be found via a locator.
    @Bean
    fun jobRegistryBeanPostProcessor(jobRegistry: JobRegistry): JobRegistryBeanPostProcessor =
        JobRegistryBeanPostProcessor().apply {
            setJobRegistry(jobRegistry)
        }
}

/**
 * Spring Batch related runtime hints
 *
 * Tells the native-image compiler to consider the JobOperator JDK proxy.
 * Otherwise, no job launch will be possible at the start of the application.
 *
 * https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html#native-image.advanced.custom-hints
 */
class SpringBatchRuntimeHints : RuntimeHintsRegistrar {
    override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
        // This was manually added by just converting the error message on launch into this code,
        // i.e. the order is straight from the error message.
        hints.proxies().registerJdkProxy(
            JobOperator::class.java,
            SpringProxy::class.java,
            Advised::class.java,
            DecoratingProxy::class.java,
        )
    }
}
