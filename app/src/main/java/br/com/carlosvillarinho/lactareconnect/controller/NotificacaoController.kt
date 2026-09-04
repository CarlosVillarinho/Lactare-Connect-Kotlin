package br.com.carlosvillarinho.lactareconnect.controller

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import br.com.carlosvillarinho.lactareconnect.model.CategoriaNotificacao
import br.com.carlosvillarinho.lactareconnect.model.Notificacao
import br.com.carlosvillarinho.lactareconnect.model.datasource.MockDataSource

/**
 * Controla a tela de Notificacoes: filtro por categoria, modo de selecao e
 * lixeira.
 *
 * Apagar move a notificacao para a lixeira em vez de remove-la, o que permite
 * a doadora consultar e restaurar o que apagou pelo botao "Apagados".
 */
class NotificacaoController {

    private val _notificacoes = mutableStateListOf<Notificacao>().apply {
        addAll(MockDataSource.notificacoes)
    }

    /** Categoria escolhida no filtro, ou null para exibir todas. */
    var filtroAtivo by mutableStateOf<CategoriaNotificacao?>(null)
        private set

    /** Quando verdadeiro, a lista mostra a lixeira em vez da caixa de entrada. */
    var mostrandoApagadas by mutableStateOf(false)
        private set

    var modoSelecao by mutableStateOf(false)
        private set

    private val _selecionadas = mutableStateListOf<Int>()
    val selecionadas: List<Int> get() = _selecionadas

    /** Lista ja filtrada, pronta para a View apenas renderizar. */
    val notificacoesVisiveis: List<Notificacao>
        get() = _notificacoes
            .filter { it.apagada == mostrandoApagadas }
            .filter { filtroAtivo == null || it.categoria == filtroAtivo }

    val quantidadeNaLixeira: Int
        get() = _notificacoes.count { it.apagada }

    // -------------------------------------------------------------- filtros

    /** Avanca o filtro para a proxima categoria, voltando a "todas" no fim. */
    fun alternarFiltro(): CategoriaNotificacao? {
        val categorias = CategoriaNotificacao.entries
        val indiceAtual = categorias.indexOf(filtroAtivo)
        filtroAtivo = if (indiceAtual == categorias.lastIndex) {
            null
        } else {
            categorias[indiceAtual + 1]
        }
        return filtroAtivo
    }

    fun alternarLixeira() {
        mostrandoApagadas = !mostrandoApagadas
        sairDoModoSelecao()
    }

    // -------------------------------------------------------------- selecao

    fun alternarModoSelecao() {
        modoSelecao = !modoSelecao
        if (!modoSelecao) _selecionadas.clear()
    }

    fun sairDoModoSelecao() {
        modoSelecao = false
        _selecionadas.clear()
    }

    fun alternarSelecao(id: Int) {
        if (_selecionadas.contains(id)) _selecionadas.remove(id) else _selecionadas.add(id)
    }

    fun estaSelecionada(id: Int): Boolean = _selecionadas.contains(id)

    // --------------------------------------------------------------- acoes

    /** Move para a lixeira e devolve quantas foram afetadas. */
    fun apagarSelecionadas(): Int {
        val quantidade = _selecionadas.size
        _selecionadas.forEach { id -> marcarComoApagada(id, true) }
        sairDoModoSelecao()
        return quantidade
    }

    /** Restaura da lixeira e devolve quantas voltaram. */
    fun restaurarSelecionadas(): Int {
        val quantidade = _selecionadas.size
        _selecionadas.forEach { id -> marcarComoApagada(id, false) }
        sairDoModoSelecao()
        return quantidade
    }

    fun apagar(id: Int) = marcarComoApagada(id, true)

    fun restaurar(id: Int) = marcarComoApagada(id, false)

    /** Esvazia a lixeira de vez e devolve quantas foram removidas. */
    fun esvaziarLixeira(): Int {
        val quantidade = _notificacoes.count { it.apagada }
        _notificacoes.removeAll { it.apagada }
        sairDoModoSelecao()
        return quantidade
    }

    private fun marcarComoApagada(id: Int, apagada: Boolean) {
        val indice = _notificacoes.indexOfFirst { it.id == id }
        if (indice >= 0) {
            _notificacoes[indice] = _notificacoes[indice].copy(apagada = apagada)
        }
    }
}