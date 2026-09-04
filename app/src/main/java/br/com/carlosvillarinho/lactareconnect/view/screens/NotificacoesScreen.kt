package br.com.carlosvillarinho.lactareconnect.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.controller.NotificacaoController
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeCampo
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.ui.theme.ContornoSuave
import br.com.carlosvillarinho.lactareconnect.ui.theme.FormasLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.PretoAcao
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoSecundario
import br.com.carlosvillarinho.lactareconnect.view.components.CartaoNotificacao
import br.com.carlosvillarinho.lactareconnect.view.components.LactareTopBar

/**
 * Central de notificacoes com filtro por categoria, modo de selecao e lixeira.
 *
 * Toda a regra fica no [NotificacaoController]: a tela apenas dispara acoes e
 * desenha o estado que o controller expoe.
 */
@Composable
fun NotificacoesScreen(
    notificacaoController: NotificacaoController,
    onVoltar: () -> Unit,
    onMensagem: (String) -> Unit
) {
    val visiveis = notificacaoController.notificacoesVisiveis
    val naLixeira = notificacaoController.mostrandoApagadas

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
    ) {
        LactareTopBar(
            titulo = if (naLixeira) "Apagados" else "Notificações",
            onVoltar = onVoltar
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BotaoDeAcao(
                texto = notificacaoController.filtroAtivo?.rotulo ?: "Filtrar",
                icone = Icons.Filled.Tune,
                onClick = {
                    val filtro = notificacaoController.alternarFiltro()
                    onMensagem(
                        filtro?.let { "Mostrando apenas: ${it.rotulo}" }
                            ?: "Mostrando todas as categorias"
                    )
                }
            )
            BotaoDeAcao(
                texto = if (notificacaoController.modoSelecao) "Cancelar" else "Selecionar",
                icone = Icons.Filled.Checklist,
                onClick = { notificacaoController.alternarModoSelecao() }
            )
            BotaoDeAcao(
                texto = if (naLixeira) {
                    "Voltar"
                } else {
                    "Apagados (${notificacaoController.quantidadeNaLixeira})"
                },
                icone = if (naLixeira) Icons.Filled.Restore else Icons.Filled.DeleteOutline,
                onClick = { notificacaoController.alternarLixeira() }
            )
        }

        if (notificacaoController.modoSelecao) {
            BarraDeSelecao(
                quantidade = notificacaoController.selecionadas.size,
                naLixeira = naLixeira,
                onConfirmar = {
                    val total = if (naLixeira) {
                        notificacaoController.restaurarSelecionadas()
                    } else {
                        notificacaoController.apagarSelecionadas()
                    }
                    onMensagem(
                        when {
                            total == 0 -> "Nenhuma notificação selecionada."
                            naLixeira -> "$total notificação(ões) restaurada(s)."
                            else -> "$total notificação(ões) movida(s) para os apagados."
                        }
                    )
                }
            )
        }

        if (visiveis.isEmpty()) {
            Text(
                text = if (naLixeira) {
                    "Nenhuma notificação apagada."
                } else {
                    "Você está em dia. Nada de novo por aqui."
                },
                style = MaterialTheme.typography.bodyLarge,
                color = TextoSecundario,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 48.dp)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(visiveis, key = { it.id }) { notificacao ->
                    CartaoNotificacao(
                        notificacao = notificacao,
                        modoSelecao = notificacaoController.modoSelecao,
                        selecionada = notificacaoController.estaSelecionada(notificacao.id),
                        onClick = {
                            if (notificacaoController.modoSelecao) {
                                notificacaoController.alternarSelecao(notificacao.id)
                            } else if (naLixeira) {
                                notificacaoController.restaurar(notificacao.id)
                                onMensagem("Notificação restaurada.")
                            } else {
                                notificacaoController.apagar(notificacao.id)
                                onMensagem("Notificação movida para os apagados.")
                            }
                        }
                    )
                }
            }
        }
    }
}

/** Botao pequeno com contorno usado na barra de acoes da tela. */
@Composable
private fun BotaoDeAcao(
    texto: String,
    icone: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .shadow(elevation = 3.dp, shape = FormasLactare.chip, clip = false)
            .background(BegeCampo, FormasLactare.chip)
            .border(1.dp, ContornoSuave, FormasLactare.chip)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelMedium,
            color = TextoPrincipal
        )
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = PretoAcao,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun BarraDeSelecao(
    quantidade: Int,
    naLixeira: Boolean,
    onConfirmar: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$quantidade selecionada(s)",
            style = MaterialTheme.typography.labelMedium,
            color = AzulLactare
        )
        BotaoDeAcao(
            texto = if (naLixeira) "Restaurar" else "Apagar",
            icone = if (naLixeira) Icons.Filled.Restore else Icons.Filled.DeleteOutline,
            onClick = onConfirmar
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificacoesScreenPreview() {
    val controllerPreview = NotificacaoController()

    NotificacoesScreen(
        notificacaoController = controllerPreview,
        onVoltar = {},
        onMensagem = {}
    )
}