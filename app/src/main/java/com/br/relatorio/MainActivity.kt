package com.br.relatorio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.br.relatorio.Telas.TelaPrincipal
import com.br.relatorio.ui.theme.RelatorioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RelatorioTheme {
                TelaPrincipal()
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RelatorioTheme {
        TelaPrincipal()
    }
}