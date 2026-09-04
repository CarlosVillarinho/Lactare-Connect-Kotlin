package br.com.carlosvillarinho.lactareconnect.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.lactareconnect.model.Usuario
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulInformacoes
import br.com.carlosvillarinho.lactareconnect.ui.theme.AzulLactare
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.ui.theme.OlivaCalendario
import br.com.carlosvillarinho.lactareconnect.ui.theme.PretoAcao
import br.com.carlosvillarinho.lactareconnect.ui.theme.RoxoContatos
import br.com.carlosvillarinho.lactareconnect.ui.theme.TextoPrincipal
import br.com.carlosvillarinho.lactareconnect.ui.theme.VerdeBancos
import br.com.carlosvillarinho.lactareconnect.ui.theme.VinhoCampanhas
import br.com.carlosvillarinho.lactareconnect.view.components.CartaoMenu
import br.com.carlosvillarinho.lactareconnect.view.components.LogoLactare

/**
 * Tela inicial: saudacao personalizada e o menu que da acesso as cinco areas
 * do app. E o centro da navegacao, de onde partem todos os fluxos.
 */
@Composable
fun InicioScreen(
    usuario: Usuario?,
    onAbrirInformacoes: () -> Unit,
    onAbrirBancosDeLeite: () -> Unit,
    onAbrirCampanhas: () -> Unit,
    onAbrirContatos: () -> Unit,
    onAbrirCalendario: () -> Unit,
    onAbrirPerfil: () -> Unit,
    onAbrirNotificacoes: () -> Unit
) {
    val rolagem = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BegeFundo)
            .verticalScroll(rolagem)
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LogoLactare(modifier = Modifier.weight(1f))
            IconButton(onClick = onAbrirPerfil) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Abrir perfil",
                    tint = PretoAcao,
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(onClick = onAbrirNotificacoes) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Abrir notificações",
                    tint = PretoAcao,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Text(
            text = "Olá, ${usuario?.primeiroNome ?: "doadora"}!",
            style = MaterialTheme.typography.headlineLarge,
            color = AzulLactare,
            modifier = Modifier.padding(top = 26.dp)
        )

        Text(
            text = "Sua doação pode transformar vidas.",
            style = MaterialTheme.typography.bodyLarge,
            color = TextoPrincipal,
            modifier = Modifier.padding(top = 6.dp, bottom = 26.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            CartaoMenu(
                titulo = "Informações",
                descricao = "Tudo sobre a doação de leite materno.",
                icone = Icons.Filled.Info,
                corDoTitulo = AzulInformacoes,
                onClick = onAbrirInformacoes
            )
            CartaoMenu(
                titulo = "Bancos de Leite",
                descricao = "Encontre o banco mais perto de você.",
                icone = Icons.Filled.LocationOn,
                corDoTitulo = VerdeBancos,
                onClick = onAbrirBancosDeLeite
            )
            CartaoMenu(
                titulo = "Campanhas",
                descricao = "Participe de campanhas e coleta.",
                icone = Icons.Filled.Campaign,
                corDoTitulo = VinhoCampanhas,
                onClick = onAbrirCampanhas
            )
            CartaoMenu(
                titulo = "Contatos",
                descricao = "Obtenha os contatos do seu banco de escolha.",
                icone = Icons.Filled.ContactPage,
                corDoTitulo = RoxoContatos,
                onClick = onAbrirContatos
            )
            CartaoMenu(
                titulo = "Calendário",
                descricao = "Consulte seus agendamentos e consultas.",
                icone = Icons.Filled.CalendarMonth,
                corDoTitulo = OlivaCalendario,
                onClick = onAbrirCalendario
            )
        }

        Text(
            text = "",
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InicioScreenPreview() {
    val usuarioExemplo = Usuario(
        id = 1,
        nomeCompleto = "Lucy Silva",
        nomeUsuario = "lucy",
        senha = "123",
        email = "lucy@exemplo.com",
        telefone = "(11) 99999-9999",
        idade = 25,
        endereco = "Rua Exemplo, 123",
        cep = "01001-000"
    )

    InicioScreen(
        usuario = usuarioExemplo,
        onAbrirInformacoes = {},
        onAbrirBancosDeLeite = {},
        onAbrirCampanhas = {},
        onAbrirContatos = {},
        onAbrirCalendario = {},
        onAbrirPerfil = {},
        onAbrirNotificacoes = {}
    )
}