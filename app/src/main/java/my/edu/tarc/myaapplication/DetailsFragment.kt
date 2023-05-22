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
import com.google.firebase.auth.FirebaseAuth
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
    private lateinit var authListener: FirebaseAuth.AuthStateListener
    val currentUser = Firebase.auth.currentUser


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

        val userId = currentUser?.uid.toString()


        mDatabase.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)

                user?.let {
                    mEditName.setText(it.name)
                    mEditPhone.setText(it.phone)
                    mEditEmail.setText(it.email)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
            }
        })





        mButton.setOnClickListener {
            val name = mEditName.text.toString().trim()
            val phone = mEditPhone.text.toString().trim()
            val email = mEditEmail.text.toString().trim()
            val userId = currentUser?.uid

            val childUpdates = HashMap<String, Any>()
            childUpdates["name"] = name
            childUpdates["phone"] = phone
            childUpdates["email"] = email
            currentUser?.updateEmail(email)
            mDatabase.child(userId.toString()).updateChildren(childUpdates)
                .addOnCompleteListener { dbTask ->
                    if (dbTask.isSuccessful) {
                        Toast.makeText(requireContext(), "User details updated successfully", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(requireContext(), "Failed to update user details", Toast.LENGTH_SHORT).show()
                    }
                }
        }







        mButtonBack.setOnClickListener{
            findNavController().navigate(R.id.action_detailsFragment_to_nav_account)
        }
    }

    // Function to update email and user details in Firebase


}



