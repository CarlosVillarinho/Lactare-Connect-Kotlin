package br.com.carlosvillarinho.lactareconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import br.com.carlosvillarinho.lactareconnect.navigation.NavGraph
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.ui.theme.LactareConnectTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LactareConnectTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BegeFundo),
                    color = BegeFundo
                ) {
                    NavGraph()
                }
            }
        }
    }
}