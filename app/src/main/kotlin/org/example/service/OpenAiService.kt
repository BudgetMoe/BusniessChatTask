package org.example.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

@Service
class OpenAiService(
    private val restTemplate: RestTemplate,
    @Qualifier("openaiApiKey") private val apiKey: String,
    @Qualifier("openaiApiUrl") private val apiUrl: String
) {


    fun generateSummary(text: String): String {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set("Authorization", "Bearer $apiKey")
        }

        val requestBody = ChatCompletionRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(
                Message(
                    role = "system",
                    content = "You are a text summarization assistant. Provide a concise summary of the given text."
                ),
                Message(
                    role = "user",
                    content = "Please summarize this text: $text"
                )
            ),
            maxTokens = 150,
            temperature = 0.5
        )

        val request = HttpEntity(
            jacksonObjectMapper().writeValueAsString(requestBody),
            headers
        )

        try {
            val response = restTemplate.postForObject(apiUrl, request, ChatCompletionResponse::class.java)
            return response?.choices?.firstOrNull()?.message?.content?.trim() ?: "No summary generated."
        } catch (e: Exception) {
            // Log the error or handle it as needed
            return "Unable to generate summary: ${e.message}"
        }
    }

    // Data classes for OpenAI API communication
    data class ChatCompletionRequest(
        val model: String,
        val messages: List<Message>,
        @JsonProperty("max_tokens") val maxTokens: Int,
        val temperature: Double
    )

    data class Message(
        val role: String,
        val content: String
    )

    data class ChatCompletionResponse(
        val id: String,
        val choices: List<Choice>
    )

    data class Choice(
        val index: Int,
        val message: Message,
        @JsonProperty("finish_reason") val finishReason: String
    )
}