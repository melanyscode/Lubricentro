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
public class ColaVenta {
    NodoColaV inicio;
    NodoColaV fin;

    public ColaVenta() {
    }
    
    public boolean isEmpty(){
        return inicio == null;
    }
    
    public void agregar(Venta v) {
        NodoColaV nuevo = new NodoColaV(v);
        if (isEmpty()) {
            inicio = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
    }
    
    public Venta desencolar() {
        Venta aux = inicio.getVenta();
        inicio.setSiguiente(inicio);
        return aux;
    }
    
    public Venta inicioCola(){
        return inicio.getVenta();
    }
    public String imprimirCola() {
        String mensaje = "";
        if (isEmpty()) {
            mensaje += "No hay productos en la cola de compra";
        } else {
            mensaje += "Lista de Productos:\n";
            NodoColaV aux = inicio;
            while (aux != null) {
               mensaje += aux.getVenta().toString();
                aux = aux.getSiguiente();
            }
        }
        return mensaje;
    }
    public double precioTotal(){
        double res = 0;
        if(isEmpty()){
            res = 0;
        }else{
            NodoColaV aux = inicio;
            while(aux != null){
                res += aux.getVenta().getPrecio();
                aux = aux.getSiguiente();
            }
        }
        return res;
    }
}
