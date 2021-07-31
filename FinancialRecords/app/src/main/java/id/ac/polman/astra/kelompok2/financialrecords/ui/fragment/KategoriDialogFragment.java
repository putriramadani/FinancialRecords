package id.ac.polman.astra.kelompok2.financialrecords.ui.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import id.ac.polman.astra.kelompok2.financialrecords.ui.activity.LoginActivity;
import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.ViewModel.KategoriViewModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.KategoriModel;

public class KategoriDialogFragment extends DialogFragment {

    String nama, jenis, pilih;
    int key;

    public KategoriDialogFragment(String jenis, String nama, int key, String pilih) {
        this.jenis = jenis;
        this.nama = nama;
        this.key = key;
        this.pilih = pilih;
    }

    EditText mNama;
    Button mAddButton;
    private View objectLTF;

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    KategoriViewModel viewModel = new KategoriViewModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objectLTF = inflater.inflate(R.layout.form_kategori, container, false);

        mNama= objectLTF.findViewById(R.id.et_nama);
        spinner = objectLTF.findViewById(R.id.spinner);
        mAddButton = objectLTF.findViewById(R.id.btn_simpan);
        Log.e("KDF: ","KATEGORI:" + nama + "jenis:"+jenis +"key:"+key);

        mNama.setText(nama);
        ArrayList<String> alist=new ArrayList<String>(Arrays.asList("Pemasukan", "Pengeluaran"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext().getApplicationContext(), android.R.layout.simple_spinner_item,alist);
        spinner.setAdapter(adapter);
        spinner.setSelection(alist.indexOf(jenis ));

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("running", "Add Category");

                mNama= objectLTF.findViewById(R.id.et_nama);
                mAddButton = objectLTF.findViewById(R.id.btn_simpan);
                spinner = objectLTF.findViewById(R.id.spinner);

                KategoriModel kategoriModel = new KategoriModel(mNama.getText().toString(), spinner.getSelectedItem().toString(), key);

                Log.d("", "nama:"+nama+",jenis:"+jenis+"TAMBAHHHHHHHHHH:"+pilih);
                if(pilih.equals("Tambah")) {
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Add Category");
                    progressDialog.setMessage("Please Wait");
                    progressDialog.show();

                    viewModel.addCategory(getActivity(), kategoriModel).observe(getActivity(), responseModel -> {
                        progressDialog.dismiss();

                        if (responseModel.isSuccess())
                            startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                        else
                            new AlertDialog
                                    .Builder(getActivity())
                                    .setTitle("Failed")
                                    .setMessage(responseModel.getMessage())
                                    .show();
                    });
                }
                else if(pilih.equals("Ubah")){
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Edit Category");
                    progressDialog.setMessage("Please Wait");
                    progressDialog.show();
                    viewModel.updateCategory(getActivity(), kategoriModel).observe(getActivity(), responseModel -> {
                        progressDialog.dismiss();

                        if (responseModel.isSuccess())
                            startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                        else
                            new AlertDialog
                                    .Builder(getActivity())
                                    .setTitle("Failed")
                                    .setMessage(responseModel.getMessage())
                                    .show();
                    });
                }
//                else if(pilih.equals("Hapus")){
//                    Log.e("running", "Hapus");
//                    //KategoriModel kategoriModel = new KategoriModel(mNama.getText().toString(), spinner.getSelectedItem().toString(), key);
//                    viewModel.deleteCategory(getActivity(), kategoriModel).observe(getActivity(), responseModel -> {
//
//                        if (responseModel.isSuccess())
//                            startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
//                        else
//                            new AlertDialog
//                                    .Builder(getActivity())
//                                    .setTitle("Failed")
//                                    .setMessage(responseModel.getMessage())
//                                    .show();
//                    });
//                }
            }
        });

        return  objectLTF;
    }
}
