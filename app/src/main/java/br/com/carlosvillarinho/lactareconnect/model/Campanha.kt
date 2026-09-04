package br.com.carlosvillarinho.lactareconnect.model

/**
 * Campanha de conscientizacao exibida na tela de Campanhas.
 * Cada campanha tem um foco diferente de argumentacao para a doadora.
 */
data class Campanha(
    val id: Int,
    val titulo: String,
    val texto: String,
    val periodo: String,
    val bancoResponsavel: String
)