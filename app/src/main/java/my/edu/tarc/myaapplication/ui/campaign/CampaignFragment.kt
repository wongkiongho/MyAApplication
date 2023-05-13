package my.edu.tarc.mobileapplicationassignment

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import my.edu.tarc.myaapplication.R
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CampaignFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CampaignFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val PICK_IMAGE_REQUEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_campaign, container, false)


        val selectDateButton = view.findViewById<Button>(R.id.datebtn)
        val textView7 = view.findViewById<TextView>(R.id.textView7)



        val selectImageButton = view.findViewById<Button>(R.id.upload_image)

        selectImageButton.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"


            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }


        // Set an onClickListener for the button
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

            // Show the dialog
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
        })

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // Get the selected image
            val imageUri = data.data

            uploadImage(imageUri)
        }

    }

    private fun uploadImage(imageUri: Uri?) {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CampaignFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CampaignFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}










