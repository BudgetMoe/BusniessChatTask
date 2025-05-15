package org.example.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class OpenAiConfig {

    @Value("\${openai.api.key}")
    private lateinit var openaiApiKey: String

    @Value("\${openai.api.url:https://api.openai.com/v1/chat/completions}")
    private lateinit var openaiApiUrl: String

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun openaiApiKey(): String {
        return openaiApiKey
    }

    @Bean
    fun openaiApiUrl(): String {
        return openaiApiUrl
    }
}