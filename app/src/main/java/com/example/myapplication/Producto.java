package com.example.myapplication;

public class Producto {

    private  int id;
    private  String nombre;
    private  String descripcion;
    private  int precio;
    private  int calificacion;
    private  String imagen;

 //public Producto(int id, String nombre, int precio, int cantidad, String imagen, String descripcion) {


    public Producto(int id, String nombre, int precio, int calificacion, String imagen, String descripcion) {

        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.calificacion = calificacion;
        this.imagen = imagen;
        this.descripcion = descripcion;

    }

    public Producto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
