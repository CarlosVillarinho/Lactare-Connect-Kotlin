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
import br.com.carlosvillarinho.lactareconnect.controller.BancoDeLeiteController
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.view.components.CartaoContato
import br.com.carlosvillarinho.lactareconnect.view.components.LactareTopBar

/**
 * Contatos dos bancos de leite. Le a mesma lista da tela de bancos, entao os
 * telefones e e-mails nunca ficam fora de sincronia entre as duas telas.
 */
@Composable
fun ContatosScreen(
    bancoController: BancoDeLeiteController,
    onVoltar: () -> Unit,
    onMensagem: (String) -> Unit
) {
    val bancos = bancoController.listar()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
    ) {
        LactareTopBar(titulo = "Contatos", onVoltar = onVoltar)

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(bancos, key = { it.id }) { banco ->
                CartaoContato(
                    banco = banco,
                    onClick = { onMensagem("Ligando para ${banco.nome}: ${banco.telefone}") }
                )
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContatosScreenPreview() {
    val bancoControllerPreview = BancoDeLeiteController()

    ContatosScreen(
        bancoController = bancoControllerPreview,
        onVoltar = {},
        onMensagem = {}
    )
}