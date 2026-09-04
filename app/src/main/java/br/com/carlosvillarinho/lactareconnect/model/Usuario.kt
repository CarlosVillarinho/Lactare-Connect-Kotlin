package br.com.carlosvillarinho.lactareconnect.model

/**
 * Doadora cadastrada no aplicativo.
 *
 * [nomeUsuario] e [senha] existem apenas para simular a autenticacao nesta
 * Sprint; em producao a senha nunca ficaria no modelo em texto puro.
 */
data class Usuario(
    val id: Int,
    val nomeCompleto: String,
    val nomeUsuario: String,
    val senha: String,
    val email: String,
    val telefone: String,
    val idade: Int,
    val endereco: String,
    val cep: String
) {
    /** Primeiro nome, usado na saudacao da tela inicial. */
    val primeiroNome: String
        get() = nomeCompleto.trim().substringBefore(" ")
}