/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Objetos.TrabajoRealizado;

/**
 *
 * @author Melanie Gutierrez
 */
public class GestionTrabajos {
    
    TrabajoRealizado t = new TrabajoRealizado();
    //listas de trabajos en curso 
    //lista de trabajos terminados 
    
    private void agregar(){
        //logica para insertar - no hay estructura para guardar los trabajos pero 
    }
    private void enCurso(){
        
        
    }
    private void Terminados(){
        
    }
    private void mostrar(){
        
    }
    
    private void eliminar(){
        
    }
    
    public void menuTrabajos(){
        String[] opcs = {"Agregar", "Eliminar", "Actualizar", "Buscar", "Mostrar", "Volver"};
        int opc;
        do {  
            opc = Menu.Menu("Inventario de Reparaciones", "Elija una opción", opcs, "Agregar");
            switch(opc){
                case 0:
                    agregar();
                    break;
                case 1: 
                    eliminar();
                    break;
                case 2: 
                    enCurso();
                    break;
                case 3:
                    Terminados();
                    break;
                case 4:
                    mostrar();
                    break;
                case 5:
                    Lubricentro.Lubricentro.InicioAdmin();
            }
        } while (opc != opcs.length);
    }
    public void menuTrabajosU(){
        String[] opcs = {"Agregar", "Eliminar", "Actualizar", "Buscar", "Mostrar", "Volver"};
        int opc;
        do {  
            opc = Menu.Menu("Inventario de Reparaciones", "Elija una opción", opcs, "Agregar");
            switch(opc){
                case 0:
                    agregar();
                    break;
                case 1: 
                    eliminar();
                    break;
                case 2: 
                    enCurso();
                    break;
                case 3:
                    Terminados();
                    break;
                case 4:
                    mostrar();
                    break;
                case 5:
                    Lubricentro.Lubricentro.InicioUsuario();
            }
        } while (opc != opcs.length);
    }
}
