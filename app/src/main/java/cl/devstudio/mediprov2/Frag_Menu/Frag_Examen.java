package cl.devstudio.mediprov2.Frag_Menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import cl.devstudio.mediprov2.Adapter.Adapter_Examenes;
import cl.devstudio.mediprov2.Model.Examen;
import cl.devstudio.mediprov2.R;


public class Frag_Examen extends Fragment {
    View view;
    ArrayList<Examen> listExamens = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_lista_paciente, container, false);
        fillDevice();
        recyclerView = view.findViewById(R.id.id_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Adapter_Examenes adapterExamens = new Adapter_Examenes(listExamens);
        recyclerView.setAdapter(adapterExamens);

        return view;
    }


    private void fillDevice() {
        listExamens.clear();
        listExamens.add(new Examen("Rango de movimiento","Examen realizado....",R.drawable.img_examen));
        listExamens.add(new Examen("Propiosepción","Examen realizado...",R.drawable.img_examen));
    }




}
