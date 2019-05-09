package be.kdg.cityofideas.model.surveys

import com.google.gson.annotations.SerializedName

enum class QuestionType() {
    @SerializedName("0")OPEN,
    @SerializedName("1")RADIO,
    @SerializedName("2")CHECK,
    @SerializedName("3")DROP,
    @SerializedName("4")EMAIL
}