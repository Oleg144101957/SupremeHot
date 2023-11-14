package jp.play.supremehot.data

import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppsManager(private val context: Context) {

    suspend fun getApps() : MutableMap<String, Any>? = suspendCoroutine { continuation ->
        AppsFlyerLib.getInstance().init("ufZBuUqPCScCeWpBytPvm9", ConversationListener{
            continuation.resume(it)
        }, context).start(context)
    }
}

class ConversationListener(private val response: (MutableMap<String, Any>?) -> Unit): AppsFlyerConversionListener{

    override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
        response(p0)
    }

    override fun onConversionDataFail(p0: String?) {
        response(null)
    }

    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
        TODO("Not yet implemented")
    }

    override fun onAttributionFailure(p0: String?) {
        TODO("Not yet implemented")
    }
}