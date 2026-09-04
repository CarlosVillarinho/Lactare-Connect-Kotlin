package br.com.carlosvillarinho.lactareconnect.controller

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import br.com.carlosvillarinho.lactareconnect.model.Usuario
import br.com.carlosvillarinho.lactareconnect.model.datasource.MockDataSource

/**
 * Controla a sessao da doadora: login, cadastro, logout e exclusao de conta.
 *
 * A View nao conhece a regra de autenticacao; ela apenas envia o que foi
 * digitado e observa [usuarioLogado] e [erro].
 */
class AuthController {

    /** Doadora autenticada, ou null quando ninguem esta logado. */
    var usuarioLogado by mutableStateOf<Usuario?>(null)
        private set

    /** Mensagem de erro do ultimo login ou cadastro, para a View exibir. */
    var erro by mutableStateOf<String?>(null)
        private set

    private var contas = mutableListOf(MockDataSource.usuarioCadastrado)

    /**
     * Confere as credenciais contra as contas mockadas.
     * Aceita tanto o nome de usuario quanto o e-mail.
     */
    fun autenticar(identificacao: String, senha: String): Boolean {
        val id = identificacao.trim()
        if (id.isBlank() || senha.isBlank()) {
            erro = "Preencha o usuário e a senha para continuar."
            return false
        }
        val conta = contas.firstOrNull {
            it.nomeUsuario.equals(id, ignoreCase = true) || it.email.equals(id, ignoreCase = true)
        }
        if (conta == null || conta.senha != senha) {
            erro = "Usuário ou senha inválidos."
            return false
        }
        usuarioLogado = conta
        erro = null
        return true
    }

    /**
     * Cria uma conta nova a partir do formulario de cadastro e ja deixa a
     * doadora autenticada.
     */
    fun cadastrar(
        email: String,
        nomeCompleto: String,
        senha: String,
        confirmacaoDaSenha: String
    ): Boolean {
        when {
            email.isBlank() || nomeCompleto.isBlank() || senha.isBlank() -> {
                erro = "Preencha todos os campos do cadastro."
                return false
            }
            !email.contains("@") || !email.contains(".") -> {
                erro = "Informe um e-mail válido."
                return false
            }
            senha.length < 6 -> {
                erro = "A senha precisa ter ao menos 6 caracteres."
                return false
            }
            senha != confirmacaoDaSenha -> {
                erro = "A confirmação não corresponde à senha digitada."
                return false
            }
            contas.any { it.email.equals(email.trim(), ignoreCase = true) } -> {
                erro = "Já existe uma conta com esse e-mail."
                return false
            }
        }

        val nova = Usuario(
            id = contas.size + 1,
            nomeCompleto = nomeCompleto.trim(),
            nomeUsuario = email.trim().substringBefore("@"),
            senha = senha,
            email = email.trim(),
            telefone = "Não informado",
            idade = 0,
            endereco = "Não informado",
            cep = "Não informado"
        )
        contas.add(nova)
        usuarioLogado = nova
        erro = null
        return true
    }

    fun limparErro() {
        erro = null
    }

    fun sair() {
        usuarioLogado = null
        erro = null
    }

    /** Remove a conta ativa da lista mockada e encerra a sessao. */
    fun excluirConta() {
        usuarioLogado?.let { contas.remove(it) }
        usuarioLogado = null
        erro = null
    }
}