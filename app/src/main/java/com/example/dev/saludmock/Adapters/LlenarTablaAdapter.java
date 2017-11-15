package com.example.dev.saludmock.Adapters;

/**
 * Created by dev on 15/08/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dev.saludmock.R;

import java.util.ArrayList;

/**
 * Created by kennethrizo on 04/04/17.
 */

public class LlenarTablaAdapter extends ArrayAdapter<LlenarTablaAdapter.PocketMov> {

    ArrayList<PocketMov> lista;
    LayoutInflater mInflater;
    Context mContext;
    int layoutResourceId;

    public LlenarTablaAdapter(Context context,int layoutResource, ArrayList<PocketMov> list) {
        super(context, layoutResource ,list);
        this.mContext = context;
        this.layoutResourceId = layoutResource;
        this.lista = list;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public PocketMov getItem(int position) {
        return lista.get(position);
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        /*
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.text_view, null);
        }
        */
        try {
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.text_view, null);
                PocketMov poc = lista.get(position);
                TextView fecha = (TextView) convertView.findViewById(R.id.txt_direccion);
                TextView amount = (TextView) convertView.findViewById(R.id.txt_numero);
                TextView mascota = (TextView) convertView.findViewById(R.id.txt_telefono);
                TextView telefono = (TextView) convertView.findViewById(R.id.txt_mascota);
                TextView direccion = (TextView) convertView.findViewById(R.id.txt_nombre);
                fecha.setText(poc.getNumero());
                amount.setText(poc.getNombre());
                mascota.setText(poc.getMascota());
                telefono.setText(poc.getTelefono());
                direccion.setText(poc.getDireccion());
            } else {
             //   convertView = convertView;

                LayoutInflater infalInflater = (LayoutInflater) this.mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.text_view, null);
                PocketMov poc = lista.get(position);
                TextView fecha = (TextView) convertView.findViewById(R.id.txt_direccion);
                TextView amount = (TextView) convertView.findViewById(R.id.txt_numero);
                TextView mascota = (TextView) convertView.findViewById(R.id.txt_telefono);
                TextView telefono = (TextView) convertView.findViewById(R.id.txt_mascota);
                TextView direccion = (TextView) convertView.findViewById(R.id.txt_nombre);
                fecha.setText(poc.getNumero());
                amount.setText(poc.getNombre());
                mascota.setText(poc.getMascota());
                telefono.setText(poc.getTelefono());
                direccion.setText(poc.getDireccion());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        //convertView = mInflater.inflate(layoutResourceId, parent, false);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class PocketMov{
        private String id;
        private String numero;
        private String nombre;
        private String mascota;
        private String telefono;
        private String direccion;
        private String edad;
        private String raza;
        private String tmascota;
        private String tratamiento;
        private String social;
        private String comentario;
        private String trescatado;
        private String fvacuna;
        private String fdesparacitacion;
        private String fcelo;
        private String tlactar;
        private String correo;
        private String dmascota;

        public String getDmascota() {
            return dmascota;
        }

        public void setDmascota(String dmascota) {
            this.dmascota = dmascota;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEdad() {
            return edad;
        }

        public void setEdad(String edad) {
            this.edad = edad;
        }

        public String getRaza() {
            return raza;
        }

        public void setRaza(String raza) {
            this.raza = raza;
        }

        public String getTmascota() {
            return tmascota;
        }

        public void setTmascota(String tmascota) {
            this.tmascota = tmascota;
        }

        public String getTratamiento() {
            return tratamiento;
        }

        public void setTratamiento(String tratamiento) {
            this.tratamiento = tratamiento;
        }

        public String getSocial() {
            return social;
        }

        public void setSocial(String social) {
            this.social = social;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }

        public String getTrescatado() {
            return trescatado;
        }

        public void setTrescatado(String trescatado) {
            this.trescatado = trescatado;
        }

        public String getFvacuna() {
            return fvacuna;
        }

        public void setFvacuna(String fvacuna) {
            this.fvacuna = fvacuna;
        }

        public String getFdesparacitacion() {
            return fdesparacitacion;
        }

        public void setFdesparacitacion(String fdesparacitacion) {
            this.fdesparacitacion = fdesparacitacion;
        }

        public String getFcelo() {
            return fcelo;
        }

        public void setFcelo(String fcelo) {
            this.fcelo = fcelo;
        }

        public String getTlactar() {
            return tlactar;
        }

        public void setTlactar(String tlactar) {
            this.tlactar = tlactar;
        }

        public String getDescaplica() {
            return descaplica;
        }

        public void setDescaplica(String descaplica) {
            this.descaplica = descaplica;
        }

        private String descaplica;

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }



        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getMascota() {
            return mascota;
        }

        public void setMascota(String mascota) {
            this.mascota = mascota;
        }

        public PocketMov(String fecha, String amount) {

            this.numero = fecha;
            this.nombre = amount;
        }

        public PocketMov(){

        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }


    }
}

