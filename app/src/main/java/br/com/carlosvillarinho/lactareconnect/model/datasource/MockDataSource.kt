package br.com.carlosvillarinho.lactareconnect.model.datasource

import br.com.carlosvillarinho.lactareconnect.model.Agendamento
import br.com.carlosvillarinho.lactareconnect.model.BancoDeLeite
import br.com.carlosvillarinho.lactareconnect.model.Campanha
import br.com.carlosvillarinho.lactareconnect.model.CategoriaNotificacao
import br.com.carlosvillarinho.lactareconnect.model.ConteudoInstitucional
import br.com.carlosvillarinho.lactareconnect.model.Notificacao
import br.com.carlosvillarinho.lactareconnect.model.TipoAgendamento
import br.com.carlosvillarinho.lactareconnect.model.Usuario

/**
 * Fonte unica de dados mockados do Lactare Connect.
 *
 * Nesta Sprint o app nao consome API, banco local nem backend, entao todo o
 * conteudo exibido nas telas nasce aqui. Concentrar os dados em um so lugar
 * mantem as telas livres de texto fixo e deixa a troca por uma fonte real
 * (Retrofit, Room) restrita aos controllers.
 *
 * Os bancos de leite, enderecos e contatos sao ficticios, mas foram escritos
 * com nomes, zonas e distancias plausiveis para a cidade de Sao Paulo.
 */
object MockDataSource {

    /** Doadora usada para simular a sessao autenticada. */
    val usuarioCadastrado = Usuario(
        id = 1,
        nomeCompleto = "Lucy de Souza Silva dos Santos",
        nomeUsuario = "lucy",
        senha = "lactare123",
        email = "lucysilvasantos01@gmail.com",
        telefone = "+55 11 98765-9080",
        idade = 35,
        endereco = "Rua Jacinto de Freitas, 1875",
        cep = "04790-876"
    )

    val bancosDeLeite = listOf(
        BancoDeLeite(
            id = 1,
            nome = "Banco de Leite Santa Casa",
            zona = "Centro de São Paulo",
            endereco = "Avenida Rio Branco, 422 - Campos Elísios",
            complemento = "Bloco A - 4º andar",
            distanciaEmMetros = 350,
            telefone = "+55 11 99191-9090",
            email = "santacasa@bancodeleite.org.br",
            horarioAtendimento = "Segunda a sexta, das 7h às 17h",
            unidadeParceira = "Maternidade São Camilo",
            posicaoXNoMapa = 0.30f,
            posicaoYNoMapa = 0.36f
        ),
        BancoDeLeite(
            id = 2,
            nome = "Banco de Leite Vila Nova Cachoeirinha",
            zona = "Zona Norte de São Paulo",
            endereco = "Avenida Deputado Emílio Carlos, 3100 - Limão",
            complemento = "Prédio da maternidade - térreo",
            distanciaEmMetros = 4200,
            telefone = "+55 11 98778-0987",
            email = "cachoeirinha@bancodeleite.org.br",
            horarioAtendimento = "Todos os dias, das 8h às 18h",
            unidadeParceira = "Hospital Maternidade Cachoeirinha",
            posicaoXNoMapa = 0.62f,
            posicaoYNoMapa = 0.18f
        ),
        BancoDeLeite(
            id = 3,
            nome = "Banco de Leite Vila Mariana",
            zona = "Zona Sul de São Paulo",
            endereco = "Rua Napoleão de Barros, 715 - Vila Clementino",
            complemento = "Ambulatório 2 - sala 12",
            distanciaEmMetros = 5800,
            telefone = "+55 11 93456-6556",
            email = "vilamariana@bancodeleite.org.br",
            horarioAtendimento = "Segunda a sábado, das 7h30 às 16h",
            unidadeParceira = "Hospital Universitário Vila Mariana",
            posicaoXNoMapa = 0.44f,
            posicaoYNoMapa = 0.68f
        ),
        BancoDeLeite(
            id = 4,
            nome = "Banco de Leite Tatuapé",
            zona = "Zona Leste de São Paulo",
            endereco = "Rua Antônio de Barros, 980 - Tatuapé",
            complemento = "Entrada pela recepção da maternidade",
            distanciaEmMetros = 7400,
            telefone = "+55 11 92560-0112",
            email = "tatuape@bancodeleite.org.br",
            horarioAtendimento = "Segunda a sexta, das 8h às 17h",
            unidadeParceira = "Maternidade Municipal do Tatuapé",
            posicaoXNoMapa = 0.78f,
            posicaoYNoMapa = 0.52f
        ),
        BancoDeLeite(
            id = 5,
            nome = "Banco de Leite Lapa",
            zona = "Zona Oeste de São Paulo",
            endereco = "Rua Guaicurus, 1274 - Lapa",
            complemento = "Anexo B - 2º andar",
            distanciaEmMetros = 6100,
            telefone = "+55 11 91918-2520",
            email = "lapa@bancodeleite.org.br",
            horarioAtendimento = "Segunda a sexta, das 9h às 18h",
            unidadeParceira = "Hospital Municipal da Lapa",
            posicaoXNoMapa = 0.16f,
            posicaoYNoMapa = 0.54f
        ),
        BancoDeLeite(
            id = 6,
            nome = "Banco de Leite Itapevi",
            zona = "Região metropolitana - Itapevi",
            endereco = "Avenida Presidente Vargas, 405 - Centro",
            complemento = "Unidade sede do Lactare",
            distanciaEmMetros = 32000,
            telefone = "+55 11 93030-4560",
            email = "itapevi@bancodeleite.org.br",
            horarioAtendimento = "Segunda a sexta, das 8h às 17h",
            unidadeParceira = "Hospital e Maternidade de Itapevi",
            posicaoXNoMapa = 0.10f,
            posicaoYNoMapa = 0.24f
        )
    )

