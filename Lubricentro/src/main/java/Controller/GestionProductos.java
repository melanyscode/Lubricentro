/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Conexiones.ConexionBD;
import ModuloInventario.ListaCircular;
import Objetos.Producto;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author Melanie Gutierrez
 */
public class GestionProductos {

    public static ListaCircular listaProductos = new ListaCircular();
    Producto p = new Producto();
    ConexionBD conexion = new ConexionBD();

    private void agregar() {
        //logica para insertar (pedir al usuario la informacion del producto y despues agregarla a la lista circular

        int idAux = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el ID del producto"));

        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM producto WHERE id_producto = " + idAux);
            ResultSet consulta = conexion.getResultado();

            if (consulta != null && consulta.next()) {
                JOptionPane.showMessageDialog(null, "El producto ya esta en el inventario, intente actualizar el producto o agregar un producto nuevo");
            } else {
                String nombre = "";
                String descripcion = "";
                double precio = 0;
                int stock = 0;

                while (true) {
                    nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto:");
                    if (nombre == null) {
                        menuProductos();
                        break;
                    }
                    descripcion = JOptionPane.showInputDialog(null, "Ingrese una pequeña descripcion del producto");
                    if (descripcion == null) {
                        menuProductos();
                        break;
                    }
                    String input = JOptionPane.showInputDialog(null, "Ingrese el precio del producto");
                    if (input == null) {
                        menuProductos();
                        break;
                    } else {
                        try {
                            precio = Double.parseDouble(input);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
                        }
                    }
                    String stockInput = JOptionPane.showInputDialog(null, "Ingrese la cantidad de existencias");
                    if (stockInput == null) {
                        menuProductos();
                        break;
                    } else {
                        try {
                            stock = Integer.parseInt(stockInput);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
                        }
                    }
                    break;
                }
                Producto p1 = new Producto(nombre, descripcion, precio, stock);
                listaProductos.agregar(p1);
                listaProductos.agregarListaBD(listaProductos);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            conexion.cerrarConexion();
        }

    }

    private void buscar() {
        int id = 0;
        String idInput = JOptionPane.showInputDialog(null, "Ingrese el ID del producto que desea buscar");
        if (idInput == null) {
            menuProductos();
        } else {
            try {
                id = Integer.parseInt(idInput);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
            }
        }

        try {
            conexion.setConexion();
            listaProductos.agregarBDaLista();
            Producto p = listaProductos.buscarId(id);
            JOptionPane.showMessageDialog(null, p.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            conexion.cerrarConexion();
        }
    }

    private void mostrar() {

    }

    private void actualizar() {

    }

    private void eliminar() {

    }

    public void menuProductos() {
        String[] opcs = {"Agregar", "Eliminar", "Actualizar", "Buscar", "Volver"};
        int opc;
        do {
            opc = Menu.Menu("Inventario Productos", "Elija una opción", opcs, "Agregar");
            switch (opc) {
                case 0:
                    agregar();
                    break;
                case 1:
                    eliminar();
                    break;
                case 2:
                    actualizar();
                    break;
                case 3:
                    buscar();
                    break;
                case 4:
                    Lubricentro.Lubricentro.Inicio();
            }
        } while (opc != opcs.length);
    }

}
