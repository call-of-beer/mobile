package call.ofbeer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {


    private val _textAppName = MutableLiveData<String>().apply {
        value = "Call of beer"
    }
    val textAppName: LiveData<String> = _textAppName


}