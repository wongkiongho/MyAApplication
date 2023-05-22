package my.edu.tarc.myaapplication.ui.campaign

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class CampaignViewModel : ViewModel() {
    var title: String = ""
    var desc: String = ""
    var requireAmount: Int = 0
    var userId: String = ""
    var categories: String = ""
    var endDate: String = ""

    fun saveDataToFirebase(context: Context) {
        val mDatabase = FirebaseDatabase.getInstance().getReference("campaigns")
        val campaignRef = mDatabase.push() // Generate a unique key for the campaign

        // Create a HashMap with the campaign data
        val campaignData = hashMapOf(
                campaignRef.key to hashMapOf(
                    "title" to title,
                    "desc" to desc,
                    "requireAmount" to requireAmount,
                    "categories" to categories,
                    "endDate" to endDate,
                    "userId" to userId
                )
            )


        mDatabase.child(categories).updateChildren(campaignData as Map<String, Any>)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Campaign successfully registered",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Failed to register campaign",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
