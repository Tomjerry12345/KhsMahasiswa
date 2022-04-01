package com.khsmahasiswa.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.khsmahasiswa.R
import com.khsmahasiswa.ui.autentikasi.AutentikasiActivity
import com.khsmahasiswa.utils.system.moveIntentToFinish

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            moveIntentToFinish(this, AutentikasiActivity())
        }, 2000)
    }
}