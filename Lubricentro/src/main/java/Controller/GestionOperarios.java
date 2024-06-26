/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Conexiones.ConexionBD;
import static Lubricentro.Lubricentro.InicioAdmin;
import static Lubricentro.Lubricentro.InicioUsuario;
import static Lubricentro.Lubricentro.isAdmin;

import ModuloOperario.ColaOperario;
import ModuloOperario.ColaVenta;
import ModuloOperario.ListaVentas;
import Objetos.Cliente;
import Objetos.Operario;
import Objetos.TrabajoRealizado;
import Objetos.Venta;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author Melanie Gutierrez
 */
public class GestionOperarios {

    // agregar la estructura con la que se va a agregar Operarios
    ConexionBD conexion = new ConexionBD();
    // para agregar operarios esten disponibles o no disponibles 
    public static ColaOperario Operarios = new ColaOperario();
    //agregar los operarios que solo estan disponibles 
    public static ColaOperario colaDisponible = new ColaOperario();
    public static ColaOperario colaTrabajos = new ColaOperario();
    //agregar los operarios que no estan disponibles 

    //agregar ventas 
    // se agrega en el ordenamiento LIFO a la BD 
    public static ColaVenta ventaEnCola = new ColaVenta();

    public static ListaVentas ventaProcesada = new ListaVentas();
    public static GestionClientesyVehiculos gestionClientes = new GestionClientesyVehiculos();

