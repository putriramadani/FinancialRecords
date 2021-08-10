package id.ac.polman.astra.kelompok2.financialrecords.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.polman.astra.kelompok2.financialrecords.Entity.UserEntity;
import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.model.KategoriModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.ResponseModel;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.KategoriDialogFragment;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.KategoriTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.LaporanTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.utils.Preference;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    KategoriTabFragment ktr;
    LaporanTabFragment ltr;
    List<KategoriModel> kategoriList;

    public RecyclerAdapter(KategoriTabFragment ktr, List<KategoriModel> kategoriList){
        this.ktr = ktr;
        this.kategoriList = kategoriList;
    }

    public RecyclerAdapter(LaporanTabFragment ltr, List<KategoriModel> kategoriList){
        this.ltr = ltr;
        this.kategoriList = kategoriList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView =  inflater.inflate(R.layout.list_item_kategori, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        KategoriModel kategoriModel = kategoriList.get(position);
        holder.nama_kategori.setText(kategoriModel.getPemasukan());
        holder.jenis_kategori.setText(kategoriModel.getJenis());

        Log.d("","CEK KEY:"+kategoriModel.getKey());
        Log.d("","CEK POSISI:"+getItemCount());

        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ktr.getContext());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //KategoriDialogFragment dialogForm = new KategoriDialogFragment(kategoriModel.getJenis(), kategoriModel.getPemasukan(),kategoriModel.getKey(),"Hapus");
                        FirebaseFirestore db;
                        db = FirebaseFirestore.getInstance();
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                        db.collection("user")
                                .document(firebaseUser.getEmail()).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot document = task.getResult();
                                        Map<String, Object> user = new HashMap<>();

                                        List<String> pemasukan = new ArrayList<>();
                                        List<String> pengeluaran = new ArrayList<>();

                                        //get all data
                                        List<String> listpem = (List<String>) document.get("pemasukan");
                                        for (int i = 0; i < listpem.size(); i++) {
                                            pemasukan.add(i, listpem.get(i));
                                        }
                                        List<String> listpen = (List<String>) document.get("pengeluaran");
                                        for (int i = 0; i < listpen.size(); i++) {
                                            pengeluaran.add(i, listpen.get(i));
                                        }

                                        String nama = kategoriModel.getNamakategori();
                                        String jenis = kategoriModel.getJenis();
                                        int index = kategoriModel.getKey();

                                        //edit data
                                        if (jenis.equals("Pemasukan")) {
                                            pemasukan.remove(index);
                                            //pemasukan.add(index, nama);
                                        } else if (jenis.equals("Pengeluaran")){
                                            pengeluaran.remove(index);
                                            //pengeluaran.add(index, nama);
                                        }

                                        user.put("pemasukan", pemasukan);
                                        user.put("pengeluaran", pengeluaran);

                                        db.collection("user").document(firebaseUser.getEmail())
                                                .update(user).addOnSuccessListener(doc -> {
                                            new Preference(ktr.getContext()).setUser(new UserEntity(
                                                    pemasukan,
                                                    pengeluaran
                                            ));
                                            //deleteCategoryLiveData.postValue(new ResponseModel(true, "Success"));
                                        }).addOnFailureListener(e -> {
                                        });
                                    }
                                });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setMessage("Are you sure want to delete?");
                builder.show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KategoriDialogFragment dialogForm = new KategoriDialogFragment(kategoriModel.getJenis(), kategoriModel.getPemasukan(),kategoriModel.getKey(),"Ubah");
                dialogForm.show(ktr.getParentFragmentManager(),"form");
            }
        });
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama_kategori;
        TextView jenis_kategori;

        CardView cv_kategori;
        ImageView hapus;
        ImageView edit;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nama_kategori = itemView.findViewById(R.id.nama_kategori);
            jenis_kategori = itemView.findViewById(R.id.jenis_kategori);
            cv_kategori = itemView.findViewById(R.id.cv_kategori);
            hapus = itemView.findViewById(R.id.hapus);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}
