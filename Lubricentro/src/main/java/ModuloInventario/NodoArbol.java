/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModuloInventario;

import Objetos.Producto;

/**
 *
 * @author Melanie Gutierrez
 */
public class NodoArbol {
    private Producto producto;
    private NodoArbol left;
    private NodoArbol right;

    public NodoArbol(Producto producto) {
        this.producto = producto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
