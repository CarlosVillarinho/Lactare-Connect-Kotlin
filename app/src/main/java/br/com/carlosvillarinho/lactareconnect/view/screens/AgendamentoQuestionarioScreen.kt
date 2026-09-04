package br.com.carlosvillarinho.lactareconnect.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.controller.AgendamentoController
import br.com.carlosvillarinho.lactareconnect.controller.BancoDeLeiteController
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoSecundario
import br.com.carlosvillarinho.lactareconnect.ui.theme.VermelhoDestrutivo
import br.com.carlosvillarinho.lactareconnect.view.components.LactareBotao
import br.com.carlosvillarinho.lactareconnect.view.components.LactareCampo
import br.com.carlosvillarinho.lactareconnect.view.components.LactarePainel
import br.com.carlosvillarinho.lactareconnect.view.components.LactareTopBar

/**
 * Primeira etapa do agendamento: o questionario de elegibilidade.
 *
 * As respostas ficam no rascunho do [AgendamentoController], entao continuam
 * preenchidas se a doadora voltar da etapa seguinte para revisar algo.
 */
@Composable
fun AgendamentoQuestionarioScreen(
    bancoId: Int,
    agendamentoController: AgendamentoController,
    bancoController: BancoDeLeiteController,
    onProximaEtapa: (Int) -> Unit,
    onVoltar: () -> Unit
) {
    val banco = bancoController.buscarPorId(bancoId)
    val rascunho = agendamentoController.rascunho
    val rolagem = rememberScrollState()

    // Zera o rascunho ao entrar em um agendamento de outro banco.
    LaunchedEffect(bancoId) {
        if (rascunho.bancoId != bancoId) {
            agendamentoController.iniciarAgendamento(bancoId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
            .verticalScroll(rolagem)
    ) {
        LactareTopBar(titulo = "Voltar", onVoltar = onVoltar)

        Text(
            text = "Agendamento para doação",
            style = MaterialTheme.typography.headlineLarge,
            color = AzulLactare,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 10.dp)
        )

        banco?.let {
            Text(
                text = it.nome,
                style = MaterialTheme.typography.bodyMedium,
                color = TextoSecundario,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        Text(
            text = "Questionário de elegibilidade",
            style = MaterialTheme.typography.titleMedium,
            color = TextoPrincipal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )

        LactarePainel(modifier = Modifier.padding(horizontal = 18.dp)) {
            LactareCampo(
                rotulo = "Quantidade de leite para doação",
                valor = rascunho.quantidadeDeLeite,
                onValorChange = { agendamentoController.atualizarQuestionario(quantidadeDeLeite = it) },
                dica = "Ex.: 300 ml",
                rotuloCentralizado = true
            )
            LactareCampo(
                rotulo = "Idade da doadora",
                valor = rascunho.idadeDaDoadora,
                onValorChange = { agendamentoController.atualizarQuestionario(idadeDaDoadora = it) },
                dica = "Ex.: 35",
                tipoDeTeclado = KeyboardType.Number,
                rotuloCentralizado = true,
                modifier = Modifier.padding(top = 16.dp)
            )
            LactareCampo(
                rotulo = "Ficou doente ou frequentou hospitais nos últimos 15 dias?",
                valor = rascunho.esteveDoente,
                onValorChange = { agendamentoController.atualizarQuestionario(esteveDoente = it) },
                dica = "Sim ou não",
                rotuloCentralizado = true,
                modifier = Modifier.padding(top = 16.dp)
            )
            LactareCampo(
                rotulo = "Toma algum remédio? (contínuo ou não)",
                valor = rascunho.tomaRemedio,
                onValorChange = { agendamentoController.atualizarQuestionario(tomaRemedio = it) },
                dica = "Sim ou não",
                rotuloCentralizado = true,
                modifier = Modifier.padding(top = 16.dp)
            )
            LactareCampo(
                rotulo = "Possui alguma doença crônica?",
                valor = rascunho.doencaCronica,
                onValorChange = { agendamentoController.atualizarQuestionario(doencaCronica = it) },
                dica = "Sim ou não",
                rotuloCentralizado = true,
                acaoDoTeclado = ImeAction.Done,
                modifier = Modifier.padding(top = 16.dp)
            )

            agendamentoController.erro?.let { mensagem ->
                Text(
                    text = mensagem,
                    style = MaterialTheme.typography.bodyMedium,
                    color = VermelhoDestrutivo,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp)
                )
            }

            LactareBotao(
                texto = "Próximo",
                icone = Icons.AutoMirrored.Filled.ArrowForward,
                onClick = {
                    if (agendamentoController.validarQuestionario()) {
                        onProximaEtapa(bancoId)
                    }
                },
                modifier = Modifier.padding(top = 24.dp)
            )
        }
        Spacer(modifier = Modifier.height(36.dp))
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun AgendamentoQuestionarioScreenPreview() {
    val bancoControllerPreview = BancoDeLeiteController()
    val agendamentoControllerPreview = AgendamentoController()

    AgendamentoQuestionarioScreen(
        bancoId = 1,
        agendamentoController = agendamentoControllerPreview,
        bancoController = bancoControllerPreview,
        onProximaEtapa = {},
        onVoltar = {}
    )
}