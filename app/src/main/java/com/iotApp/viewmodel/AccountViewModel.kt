package com.iotApp.viewmodel

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.RegexUtils
import com.google.android.material.textfield.TextInputLayout
import com.iotApp.R
import com.iotApp.api.BaseResponse
import com.iotApp.formstate.AccountFormState
import com.iotApp.model.*
import com.iotApp.repository.AccountRepository
import kotlinx.coroutines.launch

class AccountViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    private val _accountForm = MutableLiveData<AccountFormState>()
    val accountFormState: LiveData<AccountFormState> = _accountForm


    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
    val forgetPasswordResult: MutableLiveData<BaseResponse<Void>> = MutableLiveData()
    val logoutResult: MutableLiveData<BaseResponse<Void>> = MutableLiveData()
    val resendResult: MutableLiveData<BaseResponse<Void>> = MutableLiveData()
    val setPasswordResult: MutableLiveData<BaseResponse<Void>> = MutableLiveData()
    val signupResult: MutableLiveData<BaseResponse<UserInfo>> = MutableLiveData()

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


    fun forgetPassword(email: String) {
        forgetPasswordResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val forgetPasswordRequest = SendEmail(email)
                val response = accountRepository.forgotPassword(forgetPasswordRequest)
                if (response != null) {
                    if (response.isSuccessful) {
                        forgetPasswordResult.value = BaseResponse.Success(response.body())
                    } else {
                        forgetPasswordResult.value = BaseResponse.Error(response.message())
                    }
                }
            } catch (e: Exception) {
                forgetPasswordResult.value = BaseResponse.Error(e.message)
            }
        }

    }

    fun resendEmail(email: String) {
        resendResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val resendRequest = SendEmail(email)
                val response = accountRepository.resendActivationEmail(resendRequest)
                if (response != null) {
                    if (response.isSuccessful) {
                        resendResult.value = BaseResponse.Success(response.body())
                    } else {
                        resendResult.value = BaseResponse.Error(response.message())
                    }
                }

            } catch (ex: Exception) {
                resendResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun setPassword(
        token: String,
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ) {
        setPasswordResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val setPasswordRequest = SetPassword(oldPassword, newPassword, confirmPassword)
                val response = accountRepository.setPassword(token, setPasswordRequest)
                if (response != null) {
                    if (response.isSuccessful) {
                        setPasswordResult.value = BaseResponse.Success(response.body())
                    } else {
                        setPasswordResult.value = BaseResponse.Error(response.message())
                    }
                }

            } catch (ex: Exception) {
                setPasswordResult.value = BaseResponse.Error(ex.message)
            }
        }

    }

    fun signup(username: String, password: String, passwordConfirm: String, email: String) {
        signupResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val signupRequest = UserInfo(username, password, passwordConfirm, email)
                val response = accountRepository.signup(signupRequest)
                if (response != null) {
                    if (response.isSuccessful) {
                        signupResult.value = BaseResponse.Success(response.body())
                    } else {
                        signupResult.value = BaseResponse.Error(response.message())
                    }
                }
            } catch (ex: Exception) {
                signupResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun forgetPasswordDataChanged(email: String) {
        if (!isEmailValid(email)) {
            _accountForm.value = AccountFormState(emailError = R.string.invalid_email)
        } else {
            _accountForm.value = AccountFormState(isDataValid = true)
        }
    }

    fun loginDataChanged(email: String, password: String) {
        if (!isEmailValid(email)) {
            _accountForm.value = AccountFormState(usernameError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _accountForm.value = AccountFormState(passwordError = R.string.invalid_password)
        } else {
            _accountForm.value = AccountFormState(isDataValid = true)
        }
    }

    fun setPasswordDataChanged(newPassword: String, confirmPassword: String) {
        if (!isPasswordMatch(newPassword, confirmPassword)) {
            _accountForm.value = AccountFormState(passwordError = R.string.invalid_match)
        } else {
            _accountForm.value = AccountFormState(isDataValid = true)
        }
    }

    fun signupDataChanged(
        username: String,
        password: String,
        passwordConfirm: String,
        email: String
    ) {
        if (!isUserNameValid(username)) {
            _accountForm.value = AccountFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _accountForm.value = AccountFormState(passwordError = R.string.invalid_password)
        } else if (!isPasswordMatch(password, passwordConfirm)) {
            _accountForm.value = AccountFormState(passwordError = R.string.invalid_match)
        } else if (!isEmailValid(email)) {
            _accountForm.value = AccountFormState(emailError = R.string.invalid_email)
        } else {
            _accountForm.value = AccountFormState(isDataValid = true)
        }

    }


    private fun isEmailValid(email: String): Boolean = RegexUtils.isEmail(email)
    private fun isUserNameValid(username: String): Boolean = RegexUtils.isUsername(username)
    private fun isPasswordValid(password: String): Boolean = password.length > 5
    private fun isPasswordMatch(password: String, password2: String): Boolean =
        password == password2

}

fun TextInputLayout.afterTextChanged(afterTextChanged: (String) -> Unit) {
    editText?.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

