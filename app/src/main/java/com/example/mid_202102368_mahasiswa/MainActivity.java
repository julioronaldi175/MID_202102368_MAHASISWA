package com.example.mid_202102368_mahasiswa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editNim, editName, editEmail, editAlamat, editJk;
    Button btnSimpan, btnTampil, btnEdit, btnHapus;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editNim = findViewById(R.id.editNIM);
        editName = findViewById(R.id.editNama);
        editJk = findViewById(R.id.editJK);
        editAlamat = findViewById(R.id.editAlamat);
        editEmail = findViewById(R.id.editEmail);

        db = new DBHelper(this);

        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilData();
                if(res.getCount() == 0){
                    Toast.makeText(getApplicationContext(), "Tidak ada data.", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("NIM Mahasiswa: " + res.getString(0) + "\n");
                    buffer.append("Nama Mahasiswa: " + res.getString(1) + "\n");
                    buffer.append("Jenis Kelamin Mahasiswa: " + res.getString(2) + "\n");
                    buffer.append("Alamat Mahasiswa: " + res.getString(3) + "\n");
                    buffer.append("Email Mahasiswa: " + res.getString(4) + "\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Biodata Mahasiswa");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nim = editNim.getText().toString();
                String name = editName.getText().toString();
                String jk = editJk.getText().toString();
                String alamat = editAlamat.getText().toString();
                String email = editEmail.getText().toString();

                if (TextUtils.isEmpty(nim) || TextUtils.isEmpty(name) || TextUtils.isEmpty(jk) || TextUtils.isEmpty(alamat) || TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Tolong isi semua data mahasiswa!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkNim = db.checkNIM(nim);
                    if (checkNim) {
                        // NIM sudah ada
                        Toast.makeText(getApplicationContext(), "Mahasiswa sudah ada!", Toast.LENGTH_SHORT).show();

                    } else {
                        Boolean insert = db.insertData(nim, name, jk, alamat, email);
                        if (insert) {
                            Toast.makeText(getApplicationContext(), "Data mahasiswa berhasil ditambahkan!", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(getApplicationContext(), BiodataActivity.class);
                            startActivity(i);
                        } else {

                            Toast.makeText(getApplicationContext(), "Gagal menambahkan mahasiswa!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}