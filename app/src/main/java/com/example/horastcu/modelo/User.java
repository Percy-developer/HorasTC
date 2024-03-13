package com.example.horastcu.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Serializable {
    private String nombre;
    private String carnet;
    private String correo;
    private String contraseña;
    private String confContra;
    private String uid;
    private String tipo;
    private String tcu;
    private String horasAprobadas;

    public User(String uid, String nombre, String carnet, String correo, String contraseña, String confContra, String tipo, String tcu, String horasAprobadas) {
        this.uid = uid;
        this.nombre = nombre;
        this.carnet = carnet;
        this.correo = correo;
        this.contraseña = contraseña;
        this.confContra = confContra;
        this.tipo = tipo;
        this.tcu = tcu;
        this.horasAprobadas = horasAprobadas;


        if (!contraseña.equals(confContra)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
    }

    public User() {
        this.uid = "";
        this.nombre = "";
        this.correo = "";
        this.contraseña = "";
        this.confContra = "";
        this.tipo = "";
        this.tcu = "";
        this.horasAprobadas = "";
    }//Constructor sin parametros

    protected User(Parcel in) {
        //No se si aqui se ocupa el uid
        nombre = in.readString();
        correo = in.readString();
        contraseña = in.readString();
        confContra = in.readString();
        tipo = in.readString();
        tcu = in.readString();
        horasAprobadas = in.readString();
    }

    /*
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    */

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getConfContra() {
        return confContra;
    }

    public void setConfContra(String confContra) {
        this.confContra = confContra;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTcu() {
        return tcu;
    }

    public void setTcu(String tcu) {
        this.tcu = tcu;
    }

    public String getHorasAprobadas() {
        return horasAprobadas;
    }

    public void setHorasAprobadas(String horasAprobadas) {
        this.horasAprobadas = horasAprobadas;
    }

    @Override
    public String toString() {
        //No se si aqui va el uid
        return "User{" +
                "nombre='" + nombre + '\'' +
                ", carnet='" + carnet + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", confContra='" + confContra + '\'' +
                ", tipo='" + tipo + '\'' +
                ", tcu='" + tcu + '\'' +
                ", horasAprobadas='" + horasAprobadas + '\'' +
                '}';
    }

    /*
    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        //No se si aqui va el uid
        parcel.writeString(nombre);
        parcel.writeString(carnet);
        parcel.writeString(correo);
        parcel.writeString(contraseña);
        parcel.writeString(confContra);
pd        parcel.writeString(tipo);
    }
    */

}//Fin de la clase
