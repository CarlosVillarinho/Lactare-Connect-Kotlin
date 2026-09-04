package br.com.carlosvillarinho.lactareconnect.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeSuperficie
import br.com.carlosvillarinho.lactareconnect.ui.theme.BrancoTexto
import br.com.carlosvillarinho.lactareconnect.ui.theme.FormasLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoSecundario
import br.com.carlosvillarinho.lactareconnect.ui.theme.VerdeLactare
import java.util.Calendar
import java.util.GregorianCalendar

private val NOMES_DOS_MESES = listOf(
    "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
    "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
)

private val INICIAIS_DOS_DIAS = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb")

/**
 * Calendario mensal do prototipo.
 *
 * Dias com agendamento aparecem em verde; o dia selecionado, em azul. Usa
 * [GregorianCalendar] em vez de java.time porque a API de data do Java 8 so
 * esta disponivel a partir da API 26, e o app tem minSdk 24.
 *
 * @param diasComAgendamento dias do mes que possuem ao menos um compromisso.
 */
@Composable
fun CalendarioMensal(
    mes: Int,
    ano: Int,
    diasComAgendamento: Set<Int>,
    diaSelecionado: Int?,
    onDiaSelecionado: (Int) -> Unit,
    onMesAnterior: () -> Unit,
    onMesSeguinte: () -> Unit,
    modifier: Modifier = Modifier
) {
    val grade = remember(mes, ano) { montarGradeDoMes(mes, ano) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BegeSuperficie, FormasLactare.cartao)
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CabecalhoDoMes(
            mes = mes,
            ano = ano,
            onMesAnterior = onMesAnterior,
            onMesSeguinte = onMesSeguinte
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            INICIAIS_DOS_DIAS.forEach { inicial ->
                Text(
                    text = inicial,
                    style = MaterialTheme.typography.labelMedium,
                    color = TextoSecundario,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        grade.forEach { semana ->
            Row(modifier = Modifier.fillMaxWidth()) {
                semana.forEach { dia ->
                    Box(modifier = Modifier.weight(1f)) {
                        if (dia == null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                            )
                        } else {
                            CelulaDeDia(
                                dia = dia,
                                temAgendamento = dia in diasComAgendamento,
                                selecionado = dia == diaSelecionado,
                                onClick = { onDiaSelecionado(dia) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CabecalhoDoMes(
    mes: Int,
    ano: Int,
    onMesAnterior: () -> Unit,
    onMesSeguinte: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onMesAnterior) {
            Icon(
                imageVector = Icons.Filled.ChevronLeft,
                contentDescription = "Mês anterior",
                tint = AzulLactare,
                modifier = Modifier.size(26.dp)
            )
        }

        Text(
            text = "${NOMES_DOS_MESES[mes - 1]} de $ano",
            style = MaterialTheme.typography.titleMedium,
            color = AzulLactare
        )

        IconButton(onClick = onMesSeguinte) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Próximo mês",
                tint = AzulLactare,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

@Composable
private fun CelulaDeDia(
    dia: Int,
    temAgendamento: Boolean,
    selecionado: Boolean,
    onClick: () -> Unit
) {
    val corDeFundo = when {
        selecionado -> AzulLactare
        temAgendamento -> VerdeLactare
        else -> Color.Transparent
    }

    val corDoTexto = if (selecionado || temAgendamento) BrancoTexto else TextoPrincipal

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(3.dp)
            .background(corDeFundo, RoundedCornerShape(9.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dia.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (selecionado || temAgendamento) FontWeight.Bold else FontWeight.Normal,
            color = corDoTexto
        )
    }
}

/**
 * Monta a grade do mes como semanas de sete posicoes, comecando no domingo.
 * Posicoes fora do mes ficam nulas para que a View apenas as deixe em branco.
 */
private fun montarGradeDoMes(mes: Int, ano: Int): List<List<Int?>> {
    val calendario = GregorianCalendar(ano, mes - 1, 1)
    val diasNoMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH)
    val deslocamentoInicial = calendario.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY

    val celulas = MutableList<Int?>(deslocamentoInicial) { null }
    celulas.addAll((1..diasNoMes).toList())
    while (celulas.size % 7 != 0) {
        celulas.add(null)
    }

    return celulas.chunked(7)
}