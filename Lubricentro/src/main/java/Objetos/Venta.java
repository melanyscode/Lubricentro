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

    public Venta() {
    }

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

    @Override
    public String toString() {
        ConexionBD conexion = new ConexionBD();
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT detalle FROM producto WHERE id producto = ?");
            PreparedStatement preState = conexion.getConsulta();
            preState.setInt(1, idProducto);
            ResultSet rs = preState.executeQuery();
             return "Venta" + "Producto" + rs.getString("detalle") + ", ID Venta: " + idVenta + ", precio: " + precio + ", Total: " + total;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
        }
        return "";
    }

}
