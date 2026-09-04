package br.com.carlosvillarinho.lactareconnect.controller

import br.com.carlosvillarinho.lactareconnect.model.Campanha
import br.com.carlosvillarinho.lactareconnect.model.datasource.MockDataSource

/** Entrega as campanhas de conscientizacao para a tela de Campanhas. */
class CampanhaController {
    fun listar(): List<Campanha> = MockDataSource.campanhas

    fun buscarPorId(id: Int): Campanha? =
        MockDataSource.campanhas.firstOrNull { it.id == id }
}