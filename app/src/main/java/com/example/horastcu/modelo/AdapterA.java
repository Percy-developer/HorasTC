package com.example.horastcu.modelo;

import com.bumptech.glide.Glide;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horastcu.R;
import com.example.horastcu.act_detalle_actividad;
import com.example.horastcu.act_login;
import com.example.horastcu.act_modulo_profesor;
import com.example.horastcu.act_modulo_usuario;
import com.example.horastcu.act_olv_contrasena;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterA extends RecyclerView.Adapter<AdapterA.ImageViewHolder> {

    private Context mContext;
    private List<Actividad> mActividades;

    public AdapterA(Context context, List<Actividad> actividades) {
        mContext = context;
        mActividades = actividades;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
        return new ImageViewHolder(v);
    }
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Actividad uploadCurrent = mActividades.get(position);
        holder.lbNombre.setText(uploadCurrent.getNombreActividad());
        holder.btnDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(mContext, act_detalle_actividad.class);
                intent.putExtra("selectedActividad",uploadCurrent);
                mContext.startActivity(intent);
            }
        });
        // Carga la imagen usando Glide
        Glide.with(mContext)
                .load(uploadCurrent.getImagenActividad())
                .placeholder(R.drawable.image7)
                .fitCenter()
                .into(holder.imgActividadCard);
    }
    @Override
    public int getItemCount() {
        return mActividades.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView lbNombre;
        public ImageView imgActividadCard;
        public Button btnDetalles;

        public ImageViewHolder (View itemView) {
            super(itemView);
            lbNombre = itemView.findViewById(R.id.lbNombre);
            imgActividadCard = itemView.findViewById(R.id.imgActividadCard);
            btnDetalles = itemView.findViewById(R.id.btnDetalles);
        }

    }
}//Fin de la claseAdapter
