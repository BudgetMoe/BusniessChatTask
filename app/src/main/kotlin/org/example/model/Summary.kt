package org.example.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "summaries")
data class Summary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 10000)
    val originalText: String,

    @Column(nullable = false, length = 5000)
    val summarizedText: String,

    @Column(nullable = false)
    val keywords: String, // Comma-separated keywords

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
