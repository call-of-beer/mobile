package call.ofbeer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import call.ofbeer.R
import call.ofbeer.activities.MainActivity
import call.ofbeer.api.SessionManager
import kotlinx.android.synthetic.main.fragment_tasting_details.*


class TastingDetailsFragment : Fragment() {

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
        val fragmentTransaction = fragmentManager?.beginTransaction()
        (activity as? AppCompatActivity)?.supportActionBar?.title = session.beerName

        beer_name.text = session.beerName

        aroma_rate.rating = session.aromaRate
        color_rate.rating = session.colorRate
        taste_rate.rating = session.tasteRate
        bitterness_rate.rating = session.bitternessRate
        texture_rate.rating = session.textureRate
        alc_volume.text = session.beerAlcVolume


        if (session.fragmentRedirect == 1) {
            rate_beer.visibility = View.GONE
        }
        if (session.fragmentRedirect == 0) {
            rate_beer.visibility = View.VISIBLE
        }

        rate_beer.setOnClickListener {
            if (session.fragmentRedirect == 0) {
                fragmentTransaction?.replace(R.id.nav_host_fragment, RateAddFragment())
                    ?.addToBackStack(null)
                    ?.commit()
            }

        }


        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    if (session.fragmentRedirect == 0) {
                        fragmentTransaction?.replace(R.id.nav_host_fragment, TastingFragment())
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                    if (session.fragmentRedirect == 1) {
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)

                    }


                }
            })
    }


}