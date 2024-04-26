/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloOperario;

import Conexiones.ConexionBD;
import Controller.GestionOperarios;
import Objetos.Venta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Melanie Gutierrez
 */
public class ListaVentas {

    ConexionBD conexion = new ConexionBD();
    NodoLista inicio;

    public ListaVentas() {
        this.inicio = null;
    }

    public boolean isEmpty() {
        return inicio == null;
    }

    public int size() {
        if (inicio == null) {
            return 0;
        } else {
            int tamaño = 1;
            NodoLista nodoActual = inicio.getSiguiente();
            while (nodoActual != inicio) {
                tamaño++;
                nodoActual = nodoActual.getSiguiente();
            }
            return tamaño;
        }
    }

    public void agregar(Venta v) {
        NodoLista nuevo = new NodoLista(v);

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

    public Venta buscarId(int id) {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "El inventario esta vacio");
            return null;
        }
        NodoLista aux = inicio;
        do {
            if (aux.getVenta().getIdVenta() == id) {
                return aux.getVenta();
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
                if (actual.getVenta().getIdVenta() == id) {
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

        JOptionPane.showMessageDialog(null, "Venta no eliminado, no se encontro en el inventario");
    }

    public void vaciarLista() {

        NodoLista actual = inicio;
        NodoLista temp;
        do {
            temp = actual;
            actual = actual.getSiguiente();
            temp.setAnterior(null);
            temp.setSiguiente(null);

        } while (actual != inicio);
        inicio = null;
    }

    public void actualizar(int id, Venta v) {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en el inventario");
        }
        NodoLista aux = inicio;
        while (aux != inicio) {
            if (aux.getVenta().getIdVenta() == id) {
                aux.setVenta(v);
                JOptionPane.showMessageDialog(null, "La venta con ID: " + id + " ha sido modificado");
            } else {
                JOptionPane.showMessageDialog(null, "La venta con ID: " + id + " no se encuentra en el inventario");
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
                mensaje += "ID Venta: " + aux.getVenta().getIdVenta() + ", Total: " + aux.getVenta().getTotal() + ", ID Servicio: " + aux.getVenta().getIdServicio() + ", ID Cliente: " + aux.getVenta().getIdCliente() + "\n";
                aux = aux.getSiguiente();
            } while (aux != inicio);
        }
        return mensaje;
    }

    public void agregarBDaLista() {
        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM venta WHERE id_producto IS NULL");
            preState = conexion.getConsulta();

            ResultSet rs = preState.executeQuery();
            while (rs.next()) {
                int idVenta = rs.getInt("id_venta");
                int idCliente = rs.getInt("id_cliente");
                int idServicio = rs.getInt("id_servicio");
                int idOperario = rs.getInt("id_operario");
                double precio = rs.getDouble("precio");
                Venta v = new Venta(idVenta, precio, idServicio, idCliente, idOperario);
                GestionOperarios.ventaProcesada.agregar(v);
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
}
