package br.com.carlosvillarinho.lactareconnect.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeCampo
import br.com.carlosvillarinho.lactareconnect.ui.theme.ContornoSuave
import br.com.carlosvillarinho.lactareconnect.ui.theme.FormasLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoSecundario

/**
 * Campo de texto do prototipo: rotulo em negrito acima e uma caixa totalmente
 * arredondada, com contorno fino, abaixo.
 *
 * Foi montado com [BasicTextField] em vez de OutlinedTextField porque o
 * componente do Material reserva espaco interno para um rotulo flutuante, o
 * que deixaria a caixa mais alta do que o desenho pede.
 */
@Composable
fun LactareCampo(
    rotulo: String,
    valor: String,
    onValorChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    dica: String = "",
    ehSenha: Boolean = false,
    tipoDeTeclado: KeyboardType = KeyboardType.Text,
    rotuloCentralizado: Boolean = false,
    acaoDoTeclado: ImeAction = ImeAction.Next
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = rotulo,
            style = MaterialTheme.typography.titleMedium,
            color = TextoPrincipal,
            textAlign = if (rotuloCentralizado) TextAlign.Center else TextAlign.Start,
            modifier = if (rotuloCentralizado) Modifier.fillMaxWidth() else Modifier
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .background(BegeCampo, FormasLactare.campo)
                .border(1.dp, ContornoSuave, FormasLactare.campo)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = valor,
                onValueChange = onValorChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = LocalTextStyle.current.merge(
                    MaterialTheme.typography.bodyMedium
                ).copy(color = TextoPrincipal),
                cursorBrush = SolidColor(AzulLactare),
                visualTransformation = if (ehSenha) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = tipoDeTeclado,
                    imeAction = acaoDoTeclado
                ),
                decorationBox = { campoInterno ->
                    if (valor.isEmpty() && dica.isNotEmpty()) {
                        Text(
                            text = dica,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextoSecundario
                        )
                    }
                    campoInterno()
                }
            )
        }
    }
}