/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import static Lubricentro.Lubricentro.Inicio;
import Objetos.Cliente;

/**
 *
 * @author Melanie Gutierrez
 */
public class GestionClientes {
    // agregar estructura con la que se van a guardar los clientes
    Cliente c = new Cliente();
    
    public void Registrar(){
        
    }
    
    public void Consultar(){
        
    }
    
    public void Eliminar(){
        
    }
    
    
    public void Clientes(){
        String[] opcs = {"Registrar", "Consultar", "Elimianr", "Volver"};
        int opc;
        do {  
            opc = Menu.Menu("Menú Clientes", "Elija una opción", opcs, "Registrar");
            switch(opc){
                case 0:
                    Registrar();
                    break;
                case 1:
                    Consultar();
                    break;
                case 2:
                    Eliminar();
                    break;
                case 3:
                    Inicio();
                    break;
            }
        } while (opc != opcs.length);
    }
}
