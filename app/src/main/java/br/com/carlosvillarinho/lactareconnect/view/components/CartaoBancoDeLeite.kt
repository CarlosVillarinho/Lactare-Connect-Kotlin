package br.com.carlosvillarinho.lactareconnect.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
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
import br.com.carlosvillarinho.lactareconnect.ui.theme.BrancoTexto
import br.com.carlosvillarinho.lactareconnect.ui.theme.FormasLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.PretoAcao
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal

/**
 * Cartao de banco de leite da listagem: cruz medica em circulo azul, nome do
 * banco, zona da cidade e a seta que leva ao detalhe.
 */
@Composable
fun CartaoBancoDeLeite(
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
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .background(AzulLactare, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = BrancoTexto,
                modifier = Modifier.size(30.dp)
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = banco.nome,
                style = MaterialTheme.typography.titleMedium,
                color = AzulLactare
            )
            Text(
                text = banco.zona,
                style = MaterialTheme.typography.bodyMedium,
                color = TextoPrincipal
            )
            Text(
                text = banco.distanciaFormatada,
                style = MaterialTheme.typography.labelMedium,
                color = AzulLactare
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = PretoAcao,
            modifier = Modifier.size(26.dp)
        )
    }
}