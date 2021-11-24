package com.example.climby.ui.publish.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.domain.province.GetAllProvinces
import com.example.climby.domain.type.Get
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishViewModel @Inject constructor(private val getAllProvinces: GetAllProvinces, private val getAllTypes: Get) : ViewModel() {

    var provincesModel = MutableLiveData<List<String>>()
    var typesModel = MutableLiveData<List<String>>()

    fun getProvince(){
        viewModelScope.launch {
            val result = getAllProvinces()
            val resultName: MutableList<String> = arrayListOf()
            result.forEach{
                resultName.add(it.name)
            }
            if (!result.isNullOrEmpty())
                provincesModel.postValue(resultName)
        }
    }

    fun getTypes(){
        viewModelScope.launch {
            /*val type = arrayOf("Boulder, Deportiva, Rocódromo...", "Boulder", "Deportiva", "Rocódromo", "Clásica")*/
            val result = getAllTypes()
            val resultName: MutableList<String> = arrayListOf()
            result.forEach{
                resultName.add(it.name)
            }
            if (!result.isNullOrEmpty())
                typesModel.postValue(resultName)
        }
    }




    //Muestra un cuadro de dialogo con un calendario al pulsar sobre el EditextFecha:
    /*private fun showDatePickerDialog() {
        val newFragment: DatePickerFragment = DatePickerFragment.newInstance(OnDateSetListener { datePicker, year, month, day ->
            val calendar = Calendar.getInstance()
            calendar[datePicker.year, datePicker.month] = datePicker.dayOfMonth

            //final String selectedDate = year + "-" + (month+1) + "-" + day;
            //Date hey_dame_la_fecha_como_yo_quiero = java.sql.Date.valueOf(selectedDate);
            val dateFormat: DateFormat = SimpleDateFormat("dd/MM")
            val strDate = dateFormat.format(calendar.time)
            etPlannedDate.setText(strDate)
            fechaSelecionada = strDate
            comprobarControles()
        })
        val bundle = Bundle()
        bundle.putString("dateAsText", etPlannedDate.getText().toString())
        newFragment.setArguments(bundle)
        newFragment.show(getChildFragmentManager(), "datePicker")
    }*/
}