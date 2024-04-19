/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author Melanie Gutierrez
 */
public class TrabajoRealizado {

    private int id;
    private String descripcion;
    private double precio;
    private boolean activo;

    public TrabajoRealizado() {
    }

    public TrabajoRealizado(String descripcion, double precio, boolean activo) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.activo = activo;
    }

    public TrabajoRealizado(int id, String descripcion, double precio, boolean activo) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.activo = activo;
    }
    

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Trabajo Disponibles" + " ID: " + id + ", Descripcion: " + descripcion + ", Precio: " + precio + ", Activo: " + activo;
    }

}
