package id.ac.polman.astra.kelompok2.financialrecords.utils;

import android.util.Patterns;

public class Validation {
    public static boolean matchEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
