//campaignFragment
package my.edu.tarc.myaapplication.ui.campaign

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import my.edu.tarc.myaapplication.R
import my.edu.tarc.myaapplication.databinding.FragmentCampaignBinding
import my.edu.tarc.myaapplication.databinding.FragmentDonationBinding
import my.edu.tarc.myaapplication.databinding.FragmentEventBinding
import java.util.*



class CampaignFragment : Fragment() {
    private var _binding: FragmentCampaignBinding? = null
    private val binding get() = _binding!!
    //private val CampaignFragment: CampaignViewModel by activityViewModels()

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCampaignBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectDateButton = view.findViewById<Button>(R.id.datebtn)
        val textView7 = view.findViewById<TextView>(R.id.textView7)

        val selectImageButton = view.findViewById<Button>(R.id.upload_image)

        val mDatabase = FirebaseDatabase.getInstance().getReference("campaigns")
        val storageRef = FirebaseStorage.getInstance().reference

        // Select image function

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageUri: Uri? = data?.data

                if (imageUri != null) {
                    selectedImageUri = imageUri
                    // Process the selected image URI here or perform any other operations
                }
            }
        }

        selectImageButton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(galleryIntent)
        }


        //Select Campaign End Date
        selectDateButton.setOnClickListener {
            // Create a new DatePickerDialog
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, 1) // set minimum date to tomorrow
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, month, dayOfMonth ->

                    val selectedDate = "${dayOfMonth}/${month + 1}/${year}"
                    textView7.text = selectedDate // set the selected date to the textView7
                },
                year,
                month,
                day
            )

            // Set the minimum date
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }

        val categories = listOf("Animal Welfare", "Disaster Relief", "Education", "Environmental", "Medical Relief")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val categorySpinner = view.findViewById<Spinner>(R.id.category_spinner)
        categorySpinner.adapter = adapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val selectedCategory = categories[position]
                Toast.makeText(requireContext(), "You selected $selectedCategory", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val nextbutton = view.findViewById<Button>(R.id.nextpage)

        nextbutton.setOnClickListener(View.OnClickListener {
            val titleText = view.findViewById<EditText>(R.id.titleText)
            val descText = view.findViewById<EditText>(R.id.shortDesc)


            val title = titleText.text.toString().trim()
            val desc = descText.text.toString().trim()
            val imageUri = selectedImageUri.toString().trim()



            if (title.isEmpty() || desc.isEmpty()) {
                // Show an error message
                Toast.makeText(requireContext(), "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                builder.setTitle("Confirmation")
                    .setMessage("Are you sure you want to proceed?")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->

                        selectedImageUri?.let { imageUri ->
                            val filename = UUID.randomUUID().toString()
                            val imageRef = storageRef.child("images/$filename")
                            val uploadTask = imageRef.putFile(imageUri)


                            val title = titleText.text.toString()
                            val desc = descText.text.toString()
                            // val imageUri = selectedImageUri.toString().trim()

                            /* val campaign = Campaign(title, desc, "")
                             mDatabase.push().setValue(campaign)
                             findNavController().navigate(R.id.action_nav_campaign_to_nav_event)*/

                            uploadTask.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Get the download URL of the uploaded image
                                    imageRef.downloadUrl.addOnCompleteListener { urlTask ->
                                        if (urlTask.isSuccessful) {
                                            val imageUrl = urlTask.result.toString()

                                            val campaign = Campaign(title, desc, imageUrl)
                                            mDatabase.push().setValue(campaign)

                                            findNavController().navigate(R.id.action_nav_campaign_to_nav_event)
                                        } else {
                                            // Handle failure to get download URL
                                            Toast.makeText(requireContext(), "Failed to get image download URL.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } else {
                                    // Handle image upload failure
                                    Toast.makeText(requireContext(), "Failed to upload image.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } ?: run {
                            // No image selected
                            val campaign = Campaign(title, desc, "")
                            mDatabase.push().setValue(campaign)

                            findNavController().navigate(R.id.action_nav_campaign_to_nav_event)
                        }
                    })
                    .setNegativeButton(
                        "No",
                        DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        })
                    .show()




            }
            //findNavController().navigate(R.id.action_nav_campaign_to_eventFragment)
        })


    }


    //  private fun uploadImage(imageUri: Uri?) {

    // }
    /* private fun uploadImage(imageUri: Uri?) {
        // Get a reference to Firebase Storage
        val storageRef = storage.reference

        // Create a unique filename for the image
        val filename = UUID.randomUUID().toString()

        // Create a StorageReference object that points to the location where the image will be stored in Firebase Storage
        val imageRef = storageRef.child("images/$filename")
    }*/

    companion object {

        private const val GALLERY_REQUEST_CODE = 1
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CampaignFragment.
         */


    }}
