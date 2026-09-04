package br.com.carlosvillarinho.lactareconnect.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.model.Campanha
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeCampo
import br.com.carlosvillarinho.lactareconnect.ui.theme.FormasLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.PretoAcao
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoSecundario

/**
 * Cartao de campanha: bandeira e titulo no topo, texto de conscientizacao
 * abaixo e o rodape com periodo e banco responsavel.
 */
@Composable
fun CartaoCampanha(
    campanha: Campanha,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 3.dp, shape = FormasLactare.cartao, clip = false)
            .background(BegeCampo, FormasLactare.cartao)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Flag,
                contentDescription = null,
                tint = PretoAcao,
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = campanha.titulo,
                style = MaterialTheme.typography.titleLarge,
                color = AzulLactare
            )
        }
        Text(
            text = campanha.texto,
            style = MaterialTheme.typography.bodyMedium,
            color = TextoPrincipal
        )
        Text(
            text = "${campanha.periodo} · ${campanha.bancoResponsavel}",
            style = MaterialTheme.typography.labelMedium,
            color = TextoSecundario
        )
    }
}