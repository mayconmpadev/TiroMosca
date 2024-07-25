package com.mpasistemas.tiromosca.modelo

import java.util.Date

data class Torneio(


    var id: String = "",
    var nome: String = "",
    var data: Date =  Date(),
    var pontos: Int = 0,

)
