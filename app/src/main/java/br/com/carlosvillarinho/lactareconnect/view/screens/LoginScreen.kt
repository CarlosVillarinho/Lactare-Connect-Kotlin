package br.com.carlosvillarinho.lactareconnect.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.controller.AuthController
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoSecundario
import br.com.carlosvillarinho.lactareconnect.ui.theme.VermelhoDestrutivo
import br.com.carlosvillarinho.lactareconnect.view.components.LactareBotao
import br.com.carlosvillarinho.lactareconnect.view.components.LactareCampo
import br.com.carlosvillarinho.lactareconnect.view.components.LactarePainel
import br.com.carlosvillarinho.lactareconnect.view.components.LogoLactare

/**
 * Tela de login. A validacao das credenciais fica no [AuthController]; aqui a
 * View so guarda o que foi digitado e exibe o erro que o controller devolve.
 */
@Composable
fun LoginScreen(
    authController: AuthController,
    onEntrar: () -> Unit,
    onIrParaCadastro: () -> Unit,
    onMensagem: (String) -> Unit
) {
    var usuario by rememberSaveable { mutableStateOf("") }
    var senha by rememberSaveable { mutableStateOf("") }
    val rolagem = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
            .verticalScroll(rolagem)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        LogoLactare(
            modifier = Modifier.padding(top = 56.dp),
            tamanhoIcone = 54.dp,
            tamanhoTexto = MaterialTheme.typography.headlineMedium.fontSize
        )
        LactarePainel(modifier = Modifier.padding(top = 12.dp)) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                color = TextoPrincipal,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 22.dp)
            )
            LactareCampo(
                rotulo = "Usuário",
                valor = usuario,
                onValorChange = {
                    usuario = it
                    authController.limparErro()
                },
                dica = "lucy"
            )
            LactareCampo(
                rotulo = "Senha",
                valor = senha,
                onValorChange = {
                    senha = it
                    authController.limparErro()
                },
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
                texto = "Entrar",
                onClick = {
                    if (authController.autenticar(usuario, senha)) {
                        onMensagem("Bem-vinda de volta!")
                        onEntrar()
                    }
                },
                modifier = Modifier.padding(top = 24.dp)
            )
        }
        Text(
            text = "Não tem conta? Clique no botão abaixo",
            style = MaterialTheme.typography.bodyMedium,
            color = TextoPrincipal,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        LactareBotao(
            texto = "Cadastrar",
            onClick = onIrParaCadastro
        )
        Text(
            text = "Versão de demonstração — acesse com o usuário lucy e a senha lactare123.",
            style = MaterialTheme.typography.labelMedium,
            color = TextoSecundario,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp, bottom = 36.dp)
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        authController = AuthController(),
        onEntrar = {},
        onIrParaCadastro = {},
        onMensagem = {}
    )
}