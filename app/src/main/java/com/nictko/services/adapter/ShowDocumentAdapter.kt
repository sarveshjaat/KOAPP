package com.nictko.services.adapter

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nictko.services.R
import com.nictko.services.response.showdocumentresp.Data
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class ShowDocumentAdapter(private var items: List<Data>) :
    RecyclerView.Adapter<ShowDocumentAdapter
    .ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val settlementId: TextView = itemView.findViewById(R.id.tv_settlement_id)
//        val settlementDate: TextView = itemView.findViewById(R.id.tv_settlement_date)
        val tvnoticedtl: TextView = itemView.findViewById(R.id.tv_documentname)
        val imgdocument: ImageView = itemView.findViewById(R.id.tv_imgdocument)
        val imgdocumentpdf: ImageView = itemView.findViewById(R.id.tv_imgdocumentpdf)
        val imgdocumentexcel: ImageView = itemView.findViewById(R.id.tv_imgdocumentexcel)
        val imgdocumentcdr: ImageView = itemView.findViewById(R.id.tv_imgdocumentcdr)
        val imgbuttion: ImageView = itemView.findViewById(R.id.btn_download)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notice_documet_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.tvnoticedtl.text = item.documentName.toString()
        val fileName: String = items[position].documentFile.toString()
        Log.e("sayy", "documentFile" + item.documentFile);
        val fileLower: String = items.get(position).documentFile.toString()

        if (fileLower.endsWith(".jpg") || fileLower.endsWith(".jpeg") || fileLower.endsWith(".png")) {

            val fullImageUrl =
                "https://nict.ind.in/KoPortal/uploads/" + items[position].documentFile
            holder.imgdocument.visibility = View.VISIBLE
            holder.imgdocumentpdf.visibility = View.GONE
            holder.imgdocumentexcel.visibility = View.GONE

            Glide.with(holder.itemView.context)
                .load(fullImageUrl)
                .placeholder(R.drawable.placeholder) // Optional
                .into(holder.imgdocument)
            holder.imgbuttion.setOnClickListener {

                downloadImage(
                    holder.itemView.context,
                    fullImageUrl,
                    item.documentFile ?: "downloaded_image.jpg"
                )
            }

        } else if (fileLower.endsWith(".pdf")) {
            holder.imgdocument.visibility = View.GONE
            holder.imgdocumentexcel.visibility = View.GONE

            holder.imgdocumentpdf.visibility = View.VISIBLE
            holder.imgdocumentpdf.setImageResource(R.drawable.pdf)

            val fullFileUrl = "https://nict.ind.in/KoPortal/uploads/" + items[position].documentFile
            holder.imgbuttion.setOnClickListener {
                downloadFile(
                    holder.itemView.context,
                    fullFileUrl,
                    item.documentFile ?: "downloaded_file.pdf",
                    "application/pdf"
                )
            }

        }
        else if (fileLower.endsWith(".csv") || fileLower.endsWith(".xls")  || fileLower.endsWith(".xlsx")) {
            holder.imgdocument.visibility = View.GONE
            holder.imgdocumentpdf.visibility = View.GONE

            holder.imgdocumentexcel.visibility = View.VISIBLE
            holder.imgdocumentexcel.setImageResource(R.drawable.excel)

            val fullFileUrl = "https://nict.ind.in/KoPortal/uploads/" + items[position].documentFile

            holder.imgbuttion.setOnClickListener {
                // Detect correct MIME type based on file extension
                val mimeType = if (fileLower.endsWith(".xls")) {
                    "application/vnd.ms-excel"
                } else {
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                }

                downloadFileexcel(
                    holder.itemView.context,
                    fullFileUrl,
                    item.documentFile ?: "file.xlsx",
                    mimeType
                )
            }

        }
        else if(fileLower.endsWith(".cdr") )
        {
            holder.imgdocument.visibility = View.GONE
            holder.imgdocumentpdf.visibility = View.GONE
            holder.imgdocumentexcel.visibility = View.GONE
            holder.imgdocumentcdr.visibility = View.VISIBLE
            holder.imgdocumentcdr.setImageResource(R.drawable.cdr)

            val fullFileUrl = "https://nict.ind.in/KoPortal/uploads/" + items[position].documentFile

            holder.imgbuttion.setOnClickListener {
                // Detect correct MIME type based on file extension

                val mimeType = "application/x-coreldraw"

                downloadFilecdr(
                    holder.itemView.context,
                    fullFileUrl,
                    item.documentFile ?: "file.cdr",
                    mimeType
                )

            }

        }




    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: ArrayList<Data>) {
        items = newItems
        notifyDataSetChanged()
    }

    private fun downloadImage(context: Context, imageUrl: String, fileName: String) {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Downloading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        Thread {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val input = connection.inputStream

                // Scoped Storage: Always save in Downloads directory
                val downloadsDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()

                val file = File(downloadsDir, fileName)
                val output = FileOutputStream(file)

                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                }

                output.close()
                input.close()

                // Update gallery / file visibility
                MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), null, null)

                android.os.Handler(Looper.getMainLooper()).post {
                    progressDialog.dismiss()
                    Toast.makeText(context, "Saved to Downloads", Toast.LENGTH_SHORT).show()

                    // Open image after download
                    val uri: Uri = FileProvider.getUriForFile(
                        context,
                        context.packageName + ".provider",
                        file
                    )
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(uri, "image/*")
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    context.startActivity(intent)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                android.os.Handler(Looper.getMainLooper()).post {
                    progressDialog.dismiss()
                    Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun downloadFile(
        context: Context,
        fileUrl: String,
        fileName: String,
        mimeType: String
    ) {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Downloading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        Thread {
            try {
                val url = URL(fileUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val input = connection.inputStream
                val downloadsDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()

                val file = File(downloadsDir, fileName)
                val output = FileOutputStream(file)

                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                }

                output.close()
                input.close()

                MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), null, null)

                android.os.Handler(Looper.getMainLooper()).post {
                    progressDialog.dismiss()
                    Toast.makeText(context, "Saved to Downloads", Toast.LENGTH_SHORT).show()

                    val uri: Uri = FileProvider.getUriForFile(
                        context,
                        context.packageName + ".provider",
                        file
                    )
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(uri, mimeType)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    context.startActivity(intent)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                android.os.Handler(Looper.getMainLooper()).post {
                    progressDialog.dismiss()
                    Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun downloadFileexcel(context: Context, fileUrl: String, fileName: String, mimeType: String) {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Downloading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        Thread {
            try {
                val url = URL(fileUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val input = connection.inputStream
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()

                val file = File(downloadsDir, fileName)
                val output = FileOutputStream(file)

                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                }

                output.close()
                input.close()

                MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), null, null)

                android.os.Handler(Looper.getMainLooper()).post {
                    progressDialog.dismiss()
                    Toast.makeText(context, "Excel saved to Downloads", Toast.LENGTH_SHORT).show()

                    // Open Excel after download
                    val uri: Uri = FileProvider.getUriForFile(
                        context,
                        context.packageName + ".provider",
                        file
                    )
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(uri, mimeType)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    context.startActivity(intent)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                android.os.Handler(Looper.getMainLooper()).post {
                    progressDialog.dismiss()
                    Toast.makeText(context, "Excel download failed", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }



    private fun downloadFilecdr(context: Context, fileUrl: String, fileName: String, mimeType: String) {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Downloading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        Thread {
            try {
                val url = URL(fileUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val input = connection.inputStream
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()

                val file = File(downloadsDir, fileName)
                val output = FileOutputStream(file)

                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                }

                output.close()
                input.close()

                MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), null, null)

                android.os.Handler(Looper.getMainLooper()).post {
                    progressDialog.dismiss()
                    Toast.makeText(context, "Saved to Downloads", Toast.LENGTH_SHORT).show()

                    // Try to open CDR in CorelDRAW or any supported viewer
                    val uri: Uri = FileProvider.getUriForFile(
                        context,
                        context.packageName + ".provider",
                        file
                    )
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(uri, mimeType)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "No app found to open CDR file", Toast.LENGTH_LONG).show()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                android.os.Handler(Looper.getMainLooper()).post {
                    progressDialog.dismiss()
                    Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }


}