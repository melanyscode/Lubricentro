/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloInventario;

import Conexiones.ConexionBD;
import Objetos.Producto;
import javax.swing.JOptionPane;
import java.sql.*;
import java.sql.Connection;

/**
 *
 * @author Melanie Gutierrez
 */
public class ListaCircular {

    ConexionBD conexion = new ConexionBD();
    NodoLista inicio;

    public ListaCircular() {
        this.inicio = null;
    }

    public boolean isEmpty() {
        return inicio == null;
    }

    public void agregar(Producto p) {
        NodoLista nuevo = new NodoLista(p);

        if (isEmpty()) {
            inicio = nuevo;
            nuevo.setSiguiente(nuevo);
            nuevo.setAnterior(nuevo);
        } else {
            NodoLista ultimo = inicio.getAnterior();
            ultimo.setSiguiente(nuevo);
            nuevo.setAnterior(ultimo);
            nuevo.setSiguiente(inicio);
            inicio.setAnterior(nuevo);

        }
    }

    public Producto buscarId(int id) {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "El inventario esta vacio");
            return null;
        }
        NodoLista aux = inicio;
        while (aux != null) {
            if (aux.getProducto().getId() == id) {
                return aux.getProducto();
            }
            aux = aux.getSiguiente();
        }
        return null;
    }

    public void eliminar(int id) {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en el inventario");
        }
        NodoLista aux = inicio;
        while (aux != null) {
            if (aux.getProducto().getId() == id) {
                if (aux == inicio) {
                    inicio = aux.getSiguiente();
                }
                if (inicio == aux) {
                    inicio = null;
                } else {
                    aux.getAnterior().setSiguiente(aux.getSiguiente());
                    aux.getSiguiente().setAnterior(aux.getAnterior());
                }
            }
            aux = aux.getSiguiente();
        }
        JOptionPane.showMessageDialog(null, "Producto no eliminado, no se encontro en el inventario");
    }

    public void actualizar(int id, Producto p) {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en el inventario");
        }
        NodoLista aux = inicio;
        while (aux != inicio) {
            if (aux.getProducto().getId() == id) {
                aux.setProducto(p);
                JOptionPane.showMessageDialog(null, "El Prodcuto con ID: " + id + " ha sido modificado");
            }
            aux = aux.getSiguiente();
        }
        JOptionPane.showMessageDialog(null, "El producto con ID: " + id + " no se encuentra en el inventario");
    }

    @Override
    public String toString() {
        String mensaje = "";
        if (isEmpty()) {
            mensaje += "No hay productos en el inventario";
        } else {
            NodoLista aux = inicio;
            do {
                mensaje += aux.getProducto().toString();
                aux = aux.getSiguiente();
            } while (aux != inicio);
        }
        return mensaje;
    }

    public void agregarListaBD(ListaCircular lista) {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay elementos en la lista");
        }
        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            conexion.setConsulta("INSERT INTO productos (nombre, descripcion, precio, categoria_id) VALUES (?,?,?,?)");
            preState = conexion.getConsulta();

            NodoLista aux = inicio;
            do {
                Producto p = aux.getProducto();
                preState.setString(1, p.getNombre());
                preState.setString(2, p.getDescripcion());
                preState.setDouble(3, p.getPrecio());
                preState.setInt(3, p.getCategoriaId());

                preState.executeUpdate();

                aux = aux.getSiguiente();
            } while (aux != inicio);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
        } finally {
            try {
                if(preState != null){
                    preState.close();
                }
                conexion.cerrarConexion();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
            }
        }
    }

}
