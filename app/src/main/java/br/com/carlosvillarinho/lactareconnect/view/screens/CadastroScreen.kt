package br.com.carlosvillarinho.lactareconnect.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.controller.AuthController
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.ui.theme.BrancoTexto
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal
import br.com.carlosvillarinho.lactareconnect.ui.theme.VermelhoDestrutivo
import br.com.carlosvillarinho.lactareconnect.view.components.LactareBotao
import br.com.carlosvillarinho.lactareconnect.view.components.LactareCampo
import br.com.carlosvillarinho.lactareconnect.view.components.LactarePainel
import br.com.carlosvillarinho.lactareconnect.view.components.LogoLactare

/**
 * Formulario de criacao de conta. As regras (e-mail valido, tamanho minimo da
 * senha, confirmacao correspondente) ficam no [AuthController].
 */
@Composable
fun CadastroScreen(
    authController: AuthController,
    onCadastrar: () -> Unit,
    onVoltar: () -> Unit,
    onMensagem: (String) -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var nomeCompleto by rememberSaveable { mutableStateOf("") }
    var senha by rememberSaveable { mutableStateOf("") }
    var confirmacao by rememberSaveable { mutableStateOf("") }
    val rolagem = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
            .verticalScroll(rolagem)
            .padding(horizontal = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoLactare(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        )

        Text(
            text = "Crie sua conta",
            style = MaterialTheme.typography.headlineLarge,
            color = AzulLactare,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp, bottom = 22.dp)
        )

        LactarePainel {
            Text(
                text = "Cadastro",
                style = MaterialTheme.typography.headlineMedium,
                color = TextoPrincipal,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 22.dp)
            )

            LactareCampo(
                rotulo = "Email",
                valor = email,
                onValorChange = { email = it; authController.limparErro() },
                dica = "seuemail@exemplo.com",
                tipoDeTeclado = KeyboardType.Email
            )

            LactareCampo(
                rotulo = "Nome Completo",
                valor = nomeCompleto,
                onValorChange = { nomeCompleto = it; authController.limparErro() },
                modifier = Modifier.padding(top = 14.dp)
            )

            LactareCampo(
                rotulo = "Senha",
                valor = senha,
                onValorChange = { senha = it; authController.limparErro() },
                ehSenha = true,
                modifier = Modifier.padding(top = 14.dp)
            )

            LactareCampo(
                rotulo = "Confirmação da senha",
                valor = confirmacao,
                onValorChange = { confirmacao = it; authController.limparErro() },
                ehSenha = true,
                acaoDoTeclado = ImeAction.Done,
                modifier = Modifier.padding(top = 14.dp)
            )

            authController.erro?.let { mensagem ->
                Text(
                    text = mensagem,
                    style = MaterialTheme.typography.bodyMedium,
                    color = VermelhoDestrutivo,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            LactareBotao(
                texto = "Criar Conta",
                onClick = {
                    if (authController.cadastrar(email, nomeCompleto, senha, confirmacao)) {
                        onMensagem("Conta criada! Bem-vinda ao Lactare Connect.")
                        onCadastrar()
                    }
                },
                modifier = Modifier.padding(top = 24.dp)
            )
        }

        Text(
            text = "Ou use uma conta existente",
            style = MaterialTheme.typography.titleMedium,
            color = TextoPrincipal,
            modifier = Modifier.padding(top = 26.dp, bottom = 14.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(22.dp)) {
            BotaoDeContaExterna(Icons.Filled.Language, Color(0xFFDB4437), "Google", onMensagem)
            BotaoDeContaExterna(Icons.Filled.Share, Color(0xFF1877F2), "Facebook", onMensagem)
            BotaoDeContaExterna(Icons.Filled.Email, Color(0xFF0A66C2), "LinkedIn", onMensagem)
        }

        LactareBotao(
            texto = "Voltar para o login",
            onClick = onVoltar,
            modifier = Modifier.padding(top = 30.dp, bottom = 36.dp)
        )
    }
}

/**
 * Atalho de login social. Nesta Sprint nao ha integracao com provedores
 * externos, entao o toque apenas avisa a doadora pelo Snackbar.
 */
@Composable
private fun BotaoDeContaExterna(
    icone: ImageVector,
    cor: Color,
    nome: String,
    onMensagem: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(cor, CircleShape)
            .clickable { onMensagem("O login com $nome entra na próxima Sprint.") },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icone,
            contentDescription = "Entrar com $nome",
            tint = BrancoTexto,
            modifier = Modifier.size(26.dp)
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun CadastroScreenPreview() {
    CadastroScreen(
        authController = AuthController(),
        onCadastrar = {},
        onVoltar = {},
        onMensagem = {}
    )
}