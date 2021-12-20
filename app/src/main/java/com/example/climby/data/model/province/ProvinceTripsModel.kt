package com.example.climby.data.model.province

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ProvinceTripsModel(
    @SerializedName("name") val name: String?,
    @SerializedName("tripsNumber") var tripsNumber: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(tripsNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProvinceTripsModel> {
        override fun createFromParcel(parcel: Parcel): ProvinceTripsModel {
            return ProvinceTripsModel(parcel)
        }

        override fun newArray(size: Int): Array<ProvinceTripsModel?> {
            return arrayOfNulls(size)
        }
    }
}
