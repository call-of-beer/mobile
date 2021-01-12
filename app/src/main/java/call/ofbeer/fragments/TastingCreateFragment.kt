package call.ofbeer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import call.ofbeer.R
import call.ofbeer.activities.MainActivity
import call.ofbeer.api.SessionManager
import kotlinx.android.synthetic.main.fragment_tasting_create.*

class TastingCreateFragment : Fragment() {

    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasting_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())

        start_tasting_button.setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentTasting, RateAddFragment())
            ?.addToBackStack(null)
            ?.commit()
        }

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
            })

        backToHomeFragment.setOnClickListener{
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}