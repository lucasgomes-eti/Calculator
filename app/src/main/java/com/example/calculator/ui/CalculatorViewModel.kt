package com.example.calculator.ui

import androidx.lifecycle.ViewModel
import com.example.calculator.domain.Parser
import com.example.calculator.domain.model.Div
import com.example.calculator.domain.model.Expression
import com.example.calculator.domain.model.Minus
import com.example.calculator.domain.model.Mult
import com.example.calculator.domain.model.Number
import com.example.calculator.domain.model.Sum
import com.example.calculator.domain.model.Token
import com.example.calculator.domain.model.TokenType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel : ViewModel() {

    private val _expression = MutableStateFlow("")
    val expression = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result = _result.asStateFlow()

    fun onExpressionChanged(value: String) {
        _expression.update { value }
    }

    fun onEvaluate() {
        val result = try {
            val tokens = tokenize(_expression.value)
            val parser = Parser(tokens)
            val expression = parser.parse()
            eval(expression).toString()
        } catch (e: Exception) {
            e.message.toString()
        }
        _result.update { result }
    }

    private fun eval(expression: Expression): Double = when (expression) {
        is Number -> expression.value
        is Sum -> eval(expression.left) + eval(expression.right)
        is Minus -> eval(expression.left) - eval(expression.right)
        is Mult -> eval(expression.left) * eval(expression.right)
        is Div -> eval(expression.left) / eval(expression.right)
        else -> throw IllegalArgumentException("Unknown expression")
    }

    private fun tokenize(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        val regex = "\\s*(\\d+(\\.\\d+)?|[+\\-*/()]|\\s*)".toRegex()
        regex.findAll(input).forEach { matchResult ->
            val token = when (val match = matchResult.value.trim()) {
                "+" -> Token(TokenType.PLUS, match)
                "-" -> Token(TokenType.MINUS, match)
                "*" -> Token(TokenType.MULT, match)
                "/" -> Token(TokenType.DIV, match)
                "(" -> Token(TokenType.LPAREN, match)
                ")" -> Token(TokenType.RPAREN, match)
                else -> Token(TokenType.NUMBER, match)
            }
            tokens.add(token)
        }
        return tokens
    }
}