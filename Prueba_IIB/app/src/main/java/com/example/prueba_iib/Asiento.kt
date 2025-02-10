package com.example.prueba_iib

import android.annotation.SuppressLint
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

class Asiento(
    val id: Int,
    val ocupado: Boolean,
    val tipo: String?,
    val precio: Double,
    val sala_de_cine_id: Int

) : Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt()
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeByte(if (ocupado) 1 else 0)
        parcel.writeString(tipo)
        parcel.writeDouble(precio)
        parcel.writeInt(sala_de_cine_id)
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