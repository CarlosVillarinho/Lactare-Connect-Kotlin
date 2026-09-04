package br.com.carlosvillarinho.lactareconnect.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.controller.BancoDeLeiteController
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeSuperficie
import br.com.carlosvillarinho.lactareconnect.ui.theme.BrancoTexto
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoSecundario
import br.com.carlosvillarinho.lactareconnect.view.components.LactareBotao
import br.com.carlosvillarinho.lactareconnect.view.components.MapaEstilizado

/**
 * Detalhe do banco de leite escolhido na listagem.
 *
 * Recebe apenas o [bancoId] pela rota e pede o objeto completo ao controller,
 * mantendo a navegacao leve. O botao ao final abre o fluxo de agendamento
 * ja vinculado a este banco.
 */
@Composable
fun DetalheDoBancoScreen(
    bancoId: Int,
    bancoController: BancoDeLeiteController,
    onAgendarDoacao: (Int) -> Unit,
    onVoltar: () -> Unit
) {
    val banco = bancoController.buscarPorId(bancoId)
    val rolagem = rememberScrollState()

    if (banco == null) {
        BancoNaoEncontrado(onVoltar)
        return
    }

    val marcadores = buildList {
        add(banco.posicaoXNoMapa to banco.posicaoYNoMapa)
        bancoController.vizinhosDe(bancoId).forEach {
            add(it.posicaoXNoMapa to it.posicaoYNoMapa)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
            .verticalScroll(rolagem)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AzulLactare)
                .padding(horizontal = 8.dp, vertical = 14.dp)
        ) {
            IconButton(
                onClick = onVoltar,
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = BrancoTexto
                )
            }
            Text(
                text = "Encontre o banco de leite\nmais próximo de você",
                style = MaterialTheme.typography.titleLarge,
                color = BrancoTexto,
                modifier = Modifier.padding(start = 52.dp, end = 12.dp)
            )
        }

        MapaEstilizado(
            marcadores = marcadores,
            indiceDestacado = 0,
            altura = 320.dp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(BegeSuperficie)
                .padding(horizontal = 22.dp, vertical = 22.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = banco.nome,
                style = MaterialTheme.typography.headlineMedium,
                color = AzulLactare
            )
            Text(
                text = banco.endereco,
                style = MaterialTheme.typography.bodyLarge,
                color = TextoPrincipal,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = banco.complemento,
                style = MaterialTheme.typography.bodyLarge,
                color = TextoPrincipal
            )
            Text(
                text = banco.distanciaFormatada,
                style = MaterialTheme.typography.titleMedium,
                color = AzulLactare,
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                text = banco.horarioAtendimento,
                style = MaterialTheme.typography.bodyMedium,
                color = TextoSecundario
            )
            Text(
                text = banco.unidadeParceira,
                style = MaterialTheme.typography.headlineMedium,
                color = AzulLactare,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(
                text = "Unidade parceira para entrega e coleta.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextoSecundario
            )

            LactareBotao(
                texto = "Agendar doação",
                icone = Icons.AutoMirrored.Filled.ArrowForward,
                onClick = { onAgendarDoacao(banco.id) },
                modifier = Modifier.padding(top = 26.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun BancoNaoEncontrado(onVoltar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Não encontramos esse banco de leite.",
            style = MaterialTheme.typography.titleLarge,
            color = AzulLactare
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Volte à listagem e escolha outra unidade.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextoSecundario
        )
        Spacer(modifier = Modifier.height(20.dp))
        LactareBotao(texto = "Voltar para a listagem", onClick = onVoltar)
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetalheDoBancoScreenPreview() {
    val bancoControllerPreview = BancoDeLeiteController()

    DetalheDoBancoScreen(
        bancoId = 1, // Passamos um ID válido de exemplo
        bancoController = bancoControllerPreview,
        onAgendarDoacao = {},
        onVoltar = {}
    )
}