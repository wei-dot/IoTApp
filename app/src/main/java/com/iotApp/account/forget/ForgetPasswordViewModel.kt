package com.iotApp.account.forget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.RegexUtils
import com.iotApp.R
import com.iotApp.account.data.AccountRepository
import com.iotApp.account.data.BaseResponse
import com.iotApp.account.data.SendEmail
import kotlinx.coroutines.launch

class ForgetPasswordViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    private val _forgetForm = MutableLiveData<ForgetPasswordFormState>()
    val loginFormState: LiveData<ForgetPasswordFormState> = _forgetForm

    val forgetPasswordResult: MutableLiveData<BaseResponse<Void>> = MutableLiveData()

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

    fun forgetPasswordDataChanged(email: String) {
        if (!isEmailValid(email)) {
            _forgetForm.value = ForgetPasswordFormState(emailError = R.string.invalid_email)
        } else {
            _forgetForm.value = ForgetPasswordFormState(isDataValid = true)
        }
    }

    private fun isEmailValid(username: String): Boolean = RegexUtils.isEmail(username)

}