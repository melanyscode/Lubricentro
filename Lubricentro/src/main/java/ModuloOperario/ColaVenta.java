/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloOperario;
import Conexiones.ConexionBD;
import Controller.GestionOperarios;
import static Controller.GestionTrabajos.listaTrabajos;
import Objetos.Venta;
import java.sql.*;
import javax.swing.JOptionPane;
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
 public void vaciarCola(){
        inicio = null;
        fin = null;
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

   // impri cola 
    public String toString() {
        String mensaje = "Trabajos en ejecución:\n";
        if (isEmpty()) {
            mensaje += "No hay operarios realizando trabajos";
        }
        NodoVenta aux = inicio;
        while (aux != null) {
           String servicio = listaTrabajos.buscarId( aux.getVenta().getIdServicio()).getDescripcion();
          
            mensaje += "Servicio " + servicio + "Operario: "  ;
            aux = aux.getSiguiente();
        }
        return mensaje;
    }

    //agregar bd a la cola de venta 
    public void agregarBDaCola() {
        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM venta WHERE id_servicio IS NOT NULL");
            preState = conexion.getConsulta();
         
            ResultSet rs = preState.executeQuery();
            while (rs.next()) {
                int idVenta = rs.getInt("id_venta");
                int idCliente = rs.getInt("id_cliente");
                int idServicio = rs.getInt("id_servicio");
                int idOperario = rs.getInt("id_operario");
                double precio = rs.getDouble("precio");
               Venta v = new Venta(idVenta, precio, idServicio, idCliente, idOperario);
               GestionOperarios.ventaEnCola.insertar(v);
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

    //metodo para agregar cola la bd
    public void agregarColaBD(){
        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            conexion.setConsulta("INSERT INTO venta (precio, id_servicio, id_cliente, id_operario) VALUES (?,?,?,?");
            preState = conexion.getConsulta();
            while(!GestionOperarios.ventaEnCola.isEmpty()){
                Venta v = GestionOperarios.ventaEnCola.desencolar();
                preState.setDouble(1, v.getPrecio());
                preState.setInt(2, v.getIdServicio());
                preState.setInt(1, v.getIdCliente());
                preState.setInt(1, v.getIdOperario());
                
                //ejecutar update
                preState.executeUpdate();
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
