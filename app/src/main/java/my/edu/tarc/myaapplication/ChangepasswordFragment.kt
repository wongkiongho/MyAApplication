package my.edu.tarc.myaapplication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ChangepasswordFragment : Fragment() {

    companion object {
        fun newInstance() = ChangepasswordFragment()
    }

    private lateinit var viewModel: ChangepasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_changepassword, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChangepasswordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}