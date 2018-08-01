package org.fossasia.susi.ai.rest.responses.susi

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * <h1>Kotlin Data class to parse identity object in retrofit response from susi client.</h1>
 */
@Parcelize
class Identity : Parcelable {

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("type")
    @Expose
    val type: String? = null

    @SerializedName("anonymous")
    @Expose
    val anonymous: Boolean = false
}