package ru.skriplenok.shoppinglist.models

import android.os.Parcel
import android.os.Parcelable

class ShoppingModel(
    val id: Int,
    val name: String?,
    val count: String?
): Parcelable  {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShoppingModel> {
        override fun createFromParcel(parcel: Parcel): ShoppingModel {
            return ShoppingModel(parcel)
        }

        override fun newArray(size: Int): Array<ShoppingModel?> {
            return arrayOfNulls(size)
        }
    }
}