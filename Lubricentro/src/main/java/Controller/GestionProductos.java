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
    Producto p = new Producto();
    ConexionBD conexion = new ConexionBD();

    private void agregar() {
        //logica para insertar (pedir al usuario la informacion del producto y despues agregarla a la lista circular
        int idAux = -1;
        String inputID = JOptionPane.showInputDialog(null, "Ingrese el ID del producto");
        if(inputID == null){
            if(Lubricentro.Lubricentro.isAdmin){
                menuProductos();
            }else{
                menuProductosU();
            }
        }else{
            try {
                 idAux = Integer.parseInt(inputID);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
            }
        }

        try {
            //hacer conexion y consulta a la base de datos
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM producto WHERE id_producto = " + idAux);
            ResultSet consulta = conexion.getResultado();

            // si se encontro el id que se mando la consulta 
            if (consulta != null && consulta.next()) {
                JOptionPane.showMessageDialog(null, "El producto ya esta en el inventario, intente actualizar el producto o agregar un producto nuevo");
            } else {
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
                        
                        break;
                    }
                    descripcion = JOptionPane.showInputDialog(null, "Ingrese una pequeña descripcion del producto");
                    if (descripcion == null) {
                        menuProductos();
                        break;
                    }
                    String input = JOptionPane.showInputDialog(null, "Ingrese el precio del producto");
                    //control de botones (cancelar) en este se agrega else para para hacer el parse, en un try catch para identificar si errores de formato
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
                    //idcategoria 
                 String categoriaInput = JOptionPane.showInputDialog(null, "Ingrese la cantidad de existencias", p.getStock());
                if (categoriaInput == null) {
                    menuProductos();
                    break;
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
            if(Lubricentro.Lubricentro.isAdmin){
                menuProductos();
            }else{
                menuProductosU();
            }
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
            JOptionPane.showMessageDialog(null, p.toString());
            listaProductos.vaciarLista();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            conexion.cerrarConexion();
        }
    }

    private void actualizar() {
        int id = 0;
        String idInput = JOptionPane.showInputDialog(null, "Ingrese el ID del producto que desea actualizar");
        if (idInput == null) {
            menuProductos();
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
                    menuProductos();
                    break;
                }
                descripcion = JOptionPane.showInputDialog(null, "Ingrese una pequeña descripcion del producto", p.getDescripcion());
                if (descripcion == null) {
                    menuProductos();
                    break;
                }
                String input = JOptionPane.showInputDialog(null, "Ingrese el precio del producto", p.getPrecio());
                //control de botones (cancelar) en este se agrega else para para hacer el parse, en un try catch para identificar si errores de formato
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
                String stockInput = JOptionPane.showInputDialog(null, "Ingrese la cantidad de existencias", p.getStock());
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
            listaProductos.agregarListaArbol(listaProductos);
            Producto pActualizado = arbolProductos.buscarProducto(id);
            JOptionPane.showMessageDialog(null, "Productos actulizado: " + pActualizado.toString());
            conexion.cerrarConexion();

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
            menuProductos();
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
                menuProductos();
                conexion.cerrarConexion();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            conexion.cerrarConexion();
        }
    }
    public void mostrar(){
        listaProductos.agregarBDaLista();
        JOptionPane.showMessageDialog(null, listaProductos.toString());
    }
    public void mostrarCategoriasProductos(){
        //agregar parte de grafos aca
        
        //agregar bd a  lista
        listaProductos.agregarBDaLista();
        
        //agregar lista a el grafo para imprimirlo 
        int size = listaProductos.size();
        System.out.println(size);
        grafoRelaciones = new Grafo(size + 1);
        listaProductos.agregarListaGrafo();
        
        //imprimir con el grafo
        JOptionPane.showMessageDialog(null, "Inventario Categorias\n" + grafoRelaciones.imprimirMatrizAdyacencia());
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
        String[] opcs = {"Mostrar", "Buscar", "Volver"};
        int opc;
        do {
            opc = Menu.Menu("Inventario Productos", "Elija una opción", opcs, "Agregar");
            switch (opc) {
                case 0:
                    mostrar();
                    break;
                case 1:
                    buscar();
                    break;
                case 2:
                    Lubricentro.Lubricentro.InicioUsuario();
            }
        } while (opc != opcs.length);
    }
}
