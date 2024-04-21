/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloOperario;
import Conexiones.ConexionBD;
import Objetos.Venta;
import java.sql.*;
/**
 *
 * @author Melanie Gutierrez
 */
public class ColaVenta {
    ConexionBD conexion = new ConexionBD();
    NodoVenta inicio;
    NodoVenta fin;
    int size;

    public ColaVenta() {
        this.inicio = null;
        this.fin = null;
        this.size = 0;
    }

    

    //metodos
    public boolean isEmpty() {
        return inicio == null;
    }

    //retornar el tamaño de la cola
    public int size() {
        return size;
    }

    //insertar
    public void insertar(Venta v) {
        NodoVenta nuevo = new NodoVenta(v);
        if (isEmpty()) {
            inicio = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
        size++;
    }

    //desencolar 
    public Venta desencolar() {
        Venta aux = inicio.getVenta();
        inicio = inicio.getSiguiente();
        return aux;
    }

    public Venta primeroEnCola() {
        return inicio.getVenta();
    }

    //impri cola 
//    public String toString() {
//        String mensaje = "Trabajos en ejecución:\n";
//        if (isEmpty()) {
//            mensaje += "No hay operarios realizando trabajos";
//        }
//        NodoVenta aux = inicio;
//        while (aux != null) {
//            String disponible = "";
//            if (aux.getVenta().isDisponible()) {
//                disponible = "Sí";
//            } else {
//                disponible = "No";
//            }
//            mensaje += aux.getVenta().getNombre() + "Disponibilidad: " + disponible;
//            aux = aux.getSiguiente();
//        }
//        return mensaje;
//    }

    //metodo para agregar BD a la Cola (solo se agregan los que estan disponibles para hacer trabajos 
//    public void agregarBDaCola() {
//        PreparedStatement preState = null;
//        try {
//            conexion.setConexion();
//            conexion.setConsulta("SELECT * FROM venta WHERE disponible = ?");
//            preState = conexion.getConsulta();
//            preState.setInt(1, 1);
//
//            ResultSet rs = preState.executeQuery();
//            while (rs.next()) {
//                String nombre = rs.getString("nombre");
//                int id = rs.getInt("id_operario");
//                boolean disponible = rs.getBoolean("disponible");
//                Operario operario = new Operario(nombre, id, disponible);
//                GestionOperarios.colaDisponible.insertar(operario);
//            }
//
//            //agregar los que estan en ejecucion
//            conexion.setConexion();
//            conexion.setConsulta("SELECT * FROM operario WHERE disponible = ?");
//            preState = conexion.getConsulta();
//            preState.setInt(1, 0);
//
//            ResultSet rsEjecucion = preState.executeQuery();
//            while (rs.next()) {
//                String nombre = rsEjecucion.getString("nombre");
//                int id = rs.getInt("id_operario");
//                boolean disponible = rs.getBoolean("disponible");
//                Operario operario = new Operario(nombre, id, disponible);
//                GestionOperarios.colaTrabajos.insertar(operario);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
//        } finally {
//            try {
//                if (preState != null) {
//                    preState.close();
//                }
//                conexion.cerrarConexion();
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
//            }
//        }
//    }
//
//    //metodo para agregar cola a la BD
//    public void agregarColaBD(ColaOperario cola) {
//        if (cola.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "No hay trabajos en cola");
//        } else {
//            PreparedStatement preState = null;
//            try {
//                conexion.setConexion();
//                conexion.setConsulta("UPDATE operario SET disponible = ? WHERE id_operario = ?");
//                preState = conexion.getConsulta();
//                NodoCola aux = inicio;
//                while(aux != null){
//                    Operario o = aux.getOperario();
//                    preState.setBoolean(1, o.isDisponible());
//                    preState.executeUpdate();
//                    aux = aux.getSiguiente();
//                }
//                
//                
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, "Error: " + e);
//            } finally {
//                try {
//                    if (preState != null) {
//                        preState.close();
//                    }
//                    conexion.cerrarConexion();
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
//                }
//
//            }
//        }
//    }

}
