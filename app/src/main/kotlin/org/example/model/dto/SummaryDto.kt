package org.example.model.dto

import java.time.LocalDateTime

data class SummaryDto(
    val id: Long? = null,
    val originalText: String,
    val summarizedText: String,
    val keywords: List<String>,
    val createdAt: LocalDateTime? = null
) {
    companion object {
        fun fromEntity(entity: org.example.model.Summary): SummaryDto =
            SummaryDto(
                id = entity.id,
                originalText = entity.originalText,
                summarizedText = entity.summarizedText,
                keywords = entity.keywords.split(",").map { it.trim() },
                createdAt = entity.createdAt
            )
    }

//    fun toEntity(): org.example.model.Summary =
//        org.example.model.Summary(
//            id = this.id,
//            originalText = this.originalText,
//            summarizedText = this.summarizedText,
//            keywords = this.keywords.joinToString(","),
//            createdAt = this.createdAt ?: LocalDateTime.now()
//        )
}