package my.edu.tarc.myaapplication

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import my.edu.tarc.myaapplication.databinding.FragmentDonationBinding
import my.edu.tarc.myaapplication.ui.donation.DonationViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DonationFragment : Fragment() {
    private var _binding: FragmentDonationBinding? = null
    private val binding get() = _binding!!

    private val DonationViewModel: DonationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDonationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emailText = binding.email1?.text.toString()
        binding.buttonProceed.setOnClickListener{

            if (binding.email1.text.isNullOrEmpty()
                || binding.editTextAmount.text.isNullOrEmpty()
                || binding.inputContact1.text.isNullOrEmpty()
                || binding.name1.text.isNullOrEmpty()

            ) {
                Toast.makeText(
                    requireContext(), "Fill in the required section",
                    Toast.LENGTH_SHORT
                ).show()
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
                print("Invalid email format")
            }


            else {
                val name = binding.name1.text.toString()
                val email = binding.email1.text.toString()
                val contact = binding.inputContact1.text.toString()



                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Name: " + name + "\n" + "Email: " + email + "\n" + "Contact No:" + contact + "\n" + "Note: Please make sure all the information are correct")
                    .setCancelable(false)
                    .setPositiveButton("Confirm") { _, _ ->
                        // Do something or nothing when click o
                        findNavController().navigate(R.id.action_nav_donation_to_paymentFragment)
                    }
                    .setNegativeButton("Edit"){_, _ ->

                    }
                builder.create().show()
                DonationViewModel.payment = binding.editTextAmount.text.toString().toInt()
                DonationViewModel.name = binding.name1.text.toString()

            }}
        binding.buttonContinue.setOnClickListener{
            val donateType = binding.radioGroup.checkedRadioButtonId
            if(donateType == binding.radioButtonNormal.id){
                findNavController().navigate(R.id.action_nav_donation_to_paymentFragment)
            }
            if(donateType == binding.radioButtonOther.id){

            }


        }
    }


}