    val campanhas = listOf(
        Campanha(
            id = 1,
            titulo = "Cada gota vale uma vida",
            texto = "Seu leite materno é um superpoder capaz de salvar vidas de bebês " +
                    "prematuros. Para os pequenos na UTI, cada gota doada é um remédio " +
                    "essencial para a recuperação. Se você produz em excesso, compartilhe " +
                    "saúde de forma simples e segura em casa. Os Bancos de Leite fornecem " +
                    "os frascos e, muitas vezes, buscam a doação na sua porta. Doe leite " +
                    "materno: um pequeno gesto de amor que transforma o futuro!",
            periodo = "1 a 30 de setembro",
            bancoResponsavel = "Banco de Leite Santa Casa"
        ),
        Campanha(
            id = 2,
            titulo = "Um potinho, dez recém-nascidos",
            texto = "Sabia que apenas um potinho de leite materno pode alimentar até 10 " +
                    "recém-nascidos? Muitas mães não conseguem amamentar, e o seu excesso " +
                    "de leite é a esperança delas. Não deixe sobrar, transforme-o em um " +
                    "verdadeiro presente de vida para bebês internados. Ligue para o Banco " +
                    "de Leite Humano mais próximo e saiba como preparar a sua coleta. Cada " +
                    "gota conta na luta pela vida; doe leite materno e faça a diferença " +
                    "hoje mesmo!",
            periodo = "5 a 25 de setembro",
            bancoResponsavel = "Banco de Leite Vila Nova Cachoeirinha"
        ),
        Campanha(
            id = 3,
            titulo = "Maternidade é rede de apoio",
            texto = "A maternidade é uma rede de apoio, e a sua doação de leite pode " +
                    "acolher outra família. Muitos bebês precisam dos anticorpos do leite " +
                    "humano para crescerem fortes e saudáveis. Se você está amamentando e " +
                    "com saúde, junte-se a essa corrente de solidariedade. A doação é um " +
                    "ato que traz alívio para o seu peito e esperança para o coração de " +
                    "outra mãe. Doe leite materno e seja a rede de apoio que um bebê " +
                    "prematuro precisa para viver.",
            periodo = "Durante todo o mês de setembro",
            bancoResponsavel = "Banco de Leite Vila Mariana"
        )
    )

    val agendamentos = listOf(
        Agendamento(
            id = 1,
            dia = 9,
            mes = 9,
            ano = 2025,
            titulo = "Doação na Maternidade São Camilo",
            tipo = TipoAgendamento.DOACAO_NO_BANCO,
            bancoId = 1
        ),
        Agendamento(
            id = 2,
            dia = 13,
            mes = 9,
            ano = 2025,
            titulo = "Campanha de doação no Banco de Leite Santa Casa",
            tipo = TipoAgendamento.CAMPANHA,
            bancoId = 1
        ),
        Agendamento(
            id = 3,
            dia = 29,
            mes = 9,
            ano = 2025,
            titulo = "Retirada da doação na moradia",
            tipo = TipoAgendamento.COLETA_EM_CASA,
            bancoId = 3
        )
    )

    val notificacoes = listOf(
        Notificacao(
            id = 1,
            categoria = CategoriaNotificacao.CALENDARIO,
            mensagem = "Seu próximo agendamento está se aproximando! " +
                    "Está marcado para o dia 09 de Setembro.",
            tempoRelativo = "30 min atrás"
        ),
        Notificacao(
            id = 2,
            categoria = CategoriaNotificacao.AGENDAMENTOS,
            mensagem = "Nova data e horário disponíveis no Banco de Leite Santa Casa!",
            tempoRelativo = "2 horas atrás"
        ),
        Notificacao(
            id = 3,
            categoria = CategoriaNotificacao.CAMPANHAS,
            mensagem = "Nova campanha acontecendo! Não se esqueça de dar uma olhada " +
                    "na nossa área de “Campanhas”.",
            tempoRelativo = "5 horas atrás"
        ),
        Notificacao(
            id = 4,
            categoria = CategoriaNotificacao.AGENDAMENTOS,
            mensagem = "Sua coleta em casa foi confirmada para o dia 29 de Setembro.",
            tempoRelativo = "1 dia atrás"
        ),
        Notificacao(
            id = 5,
            categoria = CategoriaNotificacao.CALENDARIO,
            mensagem = "O Banco de Leite Vila Mariana passou a atender aos sábados.",
            tempoRelativo = "3 dias atrás",
            apagada = true
        )
    )

    val conteudoInstitucional = ConteudoInstitucional(
        apresentacao = "O Lactare é um Banco de Leite Humano localizado em Itapevi - SP, " +
                "integrado à Rede de Banco de Leite Brasileira, que visa auxiliar não apenas " +
                "na doação e captação de leite, mas também na parte emocional das nutrizes.",
        chamadaBeneficios = "O leite materno é muito importante para os bebês, " +
                "oferecendo diversos benefícios como:",
        beneficios = listOf(
            "Proteção contra diarreias, infecções respiratórias e alergias;",
            "Redução da mortalidade em 13% de crianças menores de 5 anos;",
            "Redução do risco de desenvolvimento de hipertensão, colesterol alto, " +
                    "diabetes e obesidade na vida adulta."
        ),
        fechamento = "E para oferecer isso para eles não é necessário muito: apenas 1 ml " +
                "a cada refeição já é o suficiente para nutrir um bebê recém-nascido, " +
                "dependendo do peso. E mesmo assim, a falta de leite materno ainda é uma pauta."
    )
}