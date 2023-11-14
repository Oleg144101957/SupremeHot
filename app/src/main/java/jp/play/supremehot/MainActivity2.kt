package jp.play.supremehot

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import jp.play.supremehot.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())


        binding.frame1.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        binding.frame2.setOnClickListener {
            finishAffinity()
        }

        binding.frame3.setOnClickListener {
            val intent = Intent(this, ScoresActivity::class.java)
            intent.putExtra(Const.DESTINATION_KEY, "file:///android_asset/scores.html")
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        //Do nothing
    }
}