/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloCompraVenta;
import Objetos.Producto;

/**
 *
 * @author josea
 */
public class NodoColaCompra {
    private Producto producto;
    private NodoColaCompra siguiente;

    public NodoColaCompra(Producto producto) {
        this.producto = producto;
        this.siguiente = null;
    }

    public Producto getProducto() {
        return producto;
    }

    public NodoColaCompra getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoColaCompra siguiente) {
        this.siguiente = siguiente;
    }
}
    

