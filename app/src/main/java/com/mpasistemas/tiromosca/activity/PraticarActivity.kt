package com.mpasistemas.tiromosca.activity

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpasistemas.tiromosca.R
import com.mpasistemas.tiromosca.adapter.jogadasAdapter
import com.mpasistemas.tiromosca.databinding.ActivityPraticarBinding
import com.mpasistemas.tiromosca.modelo.Jogadas
import com.mpasistemas.tiromosca.util.Util

class PraticarActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityPraticarBinding
    private lateinit var timer: CountDownTimer
    private lateinit var countDownTimer: CountDownTimer
    private var repeatTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 20000 // 10 minutos em milissegundos
    private var tenSecondsWarningShown = false
    private var timerRunning = false
    var apertados: ArrayList<Button> = ArrayList()
    val numAleatorio: String = Util.numeroAleatorio()
    var lista: ArrayList<Jogadas> = ArrayList()
    lateinit var jogada: Jogadas
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding.rvJogadas.adapter = jogadasAdapter(lista)

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
            pauseTimer()
            binding.textNumeroAleatorio.text = binding.textJogada.text

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

    }

    private fun startTimer() {

        val intent = Intent(this, GamerOverActivity::class.java)
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 10) { // Atualiza a cada 10 milissegundos
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
                startActivity(intent)
            }
        }.start()
        timerRunning = true;
    }

    private fun updateCountDownText() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        val milliseconds = (timeLeftInMillis % 1000) / 10
        val timeFormatted = String.format("%02d:%02d.%02d", minutes, seconds, milliseconds)
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
    }

    override fun onClick(p0: View?) {
        jogada(p0 as Button)

    }
}