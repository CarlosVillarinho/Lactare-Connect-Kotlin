package br.com.carlosvillarinho.lactareconnect.controller

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import br.com.carlosvillarinho.lactareconnect.model.Agendamento
import br.com.carlosvillarinho.lactareconnect.model.BancoDeLeite
import br.com.carlosvillarinho.lactareconnect.model.RascunhoAgendamento
import br.com.carlosvillarinho.lactareconnect.model.TipoAgendamento
import br.com.carlosvillarinho.lactareconnect.model.datasource.MockDataSource
import java.util.Calendar
import java.util.GregorianCalendar

/**
 * Controla o fluxo de agendamento, que atravessa duas telas: o questionario de
 * elegibilidade e o local de coleta.
 *
 * O [rascunho] guarda o que ja foi preenchido enquanto a doadora navega entre
 * as etapas, e so vira um [Agendamento] definitivo quando ela confirma. Assim
 * as telas ficam sem estado proprio e podem ser recriadas sem perder nada.
 *
 * A lista de agendamentos e mantida em memoria: nesta Sprint nao ha banco de
 * dados, entao os agendamentos criados duram enquanto o app estiver aberto.
 */
class AgendamentoController {

    private val _agendamentos = mutableStateListOf<Agendamento>().apply {
        addAll(MockDataSource.agendamentos)
    }

    /** Agendamentos em ordem cronologica, para o calendario exibir. */
    val agendamentos: List<Agendamento>
        get() = _agendamentos.sortedWith(compareBy({ it.ano }, { it.mes }, { it.dia }))

    var rascunho by mutableStateOf(RascunhoAgendamento())
        private set

    /** Erro de validacao da etapa atual, ou null quando esta tudo certo. */
    var erro by mutableStateOf<String?>(null)
        private set

    // ---------------------------------------------------------------- etapas

    /** Zera o rascunho ao iniciar um agendamento novo para determinado banco. */
    fun iniciarAgendamento(bancoId: Int) {
        rascunho = RascunhoAgendamento(bancoId = bancoId)
        erro = null
    }

    fun atualizarQuestionario(
        quantidadeDeLeite: String = rascunho.quantidadeDeLeite,
        idadeDaDoadora: String = rascunho.idadeDaDoadora,
        esteveDoente: String = rascunho.esteveDoente,
        tomaRemedio: String = rascunho.tomaRemedio,
        doencaCronica: String = rascunho.doencaCronica
    ) {
        rascunho = rascunho.copy(
            quantidadeDeLeite = quantidadeDeLeite,
            idadeDaDoadora = idadeDaDoadora,
            esteveDoente = esteveDoente,
            tomaRemedio = tomaRemedio,
            doencaCronica = doencaCronica
        )
        erro = null
    }

    fun atualizarLocal(
        rua: String = rascunho.rua,
        cep: String = rascunho.cep,
        numero: String = rascunho.numero
    ) {
        rascunho = rascunho.copy(rua = rua, cep = cep, numero = numero)
        erro = null
    }

    /** Valida o questionario de elegibilidade antes de liberar a etapa seguinte. */
    fun validarQuestionario(): Boolean {
        val r = rascunho
        val idade = r.idadeDaDoadora.trim().toIntOrNull()

        erro = when {
            r.quantidadeDeLeite.isBlank() || r.idadeDaDoadora.isBlank() ||
                    r.esteveDoente.isBlank() || r.tomaRemedio.isBlank() ||
                    r.doencaCronica.isBlank() ->
                "Responda todas as perguntas do questionário."

            idade == null || idade !in 15..60 ->
                "Informe a idade da doadora em anos, entre 15 e 60."

            else -> null
        }

        return erro == null
    }

    /** Valida o endereco de coleta. */
    fun validarLocal(): Boolean {
        val r = rascunho
        val digitosDoCep = r.cep.filter { it.isDigit() }

        erro = when {
            r.rua.isBlank() || r.cep.isBlank() || r.numero.isBlank() ->
                "Preencha rua, CEP e número para concluir."

            digitosDoCep.length != 8 ->
                "O CEP precisa ter 8 dígitos."

            else -> null
        }

        return erro == null
    }

    /**
     * Transforma o rascunho em um agendamento definitivo e o adiciona ao
     * calendario. A data escolhida e o proximo dia util a partir de hoje, o que
     * simula a confirmacao que viria do banco de leite.
     */
    fun confirmar(banco: BancoDeLeite): Agendamento {
        val data = proximoDiaUtil()

        val novo = Agendamento(
            id = (_agendamentos.maxOfOrNull { it.id } ?: 0) + 1,
            dia = data.get(Calendar.DAY_OF_MONTH),
            mes = data.get(Calendar.MONTH) + 1,
            ano = data.get(Calendar.YEAR),
            titulo = "Coleta em ${rascunho.rua}, ${rascunho.numero} - ${banco.nome}",
            tipo = TipoAgendamento.COLETA_EM_CASA,
            bancoId = banco.id
        )

        _agendamentos.add(novo)
        rascunho = RascunhoAgendamento()
        erro = null
        return novo
    }

    fun cancelar(agendamentoId: Int) {
        _agendamentos.removeAll { it.id == agendamentoId }
    }

    // ------------------------------------------------------------ consultas

    fun agendamentosDoMes(mes: Int, ano: Int): List<Agendamento> =
        agendamentos.filter { it.mes == mes && it.ano == ano }

    fun diasComAgendamento(mes: Int, ano: Int): Set<Int> =
        agendamentosDoMes(mes, ano).map { it.dia }.toSet()

    /**
     * Mes que o calendario deve abrir. Prioriza o mes do proximo agendamento
     * para que a tela nunca abra vazia.
     */
    fun mesInicialDoCalendario(): Pair<Int, Int> {
        val primeiro = agendamentos.firstOrNull()
        return if (primeiro != null) {
            primeiro.mes to primeiro.ano
        } else {
            val hoje = GregorianCalendar()
            (hoje.get(Calendar.MONTH) + 1) to hoje.get(Calendar.YEAR)
        }
    }

    private fun proximoDiaUtil(): Calendar {
        val data = GregorianCalendar()
        data.add(Calendar.DAY_OF_MONTH, 3)
        while (data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
            data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
        ) {
            data.add(Calendar.DAY_OF_MONTH, 1)
        }
        return data
    }
}