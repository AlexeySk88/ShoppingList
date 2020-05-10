package ru.skriplenok.shoppinglist.models

import android.os.Parcel
import android.os.Parcelable

class ShoppingModel(
    private val id: Int?,
    private val name: String?,
    private val count: String?
): Parcelable  {

    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    fun getName(): String {
        return name ?: ""
    }

    fun getCount(): String {
        return count ?: ""
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