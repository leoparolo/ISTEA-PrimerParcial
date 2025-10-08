package com.example.istea_primerparcial

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetiroPage(
    navController: NavController,
    sueldo: Sueldo
) {
    var retiroText by remember { mutableStateOf("") }
    val retiro = Parse(retiroText)

    val pattern = currencyPattern()
    val amountFmt = argentineCurrencyFormatter()
    var error by remember { mutableStateOf<String?>(null) }

    fun validar(): Boolean {
        error = when {
            retiroText.isBlank() -> "Ingresá un monto."
            retiro == null -> "Formato inválido. Usá números (ej: 1250 o 1250,50)."
            retiro <= 0.0 -> "El monto debe ser mayor a cero."
            retiro > sueldo.total -> "El monto supera el saldo disponible."
            else -> null
        }
        return error == null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Billetera Virtual - Retiro") }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Saldo disponible",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = amountFmt.format(sueldo.total),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = retiroText,
                onValueChange = { nuevo ->
                    if (nuevo.isEmpty() || pattern.matches(nuevo)) {
                        retiroText = nuevo
                    }
                },
                label = { Text("Monto a retirar") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                )
            )

            if (error != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (validar()) {
                        val encoded = Uri.encode(retiroText)
                        navController.navigate("comprobante/$encoded")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text("Retirar")
            }
        }
    }
}
