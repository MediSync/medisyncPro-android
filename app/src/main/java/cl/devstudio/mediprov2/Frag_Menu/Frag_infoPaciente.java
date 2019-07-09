package cl.devstudio.mediprov2.Frag_Menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cl.devstudio.mediprov2.R;

public class Frag_infoPaciente extends Fragment {
    TextView names,lastname,homeAdress,rut,email,phone;
    View view;
    ImageView back;
    Fragment miFragment = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_menu_infopaciente, container, false);
        back = view.findViewById(R.id.toolbar_back);
        setTitle();
        back.setOnClickListener(new onClick());
        llenarDatos();

       return view;
    }


    public void llenarDatos(){
        SharedPreferences prefs =
                getActivity().getSharedPreferences("infoPaciente",getContext().MODE_PRIVATE);

        String srut = prefs.getString("rut", "por_defecto@email.com");
        String snames = prefs.getString("names", "por_defecto@email.com");
        String slastnames = prefs.getString("lastnames", "por_defecto@email.com");
        String sdireccion = prefs.getString("direccion", "por_defecto@email.com");
        String semail = prefs.getString("email", "por_defecto@email.com");
        String sphone = prefs.getString("phone", "por_defecto@email.com");

        rut = view.findViewById(R.id.id_info_rut);
        names = view.findViewById(R.id.id_info_nombre);
        lastname = view.findViewById(R.id.id_info_apellidos);
        homeAdress = view.findViewById(R.id.id_info_direccion);
        email = view.findViewById(R.id.id_info_correo);
        phone = view.findViewById(R.id.id_info_telefono);
        rut.setText(": "+srut);
        names.setText(": "+snames);
        lastname.setText(": "+slastnames);
        homeAdress.setText(": "+sdireccion);
        email.setText(": "+semail);
        phone.setText(": "+sphone);

    }


    private void setTitle() {
        TextView titulo;
        back.setVisibility(View.VISIBLE);
        titulo = view.findViewById(R.id.tb_title);
        titulo.setText("Información Personal");

    }

    public class onClick implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.toolbar_back:
                    miFragment = new Frag_menu();
                    break;
            }
            getFragmentManager().beginTransaction().
                    replace(R.id.container_fragment, miFragment,null).commit();
        }

    }


}
