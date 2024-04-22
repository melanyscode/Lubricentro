/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloOperario;

import Objetos.Venta;

/**
 *
 * @author Melanie Gutierrez
 */
public class NodoLista {
    private Venta venta;
    private NodoLista siguiente;
    private NodoLista anterior;

    public NodoLista(Venta venta) {
        this.venta = venta;
        this.siguiente = null;
        this.anterior = null;
    }

    
    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
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
    
    
    
}
