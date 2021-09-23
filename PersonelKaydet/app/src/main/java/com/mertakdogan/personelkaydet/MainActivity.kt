package com.mertakdogan.personelkaydet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mertakdogan.personelkaydet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var database = FirebaseDatabase.getInstance().reference

        //veri ekleme işlemi
        binding.btnEkle.setOnClickListener {
            var etno = binding.etNo.text.toString().toInt()
            var etadsoyad = binding.etAdSoyad.text.toString()
            var etmaas =  binding.etMaas.text.toString().toInt()

            //database.setValue(Personel(etadsoyad,etmaas))
            database.child(etno.toString()).setValue(Personel(etadsoyad,etmaas))
        }

        var getdata = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //veri tabanına eklenmiş olan verilerin görüntülerini bize gösteriyor.
                var sb = StringBuilder() //metin birleştirme sınıfı
                for (i in snapshot.children){
                    var adsoyad = i.child("padsoyad").getValue()
                    var maas = i.child("pmaas").getValue()
                    sb.append("${i.key} $adsoyad $maas \n")
                }
                binding.tvSonuc.setText(sb)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)
    }
}