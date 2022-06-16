package com.app.climby.data.model.school

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SchoolModel(
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

        companion object CREATOR : Parcelable.Creator<SchoolModel> {
                override fun createFromParcel(parcel: Parcel): SchoolModel {
                        return SchoolModel(parcel)
                }

                override fun newArray(size: Int): Array<SchoolModel?> {
                        return arrayOfNulls(size)
                }
        }
}