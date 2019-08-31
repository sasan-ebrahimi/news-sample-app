package com.s_ebrahimi.newssample.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Model for artice source
 */
@Parcelize
data class Source(
    var id: String?,
    var name: String?
) : Parcelable{

}
