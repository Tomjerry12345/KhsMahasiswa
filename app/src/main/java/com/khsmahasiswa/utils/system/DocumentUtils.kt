package com.khsmahasiswa.utils.system

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.util.DisplayMetrics
import android.util.LruCache
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.khsmahasiswa.BuildConfig
import com.khsmahasiswa.utils.other.showLogAssert
import com.khsmahasiswa.utils.other.showToast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class DocumentUtils(val activity: ComponentActivity) {

//    val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
    val path = activity.getExternalFilesDir(null)

    fun createBitmapFromLayout(v: View, width: Int, height: Int): Bitmap? {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        v.draw(canvas)
        return bitmap
    }

    fun createBitmapRecyclerView(recyclerView: RecyclerView): Bitmap {
        recyclerView.measure(
            View.MeasureSpec.makeMeasureSpec(recyclerView.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )

        recyclerView.layoutManager?.scrollToPosition(0)

        val bitmap = Bitmap.createBitmap(
            recyclerView.width,
            recyclerView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )

        recyclerView.draw(Canvas(bitmap))

        return bitmap
    }

    fun getScreenshotFromRecyclerView(view: RecyclerView): Bitmap? {
        val adapter = view.adapter
        var bigBitmap: Bitmap? = null
        if (adapter != null) {
            val size = adapter.itemCount
            var height = 0
            val paint = Paint()
            var iHeight = 0
            val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

            // Use 1/8th of the available memory for this memory cache.
            val cacheSize = maxMemory / 8
            val bitmaCache: LruCache<String, Bitmap> = LruCache(cacheSize)
            for (i in 0 until size) {
                val holder = adapter.createViewHolder(view, adapter.getItemViewType(i))
                adapter.onBindViewHolder(holder, i)
                holder.itemView.measure(
                    View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                holder.itemView.layout(
                    0,
                    0,
                    holder.itemView.measuredWidth,
                    holder.itemView.measuredHeight
                )
                holder.itemView.isDrawingCacheEnabled = true
                holder.itemView.buildDrawingCache()
                val drawingCache = holder.itemView.drawingCache
                if (drawingCache != null) {
                    bitmaCache.put(i.toString(), drawingCache)
                }
                //                holder.itemView.setDrawingCacheEnabled(false);
//                holder.itemView.destroyDrawingCache();
                height += holder.itemView.measuredHeight
            }
            bigBitmap = Bitmap.createBitmap(view.measuredWidth, height, Bitmap.Config.ARGB_8888)
            val bigCanvas = Canvas(bigBitmap)
            bigCanvas.drawColor(Color.WHITE)
            for (i in 0 until size) {
                val bitmap: Bitmap = bitmaCache.get(i.toString())
                bigCanvas.drawBitmap(bitmap, 0f, iHeight.toFloat(), paint)
                iHeight += bitmap.height
                bitmap.recycle()
            }
        }
        return bigBitmap
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    fun createPdfFromBitmap(bitmap: Bitmap) {
        val windowManager = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        showLogAssert("width pdf", "$width")
        showLogAssert("height pdf", "$height")

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(width, height, 2).create()
        val page = pdfDocument.startPage(pageInfo)

        val canvas = page.canvas

        val paint = Paint()

        canvas.drawColor(Color.WHITE)

//        canvas.drawBitmap(bitmap, 0F, 0F, paint)


        canvas.drawPaint(paint)

        val bitmapNew: Bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)

        canvas.drawBitmap(bitmapNew, 0F, 0F, paint)

        pdfDocument.finishPage(page)

        val file = File(path, "Sample.pdf")

        try {
//            createFile(Uri.fromFile(file))
            pdfDocument.writeTo(FileOutputStream(file))
            showToast(activity, "succesfull download")
//            openPdf()
        } catch (e: IOException) {
            e.printStackTrace()
            showToast(activity, "something wrong try again $e")

            // after close document
            pdfDocument.close()
        }
    }

    fun createPdf() {
        val path = Environment.getExternalStorageDirectory().path + "/" + Environment.DIRECTORY_DOCUMENTS
//        val folder = File(path, "Khs Mahasiswa")
//        if (!folder.exists()) {
//            folder.mkdir()
//        }

        val pdfDocument = PdfDocument()

        try {
            val file = File(path, "sample.pdf")
//            file.createNewFile()

            val fOut = FileOutputStream(file)

            val document = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(500, 1000, 1).create()
            val page = document.startPage(pageInfo)

            val canvas = page.canvas
            val paint = Paint()

            // draw something on the page
            // draw something on the page


            canvas.drawText("test", 10f, 10f, paint)
            canvas.drawText("test 1", 10f, 20f, paint)
            canvas.drawLine(1F, 30F, 30F, 30F, paint)

            document.finishPage(page)
            document.writeTo(fOut)
            document.close()

//            createFile(Uri.fromFile(file))
//            pdfDocument.writeTo(FileOutputStream(file))
            showToast(activity, "succesfull download")
//            openPdf()
        } catch (e: IOException) {
            e.printStackTrace()
            showToast(activity, "something wrong try again $e")

            // after close document
            pdfDocument.close()
        }
    }

    val CREATE_FILE = 1

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createFile(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
            putExtra(Intent.EXTRA_TITLE, "invoice.pdf")

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker before your app creates the document.
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }
        activity.startActivityForResult(intent, CREATE_FILE)
    }

    fun openPdf() {
        val file = File(path, "Sample.pdf")
        if (file.exists()) {
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = Uri.fromFile(file)
            intent.setDataAndType(uri, "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            try {
                activity.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                showToast(activity, "No application for pdf view")
            }
        }
    }

    fun testCreateDocumentAndroid11() {
        val stringFilePath =
            Environment.getExternalStorageDirectory().path + "/Download/ProgrammerWorld.pdf"
        val file: File = File(stringFilePath)
    }

    fun shareFile(activity: ComponentActivity, noTeleponOrangtua: String? = null, key: String? = null) {
        val file = File(path, "Sample.pdf")
        val uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", file)
        val share = Intent()
        share.action = Intent.ACTION_SEND
        share.putExtra(Intent.EXTRA_STREAM, uri)
        val getFirstNumber = noTeleponOrangtua?.get(0)
//        noTeleponOrangtua?.get(0)?.let { showLogAssert("test", it.toString()) }
        var numberFormat = ""
        if (noTeleponOrangtua != null) {
            if (getFirstNumber == '0') {
                numberFormat = noTeleponOrangtua.drop(1)
                numberFormat = "62$numberFormat"
            }
            showLogAssert("numberFormat", numberFormat)
            share.putExtra("jid", "$numberFormat@s.whatsapp.net")
        }

        share.setPackage(key)
        share.type = "application/pdf"
        activity.startActivity(share)
    }

}
