package my.edu.tarc.myaapplication.ui.donation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import my.edu.tarc.myaapplication.R
import my.edu.tarc.myaapplication.databinding.FragmentPaymentBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class PaymentFragment : Fragment() {
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val DonationViewModel: DonationViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.editTextPaidAmount.setText(DonationViewModel.payment.toString())
        binding.editTextPerson.setText(DonationViewModel.name.toString())
        binding.buttonConfirm.setOnClickListener{
            val currentTime = LocalTime.now()

            // format the time as a string
            val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
            val formattedTime = currentTime.format(formatter)

            val pMethod = binding.radioGroup2.checkedRadioButtonId
            if(pMethod == binding.radioButtonCash.id){

                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Time:"+formattedTime+"\n"+"Name: " + DonationViewModel.name.toString() + "\n" + "Amount Paid: " + DonationViewModel.payment.toString() +"\n"+"Payment Method:"+binding.radioButtonCash.text.toString()+"\n" + "Payment Successful")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { _, _ ->

                        findNavController().navigate(R.id.action_paymentFragment_to_nav_home)

                    }
                builder.create().show()

            }
            else if(pMethod == binding.radioButtonBank.id){
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Time:"+formattedTime+"\n"+"Name: " + DonationViewModel.name.toString() + "\n" + "Amount Paid: " + DonationViewModel.payment.toString() +"\n"+"Payment Method:"+binding.radioButtonBank.text.toString()+ "\n" + "Payment Successful")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { _, _ ->
                        // Do something or nothing when click ok
                        findNavController().navigate(R.id.action_paymentFragment_to_nav_home)
                    }
                builder.create().show()
            }
            else{
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Time:"+formattedTime+"\n"+"Name: " + DonationViewModel.name.toString() + "\n" + "Amount Paid: " + DonationViewModel.payment.toString() +"\n"+"Payment Method:"+binding.radioButtonTngo.text.toString()+ "\n" + "Payment Successful")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { _, _ ->
                        // Do something or nothing when click ok
                        findNavController().navigate(R.id.action_paymentFragment_to_nav_home)

                    }
                builder.create().show()
            }
        }
    }

}