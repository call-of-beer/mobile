package call.ofbeer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import call.ofbeer.R
import call.ofbeer.api.SessionManager
import kotlinx.android.synthetic.main.fragment_tasting_set_name.*

class TastingSetNameFragment : Fragment() {

    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasting_set_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())


        go_next_button.setOnClickListener {

            val name = name_of_tasting.text.toString().trim()


            if (name.isEmpty()) {
                name_of_tasting.error = "Nazwa wymagana"
                name_of_tasting.requestFocus()
                return@setOnClickListener
            }

            val description = tastingDescription.text.toString().trim()


            if (description.isEmpty()) {
                tastingDescription.error = "Pole wymagane"
                tastingDescription.requestFocus()
                return@setOnClickListener
            }

            session.gettastingName(name)
            session.gettastingDescription(description)

            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentTasting, TastingChooseGroupFragment())
                ?.addToBackStack(null)
                ?.commit()

        }
    }


}