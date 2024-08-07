package com.mpasistemas.tiromosca.activity

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpasistemas.tiromosca.R
import com.mpasistemas.tiromosca.adapter.jogadasAdapter
import com.mpasistemas.tiromosca.databinding.ActivityPraticarBinding
import com.mpasistemas.tiromosca.databinding.DialogGanhouBinding
import com.mpasistemas.tiromosca.databinding.DialogPadraoOkCancelarBinding
import com.mpasistemas.tiromosca.modelo.Jogadas
import com.mpasistemas.tiromosca.modelo.Torneio
import com.mpasistemas.tiromosca.util.Util
import java.util.Date

class PraticarActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityPraticarBinding
    lateinit var dialog: AlertDialog
    private lateinit var timer: CountDownTimer
    private lateinit var countDownTimer: CountDownTimer
    lateinit var adapter: jogadasAdapter;
    private var repeatTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 300000 // 10 minutos em milissegundos
    private var tenSecondsWarningShown = false
    private var timerRunning = false
    var apertados: ArrayList<Button> = ArrayList()
    val numAleatorio: String = Util.numeroAleatorio()
    var lista: ArrayList<Jogadas> = ArrayList()
    lateinit var jogada: Jogadas
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!timerRunning) {
            startTimer()
        }
        binding = ActivityPraticarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.teclado.btn0.setOnClickListener(this)
        binding.teclado.btn1.setOnClickListener(this)
        binding.teclado.btn2.setOnClickListener(this)
        binding.teclado.btn3.setOnClickListener(this)
        binding.teclado.btn4.setOnClickListener(this)
        binding.teclado.btn5.setOnClickListener(this)
        binding.teclado.btn6.setOnClickListener(this)
        binding.teclado.btn7.setOnClickListener(this)
        binding.teclado.btn8.setOnClickListener(this)
        binding.teclado.btn9.setOnClickListener(this)

        binding.bntConferir.setOnClickListener() {
            finish()
        }


        val bundle = intent.extras//todos os parâmetros
        if (bundle != null) {
            val email = bundle.getString("email")
            Toast.makeText(this, email, Toast.LENGTH_LONG).show()
        }
        binding.teclado.btnApagar.setOnClickListener {
            apagar()
        }

        binding.teclado.btnSalvar.setOnClickListener {
            // playSound(R.raw.bip_2)
            if (!timerRunning) {
                startTimer()
            }
            binding.qtdJogadas.text = (lista.size + 1).toString()
            jogada = Jogadas()
            jogada.jogada = binding.textJogada.text.toString()
            mosca(binding.textJogada.text.toString())
            apagar()
        }
        binding.rvJogadas.layoutManager = LinearLayoutManager(this)
        adapter = jogadasAdapter(lista)
        binding.rvJogadas.adapter = adapter

        updateCountDownText()

    }

    fun jogada(button: Button) {
        apertados.add(button)
        val num = binding.textJogada.length()
        if (num <= 2) {
            binding.textJogada.text = binding.textJogada.text.toString() + button.text
            button.isEnabled = false
            button.setBackgroundResource(R.drawable.bg_redondo_apertado)
        } else if (binding.textJogada.length() <= 3) {
            binding.textJogada.text = binding.textJogada.text.toString() + button.text
            button.isEnabled = false
            button.setBackgroundResource(R.drawable.bg_redondo_apertado)
            binding.teclado.btnSalvar.visibility = View.VISIBLE
        }
    }

    fun apagar() {
        binding.textJogada.text = ""
        binding.teclado.btnSalvar.visibility = INVISIBLE
        for (i in apertados) {
            i.isEnabled = true
            i.setBackgroundResource(R.drawable.bg_redondo)
        }
        apertados.clear()
    }

    fun mosca(jogada: String) {
        var moscas = ""
        for (i in 0..3) {
            if (jogada[i] == numAleatorio[i]) {
                moscas += "m"
            }

        }
        if (moscas.equals("mmmm")) {
            ganhou()

        } else if (lista.size == 14) {
            pauseTimer()
            binding.textNumeroAleatorio.text = numAleatorio
            binding.bntConferir.visibility = VISIBLE
            binding.teclado.root.visibility = GONE
            val intent = Intent(this, GamerOverActivity::class.java)
            startActivity(intent)

        }
        this.jogada.mosca = moscas

        tiro(jogada)
    }

    fun tiro(jogada: String) {
        var tiros = ""
        for (i in 0..3) {
            for (j in 0..3) {
                if (jogada[j] == numAleatorio[i] && (j != i)) {
                    tiros += "t"
                }
            }
        }
        this.jogada.mosca += tiros
        lista.add(this.jogada)
        binding.rvJogadas.scrollToPosition(adapter.itemCount - 1)


    }

    private fun ganhou() {
        pauseTimer()
        binding.textNumeroAleatorio.text = numAleatorio
        val tempo: Int = (binding.timerTextView.text.toString().replace(":", "")).toInt()
        var numJogadas: Int = (lista.size + 1) * 1000
        numJogadas = 20000 - numJogadas
        val pontuacao: Int = numJogadas + tempo
        Toast.makeText(this, String.format(pontuacao.toString()), Toast.LENGTH_SHORT).show()
        binding.textNumeroAleatorio.text = binding.textJogada.text
        dialogSair(pontuacao.toString())

    }

    private fun startTimer() {

        val intent = Intent(this, GamerOverActivity::class.java)
        countDownTimer =
            object : CountDownTimer(timeLeftInMillis, 10) { // Atualiza a cada 10 milissegundos
                override fun onTick(millisUntilFinished: Long) {
                    timeLeftInMillis = millisUntilFinished
                    updateCountDownText()

                    // Verificar se faltam 10 segundos
                    if (timeLeftInMillis <= 10000 && !tenSecondsWarningShown) {
                        onTenSecondsLeft()
                        tenSecondsWarningShown = true
                    }
                }

                override fun onFinish() {
                    timerRunning = false
                    updateCountDownText()
                    binding.timerTextView.text = "Time's up!"
                    binding.bntConferir.visibility = VISIBLE
                    binding.teclado.root.visibility = GONE
                    startActivity(intent)
                }
            }.start()
        timerRunning = true;
    }

    private fun updateCountDownText() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        val milliseconds = (timeLeftInMillis % 1000) / 10
        val timeFormatted = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
        binding.timerTextView.text = timeFormatted
    }

    private fun pauseTimer() {
        Toast.makeText(this, "Pausado", Toast.LENGTH_SHORT).show()
        countDownTimer.cancel()
        timerRunning = false
    }

    private fun onTenSecondsLeft() {
        // Ação quando faltam 10 segundos
        Toast.makeText(this, "Faltam 10 segundos!", Toast.LENGTH_SHORT).show()
        // playSound(R.raw.bip_1)
    }

    private fun startRepeatSound() {
        repeatTimer = object : CountDownTimer(10000, 1000) { // 10 segundos, tocando a cada segundo
            override fun onTick(millisUntilFinished: Long) {
                //  playSound(R.raw.bip_1)
            }

            override fun onFinish() {
                // Nenhuma ação necessária aqui
            }
        }.start()
    }


    private fun playSound(soundResId: Int) {
        mediaPlayer = MediaPlayer.create(this, soundResId)
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        pauseTimer()
        Log.d("teste", "destroi")
    }

    private fun dialogSair(pontos: String) {

        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)

        val dialogBinding: DialogGanhouBinding = DialogGanhouBinding
            .inflate(LayoutInflater.from(this))
        dialogBinding.textPontuacao.text = pontos


        dialogBinding.btnPraticar.setOnClickListener { view ->
            val intent = Intent(this, PraticarActivity::class.java)
            startActivity(intent)
            finish()
            dialog!!.dismiss()

        }

        dialogBinding.btnInicio.setOnClickListener { view ->

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            dialog!!.dismiss()
        }

        builder.setView(dialogBinding.getRoot())
        dialog = builder.create()
        dialog!!.show()

        dialog!!.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.8).toInt(),  // 80% da largura da tela
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


    override fun onClick(p0: View?) {
        jogada(p0 as Button)

    }
}