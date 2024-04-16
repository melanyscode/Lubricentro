/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloClientes;

import Objetos.Cliente;

/**
 *
 * @author Melanie Gutierrez
 */
public class PilaCliente {
    private NodoCliente top;
    
    public NodoCliente getTop(){
        return top;
    }
    public boolean isEmpty(){
        return top == null;
    }
    public void push(Cliente cliente){
        NodoCliente nuevo = new NodoCliente(cliente);
        if(top == null){
            top = nuevo;
        }else{
            nuevo.setSiguiente(top);
            top = nuevo;
        }
    }
    
    public void desapilar(){
        if(!isEmpty()){
            top = top.getSiguiente();
        }
    }
    
    public String toString(){
        String respuesta = "";
        if(!isEmpty()){
            NodoCliente  aux = top;
            while(aux != null){
                respuesta += aux.getCliente().toString();
                aux = aux.getSiguiente();
            }
        }else{
            respuesta += "No hay Clientes registrados";
        }
        return respuesta;
    }
}
