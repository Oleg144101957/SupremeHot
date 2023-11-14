package jp.play.supremehot

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import jp.play.supremehot.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {

    //Game

    private lateinit var binding: ActivityMain3Binding
    private val gameViewModel by viewModels<GameViewModel>()
    private lateinit var listOfElements: List<ImageView>

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())

        listOfElements = listOf(
            binding.image1,
            binding.image2,
            binding.image3,
            binding.image4,
            binding.image5,
            binding.image6,
            binding.image7,
            binding.image8,
            binding.image9,
            binding.image10,
            binding.image11,
            binding.image12,
            binding.image13,
            binding.image14,
            binding.image15,
            binding.image16,
            binding.image17,
            binding.image18,
            binding.image19,
            binding.image20,
            binding.image21,
            binding.image22,
            binding.image23,
            binding.image24,
            binding.image25,
            binding.image26,
            binding.image27,
            binding.image28,
            binding.image29,
            binding.image30
        )

        gameViewModel.liveList.observe(this){ listFromVM ->
            listFromVM.forEach {
                listOfElements[it.id].setImageResource(listFromVM[it.id].img)
            }
        }

        gameViewModel.score.observe(this){score ->
            binding.textScore.text = "Your score is $score"
        }

        setListeners()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners(){
        var startX = 0f
        var startY = 0f
        val MOVE_T = 100

        for (i in 0..29){
            listOfElements[i].setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        // Store the initial touch position
                        startX = event.x
                        startY = event.y
                        true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val deltaX = event.x - startX
                        val deltaY = event.y - startY

                        if (Math.abs(deltaX) > MOVE_T) {
                            if (deltaX > 0) {
                                Log.d("123123", "Move Right")
                                // Move to the right
                                // Add your code here for right movement
                                gameViewModel.rightMovement(i)
                            } else {
                                Log.d("123123", "Move Left")
                                gameViewModel.leftMovement(i)
                                // Move to the left
                                // Add your code here for left movement
                            }
                        }

                        if (Math.abs(deltaY) > MOVE_T) {
                            if (deltaY > 0) {
                                gameViewModel.downMovement(i)
                                // Move down
                                // Add your code here for down movement
                            } else {
                                gameViewModel.upMovement(i)
                                // Move up
                                // Add your code here for up movement
                            }
                        }

                        true
                    }
                    else -> false
                }
            }
        }

    }
}