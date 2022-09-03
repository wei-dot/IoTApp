package com.iotApp.account.resend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iotApp.account.data.AccountRepository
import com.iotApp.account.data.BaseResponse
import com.iotApp.account.data.SendEmail
import kotlinx.coroutines.launch

class ResendViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    val resendResult: MutableLiveData<BaseResponse<Void>> = MutableLiveData()

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

}