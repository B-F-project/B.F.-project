package com.example.stt_nlp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import com.example.stt_nlp.databinding.ActivityMainBinding
import com.example.stt_nlp.databinding.ActivityTranslationBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.delay

//view binding
private lateinit var binding : ActivityTranslationBinding
class TranslationActivity : AppCompatActivity(){

    val TAG = "TranslationActivity"

    // firebase storage 경로 설정
    val storage = Firebase.storage("gs://barrier-free-d309e.appspot.com/")

    // Create a storage reference from our app
    var storageRef = storage.reference

    //단어 테이블(index = path)
    val table: Array<String> = arrayOf("왼쪽", "더미", "오른쪽", "더미", "여기", //5
                                        "더미", "저기", "더미", "운전", "천천히", //5
                                        "더미", "더미", "더미", "빨리", "가다", //5
                                        "더미", "시간", "급하다", "더미", "더미") //5
    //path type for the funtion that get the gif from the firebase
    lateinit var pathType :String
    //pathNumber for the function that get the gif from the firebase
    var pathNumber: Int = 0
//    // Create a reference to a file from a Google Cloud Storage URI
//    val gsReference = storage.getReferenceFromUrl("gs://barrier-free-d309e.appspot.com/gif/고민.gif")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //결과수신 from Kkma
        val result = intent.getStringArrayExtra("resultArr")
        Log.d("result수신 확인", result!![0])

        binding.tanslationEntireContentTv.text = result.contentToString()

        binding.translationReturnToMainBtn.setOnClickListener {
            val returnToMainintent = Intent(this, MainActivity::class.java)
            startActivity(returnToMainintent)
        }

        for(i in result){
            binding.translationCurrentContentTv.text = i
            Log.d("translation_current_word_check",i)
            pathNumber = table.indexOf(i)+1
            binding.translationCurrentContentTv.text = i
            val BYTE: Long = 4096 * 4096
            Log.d("wait for next gif","1")

            getGifFromTheStorage(BYTE)

        }


    }

    private fun getGifFromTheStorage(
        BYTE: Long

    ) {
        lateinit var Ref: StorageReference
        lateinit var leftRef: StorageReference
        lateinit var rightRef: StorageReference
        pathType = "full"
        if(pathNumber < 10){
            Ref = storageRef.child(pathType+"/result00"+pathNumber.toString()+".gif")
        }
        else{
            Ref = storageRef.child(pathType+"/result0"+pathNumber.toString()+".gif")
        }
        Log.d("full translation done","1")
        Ref.getBytes(BYTE).addOnSuccessListener {
            startAnimation(binding.translationGifIv, it)
//            Glide.with(this)
//                .asGif()
//                .load(it)
//                .into(binding.translationGifIv)
            //setting the lefthand
            pathType = "left"
            if(pathNumber < 10){
                leftRef = storageRef.child(pathType+"/result00"+pathNumber.toString()+".gif")
            }
            else{
                leftRef = storageRef.child(pathType+"/result0"+pathNumber.toString()+".gif")
            }
            leftRef.getBytes(BYTE).addOnSuccessListener {
                startAnimation(binding.translationGifLeftHandIv, it)
//                Glide.with(this)
//                    .asGif()
//                    .load(it)
//                    .into(binding.translationGifLeftHandIv)
                Log.d("left translation done","1")
                //setting the right hand
                pathType = "right"
                if(pathNumber < 10){
                    rightRef = storageRef.child(pathType+"/result00"+pathNumber.toString()+".gif")
                }
                else{
                    rightRef = storageRef.child(pathType+"/result0"+pathNumber.toString()+".gif")
                }
                rightRef.getBytes(BYTE).addOnSuccessListener {
                    startAnimation(binding.translationGifRightHandIv, it)
//                    Glide.with(this)
//                        .asGif()
//                        .load(it)
//                        .into(binding.transitionGifRightHandIv)
                    Log.d("right translation done","1")

                }.addOnFailureListener {
                    //안돌아가면 erorr출력
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                //안돌아가면 erorr출력
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            //안돌아가면 erorr출력
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    fun startAnimation(actionImage: ImageView, src: ByteArray){

            actionImage.visibility = View.VISIBLE
            val listener= object:RequestListener<GifDrawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource!!.setLoopCount(1)
                    val callback = object : Animatable2Compat.AnimationCallback(){
                        override fun onAnimationEnd(drawable: Drawable?) {
                            //애니메이션 끝나고..
                            super.onAnimationEnd(drawable)
                        }

                    }
                    resource.registerAnimationCallback(callback)
                    return false
                }
            }
            val requestOptions = RequestOptions()
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
            requestOptions.skipMemoryCache(false)
            requestOptions.signature(ObjectKey(System.currentTimeMillis()))

            Glide.with(this).asGif().listener(listener).load(src).apply(
                requestOptions).into(actionImage)

        }
}