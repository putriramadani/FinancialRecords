package id.ac.polman.astra.kelompok2.financialrecords.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.adapter.LaporanAdapter;
import id.ac.polman.astra.kelompok2.financialrecords.model.DashboardModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.LaporanModel;
import id.ac.polman.astra.kelompok2.financialrecords.ui.activity.HomeActivity;
import id.ac.polman.astra.kelompok2.financialrecords.ui.activity.LoginActivity;;

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

    private TextView mTanggalTextView;

    Calendar calendar = Calendar.getInstance();
    Locale id = new Locale("in","ID");
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("EEE, dd/MM/yyyy", id);
    Date currentDate = calendar.getTime();
    DashboardModel dashboardModel = new DashboardModel();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objectDTF = inflater.inflate(R.layout.dashboard_tab_fragment, container, false);

        mPemasukanTextView = (TextView) objectDTF.findViewById(R.id.nominal_pemasukan);
        mPengeluarannTextView = (TextView) objectDTF.findViewById(R.id.nominal_pengeluaran);
        mSaldoTextView = (TextView) objectDTF.findViewById(R.id.saldo);
        mTanggalTextView = (TextView) objectDTF.findViewById(R.id.tanggal);

        db = FirebaseFirestore.getInstance();

        mView = objectDTF.findViewById(R.id.header_dashboard);




        showData();

        showDateData();


        return objectDTF;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Fragment childFragment = new DashboardListFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, childFragment).commit();
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
                            dashboardModel.setPemasukan(listjumtam);
                            String totalpem = String.valueOf(dashboardModel.getPemasukan());
                            Log.e("LAPORAN", "Total Pemasukan :" + totalpem);

                            mPemasukanTextView = (TextView) objectDTF.findViewById(R.id.nominal_pemasukan);
                            mPemasukanTextView.setText(formatRupiah(Double.parseDouble(totalpem)));

                            dashboardModel.setPengeluaran(listjumkur);
                            String totalpen = String.valueOf(dashboardModel.getPengeluaran());
                            Log.e("LAPORAN", "Total Pengeluaran :" + totalpen);
                            Log.e("LAPORAN", "Total Pemasukan di pengeluaran :" + String.valueOf(dashboardModel.getPengeluaran()));

                            mPengeluarannTextView = (TextView) objectDTF.findViewById(R.id.nominal_pengeluaran);
                            mPengeluarannTextView.setText(formatRupiah(Double.parseDouble(totalpen)));

                            mTanggalTextView = (TextView) objectDTF.findViewById(R.id.tanggal);
                            mTanggalTextView.setText(mSimpleDateFormat.format(currentDate));
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
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            int saldo = doc.getLong("saldo").intValue();
                            dashboardModel.setSaldo(saldo);
                            mSaldoTextView.setText(formatRupiah(Double.parseDouble(Integer.toString(dashboardModel.getSaldo()))));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", e.getMessage());
                    }
                });
    }
                                       
        private void showData(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

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
                                    dashboardModel.setPengeluaran(dashboardModel.getPengeluaran() + document.getLong("jumlah").intValue());

                                }
                            }
                            mPengeluarannTextView.setText(Integer.toString(dashboardModel.getPengeluaran()));
                            mPemasukanTextView.setText(Integer.toString(dashboardModel.getPemasukan()));
                            setDashboardModel(dashboardModel);
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

    public void setDashboardModel(DashboardModel dashboardModel){
        this.dashboardModel = dashboardModel;
    }

    public DashboardModel getDashboardModel(){
        return this.dashboardModel;
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
