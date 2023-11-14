package jp.play.supremehot

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.webkit.ValueCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import jp.play.supremehot.custom.OnFileChooser
import jp.play.supremehot.custom.ScoresView
import jp.play.supremehot.databinding.ActivityScoresBinding

class ScoresActivity : AppCompatActivity() {

    private lateinit var chooseCallback: ValueCallback<Array<Uri?>>
    private val getContent = registerForActivityResult(ActivityResultContracts.GetMultipleContents()){
        chooseCallback.onReceiveValue(it.toTypedArray())
    }
    private lateinit var scoresView: ScoresView
    private lateinit var binding: ActivityScoresBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())

        scoresView = ScoresView(this, object : OnFileChooser{
            override fun onFileChooseCallbackFun(param: ValueCallback<Array<Uri?>>) {
                chooseCallback = param
            }
        })

        binding.root.addView(scoresView)
        scoresView.initCustomView(getContent, binding.root)
        val destination = intent.getStringExtra(Const.DESTINATION_KEY)
        scoresView.loadUrl(destination!!)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bundle = Bundle()
        scoresView.saveState(bundle)
        outState.putBundle(Const.BUNDLE_KEY, bundle)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        scoresView.restoreState(savedInstanceState)
    }
}