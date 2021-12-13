package com.example.climby.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.climby.R
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.user.UserModel
import com.example.climby.databinding.ActivityOnboardingThreeBinding
import com.example.climby.ui.discover.adapter.DiscoverAdapter
import com.example.climby.utils.Commons
import com.example.climby.view.adapter.OnBoardingThreeAdapter
import com.example.climby.view.viewmodel.OnBoardingThreeViewModel
import kotlin.math.absoluteValue

class OnBoardingThreeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingThreeBinding
    private lateinit var onBoardingThreeViewModel: OnBoardingThreeViewModel
    private lateinit var onBoardingThreeAdapter: OnBoardingThreeAdapter

    private var trip: TripModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBoardingThreeViewModel = ViewModelProvider(this).get(OnBoardingThreeViewModel::class.java)
        binding = ActivityOnboardingThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        init()

        binding.IVBack.setOnClickListener {
            updateBooking()
            onBackPressed()
        }

        binding.BTSendQualify.setOnClickListener {
            if(binding.ETSendMenssage.text.isNullOrEmpty()){
                showDialog(trip?.driver!!)
            }

        }
    }

    private fun showDialog(userModel: UserModel) {
        AlertDialog.Builder(this)
            .setTitle("El mensaje privado a "  + userModel.name.toString().split(" ")[0] + " está vacío." )
            .setMessage(R.string.text_qualify_attendes)
            .setNegativeButton(R.string.cancel) { view, _ ->
                Toast.makeText(this,"Cancelar",Toast.LENGTH_SHORT ).show()
                view.dismiss()
            }
            .setPositiveButton(R.string.text_send_button) { view, _ ->
                Toast.makeText(this,"Enviar",Toast.LENGTH_SHORT ).show()
                view.dismiss()
                sendQualify()
            }
            .setCancelable(false)
            .create().show()
    }


    private fun updateBooking() {
        Toast.makeText(this,"Actualizando reserva", Toast.LENGTH_SHORT ).show()
    }
    private fun sendQualify() {
        Toast.makeText(this,"Enviando valoración", Toast.LENGTH_SHORT ).show()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        setPhotoTrip(trip?.type.toString())
        binding.TVTypeQualifyAttendees.text = trip?.type?.name + " en"
        binding.TVSiteQualifyAttendees.text = trip?.site?.name + ", " + (trip?.departure?.split("-")?.get(2)?.split(" ")?.get(0) ?: "") + " " + trip?.departure?.let { Commons.getDate(it) }
        binding.TVSendMenssage.text = "Envía un mensaje a " + trip?.driver?.name.toString().split(" ")[0]
        binding.RVQualify.layoutManager = LinearLayoutManager(this)
        onBoardingThreeAdapter = OnBoardingThreeAdapter(trip?.bookings ?: emptyList(), this)
        binding.RVQualify.adapter = onBoardingThreeAdapter

    }

    private fun getData() {
        val bundle = intent.extras
        trip = bundle?.getParcelable("trip")
    }

    private fun setPhotoTrip(type: String) {
        when (type) {
            "Boulder" -> {
                Glide.with(this).load(R.mipmap.boulder).error(R.mipmap.default_picture).into(binding.IVSiteQualifyAttendees)
            }
            "Deportiva" -> {
                Glide.with(this).load(R.mipmap.lead).error(R.mipmap.default_picture).into(binding.IVSiteQualifyAttendees)
            }
            "Rocódromo" -> {
                Glide.with(this).load(R.mipmap.gym).error(R.mipmap.default_picture).into(binding.IVSiteQualifyAttendees)
            }
            "Clásica" -> {
                Glide.with(this).load(R.mipmap.trad).error(R.mipmap.default_picture).into(binding.IVSiteQualifyAttendees)
            }
            else -> {
                Glide.with(this).load(R.mipmap.default_picture).error(R.mipmap.default_picture).into(binding.IVSiteQualifyAttendees)
            }
        }
    }
}