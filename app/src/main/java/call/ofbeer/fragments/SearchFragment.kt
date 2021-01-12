package call.ofbeer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import call.ofbeer.R
import call.ofbeer.adapters.BeerAdapter
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.models.Beer
import call.ofbeer.requests.SearchRequest
import kotlinx.android.synthetic.main.fragment_search_list.*
import retrofit2.Call
import retrofit2.Response


class SearchFragment: Fragment(){

    private var beer = listOf<Beer>()
    lateinit var session: SessionManager
    private lateinit var searchAdapter: BeerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        listOfBeersSearch.layoutManager = LinearLayoutManager(requireContext())

        fetchBeers()

    }

    private fun fetchBeers(){

        session = SessionManager(requireContext())


        RetrofitClient.instance.search(SearchRequest(session.search!!))
            .enqueue(object : retrofit2.Callback<List<Beer>> {
                override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                    if (response.code() == 200) {
                        beer = response.body()!!
                        searchAdapter = BeerAdapter(requireContext(), beer)

                        if (listOfBeersSearch == null)
                            Toast.makeText(requireContext(), "Brak wyników", Toast.LENGTH_SHORT)
                                .show()

                        listOfBeersSearch.adapter = searchAdapter
                        searchAdapter.notifyDataSetChanged()

                    }
                }

            })

    }

}