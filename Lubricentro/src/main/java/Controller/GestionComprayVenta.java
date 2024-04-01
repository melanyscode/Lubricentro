/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ModuloCompraVenta.Cola;
import Objetos.Producto;

/**
 *
 * @author Melanie Gutierrez
 */
public class GestionComprayVenta {
    public static Cola listaCompra = new Cola();
    public static Cola listaVenta = new Cola();
    
    Producto p = new Producto();
    
    public void agregarCarrito(){
        
    }
    
    public void realizarCompra(){
        
    }
    
    public void Ventas(){
        String[] opcs = {"Agregar al carrito", "Realizar compra", "Volver"};
        int opc;
        do {  
            opc = Menu.Menu("Menú Ventas", "Lubricentro", opcs, "Inventario");
            switch(opc){
                case 0:
                    agregarCarrito();
                    break;
                case 1: 
                    realizarCompra();
                    break;
                case 2: 
                    Lubricentro.Lubricentro.InicioAdmin();
                    break;
            }
        } while (opc != opcs.length);
    }
     public void VentasU(){
        String[] opcs = {"Agregar al carrito", "Realizar compra", "Volver"};
        int opc;
        do {  
            opc = Menu.Menu("Menú Ventas", "Lubricentro", opcs, "Inventario");
            switch(opc){
                case 0:
                    agregarCarrito();
                    break;
                case 1: 
                    realizarCompra();
                    break;
                case 2: 
                    Lubricentro.Lubricentro.InicioUsuario();
                    break;
            }
        } while (opc != opcs.length);
    }
}
