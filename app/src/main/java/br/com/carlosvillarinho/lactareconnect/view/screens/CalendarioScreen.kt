package br.com.carlosvillarinho.lactareconnect.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.controller.AgendamentoController
import br.com.carlosvillarinho.lactareconnect.model.Agendamento
import br.com.carlosvillarinho.lactareconnect.model.TipoAgendamento
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeSuperficie
import br.com.carlosvillarinho.lactareconnect.ui.theme.BrancoTexto
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoSecundario
import br.com.carlosvillarinho.lactareconnect.ui.theme.VerdeLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.VermelhoDestrutivo
import br.com.carlosvillarinho.lactareconnect.view.components.CalendarioMensal
import br.com.carlosvillarinho.lactareconnect.view.components.LactareTopBar

/**
 * Calendario de agendamentos.
 *
 * Abre no mes do proximo compromisso para nao aparecer vazio. Tocar em um dia
 * filtra a lista abaixo; tocar de novo no mesmo dia remove o filtro.
 */
@Composable
fun CalendarioScreen(
    agendamentoController: AgendamentoController,
    onVoltar: () -> Unit,
    onMensagem: (String) -> Unit
) {
    val mesInicial = remember { agendamentoController.mesInicialDoCalendario() }
    var mes by remember { mutableStateOf(mesInicial.first) }
    var ano by remember { mutableStateOf(mesInicial.second) }
    var diaSelecionado by remember { mutableStateOf<Int?>(null) }

    val agendamentosDoMes = agendamentoController.agendamentosDoMes(mes, ano)
    val diasComAgendamento = agendamentoController.diasComAgendamento(mes, ano)

    val listaVisivel = diaSelecionado
        ?.let { dia -> agendamentosDoMes.filter { it.dia == dia } }
        ?: agendamentosDoMes

    val rolagem = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
            .verticalScroll(rolagem)
    ) {
        LactareTopBar(titulo = "Calendário", onVoltar = onVoltar)

        CalendarioMensal(
            mes = mes,
            ano = ano,
            diasComAgendamento = diasComAgendamento,
            diaSelecionado = diaSelecionado,
            onDiaSelecionado = { dia ->
                diaSelecionado = if (diaSelecionado == dia) null else dia
            },
            onMesAnterior = {
                diaSelecionado = null
                if (mes == 1) {
                    mes = 12; ano -= 1
                } else {
                    mes -= 1
                }
            },
            onMesSeguinte = {
                diaSelecionado = null
                if (mes == 12) {
                    mes = 1; ano += 1
                } else {
                    mes += 1
                }
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(BegeSuperficie)
                .padding(horizontal = 18.dp, vertical = 20.dp)
        ) {
            Text(
                text = if (diaSelecionado == null) {
                    "Agendamentos"
                } else {
                    "Agendamentos do dia $diaSelecionado"
                },
                style = MaterialTheme.typography.headlineMedium,
                color = AzulLactare,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 18.dp)
            )

            if (listaVisivel.isEmpty()) {
                Text(
                    text = "Nenhum compromisso neste período. Escolha um banco de leite " +
                            "e agende sua próxima doação.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoSecundario,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                )
            } else {
                listaVisivel.forEachIndexed { indice, agendamento ->
                    LinhaDeAgendamento(
                        agendamento = agendamento,
                        onCancelar = {
                            agendamentoController.cancelar(agendamento.id)
                            onMensagem("Agendamento cancelado.")
                        }
                    )
                    if (indice != listaVisivel.lastIndex) {
                        HorizontalDivider(
                            color = VerdeLactare.copy(alpha = 0.35f),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun LinhaDeAgendamento(
    agendamento: Agendamento,
    onCancelar: () -> Unit
) {
    val corDoDia = when (agendamento.tipo) {
        TipoAgendamento.CAMPANHA -> AzulLactare
        else -> VerdeLactare
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(corDoDia, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = agendamento.dia.toString(),
                style = MaterialTheme.typography.labelLarge,
                color = BrancoTexto
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = agendamento.titulo,
                style = MaterialTheme.typography.bodyLarge,
                color = TextoPrincipal
            )
            Text(
                text = agendamento.tipo.rotulo,
                style = MaterialTheme.typography.labelMedium,
                color = TextoSecundario
            )
        }
        IconButton(onClick = onCancelar) {
            Icon(
                imageVector = Icons.Filled.DeleteOutline,
                contentDescription = "Cancelar agendamento",
                tint = VermelhoDestrutivo
            )
        }
    }

}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalendarioScreenPreview() {
    val agendamentoControllerPreview = AgendamentoController()

    CalendarioScreen(
        agendamentoController = agendamentoControllerPreview,
        onVoltar = {},
        onMensagem = {}
    )
}