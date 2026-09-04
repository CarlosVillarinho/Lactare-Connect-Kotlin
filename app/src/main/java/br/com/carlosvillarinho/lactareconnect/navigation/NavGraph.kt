package br.com.carlosvillarinho.lactareconnect.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.carlosvillarinho.lactareconnect.controller.AgendamentoController
import br.com.carlosvillarinho.lactareconnect.controller.AuthController
import br.com.carlosvillarinho.lactareconnect.controller.BancoDeLeiteController
import br.com.carlosvillarinho.lactareconnect.controller.CampanhaController
import br.com.carlosvillarinho.lactareconnect.controller.InformacaoController
import br.com.carlosvillarinho.lactareconnect.controller.NotificacaoController
import br.com.carlosvillarinho.lactareconnect.ui.theme.BegeFundo
import br.com.carlosvillarinho.lactareconnect.view.screens.AberturaScreen
import br.com.carlosvillarinho.lactareconnect.view.screens.AgendamentoLocalScreen
import br.com.carlosvillarinho.lactareconnect.view.screens.AgendamentoQuestionarioScreen
import br.com.carlosvillarinho.lactareconnect.view.screens.BancosDeLeiteScreen
import br.com.carlosvillarinho.lactareconnect.view.screens.CadastroScreen
import br.com.carlosvillarinho.lactareconnect.view.screens.CalendarioScreen
import br.com.carlosvillarinho.lactareconnect.view.screens.CampanhasScreen
import br.com.carlosvillarinho.lactareconnect.view.screens.ContatosScreen
import br.com.carlosvillarinho.lactareconnect.view.screens.DetalheDoBancoScreen
import br.com.carlosvillarinho.lactareconnect.view.screens.InformacoesScreen
import br.com.carlosvillarinho.lactareconnect.view.screens.InicioScreen
import br.com.carlosvillarinho.lactareconnect.view.screens.LoginScreen
import br.com.carlosvillarinho.lactareconnect.view.screens.NotificacoesScreen
import br.com.carlosvillarinho.lactareconnect.view.screens.PerfilScreen
import kotlinx.coroutines.launch

