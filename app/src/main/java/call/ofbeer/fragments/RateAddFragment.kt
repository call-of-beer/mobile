package call.ofbeer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import call.ofbeer.R
import call.ofbeer.activities.MainActivity
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.api.SuccesfulResponse
import call.ofbeer.requests.AddCommentRequest
import call.ofbeer.requests.NewRateRequest
import kotlinx.android.synthetic.main.fragment_rate_add.*
import retrofit2.Call
import retrofit2.Response

class RateAddFragment : Fragment() {

    lateinit var session: SessionManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rate_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())
        if(session.fragmentRedirect==1) {
            tolbarRate.visibility = View.VISIBLE
            tolbarRate.title = session.beerName
        }
        val fragmentTransaction = fragmentManager?.beginTransaction()

        send_ratings.setOnClickListener {

            val aroma = aroma_rate.rating.toInt()
            val color = color_rate.rating.toInt()
            val taste = taste_rate.rating.toInt()
            val bitterness = bitterness_rate.rating.toInt()
            val texture = texture_rate.rating.toInt()
            val comment = comment.text.toString().trim()

            requireActivity().onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {

                        if (session.fragmentRedirect == 0) {
                            fragmentTransaction?.replace(
                                R.id.nav_host_fragment,
                                TastingDetailsFragment()
                            )
                                ?.addToBackStack(null)
                                ?.commit()
                        }
                        if (session.fragmentRedirect == 1) {
                            val intent = Intent(activity, MainActivity::class.java)
                            startActivity(intent)
                        }

                    }
                })

            if (comment.isNotEmpty()) {
                RetrofitClient.instance.addComment(
                    AddCommentRequest(comment),
                    session.tastingId,
                    session.TOKEN
                )
                    .enqueue(object : retrofit2.Callback<SuccesfulResponse> {
                        override fun onFailure(call: Call<SuccesfulResponse>, t: Throwable) {
                            Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<SuccesfulResponse>,
                            response: Response<SuccesfulResponse>
                        ) {
                            if (response.code() == 200) {
                            } else {
                                Toast.makeText(context, "Coś poszło nie tak", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                    })
            }


            RetrofitClient.instance.addRate(
                NewRateRequest(aroma,color,taste,bitterness,texture,session.userID), session.beerId, session.tastingId, session.TOKEN)
                .enqueue(object : retrofit2.Callback<SuccesfulResponse> {
                    override fun onFailure(call: Call<SuccesfulResponse>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<SuccesfulResponse>,
                        response: Response<SuccesfulResponse>
                    ) {
                        if (response.code() == 201) {
                            Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show()

                            if (session.fragmentRedirect == 0) {
                                fragmentTransaction?.replace(
                                    R.id.nav_host_fragment,
                                    TastingDetailsFragment()
                                )?.addToBackStack(null)
                                    ?.commit()
                            }
                            if (session.fragmentRedirect == 1) {
                                fragmentTransaction?.replace(
                                    R.id.fragmentTasting,
                                    TastingDetailsFragment()
                                )?.addToBackStack(null)
                                    ?.commit()

                            }

                        } else {
                            Toast.makeText(context, "Coś poszło nie tak", Toast.LENGTH_SHORT).show()
                        }
                    }

                })


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireFragmentManager().beginTransaction()
            .remove((RateAddFragment() as Fragment?)!!).commitAllowingStateLoss()
    }
}