package my.edu.tarc.myaapplication.ui.donation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DonationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is donation Fragment"
    }
    val text: LiveData<String> = _text
}