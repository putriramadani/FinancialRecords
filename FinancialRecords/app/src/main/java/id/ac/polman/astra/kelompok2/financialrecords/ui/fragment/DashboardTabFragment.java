package id.ac.polman.astra.kelompok2.financialrecords.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.model.DashboardModel;
import id.ac.polman.astra.kelompok2.financialrecords.ui.activity.HomeActivity;
import id.ac.polman.astra.kelompok2.financialrecords.ui.activity.LoginActivity;
import id.ac.polman.astra.kelompok2.financialrecords.ui.activity.ProfileActivity;

public class DashboardTabFragment extends Fragment {

    private View objectDTF;
    private FirebaseAuth firebaseAuth;
    View mView;
    RecyclerView.LayoutManager mLayoutManager;
    FirebaseFirestore db;
    DashboardAdapter mAdapter;
    private TextView mPemasukanTextView;
    private TextView mPengeluarannTextView;
    private TextView mSaldoTextView;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dashboard_tab_fragment);
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        //checkUser();
//
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
//        if (fragment == null){
//            fragment = DashboardListFragment.newInstance();
//            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objectDTF = inflater.inflate(R.layout.dashboard_tab_fragment, container, false);

        mPemasukanTextView = (TextView) objectDTF.findViewById(R.id.nominal_pemasukan);
        mPengeluarannTextView = (TextView) objectDTF.findViewById(R.id.nominal_pengeluaran);
        mSaldoTextView = (TextView) objectDTF.findViewById(R.id.saldo);

        db = FirebaseFirestore.getInstance();

        mView = objectDTF.findViewById(R.id.header_dashboard);

        showData();

        return objectDTF;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Fragment childFragment = new DashboardListFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, childFragment).commit();
    }

    private void showData(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DashboardModel dashboardModel = new DashboardModel();
        db.collection("user").document(firebaseUser.getEmail())
                .collection("Laporan").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                if (document.getLong("jenis_kategori").intValue() == 1){
                                    dashboardModel.setPemasukan(dashboardModel.getPemasukan() + document.getLong("jumlah").intValue());

                                }
                                else if (document.getLong("jenis_kategori").intValue() == 2){
                                    dashboardModel.setPengeluaran(dashboardModel.getPemasukan() + document.getLong("jumlah").intValue());

                                }
                            }
                            mPengeluarannTextView.setText(Integer.toString(dashboardModel.getPengeluaran()));
                            mPemasukanTextView.setText(Integer.toString(dashboardModel.getPemasukan()));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", e.getMessage());
                    }
                });

        db.collection("user").document(firebaseUser.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            int saldo = doc.getLong("saldo").intValue();
                            dashboardModel.setSaldo(saldo);
                            mSaldoTextView.setText(Integer.toString(dashboardModel.getSaldo()));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", e.getMessage());
                    }
                });
    }

    private class DashboardHolder extends RecyclerView.ViewHolder {
        private TextView mPemasukanTextView;
        private TextView mPengeluarannTextView;

        private DashboardModel mDashboardModel;

        public DashboardHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.dashboard_tab_fragment, parent, false));

            mPemasukanTextView = (TextView) itemView.findViewById(R.id.nominal_pemasukan);
            mPengeluarannTextView = (TextView) itemView.findViewById(R.id.nominal_pengeluaran);
        }

        public void bind(DashboardModel dashboardModel){
            mDashboardModel = dashboardModel;

            mPengeluarannTextView.setText(dashboardModel.getPengeluaran());
            mPemasukanTextView.setText(dashboardModel.getPemasukan());
        }
    }

    private class DashboardAdapter extends RecyclerView.Adapter<DashboardHolder> {
        private DashboardModel mDashboardModel;

        public DashboardAdapter (DashboardModel dashboardModel) {
            mDashboardModel = dashboardModel;
        }

        @NonNull
        @Override
        public DashboardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DashboardHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DashboardHolder holder, int position) {
            holder.bind(mDashboardModel);
        }


        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
