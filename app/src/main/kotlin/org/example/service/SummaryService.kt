package org.example.service

import org.example.model.Summary
import org.example.model.dto.SummaryDto
import org.example.repository.SummaryRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SummaryService(
    private val summaryRepository: SummaryRepository,
    private val openAiService: OpenAiService
) {

    //generate summary
    fun summarizeText(text: String): SummaryDto {
        // Use OpenAI API to generate the summary
        val summarizedText = generateSummaryWithOpenAi(text)
        val keywords = extractKeywords(text)

        val summary = Summary(
            originalText = text,
            summarizedText = summarizedText,
            keywords = keywords.joinToString(","),
            createdAt = LocalDateTime.now()
        )

        val savedSummary = summaryRepository.save(summary)
        return SummaryDto.fromEntity(savedSummary)
    }

    //fetch all summaries
    fun getAllSummaries(): List<SummaryDto> {
        return summaryRepository.findAll().map { SummaryDto.fromEntity(it) }
    }

    //Fetch summary by ID
    fun getSummaryById(id: Long): SummaryDto {
        val summary = summaryRepository.findById(id)
            .orElseThrow { NoSuchElementException("Summary not found with id: $id") }
        return SummaryDto.fromEntity(summary)
    }

    //find summary by keywords
    fun findSummariesByKeyword(keyword: String): List<SummaryDto> {
        return summaryRepository.findByKeyword(keyword).map { SummaryDto.fromEntity(it) }
    }


    private fun generateSummaryWithOpenAi(text: String): String {
        return try {
            val truncatedText = if (text.length > 4000) text.substring(0, 4000) + "..." else text
            openAiService.generateSummary(truncatedText)

        } catch (e: Exception) {
            // Fallback
            print(e.message)
            "An Error Occurred"

        }
    }


    //basic function to return the top most frequent words in a text, will return top 5
    private fun extractKeywords(text: String): List<String> {

        val commonWords = setOf("the", "a", "an", "and", "or", "but", "in", "on", "at", "to", "for", "with", "by",
            "is", "of", "this", "that", "it", "as", "are", "was", "were", "be", "from", "have", "has",
            "i", "you", "he", "she", "they", "we", "not", "do", "does", "did", "so", "can")

        return text.lowercase()
            .replace(Regex("[^a-z\\s]"), " ")
            .split(Regex("\\s+"))
            .filter { it.length > 3 && it !in commonWords }
            .groupBy { it }
            .mapValues { it.value.size }
            .entries
            .sortedByDescending { it.value }
            .take(5)
            .map { it.key }
    }
}