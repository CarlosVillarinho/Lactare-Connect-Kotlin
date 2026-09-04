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
 * Segunda etapa do agendamento: o endereco onde o leite sera coletado.
 *
 * Ao concluir, o controller transforma o rascunho em um agendamento real, a
 * doadora recebe a confirmacao pelo Snackbar e cai no calendario, ja com o
 * compromisso novo na lista.
 */
@Composable
fun AgendamentoLocalScreen(
    bancoId: Int,
    agendamentoController: AgendamentoController,
    bancoController: BancoDeLeiteController,
    onConcluido: () -> Unit,
    onVoltar: () -> Unit,
    onMensagem: (String) -> Unit
) {
    val banco = bancoController.buscarPorId(bancoId)
    val rascunho = agendamentoController.rascunho
    val rolagem = rememberScrollState()

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
            text = "Local de coleta do leite",
            style = MaterialTheme.typography.titleMedium,
            color = TextoPrincipal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )

        LactarePainel(modifier = Modifier.padding(horizontal = 18.dp)) {
            LactareCampo(
                rotulo = "Rua",
                valor = rascunho.rua,
                onValorChange = { agendamentoController.atualizarLocal(rua = it) },
                dica = "Rua Jacinto de Freitas",
                rotuloCentralizado = true
            )
            LactareCampo(
                rotulo = "CEP",
                valor = rascunho.cep,
                onValorChange = { agendamentoController.atualizarLocal(cep = it) },
                dica = "04790-876",
                tipoDeTeclado = KeyboardType.Number,
                rotuloCentralizado = true,
                modifier = Modifier.padding(top = 18.dp)
            )
            LactareCampo(
                rotulo = "Número",
                valor = rascunho.numero,
                onValorChange = { agendamentoController.atualizarLocal(numero = it) },
                dica = "1875",
                tipoDeTeclado = KeyboardType.Number,
                rotuloCentralizado = true,
                acaoDoTeclado = ImeAction.Done,
                modifier = Modifier.padding(top = 18.dp)
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
                texto = "Concluído",
                icone = Icons.AutoMirrored.Filled.ArrowForward,
                onClick = {
                    if (banco != null && agendamentoController.validarLocal()) {
                        val novo = agendamentoController.confirmar(banco)
                        onMensagem(
                            "Agendamento confirmado para ${novo.dia}/${novo.mes} " +
                                    "no ${banco.nome}."
                        )
                        onConcluido()
                    }
                },
                modifier = Modifier.padding(top = 26.dp)
            )
        }
        Spacer(modifier = Modifier.height(36.dp))
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun AgendamentoLocalScreenPreview() {
    val bancoControllerPreview = BancoDeLeiteController()
    val agendamentoControllerPreview = AgendamentoController()

    AgendamentoLocalScreen(
        bancoId = 1,
        agendamentoController = agendamentoControllerPreview,
        bancoController = bancoControllerPreview,
        onConcluido = {},
        onVoltar = {},
        onMensagem = {}
    )
}