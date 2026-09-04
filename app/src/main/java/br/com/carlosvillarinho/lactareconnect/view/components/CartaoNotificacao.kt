package br.com.carlosvillarinho.lactareconnect.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.model.Notificacao
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeCampo
import br.com.carlosvillarinho.lactareconnect.ui.theme.ContornoSuave
import br.com.carlosvillarinho.lactareconnect.ui.theme.FormasLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoSecundario

/**
 * Cartao de notificacao: categoria e tempo relativo no topo, mensagem em
 * destaque abaixo.
 *
 * Quando [modoSelecao] esta ativo o cartao ganha um circulo de marcacao a
 * esquerda e o contorno muda de cor, indicando que esta selecionado.
 */
@Composable
fun CartaoNotificacao(
    notificacao: Notificacao,
    modoSelecao: Boolean,
    selecionada: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 3.dp, shape = FormasLactare.cartao, clip = false)
            .background(BegeCampo, FormasLactare.cartao)
            .border(
                width = if (selecionada) 2.dp else 1.dp,
                color = if (selecionada) AzulLactare else ContornoSuave,
                shape = FormasLactare.cartao
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (modoSelecao) {
            Icon(
                imageVector = if (selecionada) {
                    Icons.Filled.CheckCircle
                } else {
                    Icons.Outlined.Circle
                },
                contentDescription = if (selecionada) "Selecionada" else "Não selecionada",
                tint = AzulLactare,
                modifier = Modifier.size(24.dp)
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = notificacao.categoria.rotulo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoSecundario
                )
                Text(
                    text = notificacao.tempoRelativo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoSecundario
                )
            }
            Text(
                text = notificacao.mensagem,
                style = MaterialTheme.typography.titleMedium,
                color = TextoPrincipal,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}