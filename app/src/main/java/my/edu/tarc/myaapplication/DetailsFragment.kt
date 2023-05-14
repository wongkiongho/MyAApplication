package my.edu.tarc.myaapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class DetailsFragment : Fragment() {
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mEditName: EditText
    private lateinit var mEditPhone: EditText
    private lateinit var mEditEmail: EditText
    private lateinit var mButton: Button
    private lateinit var mButtonBack: Button

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
        mButtonBack = view.findViewById(R.id.buttonBack)

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
        val user = Firebase.auth.currentUser
        // Add the ValueEventListener to the database reference
        user?.let { mDatabase.child(it.uid).addValueEventListener(userListener) }

        mButton.setOnClickListener {
            val name = mEditName.text.toString().trim()
            val phone = mEditPhone.text.toString().trim()
            val email = mEditEmail.text.toString().trim()

            val user = User(name, phone, email)
            val userId = "qwk" // replace with the user ID you want to update

            mDatabase.child(userId).setValue(user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "User updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to update user", Toast.LENGTH_SHORT).show()
                }
            }
        }


        mButtonBack.setOnClickListener{
            findNavController().navigate(R.id.action_detailsFragment_to_nav_account)
        }
    }



}

data class User(
    val name: String? = "",
    val phone: String? = "",
    val email: String? = "",
)
