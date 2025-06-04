/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejempjdbcconexion2;
// Paquete donde se encuentra la clase

// Importación de librerías necesarias
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// Clase principal
public class ejempjdbcconexion2 {

    public static void main(String[] args) {

        // Credenciales de la base de datos
        String usuario = "root";
        String password = "Pipefelipe123";
        String url = "jdbc:mysql://localhost:3306/tienda_canasta_familiar";

        // Objetos para manejar la conexión y consultas
        Connection conexion = null;
        Statement statement = null;
        ResultSet rs = null;

        // Correo que vamos a insertar
        String nuevoCorreo = "admin11@correo.com";

        try {
            // Cargar el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver cargado correctamente.");

            // Establecer la conexión
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("✅ Conexión establecida con la base de datos.");

            // Crear el objeto Statement
            statement = conexion.createStatement();

            // Consulta para verificar si el correo ya existe en la base de datos
            String checkEmailSQL = "SELECT COUNT(*) FROM administrador WHERE correo = '" + nuevoCorreo + "'";
            ResultSet checkRs = statement.executeQuery(checkEmailSQL);

            // Si el correo no existe, lo insertamos
            if (checkRs.next() && checkRs.getInt(1) == 0) {
                // Insertar un nuevo administrador (incluyendo el campo 'contraseña')
                String insertSQL = "INSERT INTO administrador(Nombre, correo, contraseña) " +
                                   "VALUES('Admin_011', '" + nuevoCorreo + "', '12345')";
                statement.executeUpdate(insertSQL);
                System.out.println("✅ Registro insertado correctamente.");
            } else {
                System.out.println("⚠️ El correo " + nuevoCorreo + " ya existe en la base de datos.");
            }

            // Consultar los registros
            String selectSQL = "SELECT * FROM administrador";
            rs = statement.executeQuery(selectSQL);

            // Mostrar resultados
            System.out.println("\n📋 Lista de administradores:");
            while (rs.next()) {
                int id = rs.getInt("ID_administrador");
                String nombre = rs.getString("Nombre");
                String correo = rs.getString("correo");
                String contrasena = rs.getString("contraseña");

                System.out.println(id + " : " + nombre + " : " + correo + " : " + contrasena);
            }

        } catch (ClassNotFoundException e) {
            Logger.getLogger(ejempjdbcconexion2.class.getName()).log(Level.SEVERE, "❌ No se encontró el driver JDBC", e);
        } catch (SQLException e) {
            Logger.getLogger(ejempjdbcconexion2.class.getName()).log(Level.SEVERE, "❌ Error de conexión o SQL", e);
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
                System.out.println("\n🔒 Conexión cerrada.");
            } catch (SQLException e) {
                Logger.getLogger(ejempjdbcconexion2.class.getName()).log(Level.SEVERE, "❌ Error al cerrar la conexión", e);
            }
        }
    }
}
