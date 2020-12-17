package call.ofbeer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import call.ofbeer.R
import kotlinx.android.synthetic.main.app_bar_main.*

class GroupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "Grupy"
            // show back button on toolbar
            // on back button press, it will navigate to parent fragment
            setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        }//when want to add on toolbar back strzałka xd zamiast menu*/

    }


}

