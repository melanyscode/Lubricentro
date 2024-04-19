/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloOperario;

import Objetos.Operario;

/**
 *
 * @author Melanie Gutierrez
 */
public class NodoCola {
    private Operario operario;
    private NodoCola siguiente;

    public NodoCola(Operario operario) {
        this.operario = operario;
        this.siguiente = null;
    }

    public Operario getOperario() {
        return operario;
    }

    public void setOperario(Operario operario) {
        this.operario = operario;
    }

    public NodoCola getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCola siguiente) {
        this.siguiente = siguiente;
    }
    
    
}
