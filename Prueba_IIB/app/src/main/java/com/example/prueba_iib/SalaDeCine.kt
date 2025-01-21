package com.example.prueba_iib

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi


class SalaDeCine(
    var nombreDeLaSalaDeCine: String?,
    var numeroDeAsientos: Int,
    var habilitada: Boolean,
    var horarioApertura: String?

) : Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readBoolean(),
        parcel.readString()

    )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombreDeLaSalaDeCine)
        parcel.writeInt(numeroDeAsientos)
        parcel.writeBoolean(habilitada)
        parcel.writeString(horarioApertura)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SalaDeCine> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): SalaDeCine {
            return SalaDeCine(parcel)
        }

        override fun newArray(size: Int): Array<SalaDeCine?> {
            return arrayOfNulls(size)
        }
    }
}