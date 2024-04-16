/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloInventario;

/**
 *
 * @author Melanie Gutierrez
 */
public class NodoListaIds {
    private int valor;
    private NodoListaIds siguiente;

    public NodoListaIds(int valor) {
        this.valor = valor;
        this.siguiente = null;
    }

    public NodoListaIds getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoListaIds siguiente) {
        this.siguiente = siguiente;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    
    
}
