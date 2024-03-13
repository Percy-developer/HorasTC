package com.example.horastcu.modelo;

import java.util.ArrayList;

public class RegistroReporte {
    ArrayList<Reporte> listaReporte;

    public RegistroReporte(){
        listaReporte = new ArrayList<>();
    }//Fin del constructor
    //--------------------------------------------------------------------------------------------------
    public String agregarReporte(Reporte reporte){
        if(reporte != null){
            if(buscarPosiciones(reporte.getNombreR())==-1){
                listaReporte.add(reporte);
                return "Reporte agregada correctamente";
            }else{
                return "El Reporte ya se encuentra registrado";
            }
        }
        return "Error al agregar el Reporte";
    }//Fin del agregar

    //--------------------------------------------------------------------------------------------------
    public int buscarPosiciones(String nombreReporte){
        if(nombreReporte != null){
                for (int i = 0; i < listaReporte.size(); i++) {
                if(listaReporte.get(i).getNombreR().equalsIgnoreCase(nombreReporte)){
                    return i;
                }
            }
        }
        return -1;
    }//Fin del metodo buscarPosiciones
}
