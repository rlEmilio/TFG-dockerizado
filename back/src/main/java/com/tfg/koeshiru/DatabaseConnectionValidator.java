//esta clase sirve para detectar errores de conexion con la bbdd al iniciar la aplicacion.
//es normal que se muestre el chorizo de error si la conexión falla, porque la aplicación no podrá
//arrancar y la excepcion se propaga

package com.tfg.koeshiru;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DatabaseConnectionValidator {

    private final DataSource dataSource;

    public DatabaseConnectionValidator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void checkConnection() {
        try (Connection conn = dataSource.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeQuery("SELECT 1");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error de conexión a la base de datos al iniciar", e);
        }
    }
}


