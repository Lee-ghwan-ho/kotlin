package com.example.calculator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import java.util.Stack

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val showresult = binding.showresult
        val showinput = binding.showinput

        binding.btnzero.setOnClickListener{binding.showinput.append("0")}
        binding.btnone.setOnClickListener { binding.showinput.append("1") }
        binding.btntwo.setOnClickListener { binding.showinput.append("2")}
        binding.btnthree.setOnClickListener { binding.showinput.append("3")}
        binding.btnfour.setOnClickListener { binding.showinput.append("4")}
        binding.btnfive.setOnClickListener { binding.showinput.append("5")}
        binding.btnsix.setOnClickListener { binding.showinput.append("6")}
        binding.btnseven.setOnClickListener { binding.showinput.append("7")}
        binding.btneight.setOnClickListener { binding.showinput.append("8")}
        binding.btnnine.setOnClickListener { binding.showinput.append("9")}

        binding.btndivision1.setOnClickListener { binding.showinput.append("%") }
        binding.btndivision2.setOnClickListener { binding.showinput.append("/") }
        binding.btnplus.setOnClickListener { binding.showinput.append("+") }
        binding.btnsubtract.setOnClickListener { binding.showinput.append("-") }
        binding.btndot.setOnClickListener { binding.showinput.append(".") }
        binding.btnAC.setOnClickListener{ binding.showinput.text = ""}
        binding.btndelete.setOnClickListener {
            val text = binding.showinput.text.toString()
            if (text.isNotEmpty()) {
                binding.showinput.text = text.substring(0, text.length - 1)
            }
        }
        binding.btnenter.setOnClickListener {
            val expression = binding.showinput.text.toString()
            val result = evaluateExpression(expression)
            binding.showresult.text = result.toString()
        }

    }

    fun evaluateExpression(expression: String): Double {
        // Split the expression on spaces for tokenization
        val tokens = expression.split(" ")
        val stack = Stack<Double>()

        for (token in tokens) {
            when {
                token.toDoubleOrNull() != null -> stack.push(token.toDouble())
                stack.size >= 1 -> {
                    val b = stack.pop() // second operand
                    val a = stack.pop() // first operand
                    when (token.trim()) { // Trim the token to avoid spaces causing issues
                        "+" -> stack.push(a + b)
                        "-" -> stack.push(a - b)
                        "*" -> stack.push(a * b)
                        "/" -> stack.push(if (b == 0.0) Double.NaN else a / b)
                        else -> throw IllegalStateException("Unexpected token: $token")
                    }
                }
            }
        }
        return if (stack.isEmpty()) 0.0 else stack.pop()
    }


}


package com.example.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import java.util.Stack

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Appending digits and operators to the input
        binding.btnzero.setOnClickListener { appendValue(binding, "0") }
        binding.btnone.setOnClickListener { appendValue(binding, "1") }
        binding.btntwo.setOnClickListener { appendValue(binding, "2") }
        binding.btnthree.setOnClickListener { appendValue(binding, "3") }
        binding.btnfour.setOnClickListener { appendValue(binding, "4") }
        binding.btnfive.setOnClickListener { appendValue(binding, "5") }
        binding.btnsix.setOnClickListener { appendValue(binding, "6") }
        binding.btnseven.setOnClickListener { appendValue(binding, "7") }
        binding.btneight.setOnClickListener { appendValue(binding, "8") }
        binding.btnnine.setOnClickListener { appendValue(binding, "9") }
        binding.btndot.setOnClickListener { appendValue(binding, ".") }

        // Handling operations
        binding.btnplus.setOnClickListener { appendValue(binding, " + ") }
        binding.btnsubtract.setOnClickListener { appendValue(binding, " - ") }
        binding.btnmultiple.setOnClickListener { appendValue(binding, " * ") }
        binding.btndivision2.setOnClickListener { appendValue(binding, " / ") }

        // Clear input and results
        binding.btnAC.setOnClickListener {
            binding.showinput.text = ""
            binding.showresult.text = ""
        }

        // Delete the last character
        binding.btndelete.setOnClickListener {
            val text = binding.showinput.text.toString()
            if (text.isNotEmpty()) {
                binding.showinput.text = text.substring(0, text.length - 1)
            }
        }

        // Evaluate the expression
        binding.btnenter.setOnClickListener {
            val expression = binding.showinput.text.toString()
            val result = evaluateExpression(expression)
            binding.showresult.text = result.toString()
        }
    }
    private fun appendValue(binding: ActivityMainBinding, value: String) {
        binding.showinput.append(value)
    }
    private fun evaluateExpression(expression: String): Double {
        val values = Stack<Double>() // Stack for numbers
        val ops = Stack<String>()   // Stack for operators
        val tokens = tokenize(expression) // Tokenize the expression based on regex to handle numbers and operators

        tokens.forEach { token ->
            when {
                token.toDoubleOrNull() != null -> {
                    // Push the number directly onto the values stack
                    values.push(token.toDouble())
                }
                token in listOf("+", "-", "*", "/") -> {
                    // Process all operators in the ops stack that have higher or the same precedence
                    while (ops.isNotEmpty() && hasPrecedence(token, ops.peek())) {
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()))
                    }
                    // Push the current operator onto the ops stack
                    ops.push(token)
                }
            }
        }
        // Entire expression has been parsed at this point, apply remaining ops to remaining values
        while (ops.isNotEmpty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()))
        }

        // Top of the values stack contains result, assuming well-formed input
        return values.pop()
    }
    private fun hasPrecedence(op1: String, op2: String): Boolean {
        if (op2 in listOf("(", ")")) {
            return false
        }
        return !((op1 == "*" || op1 == "/") && (op2 == "+" || op2 == "-"))
    }
    private fun applyOp(op: String, b: Double, a: Double): Double {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> if (b == 0.0) Double.NaN else a / b
            else -> throw UnsupportedOperationException("Unsupported operation $op")
        }
    }
    private fun tokenize(expression: String): List<String> {
        // Regex to split numbers and operators
        return Regex("(-?\\d*\\.?\\d+|[+\\-*/])").findAll(expression).map { it.value }.toList()
    }
}
