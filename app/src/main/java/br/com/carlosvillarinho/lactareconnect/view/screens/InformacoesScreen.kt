package br.com.carlosvillarinho.lactareconnect.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.controller.InformacaoController
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal
import br.com.carlosvillarinho.lactareconnect.view.components.LactarePainel
import br.com.carlosvillarinho.lactareconnect.view.components.LactareTopBar

/** Tela "Sobre | Informacoes", com o texto institucional do banco de leite. */
@Composable
fun InformacoesScreen(
    informacaoController: InformacaoController,
    onVoltar: () -> Unit
) {
    val conteudo = informacaoController.obterConteudo()
    val rolagem = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
    ) {
        LactareTopBar(titulo = "Sobre | Informações", onVoltar = onVoltar)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rolagem)
                .padding(horizontal = 18.dp, vertical = 8.dp)
        ) {
            LactarePainel {
                Text(
                    text = conteudo.apresentacao,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextoPrincipal
                )
                Text(
                    text = conteudo.chamadaBeneficios,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextoPrincipal,
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    conteudo.beneficios.forEach { beneficio ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "•",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextoPrincipal,
                                modifier = Modifier.padding(end = 10.dp)
                            )
                            Text(
                                text = beneficio,
                                style = MaterialTheme.typography.titleMedium,
                                color = TextoPrincipal
                            )
                        }
                    }
                }
                Text(
                    text = conteudo.fechamento,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextoPrincipal,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
            Text(text = "", modifier = Modifier.padding(bottom = 32.dp))
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun InformacoesScreenPreview() {
    val controllerPreview = InformacaoController()

    InformacoesScreen(
        informacaoController = controllerPreview,
        onVoltar = {}
    )
}