/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloClientes;

import Objetos.Cliente;

/**
 *
 * @author josea
 */
public class NodoCliente {

    private Cliente elemento;
    private NodoCliente siguiente;

    public NodoCliente(Cliente cliente) {
    this.elemento = cliente;
    this.siguiente = null;
}

    public Cliente getElemento() {
    return elemento;
    }

    public void setElemento(Cliente elemento) {
        this.elemento = elemento;
    }

    public NodoCliente getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCliente siguiente) {
        this.siguiente = siguiente;
    }

}
