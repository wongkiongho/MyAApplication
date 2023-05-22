//eventFragment
package my.edu.tarc.myaapplication.ui.campaign

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import my.edu.tarc.myaapplication.R
import my.edu.tarc.myaapplication.databinding.FragmentEventBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage


class EventFragment : Fragment() {

    // private var _binding: FragmentEventBinding? = null
    // private val binding get() = _binding!!
    // private val CampaignFragment: CampaignViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater.inflate(R.layout.fragment_event, container, false)

        // Inflate the layout for this fragment
        val binding = FragmentEventBinding.inflate(inflater, container, false)

        // Get a reference to the Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val campaignsRef = database.getReference("campaigns")


        // Set up a ValueEventListener to listen for data changes
        val campaignListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Iterate through the campaign data snapshots
                for (snapshot in dataSnapshot.children) {
                    // Retrieve the campaign data
                    val campaign = snapshot.getValue(Campaign::class.java)

                    if (campaign != null) {
                        binding.eventTitle.text = campaign.title.toEditable()
                        binding.eventDesc.text = campaign.desc.toEditable()
                        val imageUrl = campaign?.imageUrl.toString()

/*
                        val imageView = view?.findViewById<ImageView>(R.id.imageView7)?.let {
                            if (!imageUrl.isNullOrEmpty()) {
                                val imageRef = storageRef.child(imageUrl)
                                val ONE_MEGABYTE: Long = 1024 * 1024
                                imageRef.getBytes(ONE_MEGABYTE)
                                    .addOnSuccessListener { imageData ->
                                        // Decode the byte array into a Bitmap or use it directly
                                        val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)

                                        // Set the Bitmap to the ImageView
                                        it.setImageBitmap(bitmap)
                                    }
                                    .addOnFailureListener { exception ->
                                        // Handle any errors that occur during image retrieval
                                        Log.e("EventFragment", "Image retrieval failed: ${exception.message}")
                                    }
                            } else {
                                // Handle the case when imageUrl is null or empty
                                Log.e("EventFragment", "Invalid imageUrl: $imageUrl")
                            }

                        } */

                    } else {
                        // Handle null campaign object or invalid data
                        Log.e("CampaignListener", "Invalid campaign data: $snapshot")
                    }

                }
                /* Retrieve the arguments bundle
        val args = arguments

        // Check if the arguments bundle is not null
        if (args != null) {
            // Retrieve the title and desc values from the bundle
            val title = args.getString("title")
            val desc = args.getString("desc")

            // Use the retrieved values as needed
            // For example, set the values to TextViews
            binding.CtitleText.text = title
            binding.CtitleDesc.text = desc }
            return binding.root */


            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
                Log.e("CampaignListener", "Error: ${databaseError.message}")
            }
        }
        campaignsRef.addValueEventListener(campaignListener)

        return binding.root
    } }
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

/* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
     super.onViewCreated(view, savedInstanceState)
     //binding.dTitle2.setText(CampaignFragment.title.toString())
     //binding.dAmount.setText(CampaignFragment.amount.toString().toInt())
     binding.buttonDonate.setOnClickListener{
         findNavController().navigate(R.id.action_nav_event_to_nav_donation)
     }
 }
}*/

