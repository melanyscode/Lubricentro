/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloInventario;

import Objetos.Producto;

/**
 *
 * @author Melanie Gutierrez
 */
public class NodoLista {
    private Producto producto;
    private NodoLista siguiente;
    private NodoLista anterior;

    public NodoLista(Producto producto) {
        this.producto = producto;
        this.siguiente = null;
        this.anterior = null;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public NodoLista getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoLista siguiente) {
        this.siguiente = siguiente;
    }

    public NodoLista getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoLista anterior) {
        this.anterior = anterior;
    }

    @Override
    public String toString() {
        return "NodoLista" + " Producto: " + producto + ", Siguiente: " + siguiente + ", Anterior: " + anterior;
    }
    
    
    
    
}
