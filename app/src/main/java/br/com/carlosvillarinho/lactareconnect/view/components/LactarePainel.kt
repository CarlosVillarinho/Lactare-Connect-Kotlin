package br.com.carlosvillarinho.lactareconnect.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeSuperficie
import br.com.carlosvillarinho.lactareconnect.ui.theme.ContornoSuave
import br.com.carlosvillarinho.lactareconnect.ui.theme.FormasLactare

/**
 * Painel bege bem arredondado com contorno fino que envolve os formularios e
 * os blocos de conteudo do prototipo (Login, Cadastro, Perfil, Informacoes e
 * as duas etapas do agendamento).
 */
@Composable
fun LactarePainel(
    modifier: Modifier = Modifier,
    espacamentoInterno: Int = 22,
    conteudo: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp, shape = FormasLactare.painel, clip = false)
            .background(BegeSuperficie, FormasLactare.painel)
            .border(1.dp, ContornoSuave, FormasLactare.painel)
            .padding(espacamentoInterno.dp),
        content = conteudo
    )
}