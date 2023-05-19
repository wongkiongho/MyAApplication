package my.edu.tarc.myaapplication.ui.Account

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.myaapplication.*
import my.edu.tarc.myaapplication.databinding.ActivityMainBinding
import my.edu.tarc.myaapplication.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var mProfile: CardView
    private lateinit var mOrderHistory: CardView
    private lateinit var mChangePassword: CardView
    private lateinit var mButton: Button


    companion object {
        fun newInstance() = AccountFragment()
    }

    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mChangePassword = view.findViewById(R.id.cardView6)
        mOrderHistory = view.findViewById(R.id.cardView7)
        mProfile = view.findViewById(R.id.cardView5)
        mButton = view.findViewById(R.id.buttonLogout)

        mProfile.setOnClickListener {
            findNavController().navigate(R.id.action_nav_account_to_detailsFragment)
        }
        mOrderHistory.setOnClickListener {
            findNavController().navigate(R.id.action_nav_account_to_historyFragment)
        }
        mChangePassword.setOnClickListener {
            findNavController().navigate(R.id.action_nav_account_to_changepasswordFragment)
        }
        mButton.setOnClickListener {
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
        }
    }


}