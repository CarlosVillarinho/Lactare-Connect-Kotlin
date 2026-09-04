package br.com.carlosvillarinho.lactareconnect.model

/**
 * Banco de leite humano exibido na listagem, no detalhe com mapa e na tela de
 * contatos.
 *
 * [posicaoXNoMapa] e [posicaoYNoMapa] sao coordenadas relativas (de 0 a 1)
 * usadas para posicionar o marcador no mapa estilizado desenhado em Compose.
 * Nao sao latitude e longitude reais: nesta Sprint o app nao consome API de
 * mapas.
 */
data class BancoDeLeite(
    val id: Int,
    val nome: String,
    val zona: String,
    val endereco: String,
    val complemento: String,
    val distanciaEmMetros: Int,
    val telefone: String,
    val email: String,
    val horarioAtendimento: String,
    val unidadeParceira: String,
    val posicaoXNoMapa: Float,
    val posicaoYNoMapa: Float
) {
    /** Distancia formatada para leitura, em metros ou quilometros. */
    val distanciaFormatada: String
        get() = if (distanciaEmMetros < 1000) {
            "$distanciaEmMetros metros de distância"
        } else {
            val km = distanciaEmMetros / 1000.0
            String.format("%.1f km de distância", km).replace('.', ',')
        }
}