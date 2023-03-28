package com.example.stt_nlp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stt_nlp.databinding.ActivityMainBinding
import com.example.stt_nlp.databinding.ActivityTestmainBinding


//view binding
private lateinit var binding : ActivityTestmainBinding
class TestMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestmainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val result_string = binding.testMainResultTv.text
        binding.testMainSendBtn.setOnClickListener {
            var sendString = result_string
            var intent = Intent(this@TestMain, Kkma::class.java)
            intent.putExtra("analyizeM",sendString)
            startActivity(intent)
            finish()
        }
    }
}
