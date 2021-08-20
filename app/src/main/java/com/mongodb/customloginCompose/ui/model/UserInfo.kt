package com.mongodb.customloginCompose.ui.model

import io.realm.RealmObject


data class UserInfo(
    var email: String = "",

    var name: String = "",

    var password: String = ""
)