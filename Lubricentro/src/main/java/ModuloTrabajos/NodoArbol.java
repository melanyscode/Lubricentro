/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloTrabajos;

import Objetos.TrabajoRealizado;

/**
 *
 * @author Melanie Gutierrez
 */
public class NodoArbol {
    private TrabajoRealizado trabajo;
    private NodoArbol left;
    private NodoArbol right;

    public NodoArbol(TrabajoRealizado trabajo) {
        this.trabajo = trabajo;
    }
    

    public TrabajoRealizado getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(TrabajoRealizado trabajo) {
        this.trabajo = trabajo;
    }

    public NodoArbol getLeft() {
        return left;
    }

    public void setLeft(NodoArbol left) {
        this.left = left;
    }

    public NodoArbol getRight() {
        return right;
    }

    public void setRight(NodoArbol right) {
        this.right = right;
    }

   
}
