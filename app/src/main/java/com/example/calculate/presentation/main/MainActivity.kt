package com.example.calculate.presentation.main

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculate.R
import com.example.calculate.presentation.calculator.Calc1Fragment
import com.example.calculate.presentation.calculator.Calc2Fragment

/**
 * Main Activity that hosts calculator fragments
 * Manages switching between two calculator themes
 *
 * Architecture: MVVM
 * - MainActivity: View layer (handles UI and navigation)
 * - Fragments: View layer (display calculator UI)
 * - ViewModel: Manages state and business logic
 * - Domain: Pure business logic (CalculatorEngine)
 */
class MainActivity : AppCompatActivity() {

    private companion object {
        const val TAG_CALC1 = "calc1"
        const val TAG_CALC2 = "calc2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFragmentSwitching()

        // Show first calculator on initial load
        if (savedInstanceState == null) {
            showCalculator(TAG_CALC1)
        }
    }

    private fun setupFragmentSwitching() {
        findViewById<Button>(R.id.button).setOnClickListener {
            showCalculator(TAG_CALC1)
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            showCalculator(TAG_CALC2)
        }
    }

    /**
     * Shows the specified calculator fragment
     * Uses fragment tags to properly manage fragment lifecycle
     */
    private fun showCalculator(tag: String) {
        val fragment = when (tag) {
            TAG_CALC1 -> {
                supportFragmentManager.findFragmentByTag(TAG_CALC1) ?: Calc1Fragment()
            }
            TAG_CALC2 -> {
                supportFragmentManager.findFragmentByTag(TAG_CALC2) ?: Calc2Fragment()
            }
            else -> return
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.FL, fragment, tag)
            .commit()
    }
}
