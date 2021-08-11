package id.ac.polman.astra.kelompok2.financialrecords.ui.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.ViewModel.TransaksiViewModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.TransaksiModel;
import id.ac.polman.astra.kelompok2.financialrecords.ui.activity.LoginActivity;

public class TransaksiDialogFragment extends DialogFragment {

    EditText mJumlahEditText;
    EditText mKeteranganEditText;
    Spinner mJenisSpinner;
    Spinner mKategoriSpinner;
    Button mButton;
    private View objectLTF;
    private FirebaseFirestore db;
    private String jenis;
    private int jenis_kategori;
    TransaksiViewModel viewModel = new TransaksiViewModel();


    public TransaksiDialogFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        objectLTF = inflater.inflate(R.layout.form_transaksi, container, false);

        mJumlahEditText = objectLTF.findViewById(R.id.txt_jumlah);
        mKeteranganEditText = objectLTF.findViewById(R.id.txt_keterangan);
        mJenisSpinner = objectLTF.findViewById(R.id.ddl_jenis);
        mKategoriSpinner = objectLTF.findViewById(R.id.ddl_kategori);
        mButton = objectLTF.findViewById(R.id.btn_simpan);

        db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        List<String> subjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mKategoriSpinner.setAdapter(adapter);
        mJenisSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0){
                    jenis = "pemasukan";
                    jenis_kategori = 1;
                } else if (position == 1) {
                    jenis = "pengeluaran";
                    jenis_kategori = 2;
                }
                db.collection("user").document(firebaseUser.getEmail())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            subjects.clear();
                            DocumentSnapshot document = task.getResult();
                            List<String> listpem = (List<String>) document.get(jenis);
                            for(int i=0;i<listpem.size();i++){
                                subjects.add(i,listpem.get(i));
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", e.getMessage());
                    }
                });
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransaksiModel transaksiModel = new TransaksiModel(jenis_kategori, Integer.valueOf(mJumlahEditText.getText().toString()),
                        mKategoriSpinner.getSelectedItem().toString() ,mKeteranganEditText.getText().toString(), new Date());

                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Add Transaction");
                progressDialog.setMessage("Please Wait");
                progressDialog.show();

                viewModel.saveTransaksi(getActivity(), transaksiModel).observe(getActivity(), responseModel -> {
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
        return objectLTF;
    }
}