    //por hacer cola de venta 
    public void AsignarTrabajo() {
        colaDisponible.agregarBDaCola();
        //para asignar un trabajo al operario, se le lee desde la lista de trabajos en gestion de trabajos
        GestionTrabajos.listaTrabajos.agregarBDaLista();
        GestionTrabajos.listaTrabajos.agregarListaArbol(GestionTrabajos.listaTrabajos);

        //pedir el id de cliente para vincular el vehiculo con el trabajo a realizar
        int id = 0;
        String idInput = JOptionPane.showInputDialog(null, "Ingrese el ID del cliente");
        if (idInput == null) {
            colaTrabajos.vaciarCola();
            colaDisponible.vaciarCola();
            GestionTrabajos.listaTrabajos.vaciarLista();
            return;
        } else {
            try {
                id = Integer.parseInt(idInput);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
            }
        }
        //verificar si el cliente tiene un id de vehiculo vinculado
        GestionClientesyVehiculos.agregarBDPila(GestionClientesyVehiculos.pilaClientes);
        if (!colaDisponible.isEmpty()) {
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
                        idServicio = 1;
                        idCliente = c.getIdCliente();
                        precio = t.getPrecio();
                        Operario o = colaDisponible.desencolar();
                        idOperario = o.getIdOperario();
                        o.setDisponible(false);
                        colaTrabajos.insertar(o);
                        actualizarBD(o);
                        Venta venta = new Venta(precio, idServicio, idCliente, idOperario);
                        JOptionPane.showMessageDialog(null, "Trabajo agregado a la Cola de trabajos:\n"
                                + "Servicio: " + t.getDescripcion() + "\n"
                                + "Cliente: " + idCliente + "\n"
                                + "Operario: " + idOperario + "\n"
                                + "Precio: " + precio);
                        ventaEnCola.insertar(venta);
                        ventaEnCola.agregarColaBD();
                        colaDisponible.vaciarCola();
                        colaTrabajos.vaciarCola();
                        GestionTrabajos.listaTrabajos.vaciarLista();
                        break;
                    case 1:
                        TrabajoRealizado t2 = GestionTrabajos.listaTrabajos.buscarId(2);
                        idServicio = 2;
                        idCliente = c.getIdCliente();
                        precio = t2.getPrecio();
                        Operario o2 = colaDisponible.desencolar();
                        idOperario = o2.getIdOperario();
                        o2.setDisponible(false);
                        colaTrabajos.insertar(o2);
                        actualizarBD(o2);
                        Venta venta2 = new Venta(precio, idServicio, idCliente, idOperario);
                        JOptionPane.showMessageDialog(null, "Trabajo agregado a la Cola de trabajos: \n"
                                + "Servicio: " + t2.getDescripcion() + "\n"
                                + "Cliente: " + idCliente + "\n"
                                + "Operario: " + idOperario + "\n"
                                + "Precio: " + precio);
                        ventaEnCola.insertar(venta2);
                        ventaEnCola.agregarColaBD();
                        colaTrabajos.vaciarCola();
                        colaDisponible.vaciarCola();
                        GestionTrabajos.listaTrabajos.vaciarLista();
                        break;
                    case 2:
                        TrabajoRealizado t3 = GestionTrabajos.listaTrabajos.buscarId(3);
                        idServicio = 3;
                        idCliente = c.getIdCliente();
                        precio = t3.getPrecio();
                        Operario o3 = colaDisponible.desencolar();
                        idOperario = o3.getIdOperario();
                        o3.setDisponible(false);
                        colaTrabajos.insertar(o3);
                        actualizarBD(o3);
                        Venta venta3 = new Venta(precio, idServicio, idCliente, idOperario);
                        JOptionPane.showMessageDialog(null, "Trabajo agregado a la Cola de trabajos: \n"
                                + "Servicio: " + t3.getDescripcion() + "\n"
                                + "Cliente: " + idCliente + "\n"
                                + "Operario: " + idOperario + "\n"
                                + "Precio: " + precio);
                        ventaEnCola.insertar(venta3);
                        ventaEnCola.agregarColaBD();
                        colaTrabajos.vaciarCola();
                        colaDisponible.vaciarCola();
                        GestionTrabajos.listaTrabajos.vaciarLista();
                        break;
                    case 3:
                        TrabajoRealizado t4 = GestionTrabajos.listaTrabajos.buscarId(4);
                        idServicio = 4;
                        idCliente = c.getIdCliente();
                        precio = t4.getPrecio();
                        Operario o4 = colaDisponible.desencolar();
                        idOperario = o4.getIdOperario();
                        o4.setDisponible(false);
                        colaTrabajos.insertar(o4);
                        actualizarBD(o4);
                        Venta venta4 = new Venta(precio, idServicio, idCliente, idOperario);
                        JOptionPane.showMessageDialog(null, "Trabajo agregado a la Cola de trabajos: \n"
                                + "Servicio: " + t4.getDescripcion() + "\n"
                                + "Cliente: " + idCliente + "\n"
                                + "Operario: " + idOperario + "\n"
                                + "Precio: " + precio);
                        ventaEnCola.insertar(venta4);
                        ventaEnCola.agregarColaBD();
                        colaDisponible.vaciarCola();
                        colaTrabajos.vaciarCola();
                        GestionTrabajos.listaTrabajos.vaciarLista();
                        break;
                    case 4:
                        TrabajoRealizado t5 = GestionTrabajos.listaTrabajos.buscarId(5);
                        idServicio = 5;
                        idCliente = c.getIdCliente();
                        precio = t5.getPrecio();
                        Operario o5 = colaDisponible.desencolar();
                        idOperario = o5.getIdOperario();
                        o5.setDisponible(false);
                        colaTrabajos.insertar(o5);
                        actualizarBD(o5);
                        Venta venta5 = new Venta(precio, idServicio, idCliente, idOperario);
                        JOptionPane.showMessageDialog(null, "Trabajo agregado a la Cola de trabajos: \n"
                                + "Servicio: " + t5.getDescripcion() + "\n"
                                + "Cliente: " + idCliente + "\n"
                                + "Operario: " + idOperario + "\n"
                                + "Precio: " + precio);
                        //guardar venta en cola de espera 
                        ventaEnCola.insertar(venta5);
                        ventaEnCola.agregarColaBD();
                        colaTrabajos.vaciarCola();
                        GestionTrabajos.listaTrabajos.vaciarLista();
                        colaDisponible.vaciarCola();
                        break;
                    case 5:
                        GestionTrabajos.listaTrabajos.vaciarLista();
                        colaTrabajos.vaciarCola();
                        colaDisponible.vaciarCola();
                        colaDisponible.vaciarCola();
                        OperarioMenu();
                        break;
                }
                ventaEnCola.vaciarCola();
                colaDisponible.vaciarCola();
            } else {
                JOptionPane.showMessageDialog(null, "El cliente no tiene un vehiculo asociado, intentelo de nuevo");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay Operarios Disponibles");
        }
    }

    public void consultarDispOperario() {
        colaDisponible.agregarBDaCola();
        JOptionPane.showMessageDialog(null, "Operarios:\n " + colaDisponible.toString() + "\n"
                + "-------------------------------------------\n"
                + "Operarios con trabajos asignados\n" + colaTrabajos.toString());
        colaDisponible.vaciarCola();
        colaTrabajos.vaciarCola();
    }

    public void FacturaID() {
        ///
        ///
        ///
        //lista de trabajos para leer el tipo de servicio que se le dio al cliente 
        GestionTrabajos.listaTrabajos.agregarBDaLista();
        GestionTrabajos.listaTrabajos.agregarListaArbol(GestionTrabajos.listaTrabajos);

        ventaProcesada.agregarBDaLista();
        ventaProcesada.toString();
        JOptionPane.showMessageDialog(null, ventaProcesada.toString());
        int idCliente = 0;
        String input = JOptionPane.showInputDialog(null, "Ingrese el ID de venta para procesar la venta y obtener la factura");
        if (input == null) {
            ventaProcesada.vaciarLista();
            GestionTrabajos.listaTrabajos.vaciarLista();
            colaTrabajos.vaciarCola();
            return;
        } else {
            try {
                idCliente = Integer.parseInt(input);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
            }
        }
        Venta v = ventaProcesada.buscarId(idCliente);
        //crear factura
        if (v == null) {
            ventaProcesada.vaciarLista();
            colaDisponible.vaciarCola();
            colaTrabajos.vaciarCola();
            JOptionPane.showMessageDialog(null, "No se encontro la venta, o no tiene un servicio asociado");
            return;
        }
        int idVenta = v.getIdVenta();
        java.sql.Date fechaActual = new java.sql.Date(System.currentTimeMillis());
        double precio = v.getTotal();
        double iva = precio * 0.13;
        double total = precio + iva;
        int idOperario = v.getIdOperario();
        //obtener nombre del operario
        Operario operario = obtenerOperario(idOperario);
        String nombreOperario = operario.getNombre();
        int idServicio = v.getIdServicio();
        String descripcion = GestionTrabajos.listaTrabajos.buscarId(idServicio).getDescripcion();

        //agregar factura a la BD
        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            conexion.setConsulta("INSERT INTO factura (id_cliente, fecha, total, id_operario, id_venta) VALUES (?,?,?,?,?)");
            preState = conexion.getConsulta();

            preState.setInt(1, idCliente);
            preState.setDate(2, fechaActual);
            preState.setDouble(3, total);
            preState.setInt(4, idOperario);
            preState.setInt(5, idVenta);
            preState.executeUpdate();
            conexion.cerrarConexion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            try {
                if (preState != null) {
                    preState.close();
                }
                conexion.cerrarConexion();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
            }
        }
        //imprimir factura

        JOptionPane.showMessageDialog(null, "Factura de Servicio\n"
                + "ID Venta: " + idVenta + "\n"
                + "Servicio: " + descripcion + "\n"
                + "Precio: " + precio + "\n"
                + "Total con I.V.A: " + total + "\n"
                + "Operario: " + nombreOperario);

        //mostrar el operario como disponible 
        colaDisponible.vaciarCola();
        operario.setDisponible(true);
        actualizarBD(operario);
        ventaProcesada.vaciarLista();
        colaTrabajos.vaciarCola();
        GestionTrabajos.listaTrabajos.vaciarLista();
    }

    public void OperarioMenu() {
        String[] opcs = {"Asignar trabajo", "Consultar Disponibilidad Operario", "Procesar", "Volver"};
        int opc;
        do {
            opc = Menu.Menu("Menú Operarios", "Elija una opción", opcs, "Registrar");
            switch (opc) {
                case 0:
                    AsignarTrabajo();
                    break;
                case 1:
                    consultarDispOperario();
                    break;
                case 2:
                    FacturaID();
                    break;
                case 3:
                    if (isAdmin) {
                        InicioAdmin();
                    } else {
                        InicioUsuario();
                    }
                    break;
            }
        } while (opc != opcs.length);
    }

    //metodo de base de datos 
    public void actualizarBD(Operario operario) {
        PreparedStatement preState = null;
        try {
            //actualizar disponibilidad del operario en la bd
            conexion.setConexion();
            conexion.setConsulta("UPDATE operario SET disponible = ? WHERE id_operario = ?");
            preState = conexion.getConsulta();
            preState.setBoolean(1, operario.isDisponible());
            preState.setInt(2, operario.getIdOperario());
            preState.executeUpdate();
            conexion.cerrarConexion();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
        } finally {
            try {
                if (preState != null) {
                    preState.close();
                }
                conexion.cerrarConexion();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
            }
        }

    }

    //buscar operario - PENDIENTE
    // construir operario desde la BD
    public Operario obtenerOperario(int idOperario) {
        PreparedStatement preState = null;
        Operario operario = new Operario();
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM operario WHERE id_operario = ?");
            preState = conexion.getConsulta();
            preState.setInt(1, idOperario);

            ResultSet rs = preState.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("Nombre");
                int id = rs.getInt("id_operario");
                boolean disponible = rs.getBoolean("disponible");

                operario = new Operario(nombre, id, disponible);
                return operario;
            } else {
                JOptionPane.showMessageDialog(null, "Operario no encontrado");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
        } finally {
            try {
                if (preState != null) {
                    preState.close();
                }
                conexion.cerrarConexion();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
            }
        }
        return operario;
    }
}
