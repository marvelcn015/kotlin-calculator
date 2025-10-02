package com.example.calculate.presentation.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.calculate.R
import com.example.calculate.domain.CalculatorEngine

/**
 * Base class for calculator fragments using MVVM architecture
 *
 * MVVM Benefits:
 * - ViewModel survives configuration changes (e.g., screen rotation)
 * - Clear separation between UI (Fragment) and business logic (ViewModel)
 * - Easier to test business logic independently
 * - LiveData provides automatic UI updates
 */
abstract class BaseCalculatorFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    // ViewModel - shared between configuration changes
    protected val viewModel: CalculatorViewModel by viewModels()

    protected lateinit var tvDisplay: TextView
    protected lateinit var tvExpression: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvDisplay = view.findViewById(R.id.tvDisplay)
        tvExpression = view.findViewById(R.id.tvExpression)

        setupButtons(view)
        observeViewModel()
    }

    /**
     * Observe ViewModel state changes and update UI accordingly
     */
    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            tvDisplay.text = state.displayValue
            tvExpression.text = state.expression

            // Show error message if present
            state.errorMessage?.let { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupButtons(view: View) {
        // Number buttons (0-9)
        val numberButtons = listOf(
            R.id.btn0 to 0, R.id.btn1 to 1, R.id.btn2 to 2,
            R.id.btn3 to 3, R.id.btn4 to 4, R.id.btn5 to 5,
            R.id.btn6 to 6, R.id.btn7 to 7, R.id.btn8 to 8,
            R.id.btn9 to 9
        )

        numberButtons.forEach { (id, number) ->
            view.findViewById<Button>(id).setOnClickListener {
                viewModel.onNumberClick(number)
            }
        }

        // Decimal button
        view.findViewById<Button>(R.id.btnDot).setOnClickListener {
            viewModel.onDecimalClick()
        }

        // Operation buttons
        view.findViewById<Button>(R.id.btnAdd).setOnClickListener {
            viewModel.onOperationClick(CalculatorEngine.Operation.ADD)
        }

        view.findViewById<Button>(R.id.btnSubtract).setOnClickListener {
            viewModel.onOperationClick(CalculatorEngine.Operation.SUBTRACT)
        }

        view.findViewById<Button>(R.id.btnMultiply).setOnClickListener {
            viewModel.onOperationClick(CalculatorEngine.Operation.MULTIPLY)
        }

        view.findViewById<Button>(R.id.btnDivide).setOnClickListener {
            viewModel.onOperationClick(CalculatorEngine.Operation.DIVIDE)
        }

        // Equals button
        view.findViewById<Button>(R.id.btnEquals).setOnClickListener {
            viewModel.onEqualsClick()
        }

        // Clear buttons
        view.findViewById<Button>(R.id.btnClear).setOnClickListener {
            viewModel.onClearEntryClick()
        }

        view.findViewById<Button>(R.id.btnAllClear).setOnClickListener {
            viewModel.onAllClearClick()
        }
    }
}
