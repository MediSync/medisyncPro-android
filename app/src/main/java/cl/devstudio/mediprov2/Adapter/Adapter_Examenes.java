package cl.devstudio.mediprov2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import cl.devstudio.mediprov2.Examenes.Frag_Propiosepcion;
import cl.devstudio.mediprov2.Examenes.Frag_Rango_Movimiento;
import cl.devstudio.mediprov2.Model.Examen;
import cl.devstudio.mediprov2.R;



public class Adapter_Examenes extends RecyclerView.Adapter<Adapter_Examenes.ViewHolderData> {
    private Context context;
    ArrayList<Examen> listExamenes;
    Fragment fragment = null;

    public Adapter_Examenes(ArrayList<Examen> listExamens) {
        this.listExamenes = listExamens;
        this.fragment = null;

    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        final ViewHolderData viewHolder = new ViewHolderData(view);

        viewHolder.item_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.getAdapterPosition() == 0) {
                    AppCompatActivity activity = (AppCompatActivity) viewGroup.getContext();
                    Fragment myFragment = new Frag_Rango_Movimiento();
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.container_fragment, myFragment).addToBackStack(null).commit();

                }
                if (viewHolder.getAdapterPosition() == 1) {
                    AppCompatActivity activity = (AppCompatActivity) viewGroup.getContext();
                    Fragment myFragment = new Frag_Propiosepcion();
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.container_fragment, myFragment).addToBackStack(null).commit();
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int i) {
        holder.name.setText(listExamenes.get(i).getName());
        holder.desc.setText(listExamenes.get(i).getDescription());
        holder.image.setImageResource(listExamenes.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return listExamenes.size();
    }


    public class ViewHolderData extends RecyclerView.ViewHolder {
        TextView name, desc;
        ImageView image;
        LinearLayout item_list;
        Fragment fragmnet;

        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
            fragment = null;
            item_list = itemView.findViewById(R.id.id_item_list);
            name = itemView.findViewById(R.id.id_list_titulo);
            desc = itemView.findViewById(R.id.id_list_subtitulo);
            image = itemView.findViewById(R.id.id_list_img);

        }

    }
}
