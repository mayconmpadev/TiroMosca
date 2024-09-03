package com.mpasistemas.tiromosca.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class DailyTaskWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        // Coloque aqui a lógica da tarefa a ser executada
        executarMinhaFuncaoNoFinalDoDia()

        // Retorne um resultado indicando sucesso, falha ou necessidade de tentativa
        return Result.success()
    }

    private fun executarMinhaFuncaoNoFinalDoDia() {
        // Sua função que deve ser executada
        Log.i("diaria","teste")
    }
}