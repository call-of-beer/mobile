package call.ofbeer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import call.ofbeer.R
import call.ofbeer.api.SessionManager
import kotlinx.android.synthetic.main.fragment_add_user.*
import kotlinx.android.synthetic.main.fragment_tasting_details.*

class BeerSeeDetailsFragment : Fragment() {

    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasting_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())

        beer_name.text = session.beerName

        aroma_rate.rating = session.aromaRate
        color_rate.rating = session.colorRate
        taste_rate.rating = session.tasteRate
        bitterness_rate.rating = session.bitternessRate
        texture_rate.rating = session.textureRate
        alc_volume.text = session.beerAlcVolume


        val rate = view.findViewById<Button>(R.id.rate_beer)

        rate.visibility = View.GONE


        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.nav_host_fragment, BeerFragment())
                    ?.addToBackStack(null)
                    ?.commit()
                }
            })

    }
}