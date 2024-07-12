package com.mpasistemas.tiromosca.modelo

data class Usuario(

    var id: String = "",
    var nome: String = "",
    var data: String = "",
    var status: String = "",
    var status2: String = "",
    var vitorias: Int = 0,
    var derrotas: Int = 0,
    var pontuacao: Double = 0.0,
    var partidas: Int = 0,
)
