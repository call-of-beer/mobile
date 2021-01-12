package call.ofbeer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import call.ofbeer.R
import call.ofbeer.api.*
import call.ofbeer.requests.AddBeerRequest
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_beer_add.*
import retrofit2.Call
import retrofit2.Response

class BeerAddFragment : Fragment() {

    lateinit var session: SessionManager
    private var inputCountry: Spinner? = null
    private var _idsCountry: ArrayList<Int> = ArrayList<Int>()
    private var countryPosition = 0


    private var inputType: Spinner? = null
    private var _idsType: ArrayList<Int> = ArrayList<Int>()
    private var typePosition = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beer_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())


        inputCountry = getView()?.findViewById(R.id.input_country) as Spinner
        fetchCountry()
        inputCountry?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val id = _idsCountry.get(position)
                session.getcountryId(id)
                countryPosition = parent?.getItemIdAtPosition(position)!!.toInt()

            }
        }




        inputType = getView()?.findViewById(R.id.input_type) as Spinner
        fetchType()
        inputType?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val id = _idsType.get(position)
                session.gettypeId(id)
                typePosition = parent?.getItemIdAtPosition(position)!!.toInt()

            }
        }



        beer_send_data.setOnClickListener{

            val name = input_name.text.toString().trim()
            val alcVolume = input_aclVolume.text.toString().trim()
            val desctiprion = input_description.text.toString().trim()


            if (name.isEmpty()) {
                input_name.error = "Nazwa wymagana"
                input_name.requestFocus()
                return@setOnClickListener
            }

            if (alcVolume.isEmpty()) {
                input_aclVolume.error = "Pole wymagane"
                input_aclVolume.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.addNewBeer(AddBeerRequest(name, alcVolume, desctiprion), session.countryId, session.typeId, session.TOKEN)
                .enqueue(object : retrofit2.Callback<SuccesfulResponse>{
                    override fun onFailure(call: Call<SuccesfulResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<SuccesfulResponse>,
                        response: Response<SuccesfulResponse>
                    ) {
                        if(response.code() ==200)
                        {
                            val fragmentTransaction = fragmentManager?.beginTransaction()
                            fragmentTransaction?.replace(R.id.fragmentTasting, TastingChooseBeersFragment())
                            ?.addToBackStack(null)
                            ?.commit()
                            Toast.makeText(requireContext(), "Piwo dodane! Możesz teraz je wybrać z listy", Toast.LENGTH_LONG).show()
                        }
                    }

                })
        }



    }





    private fun fetchCountry(){

        RetrofitClient.instance.getCountriesList(session.TOKEN)
            .enqueue(object : retrofit2.Callback<CountryGetResponse>{
                override fun onFailure(call: Call<CountryGetResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<CountryGetResponse>,
                    response: Response<CountryGetResponse>
                ) {
                    if (response.code() == 200) {

                        if(response.body()!=null){

                            val responseList = response.body()?.result!!
                            val item = arrayOfNulls<String>(responseList!!.size)
                            for(i in responseList.indices)
                            {
                                item[i] = responseList[i].name
                                var id = responseList[i].id
                                _idsCountry.add(id)
                            }
                            val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item)
                            inputCountry?.adapter = arrayAdapter
                        }



                    }
                }

            })




    }




    private fun fetchType(){


        RetrofitClient.instance.getTypesList(session.TOKEN)
            .enqueue(object : retrofit2.Callback<TypeGetResponse>{
                override fun onFailure(call: Call<TypeGetResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(
                    call: Call<TypeGetResponse>,
                    response: Response<TypeGetResponse>
                ) {
                    if (response.code() == 200) {

                        if(response.body()!=null){

                            val responseList = response.body()?.result!!
                            val item = arrayOfNulls<String>(responseList!!.size)
                            for(i in responseList.indices)
                            {
                                item[i] = responseList[i].name
                                var id = responseList[i].id
                                _idsType.add(id)
                            }
                            val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item)
                            inputType?.adapter = arrayAdapter
                        }



                    }
                }

            })



    }
}