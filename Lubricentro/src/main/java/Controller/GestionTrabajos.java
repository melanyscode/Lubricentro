/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Conexiones.ConexionBD;
import ModuloTrabajos.Arbol;

import ModuloTrabajos.ListaTrabajos;
import Objetos.TrabajoRealizado;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Melanie Gutierrez
 */
public class GestionTrabajos {

    public static ListaTrabajos listaTrabajos = new ListaTrabajos();
    public static Arbol arbolTrabajos = new Arbol();
    ConexionBD conexion = new ConexionBD();
    TrabajoRealizado t = new TrabajoRealizado();
    //listas de trabajos en curso 
    //lista de trabajos terminados 

    private void agregar() {
        //logica para insertar - no hay estructura para guardar los trabajos pero 
        try {

            double precio = 0;
            String descripcion = JOptionPane.showInputDialog(null, "Ignrese una descripcion del trabajo: ");
            if (descripcion == null) {

            }
            String precioInput = JOptionPane.showInputDialog(null, "Ingrese el precio del servicio");
            try {
                precio = Double.parseDouble(precioInput);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
            }
            boolean activo = false;
            String[] options = {"Disponible", "No disponible"};
            String activoInput = (String) JOptionPane.showInputDialog(null, "¿El servicio esta disponible?", "Producto Disponible", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (activoInput.endsWith("Disponible")) {
                activo = true;
            } else {
                activo = false;
            }
            TrabajoRealizado t = new TrabajoRealizado(descripcion, precio, activo);
            listaTrabajos.agregar(t);
            listaTrabajos.agregarListaBD(listaTrabajos);
            conexion.cerrarConexion();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
        } finally {
            try {
                conexion.cerrarConexion();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
            }
        }

    }

    private void actualizar() {
        int id = 0;
        String idInput = JOptionPane.showInputDialog(null, "Ingrese el ID del producto que desea eliminar");
        if (idInput == null) {
            menuTrabajos();
        } else {
            try {
                id = Integer.parseInt(idInput);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
            }
        }
        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            listaTrabajos.agregarBDaLista();
            TrabajoRealizado t = listaTrabajos.buscarId(id);
            if (t != null) {
                //se le pide toda la informacion que desea cambiar al usuario
                String descripcion = "";
                double precio = 0;
                boolean disponible = false;

                descripcion = JOptionPane.showInputDialog(null, "Ingrese una nueva descripción del servicio", t.getDescripcion());
                if (descripcion == null) {
                    menuTrabajos();
                }
                String precioInput = JOptionPane.showInputDialog(null, "Modifiqué el precio", t.getPrecio());
                if (precioInput == null) {
                    menuTrabajos();
                }
                try {
                    precio = Double.parseDouble(precioInput);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
                }
                boolean activo = false;
                String[] options = {"Disponible", "No disponible"};
                String activoInput = (String) JOptionPane.showInputDialog(null, "¿El servicio esta disponible?", "Producto Disponible", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (activoInput.endsWith("Disponible")) {
                    activo = true;
                } else {
                    activo = false;
                }
                t = new TrabajoRealizado(descripcion, precio, activo);
                listaTrabajos.actualizar(id, t);

                //actualizar bd 
                conexion.setConsulta("UPDATE servicio SET descripcion= ?, precio = ?, activo = ? WHERE id_servicio = ?");
                preState = conexion.getConsulta();
                preState.executeUpdate();
                listaTrabajos.vaciarLista();
                listaTrabajos.agregarBDaLista();
                TrabajoRealizado trabajo = listaTrabajos.buscarId(id);
                JOptionPane.showMessageDialog(null, "Servicio actualizado: \n"
                        + trabajo.toString());
                conexion.cerrarConexion();

            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un servicio que coincida con ese número de ID");
                conexion.cerrarConexion();
            }
            ///
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
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

    private void mostrar() {
        //mostrart los servicios disponibles 
        listaTrabajos.agregarBDaLista();
        JOptionPane.showMessageDialog(null, listaTrabajos.toString());

    }

    public void menuTrabajos() {
        String[] opcs = {"Agregar", "Actualizar", "Mostrar", "Volver"};
        int opc;
        do {
            opc = Menu.Menu("Inventario de Reparaciones", "Elija una opción", opcs, "Agregar");
            switch (opc) {
                case 0:
                    agregar();
                    break;
                case 1:
                    actualizar();
                    break;
                case 2:
                    mostrar();
                    break;
                case 3:
                    Lubricentro.Lubricentro.InicioAdmin();
            }
        } while (opc != opcs.length);
    }

    public void menuTrabajosU() {
        String[] opcs = {"Agregar", "Eliminar", "Actualizar", "Buscar", "Mostrar", "Volver"};
        int opc;
        do {
            opc = Menu.Menu("Inventario de Reparaciones", "Elija una opción", opcs, "Agregar");
            switch (opc) {
                case 0:
                    agregar();
                    break;
                case 1:
                    actualizar();
                    break;
                case 2:
                    mostrar();
                    break;
                case 3:
                    Lubricentro.Lubricentro.InicioUsuario();
            }
        } while (opc != opcs.length);
    }
}
