package com.iotApp.account.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.RegexUtils
import com.iotApp.R
import com.iotApp.account.data.AccountRepository
import com.iotApp.account.data.BaseResponse
import com.iotApp.account.data.LoginRequest
import com.iotApp.account.data.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()


    val logoutResult: MutableLiveData<BaseResponse<Void>> = MutableLiveData()

    fun loginUser(email: String, password: String) {
        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email, password)
                val response = accountRepository.login(loginRequest)
                if (response != null) {
                    if (response.isSuccessful) {
                        loginResult.value = BaseResponse.Success(response.body())
                    } else {
                        loginResult.value = BaseResponse.Error(response.message())
                    }
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun loginDataChanged(email: String, password: String) {
        if (!isEmailValid(email)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isEmailValid(username: String): Boolean = RegexUtils.isEmail(username)


    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean = password.length > 5

    fun logoutUser(logoutRequest: String) {
        logoutResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = accountRepository.logout(logoutRequest)
                if (response != null) {
                    if (response.isSuccessful) {
                        logoutResult.value = BaseResponse.Success(response.body())
                    } else {
                        logoutResult.value = BaseResponse.Error(response.message())
                    }
                }

            } catch (ex: Exception) {
                logoutResult.value = BaseResponse.Error(ex.message)
            }
        }

    }


}