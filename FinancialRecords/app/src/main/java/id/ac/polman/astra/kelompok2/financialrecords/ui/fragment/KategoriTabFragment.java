package id.ac.polman.astra.kelompok2.financialrecords.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.polman.astra.kelompok2.financialrecords.Entity.UserEntity;
import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.adapter.RecyclerAdapter;
import id.ac.polman.astra.kelompok2.financialrecords.model.KategoriModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.ResponseModel;
import id.ac.polman.astra.kelompok2.financialrecords.utils.Preference;

public class KategoriTabFragment extends Fragment {
    private View objectKTF;
    FloatingActionButton fab_tambah;

    List<KategoriModel> listKategori = new ArrayList<>();
    RecyclerView rv_view;
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    RecyclerAdapter mRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objectKTF = inflater.inflate(R.layout.fragment_kategori_list, container, false);

        fab_tambah = objectKTF.findViewById(R.id.fab_tambah);
        rv_view = objectKTF.findViewById(R.id.rv_view);

        db = FirebaseFirestore.getInstance();

        rv_view.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rv_view.setLayoutManager(layoutManager);
        //rv_view.setItemAnimator(new DefaultItemAnimator());

        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KategoriDialogFragment dialogForm = new KategoriDialogFragment("","", 0,"Tambah");
                dialogForm.show(getFragmentManager(), "form");
            }
        });
        showData();
        return objectKTF;
    }

    private void showData(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //DocumentReference docRef = db.collection("user");
        db.collection("user").document(firebaseUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        //show data
                        DocumentSnapshot doc = task.getResult();
                        //for(DocumentSnapshot doc : task.getResult()){
                        List<String> listpem = (List<String>) doc.get("pemasukan");
                        List<String> listpen = (List<String>) doc.get("pengeluaran");
                        for(int i=0;i<listpem.size();i++) {
                            KategoriModel kList = new KategoriModel(
                                    listpem.get(i),
                                    i
                            );
                            kList.setJenis("Pemasukan");
                            listKategori.add(kList);
                            kList.setKey(i);
                        }
                        for(int i=0;i<listpen.size();i++) {
                            KategoriModel kList = new KategoriModel(
                                    listpen.get(i),
                                    i
                            );
                            kList.setJenis("Pengeluaran");
                            listKategori.add(kList);
                            kList.setKey(i);
                        }

                        mRecyclerAdapter = new RecyclerAdapter( KategoriTabFragment.this, listKategori);
                        rv_view.setAdapter(mRecyclerAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(KategoriTabFragment.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
