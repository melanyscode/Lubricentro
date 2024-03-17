/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import static Lubricentro.Lubricentro.Inicio;

/**
 *
 * @author Melanie Gutierrez
 */
public class GestionOperarios {
    // agregar la estructura con la que se va a agregar Operarios
   
    public void Registrar(){
        
    }
    
    public void Consultar(){
        
    }
    
    public void Asignar(){
        //asignar trabajos
    }
    
    public void Eliminar(){
        
    }
    
    public void OperarioMenu(){
        String[] opcs = {"Registrar", "Consultar", "Asignar Trabajo", "Elimianr", "Volver"};
        int opc;
        do {  
            opc = Menu.Menu("Menú Operarios", "Elija una opción", opcs, "Registrar");
            switch(opc){
                case 0:
                    Registrar();
                    break;
                case 1: 
                    Consultar();
                    break;
                case 2:
                    Asignar();
                    break;
                case 3: 
                    Eliminar();
                    break;
                case 4:
                    Inicio();
                    break;
            }
        } while (opc != opcs.length);
    }
}
