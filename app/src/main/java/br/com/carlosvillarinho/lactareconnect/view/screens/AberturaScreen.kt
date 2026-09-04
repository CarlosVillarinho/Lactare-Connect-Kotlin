package br.com.carlosvillarinho.lactareconnect.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.R
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeSuperficie
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoSecundario
import br.com.carlosvillarinho.lactareconnect.ui.theme.VerdeLactare
import kotlinx.coroutines.delay

/**
 * Tela de abertura. Mostra a marca por alguns segundos e segue para o login;
 * um toque em qualquer ponto adianta a transicao.
 *
 * O prototipo traz uma foto de mae com bebe na metade inferior. Como a imagem
 * nao acompanha esta entrega, o espaco recebe um painel decorativo com a
 * mesma proporcao. Para usar a foto definitiva, basta colocar o arquivo em
 * res/drawable com o nome splash_mae_bebe e trocar o Box abaixo por:
 *
 *     Image(
 *         painter = painterResource(R.drawable.splash_mae_bebe),
 *         contentDescription = null,
 *         contentScale = ContentScale.Crop,
 *         modifier = Modifier.fillMaxWidth().weight(1f)
 *     )
 */
@Composable
fun AberturaScreen(onAvancar: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2600)
        onAvancar()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
            .clickable(onClick = onAvancar)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo_lactare),
                contentDescription = stringResource(R.string.descricao_logo),
                modifier = Modifier.size(84.dp)
            )
            Text(
                text = "Lactare\nConnect",
                style = MaterialTheme.typography.displayLarge,
                color = AzulLactare
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 18.dp)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(BegeSuperficie, VerdeLactare.copy(alpha = 0.28f), AzulLactare.copy(alpha = 0.18f))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Doar leite materno é\ncuidar de duas vidas.",
                style = MaterialTheme.typography.headlineMedium,
                color = AzulLactare,
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = "Toque para continuar",
            style = MaterialTheme.typography.labelMedium,
            color = TextoSecundario,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AberturaScreenPreview() {
    AberturaScreen(
        onAvancar = {}
    )
}