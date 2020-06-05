package com.example.individualassignment.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.individualassignment.database.CustomPrayerRepository
import com.example.individualassignment.model.CustomPrayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditCustomPrayerViewModel(application: Application) : AndroidViewModel(application) {


    private val customPrayerRepository = CustomPrayerRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)


    val error = MutableLiveData<String?>()
    val success = MutableLiveData<Boolean>()


    fun updateCustomPrayer(customPrayer: CustomPrayer) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                customPrayerRepository.updatePrayer(customPrayer)
            }
        }
    }
}