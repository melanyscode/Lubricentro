/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author Melanie Gutierrez
 */
public class Operario {
    private String nombre; 
    private int idOperario;
    private boolean disponible;

    public Operario() {
    }
    
    public Operario(String nombre, int idOperario) {
        this.nombre = nombre;
        this.idOperario = idOperario;
    }

    public Operario(String nombre, int idOperario, boolean disponible) {
        this.nombre = nombre;
        this.idOperario = idOperario;
        this.disponible = disponible;
    }
    

    public int getIdOperario() {
        return idOperario;
    }

    public void setIdOperario(int idOperario) {
        this.idOperario = idOperario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Operario: " + "Nombre: " + nombre + ", ID: " + idOperario;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    
    
}
