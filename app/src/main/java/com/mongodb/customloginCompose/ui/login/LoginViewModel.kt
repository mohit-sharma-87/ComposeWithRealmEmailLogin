package com.mongodb.customloginCompose.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.mongodb.customloginCompose.BuildConfig
import com.mongodb.customloginCompose.ui.model.UserInfo
import io.realm.mongodb.App
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.functions.Functions

class LoginViewModel : ViewModel() {

    private val TAG: String = "LoginViewModel"
    private val realmSync: App = App(BuildConfig.RealmAppId)

    val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    private val _userInfo = MutableLiveData(UserInfo())
    val userInfo: LiveData<UserInfo> = _userInfo

    private fun login(userInfo: UserInfo) {
        val creds = Credentials.emailPassword(userInfo.email, userInfo.password)

        realmSync.loginAsync(creds) {
            if (it.isSuccess) {
                addUserInfo(userInfo = userInfo)
            }

        }
    }

    private fun addUserInfo(userInfo: UserInfo) {
        val user: User? = realmSync.currentUser()

        val functionsManager: Functions = realmSync.getFunctions(user)
        functionsManager.callFunctionAsync(
            "addUserInfo", listOf(userInfo.name, userInfo.email),
            String::class.java
        ) { result ->
            if (result.isSuccess) {
                _loginResult.value = true
                Log.v("EXAMPLE", "document id: ${result.get()}")
            } else {
                Log.e("EXAMPLE", "failed to call sum function with: " + result.error)
            }
        }
    }

    fun registerUser(userInfo: UserInfo) {
        realmSync.emailPassword.registerUserAsync(userInfo.email, userInfo.password) {
            // re-enable the buttons after user registration returns a result
            if (it.isSuccess) {
                //login(userInfo)


            } else {
                Log.i("LoginViewModel", "registered user failed")
            }
        }
    }

    fun onUserInfoUpdate(userInfo: UserInfo) {
        _userInfo.value = userInfo
    }


}