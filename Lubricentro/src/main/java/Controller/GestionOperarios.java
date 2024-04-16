/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import static Lubricentro.Lubricentro.InicioAdmin;
import javax.swing.JOptionPane;

/**
 *
 * @author Melanie Gutierrez
 */
public class GestionOperarios {
    // agregar la estructura con la que se va a agregar Operarios

    public void AsignarTrabajo() {
        //para asignar un trabajo al operario, se le lee desde la lista de trabajos en gestion de trabajos
        GestionTrabajos.listaTrabajos.agregarBDaLista();
        GestionTrabajos.listaTrabajos.agregarListaArbol(GestionTrabajos.listaTrabajos);

        //pedir el id de cliente para vincular el vehiculo con el trabajo a realizar
        int id = 0;
        String idInput = JOptionPane.showInputDialog(null, "Ingrese el ID del cliente");
        if (idInput == null) {
            OperarioMenu();
        } else {
            try {
                id = Integer.parseInt(idInput);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
            }
        }
        
    }

    public void Procesar() {
        //el operario procesa el trabajo asignado
    }

    public void OperarioMenu() {
        String[] opcs = {"Asignar trabajo", "Consultar", "Volver"};
        int opc;
        do {
            opc = Menu.Menu("Menú Operarios", "Elija una opción", opcs, "Registrar");
            switch (opc) {
                case 0:
                    AsignarTrabajo();
                    break;
                case 1:
                    Procesar();
                    break;
                case 2:
                    InicioAdmin();
                    break;
            }
        } while (opc != opcs.length);
    }
}
