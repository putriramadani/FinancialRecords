package id.ac.polman.astra.kelompok2.financialrecords.ui.fragment;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
//import androidx.navigation.fragment.NavHostFragment;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.adapter.LaporanAdapter;
import id.ac.polman.astra.kelompok2.financialrecords.adapter.RecyclerAdapter;
import id.ac.polman.astra.kelompok2.financialrecords.model.DashboardModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.DashboardModel;

public class DashboardListFragment extends Fragment {
    private View objectKTF;

    List<DashboardModel> listDashboard = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    FirebaseFirestore db;
    LaporanAdapter mAdapter;
    FloatingActionButton mButton;

    Calendar calendar = Calendar.getInstance();
    Locale id = new Locale("in","ID");
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd-MM-YYYY", id);
    Date currentDate = calendar.getTime();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objectKTF = inflater.inflate(R.layout.fragment_dashboard_list, container, false);

        mRecyclerView = objectKTF.findViewById(R.id.dashboard_recycler_view);

        db = FirebaseFirestore.getInstance();

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mButton = objectKTF.findViewById(R.id.transaksi_tambah);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransaksiDialogFragment dialogFragment = new TransaksiDialogFragment();
                dialogFragment.show(getFragmentManager(), "Form");
            }
        });

        showDateData();

        return objectKTF;
    }

    private void showDateData() {
        Log.e("BUTTON CARI", " MASUK DATE DATA");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String email = firebaseUser.getEmail();
        db.collection("user").document(email)
                .collection("Laporan")
                .orderBy("tanggal", Query.Direction.ASCENDING)
                .startAt(yesterday()).endAt(currentDate)
                //.startAfter(date_minimal.getTime()).endBefore(date_maximal.getTime())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            //show data
                            int listjumlahtam = 0;
                            int listjumtam = 0;
                            int listjumlahkur = 0;
                            int listjumkur = 0;
                            int listkategori = 0;

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DashboardModel dashboardModel = new DashboardModel();
                                dashboardModel.setJenis_kategori(document.getLong("jenis_kategori").intValue());
                                dashboardModel.setJumlah((document.getLong("jumlah").intValue()));
                                dashboardModel.setKategori(document.getString("kategori"));
                                dashboardModel.setTanggal(document.getDate("tanggal"));
                                dashboardModel.setKeterangan(document.getString("keterangan"));
                                listDashboard.add(dashboardModel);

                                Log.e("BUTTON CARI", " selesai FOR");

                                //getTotalPemasukan
                                listkategori = (int) document.getLong("jenis_kategori").intValue();

                                if(listkategori == 1){
                                    listjumlahtam = (int) document.getLong("jumlah").intValue();
                                    Log.e("LAPORAN TAMBAH", "listjumlah :" + listjumlahtam);

                                    listjumtam = listjumtam + listjumlahtam;
                                    Log.e("LAPORAN LIST TAMBAH", "listjum :" + listjumtam);
                                }else if (listkategori == 2){
                                    listjumlahkur = (int) document.getLong("jumlah").intValue();
                                    Log.e("LAPORAN KURANG", "listjumlah :" + listjumlahkur);

                                    listjumkur = listjumkur + listjumlahkur;
                                    Log.e("LAPORAN LIST KURANG", "listjum :" + listjumkur);
                                }

                            }
                        }
                        mAdapter = new DashboardListFragment.LaporanAdapter(listDashboard);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", e.getMessage());
                    }
                });
    }

    private class LaporanHolder extends RecyclerView.ViewHolder {
        private TextView mKategoriTextView;
        private TextView mTotalTextView;
        private TextView mTanggalTextView;
        private TextView mKeteranganTextView;
        private TextView mPemasukanTextView;
        private  TextView mPengeluaranTextView;

        private DashboardModel mDashboardModel;

        public LaporanHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_dashboard, parent, false));

            mKategoriTextView = (TextView) itemView.findViewById(R.id.nama_kategori);
            mTotalTextView = (TextView) itemView.findViewById(R.id.total_kategori);
            mTanggalTextView = (TextView) itemView.findViewById(R.id.tanggal);
            mKeteranganTextView = (TextView) itemView.findViewById(R.id.keterangan_kategori);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(DashboardModel dashboardModel) {
            mDashboardModel = dashboardModel;
            int pemasukan = 0;
            int pengeluaran = 0;

            mKategoriTextView.setText(mDashboardModel.getKategori());
            mTotalTextView.setText(formatRupiah(Double.parseDouble(String.valueOf(mDashboardModel.getJumlah()))));
            mTanggalTextView.setText(mSimpleDateFormat.format(dashboardModel.getTanggal()));
            mKeteranganTextView.setText(mDashboardModel.getKeterangan());
        }
    }

    private class LaporanAdapter extends RecyclerView.Adapter<LaporanHolder>{
        private List<DashboardModel> mDashboardModels;

        public LaporanAdapter(List<DashboardModel> dashboardModels) {mDashboardModels = dashboardModels;}

        @NonNull
        @Override
        public LaporanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LaporanHolder(layoutInflater, parent);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(@NonNull LaporanHolder holder, int position) {
            DashboardModel dashboardModel = mDashboardModels.get(position);
            holder.bind(dashboardModel);
        }

        @Override
        public int getItemCount() {
            return mDashboardModels.size();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}

