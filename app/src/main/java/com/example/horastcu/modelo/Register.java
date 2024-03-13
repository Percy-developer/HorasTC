package com.example.horastcu.modelo;

import java.util.ArrayList;

public class Register {
    ArrayList<User> listaUsuarios;

    public Register(){
        listaUsuarios = new ArrayList<>();
    }//Fin del constructor
    //--------------------------------------------------------------------------------------------------
    public String agregarUsuario(User user){
        if(user != null){
            if(buscarPosiciones(user.getCorreo())==-1){
                listaUsuarios.add(user);
                return "Usuario agregado correctamente";
            }else{
                return "El Usuario ya se encuentra registrado";
            }
        }
        return "Error al agregar el Usuario";
    }//Fin del agregar

    //--------------------------------------------------------------------------------------------------
    public int buscarPosiciones(String correo){
        if(correo != null){
            for (int i = 0; i < listaUsuarios.size(); i++) {
                if(listaUsuarios.get(i).getCorreo().equalsIgnoreCase(correo)){
                    return i;
                }
            }
        }
        return -1;
    }//Fin del metodo buscarPosiciones
}
