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
public class Arbol {
     private NodoArbol raiz;

    public void insertar(TrabajoRealizado t) {
        if (raiz == null) {
            raiz = new NodoArbol(t);
        } else {
            insertarRecursivo(raiz, t);
        }
    }

    private void insertarRecursivo(NodoArbol n, TrabajoRealizado t) {
        if (t.getId() <= n.getTrabajo().getId()) {
            if (n.getLeft() == null) {
                n.setLeft(new NodoArbol(t));
            } else {
                insertarRecursivo(n.getLeft(), t);
            }
        } else {
            if (n.getRight() == null) {
                n.setRight(new NodoArbol(t));
            } else {
                insertarRecursivo(n.getRight(), t);
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
            resultado += n.getTrabajo().toString();
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
            resultado += n.getTrabajo().toString();
        }
        return resultado;
    }

    //metodo para buscar por ID - mejor manejo de datos, se llega al dato deseado de manera mas rapida que leer toda una lista 
    public TrabajoRealizado buscarProducto(int id) {
        return buscarProductoRecursivo(raiz, id);
    }
    
    private TrabajoRealizado buscarProductoRecursivo(NodoArbol n, int id) {
        if (n == null){
            return null;
        }
        if(n.getTrabajo().getId() == id){
            return n.getTrabajo();
        }else if(id < n.getTrabajo().getId()){
            return buscarProductoRecursivo(n.getLeft(), id);
        }else{
            return buscarProductoRecursivo(n.getRight(), id);
        }
    }
}
