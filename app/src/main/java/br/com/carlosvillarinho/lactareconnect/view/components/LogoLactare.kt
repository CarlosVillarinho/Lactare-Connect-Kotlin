package br.com.carlosvillarinho.lactareconnect.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.carlosvillarinho.lactareconnect.R
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare

/**
 * Marca do aplicativo: a gota com o coracao ao lado do nome em duas linhas.
 * O tamanho do icone e do texto sao parametros porque a marca aparece bem
 * grande na abertura e bem pequena no cabecalho das telas internas.
 */
@Composable
fun LogoLactare(
    modifier: Modifier = Modifier,
    tamanhoIcone: Dp = 28.dp,
    tamanhoTexto: TextUnit = 13.sp,
    espacamento: Dp = 8.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(espacamento)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo_lactare),
            contentDescription = stringResource(R.string.descricao_logo),
            modifier = Modifier.size(tamanhoIcone)
        )
        Text(
            text = "Lactare\nConnect",
            style = MaterialTheme.typography.titleMedium,
            color = AzulLactare,
            fontSize = tamanhoTexto,
            lineHeight = tamanhoTexto * 1.15f,
            fontWeight = FontWeight.Bold
        )
    }
}