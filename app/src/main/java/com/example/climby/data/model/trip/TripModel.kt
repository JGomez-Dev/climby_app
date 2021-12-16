package com.example.climby.data.model.trip

import android.os.Parcel
import android.os.Parcelable
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.province.ProvinceModel
import com.example.climby.data.model.school.SchoolModel
import com.example.climby.data.model.types.TypesModel
import com.example.climby.data.model.user.UserModel
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class TripModel(
    @SerializedName("id") val id: Int,
    @SerializedName("school") val site: SchoolModel?,
    @SerializedName("ClimbingType") val type: TypesModel?,
    @SerializedName("numberSeats") val availablePlaces: Int,
    @SerializedName("Date") val departure: String?,
    @SerializedName("idProvince") val province: ProvinceModel?,
    @SerializedName("driver") val driver: UserModel?,
    @SerializedName("reservation") var bookings: ArrayList<BookingModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(SchoolModel::class.java.classLoader),
        parcel.readParcelable(TypesModel::class.java.classLoader),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(ProvinceModel::class.java.classLoader),
        parcel.readParcelable(UserModel::class.java.classLoader),
        parcel.createTypedArrayList(BookingModel)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(site, flags)
        parcel.writeParcelable(type, flags)
        parcel.writeInt(availablePlaces)
        parcel.writeString(departure)
        parcel.writeParcelable(province, flags)
        parcel.writeParcelable(driver, flags)
        parcel.writeTypedList(bookings)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TripModel> {
        override fun createFromParcel(parcel: Parcel): TripModel {
            return TripModel(parcel)
        }

        override fun newArray(size: Int): Array<TripModel?> {
            return arrayOfNulls(size)
        }
    }
}