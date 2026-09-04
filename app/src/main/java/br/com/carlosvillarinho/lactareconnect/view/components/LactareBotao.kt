package br.com.carlosvillarinho.lactareconnect.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.ui.theme.BrancoTexto
import br.com.carlosvillarinho.lactareconnect.ui.theme.FormasLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.PretoAcao

/**
 * Botao principal do prototipo: retangulo preto arredondado, com sombra
 * marcada e texto branco. Aceita um icone opcional a direita, como o "Proximo"
 * e o "Concluido" do fluxo de agendamento.
 */
@Composable
fun LactareBotao(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icone: ImageVector? = null,
    habilitado: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .shadow(
                elevation = if (habilitado) 8.dp else 0.dp,
                shape = FormasLactare.botao,
                clip = false
            )
            .background(
                color = if (habilitado) PretoAcao else PretoAcao.copy(alpha = 0.4f),
                shape = FormasLactare.botao
            )
            .clickable(enabled = habilitado, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = texto,
                style = MaterialTheme.typography.labelLarge,
                color = BrancoTexto
            )
            if (icone != null) {
                Icon(
                    imageVector = icone,
                    contentDescription = null,
                    tint = BrancoTexto,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}