package br.com.carlosvillarinho.lactareconnect.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.controller.CampanhaController
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.view.components.CartaoCampanha
import br.com.carlosvillarinho.lactareconnect.view.components.LactareTopBar

/** Campanhas de conscientizacao sobre a doacao de leite materno. */
@Composable
fun CampanhasScreen(
    campanhaController: CampanhaController,
    onVoltar: () -> Unit
) {
    val campanhas = campanhaController.listar()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
    ) {
        LactareTopBar(titulo = "Campanhas", onVoltar = onVoltar)

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            items(campanhas, key = { it.id }) { campanha ->
                CartaoCampanha(campanha = campanha)
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun CampanhasScreenPreview() {
    val campanhaControllerPreview = CampanhaController()

    CampanhasScreen(
        campanhaController = campanhaControllerPreview,
        onVoltar = {}
    )
}