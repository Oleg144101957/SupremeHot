package jp.play.supremehot.custom

import android.net.Uri
import android.webkit.ValueCallback

interface OnFileChooser {

    fun onFileChooseCallbackFun(param: ValueCallback<Array<Uri?>>)

}