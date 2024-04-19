/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author Melanie Gutierrez
 */
public class Cliente {
    private String nombre;
    private String cedula;
    private int idCliente;
    private Integer idVehiculo;

    public Cliente(String nombre, String cedula, int idCliente, int idVehiculo) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.idCliente = idCliente;
        this.idVehiculo = idVehiculo;
    }

    public Cliente(String nombre, String cedula, Integer idVehiculo) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.idVehiculo = idVehiculo;
    }

    public Cliente(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
    }
    

    public Cliente() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idLCiente) {
        this.idCliente = idLCiente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @Override
    public String toString() {
        return "Cliente: " + "Nombre: " + nombre + ", Cedula: " + cedula + ", ID Cliente: " + idCliente + ", ID Vehiculo: " + idVehiculo;
    }
    
    
    
}
