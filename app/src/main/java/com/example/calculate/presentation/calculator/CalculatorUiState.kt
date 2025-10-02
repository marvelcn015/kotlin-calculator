package com.example.calculate.presentation.calculator

/**
 * UI State for Calculator
 * Represents the current state of the calculator display
 */
data class CalculatorUiState(
    val displayValue: String = "0",
    val expression: String = "",
    val errorMessage: String? = null
)
