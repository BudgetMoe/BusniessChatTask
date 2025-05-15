package org.example.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.example.model.dto.SummarizeRequestDto
import org.example.model.dto.SummaryDto
import org.example.service.SummaryService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@Tag(name = "Summary API", description = "Endpoints for text summarization")
class SummaryController(private val summaryService: SummaryService) {

    @PostMapping("/summarize")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Summarize text",
        description = "Creates a summary of the provided text and stores it"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Summary created successfully",
        content = [Content(schema = Schema(implementation = SummaryDto::class))]
    )
    fun summarizeText(@RequestBody request: SummarizeRequestDto): SummaryDto {
        return summaryService.summarizeText(request.text)
    }

    @GetMapping("/summaries")
    @Operation(
        summary = "Get all summaries",
        description = "Retrieves all summaries or filters by keyword if provided"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successful operation",
        content = [Content(schema = Schema(implementation = SummaryDto::class))]
    )
    fun getSummaries(
        @Parameter(description = "Keyword to filter summaries")
        @RequestParam(required = false) keyword: String?
    ): List<SummaryDto> {
        return if (keyword != null) {
            summaryService.findSummariesByKeyword(keyword)
        } else {
            summaryService.getAllSummaries()
        }
    }

    @GetMapping("/summaries/{id}")
    @Operation(
        summary = "Get summary by ID",
        description = "Retrieves a specific summary by its ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successful operation",
        content = [Content(schema = Schema(implementation = SummaryDto::class))]
    )
    @ApiResponse(
        responseCode = "404",
        description = "Summary not found"
    )
    fun getSummaryById(@PathVariable id: Long): SummaryDto {
        return summaryService.getSummaryById(id)
    }
}