/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloCompraVenta;

import Objetos.Venta;

/**
 *
 * @author Melanie Gutierrez
 */
public class NodoColaV {
    private Venta venta;
    private NodoColaV siguiente;
    
    public NodoColaV(Venta venta){
        this.venta = venta;
        this.siguiente = null;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public NodoColaV getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoColaV siguiente) {
        this.siguiente = siguiente;
    }
    
}
