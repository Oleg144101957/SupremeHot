package jp.play.supremehot

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.play.supremehot.domain.ElementState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    val score: MutableLiveData<Int> = MutableLiveData(0)

    val liveList: MutableLiveData<List<ElementState>> = MutableLiveData(listOf(
        ElementState(0, R.drawable.element1),
        ElementState(1, R.drawable.element2),
        ElementState(2, R.drawable.element4),
        ElementState(3, R.drawable.element5),
        ElementState(4, R.drawable.element6),
        ElementState(5, R.drawable.element1),
        ElementState(6, R.drawable.element2),
        ElementState(7, R.drawable.element4),
        ElementState(8, R.drawable.element5),
        ElementState(9, R.drawable.element6),
        ElementState(10, R.drawable.element1),
        ElementState(11, R.drawable.element2),
        ElementState(12, R.drawable.element4),
        ElementState(13, R.drawable.element5),
        ElementState(14, R.drawable.element6),
        ElementState(15, R.drawable.element1),
        ElementState(16, R.drawable.element2),
        ElementState(17, R.drawable.element4),
        ElementState(18, R.drawable.element5),
        ElementState(19, R.drawable.element6),
        ElementState(20, R.drawable.element1),
        ElementState(21, R.drawable.element2),
        ElementState(22, R.drawable.element4),
        ElementState(23, R.drawable.element5),
        ElementState(24, R.drawable.element6),
        ElementState(25, R.drawable.element1),
        ElementState(26, R.drawable.element2),
        ElementState(27, R.drawable.element4),
        ElementState(28, R.drawable.element5),
        ElementState(29, R.drawable.element6)
    ))

    fun leftMovement(current: Int){
        val newPosition = current - 1
        if (newPosition in 0..29){
            swapElements(current, newPosition)
        }

    }

    fun rightMovement(current: Int){
        val newPosition = current + 1
        if (newPosition in 0..29){
            swapElements(current, newPosition)
        }

    }

    fun downMovement(current: Int){
        val newPosition = current + 5
        if (newPosition in 0..29){
            swapElements(current, newPosition)
        }

    }

    fun upMovement(current: Int){
        val newPosition = current - 5
        if (newPosition in 0..29){
            swapElements(current, newPosition)
        }
    }

    private fun swapElements(currentPosition: Int, newPosition: Int){
        val elementCurrent = liveList.value?.get(newPosition)?.img
        val elementToSwap = liveList.value?.get(currentPosition)?.img

        val newListToPost = liveList.value?.map {element ->
            if (element.id == currentPosition){
                element.copy(img = elementCurrent!!)
            } else if(element.id == newPosition){
                element.copy(img = elementToSwap!!)
            } else {
                element
            }
        }


        viewModelScope.launch {
            delay(300)
            liveList.postValue(newListToPost!!)
            delay(200)
            check3inLine()
        }
    }

    private suspend fun check3inLine(){
        var firstRowTheSameCounter = 0
        var secondRowTheSameCounter = 0
        var thirdRowTheSameCounter = 0
        var fourthRowTheSameCounter = 0
        var fifthRowTheSameCounter = 0
        var sixthRowTheSameCounter = 0

        for (i in 1..4){
            if (liveList.value!![i].img == liveList.value!![i-1].img){
                firstRowTheSameCounter++
                if (firstRowTheSameCounter>=2){
                    delay(200)
                    score.value = score.value?.plus(1)
                }
            }
        }

        for (i in 6..9){
            if (liveList.value!![i].img == liveList.value!![i-1].img){
                secondRowTheSameCounter++
                if (secondRowTheSameCounter>=2){
                    delay(200)
                    score.value = score.value?.plus(1)
                }
            }
        }

        for (i in 11..14){
            if (liveList.value!![i].img == liveList.value!![i-1].img){
                thirdRowTheSameCounter++
                if (thirdRowTheSameCounter>=2){
                    delay(200)
                    score.value = score.value?.plus(1)
                }
            }
        }


        for (i in 16..19){
            if (liveList.value!![i].img == liveList.value!![i-1].img){
                fourthRowTheSameCounter++
                if (fourthRowTheSameCounter>=2){
                    delay(200)
                    score.value = score.value?.plus(1)
                }
            }
        }

        for (i in 21..24){
            if (liveList.value!![i].img == liveList.value!![i-1].img){
                fifthRowTheSameCounter++
                if (fifthRowTheSameCounter>=2){
                    delay(200)
                    score.value = score.value?.plus(1)
                }
            }
        }

        for (i in 26..29){
            if (liveList.value!![i].img == liveList.value!![i-1].img){
                sixthRowTheSameCounter++
                if (sixthRowTheSameCounter>=2){
                    delay(200)
                    score.value = score.value?.plus(1)
                }
            }
        }
    }
}