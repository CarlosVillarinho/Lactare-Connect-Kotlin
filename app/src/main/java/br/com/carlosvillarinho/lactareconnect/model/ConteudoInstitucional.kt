package br.com.carlosvillarinho.lactareconnect.model

/**
 * Texto da tela "Sobre | Informacoes". Fica em um modelo proprio para que a
 * View apenas o renderize, sem carregar texto fixo dentro dos Composables.
 */
data class ConteudoInstitucional(
    val apresentacao: String,
    val chamadaBeneficios: String,
    val beneficios: List<String>,
    val fechamento: String
)