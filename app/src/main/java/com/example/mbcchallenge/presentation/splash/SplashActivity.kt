package com.example.mbcchallenge.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.example.mbcchallenge.R
import com.example.mbcchallenge.databinding.ActivitySplashBinding
import com.example.mbcchallenge.presentation.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.ivLogo.startAnimation(slideAnimation)

        lifecycleScope.launch {
            delay(1000)
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }
}