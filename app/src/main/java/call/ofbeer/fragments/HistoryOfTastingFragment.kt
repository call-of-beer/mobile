package call.ofbeer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import call.ofbeer.R
import call.ofbeer.adapters.TastingAdapter
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.api.TastingGetResponse
import call.ofbeer.models.Tasting
import kotlinx.android.synthetic.main.fragment_history_of_tasting.*
import retrofit2.Call
import retrofit2.Response

class HistoryOfTastingFragment : Fragment() {

    lateinit var session: SessionManager
    private var tasting = listOf<Tasting>()
    private lateinit var tastingAdapter: TastingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_history_of_tasting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Historia degustacji"

        tastingList.layoutManager = LinearLayoutManager(requireContext())

        fetchTasting()


        val fragmentTransaction = fragmentManager?.beginTransaction()

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    if (session.fragmentRedirect == 0) {
                        fragmentTransaction?.replace(R.id.nav_host_fragment,ManageGroupFragment())
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                    if (session.fragmentRedirect == 1) {
                        fragmentTransaction?.replace(R.id.fragmentTasting,ManageGroupFragment())
                            ?.addToBackStack(null)
                            ?.commit()

                    }
                }
            })

    }

    private fun fetchTasting() {

        session = SessionManager(requireContext())

        RetrofitClient.instance.getTastingOfGroup(session.goupID, session.TOKEN)
            .enqueue(object : retrofit2.Callback<TastingGetResponse> {
                override fun onFailure(call: Call<TastingGetResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<TastingGetResponse>, response: Response<TastingGetResponse>
                ) {
                    if(response.body()?.result!!.isEmpty())
                    {
                        Toast.makeText(requireContext(), "Ta grupa nie ma jeszcze żadnych degustacji.", Toast.LENGTH_SHORT).show()
                    }
                    tasting = response.body()?.result!!
                    tastingAdapter = TastingAdapter(requireContext(), tasting)

                    tastingList.adapter = tastingAdapter
                    tastingAdapter.notifyDataSetChanged()
                }

            })
    }
}