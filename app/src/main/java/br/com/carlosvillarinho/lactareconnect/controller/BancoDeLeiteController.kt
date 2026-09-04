package br.com.carlosvillarinho.lactareconnect.controller

import br.com.carlosvillarinho.lactareconnect.model.BancoDeLeite
import br.com.carlosvillarinho.lactareconnect.model.datasource.MockDataSource

/**
 * Entrega os bancos de leite para a listagem, para o detalhe com mapa e para a
 * tela de contatos. As tres telas leem a mesma fonte, entao um dado corrigido
 * aqui aparece corrigido em todas.
 */
class BancoDeLeiteController {

    /** Bancos ordenados do mais proximo para o mais distante. */
    fun listar(): List<BancoDeLeite> =
        MockDataSource.bancosDeLeite.sortedBy { it.distanciaEmMetros }

    fun buscarPorId(id: Int): BancoDeLeite? =
        MockDataSource.bancosDeLeite.firstOrNull { it.id == id }

    /** Bancos vizinhos, usados para plotar marcadores extras no mapa. */
    fun vizinhosDe(id: Int): List<BancoDeLeite> =
        MockDataSource.bancosDeLeite.filter { it.id != id }.take(2)
}