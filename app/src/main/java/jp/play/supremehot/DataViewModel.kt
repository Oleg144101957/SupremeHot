package jp.play.supremehot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel : ViewModel() {

    private val _fbResponse: MutableLiveData<String> = MutableLiveData(Const.EMPTY)
    val fbResponse: LiveData<String> = _fbResponse

    private val _appsResponse: MutableLiveData<MutableMap<String, Any>?> = MutableLiveData()
    val appsResponse: LiveData<MutableMap<String, Any>?> = _appsResponse

    fun addFaceToDataVM(data: String){
        _fbResponse.value = data
    }

    fun addAppsToVM(map: MutableMap<String, Any>?){
        _appsResponse.value = map
    }
}