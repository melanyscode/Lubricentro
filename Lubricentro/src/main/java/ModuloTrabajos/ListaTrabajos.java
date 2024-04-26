/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloTrabajos;

import Conexiones.ConexionBD;
import Controller.GestionProductos;
import Controller.GestionTrabajos;
import static Controller.GestionTrabajos.listaTrabajos;
import ModuloInventario.ListaCircular;
import ModuloInventario.NodoLista;
import Objetos.Producto;
import Objetos.TrabajoRealizado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Melanie Gutierrez
 */
public class ListaTrabajos {

    ConexionBD conexion = new ConexionBD();
    Nodo inicio;

    public ListaTrabajos() {
        this.inicio = null;
    }

    public boolean isEmpty() {
        return inicio == null;
    }

    public int size() {
        if (inicio == null) {
            return 0;
        } else {
            int size = 1;
            Nodo nodoActual = inicio;
            do {
                size++;
                nodoActual = nodoActual.getSiguiente();
            } while (nodoActual != inicio);
            return size;
        }
    }

    public void agregar(TrabajoRealizado t) {
        Nodo nuevo = new Nodo(t);

        if (isEmpty()) {
            inicio = nuevo;
            nuevo.setSiguiente(nuevo);
            nuevo.setAnterior(nuevo);
        } else {
            Nodo ultimo = inicio.getAnterior();
            ultimo.setSiguiente(nuevo);
            nuevo.setAnterior(ultimo);
            nuevo.setSiguiente(inicio);
            inicio.setAnterior(nuevo);

        }
    }

    public TrabajoRealizado buscarId(int id) {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "El inventario esta vacio");
            return null;
        }
        Nodo aux = inicio;
        do {
            if (aux.getTrabajo().getId() == id) {
                return aux.getTrabajo();
            }
            aux = aux.getSiguiente();
        } while (aux != inicio);
        return null;
    }

    public void eliminar(int id) {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en el inventario");
        } else {
            Nodo actual = inicio;
            do {
                //comprobar si el elemento es el que se estabuscando
                if (actual.getTrabajo().getId() == id) {
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

    public void actualizar(int id, TrabajoRealizado t) {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en el inventario");
        }
        Nodo aux = inicio;
        do {
            if (aux.getTrabajo().getId() == id) {
                aux.setTrabajo(t);
                JOptionPane.showMessageDialog(null, "El Servicio con ID: " + id + " ha sido modificado");
                break;
            }
            aux = aux.getSiguiente();
        } while (aux != inicio);

    }

    @Override
    public String toString() {
        String mensaje = "";
        if (isEmpty()) {
            mensaje += "No hay productos en el inventario";
        } else {
            Nodo aux = inicio;
            do {
                mensaje += aux.getTrabajo().toString() + "\n";
                aux = aux.getSiguiente();
            } while (aux != inicio);
        }
        return mensaje;
    }

    public void agregarListaBD(ListaTrabajos lista) {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay elementos en la lista");
        }
        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            conexion.setConsulta("INSERT INTO servicio (id_servicio, descripcion, precio, disponible) VALUES (?,?,?,?)");
            preState = conexion.getConsulta();

            Nodo aux = inicio;
            do {
                TrabajoRealizado t = aux.getTrabajo();
                preState.setInt(1, t.getId());
                preState.setString(2, t.getDescripcion());
                preState.setDouble(3, t.getPrecio());
                preState.setBoolean(4, t.isActivo());

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
            conexion.setConsulta("SELECT * FROM servicio");
            preState = conexion.getConsulta();
            ResultSet consulta = preState.executeQuery();
            while (consulta.next()) {
                int id = consulta.getInt("id_servicio");
                String descripcion = consulta.getString("descripcion");
                double precio = consulta.getDouble("precio");
                boolean disponible = consulta.getBoolean("disponible");
                TrabajoRealizado t = new TrabajoRealizado(id, descripcion, precio, disponible);
                listaTrabajos.agregar(t);
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
    //metodo de grafo puede que sea necesario (o no ) 
//    public void agregarListaGrafo(){
//         Nodo aux = inicio;
//            do {
//                int idProducto = aux.getProducto().getId();
//                int categoria = aux.getProducto().getCategoriaId();
//                GestionProductos.grafoRelaciones.agregarRelacion(idProducto, categoria);
//                aux = aux.getSiguiente();
//            } while (aux != inicio);
//    }

    //metodo para mover la lista a una estructura de arbol
    public void agregarListaArbol(ListaTrabajos lista) {
        Nodo aux = inicio;
        do {
            TrabajoRealizado t = aux.getTrabajo();
            GestionTrabajos.arbolTrabajos.insertar(t);
            aux = aux.getSiguiente();
        } while (aux != inicio);
    }
}
