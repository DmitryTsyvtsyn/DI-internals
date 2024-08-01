package io.github.dmitrytsyvtsyn.core.model

import android.os.Parcel
import android.os.Parcelable

@JvmInline
value class IdLong(val value: Long): Parcelable {

    val isEmpty: Boolean
        get() = value <= MIN_POSSIBLE_VALUE

    val isNotEmpty: Boolean
        get() = !isEmpty

    constructor(parcel: Parcel) : this(parcel.readLong())

    init {
        require(value >= MIN_POSSIBLE_VALUE) {
            "id must not be less than $MIN_POSSIBLE_VALUE"
        }
    }

    companion object {
        private const val MIN_POSSIBLE_VALUE = -1L

        val Empty = IdLong(MIN_POSSIBLE_VALUE)

        @JvmField
        val CREATOR = object : Parcelable.Creator<IdLong> {
            override fun createFromParcel(parcel: Parcel): IdLong {
                return IdLong(parcel)
            }

            override fun newArray(size: Int): Array<IdLong?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(value)
    }

    override fun describeContents(): Int {
        return 0
    }
}