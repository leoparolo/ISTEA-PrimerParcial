package com.example.istea_primerparcial

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.text.NumberFormat
import java.util.Locale

@Composable
fun argentineCurrencyFormatter(): NumberFormat {
    return remember {
        NumberFormat.getNumberInstance(Locale("es", "AR")).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
            isGroupingUsed = true
        }
    }
}

fun Parse(texto: String): Double? =
    texto.replace(',', '.').toDoubleOrNull()

@Composable
fun currencyPattern(): Regex = remember {
    Regex("^\\d{0,9}(?:[\\.,]\\d{0,2})?$") // 9 enteros y hasta 2 decimales
}