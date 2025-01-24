package com.example.prueba_iib

import android.annotation.SuppressLint
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi


class SalaDeCine(
    var id: Int,
    var nombre_de_la_sala_de_cine: String?,
    var numero_de_asientos: Int,
    var habilitada: Boolean,
    var horario_apertura: String?

) : Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre_de_la_sala_de_cine)
        parcel.writeInt(numero_de_asientos)
        parcel.writeByte(if (habilitada) 1 else 0)
        parcel.writeString(horario_apertura)
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