package com.example.istea_primerparcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.istea_primerparcial.ui.theme.ISTEAPrimerParcialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ISTEAPrimerParcialTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "retiro") {
                    composable("retiro"){
                        RetiroPage(
                            navController = navController,
                            sueldo = Sueldo(100000.00)
                        )
                    }
                    composable("comprobante/{monto}") { backStack ->
                        val monto = backStack.arguments?.getString("monto") ?: "0"
                        ComprobantePage(monto)
                    }
                }
            }
        }
    }
}