package com.example.climby.data.model.province

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ProvinceModel(
        @SerializedName("name") val name: String?
        ): Parcelable {
        constructor(parcel: Parcel) : this(parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(name)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<ProvinceModel> {
                override fun createFromParcel(parcel: Parcel): ProvinceModel {
                        return ProvinceModel(parcel)
                }

                override fun newArray(size: Int): Array<ProvinceModel?> {
                        return arrayOfNulls(size)
                }
        }
}
