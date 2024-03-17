package Lubricentro;

import Controller.GestionClientes;
import Controller.GestionComprayVenta;
import Controller.GestionOperarios;
import Controller.GestionProductos;
import Controller.GestionTrabajos;
import Controller.GestionVehiculos;
import Controller.Menu;
import javax.swing.JOptionPane;

/**
 *
 * @author josea
 */
public class Lubricentro {
    public static Menu menu = new Menu();
    public static GestionProductos gestionP = new GestionProductos();
    public static GestionComprayVenta gestionCYV = new GestionComprayVenta();
    public static GestionOperarios gestionOp = new GestionOperarios();
    public static GestionClientes gestionCl = new GestionClientes();
    public static GestionTrabajos gestionTr = new GestionTrabajos();
    public static GestionVehiculos gestionVe = new GestionVehiculos();
    
    public static void main(String[] args) {

        Inicio();
    }
   
    
    public static void Inicio(){
        String[] opcs = {"Inventario", "Ventas", "Operarios", "Clientes", "Trabajos", "Salir"};
        int opc;
        do {  
            opc = Menu.Menu("Menu Principal", "Lubricentro", opcs, "Inventario");
            switch(opc){
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
    
    public static void Inventario(){
        String[] opcs = {"Productos", "Vehiculos", "Volver"};
        int opc;
        do {  
            opc = Menu.Menu("Inventario", "Elija una opcion", opcs, "Productos");
            switch(opc){
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
    

}
