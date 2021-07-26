package id.ac.polman.astra.kelompok2.financialrecords.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.model.KategoriModel;

public class DialogForm extends DialogFragment {

    String namakategori;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogForm(String namakategori) {
        this.namakategori = namakategori;
    }

    public DialogForm(){

    }

    TextView et_nama;
    Button btn_simpan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.form_kategori, container, false);

        et_nama = view.findViewById(R.id.et_nama);
        et_nama.setText(namakategori);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namakategori = et_nama.getText().toString();

                if(TextUtils.isEmpty(namakategori)){
                    input((EditText) et_nama, "Nama");
                }
                else
                {
                    database.child("Data").push().setValue(new KategoriModel(namakategori)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(view.getContext(), "Data tersimpan", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Data tersimpan", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }

    public void onStart(){
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void input (EditText txt, String s){
        txt.setError(s+"tidak boleh kosong");
        txt.requestFocus();
    }
}
