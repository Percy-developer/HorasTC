package com.example.horastcu.modelo;

import java.util.ArrayList;

public class RegistroActividad {
    ArrayList<Actividad> listaActividad;

    public RegistroActividad(){
        listaActividad = new ArrayList<>();
    }//Fin del constructor
    //--------------------------------------------------------------------------------------------------
    public String agregarActividad(Actividad actividad){
        if(actividad != null){
            if(buscarPosiciones(actividad.getNombreActividad())==-1){
                listaActividad.add(actividad);
                return "Actividad agregada correctamente";
            }else{
                return "La Actividad ya se encuentra registrada";
            }
        }
        return "Error al agregar la Actividad";
    }//Fin del agregar

    //--------------------------------------------------------------------------------------------------
    public int buscarPosiciones(String nombreActividad){
        if(nombreActividad != null){
            for (int i = 0; i < listaActividad.size(); i++) {
                if(listaActividad.get(i).getNombreActividad().equalsIgnoreCase(nombreActividad)){
                    return i;
                }
            }
        }
        return -1;
    }//Fin del metodo buscarPosiciones
}
