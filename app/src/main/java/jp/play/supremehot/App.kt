package jp.play.supremehot

import android.app.Application
import com.onesignal.OneSignal

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setAppId("e0d62c88-0d89-480e-9565-278e3aa5115b")

    }
}