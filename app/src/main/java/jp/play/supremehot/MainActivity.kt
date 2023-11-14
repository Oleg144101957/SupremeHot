package jp.play.supremehot

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.WindowInsets
import android.view.animation.LinearInterpolator
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import jp.play.supremehot.data.AppsManager
import jp.play.supremehot.data.DataSaver
import jp.play.supremehot.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataSaver: DataSaver

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())

        val rotationAnimator = ObjectAnimator.ofFloat(binding.loadingWheel, "rotation", 0f, 360f)
        rotationAnimator.duration = 1500 // Set the duration of the animation (in milliseconds)
        rotationAnimator.repeatCount = ObjectAnimator.INFINITE // Set the repeat count for infinite rotation
        rotationAnimator.interpolator = LinearInterpolator() // Set the interpolator for smooth rotation

        rotationAnimator.start()

        dataSaver = DataSaver(this)

        val currentDestination = dataSaver.getData()

        when(currentDestination){
            Const.EMPTY -> {
                //First time
                //set observers, ask apps and fb and listen to response
                //ADB check
                val checkResult = Settings.Global.getString(contentResolver, Settings.Global.ADB_ENABLED)
                if (checkResult != "1"){
                    setObserversAndAskForTrack()
                }

                val intentToMenu = Intent(this, MainActivity2::class.java)

                lifecycleScope.launch {
                    delay(2000)
                    startActivity(intentToMenu)
                }
            }

            Const.ATTENTION -> {
                //go to the game
                val intentToMainActivity3 = Intent(this, MainActivity2::class.java)
                startActivity(intentToMainActivity3)
            }

            else -> {
                val intentToScores = Intent(this, ScoresActivity::class.java)
                intentToScores.putExtra(Const.DESTINATION_KEY, currentDestination)
                startActivity(intentToScores)
            }
        }


        lifecycleScope.launch {
            printApps()
        }
    }

    private fun setObserversAndAskForTrack() {
        //set observers and ask for track
    }

    fun printAID(){
        val aid = AdvertisingIdClient.getAdvertisingIdInfo(this).id.toString()
    }

    fun printApps(){
        val apps = AppsManager(this)

        lifecycleScope.launch {
            val response = apps.getApps()
        }
    }

    override fun onBackPressed() {
        //Do nothing
    }
}