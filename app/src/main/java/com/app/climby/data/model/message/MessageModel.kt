package com.app.climby.data.model.message

import android.os.Parcel
import android.os.Parcelable
import com.app.climby.data.model.user.UserModel
import com.google.gson.annotations.SerializedName

data class MessageModel(
    @SerializedName("read") val read:  Boolean?,
    @SerializedName("text") val text: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(read)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageModel> {
        override fun createFromParcel(parcel: Parcel): MessageModel {
            return MessageModel(parcel)
        }

        override fun newArray(size: Int): Array<MessageModel?> {
            return arrayOfNulls(size)
        }
    }
}

