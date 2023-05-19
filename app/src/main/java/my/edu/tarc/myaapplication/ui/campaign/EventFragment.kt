package my.edu.tarc.myaapplication.ui.campaign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import my.edu.tarc.myaapplication.R
import my.edu.tarc.myaapplication.databinding.FragmentEventBinding



class EventFragment : Fragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private val CampaignFragment: CampaignViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.dTitle2.setText(CampaignFragment.title.toString())
        //binding.dAmount.setText(CampaignFragment.amount.toString().toInt())
        binding.buttonDonate.setOnClickListener{
            findNavController().navigate(R.id.action_nav_event_to_nav_donation)
        }
    }
}