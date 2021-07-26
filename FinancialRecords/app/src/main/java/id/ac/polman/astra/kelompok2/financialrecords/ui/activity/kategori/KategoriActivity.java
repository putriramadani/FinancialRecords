package id.ac.polman.astra.kelompok2.financialrecords.ui.activity.kategori;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.model.KategoriModel;
import id.ac.polman.astra.kelompok2.financialrecords.utils.DialogForm;

public class KategoriActivity extends AppCompatActivity {
    FloatingActionButton fab_tambah;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_kategori_list);

        fab_tambah = findViewById(R.id.fab_tambah);

        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm dialogForm = new DialogForm();
                dialogForm.show(getSupportFragmentManager(), "form");
            }
        });
    }
}
