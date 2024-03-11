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
        String[] opcs = {"Productos", "Ventas", "Salir"};
        int opc;
        do {  
            opc = Menu.Menu("Menu Principal", "Lubricentro", opcs, "Inventario");
            switch(opc){
                case 0:
                    gestionP.menuProductos();
                    break;
                case 1: 
                    //
                    break;
                case 2: 
                    System.exit(0);
                    break;
            }
        } while (opc != opcs.length);
    }
    
    public static void Ventas(){
        String[] opcs = {"Agregar al carrito", "Realizar compra", "Salir"};
        int opc;
        do {  
            opc = Menu.Menu("Menu Ventas", "Lubricentro", opcs, "Inventario");
            switch(opc){
                case 0:
                    Inventario();
                    break;
                case 1: 
                    Ventas();
                    break;
                case 2: 
                    System.exit(0);
                    break;
            }
        } while (opc != opcs.length);
    }
    public static void Operarios(){
        
    }
    public static void Clientes(){
        
    }
    
}
