package com.example.calculate.presentation.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculate.domain.CalculatorEngine

/**
 * ViewModel for Calculator
 * Manages calculator state and business logic using MVVM architecture
 *
 * Benefits:
 * - Survives configuration changes (screen rotation)
 * - Separates UI logic from business logic
 * - Easier to test
 */
class CalculatorViewModel : ViewModel() {

    // Domain layer - Business logic
    private val engine = CalculatorEngine()

    // UI State - Exposed as LiveData for observation
    private val _uiState = MutableLiveData(CalculatorUiState())
    val uiState: LiveData<CalculatorUiState> = _uiState

    /**
     * Handles number button clicks
     */
    fun onNumberClick(digit: Int) {
        engine.appendDigit(digit)
        updateDisplay()
    }

    /**
     * Handles decimal point button click
     */
    fun onDecimalClick() {
        engine.inputDecimal()
        updateDisplay()
    }

    /**
     * Handles operation button clicks
     */
    fun onOperationClick(operation: CalculatorEngine.Operation) {
        engine.setOperation(operation)
        updateDisplay()
    }

    /**
     * Handles equals button click
     */
    fun onEqualsClick() {
        try {
            engine.calculate()
            updateDisplay()
            clearError()
        } catch (e: ArithmeticException) {
            showError(e.message ?: "Error")
        }
    }

    /**
     * Handles clear entry button (C)
     */
    fun onClearEntryClick() {
        engine.clearEntry()
        updateDisplay()
        clearError()
    }

    /**
     * Handles all clear button (AC)
     */
    fun onAllClearClick() {
        engine.clear()
        updateDisplay()
        clearError()
    }

    /**
     * Updates the display value in UI state
     */
    private fun updateDisplay() {
        _uiState.value = _uiState.value?.copy(
            displayValue = engine.getDisplayValue(),
            expression = engine.getExpression()
        )
    }

    /**
     * Shows error message in UI state
     */
    private fun showError(message: String) {
        _uiState.value = _uiState.value?.copy(
            displayValue = "Error",
            errorMessage = message
        )
    }

    /**
     * Clears error message in UI state
     */
    private fun clearError() {
        _uiState.value = _uiState.value?.copy(
            errorMessage = null
        )
    }
}
