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
public class ColaCompra {
    private NodoColaCompra frente;
    private NodoColaCompra ultimo;

    public ColaCompra() {
        this.frente = null;
        this.ultimo = null;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public void agregar(Producto producto) {
        NodoColaCompra nuevo = new NodoColaCompra(producto);
        if (estaVacia()) {
            frente = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.setSiguiente(nuevo);
            ultimo = nuevo;
        }
    }

    public Producto atender() {
        if (!estaVacia()) {
            NodoColaCompra actual = frente;
            frente = frente.getSiguiente();
            if (frente == null) {
                ultimo = null;
            }
            return actual.getProducto();
        } else {
            return null;
        }
    }

    public String mostrarCarrito() {
        StringBuilder mensaje = new StringBuilder();
        NodoColaCompra actual = frente;
        while (actual != null) {
            mensaje.append("Producto en el carrito: ").append(actual.getProducto().getNombre()).append("\n");
            actual = actual.getSiguiente();
        }
        return mensaje.toString();
    }
}
    

