package com.heewoong.threebasicscreens

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView

class splashActivity : AppCompatActivity() {
    private lateinit var madCamp: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        madCamp = findViewById(R.id.madCamp)
         fun moveToFront() {

             val scaleXAnimator: ValueAnimator = ObjectAnimator.ofFloat(madCamp, "scaleX", 1f, 4f)
             scaleXAnimator.duration = 3000

             val scaleYAnimator: ValueAnimator = ObjectAnimator.ofFloat(madCamp, "scaleY", 1f, 4f)
             scaleYAnimator.duration = 3000

             val animatorSet = AnimatorSet()
             animatorSet.playTogether( scaleXAnimator, scaleYAnimator)
             animatorSet.start()
        }
        moveToFront()


        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}