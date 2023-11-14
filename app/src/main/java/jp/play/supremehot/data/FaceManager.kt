package jp.play.supremehot.data

import android.content.Context
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData

class FaceManager(private val context: Context) {

    val storage = DataSaver(context)

    fun provideFaceData(){
        FacebookSdk.setApplicationId("707951467807616")
        FacebookSdk.setClientToken("bd3923227d3c6c13ac1c422dd9c56f7d")
        uploadFB()
    }

    private fun uploadFB() {
        AppLinkData.fetchDeferredAppLinkData(context){
            storage.saveData(it?.targetUri.toString())
        }
    }
}