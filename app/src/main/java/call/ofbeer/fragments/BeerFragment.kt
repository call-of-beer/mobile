package call.ofbeer.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import call.ofbeer.R
import call.ofbeer.adapters.BeerAdapter
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.models.Beer
import kotlinx.android.synthetic.main.fragment_beer_list.*
import retrofit2.Call
import retrofit2.Response


class BeerFragment : Fragment() {

    private var beer = listOf<Beer>()
    lateinit var session: SessionManager
    private lateinit var beerAdapter: BeerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_beer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Baza piw"

        listOfBeers.layoutManager = LinearLayoutManager(requireContext())

        fetchBeers()

        btn_search.setOnClickListener {
            val beerName = searchValue.text.toString().trim()


            if(beerName.isEmpty()) {
                searchValue.error = "Input name to search"
                searchValue.requestFocus()
                return@setOnClickListener
            }

            session.searchBeer(beerName)
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.nav_host_fragment, SearchFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

    }

    private fun fetchBeers(){

        session = SessionManager(requireContext())

        RetrofitClient.instance.getAllBeers()
            .enqueue(object : retrofit2.Callback<List<Beer>> {
                override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                    if (response.code() == 200) {
                        beer = response.body()!!
                        beerAdapter = BeerAdapter(requireContext(), beer)

                        listOfBeers.adapter = beerAdapter
                        beerAdapter.notifyDataSetChanged()

                    }
                }

            })

    }




/*
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Beer?)
    }
*/

}
