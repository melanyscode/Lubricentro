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
public class NodoVenta {
    private Venta venta;
    private NodoVenta siguiente;

    public NodoVenta(Venta venta) {
        this.venta = venta;
        this.siguiente = null;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public NodoVenta getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoVenta siguiente) {
        this.siguiente = siguiente;
    }
    
}
