package com.kb.choco.util.extensions

import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class ConnectivityInterceptor @Inject constructor(
    private val cm: ConnectivityManager
)  :  Interceptor {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (cm.isConnected()) {
            return chain.proceed(chain.request())
        }
        throw IOException()
    }

}