package br.com.carlosvillarinho.lactareconnect.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare

/**
 * Cabecalho padrao das telas internas: seta de voltar seguida do titulo em
 * azul. Aparece em todas as telas do prototipo que nao sao a inicial.
 */
@Composable
fun LactareTopBar(
    titulo: String,
    onVoltar: () -> Unit,
    modifier: Modifier = Modifier,
    acoes: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onVoltar) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = AzulLactare,
                modifier = Modifier.size(26.dp)
            )
        }
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleLarge,
            color = AzulLactare,
            modifier = Modifier.padding(start = 2.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            acoes()
        }
    }
}