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
public class Cola {

    private NodoCola inicio;
    private NodoCola fin;

    public Cola() {
    }

    //metodos de la cola
    public boolean isEmpty() {
        return inicio == null;
    }

    public void agregar(Producto p) {
        NodoCola nuevo = new NodoCola(p);
        if (isEmpty()) {
            inicio = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
    }

    public Producto desencolar() {
        Producto aux = inicio.getProducto();
        inicio.setSiguiente(inicio);
        return aux;
    }

    public Producto inicioCola() {
        return inicio.getProducto();
    }

    public String imprimirCola() {
        String mensaje = "";
        if (isEmpty()) {
            mensaje += "No hay productos en la cola de compra";
        } else {
            mensaje += "Lista de Productos:\n";
            NodoCola aux = inicio;
            while (aux != null) {
                mensaje += aux.getProducto().getNombre() + " - ₡" + aux.getProducto().getPrecio() + "\n";
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
            NodoCola aux = inicio;
            while(aux != null){
                res += aux.getProducto().getPrecio();
                aux = aux.getSiguiente();
            }
        }
        return res;
    }
}
