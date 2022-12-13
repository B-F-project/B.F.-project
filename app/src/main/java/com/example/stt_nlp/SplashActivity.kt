package com.example.stt_nlp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.stt_nlp.databinding.ActivityMainBinding
import com.example.stt_nlp.databinding.ActivitySplashBinding

private lateinit var binding : ActivitySplashBinding

class SplashActivity: AppCompatActivity() {
    private val tag = "ACT/SPLASH"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Log.d(tag, "initAfterBinding()")

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}