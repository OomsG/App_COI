package be.kdg.cityofideas.model.ideations

import com.google.gson.annotations.SerializedName

enum class VoteType() {
    @SerializedName("0")VOTE,
    @SerializedName("1")SHARE_FB,
    @SerializedName("2")SHARE_TW,
    @SerializedName("3")IOT
}