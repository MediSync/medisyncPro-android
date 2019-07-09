package cl.devstudio.mediprov2.Frag_Bottom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

import cl.devstudio.mediprov2.Act_Login;
import cl.devstudio.mediprov2.Adapter.AdapterLista_Pacientes;
import cl.devstudio.mediprov2.Frag_Menu.Frag_menu;
import cl.devstudio.mediprov2.Model.Paciente;
import cl.devstudio.mediprov2.R;


public class Frag_listaPaciente extends Fragment {
    TextView titulo;
    Boolean bandera = false;
    Paciente paciente = new Paciente();
    RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressBar progressBar;
    View view;
    ArrayList<Paciente> listaPacientes = new ArrayList<>();
    public String srut="",snames="",slast_name1="",
            slast_name2="",slast_names="",sdireccion="",semail="",sphone="",
            ssexo="",sbirth_date="";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_lista_paciente, container, false);
        recyclerView = view.findViewById(R.id.id_recycler_view);
        recyclerView.setHasFixedSize(true);
        progressBar = view.findViewById(R.id.id_paciente_progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mostrarListaPaciente();
        setTitle();
       return view;
    }



    public void mostrarListaPaciente() {

        if (listaPacientes.size()>0)listaPacientes.clear();
        db.collection("patient").
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot querySnapshot : task.getResult()) {
                    Paciente p = new Paciente(querySnapshot.getString("rut"),
                            querySnapshot.getString("names"), querySnapshot.getString("last_name1")
                            , querySnapshot.getString("last_name2"), querySnapshot.getString("phone"),
                            querySnapshot.getString("email"), querySnapshot.getString("address"),
                            querySnapshot.getString("sexo"),querySnapshot.getString("birth_date"),
                            querySnapshot.getString("password"), R.drawable.img_arm);

                    Toast.makeText(getContext(),""+p.getNames(),Toast.LENGTH_LONG).show();
                    listaPacientes.add(p);

                    AdapterLista_Pacientes adapterDataPatients = new AdapterLista_Pacientes(listaPacientes);
                    //Button seleccion de paciente
                    adapterDataPatients.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id = listaPacientes.get
                                    (recyclerView.getChildAdapterPosition(v)).getRut();
                            db = FirebaseFirestore.getInstance();
                            DocumentReference docRef = db.collection("patient").document(id);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            srut = document.getString("rut");
                                            snames = document.getString("names");
                                            slast_name1 = document.getString("last_name1");
                                            slast_name2 = document.getString("last_name2");
                                            slast_names = slast_name1+" "+slast_name2;
                                            sdireccion = document.getString("address");
                                            semail = document.getString("email");
                                            sphone = document.getString("phone");
                                            ssexo = document.getString("sexo");
                                            sbirth_date = document.getString("birth_date");

                                            sessionPaciente();
                                            validacionSP();
                                        }
                                    }
                                }
                            });
                        }
                    });
                    recyclerView.setAdapter(adapterDataPatients);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Problema al carga datos", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setTitle() {
        titulo = view.findViewById(R.id.tb_title);
        String t= "Pacientes";
        titulo.setText(t);
    }


    public void sessionPaciente( ){
        SharedPreferences prefs = getContext().getSharedPreferences
                ("infoPaciente", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("rut",srut);
        editor.putString("names",snames);
        editor.putString("lastnames",slast_names);
        editor.putString("direccion", sdireccion);
        editor.putString("email", semail);
        editor.putString("phone", sphone);
        editor.apply();
        if (srut!=null){
            bandera = true;
        }
    }

    private  void validacionSP(){
        if (bandera=true){
            Fragment fragment = new Frag_menu();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,fragment);
            fragmentTransaction.addToBackStack(null).commit();

            progressBar.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(),"Problema al carga datos", Toast.LENGTH_LONG).show();
        }

    }
}
