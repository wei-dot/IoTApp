package com.iotApp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iotApp.R
import com.iotApp.repository.AccountRepository
import com.iotApp.api.BaseResponse
import com.iotApp.api.data.SetPassword
import com.iotApp.view.account.set.setPasswordFormState
import kotlinx.coroutines.launch

class SetPasswordViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    private val _setPasswordForm = MutableLiveData<setPasswordFormState>()
    val setPasswordFormState: LiveData<setPasswordFormState> = _setPasswordForm

    val setPasswordResult: MutableLiveData<BaseResponse<Void>> = MutableLiveData()

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

    fun setPasswordDataChanged(newPassword: String, confirmPassword: String) {
        if (!isPasswordMatch(newPassword, confirmPassword)) {
            _setPasswordForm.value = setPasswordFormState(passwordError = R.string.invalid_match)
        } else {
            _setPasswordForm.value = setPasswordFormState(isDataValid = true)
        }
    }


    private fun isPasswordMatch(password: String, password2: String): Boolean =
        password == password2


}