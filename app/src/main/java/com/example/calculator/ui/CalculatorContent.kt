package com.example.calculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun CalculatorContent() {

    val viewModel = koinViewModel<CalculatorViewModel>()

    val expression by viewModel.expression.collectAsState()
    val result by viewModel.result.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(
            text = "Result: $result",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge
        )
        TextField(
            value = expression,
            onValueChange = viewModel::onExpressionChanged,
        )
        Button(onClick = viewModel::onEvaluate) {
            Text(text = "Evaluate")
        }
    }
}