package com.example.climby.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.climby.R
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.message.MessageModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.user.UserModel
import com.example.climby.databinding.ActivityOnboardingThreeBinding
import com.example.climby.utils.Commons
import com.example.climby.view.adapter.OnBoardingThreeAdapter
import com.example.climby.view.viewmodel.OnBoardingThreeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingThreeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingThreeBinding
    private lateinit var onBoardingThreeViewModel: OnBoardingThreeViewModel
    private lateinit var onBoardingThreeAdapter: OnBoardingThreeAdapter

    private var userSession: UserModel = Commons.userSession!!

    private var trip: TripModel? = null
    private var booking: BookingModel? = null
    private var userScores = false

    private var bookingWithOutUser: ArrayList<BookingModel>? = ArrayList()

    private var contStart = 3.0
    private var oldScore = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBoardingThreeViewModel = ViewModelProvider(this).get(OnBoardingThreeViewModel::class.java)
        binding = ActivityOnboardingThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        init()

        binding.IVBack.setOnClickListener {
            showDialogSure()
        }

        binding.BTSendQualify.setOnClickListener {
            userScores = true
            if (binding.ETSendMenssage.text.isNullOrEmpty()) {
                showDialog()
            } else {
                updateBooking()
            }
        }

        onBoardingThreeViewModel.isComplete.observe(this, Observer { it ->
            if(it){
                showMainActivity()
            }
        })
    }

    private fun showDialogSure() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.text_sure))
            .setMessage(getString(R.string.text_if_cancel))
            .setNegativeButton(R.string.cancel) { view, _ ->
                view.dismiss()
            }
            .setPositiveButton(R.string.text_send_button) { view, _ ->
                updateBooking()
                /*onBackPressed()*/
                view.dismiss()
            }
            .setCancelable(false)
            .create().show()
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.text_private_message_1) + " " + trip?.driver?.name.toString().split(" ")[0] + " " + getString(R.string.text_private_message_2))
            .setMessage(R.string.text_qualify_attendes)
            .setNegativeButton(R.string.cancel) { view, _ ->
                view.dismiss()
            }
            .setPositiveButton(R.string.text_send_button) { view, _ ->
                updateBooking()
                view.dismiss()
            }
            .setCancelable(false)
            .create().show()
    }

    private fun updateBooking() {
        if(userScores){
            trip?.driver?.ratings = trip?.driver?.ratings!! + 1
            trip?.driver?.score = (trip?.driver?.score?.plus(contStart))!!
            if(!binding.ETSendMenssage.text.toString().isNullOrEmpty()){
                val message = MessageModel(false, binding.ETSendMenssage.text.toString())
                onBoardingThreeViewModel.updateBooking(BookingModel(booking?.id!!, booking?.passenger, booking?.tripId!!, booking?.status, true, booking?.date, message), trip!!, this.applicationContext, this, true, withTrip = true)
            }else{
                onBoardingThreeViewModel.updateBooking(BookingModel(booking?.id!!, booking?.passenger, booking?.tripId!!, booking?.status, true, booking?.date, null), trip!!, this.applicationContext, this, false, withTrip = true)
            }
        }else{
            onBoardingThreeViewModel.updateBooking(BookingModel(booking?.id!!, booking?.passenger, booking?.tripId!!, booking?.status, true, booking?.date, null), trip!!,  this.applicationContext, this, false, withTrip = false)
        }
    }

    private fun showMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        setPhotoTrip(trip?.type?.name.toString())
        binding.ETSendMenssage.hint = "Cuenta cómo te sentiste escalando con el grupo o ayuda a " + (trip?.driver?.name?.split(" ")?.get(0) ?: "conductor") + " a mejorar para la próxima vez…"
        binding.TVTypeQualifyAttendees.text = trip?.type?.name + " en"
        binding.TVSiteQualifyAttendees.text = trip?.site?.name + ", " + (trip?.departure?.split("-")?.get(2)?.split(" ")?.get(0) ?: "") + " " + trip?.departure?.let { Commons.getDate(it) }
        binding.TVSendMenssage.text = "Envía un mensaje a " + trip?.driver?.name.toString().split(" ")[0]
        binding.RVQualify.layoutManager = LinearLayoutManager(this)
        onBoardingThreeAdapter = OnBoardingThreeAdapter(bookingWithOutUser ?: emptyList(), this)
        binding.RVQualify.adapter = onBoardingThreeAdapter
        onBoardingThreeAdapter.setOnClickListener(object : OnBoardingThreeAdapter.OnClickListener {
            override fun onClickAddStart(position: Int) {
                userScores = true
            }

            override fun onClickRemoveStart(position: Int) {
                userScores = true
            }
        })
        Glide.with(this).load(trip?.driver?.photo).error(R.mipmap.user).into(binding.CVAttendeesOrganizer)
        binding.TVNameQualifyAttendeesOrganizer.text = trip?.driver?.name?.split(" ")?.get(0) ?: "Organizador"

        binding.IVAddStartOrganizer.setOnClickListener {
            if (contStart != 3.0) {
                contStart += 0.5
                setStart(contStart)
            }
        }
        binding.IVRemoveStartOrganizer.setOnClickListener {
            if (contStart != 0.0) {
                contStart -= 0.5
                setStart(contStart)
            }
        }
    }

    private fun getData() {
        val bundle = intent.extras
        trip = bundle?.getParcelable("trip")
        booking = bundle?.getParcelable("booking")
        cleanBookings()
    }

    private fun cleanBookings() {
        trip?.bookings?.forEach { it ->
            if (!it.passenger?.email?.equals(userSession.email)!!) {
                bookingWithOutUser?.add(it)
            } else {
                it.valuationStatus = true
            }
        }
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
    private fun setStart(contStart: Double) {
        when {
            contStart.equals(0.0)!! -> {
                binding.IVStart1Organizer.setImageResource(R.mipmap.withoutstart)
                binding.IVStart2Organizer.setImageResource(R.mipmap.withoutstart)
                binding.IVStart3Organizer.setImageResource(R.mipmap.withoutstart)
            }
            contStart.equals(0.5) -> {
                binding.IVStart1Organizer.setImageResource(R.mipmap.medstart)
                binding.IVStart2Organizer.setImageResource(R.mipmap.withoutstart)
                binding.IVStart3Organizer.setImageResource(R.mipmap.withoutstart)
            }
            contStart.equals(1.0) -> {
                binding.IVStart1Organizer.setImageResource(R.mipmap.star)
                binding.IVStart2Organizer.setImageResource(R.mipmap.withoutstart)
                binding.IVStart3Organizer.setImageResource(R.mipmap.withoutstart)
            }
            contStart.equals(1.5) -> {
                binding.IVStart1Organizer.setImageResource(R.mipmap.star)
                binding.IVStart2Organizer.setImageResource(R.mipmap.medstart)
                binding.IVStart3Organizer.setImageResource(R.mipmap.withoutstart)
            }
            contStart.equals(2.0) -> {
                binding.IVStart1Organizer.setImageResource(R.mipmap.star)
                binding.IVStart2Organizer.setImageResource(R.mipmap.star)
                binding.IVStart3Organizer.setImageResource(R.mipmap.withoutstart)
            }
            contStart.equals(2.5) -> {
                binding.IVStart1Organizer.setImageResource(R.mipmap.star)
                binding.IVStart2Organizer.setImageResource(R.mipmap.star)
                binding.IVStart3Organizer.setImageResource(R.mipmap.medstart)
            }
            contStart.equals(3.0) -> {
                binding.IVStart1Organizer.setImageResource(R.mipmap.star)
                binding.IVStart2Organizer.setImageResource(R.mipmap.star)
                binding.IVStart3Organizer.setImageResource(R.mipmap.star)
            }
        }
    }
}