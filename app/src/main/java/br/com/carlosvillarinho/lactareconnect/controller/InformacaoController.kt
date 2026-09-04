package br.com.carlosvillarinho.lactareconnect.controller

import br.com.carlosvillarinho.lactareconnect.model.ConteudoInstitucional
import br.com.carlosvillarinho.lactareconnect.model.datasource.MockDataSource

/** Entrega o texto institucional da tela "Sobre | Informacoes". */
class InformacaoController {
    fun obterConteudo(): ConteudoInstitucional = MockDataSource.conteudoInstitucional
}