package org.example.repository

import org.example.model.Summary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SummaryRepository : JpaRepository<Summary, Long> {
    @Query("SELECT s FROM Summary s WHERE LOWER(s.keywords) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    fun findByKeyword(@Param("keyword") keyword: String): List<Summary>
}