/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloClientes;

import Objetos.Cliente;

/**
 *
 * @author josea
 */
public class PilaCliente {

    private NodoCliente cima;

    public PilaCliente() {
        this.cima = null;
    }

    public boolean esVacia() {
        return cima == null;
    }

    public void apilar(Cliente cliente) {
        NodoCliente nuevo = new NodoCliente(cliente);
        if (esVacia()) {
            cima = nuevo;
        } else {
            nuevo.setSiguiente(cima);
            cima = nuevo;
        }
    }

    public void desapilar() {
        if (!esVacia()) {
            cima = cima.getSiguiente();
        } else {
            System.out.println("No se puede extraer ningún elemento porque la pila está vacía");
        }
    }

    public String imprimirPila() {
        StringBuilder respuesta = new StringBuilder();

        if (!esVacia()) {
            NodoCliente auxiliar = cima;
            while (auxiliar != null) {
                respuesta.append(auxiliar.getElemento().toString()).append("\n");
                auxiliar = auxiliar.getSiguiente();
            }
        }

        return respuesta.toString();
    }

    public NodoCliente getCima() {
        return cima;
    }

    public Cliente buscar(int id) {
        NodoCliente aux = cima;
        while (aux != null) {
            if (aux.getElemento().getIdCliente() == id) {
                return aux.getElemento();
            }
            aux = aux.getSiguiente();
        }
        return null;
    }
    
    public void vaciarPila(){
        cima = null;
    }
}
