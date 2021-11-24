package com.example.climby.data.network.trip

import com.example.climby.data.model.trip.TripModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TripService @Inject constructor(private val api: TripApiClient) {

    suspend fun getTrips(id: Int): List<TripModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getTrips(id)
            response.body() ?: emptyList()
        }
    }
   /* fun getAllTrips(): List<TripModel> {*/
        /*val user = UserModel(1, "Javier", 0, passenger = false, driver = false, phone = 685775927, "Javiergf1991@gmail.com", 3.0, 1, "",  emptySet(),  emptySet())
        val provinceModel = ProvinceModel(1, "Madrid")
        val trip1 = TripModel(1, user, "Albarracin", "Boulder", 3, Timestamp(System.currentTimeMillis()), "22/07/2021", provinceModel, emptySet())
        val user1 = UserModel(2, "Pepito", 0, passenger = false, driver = false, phone = 685775927, "Pepitogmail.com", 3.0, 1, "",  emptySet(),  emptySet())
        var idBooking = BookingModel.Id(1,1,1)
        val book = BookingModel(idBooking ,user1 , trip1, 1, Timestamp(System.currentTimeMillis()), "22/07/2021", 1,1)

        val books = mutableSetOf<BookingModel>()
        books.add(book)
        books.add(book)

        val list = mutableListOf<TripModel>()

        for (i in 6 downTo 0 step 1) {
            val trip = TripModel(i, user, "Albarracin", "Boulder", 3, Timestamp(System.currentTimeMillis()), "22/07/2021", provinceModel, books)
            list.add(trip)
        }*/
      /*  return *//*list*//* emptyList()

    }*/
    /*suspend fun getAllTrips():List<TripModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllTrips()
            response.body() ?: emptyList()
        }
    }*/
}