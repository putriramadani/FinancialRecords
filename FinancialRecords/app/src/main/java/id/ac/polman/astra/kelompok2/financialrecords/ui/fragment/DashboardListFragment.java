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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.ViewModel.DashboardListViewModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.DashboardModel;

public class DashboardListFragment extends Fragment {
    private static final String TAG = DashboardListFragment.class.getSimpleName();

    private RecyclerView mDashboardRecyclerView;
    private DashboardListViewModel mDashboardListViewModel;
    private LaporanAdapter mAdapter;

    public static DashboardListFragment newInstance() { return new DashboardListFragment(); }

    private void updateUI(){
        List<DashboardModel> dashboardModels = mDashboardListViewModel.getDashboardModels();
        Log.e(TAG, "Total Kategori : " + dashboardModels.size());
        mAdapter = new LaporanAdapter(dashboardModels);
        mDashboardRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FirebaseApp.initializeApp(getContext());
        mDashboardListViewModel = new ViewModelProvider(this).get(DashboardListViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_list, container, false);
        mDashboardRecyclerView = (RecyclerView) view.findViewById(R.id.dashboard_recycler_view);
        mDashboardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private class LaporanHolder extends RecyclerView.ViewHolder {
        private TextView mKategoriTextView;
        private TextView mTotalTextView;

        private DashboardModel mDashboardModel;

        public LaporanHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_dashboard, parent, false));

            mKategoriTextView = (TextView) itemView.findViewById(R.id.nama_kategori);
            mTotalTextView = (TextView) itemView.findViewById(R.id.total_kategori);
        }

        public void bind(DashboardModel dashboardModel) {
            mDashboardModel = dashboardModel;
            mKategoriTextView.setText(mDashboardModel.getKategori());
            mTotalTextView.setText(mDashboardModel.getJumlah());
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
