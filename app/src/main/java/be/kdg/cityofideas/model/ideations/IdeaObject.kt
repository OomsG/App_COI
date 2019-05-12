package be.kdg.cityofideas.model.ideations

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Bitmap.CompressFormat
import java.io.ByteArrayOutputStream

//region Toplevel Functions Bitmap Utility
// convert from bitmap to byte array
fun getBytes(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(CompressFormat.PNG, 0, stream)
    return stream.toByteArray()
}

// convert from byte array to bitmap
fun getImage(image: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(image, 0, image.size)
}
//endregion

data class IdeaObject(
    val IdeaObjectId : Int,
    val OrderNr: Int,
    val Discriminator: String?,
    val ImageName: String?,
    val ImagePath: String?,
    val ImageData: ByteArray?,
    var Image: Bitmap?,
    val Url: String?,
    val Text: String?
)