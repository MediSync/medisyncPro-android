package cl.devstudio.mediprov2.Frag_Menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import cl.devstudio.mediprov2.Frag_Bottom.Frag_listaPaciente;
import cl.devstudio.mediprov2.R;


public class Frag_menu extends Fragment {
    CardView paciente, evolucion, examen, historial, editar, varios;
    ImageView back;
    Fragment miFragment = null;

    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_menu, container, false);
        //---------------------------------------------------------
        paciente = view.findViewById(R.id.id_card_paciente);
        evolucion = view.findViewById(R.id.id_card_evolucion);
        examen = view.findViewById(R.id.id_card_examen);
        historial = view.findViewById(R.id.id_card_historial);
        editar = view.findViewById(R.id.id_card_editar);
        varios = view.findViewById(R.id.id_card_varios);
        back = view.findViewById(R.id.toolbar_back);
        //----------------------------------------------------------
        back.setOnClickListener(new onClick());
        paciente.setOnClickListener(new onClick());
        evolucion.setOnClickListener(new onClick());
        examen.setOnClickListener(new onClick());
        historial.setOnClickListener(new onClick());
        editar.setOnClickListener(new onClick());
        varios.setOnClickListener(new onClick());

        setTitle();
        return view;
    }

    public class onClick implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.id_card_paciente:
                    miFragment = new Frag_listaPaciente();
                    break;
                case R.id.id_card_evolucion:
                    Toast.makeText(getContext(), "evolucion", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.id_card_examen:
                    miFragment = new Frag_Examen();
                    break;
                case R.id.id_card_historial:
                    Toast.makeText(getContext(), "historial", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.id_card_editar:
                    Toast.makeText(getContext(), "editar", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.id_card_varios:
                    Toast.makeText(getContext(), "varios", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.toolbar_back:
                    miFragment = new Frag_listaPaciente();
                    break;
            }
            getFragmentManager().beginTransaction().
                    replace(R.id.container_fragment, miFragment,null).
                    addToBackStack(null).commit();
        }

    }

    private void setTitle() {
        SharedPreferences prefs =
                getActivity().getSharedPreferences("infoPaciente", Context.MODE_PRIVATE);

        String nombre = prefs.getString("names", "por_defecto@email.com");
        TextView titulo;
        titulo = view.findViewById(R.id.tb_title);
        titulo.setText(nombre);
        back.setVisibility(View.VISIBLE);
    }

}
