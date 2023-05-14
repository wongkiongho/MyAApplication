package my.edu.tarc.myaapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.database.*

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

        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Retrieve the user's data from the database
                val user = dataSnapshot.getValue(User::class.java)

                // Populate the EditText views with the user's data
                mEditName.setText(user?.name)
                mEditPhone.setText(user?.phone)
                mEditEmail.setText(user?.email)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
            }
        }

        // Add the ValueEventListener to the database reference
        mDatabase.child("qwk").addValueEventListener(userListener)

        mButton.setOnClickListener {
            // Save the user's edited data to the database
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
