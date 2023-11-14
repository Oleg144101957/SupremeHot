package jp.play.supremehot.custom

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Message
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.result.ActivityResultLauncher
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ScoresView(private val context: Context, private val onFileChoseCallback: OnFileChooser) : WebView(context) {

    fun initCustomView(content: ActivityResultLauncher<String>, root: FrameLayout){
        configSettings(settings)
        setWebContentsDebuggingEnabled(true)
        isSaveEnabled = true
        isFocusableInTouchMode = true
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        webViewClient = provideWVC()
        webChromeClient = provideWCC(content, root)
    }

    private fun provideWVC(): WebViewClient{
        val scope = MainScope()

        return object: WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                scope.launch {
                    CookieManager.getInstance().flush()
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                scope.launch {
                    CookieManager.getInstance().flush()
                }
            }
        }
    }

    private fun provideWCC(content: ActivityResultLauncher<String>, root: FrameLayout): WebChromeClient{
        return object : WebChromeClient(){
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri?>>,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                onFileChoseCallback.onFileChooseCallbackFun(filePathCallback)
                content.launch("image/*")
                return true
            }

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