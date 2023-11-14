package jp.play.supremehot.custom

import android.content.Context
import android.os.Message
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout

class PaymentView(context: Context) : WebView(context) {

    fun initPaymentView(root: FrameLayout){
        configSettings(settings)
        webViewClient = provideWVC()
        webChromeClient = provideWCC(root)
    }

    private fun provideWVC(): WebViewClient{
        return object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                CookieManager.getInstance().flush()
            }
        }
    }

    private fun provideWCC(root: FrameLayout): WebChromeClient{
        return object : WebChromeClient(){
            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
            ): Boolean {

                val newScoreView = PaymentView(context)
                newScoreView.initPaymentView(root)
                root.addView(newScoreView)
                val trans = resultMsg?.obj as WebView.WebViewTransport
                trans.webView = newScoreView
                resultMsg.sendToTarget()

                return true
            }

            override fun onCloseWindow(window: WebView?) {
                super.onCloseWindow(window)
                root.removeView(window)
            }
        }
    }


    private fun configSettings(settings: WebSettings) {
        with(settings){
            setRenderPriority(WebSettings.RenderPriority.HIGH)
            allowContentAccess = true
            useWideViewPort = true
            mediaPlaybackRequiresUserGesture = true
            pluginState = WebSettings.PluginState.ON
            cacheMode = WebSettings.LOAD_NORMAL
            loadsImagesAutomatically = true
            mixedContentMode = 0
            setEnableSmoothTransition(true)
            databaseEnabled = true
            savePassword = true
            allowUniversalAccessFromFileURLs = true
            saveFormData = true
            allowFileAccess = true
            javaScriptCanOpenWindowsAutomatically = true
            allowFileAccessFromFileURLs = true
            setSupportMultipleWindows(true)
            loadWithOverviewMode = true
            domStorageEnabled = true
            setJavaScriptEnabled(true)
            userAgentString = userAgentString.customReplacer()
        }
    }


    private fun String.customReplacer(): String{
        val w = "w"
        val v = "v"
        return this.replace(w+v, "")
    }

}