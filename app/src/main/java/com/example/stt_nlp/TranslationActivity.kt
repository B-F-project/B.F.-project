package com.example.stt_nlp


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.stt_nlp.databinding.ActivityTranslationBinding
import com.google.firebase.FirebaseApp

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

//view binding
private lateinit var binding : ActivityTranslationBinding

class TranslationActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val receivedStringList = intent.getStringArrayListExtra("resultArr")

        val receivedString = receivedStringList!!.joinToString(separator = " ")
        binding.tanslationEntireContentTv.text = receivedString

        // Firebase 초기화
        //google-services.json을 사용해서 따로 config설정을 안해줘도 됨
        FirebaseApp.initializeApp(this@TranslationActivity)

        // Storage 서비스 생성
        val storage = Firebase.storage("gs://barrier-free-d309e.appspot.com")

        var storageRef = storage.reference
        var videoRef = storageRef.child("video")

        // 모든 파일 목록 가져오기
        videoRef.listAll().addOnSuccessListener { listResult ->
            // 파일 목록 중에서 word.mp4 형식인 파일만 선택
            val filteredList = listResult.items.filter { it.name.endsWith("시간"+".mp4") }

            // 일치하는 파일이 없으면 에러 처리
            if (filteredList.isEmpty()) {
                Log.e("TAG", "No matching file found for 운전.mp4")
                return@addOnSuccessListener
            }

            // 첫 번째 일치하는 파일을 선택하여 동영상 다운로드 URL 가져오기
            val videoRef = filteredList[0]
            videoRef.downloadUrl.addOnSuccessListener { uri ->
                // 동영상을 로드하는 코드 작성
                val videoView = binding.translationGifVv
                videoView.setVideoURI(uri)
                videoView.start()
            }.addOnFailureListener { exception ->
                // 에러 처리 코드 작성
                Log.e("TAG", "Error: $exception")
            }
        }.addOnFailureListener { exception ->
            // 에러 처리 코드 작성
            Log.e("TAG", "Error: $exception")
        }


    }
}