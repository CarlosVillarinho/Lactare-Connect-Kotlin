package br.com.carlosvillarinho.lactareconnect.model

/**
 * Dados preenchidos ao longo do fluxo de agendamento, que ocupa duas telas.
 * O rascunho vive no controller enquanto a doadora navega entre o questionario
 * de elegibilidade e o local de coleta, e vira um [Agendamento] ao final.
 */
data class RascunhoAgendamento(
    val bancoId: Int = 0,
    val quantidadeDeLeite: String = "",
    val idadeDaDoadora: String = "",
    val esteveDoente: String = "",
    val tomaRemedio: String = "",
    val doencaCronica: String = "",
    val rua: String = "",
    val cep: String = "",
    val numero: String = ""
)