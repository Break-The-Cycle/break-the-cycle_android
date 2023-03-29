package kau.brave.breakthecycle.contentprovider

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import kau.brave.breakthecycle.R
import java.io.File

class CameraFileProvider : FileProvider(
    R.xml.camera_filepaths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory,
            )
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}