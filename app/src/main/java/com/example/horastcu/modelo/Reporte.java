package com.example.horastcu.modelo;

public class Reporte {
    private String nombreR;
    private int horasT;
    private String DescripcionR;
    private String lugarR;
    private String fechaR;
    private String horaActR;
    private String EstadoR;
    private String correoEstudianteR;
    private String uid;

    public Reporte(String uid, String nombreR, int horasT, String descripcionR, String lugarR, String fechaR, String horaActR, String estradoR, String correoEstudianteR) {
        this.uid = uid;
        this.nombreR = nombreR;
        this.horasT = horasT;
        this.DescripcionR = descripcionR;
        this.lugarR = lugarR;
        this.fechaR = fechaR;
        this.horaActR = horaActR;
        this.EstadoR = estradoR;
        this.correoEstudianteR = correoEstudianteR;
    }
    public Reporte() {
        this.uid = "";
        this.nombreR = "";
        this.horasT = 0;
        this.DescripcionR = "";
        this.lugarR = "";
        this.fechaR = "";
        this.horaActR = "";
        this.EstadoR = "";
        this.correoEstudianteR = "";
    }

    public String getNombreR() {
        return nombreR;
    }

    public void setNombreR(String nombreR) {
        this.nombreR = nombreR;
    }

    public int getHorasT() {
        return horasT;
    }

    public void setHorasT(int horasT) {
        this.horasT = horasT;
    }

    public String getDescripcionR() {
        return DescripcionR;
    }

    public void setDescripcionR(String descripcionR) {
        DescripcionR = descripcionR;
    }

    public String getLugarR() {
        return lugarR;
    }

    public void setLugarR(String lugarR) {
        this.lugarR = lugarR;
    }

    public String getFechaR() {
        return fechaR;
    }

    public void setFechaR(String fechaR) {
        this.fechaR = fechaR;
    }

    public String getHoraActR() {
        return horaActR;
    }

    public void setHoraActR(String horaActR) {
        this.horaActR = horaActR;
    }

    public String getEstadoR() {
        return EstadoR;
    }

    public void setEstadoR(String estadoR) {
        EstadoR = estadoR;
    }

    public String getCorreoEstudianteR() {
        return correoEstudianteR;
    }

    public void setCorreoEstudianteR(String correoEstudianteR) {
        this.correoEstudianteR = correoEstudianteR;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "uid='" + uid + '\'' +
                "nombreR='" + nombreR + '\'' +
                ", horasT=" + horasT +
                ", DescripcionR='" + DescripcionR + '\'' +
                ", lugarR='" + lugarR + '\'' +
                ", fechaR='" + fechaR + '\'' +
                ", horaActR='" + horaActR + '\'' +
                ", EstadoR='" + EstadoR + '\'' +
                ", correoEstudianteR='" + correoEstudianteR + '\'' +
                '}';
    }
}
