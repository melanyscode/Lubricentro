/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Objetos.Vehiculo;

/**
 *
 * @author Melanie Gutierrez
 */
public class GestionVehiculos {
    //importar estrucura para agregar productos
    Vehiculo v = new Vehiculo();
    
     private void agregar(){
         
    }
    private void buscar(){
        
        
    }
    private void mostrar(){
        
    }
    private void actualizar(){
        
    }
    
    private void eliminar(){
        
    }
    
    public void menuVehiculos(){
        String[] opcs = {"Agregar", "Eliminar", "Actualizar", "Buscar", "Volver"};
        int opc;
        do {  
            opc = Menu.Menu("Inventario Vehiculos", "Elija una opción", opcs, "Agregar");
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
