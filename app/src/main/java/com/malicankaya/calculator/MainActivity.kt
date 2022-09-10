package com.malicankaya.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        val btn = view as Button
        tvInput?.append(btn.text)

        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot && !(tvInput?.text.toString()).contains(".")) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var prefix = ""
            var tvValue = tvInput?.text.toString()

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = prefix + splitValue[0]
                    var two = splitValue[1]

                    var result = one.toDouble() - two.toDouble()

                    tvInput?.text = removeZero(result.toString())
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = prefix + splitValue[0]
                    var two = splitValue[1]

                    var result = one.toDouble() + two.toDouble()

                    tvInput?.text = removeZero(result.toString())
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = prefix + splitValue[0]
                    var two = splitValue[1]

                    var result = one.toDouble() * two.toDouble()

                    tvInput?.text = removeZero(result.toString())
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = prefix + splitValue[0]
                    var two = splitValue[1]

                    var result = one.toDouble() / two.toDouble()

                    tvInput?.text = removeZero(result.toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }

        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("+")
                    || value.contains("*")
                    || value.contains("/")
                    || value.contains("-")
        }
    }

    private fun removeZero(result: String): String {
        var value = result
        val length = result.length
        if (value.substring(length - 2, length) == ".0")
            value = value.substring(0, length - 2)
        else {
            if (value.indexOf(".") < length - 5)
                value = value.substring(0, value.indexOf(".") + 5)
        }

        return value
    }
}