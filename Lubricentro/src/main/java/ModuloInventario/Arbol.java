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
public class Arbol {

    private NodoArbol raiz;

    public void insertar(Producto p) {
        if (raiz == null) {
            raiz = new NodoArbol(p);
        } else {
            insertarRecursivo(raiz, p);
        }
    }

    private void insertarRecursivo(NodoArbol n, Producto p) {
        if (p.getId() <= n.getProducto().getId()) {
            if (n.getLeft() == null) {
                n.setLeft(new NodoArbol(p));
            } else {
                insertarRecursivo(n.getLeft(), p);
            }
        } else {
            if (n.getRight() == null) {
                n.setRight(new NodoArbol(p));
            } else {
                insertarRecursivo(n.getRight(), p);
            }
        }
    }

    public String preorden() {
        if (raiz == null) {
            return "El arbol esta vacio";
        } else {
            return preordenRecursivo(raiz);
        }
    }

    private String preordenRecursivo(NodoArbol n) {
        String resultado = "";
        if (n != null) {
            resultado += n.getProducto().toString();
            resultado += preordenRecursivo(n.getLeft());
            resultado += preordenRecursivo(n.getRight());
        }
        return resultado;
    }

    public String postOrden() {
        if (raiz == null) {
            return "El arbol esta vacio";
        } else {
            return postOrdenRecursivo(raiz);
        }
    }

    private String postOrdenRecursivo(NodoArbol n) {
        String resultado = "";
        if (n != null) {
            resultado += postOrdenRecursivo(n.getLeft());
            resultado += postOrdenRecursivo(n.getRight());
            resultado += n.getProducto().toString();
        }
        return resultado;
    }

    //metodo para buscar por ID - mejor manejo de datos, se llega al dato deseado de manera mas rapida que leer toda una lista 
    public Producto buscarProducto(int id) {
        return buscarProductoRecursivo(raiz, id);
    }
    
    private Producto buscarProductoRecursivo(NodoArbol n, int id) {
        if (n == null){
            return null;
        }
        if(n.getProducto().getId() == id){
            return n.getProducto();
        }else if(id < n.getProducto().getId()){
            return buscarProductoRecursivo(n.getLeft(), id);
        }else{
            return buscarProductoRecursivo(n.getRight(), id);
        }
    }
    
}
