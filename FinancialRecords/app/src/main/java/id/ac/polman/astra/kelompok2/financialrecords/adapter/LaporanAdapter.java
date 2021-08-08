package id.ac.polman.astra.kelompok2.financialrecords.adapter;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.model.DashboardModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.LaporanModel;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.DashboardListFragment;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.LaporanTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.utils.Validation;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.LaporanHolder> {
    LaporanTabFragment ltr;
    List<LaporanModel> mLaporanModels;
    Locale id = new Locale("in","ID");
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd-MM-YYYY", id);

    public LaporanAdapter(LaporanTabFragment ltr, List<LaporanModel> laporanModels) {
        this.ltr = ltr;
        mLaporanModels = laporanModels;
    }

    @NonNull
    @Override
    public LaporanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView =  inflater.inflate(R.layout.list_item_laporan, parent, false);
        return new LaporanHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull LaporanHolder holder, int position) {
        LaporanModel laporanModel = mLaporanModels.get(position);
        holder.bind(laporanModel);
    }

    @Override
    public int getItemCount() {
        return mLaporanModels.size();
    }

    public class LaporanHolder extends RecyclerView.ViewHolder {
        private TextView mKategoriTextView;
        private TextView mTotalTextView;
        private TextView mTanggalTextView;

        private LaporanModel mLaporanModel;

        public LaporanHolder(View itemView) {
            super(itemView);
            mKategoriTextView = (TextView) itemView.findViewById(R.id.nama_kategori);
            mTotalTextView = (TextView) itemView.findViewById(R.id.total_kategori);
            mTanggalTextView = (TextView) itemView.findViewById(R.id.tanggal);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(LaporanModel laporanModel) {
            mLaporanModel = laporanModel;
            Log.e("Kategori", mLaporanModel.getKategori());
            Log.e("Jumlah", String.valueOf(mLaporanModel.getJumlah()));

            mKategoriTextView.setText(mLaporanModel.getKategori());
            mTotalTextView.setText(formatRupiah(Double.parseDouble(String.valueOf(mLaporanModel.getJumlah()))));
            mTanggalTextView.setText(mSimpleDateFormat.format(laporanModel.getTanggal()));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
