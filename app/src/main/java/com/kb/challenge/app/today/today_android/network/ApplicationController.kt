package com.kb.challenge.app.today.today_android.network

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.app.Activity

class ApplicationController : Application() {

    lateinit var networkService: NetworkService
    private var baseUrl = "https://today.apps.dev.clayon.io/api/" // 우리 주소


    companion object {
        var instance: ApplicationController? = null

        var globalApplicationContext: ApplicationController? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        globalApplicationContext = this
        buildNetwork()
    }

    fun buildNetwork() {

        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        networkService = retrofit.create(NetworkService::class.java)
    }




    /**
     * singleton 애플리케이션 객체를 얻는다.
     * @return singleton 애플리케이션 객체
     */


    /**
     * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
     */
    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }
}


