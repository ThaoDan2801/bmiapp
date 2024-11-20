package com.monk.bmi.model

data class History(
    val id: Int,
    val height: Float? = null,
    val weight: Float? = null,
    val bmi: Double,
    val note: String? = null,
    val createdAt: Long? = null
)
