package com.example.horastcu.modelo;

import android.net.Uri;

import java.io.Serializable;

public class Actividad implements Serializable {
    private String nombreActividad;
    private String descripcionActividad;
    private int horasEstimadas;
    private String lugar;
    private String publico;
    private String fecha;
    private String hora;
    private String imagenActividad;
    private String nombreImagen;
    private String tcu;

    public Actividad(String nombreActividad, String descripcionActividad, int horasEstimadas, String lugar, String publico, String fecha, String hora, String imagenActividad, String nombreImagen, String tcu) {
        this.nombreActividad = nombreActividad;
        this.descripcionActividad = descripcionActividad;
        this.horasEstimadas = horasEstimadas;
        this.lugar = lugar;
        this.publico = publico;
        this.fecha = fecha;
        this.hora = hora;
        this.imagenActividad = imagenActividad;
        this.nombreImagen = nombreImagen;
        this.tcu = tcu;

    }

    public Actividad() {
        this.nombreActividad = "";
        this.descripcionActividad = "";
        this.horasEstimadas = 0;
        this.lugar = "";
        this.publico = "";
        this.fecha = "";
        this.hora = "";
        this.imagenActividad = "";
        this.nombreImagen = "";
        this.tcu = "";
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getDescripcionActividad() {
        return descripcionActividad;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }

    public int getHorasEstimadas() {
        return horasEstimadas;
    }

    public void setHorasEstimadas(int horasEstimadas) {
        this.horasEstimadas = horasEstimadas;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getPublico() {
        return publico;
    }

    public void setPublico(String publico) {
        this.publico = publico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getImagenActividad() {
        return imagenActividad;
    }

    public void setImagenActividad(String imagenActividad) {
        this.imagenActividad = imagenActividad;
    }

    public String getTcu() {
        return tcu;
    }

    public void setTcu(String tcu) {
        this.tcu = tcu;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "nombreActividad='" + nombreActividad + '\'' +
                ", descripcionActividad='" + descripcionActividad + '\'' +
                ", horasEstimadas=" + horasEstimadas +
                ", lugar='" + lugar + '\'' +
                ", publico='" + publico + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", imagenActividad='" + imagenActividad + '\'' +
                ", nombreImagen='" + nombreImagen + '\'' +
                ", tcu='" + tcu + '\'' +
                '}';
    }
}//Fin de la clase
