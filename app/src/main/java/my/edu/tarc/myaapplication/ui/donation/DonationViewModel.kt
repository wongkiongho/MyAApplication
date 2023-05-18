package my.edu.tarc.myaapplication.ui.donation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import my.edu.tarc.myaapplication.Donateuser

class DonationViewModel(application: Application) : AndroidViewModel(application) {
    var payment: Int? = null;
    var name: String? = null;


}