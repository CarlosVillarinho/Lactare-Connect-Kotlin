package br.com.carlosvillarinho.lactareconnect.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.controller.AuthController
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeSuperficie
import br.com.carlosvillarinho.lactareconnect.ui.theme.PretoAcao
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoSecundario
import br.com.carlosvillarinho.lactareconnect.ui.theme.VermelhoDestrutivo
import br.com.carlosvillarinho.lactareconnect.view.components.LactareBotao
import br.com.carlosvillarinho.lactareconnect.view.components.LactarePainel
import br.com.carlosvillarinho.lactareconnect.view.components.LactareTopBar

/**
 * Perfil da doadora, com os dados cadastrais e as duas acoes de conta.
 *
 * A exclusao passa por uma confirmacao em dialogo, porque e irreversivel e nao
 * deve acontecer por um toque acidental.
 */
@Composable
fun PerfilScreen(
    authController: AuthController,
    onSair: () -> Unit,
    onVoltar: () -> Unit,
    onMensagem: (String) -> Unit
) {
    val usuario = authController.usuarioLogado
    var confirmandoExclusao by remember { mutableStateOf(false) }
    val rolagem = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
            .verticalScroll(rolagem)
    ) {
        LactareTopBar(titulo = "Perfil", onVoltar = onVoltar)

        LactarePainel(modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(112.dp)
                        .background(PretoAcao, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = BegeSuperficie,
                        modifier = Modifier.size(76.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 92.dp)
                        .size(34.dp)
                        .background(BegeFundo, CircleShape)
                        .clickable {
                            onMensagem("A edição do perfil entra na próxima Sprint.")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar perfil",
                        tint = PretoAcao,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Text(
                text = usuario?.nomeCompleto ?: "Sessão encerrada",
                style = MaterialTheme.typography.headlineMedium,
                color = TextoPrincipal,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 26.dp)
            )

            if (usuario != null) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    LinhaDoPerfil("Email", usuario.email)
                    LinhaDoPerfil("Telefone", usuario.telefone)
                    LinhaDoPerfil(
                        "Idade",
                        if (usuario.idade > 0) "${usuario.idade} anos" else "Não informada"
                    )
                    LinhaDoPerfil("Endereço", usuario.endereco)
                    LinhaDoPerfil("CEP", usuario.cep)
                }
            }

            LactareBotao(
                texto = "Sair",
                onClick = {
                    authController.sair()
                    onMensagem("Você saiu da sua conta.")
                    onSair()
                },
                modifier = Modifier.padding(top = 40.dp)
            )

            Text(
                text = "Excluir Conta",
                style = MaterialTheme.typography.labelLarge,
                color = VermelhoDestrutivo,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
                    .clickable { confirmandoExclusao = true }
            )
        }

        Spacer(modifier = Modifier.height(36.dp))
    }

    if (confirmandoExclusao) {
        AlertDialog(
            onDismissRequest = { confirmandoExclusao = false },
            containerColor = BegeSuperficie,
            title = {
                Text(
                    text = "Excluir sua conta?",
                    style = MaterialTheme.typography.titleLarge,
                    color = AzulLactare
                )
            },
            text = {
                Text(
                    text = "Seus agendamentos e o histórico de doação serão apagados. " +
                            "Essa ação não pode ser desfeita.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoPrincipal
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    confirmandoExclusao = false
                    authController.excluirConta()
                    onMensagem("Conta excluída. Sentiremos sua falta!")
                    onSair()
                }) {
                    Text(
                        text = "Excluir",
                        color = VermelhoDestrutivo,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmandoExclusao = false }) {
                    Text(
                        text = "Cancelar",
                        color = AzulLactare,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        )
    }
}

@Composable
private fun LinhaDoPerfil(rotulo: String, valor: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = rotulo,
            style = MaterialTheme.typography.labelMedium,
            color = TextoSecundario
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.titleMedium,
            color = TextoPrincipal
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun PerfilScreenPreview() {
    val authPreview = AuthController().apply {
        autenticar("lucy", "lactare123")
    }

    PerfilScreen(
        authController = authPreview,
        onSair = {},
        onVoltar = {},
        onMensagem = {}
    )
}