package jp.play.supremehot.data

import android.content.Context
import android.content.SharedPreferences
import jp.play.supremehot.Const

class DataSaver(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(Const.SHARED_NAME, Context.MODE_PRIVATE)

    fun saveData(input: String){

        val currentData = sharedPreferences.getString(Const.DESTINATION_KEY, Const.EMPTY) ?: Const.EMPTY

        if (currentData != Const.ATTENTION){
            sharedPreferences.edit().putString(Const.DESTINATION_KEY, input).apply()
        }
    }

    fun getData(): String {
        return sharedPreferences.getString(Const.DESTINATION_KEY, Const.EMPTY) ?: Const.EMPTY
    }
}