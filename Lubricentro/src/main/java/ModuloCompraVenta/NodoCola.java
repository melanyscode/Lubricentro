/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloCompraVenta;

import Objetos.Producto;

/**
 *
 * @author Melanie Gutierrez
 */
public class NodoCola {
    private Producto producto;
    private NodoCola siguiente;

    public NodoCola(Producto producto) {
        this.producto = producto;
        this.siguiente = null;
    }

    public NodoCola getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCola siguiente) {
        this.siguiente = siguiente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    
}
