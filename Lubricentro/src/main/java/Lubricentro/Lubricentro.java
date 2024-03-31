package Lubricentro;

import Conexiones.ConexionBD;
import Controller.GestionClientes;
import Controller.GestionComprayVenta;
import Controller.GestionOperarios;
import Controller.GestionProductos;
import Controller.GestionTrabajos;
import Controller.GestionVehiculos;
import Controller.Menu;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author josea
 */
public class Lubricentro {

    public static ConexionBD conexion = new ConexionBD();
    public static Menu menu = new Menu();
    public static GestionProductos gestionP = new GestionProductos();
    public static GestionComprayVenta gestionCYV = new GestionComprayVenta();
    public static GestionOperarios gestionOp = new GestionOperarios();
    public static GestionClientes gestionCl = new GestionClientes();
    public static GestionTrabajos gestionTr = new GestionTrabajos();
    public static GestionVehiculos gestionVe = new GestionVehiculos();

    public static void main(String[] args) {
        Login();
    }

    public static void Inicio() {
        String[] opcs = {"Inventario", "Ventas", "Operarios", "Clientes", "Trabajos", "Salir"};
        int opc;
        do {
            opc = Menu.Menu("Menu Principal", "Lubricentro", opcs, "Inventario");
            switch (opc) {
                case 0:
                    Inventario();
                    break;
                case 1:
                    gestionCYV.Ventas();
                    break;
                case 2:
                    gestionOp.OperarioMenu();
                    break;
                case 3:
                    gestionCl.Clientes();
                    break;
                case 4:
                    gestionTr.menuTrabajos();
                case 5:
                    System.exit(0);
                    break;
            }
        } while (opc != opcs.length);
    }

    public static void Inventario() {
        String[] opcs = {"Productos", "Vehiculos", "Volver"};
        int opc;
        do {
            opc = Menu.Menu("Inventario", "Elija una opcion", opcs, "Productos");
            switch (opc) {
                case 0:
                    gestionP.menuProductos();
                    break;
                case 1:
                    gestionVe.menuVehiculos();
                    break;
                case 2:
                    Inicio();
                    break;
            }
        } while (opc != opcs.length);
    }

    public static void Login() {
        String username = JOptionPane.showInputDialog(null, "Ingrese su nombre de usuario");
        if (username == null) {
            int decision = JOptionPane.showConfirmDialog(null, "¿Desea salir del sistema?", null, JOptionPane.YES_NO_OPTION);
            if (decision == 0) {
                System.exit(0);
            } else {
                Login();
            }
        }
        String contrasenia = JOptionPane.showInputDialog(null, "Ingrese la contraseña");
        if (contrasenia == null) {
            int decision = JOptionPane.showConfirmDialog(null, "¿Desea salir del sistema?", null, JOptionPane.YES_NO_OPTION);
            if (decision == 0) {
                System.exit(0);
            } else {
                Login();
            }
        }
        // conexion con la BD
        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM usuario");
            preState = conexion.getConsulta();
            ResultSet consulta = preState.executeQuery();

            while (consulta.next()) {
                if (username.equals(consulta.getString("username")) && contrasenia.equals(consulta.getString("password"))) {
                    Inicio();
                    break;
                }
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
