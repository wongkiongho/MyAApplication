package my.edu.tarc.myaapplication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailsFragment : Fragment() {
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mEditName: EditText
    private lateinit var mEditPhone: EditText
    private lateinit var mEditEmail: EditText
    private lateinit var mButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        mEditName = view.findViewById(R.id.editTextTextPersonName2)
        mEditPhone = view.findViewById(R.id.editTextPhone)
        mEditEmail = view.findViewById(R.id.editTextTextEmailAddress)
        mButton = view.findViewById(R.id.button3)

        mButton.setOnClickListener {
            val name = mEditName.text.toString()
            val phone = mEditPhone.text.toString()
            val email = mEditEmail.text.toString()
            val user = User(name, phone, email)
            mDatabase.child(name).setValue(user)
        }
    }
}

data class User(
    val name: String? = "",
    val phone: String? = "",
    val email: String? = ""
)