package br.com.carlosvillarinho.lactareconnect.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * O prototipo tem unicamente um tema claro, entao nao ha variante escura nem cores
 * dinamicas: manter uma so paleta garante que o app fique identico ao Figma
 * em qualquer aparelho.
 */
private val EsquemaLactare = lightColorScheme(
    primary = AzulLactare,
    onPrimary = BrancoTexto,
    secondary = VerdeLactare,
    onSecondary = BrancoTexto,
    background = BegeFundo,
    onBackground = TextoPrincipal,
    surface = BegeSuperficie,
    onSurface = TextoPrincipal,
    surfaceVariant = BegeCampo,
    onSurfaceVariant = TextoSecundario,
    error = VermelhoDestrutivo,
    onError = BrancoTexto
)

@Composable
fun LactareConnectTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaLactare,
        typography = LactareTypography,
        shapes = LactareShapes,
        content = content
    )
}