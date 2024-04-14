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
public class NodoCliente {
    private Cliente cliente;
    private NodoCliente siguiente;  

    public NodoCliente() {
        this.siguiente = null;
    }

    public NodoCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public NodoCliente getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCliente siguiente) {
        this.siguiente = siguiente;
    }
    
    
    
    
    
}
