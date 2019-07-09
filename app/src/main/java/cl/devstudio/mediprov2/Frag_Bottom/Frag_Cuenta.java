package cl.devstudio.mediprov2.Frag_Bottom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cl.devstudio.mediprov2.R;


public class Frag_Cuenta extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.frag_cuenta, container, false);


       return view;
    }
}
