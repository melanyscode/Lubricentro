/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import static Lubricentro.Lubricentro.InicioAdmin;
import ModuloOperario.ColaOperario;
import Objetos.Cliente;
import Objetos.TrabajoRealizado;
import Objetos.Venta;
import javax.swing.JOptionPane;

/**
 *
 * @author Melanie Gutierrez
 */
public class GestionOperarios {

    // agregar la estructura con la que se va a agregar Operarios
    public static ColaOperario colaDisponible = new ColaOperario();
    public static ColaOperario colaTrabajos = new ColaOperario();
    public static ColaOperario trabajosAsignados = new ColaOperario();
    public static GestionClientesyVehiculos gestionClientes = new GestionClientesyVehiculos();
    
    //por hacer cola de venta 

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
        //verificar si el cliente tiene un id de vehiculo vinculado
        GestionClientesyVehiculos.agregarBDPila(GestionClientesyVehiculos.pilaClientes);
        if (GestionClientesyVehiculos.pilaClientes.buscar(id) != null && GestionClientesyVehiculos.pilaClientes.buscar(id).getIdVehiculo() != 0) {
            int idServicio = 0;
            int idCliente = 0;
            double precio = 0;
            int idOperario = 0;

            Cliente c = GestionClientesyVehiculos.pilaClientes.buscar(id);

            //mostrar la informacion de servicio y elegir 
            String mensaje = "Trabajos Disponibles \n" + GestionTrabajos.listaTrabajos.toString();
            String[] opcs = {"1", "2", "3", "4", "5", "Cancelar"};
            int opc = JOptionPane.showOptionDialog(null, mensaje, "Seleccione un servicio", 0, JOptionPane.QUESTION_MESSAGE, null, opcs, "1");
            switch (opc) {
                case 0:
                    TrabajoRealizado t = GestionTrabajos.listaTrabajos.buscarId(1);
                    colaDisponible.agregarBDaCola();
                    idServicio = 1;
                    idCliente = c.getIdCliente();
                    precio = GestionTrabajos.listaTrabajos.buscarId(1).getPrecio();
                    idOperario = colaDisponible.desencolar().getIdOperario();
                    Venta venta = new Venta(idServicio, precio, idCliente, idOperario);
                    JOptionPane.showMessageDialog(null, "Trabajo agregado a la Cola de trabajos:\n"
                            + "Servicio: " + t.getDescripcion() + "\n"
                            + "Cliente: " + idCliente + "\n"
                            + "Operario: " + idOperario + "\n"
                            + "Precio: " + precio);
                    break;
                case 1: 
                    TrabajoRealizado t2 = GestionTrabajos.listaTrabajos.buscarId(2);
                    colaDisponible.agregarBDaCola();
                    idServicio = 1;
                    idCliente = c.getIdCliente();
                    precio = GestionTrabajos.listaTrabajos.buscarId(1).getPrecio();
                    idOperario = colaDisponible.desencolar().getIdOperario();
                    Venta venta2 = new Venta(idServicio, precio, idCliente, idOperario);
                    JOptionPane.showMessageDialog(null, "Trabajo agregado a la Cola de trabajos: \n"
                            + "Servicio: " + t2.getDescripcion() + "\n"
                            + "Cliente: " + idCliente + "\n"
                            + "Operario: " + idOperario + "\n"
                            + "Precio: " + precio);
                    break;
                case 2: 
                    TrabajoRealizado t3 = GestionTrabajos.listaTrabajos.buscarId(3);
                    colaDisponible.agregarBDaCola();
                    idServicio = 1;
                    idCliente = c.getIdCliente();
                    precio = GestionTrabajos.listaTrabajos.buscarId(1).getPrecio();
                    idOperario = colaDisponible.desencolar().getIdOperario();
                    Venta venta3 = new Venta(idServicio, precio, idCliente, idOperario);
                    JOptionPane.showMessageDialog(null, "Trabajo agregado a la Cola de trabajos: \n"
                            + "Servicio: " + t3.getDescripcion() + "\n"
                            + "Cliente: " + idCliente + "\n"
                            + "Operario: " + idOperario + "\n"
                            + "Precio: " + precio);
                    break;
                case 3: 
                    TrabajoRealizado t4 = GestionTrabajos.listaTrabajos.buscarId(4);
                    colaDisponible.agregarBDaCola();
                    idServicio = 1;
                    idCliente = c.getIdCliente();
                    precio = GestionTrabajos.listaTrabajos.buscarId(1).getPrecio();
                    idOperario = colaDisponible.desencolar().getIdOperario();
                    Venta venta4 = new Venta(idServicio, precio, idCliente, idOperario);
                    JOptionPane.showMessageDialog(null, "Trabajo agregado a la Cola de trabajos: \n"
                            + "Servicio: " + t4.getDescripcion() + "\n"
                            + "Cliente: " + idCliente + "\n"
                            + "Operario: " + idOperario + "\n"
                            + "Precio: " + precio);
                    break;
                case 4: 
                TrabajoRealizado t5 = GestionTrabajos.listaTrabajos.buscarId(5);
                    colaDisponible.agregarBDaCola();
                    idServicio = 1;
                    idCliente = c.getIdCliente();
                    precio = GestionTrabajos.listaTrabajos.buscarId(1).getPrecio();
                    idOperario = colaDisponible.desencolar().getIdOperario();
                    Venta venta5 = new Venta(idServicio, precio, idCliente, idOperario);
                    JOptionPane.showMessageDialog(null, "Trabajo agregado a la Cola de trabajos: \n"
                            + "Servicio: " + t5.getDescripcion() + "\n"
                            + "Cliente: " + idCliente + "\n"
                            + "Operario: " + idOperario + "\n"
                            + "Precio: " + precio);
                    break;
                case 5: 
                    OperarioMenu();
                    break;

            }
        } else {
            JOptionShowMessageDialog(null, "El cliente no tiene un vehiculo asociado, intentelo de nuevo");
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

    private void JOptionShowMessageDialog(Object object, String el_cliente_no_tiene_un_vehiculo_asociado_) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
