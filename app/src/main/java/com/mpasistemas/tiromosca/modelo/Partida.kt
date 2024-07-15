package com.mpasistemas.tiromosca.modelo

data class Partida(

    var id: String = "",
    var data: String = "",
    var status: String = "",
    var desafiante: String = "",
    var desafiado: String = "",
    var numeroDesafiante: String = "",
    var numeroDesafiado: String = "",
    var vitorias: Int = 0,
    var derrotas: Int = 0,
    var pontuacao: Double = 0.0,
    var partidas: Int = 0,
    var jogadasDesafiante: List<Jogadas>? = null,
    var jogadasDesafiado: List<Jogadas>? = null,
)
