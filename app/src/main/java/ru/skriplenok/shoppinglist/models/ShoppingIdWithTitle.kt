package ru.skriplenok.shoppinglist.models

import android.os.Parcel
import android.os.Parcelable

data class ShoppingIdWithTitle(val id: Int, val title: String?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShoppingIdWithTitle> {
        override fun createFromParcel(parcel: Parcel): ShoppingIdWithTitle {
            return ShoppingIdWithTitle(parcel)
        }

        override fun newArray(size: Int): Array<ShoppingIdWithTitle?> {
            return arrayOfNulls(size)
        }
    }

}