/**
 * Grafo de navegacao do Lactare Connect.
 *
 * Este Composable e o ponto de composicao do MVC: cria os controllers uma unica
 * vez, entrega a cada tela apenas o que ela precisa e traduz os eventos das
 * telas em rotas. As telas nunca criam controller nem conhecem outras telas,
 * apenas chamam funcoes como `onAbrirDetalhe`.
 *
 * O Scaffold no topo concentra o Snackbar, que da o retorno visual das acoes
 * (login, agendamento confirmado, notificacao apagada) em qualquer tela.
 */
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val escopo = rememberCoroutineScope()

    // Criados aqui para que o estado sobreviva a navegacao entre as telas.
    val authController = remember { AuthController() }
    val bancoController = remember { BancoDeLeiteController() }
    val agendamentoController = remember { AgendamentoController() }
    val campanhaController = remember { CampanhaController() }
    val notificacaoController = remember { NotificacaoController() }
    val informacaoController = remember { InformacaoController() }

    val mostrarMensagem: (String) -> Unit = { mensagem ->
        escopo.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(mensagem)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = BegeFundo
    ) { espacamento ->

        NavHost(
            navController = navController,
            startDestination = Rotas.ABERTURA,
            modifier = Modifier.padding(espacamento)
        ) {

            composable(Rotas.ABERTURA) {
                AberturaScreen(
                    onAvancar = {
                        navController.navigate(Rotas.LOGIN) {
                            popUpTo(Rotas.ABERTURA) { inclusive = true }
                        }
                    }
                )
            }

            composable(Rotas.LOGIN) {
                LoginScreen(
                    authController = authController,
                    onEntrar = {
                        navController.navigate(Rotas.INICIO) {
                            popUpTo(Rotas.LOGIN) { inclusive = true }
                        }
                    },
                    onIrParaCadastro = { navController.navigate(Rotas.CADASTRO) },
                    onMensagem = mostrarMensagem
                )
            }

            composable(Rotas.CADASTRO) {
                CadastroScreen(
                    authController = authController,
                    onCadastrar = {
                        navController.navigate(Rotas.INICIO) {
                            popUpTo(Rotas.LOGIN) { inclusive = true }
                        }
                    },
                    onVoltar = { navController.popBackStack() },
                    onMensagem = mostrarMensagem
                )
            }

            composable(Rotas.INICIO) {
                InicioScreen(
                    usuario = authController.usuarioLogado,
                    onAbrirInformacoes = { navController.navigate(Rotas.INFORMACOES) },
                    onAbrirBancosDeLeite = { navController.navigate(Rotas.BANCOS_DE_LEITE) },
                    onAbrirCampanhas = { navController.navigate(Rotas.CAMPANHAS) },
                    onAbrirContatos = { navController.navigate(Rotas.CONTATOS) },
                    onAbrirCalendario = { navController.navigate(Rotas.CALENDARIO) },
                    onAbrirPerfil = { navController.navigate(Rotas.PERFIL) },
                    onAbrirNotificacoes = { navController.navigate(Rotas.NOTIFICACOES) }
                )
            }

            composable(Rotas.INFORMACOES) {
                InformacoesScreen(
                    informacaoController = informacaoController,
                    onVoltar = { navController.popBackStack() }
                )
            }

            composable(Rotas.BANCOS_DE_LEITE) {
                BancosDeLeiteScreen(
                    bancoController = bancoController,
                    onAbrirDetalhe = { bancoId ->
                        navController.navigate(Rotas.criarDetalheDoBanco(bancoId))
                    },
                    onVoltar = { navController.popBackStack() }
                )
            }

            composable(
                route = Rotas.DETALHE_DO_BANCO,
                arguments = listOf(
                    navArgument(Rotas.PARAMETRO_BANCO_ID) { type = NavType.IntType }
                )
            ) { entrada ->
                val bancoId = entrada.arguments?.getInt(Rotas.PARAMETRO_BANCO_ID) ?: 0

                DetalheDoBancoScreen(
                    bancoId = bancoId,
                    bancoController = bancoController,
                    onAgendarDoacao = { id ->
                        agendamentoController.iniciarAgendamento(id)
                        navController.navigate(Rotas.criarAgendamentoQuestionario(id))
                    },
                    onVoltar = { navController.popBackStack() }
                )
            }

            composable(
                route = Rotas.AGENDAMENTO_QUESTIONARIO,
                arguments = listOf(
                    navArgument(Rotas.PARAMETRO_BANCO_ID) { type = NavType.IntType }
                )
            ) { entrada ->
                val bancoId = entrada.arguments?.getInt(Rotas.PARAMETRO_BANCO_ID) ?: 0

                AgendamentoQuestionarioScreen(
                    bancoId = bancoId,
                    agendamentoController = agendamentoController,
                    bancoController = bancoController,
                    onProximaEtapa = { id ->
                        navController.navigate(Rotas.criarAgendamentoLocal(id))
                    },
                    onVoltar = { navController.popBackStack() }
                )
            }

            composable(
                route = Rotas.AGENDAMENTO_LOCAL,
                arguments = listOf(
                    navArgument(Rotas.PARAMETRO_BANCO_ID) { type = NavType.IntType }
                )
            ) { entrada ->
                val bancoId = entrada.arguments?.getInt(Rotas.PARAMETRO_BANCO_ID) ?: 0

                AgendamentoLocalScreen(
                    bancoId = bancoId,
                    agendamentoController = agendamentoController,
                    bancoController = bancoController,
                    onConcluido = {
                        navController.navigate(Rotas.CALENDARIO) {
                            popUpTo(Rotas.BANCOS_DE_LEITE) { inclusive = false }
                        }
                    },
                    onVoltar = { navController.popBackStack() },
                    onMensagem = mostrarMensagem
                )
            }

            composable(Rotas.CALENDARIO) {
                CalendarioScreen(
                    agendamentoController = agendamentoController,
                    onVoltar = { navController.popBackStack() },
                    onMensagem = mostrarMensagem
                )
            }

            composable(Rotas.CAMPANHAS) {
                CampanhasScreen(
                    campanhaController = campanhaController,
                    onVoltar = { navController.popBackStack() }
                )
            }

            composable(Rotas.CONTATOS) {
                ContatosScreen(
                    bancoController = bancoController,
                    onVoltar = { navController.popBackStack() },
                    onMensagem = mostrarMensagem
                )
            }

            composable(Rotas.NOTIFICACOES) {
                NotificacoesScreen(
                    notificacaoController = notificacaoController,
                    onVoltar = { navController.popBackStack() },
                    onMensagem = mostrarMensagem
                )
            }

            composable(Rotas.PERFIL) {
                PerfilScreen(
                    authController = authController,
                    onSair = {
                        navController.navigate(Rotas.LOGIN) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onVoltar = { navController.popBackStack() },
                    onMensagem = mostrarMensagem
                )
            }
        }
    }
}
