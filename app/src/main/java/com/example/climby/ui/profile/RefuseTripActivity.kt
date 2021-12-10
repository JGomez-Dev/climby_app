package com.example.climby.ui.profile

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.example.climby.R
import com.example.climby.databinding.ActivityRefuseTripBinding
import com.example.climby.ui.profile.viewmodel.RefuseTripViewModel

class RefuseTripActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRefuseTripBinding
    private lateinit var refuseTripViewModel: RefuseTripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refuseTripViewModel = ViewModelProvider(this).get(RefuseTripViewModel::class.java)
        binding = ActivityRefuseTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.IVBack.setOnClickListener {
            onBackPressed()
            /*generateNotification()*/
        }
        /*createNotificationChannel()*/
    }
}