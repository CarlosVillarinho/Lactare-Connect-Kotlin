package br.com.carlosvillarinho.lactareconnect.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Raios de canto do prototipo. O painel grande e bem mais arredondado que os
 * cartoes de lista, e os campos de texto sao totalmente arredondados.
 */
object FormasLactare {
    val painel = RoundedCornerShape(28.dp)
    val cartao = RoundedCornerShape(14.dp)
    val botao = RoundedCornerShape(12.dp)
    val campo = RoundedCornerShape(percent = 50)
    val chip = RoundedCornerShape(10.dp)
}

val LactareShapes = Shapes(
    small = FormasLactare.botao,
    medium = FormasLactare.cartao,
    large = FormasLactare.painel
)