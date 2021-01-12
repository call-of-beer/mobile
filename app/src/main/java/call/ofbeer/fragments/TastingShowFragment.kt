package call.ofbeer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import call.ofbeer.R
import call.ofbeer.activities.TastingActivity
import call.ofbeer.adapters.TastingAdapter
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.api.TastingGetResponse
import call.ofbeer.models.Tasting
import kotlinx.android.synthetic.main.fragment_history_of_tasting.tastingList
import kotlinx.android.synthetic.main.fragment_tasting_show.*
import retrofit2.Call
import retrofit2.Response

class TastingShowFragment : Fragment() {

    lateinit var session: SessionManager
    private var tasting = listOf<Tasting>()
    private lateinit var tastingAdapter: TastingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasting_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tastingList.layoutManager = LinearLayoutManager(requireContext())

        session = SessionManager(requireContext())

        session.getfragmentRedirect(0)
        session.getdifferentOption(0)

        fetchTasting()


        startTastng.setOnClickListener{
            val intent = Intent(activity, TastingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchTasting(){

        session = SessionManager(requireContext())

        RetrofitClient.instance.getTastingOfGroup(session.goupID, session.TOKEN)
            .enqueue(object : retrofit2.Callback<TastingGetResponse>{
                override fun onFailure(call: Call<TastingGetResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<TastingGetResponse>, response: Response<TastingGetResponse>
                ) {
                    if (response.body() == null) {
                        Toast.makeText(
                            requireContext(),
                            "Rozpocznij swoją pierwszą degustację!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        tasting = response.body()?.result!!
                        tastingAdapter = TastingAdapter(requireContext(), tasting)

                        tastingList.adapter = tastingAdapter
                        tastingAdapter.notifyDataSetChanged()
                    }
                }

            })


    }
}