package com.mongodb.customloginCompose.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mongodb.customloginCompose.R
import com.mongodb.customloginCompose.ui.model.UserInfo
import com.mongodb.customloginCompose.ui.theme.CustomLoginRealmComposeTheme
import io.realm.mongodb.User

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomLoginRealmComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    loginState()
                }
            }
        }
    }

    @Composable
    fun loginState(userModel: LoginViewModel = viewModel()) {
        val userInfo = userModel.userInfo.observeAsState()

        LoginScreen(
            userInfo = userInfo.value!!,
            onSignInfoUpdate = { userModel.onUserInfoUpdate(it) }
        )
    }

    @Composable
    fun LoginScreen(
        userInfo: UserInfo,
        onSignInfoUpdate: (UserInfo) -> Unit
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_realm_logo),
                contentDescription = stringResource(id = R.string.cd_realm_logo),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp, bottom = 20.dp),
                alignment = Alignment.Center
            )

            OutlinedTextField(
                value = userInfo.name,
                onValueChange = {
                    val info = userInfo.apply { name = it }
                    onSignInfoUpdate(info)
                },
                label = { Text(text = "Name") },
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = userInfo.email,
                onValueChange = {
                    onSignInfoUpdate(userInfo.apply { email = it })
                },
                label = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = userInfo.password,
                onValueChange = {
                    onSignInfoUpdate(userInfo.apply { password = it })
                },
                label = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                content = { Text(text = "Sign Up") },
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}

