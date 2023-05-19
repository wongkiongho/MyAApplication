package my.edu.tarc.myaapplication.ui.campaign

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import my.edu.tarc.myaapplication.R
import my.edu.tarc.myaapplication.databinding.FragmentCampaignBinding
import my.edu.tarc.myaapplication.databinding.FragmentDonationBinding
import my.edu.tarc.myaapplication.databinding.FragmentEventBinding
import java.util.*



class CampaignFragment : Fragment() {
    private var _binding: FragmentCampaignBinding? = null
    private val binding get() = _binding!!
    private val CampaignFragment: CampaignViewModel by activityViewModels()




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



            if (title.isEmpty() || desc.isEmpty()) {
                // Show an error message
                Toast.makeText(requireContext(), "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                builder.setTitle("Confirmation")
                    .setMessage("Are you sure you want to proceed?")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->

                    })
                    .setNegativeButton(
                        "No",
                        DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        })
                    .show()

                val title = titleText.text.toString()
                val desc = descText.text.toString()
                val campaign = Campaign(title, desc)

                CampaignFragment.title = binding.titleText.toString()
                CampaignFragment.amount = binding.textAmount.toString().toInt()

                mDatabase.push().setValue(campaign)
                // Save titleText in database
              //  val campaign = Campaign(title)
               // mDatabase.child("campaigns").push().setValue(campaign)
                val bundle = Bundle()
                bundle.putString("title", title)
                val eventFragment = EventFragment()
                eventFragment.arguments = bundle
                //supportFragmentManager.beginTransaction().replace(R.id.fragment_container, eventFragment).commit()
                findNavController().navigate(R.id.action_nav_campaign_to_nav_event)


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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CampaignFragment.
         */

    data class Campaign(val title: String, val desc: String) {
        constructor() : this("","")
    }
}}
