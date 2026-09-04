package br.com.carlosvillarinho.lactareconnect.model

/** Origem da notificacao, usada pelo filtro da tela de Notificacoes. */
enum class CategoriaNotificacao(val rotulo: String) {
    CALENDARIO("Calendário"),
    AGENDAMENTOS("Agendamentos"),
    CAMPANHAS("Campanhas")
}

/**
 * Aviso enviado a doadora.
 *
 * [apagada] marca a notificacao como movida para a lixeira em vez de removida
 * da lista, permitindo que a tela ofereca a visualizacao de "Apagados".
 */
data class Notificacao(
    val id: Int,
    val categoria: CategoriaNotificacao,
    val mensagem: String,
    val tempoRelativo: String,
    val apagada: Boolean = false
)