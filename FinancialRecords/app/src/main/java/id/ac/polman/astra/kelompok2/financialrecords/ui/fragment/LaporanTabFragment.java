package id.ac.polman.astra.kelompok2.financialrecords.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.adapter.RecyclerAdapter;
import id.ac.polman.astra.kelompok2.financialrecords.model.KategoriModel;

public class LaporanTabFragment extends Fragment {
    private View objectKTF;
    //FloatingActionButton fab_tambah;

    List<KategoriModel> listKategori = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    RecyclerAdapter mRecyclerAdapter;

    RecyclerView rv_view_laporan;
    CardView cv_kategori;
    TextView total_pemasukan;
    TextView total_pengeluaran;
    TextView selisih;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objectKTF = inflater.inflate(R.layout.fragment_laporan_list, container, false);

        //fab_tambah = objectKTF.findViewById(R.id.fab_tambah);
        rv_view_laporan = objectKTF.findViewById(R.id.rv_view_laporan);
        cv_kategori = objectKTF.findViewById(R.id.cv_kategori);
        total_pemasukan = objectKTF.findViewById(R.id.total_pemasukan);
        total_pengeluaran = objectKTF.findViewById(R.id.total_pengeluaran);
        selisih = objectKTF.findViewById(R.id.selisih);

        total_pemasukan.setText("Total Pemasukan                  Rp200.000");
        total_pengeluaran.setText("Total Pengeluaran                Rp50.000");
        selisih.setText("Selisih                                    Rp150.000");

        db = FirebaseFirestore.getInstance();

        rv_view_laporan.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rv_view_laporan.setLayoutManager(layoutManager);

        //rv_view.setItemAnimator(new DefaultItemAnimator());

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

                        mRecyclerAdapter = new RecyclerAdapter( LaporanTabFragment.this, listKategori);
                        rv_view_laporan.setAdapter(mRecyclerAdapter);
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
