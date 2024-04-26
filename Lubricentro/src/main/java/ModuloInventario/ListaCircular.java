/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloInventario;

import Conexiones.ConexionBD;
import Controller.GestionProductos;
import static Controller.GestionProductos.listaProductos;
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

    public int size() {
        if (inicio == null) {
            return 0;
        } else {
            int size = 0;
            NodoLista actual = inicio;
            do {
                size++;
                actual = actual.getSiguiente();
            } while (actual != inicio && actual != null);
            return size;
        }
    }

    public void agregar(Producto p) {
        NodoLista nuevo = new NodoLista(p);

        if (isEmpty()) {
            inicio = nuevo;
            nuevo.setSiguiente(inicio);
            nuevo.setAnterior(inicio);
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
        do {
            if (aux.getProducto().getId() == id) {
                return aux.getProducto();
            }
            aux = aux.getSiguiente();
        } while (aux != inicio);
        return null;
    }

    public void eliminar(int id) {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en el inventario");
        } else {
            NodoLista actual = inicio;
            do {
                //comprobar si el elemento es el que se estabuscando
                if (actual.getProducto().getId() == id) {
                    if (actual == inicio) {
                        inicio = actual.getSiguiente();
                        if (inicio == actual) {
                            inicio = null;
                        } else {
                            inicio.setAnterior(actual.getAnterior());
                            actual.getAnterior().setSiguiente(inicio);
                        }
                    } else {
                        actual.getAnterior().setSiguiente(actual.getSiguiente());
                        actual.getSiguiente().setAnterior(actual.getAnterior());
                    }
                    return;
                }
                actual = actual.getSiguiente();
            } while (actual != null);
        }

        JOptionPane.showMessageDialog(null, "Producto no eliminado, no se encontro en el inventario");
    }

    public void vaciarLista() {
        inicio = null;
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
            } else {
                JOptionPane.showMessageDialog(null, "El producto con ID: " + id + " no se encuentra en el inventario");
            }
            aux = aux.getSiguiente();
        }

    }

    @Override
    public String toString() {
        String mensaje = "";
        if (isEmpty()) {
            mensaje += "No hay productos en el inventario";
        } else {
            NodoLista aux = inicio;
            do {
                mensaje += aux.getProducto().toString() + "\n";
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
            conexion.setConsulta("INSERT INTO producto (id_categoria, descripcion, detalle, precio, existencias) VALUES (?,?,?,?,?)");
            preState = conexion.getConsulta();

            NodoLista aux = inicio;
            do {
                Producto p = aux.getProducto();
                preState.setInt(1, p.getCategoriaId());
                preState.setString(2, p.getNombre());
                preState.setString(3, p.getDescripcion());
                preState.setDouble(4, p.getPrecio());
                preState.setInt(5, p.getStock());

                preState.executeUpdate();

                aux = aux.getSiguiente();
            } while (aux != inicio);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
        } finally {
            try {
                if (preState != null) {
                    preState.close();
                }
                conexion.cerrarConexion();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
            }
        }
    }

    public void agregarBDaLista() {
        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM producto");
            preState = conexion.getConsulta();
            ResultSet consulta = preState.executeQuery();
            while (consulta.next()) {
                int id = consulta.getInt("id_producto");
                int categoria = consulta.getInt("id_categoria");
                String descripcion = consulta.getString("descripcion");
                String detalle = consulta.getString("detalle");
                double precio = consulta.getDouble("precio");
                int stock = consulta.getInt("existencias");
                boolean activo = consulta.getBoolean("activo");
                Producto p = new Producto(id, descripcion, detalle, precio, stock, categoria, activo);
                listaProductos.agregar(p);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
        } finally {
            try {
                if (preState != null) {
                    preState.close();
                }
                conexion.cerrarConexion();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
            }
        }
    }

    public void agregarListaGrafo() {
        NodoLista aux = inicio;
            do {
                int idProducto = aux.getProducto().getId();
                int categoria = aux.getProducto().getCategoriaId();
                GestionProductos.grafoRelaciones.agregarRelacion(idProducto, categoria);
                aux = aux.getSiguiente();
            } while (aux != inicio);
    }

    //metodo para mover la lista a una estructura de arbol
    public void agregarListaArbol(ListaCircular lista) {
        NodoLista aux = inicio;
        do {
            Producto p = aux.getProducto();
            GestionProductos.arbolProductos.insertar(p);
            aux = aux.getSiguiente();
        } while (aux != inicio);
    }
    public void agregarListaArbol2(ListaCircular lista) {
        NodoLista aux = inicio;
        do {
            Producto p = aux.getProducto();
            GestionProductos.arbolPCambiado.insertar(p);
            aux = aux.getSiguiente();
        } while (aux != inicio);
    }
}
