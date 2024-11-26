package mbenedictt.paba.todolist

import android.os.Parcel
import android.os.Parcelable

data class task(
    var taskName : String?,
    var taskStatus : String?,
    var taskDate : String?,
    var taskDesc : String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(taskName)
        parcel.writeString(taskStatus)
        parcel.writeString(taskDate)
        parcel.writeString(taskDesc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<task> {
        override fun createFromParcel(parcel: Parcel): task {
            return task(parcel)
        }

        override fun newArray(size: Int): Array<task?> {
            return arrayOfNulls(size)
        }
    }
}
