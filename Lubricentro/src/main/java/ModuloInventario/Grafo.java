/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloInventario;

import Controller.GestionProductos;
import static Lubricentro.Lubricentro.conexion;
import Objetos.Producto;
import Objetos.Subcategoria;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Melanie Gutierrez
 */
public class Grafo {

    private NodoGrafo[] nodos;
    private int[][] matrizAdyacencia;

    ListaIds listaId = new ListaIds();

    public Grafo() {
    }

    public Grafo(int cantidadNodos) {

        nodos = new NodoGrafo[cantidadNodos];
        // los nodos del grafo

        matrizAdyacencia = new int[cantidadNodos][cantidadNodos];

        for (int i = 0; i < cantidadNodos; i++) {
            nodos[i] = new NodoGrafo(i);

        }
    }

    public void agregarRelacion(int idProducto, int idSubcategoria) {
        listaId.insetar(idProducto);

        matrizAdyacencia[idProducto][idSubcategoria] = 1;
        
    }

    public String imprimirMatrizAdyacencia() {
        String mensaje = "";
        Subcategoria[] subLista = new Subcategoria[2];
        int contador = 0;
        //obtener subcategorias de bd

        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM categoria");
            preState = conexion.getConsulta();
            ResultSet consulta = preState.executeQuery();
            while (consulta.next()) {
                int id = consulta.getInt("id_categoria");
                String nombre = consulta.getString("descripcion");
                Subcategoria sub = new Subcategoria(id, nombre);
                subLista[contador] = sub;
                contador++;
            }

            for (Subcategoria subcategoria : subLista) {
                mensaje += subcategoria.getNombre() + ":\n";
                for (int i = 0; i < matrizAdyacencia.length; i++) {
                    if (matrizAdyacencia[i][subcategoria.getIdCategoria()] != 0) {
                        Producto producto = GestionProductos.listaProductos.buscarId(i);
                        mensaje += "- Producto: "+ producto.getNombre() + ", ID: " + i + "\n";
                    }
                }
                mensaje += "\n";
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
        return mensaje;
    }

}
