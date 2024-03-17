/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ModuloInventario.ListaCircular;
import Objetos.Producto;

/**
 *
 * @author Melanie Gutierrez
 */
public class GestionProductos {
    public static ListaCircular listaProductos = new ListaCircular();
    Producto p = new Producto();
    
    private void agregar(){
        //logica para insertar (pedir al usuario la informacion del producto y despues agregarla a la lista circular
    }
    private void buscar(){
        
        
    }
    private void mostrar(){
        
    }
    private void actualizar(){
        
    }
    
    private void eliminar(){
        
    }
    
    public void menuProductos(){
        String[] opcs = {"Agregar", "Eliminar", "Actualizar", "Buscar", "Volver"};
        int opc;
        do {  
            opc = Menu.Menu("Inventario Productos", "Elija una opción", opcs, "Agregar");
            switch(opc){
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
