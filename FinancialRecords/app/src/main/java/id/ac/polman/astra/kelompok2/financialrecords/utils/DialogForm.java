package id.ac.polman.astra.kelompok2.financialrecords.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import id.ac.polman.astra.kelompok2.financialrecords.LoginActivity;
import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.ViewModel.KategoriViewModel;
import id.ac.polman.astra.kelompok2.financialrecords.ViewModel.SignUpViewModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.KategoriModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.SignUpModel;

public class DialogForm extends DialogFragment {

    public DialogForm() {

    }

    EditText mNama;
    Button mAddButton;
    private View objectLTF;

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objectLTF = inflater.inflate(R.layout.form_kategori, container, false);

        mNama= objectLTF.findViewById(R.id.et_nama);
        mAddButton = objectLTF.findViewById(R.id.btn_simpan);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KategoriViewModel viewModel = new KategoriViewModel();
                Log.e("running", "Add Category");

                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Add Category");
                progressDialog.setMessage("Please Wait");
                progressDialog.show();

                mNama= objectLTF.findViewById(R.id.et_nama);
                mAddButton = objectLTF.findViewById(R.id.btn_simpan);
                spinner = objectLTF.findViewById(R.id.spinner);

                KategoriModel kategoriModel = new KategoriModel(mNama.getText().toString(), spinner.getSelectedItem().toString());

                viewModel.signUp(getActivity(), kategoriModel).observe(getActivity(), responseModel -> {
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
        });

        return  objectLTF;
    }

}
