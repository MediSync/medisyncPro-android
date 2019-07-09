package cl.devstudio.mediprov2.Examenes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cl.devstudio.mediprov2.R;

public class Frag_Propiosepcion extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_examen_propiosep, container, false);


       return view;
    }




}
