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
            val animatorZ: ValueAnimator = ObjectAnimator.ofFloat(madCamp, "translationZ", 100f, 500f)

            animatorZ.duration = 1000
            animatorZ.start()

             val animatorX: ValueAnimator = ObjectAnimator.ofFloat(madCamp, "translationX", 0f, 200f)
             animatorX.duration = 1000

             val animatorY: ValueAnimator = ObjectAnimator.ofFloat(madCamp, "translationY", 0f, 200f)
             animatorY.duration = 1000

             val animatorSet = AnimatorSet()
             animatorSet.playTogether(animatorZ, animatorX, animatorY)
             animatorSet.start()
        }
        moveToFront()


        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}