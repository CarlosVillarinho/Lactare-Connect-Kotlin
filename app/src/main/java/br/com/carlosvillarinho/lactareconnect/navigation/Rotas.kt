package br.com.carlosvillarinho.lactareconnect.navigation

/**
 * Rotas do grafo de navegacao.
 *
 * As rotas que dependem de um banco de leite recebem o id como parametro, e as
 * funcoes `criar` montam o caminho ja preenchido. Assim nenhuma tela precisa
 * concatenar strings de rota na mao, o que evita erros de digitacao.
 */
object Rotas {
    const val ABERTURA = "abertura"
    const val LOGIN = "login"
    const val CADASTRO = "cadastro"
    const val INICIO = "inicio"
    const val INFORMACOES = "informacoes"
    const val BANCOS_DE_LEITE = "bancos_de_leite"
    const val CAMPANHAS = "campanhas"
    const val CONTATOS = "contatos"
    const val CALENDARIO = "calendario"
    const val NOTIFICACOES = "notificacoes"
    const val PERFIL = "perfil"

    const val PARAMETRO_BANCO_ID = "bancoId"
    const val DETALHE_DO_BANCO = "detalhe_do_banco/{$PARAMETRO_BANCO_ID}"
    const val AGENDAMENTO_QUESTIONARIO = "agendamento_questionario/{$PARAMETRO_BANCO_ID}"
    const val AGENDAMENTO_LOCAL = "agendamento_local/{$PARAMETRO_BANCO_ID}"

    fun criarDetalheDoBanco(bancoId: Int) = "detalhe_do_banco/$bancoId"
    fun criarAgendamentoQuestionario(bancoId: Int) = "agendamento_questionario/$bancoId"
    fun criarAgendamentoLocal(bancoId: Int) = "agendamento_local/$bancoId"
}