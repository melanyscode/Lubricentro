/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Conexiones.ConexionBD;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Melanie Gutierrez
 */
public class Venta {

    private int idProducto;
    private int idVenta;
    private double precio;
    private double total;
    private int idServicio;
    private int idCliente;
    private int idOperario;
    private int cantidad;

    public Venta() {
    }

    //constructor de venta para la bd

    public Venta(int idProducto, int idVenta, double precio, double total, int idServicio, int idCliente, int idOperario, int cantidad) {
        this.idProducto = idProducto;
        this.idVenta = idVenta;
        this.precio = precio;
        this.total = total;
        this.idServicio = idServicio;
        this.idCliente = idCliente;
        this.idOperario = idOperario;
        this.cantidad = cantidad;
    }
    //---------------------------------------------------------------------------------------
    //venta para de producto 
    //para obtener el id en la bd
    public Venta(int idProducto, int idVenta, double precio, double total, int idCliente, int cantidad) {   
        this.idProducto = idProducto;
        this.idVenta = idVenta;
        this.precio = precio;
        this.total = total;
        this.idCliente = idCliente;
        this.cantidad = cantidad;
    }

    //para construir la venta sin el id(el id lo hace la BD)
    public Venta(int idProducto, double precio, double total, int idCliente, int cantidad) {
        this.idProducto = idProducto;
        this.precio = precio;
        this.total = total;
        this.idCliente = idCliente;
        this.cantidad = cantidad;
    }
    //----------------------------------------------------------------------------------------
    // para crear un venta de SERVICIO 
    // PARA CONSTRUIR LA VENTA DESDE LA BD 

    public Venta(int idVenta, double total, int idServicio, int idCliente, int idOperario) {
        this.idVenta = idVenta;
        this.total = total;
        this.idServicio = idServicio;
        this.idCliente = idCliente;
        this.idOperario = idOperario;
    }
    
    //PARA CONSTRUIR LA VENTA DESDE EL PROGRAMA 

    public Venta(double total, int idServicio, int idCliente, int idOperario) {
        this.total = total;
        this.idServicio = idServicio;
        this.idCliente = idCliente;
        this.idOperario = idOperario;
    }
    

    //----------------------GETTERS AND SETTERS -------------------------------
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
     public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
     public int getIdOperario() {
        return idOperario;
    }

    public void setIdOperario(int idOperario) {
        this.idOperario = idOperario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Venta{" + "idProducto=" + idProducto + ", idVenta=" + idVenta + ", precio=" + precio + ", total=" + total + ", idServicio=" + idServicio + ", idCliente=" + idCliente + ", idOperario=" + idOperario + ", cantidad=" + cantidad + '}';
    }

    

}
