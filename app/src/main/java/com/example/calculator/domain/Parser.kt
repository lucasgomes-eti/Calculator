package com.example.calculator.domain

import com.example.calculator.domain.model.Div
import com.example.calculator.domain.model.Expression
import com.example.calculator.domain.model.Minus
import com.example.calculator.domain.model.Mult
import com.example.calculator.domain.model.Number
import com.example.calculator.domain.model.Sum
import com.example.calculator.domain.model.Token
import com.example.calculator.domain.model.TokenType

class Parser(private val tokens: List<Token>) {
    private var position = 0

    private fun currentToken(): Token? = tokens.getOrNull(position)
    private fun advance() {
        position++
    }

    private fun parsePrimary(): Expression {
        val token = currentToken()
        return when (token?.type) {
            TokenType.NUMBER -> {
                advance()
                Number(token.value.toDouble())
            }

            TokenType.LPAREN -> {
                advance()
                val expression = parseExpression()
                if (currentToken()?.type == TokenType.RPAREN) {
                    advance()
                } else {
                    throw IllegalArgumentException("Missing closing parenthesis")
                }
                expression
            }

            else -> throw IllegalArgumentException("Unexpected token: ${token?.value}")
        }
    }

    private fun parseExpression(): Expression {
        var left = parsePrimary()
        while (currentToken()?.type == TokenType.PLUS) {
            advance()
            val right = parsePrimary()
            left = Sum(left, right)
        }
        while (currentToken()?.type == TokenType.MINUS) {
            advance()
            val right = parsePrimary()
            left = Minus(left, right)
        }
        while (currentToken()?.type == TokenType.MULT) {
            advance()
            val right = parsePrimary()
            left = Mult(left, right)
        }
        while (currentToken()?.type == TokenType.DIV) {
            advance()
            val right = parsePrimary()
            left = Div(left, right)
        }
        return left
    }

    fun parse(): Expression {
        return parseExpression()
    }
}
