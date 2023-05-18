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

class ChangepasswordFragment : Fragment() {
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mCurrentPassword: EditText
    private lateinit var mNewPassword: EditText
    private lateinit var mConfirmPassword: EditText
    private lateinit var mButton: Button
    private lateinit var mButtonBack: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_changepassword, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = Firebase.auth.currentUser

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        mCurrentPassword = view.findViewById(R.id.editTextTextPassword)
        mNewPassword = view.findViewById(R.id.editTextTextPassword3)
        mConfirmPassword = view.findViewById(R.id.editTextTextPassword4)
        mButton = view.findViewById(R.id.button4)
        mButtonBack = view.findViewById(R.id.buttonBack1)

        mButtonBack.setOnClickListener {
            findNavController().navigate(R.id.action_changepasswordFragment_to_nav_account)
        }

        mButton.setOnClickListener {
            val currentUser = user?.let { it1 -> mDatabase.child(it1.uid) }
            currentUser?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Get the User object from the snapshot
                    val user = snapshot.getValue(User::class.java)

                    // Check if the entered password matches the one in Firebase
                    if (user != null && user.password == mCurrentPassword.text.toString()) {
                        // Passwords match, update the password field in Firebase
                        if (mNewPassword.text.toString() == mConfirmPassword.text.toString()) {
                            currentUser.child("password").setValue(mNewPassword.text.toString())
                            // Display success message
                            Toast.makeText(
                                requireContext(), "Successful changed password",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            // Display error message for new password and confirm password not matching
                        }
                    } else {
                        // Display error message for incorrect password
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }

    }

    data class User(
        val name: String? = "",
        val phone: String? = "",
        val email: String? = "",
        val password: String? = ""
    )
}