package br.com.carlosvillarinho.lactareconnect.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min

/**
 * Mapa estilizado desenhado inteiramente com o Canvas do Compose.
 *
 * Nesta Sprint o app nao integra com API de mapas, entao o mapa e uma
 * ilustracao: quarteiroes, avenidas, um parque e um rio compoem o fundo, e os
 * marcadores sao posicionados pelas coordenadas relativas de cada banco de
 * leite. O resultado transmite a ideia de localizacao sem depender de rede,
 * chave de API ou biblioteca externa.
 *
 * @param marcadores posicoes relativas (x e y de 0 a 1) de cada banco.
 * @param indiceDestacado marcador do banco aberto, desenhado maior e em azul.
 */
@Composable
fun MapaEstilizado(
    marcadores: List<Pair<Float, Float>>,
    modifier: Modifier = Modifier,
    indiceDestacado: Int = 0,
    altura: Dp = 300.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(altura)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            desenharBaseDoMapa()
            desenharMalhaViaria()
            desenharAvenidaPrincipal()
            desenharRio()

            marcadores.forEachIndexed { indice, (x, y) ->
                desenharMarcador(
                    centroX = x * size.width,
                    centroY = y * size.height,
                    destacado = indice == indiceDestacado
                )
            }
        }
    }
}

// Tons de um mapa urbano claro, harmonizados com a paleta bege do app.
private val FundoDoMapa = Color(0xFFEFEDE6)
private val Quarteirao = Color(0xFFE3E0D6)
private val AreaVerde = Color(0xFFCBE0C4)
private val Via = Color(0xFFFFFFFF)
private val ViaPrincipal = Color(0xFFF6D9A8)
private val Agua = Color(0xFFAFC9DE)
private val MarcadorComum = Color(0xFFC62828)
private val MarcadorDestaque = Color(0xFF143567)

private fun DrawScope.desenharBaseDoMapa() {
    drawRect(color = FundoDoMapa)

    // Quarteiroes espalhados em uma grade irregular, para nao parecer xadrez.
    val larguras = listOf(0.18f, 0.24f, 0.15f, 0.21f)
    val alturas = listOf(0.16f, 0.22f, 0.14f, 0.19f)
    var y = 0.06f

    for ((indiceLinha, alturaRelativa) in alturas.withIndex()) {
        var x = 0.04f
        for ((indiceColuna, larguraRelativa) in larguras.withIndex()) {
            val ehParque = (indiceLinha + indiceColuna) % 5 == 0
            drawRect(
                color = if (ehParque) AreaVerde else Quarteirao,
                topLeft = Offset(x * size.width, y * size.height),
                size = Size(larguraRelativa * size.width, alturaRelativa * size.height)
            )
            x += larguraRelativa + 0.045f
        }
        y += alturaRelativa + 0.055f
    }
}

private fun DrawScope.desenharMalhaViaria() {
    val espessura = min(size.width, size.height) * 0.022f

    listOf(0.21f, 0.44f, 0.66f, 0.88f).forEach { posicao ->
        drawLine(
            color = Via,
            start = Offset(0f, posicao * size.height),
            end = Offset(size.width, posicao * size.height),
            strokeWidth = espessura
        )
    }

    listOf(0.20f, 0.47f, 0.74f).forEach { posicao ->
        drawLine(
            color = Via,
            start = Offset(posicao * size.width, 0f),
            end = Offset(posicao * size.width, size.height),
            strokeWidth = espessura
        )
    }
}

private fun DrawScope.desenharAvenidaPrincipal() {
    val espessura = min(size.width, size.height) * 0.045f

    drawLine(
        color = ViaPrincipal,
        start = Offset(size.width * 0.02f, size.height * 0.92f),
        end = Offset(size.width * 0.98f, size.height * 0.10f),
        strokeWidth = espessura
    )
}

private fun DrawScope.desenharRio() {
    val caminho = Path().apply {
        moveTo(size.width * 0.86f, 0f)
        cubicTo(
            size.width * 0.74f, size.height * 0.28f,
            size.width * 0.95f, size.height * 0.58f,
            size.width * 0.80f, size.height
        )
    }

    drawPath(
        path = caminho,
        color = Agua,
        style = Stroke(width = min(size.width, size.height) * 0.055f)
    )
}

/** Pino de mapa: gota apontando para baixo com um furo branco no centro. */
private fun DrawScope.desenharMarcador(centroX: Float, centroY: Float, destacado: Boolean) {
    val cor = if (destacado) MarcadorDestaque else MarcadorComum
    val raio = min(size.width, size.height) * (if (destacado) 0.055f else 0.042f)

    // Haste triangular sob o circulo, formando a ponta do pino.
    val haste = Path().apply {
        moveTo(centroX - raio * 0.62f, centroY + raio * 0.55f)
        lineTo(centroX + raio * 0.62f, centroY + raio * 0.55f)
        lineTo(centroX, centroY + raio * 2.1f)
        close()
    }
    drawPath(path = haste, color = cor)

    drawCircle(color = cor, radius = raio, center = Offset(centroX, centroY))
    drawCircle(color = Via, radius = raio * 0.42f, center = Offset(centroX, centroY))
}