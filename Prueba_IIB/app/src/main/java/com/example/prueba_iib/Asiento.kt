package com.example.prueba_iib

import android.annotation.SuppressLint
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

class Asiento(
    val numeroDeAsiento: Int,
    val ocupado: Boolean,
    val tipo: String?,
    val precio: Double,
    val fechaReservacion: String?

) : Parcelable {
    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readBoolean(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString()
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(numeroDeAsiento)
        parcel.writeBoolean(ocupado)
        parcel.writeString(tipo)
        parcel.writeDouble(precio)
        parcel.writeString(fechaReservacion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Asiento> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Asiento {
            return Asiento(parcel)
        }

        override fun newArray(size: Int): Array<Asiento?> {
            return arrayOfNulls(size)
        }
    }
}