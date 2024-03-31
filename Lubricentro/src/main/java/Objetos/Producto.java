/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author Melanie Gutierrez
 */
public class Producto {
    private int id;
    private String nombre; 
    private String descripcion;
    private double precio;
    private int stock;
    private int categoriaId;
    private boolean activo; 

    public Producto() {
    }

    
    public Producto(String nombre, String descripcion, double precio, int stock, boolean activo) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoriaId = 2;
        this.activo = activo;
        
    }

    public Producto(int id, String nombre, String descripcion, double precio, int stock, int categoriaId, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoriaId = 2;
        this.activo = activo;
    }
    

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
      @Override
    public String toString() {
        return "Producto, " + "ID: " + id + ", Nombre: " + nombre + ", Descripcion: " + descripcion + ", Precio: " + precio + ", ID categoria: " + categoriaId + " Cantidad "+ stock;
    }
    
}
