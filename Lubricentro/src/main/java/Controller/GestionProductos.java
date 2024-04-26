/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Conexiones.ConexionBD;
import ModuloInventario.Arbol;
import ModuloInventario.Grafo;
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
    public static Grafo grafoRelaciones = new Grafo();
    public static Arbol arbolProductos = new Arbol();
    public static Arbol arbolPCambiado = new Arbol();
    Producto p = new Producto();
    ConexionBD conexion = new ConexionBD();

    private void agregar() {

        // si se encontro el id que se mando la consulta 
        //si no se encontro se le pide toda la informacion del producto al usuario 
        String nombre = "";
        String descripcion = "";
        double precio = 0;
        int stock = 0;
        int categoria = 0;
        boolean activo = true;

        while (true) {
            nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto:");
            //control de botones de joptionpane, si es null (cuando se da en el boton de "cancelar" se vuelve al menu de productos 
            if (nombre == null) {
                return;
            }
            descripcion = JOptionPane.showInputDialog(null, "Ingrese una pequeña descripcion del producto");
            if (descripcion == null) {
                return;
            }
            String input = JOptionPane.showInputDialog(null, "Ingrese el precio del producto");
            //control de botones (cancelar) en este se agrega else para para hacer el parse, en un try catch para identificar si errores de formato
            if (input == null) {
                return;
            } else {
                try {
                    precio = Double.parseDouble(input);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
                }
            }
            String stockInput = JOptionPane.showInputDialog(null, "Ingrese la cantidad de existencias");
            if (stockInput == null) {
                return;
            } else {
                try {
                    stock = Integer.parseInt(stockInput);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
                }
            }
            //idcategoria 
            String categoriaInput = JOptionPane.showInputDialog(null, "Ingrese la categoria del producto.\n1. Aceites\n2.Grasas");
            if (categoriaInput == null) {
                return;
            } else {
                try {
                    categoria = Integer.parseInt(categoriaInput);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
                }
            }
            String[] options = {"Disponible", "No disponible"};
            String activoInput = (String) JOptionPane.showInputDialog(null, "¿El producto esta disponible?", "Producto Disponible", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (activoInput.endsWith("Disponible")) {
                activo = true;
            } else {
                activo = false;
            }
            break; //hacer un break para salir del while 
        }
        //Crear producto
        Producto p1 = new Producto(nombre, descripcion, precio, stock, categoria, activo);
        //agregar producto a la lsita
        listaProductos.agregar(p1);
        //agregar lista a la bd 
        listaProductos.agregarListaBD(listaProductos);
        listaProductos.vaciarLista();
        JOptionPane.showMessageDialog(null, "Producto agregado al inventario exitosamente");
    }

    private void buscar() {
        int id = 0;
        String idInput = JOptionPane.showInputDialog(null, "Ingrese el ID del producto que desea buscar");
        if (idInput == null) {
            return;
        } else {
            try {
                id = Integer.parseInt(idInput);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
            }
        }

        try {
            conexion.setConexion(); //setear conexion con bd
            listaProductos.agregarBDaLista(); // metodo de lista circular lee toda la info de la base de datos y la agrega a la lista 
            listaProductos.agregarListaArbol(listaProductos); //agregar lista a una estructura de arbol para poder buscar mas rapido el id que se esta buscando en vez de recorrer todos los nodos de la lista
            Producto p = arbolProductos.buscarProducto(id); // como toda la info de la BD ahora esta en la lista se puede usar el metodo de buscar por id en la lista
            if (p != null) {
                JOptionPane.showMessageDialog(null, p.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro ningun producto asociado con ese ID");
            }
            listaProductos.vaciarLista();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            conexion.cerrarConexion();
            listaProductos.vaciarLista();
        }
    }

    private void actualizar() {
        int id = 0;
        String idInput = JOptionPane.showInputDialog(null, "Ingrese el ID del producto que desea actualizar");
        if (idInput == null) {
            return;
        } else {
            try {
                id = Integer.parseInt(idInput);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
            }
        }

        //realizar conexion a la bd 
        PreparedStatement preState = null;
        try {
            conexion.setConexion(); //setear conexion con bd

            listaProductos.agregarBDaLista(); // metodo de lista circular lee toda la info de la base de datos y la agrega a la lista 
            //agregar lista al arbol para buscar el producto sin recorrer toda la lista 
            listaProductos.agregarListaArbol(listaProductos);
            Producto p = arbolProductos.buscarProducto(id);

            //si no se encontro se le pide toda la informacion del producto al usuario 
            String nombre = "";
            String descripcion = "";
            double precio = 0;
            int stock = 0;
            boolean activo = true;

            while (true) {
                nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto:", p.getNombre());
                //control de botones de joptionpane, si es null (cuando se da en el boton de "cancelar" se vuelve al menu de productos 
                if (nombre == null) {
                    listaProductos.vaciarLista();
                    return;
                }
                descripcion = JOptionPane.showInputDialog(null, "Ingrese una pequeña descripcion del producto", p.getDescripcion());
                if (descripcion == null) {
                    listaProductos.vaciarLista();
                    return;
                }
                String input = JOptionPane.showInputDialog(null, "Ingrese el precio del producto", p.getPrecio());
                //control de botones (cancelar) en este se agrega else para para hacer el parse, en un try catch para identificar si errores de formato
                if (input == null) {
                    listaProductos.vaciarLista();
                    return;
                } else {
                    try {
                        precio = Double.parseDouble(input);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
                    }
                }
                String stockInput = JOptionPane.showInputDialog(null, "Ingrese la cantidad de existencias", p.getStock());
                if (stockInput == null) {
                    listaProductos.vaciarLista();
                    return;
                } else {
                    try {
                        stock = Integer.parseInt(stockInput);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
                    }
                }

                String[] options = {"Disponible", "No disponible"};
                String activoInput = (String) JOptionPane.showInputDialog(null, "¿El producto esta disponible?", "Producto Disponible", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (activoInput.endsWith("Disponible")) {
                    activo = true;
                } else {
                    activo = false;
                }
                break; //hacer un break para salir del while 
            }
            //actualizar producto en la lista
            p = new Producto(nombre, descripcion, precio, stock, activo);
            listaProductos.actualizar(id, p);
            listaProductos.vaciarLista();
            //actualizar productos en la tabla de BD
            conexion.setConsulta("UPDATE producto SET descripcion = ?, detalle = ?, precio = ?, existencias = ?, activo = ? WHERE id_producto = ?");
            preState = conexion.getConsulta();
            preState.setString(1, p.getNombre());
            preState.setString(2, p.getDescripcion());
            preState.setDouble(3, p.getPrecio());
            preState.setInt(4, p.getStock());
            preState.setBoolean(5, p.isActivo());
            preState.setInt(6, id);

            //ejecutar la consulta
            preState.executeUpdate();
            listaProductos.vaciarLista();
            listaProductos.agregarBDaLista();
            //agregar lista al arbol para mejor eficiencia a la hora buscar por id
            listaProductos.agregarListaArbol2(listaProductos);
            Producto pActualizado = arbolPCambiado.buscarProducto(id);
            JOptionPane.showMessageDialog(null, "Productos actulizado: " + pActualizado.toString());
            conexion.cerrarConexion();
            listaProductos.vaciarLista();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            try {
                if (preState != null) {
                    preState.close();
                }
                conexion.cerrarConexion();
                listaProductos.vaciarLista();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
            }

        }
    }

    private void eliminar() {

        /**
         * IMPORTANTE
         *
         * cuando se elimna un producto el id de ese producto ya no se asigna a
         * otro producto que se agrege a la BD *
         */
        //pedir el id del producto a eliminar
        int id = 0;
        String idInput = JOptionPane.showInputDialog(null, "Ingrese el ID del producto que desea eliminar");
        if (idInput == null) {
            return;
        } else {
            try {
                id = Integer.parseInt(idInput);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
            }
        }
        //realizar conexion a la bd y agregar todo los elementos a la lista 
        try {
            conexion.setConexion(); //setear conexion con bd
            PreparedStatement preState = null;
            listaProductos.agregarBDaLista(); // metodo de lista circular lee toda la info de la base de datos y la agrega a la lista 
            //agregar lista a un arbol para recorrer de manera mas sencilla los productos
            listaProductos.agregarListaArbol(listaProductos);
            Producto p = arbolProductos.buscarProducto(id);
            if(p == null){
                JOptionPane.showMessageDialog(null, "No existe un producto asociado con ese ID");
                listaProductos.vaciarLista();
                
                return;
            }
            int input = JOptionPane.showConfirmDialog(null, "¿Desea eliminar " + p.toString() + "?", null, JOptionPane.YES_NO_OPTION);
            if (input == 0) {
                listaProductos.eliminar(id);
                //vaciar la lista de productos
                listaProductos.vaciarLista();

                //consulta para eliminarla de la BD
                conexion.setConsulta("DELETE FROM producto WHERE id_producto = ?");
                preState = conexion.getConsulta();
                // parametro a eliminar
                preState.setInt(1, id);
                //ejecutar la consulta
                preState.executeUpdate();

                JOptionPane.showMessageDialog(null, "El producto fue eliminado satisfactoriamente");
                
            } else {
                JOptionPane.showMessageDialog(null, "Eliminacion cancelada");
                conexion.cerrarConexion();
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        } finally {
            conexion.cerrarConexion();
            listaProductos.vaciarLista();
        }
        listaProductos.vaciarLista();
    }

    public void mostrar() {
        listaProductos.agregarBDaLista();
        JOptionPane.showMessageDialog(null, listaProductos.toString());
        listaProductos.vaciarLista();
    }

    public void mostrarCategoriasProductos() {
        //agregar parte de grafos aca

        //agregar bd a  lista
        listaProductos.agregarBDaLista();

        //agregar lista a el grafo para imprimirlo 
        int size = listaProductos.size();
        if (size != 0) {
            grafoRelaciones = new Grafo(120);
            listaProductos.agregarListaGrafo();
        } else {
            JOptionPane.showMessageDialog(null, "La lista esta vacía");
            return;
        }

        //imprimir con el grafo
        JOptionPane.showMessageDialog(null, "Inventario Categorias\n" + grafoRelaciones.imprimirMatrizAdyacencia());
        listaProductos.vaciarLista();
    }

    public void menuProductos() {
        String[] opcs = {"Agregar", "Eliminar", "Actualizar", "Buscar", "Categorías", "Volver"};
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
                    mostrarCategoriasProductos();
                    break;
                case 5:
                    Lubricentro.Lubricentro.InicioAdmin();
            }
        } while (opc != opcs.length);
    }

    public void menuProductosU() {
        String[] opcs = {"Mostrar", "Categorías", "Buscar", "Volver"};
        int opc;
        do {
            opc = Menu.Menu("Inventario Productos", "Elija una opción", opcs, "Agregar");
            switch (opc) {
                case 0:
                    mostrar();
                    break;
                case 1:
                    mostrarCategoriasProductos();
                    break;
                case  2:
                    buscar();
                    break;
                case 3:
                    Lubricentro.Lubricentro.InicioUsuario();
            }
        } while (opc != opcs.length);
    }
}
