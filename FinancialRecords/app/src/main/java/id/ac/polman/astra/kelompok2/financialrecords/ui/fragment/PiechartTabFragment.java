package id.ac.polman.astra.kelompok2.financialrecords.ui.fragment;

import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.model.LaporanModel;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class PiechartTabFragment extends Fragment {

    PieChartView pieChartView;
    private View objectLTF;
    int totalpem, totalpen;

    FirebaseFirestore db;
    LaporanModel laporanModel = new LaporanModel();

    public PiechartTabFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        objectLTF = inflater.inflate(R.layout.piechart_tab_fragment, container, false);

        pieChartView = objectLTF.findViewById(R.id.chart);
        List pieData = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        //getTotalPemasukan
        db.collection("user").document(firebaseUser.getEmail())
                .collection("Laporan").whereEqualTo("jenis_kategori", 1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //show data
                        int listjumlah = 0;
                        int listjum = 0;

                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            listjumlah = (int) doc.getLong("jumlah").intValue();
                            listjum = listjum + listjumlah;
                        }

                        laporanModel.setTotal_pemasukan(listjum);
                        totalpem = laporanModel.getTotal_pemasukan();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", e.getMessage());
                    }
                });

        //getTotalPengeluaran
        db.collection("user").document(firebaseUser.getEmail())
                .collection("Laporan").whereEqualTo("jenis_kategori", 2)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //show data
                int listjumlah = 0;
                int listjum = 0;

                for (QueryDocumentSnapshot doc : task.getResult()) {
                    listjumlah = (int) doc.getLong("jumlah").intValue();
                    listjum = listjum + listjumlah;
                }

                laporanModel.setTotal_pengeluaran(listjum);
                totalpen = laporanModel.getTotal_pengeluaran();

                int c = laporanModel.getTotal_pengeluaran();
                int d = laporanModel.getTotal_pemasukan();
                double totalpe = (double) d/(c+d)*100;
                Log.e("CEK di PieChartFragment", "Total Pemasukan :" + d+ " t" +totalpe +" c"+c);
                pieData.add(new SliceValue(totalpem, Color.parseColor("#A3812C")).setLabel("Pemasukan "+new DecimalFormat("##.##").format(totalpe)+"%"));

                int b = laporanModel.getTotal_pemasukan();
                double totalp = (double) totalpen/(b+totalpen)*100;
                Log.e("CEK di PieChartFragment", "Total Pengeluaran :" + totalpen+ "t" +totalp);
                pieData.add(new SliceValue(totalpen, Color.parseColor("#EDC35A")).setLabel("Pengeluaran " + new DecimalFormat("##.##").format(totalp)+"%"));

                PieChartData pieChartData = new PieChartData(pieData);
                pieChartData.setHasLabels(true).setValueLabelTextSize(14);
                pieChartData.setHasCenterCircle(true).setCenterText1("Pie Chart Keuangan").setCenterText1FontSize(16).setCenterText1Color(Color.BLACK);
                pieChartView.setPieChartData(pieChartData);

                pieChartView.setOnValueTouchListener(new PieChartOnValueSelectListener() {
                    @Override
                    public void onValueSelected(int arcIndex, SliceValue value) {
                        if(value.getColor() == Color.parseColor("#A3812C")){
                            Toast.makeText(getContext(), "Total Pemasukan " + formatRupiah(Double.parseDouble(String.valueOf(b))) + "\n", Toast.LENGTH_LONG).show();
                        } else if(value.getColor() == Color.parseColor("#EDC35A")){
                            Toast.makeText(getContext(), "Total Pengeluaran " + formatRupiah(Double.parseDouble(String.valueOf(c))) + "\n", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onValueDeselected() {

                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", e.getMessage());
                    }
                });

        return objectLTF;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

}
