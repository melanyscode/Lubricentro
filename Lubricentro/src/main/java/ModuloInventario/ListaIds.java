/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloInventario;

/**
 *
 * @author Melanie Gutierrez
 */
public class ListaIds {

    NodoListaIds cabeza;

    public ListaIds() {
        this.cabeza = null;
    }

    public boolean isEmpty() {
        return cabeza == null;
    }

    public void insetar(int id) {
        if (isEmpty()) {
            cabeza = new NodoListaIds(id);
        } else {
            NodoListaIds nuevo = new NodoListaIds(id);
            nuevo.setSiguiente(cabeza);
            cabeza = nuevo;
        }

    }

    public int buscarId(int id) {
        if (isEmpty()) {
            return 0;
        }
        NodoListaIds aux = cabeza;
        do {
            if (aux.getValor() == id) {
                return id;
            }
            aux = aux.getSiguiente();
        } while (aux != cabeza);
        return 0;
    }

}
