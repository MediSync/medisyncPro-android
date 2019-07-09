package cl.devstudio.mediprov2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import cl.devstudio.mediprov2.Model.Paciente;
import cl.devstudio.mediprov2.R;


public class AdapterLista_Pacientes extends RecyclerView.Adapter<AdapterLista_Pacientes.ViewHolderData>
                implements View.OnClickListener {

    ArrayList<Paciente> listaPaciente;
    private View.OnClickListener listener;
    public AdapterLista_Pacientes(ArrayList<Paciente> listPatients) {
        this.listaPaciente = listPatients;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        view.setOnClickListener(this);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int i) {
        holder.titulo.setText(listaPaciente.get(i).getNames());
        holder.subtitulo.setText(listaPaciente.get(i).getRut());
        holder.image.setImageResource(listaPaciente.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return listaPaciente.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {
        TextView titulo,subtitulo;
        ImageView image;


        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.id_list_titulo);
            subtitulo = itemView.findViewById(R.id.id_list_subtitulo);
            image = itemView.findViewById(R.id.id_list_img);

        }

    }
}
