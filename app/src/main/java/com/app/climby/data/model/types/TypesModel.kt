package com.app.climby.data.model.types

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class TypesModel(
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

    companion object CREATOR : Parcelable.Creator<TypesModel> {
        override fun createFromParcel(parcel: Parcel): TypesModel {
            return TypesModel(parcel)
        }

        override fun newArray(size: Int): Array<TypesModel?> {
            return arrayOfNulls(size)
        }
    }
}
