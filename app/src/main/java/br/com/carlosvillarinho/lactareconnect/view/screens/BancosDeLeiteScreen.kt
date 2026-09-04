package br.com.carlosvillarinho.lactareconnect.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.controller.BancoDeLeiteController
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoSecundario
import br.com.carlosvillarinho.lactareconnect.view.components.CartaoBancoDeLeite
import br.com.carlosvillarinho.lactareconnect.view.components.LactareTopBar

/**
 * Listagem dos bancos de leite. Tocar em um cartao abre o detalhe daquele
 * banco, passando o id pela rota.
 */
@Composable
fun BancosDeLeiteScreen(
    bancoController: BancoDeLeiteController,
    onAbrirDetalhe: (Int) -> Unit,
    onVoltar: () -> Unit
) {
    val bancos = bancoController.listar()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
    ) {
        LactareTopBar(titulo = "Bancos de Leite", onVoltar = onVoltar)

        Text(
            text = "${bancos.size} unidades ordenadas pela distância até você.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextoSecundario,
            modifier = Modifier.padding(start = 20.dp, bottom = 12.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(bancos, key = { it.id }) { banco ->
                CartaoBancoDeLeite(
                    banco = banco,
                    onClick = { onAbrirDetalhe(banco.id) }
                )
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun BancosDeLeiteScreenPreview() {
    val controllerPreview = BancoDeLeiteController()

    BancosDeLeiteScreen(
        bancoController = controllerPreview,
        onAbrirDetalhe = {},
        onVoltar = {}
    )
}