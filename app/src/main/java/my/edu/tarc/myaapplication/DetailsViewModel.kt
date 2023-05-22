package my.edu.tarc.myaapplication

import androidx.lifecycle.ViewModel

data class User(
    val name: String? = "",
    val phone: String? = "",
    val email: String? = "",
    val password : String? = "",
)
class DetailsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    //hold current data
}