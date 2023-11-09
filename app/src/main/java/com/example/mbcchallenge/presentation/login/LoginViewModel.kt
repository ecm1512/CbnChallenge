package com.example.mbcchallenge.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbcchallenge.domain.usecases.LoginUseCase
import com.example.mbcchallenge.domain.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) :  ViewModel() {

    private val _loginState: MutableStateFlow<Response<Boolean>> = MutableStateFlow(Response.Finished)
    val loginState: StateFlow<Response<Boolean>> get() = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val response = loginUseCase.invoke(email, password)
            if(response){
                _loginState.value = Response.Success(true)
            }else{
                _loginState.value = Response.Error("Error")
            }
        }
    }
}