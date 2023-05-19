package my.edu.tarc.myaapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class User(
    val name: String? = "",
    val phone: String? = "",
    val email: String? = "",
    val password: String? = ""
)
