package call.ofbeer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import call.ofbeer.R
import call.ofbeer.api.BeerResponse
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.api.TastingResponse
import call.ofbeer.requests.CreateTastingRequest
import kotlinx.android.synthetic.main.fragment_tasting_choose_beers.*
import retrofit2.Call
import retrofit2.Response

class TastingChooseBeersFragment : Fragment() {

    lateinit var session: SessionManager
    private var inputBeer: Spinner? = null
    private var _idsBeer: ArrayList<Int> = ArrayList()
    private var beerPosition = 0
    private var _namesBeer: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasting_choose_beers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())



        inputBeer = getView()?.findViewById(R.id.choose_beer) as Spinner
        fetchBeer()
        inputBeer?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val id = _idsBeer.get(position)
                val name = _namesBeer.get(position)
                session.getbeerId(id)
                session.getbeerName(name)
                beerPosition = parent?.getItemIdAtPosition(position)!!.toInt()

            }
        }

        createBeer.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentTasting, BeerAddFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        createTastingBtn.setOnClickListener {

            RetrofitClient.instance.createTasting(
                CreateTastingRequest(
                    session.tastingName!!,
                    session.tastingDescription!!,
                    session.userID,
                    session.goupID,
                    session.beerId
                ), session.goupID, session.beerId, session.TOKEN
            )
                .enqueue(object : retrofit2.Callback<TastingResponse> {
                    override fun onFailure(call: Call<TastingResponse>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<TastingResponse>,
                        response: Response<TastingResponse>
                    ) {
                        if (response.code() == 200) {
                            session.gettastingId(response.body()!!.result.id)


                            val fragmentTransaction = fragmentManager?.beginTransaction()
                            fragmentTransaction?.replace(
                                R.id.fragmentTasting,
                                TastingCreateFragment()
                            )
                                ?.addToBackStack(null)
                                ?.commit()
                        }
                    }

                })
        }
    }

    private fun fetchBeer() {

        session = SessionManager(requireContext())

        RetrofitClient.instance.getAllBeers()
            .enqueue(object : retrofit2.Callback<BeerResponse> {
                override fun onFailure(call: Call<BeerResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<BeerResponse>,
                    response: Response<BeerResponse>
                ) {
                    if (response.code() == 200) {

                        if (response.body() != null) {

                            val responseList = response.body()?.result!!
                            val item = arrayOfNulls<String>(responseList.size)
                            for (i in responseList.indices) {
                                item[i] = responseList[i].name
                                var id = responseList[i].id
                                _idsBeer.add(id)

                                var name = responseList[i].name
                                _namesBeer.add(name)
                            }
                            val arrayAdapter = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_item,
                                item
                            )
                            inputBeer?.adapter = arrayAdapter
                        }


                    }
                }

            })

    }

}