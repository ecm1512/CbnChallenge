package com.example.mbcchallenge.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mbcchallenge.R
import com.example.mbcchallenge.databinding.ActivityLoginBinding
import com.example.mbcchallenge.domain.utils.Response
import com.example.mbcchallenge.presentation.home.HomeActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {


    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.buttonLogin.setOnClickListener { handleLogin() }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.loginState.collectLatest {
                when(it) {
                    is Response.Success -> {
                        handleLoading(isLoading = false)
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                        //findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                    is Response.Error -> {
                        handleLoading(isLoading = false)
                        Snackbar.make(
                            applicationContext, binding.constraint,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Response.Loading -> handleLoading(isLoading = true)
                    else -> Unit
                }
            }
        }

    }
    private fun handleLogin() {
        val icon = AppCompatResources.getDrawable(this,R.drawable.ic_error)
        icon?.setBounds(0,0,icon.intrinsicWidth,icon.intrinsicHeight)

        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextPassword.text.toString()

        when{
            TextUtils.isEmpty(email.trim()) ->{
                binding.editTextTextEmailAddress.setError("Ingresa el correo",icon)
            }
            TextUtils.isEmpty(password.trim()) ->{
                binding.editTextTextPassword.setError("Ingresa la contraseña",icon)
            }

            email.isNotEmpty() && password.isNotEmpty() -> {
                viewModel.login(email.trim(), password.trim())
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                buttonLogin.text = ""
                buttonLogin.isEnabled = false
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
                buttonLogin.isEnabled = true
            }
        }
    }
}