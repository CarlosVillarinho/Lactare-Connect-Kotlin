package br.com.carlosvillarinho.lactareconnect.model

/** Natureza do compromisso marcado pela doadora. */
enum class TipoAgendamento(val rotulo: String) {
    DOACAO_NO_BANCO("Doação no banco"),
    COLETA_EM_CASA("Coleta em casa"),
    CAMPANHA("Campanha")
}

/**
 * Compromisso exibido no calendario. Guarda dia, mes e ano separados para
 * evitar depender de java.time, que so esta disponivel a partir da API 26.
 */
data class Agendamento(
    val id: Int,
    val dia: Int,
    val mes: Int,
    val ano: Int,
    val titulo: String,
    val tipo: TipoAgendamento,
    val bancoId: Int? = null
)