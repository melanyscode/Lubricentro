package Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexionBD {

    Connection conexion = null;
    PreparedStatement consulta = null;
    ResultSet resultado = null;

    String url = "jdbc:mysql://localhost:3306/lubricentro_jomean";
    String username = "root";
    String password = "Ameli@040699";

    public void setConexion() {
        try {
            this.conexion = DriverManager.getConnection(url, username, password);
        } catch (SQLException error) {
            System.err.println("Error al establecer la conexión: " + error.getMessage());
            error.printStackTrace();
        }
    }

    public void setConsulta(String consulta) {
        try {
            this.consulta = conexion.prepareStatement(consulta);
        } catch (SQLException error) {
            System.err.println("Error al preparar la consulta: " + error.getMessage());
            error.printStackTrace();
        }
    }

    public ResultSet getResultado() {
        try {
            return consulta.executeQuery();
        } catch (SQLException error) {
            System.err.println("Error al ejecutar la consulta: " + error.getMessage());
            error.printStackTrace();
            return null;
        }
    }

    public PreparedStatement getConsulta() {
        return consulta;
    }

    public void cerrarConexion() {
        try {
            if (resultado != null) {
                resultado.close();
            }
            if (consulta != null) {
                consulta.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
