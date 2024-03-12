package Lubricentro;

import Controller.GestionProductos;
import Controller.Menu;
import javax.swing.JOptionPane;

/**
 *
 * @author josea
 */
public class Lubricentro {
    public static Menu menu = new Menu();
    public static GestionProductos gestionP = new GestionProductos();

    public static void main(String[] args) {

        Inicio();
    }
   
    
    public static void Inicio(){
        String[] opcs = {"Inventario", "Ventas", "Operarios", "Clientes", "Salir"};
        int opc;
        do {  
            opc = Menu.Menu("Menu Principal", "Lubricentro", opcs, "Inventario");
            switch(opc){
                case 0:
                    Inventario();
                    break;
                case 1: 
                    Ventas();
                    break;
                case 2: 
                    Operarios();
                    break;
                case 3:
                    Clientes();
                    break;
                case 4:
                    System.exit(0);
                    break;
            }
        } while (opc != opcs.length);
    }
    
    public static void Inventario(){
        String[] opcs = {"Productos", "Ventas", "Volver"};
        int opc;
        do {  
            opc = Menu.Menu("Inventario", "Elija una opcion", opcs, "Productos");
            switch(opc){
                case 0:
                    gestionP.menuProductos();
                    break;
                case 1: 
                    //
                    break;
                case 2: 
                    Inicio();
                    break;
            }
        } while (opc != opcs.length);
    }
    
    public static void Ventas(){
        String[] opcs = {"Agregar al carrito", "Realizar compra", "Volver"};
        int opc;
        do {  
            opc = Menu.Menu("Menú Ventas", "Lubricentro", opcs, "Inventario");
            switch(opc){
                case 0:
                    break;
                case 1: 
                    break;
                case 2: 
                    Inicio();
                    break;
            }
        } while (opc != opcs.length);
    }
    public static void Operarios(){
        String[] opcs = {"Registrar", "Consultar", "Asignar Trabajo", "Elimianr", "Volver"};
        int opc;
        do {  
            opc = Menu.Menu("Menú Operarios", "Elija una opción", opcs, "Registrar");
            switch(opc){
                case 0:
                    break;
                case 1: 
                    break;
                case 2:
                    break;
                case 3: 
                    break;
                case 4:
                    Inicio();
                    break;
            }
        } while (opc != opcs.length);
    }
    public static void Clientes(){
        String[] opcs = {"Registrar", "Consultar", "Elimianr", "Volver"};
        int opc;
        do {  
            opc = Menu.Menu("Menú Clientes", "Elija una opción", opcs, "Registrar");
            switch(opc){
                case 0:
                    break;
                case 1: 
                    break;
                case 2:
                    break;
                case 3:
                    Inicio();
                    break;
            }
        } while (opc != opcs.length);
    }
    
}
