package id.ac.polman.astra.kelompok2.financialrecords.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import id.ac.polman.astra.kelompok2.financialrecords.R;

public class KategoriTabFragment extends Fragment {
    private View objectKTF;
    FloatingActionButton fab_tambah;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objectKTF = inflater.inflate(R.layout.fragment_kategori_list, container, false);

        fab_tambah = objectKTF.findViewById(R.id.fab_tambah);

        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KategoriDialogFragment dialogForm = new KategoriDialogFragment();
                dialogForm.show(getFragmentManager(), "form");
            }
        });

        return objectKTF;
    }
}
