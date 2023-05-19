package my.edu.tarc.myaapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mEmail : EditText
    private lateinit var mPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        val loginText: TextView = findViewById(R.id.textView_login_now)

        loginText.setOnClickListener {
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
        }

        val registerButton: Button = findViewById(R.id.button_register)
        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        mEmail = findViewById(R.id.editText_email_register)
        mPassword = findViewById(R.id.editText_password_register)

        registerButton.setOnClickListener {
            performSignUp()
        }

        //get email and password from user
    }

    private fun performSignUp() {
        val email = findViewById<EditText>(R.id.editText_email_register)
        val password = findViewById<EditText>(R.id.editText_password_register)

        if (email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val userId = it.uid
                        val email = it.email.toString()
                        val password = mPassword.text.toString().trim()

                        val childUpdates = HashMap<String, Any>()
                        childUpdates["email"] = email
                        childUpdates["password"] = password

                        mDatabase.child(userId.toString()).updateChildren(childUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "User registered successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "User failed to register",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        baseContext,
                        "Registration successful",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}


