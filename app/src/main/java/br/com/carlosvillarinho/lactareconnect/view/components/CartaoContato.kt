package br.com.carlosvillarinho.lactareconnect.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.model.BancoDeLeite
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeCampo
import br.com.carlosvillarinho.lactareconnect.ui.theme.FormasLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.PretoAcao
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal

/**
 * Cartao da tela de Contatos: telefone, nome do banco e as duas formas de
 * contato. Reaproveita o mesmo modelo da listagem de bancos.
 */
@Composable
fun CartaoContato(
    banco: BancoDeLeite,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 3.dp, shape = FormasLactare.cartao, clip = false)
            .background(BegeCampo, FormasLactare.cartao)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Phone,
            contentDescription = null,
            tint = PretoAcao,
            modifier = Modifier.size(34.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = banco.nome,
                style = MaterialTheme.typography.titleMedium,
                color = AzulLactare
            )
            Text(
                text = banco.telefone,
                style = MaterialTheme.typography.bodyMedium,
                color = TextoPrincipal
            )
            Text(
                text = banco.email,
                style = MaterialTheme.typography.bodyMedium,
                color = TextoPrincipal
            )
        }
    }
}