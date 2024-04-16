/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloTrabajos;

import ModuloInventario.NodoLista;
import Objetos.Producto;
import Objetos.TrabajoRealizado;

/**
 *
 * @author Melanie Gutierrez
 */
public class Nodo {
    private TrabajoRealizado trabajo;
    private Nodo siguiente;
    private Nodo anterior;

    public Nodo(TrabajoRealizado trabajo) {
        this.trabajo = trabajo;
        this.siguiente = null;
        this.anterior = null;
    }

    
    public TrabajoRealizado getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(TrabajoRealizado trabajo) {
        this.trabajo = trabajo;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }

    
}
