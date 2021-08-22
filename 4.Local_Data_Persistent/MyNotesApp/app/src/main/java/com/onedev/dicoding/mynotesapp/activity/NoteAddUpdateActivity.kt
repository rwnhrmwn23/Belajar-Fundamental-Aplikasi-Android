package com.onedev.dicoding.mynotesapp.activity

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.onedev.dicoding.mynotesapp.R
import com.onedev.dicoding.mynotesapp.databinding.ActivityNoteAddUpdateBinding
import com.onedev.dicoding.mynotesapp.db.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.onedev.dicoding.mynotesapp.db.DatabaseContract.NoteColumns.Companion.DATE
import com.onedev.dicoding.mynotesapp.db.DatabaseContract.NoteColumns.Companion.DESCRIPTION
import com.onedev.dicoding.mynotesapp.db.DatabaseContract.NoteColumns.Companion.TITLE
import com.onedev.dicoding.mynotesapp.entity.Note
import com.onedev.dicoding.mynotesapp.helper.MappingHelper
import java.text.SimpleDateFormat
import java.util.*

class NoteAddUpdateActivity : AppCompatActivity(), View.OnClickListener {

    private var isEdit = false
    private var note: Note? = null
    private var position: Int = 0
    private lateinit var uriWithId: Uri
    private lateinit var binding: ActivityNoteAddUpdateBinding

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            note = Note()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            // Uri yang di dapatkan disini akan digunakan untuk ambil data dari provider
            // content://com.onedev.dicoding.mynotesapp/note/id

            uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + note?.id)

            val cursor = contentResolver.query(uriWithId, null, null, null, null)
            if (cursor != null) {
                note = MappingHelper.mapCursorToObject(cursor)
                cursor.close()
            }

            actionBarTitle = "Ubah"
            btnTitle = "Update"

            note?.let {
                binding.edtTitle.setText(it.title)
                binding.edtDescription.setText(it.description)
            }
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSubmit.text = btnTitle

        binding.btnSubmit.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
        } else {
            dialogTitle = "Hapus Note"
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder
            .setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { _, _ ->
                if (isDialogClose)
                    finish()
                else {
                    // Gunakan uriWithId untuk delete
                    // content://com.onedev.dicoding.mynotesapp/note/id
                    contentResolver.delete(uriWithId, null, null)
                    Toast.makeText(this, "Satu Item Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.cancel()
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnSubmit -> {
                val title = binding.edtTitle.text.toString().trim()
                val description = binding.edtDescription.text.toString().trim()

                if (title.isEmpty()) {
                    binding.edtTitle.error = "Field Can Not Blank"
                    return
                }

                val values = ContentValues()
                values.put(TITLE, title)
                values.put(DESCRIPTION, description)

                if (isEdit) {
                    // Gunakan uriWithId dari intent activity ini
                    // content://com.onedev.dicoding.mynotesapp/note/id
                    contentResolver.update(uriWithId, values, null, null)
                    Toast.makeText(this, "Satu Item Berhasil Diedit", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    // Gunakan content uri untuk insert
                    // content://com.onedev.dicoding.mynotesapp/note/
                    values.put(DATE, getCurrentDate())
                    contentResolver.insert(CONTENT_URI, values)
                    Toast.makeText(this, "Satu Item Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}