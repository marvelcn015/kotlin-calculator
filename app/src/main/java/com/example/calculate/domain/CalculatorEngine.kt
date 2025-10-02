package com.example.calculate.domain

/**
 * Calculator engine that handles all calculation logic
 * Implements a simple calculator with basic arithmetic operations
 *
 * Domain layer - Pure business logic with no Android dependencies
 */
class CalculatorEngine {

    private var currentValue: Double = 0.0
    private var operand: Double = 0.0
    private var currentOperation: Operation? = null
    private var isNewNumber: Boolean = true
    private var hasDecimal: Boolean = false
    private var expression: String = ""

    enum class Operation(val symbol: String) {
        ADD("+"), SUBTRACT("-"), MULTIPLY("×"), DIVIDE("÷")
    }

    /**
     * Gets the current display value
     */
    fun getDisplayValue(): String {
        // Remove trailing zeros and decimal point if not needed
        val formatted = if (currentValue % 1.0 == 0.0) {
            currentValue.toLong().toString()
        } else {
            currentValue.toString()
        }
        return formatted
    }

    /**
     * Gets the current expression string
     */
    fun getExpression(): String {
        return expression
    }

    /**
     * Formats a number for display in expression
     */
    private fun formatNumber(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }

    /**
     * Handles number input
     */
    fun inputNumber(digit: Int) {
        if (isNewNumber) {
            currentValue = digit.toDouble()
            isNewNumber = false
            hasDecimal = false
        } else {
            val currentStr = if (currentValue % 1.0 == 0.0) {
                currentValue.toLong().toString()
            } else {
                currentValue.toString()
            }

            // Limit to 15 digits to prevent overflow
            if (currentStr.replace(".", "").replace("-", "").length < 15) {
                currentValue = (currentStr + digit).toDouble()
            }
        }
    }

    /**
     * Handles decimal point input
     */
    fun inputDecimal() {
        if (isNewNumber) {
            currentValue = 0.0
            isNewNumber = false
        }
        hasDecimal = true
        // The actual decimal handling is done in inputNumber
    }

    /**
     * Appends digit considering decimal point
     */
    fun appendDigit(digit: Int) {
        if (isNewNumber) {
            currentValue = digit.toDouble()
            isNewNumber = false
        } else {
            if (hasDecimal) {
                // Add digit after decimal point
                val currentStr = currentValue.toString()
                val newStr = currentStr + digit
                currentValue = newStr.toDouble()
            } else {
                // Add digit before decimal point
                currentValue = currentValue * 10 + digit
            }
        }
    }

    /**
     * Sets the operation to perform
     */
    fun setOperation(operation: Operation) {
        if (currentOperation != null && !isNewNumber) {
            calculate()
        }
        operand = currentValue
        currentOperation = operation

        // Build expression string
        expression = "${formatNumber(operand)} ${operation.symbol} "

        isNewNumber = true
        hasDecimal = false
    }

    /**
     * Performs the calculation
     */
    fun calculate() {
        if (currentOperation == null) return

        try {
            // Complete the expression string before calculating
            expression += formatNumber(currentValue)

            val result = when (currentOperation) {
                Operation.ADD -> operand + currentValue
                Operation.SUBTRACT -> operand - currentValue
                Operation.MULTIPLY -> operand * currentValue
                Operation.DIVIDE -> {
                    if (currentValue == 0.0) {
                        // Handle division by zero
                        throw ArithmeticException("Cannot divide by zero")
                    }
                    operand / currentValue
                }
                null -> currentValue
            }

            currentValue = result
            currentOperation = null
            isNewNumber = true
            hasDecimal = false
        } catch (e: ArithmeticException) {
            expression = ""
            clear()
            throw e
        }
    }

    /**
     * Clears the current input (C button)
     */
    fun clearEntry() {
        currentValue = 0.0
        isNewNumber = true
        hasDecimal = false
    }

    /**
     * Clears everything (AC button)
     */
    fun clear() {
        currentValue = 0.0
        operand = 0.0
        currentOperation = null
        isNewNumber = true
        hasDecimal = false
        expression = ""
    }

    /**
     * Gets current state for debugging
     */
    fun getState(): String {
        return "Current: $currentValue, Operand: $operand, Op: $currentOperation, New: $isNewNumber"
    }
}
