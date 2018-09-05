package com.example.dev.saludmock.adapters;

public class DoctorAdapter {

    String nombreDoctor;
    String folio;

    public DoctorAdapter(String nombreDoctor, String folio) {
        this.nombreDoctor = nombreDoctor;
        this.folio = folio;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }
}
