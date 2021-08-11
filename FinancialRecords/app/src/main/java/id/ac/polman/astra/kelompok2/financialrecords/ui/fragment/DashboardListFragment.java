package id.ac.polman.astra.kelompok2.financialrecords.ui.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.adapter.RecyclerAdapter;
import id.ac.polman.astra.kelompok2.financialrecords.model.DashboardModel;

public class DashboardListFragment extends Fragment {
    private View objectKTF;

    List<DashboardModel> listDashboard = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    FirebaseFirestore db;
    LaporanAdapter mAdapter;
    FloatingActionButton mButton;

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



        showData();

        return objectKTF;
    }

    private void showData() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String email = firebaseUser.getEmail();
        db.collection("user").document(email)
                .collection("Laporan").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DashboardModel dashboardModel = new DashboardModel();
                                dashboardModel.setJenis_kategori(document.getLong("jenis_kategori").intValue());
                                Log.e("Login", "jenis kategori :" + dashboardModel.getJenis_kategori());
                                dashboardModel.setJumlah((document.getLong("jumlah").intValue()));
                                Log.e("Login", "jumlah :" + dashboardModel.getJumlah());
                                dashboardModel.setKategori(document.getString("kategori"));
                                Log.e("Login", "kategori :" + dashboardModel.getKategori());
                                dashboardModel.setTanggal(document.getDate("tanggal"));
                                Log.e("Login", "tanggal :" + dashboardModel.getTanggal());
                                listDashboard.add(dashboardModel);
                            }
                        }
                        mAdapter = new LaporanAdapter(listDashboard);
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
        private TextView mPemasukanTextView;
        private  TextView mPengeluaranTextView;

        private DashboardModel mDashboardModel;

        public LaporanHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_dashboard, parent, false));

            //NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            //Fragment parentFragment = (Fragment) navHostFragment.getParentFragment();
            //parentFragment.getView().findViewById(R.id.)

            mKategoriTextView = (TextView) itemView.findViewById(R.id.nama_kategori);
            mTotalTextView = (TextView) itemView.findViewById(R.id.total_kategori);
            //mPemasukanTextView = (TextView) parentFragment.getView().findViewById(R.id.nominal_pemasukan);
            //mPemasukanTextView = (TextView) parentFragment.getView().findViewById(R.id.nominal_pengeluaran);

        }

        public void bind(DashboardModel dashboardModel) {
            mDashboardModel = dashboardModel;
            int pemasukan = 0;
            int pengeluaran = 0;
//            int pemasukan_awal = Integer.parseInt(mPemasukanTextView.getText().toString());
//            int pengeluaran_awal = Integer.parseInt(mPengeluaranTextView.getText().toString());
//            if (mDashboardModel.getJenis_kategori() == 1) {
//                pemasukan = pemasukan_awal + mDashboardModel.getJumlah();
//            }
//            else {
//                pengeluaran = pengeluaran_awal + mDashboardModel.getJumlah();
//            }
//            mPemasukanTextView.setText(String.valueOf(pemasukan));
//            mPengeluaranTextView.setText(String.valueOf(pengeluaran));
            mKategoriTextView.setText(mDashboardModel.getKategori());
            mTotalTextView.setText(String.valueOf(mDashboardModel.getJumlah()));
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

}

