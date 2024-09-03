package com.example.facebook_posts

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.facebook_posts.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var postsAdapter: PostsAdapter
    private val posts = mutableListOf<Post>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()

        //EXAMPLE 1
        binding.btnOpenWebPage.setOnClickListener {
            openWebPage()
        }

        //EXAMPLE 2
        //Resource URI:are used to reference resources bundled with the application’s APK, such as images, audio files, or other raw resources.
        val imageUri = Uri.parse("android.resource://$packageName/drawable/${R.drawable.lovely}")

        binding.image.setImageURI(imageUri)

        Log.e(
            "TAG",
            " uri: $imageUri parsed: scheme: ${imageUri.scheme},authority: ${imageUri.authority},path: ${imageUri.path}"
        )
    }

    private fun setRecyclerView() {
        createPostsList()
        postsAdapter = PostsAdapter(posts)
        binding.rvPosts.apply {
            adapter = postsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        postsAdapter.onShareClick = { post ->
            sharePost(post)
        }
    }

    //EXAMPLE 3
    private fun sharePost(post: Post) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Check out this amazing post ${post.content} https://facebook.com"
            )
            val uri = getUri(post.image)
            Log.e(
                "TAG",
                " uri: $uri parsed: scheme: ${uri.scheme},authority: ${uri.authority},path: ${uri.path}"
            )
            putExtra(Intent.EXTRA_STREAM, uri)
            //specify type text/plain  , image/*
            type = "*/*"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION // Grant read access to the URI
        }

        /*
        A MIME type (Multipurpose Internet Mail Extensions type) is a standardized way to indicate the nature and format of a file or data. MIME types are used by various internet protocols (such as HTTP, email, and more) to identify the type of content being sent so that it can be properly handled by the receiving system.

            Structure of a MIME Type
            A MIME type consists of two parts:
                        type/subtype

            Type: This is the general category of the data (e.g., text, image, application).
            Subtype: This indicates the specific format within that category (e.g., html, jpeg, pdf).

            Examples of Common MIME Types
          //  Text Files:
            text/plain: Plain text
            text/html: HTML document
            text/css: Cascading Style Sheets (CSS)

         //   Image Files:
            image/jpeg: JPEG image
            image/png: PNG image
            image/gif: GIF image

         //   Application Files:
            application/json: JSON data
            application/pdf: PDF document
            application/zip: ZIP archive

         //   Audio/Video Files:
            audio/mpeg: MP3 audio file
            video/mp4: MP4 video file
            audio/wav: WAV audio file
         */

        val chooser = Intent.createChooser(intent, "Share this post via...")

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        } else {
            Toast.makeText(this, "No apps available to share content", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUri(postImage: Int): Uri {

        // how to share drawable file in an intent ? prepare a file to be represented by a uri that we will send in the intent
        // convert a drawable resource into a bitmap
        // convert bitmap to a PNG image file,
        // saves it to the app's cache directory,
        // and then returns a URI that can be used to access this file securely.
        val bitmap = BitmapFactory.decodeResource(resources, postImage)
        val cachePath = File(cacheDir, "images")
        cachePath.mkdirs() // Create the directory if it doesn't exist
        val file = File(cachePath, "${postImage}.png")
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()

        // Get the URI of the saved file using FileProvider
        return FileProvider.getUriForFile(this, "${packageName}.fileprovider", file)

    }

    private fun openWebPage() {
        //URL (Uniform Resource Locator) is a specific type of URI that provides the location of a resource on the web.
        val url = "https://olympics.com/en/athletes/"
        //URI : Uniform Resource Identifier -> string represents resources on the device, images, audio, videos, files
        //  use ? to locate and access various types of data and resources.
        //  [ scheme:authority/path ]
        //  Scheme: Indicates the protocol or scheme used to access the resource (e.g., “http”, “file”, “content”).
        //  Authority: Specifies the domain or source of the resource (e.g., website domain, file system path).
        // Path: Represents the specific location or identifier of the resource within the authority.
        // URI Types:  url , content uri , file uri , resource uri , intent uri
        //Content URIs:  are primarily used to access data managed by content providers.
        //File URIs:  are used to represent files stored on the device’s storage.
        //Resource URI:are used to reference resources bundled with the application’s APK, such as images, audio files, or other raw resources.

        val uri = Uri.parse(url)
        Log.e(
            "TAG",
            " uri: $uri parsed: scheme: ${uri.scheme},authority: ${uri.authority},path: ${uri.path}"
        )
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val chooser = Intent.createChooser(intent, "open web page by ..")
        // packageManager is a system service that provides information about installed packages (apps) and their components.
        if (intent.resolveActivity(packageManager) != null)//will not show chooser dialog if one app only can handle intent or if a default app is set to handle such events
            startActivity(chooser)

    }

    private fun createPostsList() {

        for (i in 1..10) {

            posts.add(
                Post(
                    author = "Nadia Seleem",
                    date = getCurrentDateTime().toString("yyyy/MM/dd HH:mm:ss"),
                    content = "This app is great! :)",
                    image = R.drawable.happy
                )
            )
            posts.add(
                Post(
                    author = "Nadia Seleem",
                    date = getCurrentDateTime().toString("yyyy/MM/dd HH:mm:ss"),
                    content = "This app is fantastic! :)",
                    image = R.drawable.excited
                )
            )
            posts.add(
                Post(
                    author = "Nadia Seleem",
                    date = getCurrentDateTime().toString("yyyy/MM/dd HH:mm:ss"),
                    content = "This app is awesome! :)",
                    image = R.drawable.lovely
                )
            )
        }

    }

}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}