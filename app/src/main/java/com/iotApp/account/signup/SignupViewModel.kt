package com.iotApp.account.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.RegexUtils
import com.iotApp.R
import com.iotApp.account.data.AccountRepository
import com.iotApp.account.data.BaseResponse
import com.iotApp.account.data.UserInfo
import kotlinx.coroutines.launch

class SignupViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    private val _signupForm = MutableLiveData<SignupFormState>()
    val signupFormState: LiveData<SignupFormState> = _signupForm

    val signupResult: MutableLiveData<BaseResponse<UserInfo>> = MutableLiveData()

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


    fun signupDataChanged(username: String, password: String, passwordConfirm: String, email: String) {
        if (!isUserNameValid(username)) {
            _signupForm.value = SignupFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _signupForm.value = SignupFormState(passwordError = R.string.invalid_password)
        } else if (!isPasswordMatch(password, passwordConfirm)) {
            _signupForm.value = SignupFormState(passwordError = R.string.invalid_match)
        } else if (!isEmailValid(email)) {
            _signupForm.value = SignupFormState(emailError = R.string.invalid_email)
        } else {
            _signupForm.value = SignupFormState(isDataValid = true)
        }

    }

    private fun isEmailValid(email: String): Boolean = RegexUtils.isEmail(email)
    private fun isUserNameValid(username: String): Boolean = RegexUtils.isUsername(username)

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean = password.length > 5
    private fun isPasswordMatch(password: String, password2: String): Boolean =
        password == password2
}