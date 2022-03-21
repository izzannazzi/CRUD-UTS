package com.tauhidizzan.crud_uts

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class PeminjamAdapter (val mCtx : Context, val layoutResId : Int, val wrgList :List<Peminjam>) :ArrayAdapter<Peminjam> (mCtx, layoutResId, wrgList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view :  View = layoutInflater.inflate(layoutResId, null)

        val  tvNama : TextView = view.findViewById(R.id.tv_nama)
        val  tvRuang : TextView = view.findViewById(R.id.tv_ruang)
        val  tvAEdit : TextView = view.findViewById(R.id.tv_edit)

        val  peminjam : Peminjam = wrgList[position]

        tvAEdit.setOnClickListener {
            showUpdateDialog(peminjam)
        }

        tvNama.text = peminjam.nama
        tvRuang.text = peminjam.asal

        return view
    }

    private fun showUpdateDialog(peminjam: Peminjam) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Edit Data Peminjam Kunci")

        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.update_dialog, null)

        val  etNama : EditText = view.findViewById<EditText>(R.id.et_nama)
        val  etAsal : EditText = view.findViewById<EditText>(R.id.et_asal)


        etNama.setText(peminjam.nama)
        etAsal.setText(peminjam.asal)
        builder.setView(view)

        builder.setPositiveButton("Update"){ p0, p1 ->

            val dbWrg = FirebaseDatabase.getInstance().getReference("Data Peminjam")

            //catch changes content for input into database
            val nama = etNama.text.toString().trim()
            val asal = etAsal.text.toString().trim()

            if(nama.isEmpty()){
                etNama.error = "Tolong isi Nama Peminjam !!"
                etNama.requestFocus()
                return@setPositiveButton
            }

            if(asal.isEmpty()){
                etAsal.error = "Tolong isi Nama Ruang !!"
                etAsal.requestFocus()
                return@setPositiveButton
            }

            val warga = Peminjam(peminjam.id, nama, asal)
            dbWrg.child(warga.id).setValue(warga)

            Toast.makeText(mCtx, "Data berhasil di update", Toast.LENGTH_SHORT).show()
        }

        builder.setNeutralButton("No"){p0, p1 ->
        }

        builder.setNegativeButton("Delete"){p0,p1 ->
            val dbWrga = FirebaseDatabase.getInstance().getReference("warga").child(peminjam.id)


            dbWrga.removeValue()


            Toast.makeText(mCtx, "Data berhasil di hapus", Toast.LENGTH_SHORT).show()
        }

        val alert = builder.create()
        alert.show()
    }
}