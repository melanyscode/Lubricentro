package com.mycompany.lubricentro;

import javax.swing.JOptionPane;

/**
 *
 * @author josea
 */
public class Lubricentro {

    public static void main(String[] args) {

        int opcion;
        do {
            opcion = mostrarMenuPrincipal();
            switch (opcion) {
                case 1:
                    gestionarOperarios();
                    break;
                case 2:
                    gestionarVehiculos();
                    break;
                case 3:
                    Ayuda();
                    break;
                case 4:
                    Salir();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
                    break;
            }
        } while (opcion != 4);
    }


    private static int mostrarMenuPrincipal() {
        String menu = "Menú Principal:\n";
        menu += "1. Gestionar Operarios\n";
        menu += "2. Gestionar Vehiculos\n";
        menu += "3. Ayuda\n";
        menu += "4. Salir del programa\n";
        try {
            return Integer.parseInt(JOptionPane.showInputDialog(menu));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private static void gestionarOperarios() {
        
    }
    
    private static void gestionarVehiculos() {
        
    }
    
    private static void Ayuda() {
        
    }
    
    private static void Salir() {
        
    }
    
}
