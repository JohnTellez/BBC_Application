package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable{

    private int id;
    private String email;
    private String pass;
    private String nombre;
    private String foto;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Usuario() {
    }

    public Usuario(int id, String email, String username, String nombre) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
    }

    public Usuario(int id, String correo, String username, String pass, String nombre) {
        this.id = id;
        this.email = correo;
        this.pass = pass;
        this.nombre = nombre;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    protected Usuario(Parcel in) {
        id = in.readInt();
        email = in.readString();
        pass = in.readString();
        nombre = in.readString();
        foto = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(email);
        dest.writeString(pass);
        dest.writeString(nombre);
        dest.writeString(foto);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };


}
