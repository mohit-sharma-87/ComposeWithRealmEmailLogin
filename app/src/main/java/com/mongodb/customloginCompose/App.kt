package com.mongodb.customloginCompose

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.log.LogLevel
import io.realm.log.RealmLog
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

class App : Application() {

    val realmSync by lazy {
        App(AppConfiguration.Builder(BuildConfig.RealmAppId).build())
    }


    override fun onCreate() {
        super.onCreate()
        setupRealm()
    }

    private fun setupRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("rChatDb.db")
            .allowQueriesOnUiThread(false)
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)

        if (BuildConfig.DEBUG)
            RealmLog.setLevel(LogLevel.ALL)
    }
}