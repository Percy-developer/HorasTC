package com.example.horastcu.modelo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.horastcu.R;

import java.util.List;
import com.example.horastcu.modelo.Reporte;
import com.example.horastcu.act_reportes;

public class AdapterR extends RecyclerView.Adapter<AdapterR.ImageViewHolder> {

    private Context mContext;
    private List<Reporte> mReportes;

    public AdapterR(Context context, List<Reporte> reportes) {
        mContext = context;
        mReportes = reportes;
    }

    @NonNull
    @Override
    public AdapterR.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_row,parent,false);
        return new AdapterR.ImageViewHolder(v);


    }
    public void onBindViewHolder(@NonNull AdapterR.ImageViewHolder holder, int position) {
        Reporte uploadCurrent = mReportes.get(position);
        holder.lbFecha.setText(uploadCurrent.getFechaR());
        holder.lbActividad.setText(uploadCurrent.getFechaR());
        holder.lbLugar.setText(uploadCurrent.getFechaR());
        holder.lbHoras.setText(uploadCurrent.getFechaR());
        holder.spRevision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public int getItemCount() {
        return mReportes.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView lbFecha;
        public TextView lbActividad;
        public TextView lbLugar;
        public TextView lbHoras;
        public Spinner spRevision;

        public ImageViewHolder (View itemView) {
            super(itemView);
            lbFecha = itemView.findViewById(R.id.lbFecha);
            lbActividad = itemView.findViewById(R.id.lbActividad);
            lbLugar = itemView.findViewById(R.id.lbLugar);
            lbHoras = itemView.findViewById(R.id.lbHoras);
            spRevision = itemView.findViewById(R.id.spRevision);
        }

    }
}//Fin de la claseAdapterR
