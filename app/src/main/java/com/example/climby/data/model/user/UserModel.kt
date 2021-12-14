package com.example.climby.data.model.user

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UserModel(
        @SerializedName("id") val id: Int,
        @SerializedName("fullName") val name: String?,
        @SerializedName("experience") val experience: String?,
        @SerializedName("phone") val phone: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("score") var score: Double,
        @SerializedName("ratings") var ratings: Int,
        @SerializedName("outputs") val outings: Int,
        @SerializedName("userPhoto") val photo: String?
        ): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readDouble(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeString(name)
                parcel.writeString(experience)
                parcel.writeString(phone)
                parcel.writeString(email)
                parcel.writeDouble(score)
                parcel.writeInt(ratings)
                parcel.writeInt(outings)
                parcel.writeString(photo)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<UserModel> {
                override fun createFromParcel(parcel: Parcel): UserModel {
                        return UserModel(parcel)
                }

                override fun newArray(size: Int): Array<UserModel?> {
                        return arrayOfNulls(size)
                }
        }
}
