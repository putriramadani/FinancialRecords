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
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.ViewModel.KategoriViewModel;
import id.ac.polman.astra.kelompok2.financialrecords.ViewModel.SignUpViewModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.KategoriModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.SignUpModel;
import id.ac.polman.astra.kelompok2.financialrecords.ui.activity.LoginActivity;

public class  ProfileTabFragment extends Fragment {
    String nama, alamat;

    public ProfileTabFragment(String nama, String alamat) {
        this.nama = nama;
        this.alamat = alamat;
    }

    EditText mNama;
    EditText mAlamat;
    Button mEditButton;
    private View objectLTF;

    ArrayAdapter<CharSequence> adapter;
    SignUpViewModel viewModel = new SignUpViewModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objectLTF = inflater.inflate(R.layout.profile_tab_fragment, container, false);

        mNama= objectLTF.findViewById(R.id.edit_nama);
        mAlamat = objectLTF.findViewById(R.id.edit_alamat);
        mEditButton = objectLTF.findViewById(R.id.edit_button);
        Log.e("KDF: ","KATEGORI:" + nama + " alamat:"+alamat);

        mNama.setText(nama);
        mAlamat.setText(alamat);

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("running", "edit profile");

                mNama= objectLTF.findViewById(R.id.edit_nama);
                mAlamat = objectLTF.findViewById(R.id.edit_alamat);
                mEditButton = objectLTF.findViewById(R.id.edit_button);

                SignUpModel signUpModel = new SignUpModel(mNama.getText().toString(), mAlamat.getText().toString());

                Log.d("", "nama:"+nama+", alamat:"+alamat);

                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Edit Profile");
                    progressDialog.setMessage("Please Wait");
                    progressDialog.show();
                    viewModel.editProfile(getActivity(), signUpModel).observe(getActivity(), responseModel -> {
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

