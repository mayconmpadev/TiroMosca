package com.mpasistemas.tiromosca.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mpasistemas.tiromosca.R
import com.mpasistemas.tiromosca.activity.MainActivity
import com.mpasistemas.tiromosca.activity.RankingActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Token atualizado: $token")
        // Envie o token atualizado para seu servidor de backend aqui, se necessário
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Verifique se a mensagem contém dados de notificação
        remoteMessage.notification?.let {
            val title = it.title ?: "Notificação"
            val body = it.body ?: "Você tem uma nova mensagem."

            // Exiba a notificação
            showNotification(title, body)
        }
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "my_channel_id"
        val notificationId = 1001

        // Crie um intent para abrir o aplicativo quando a notificação for clicada
        val intent = Intent(this, RankingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Crie o canal de notificação (para Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Meu Canal de Notificação"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Canal para notificações do app"
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Crie a notificação
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.alvo) // Ícone da notificação
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // Exiba a notificação
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            notify(notificationId, notification)
        }
    }
}
