package at01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PersistenciaMySQL {
    private final String urlServidor = "jdbc:mysql://localhost:3306/";
    private final String dbName = "carros_db";
    private final String user = "root";
    private final String password = "";

    public PersistenciaMySQL() {
        prepararBanco();
    }

    private void prepararBanco() {
        try (Connection conn = DriverManager.getConnection(urlServidor, user, password);
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            stmt.executeUpdate("USE " + dbName);
            
            String sqlTabela = "CREATE TABLE IF NOT EXISTS estatisticas (" +
                               "id INT AUTO_INCREMENT PRIMARY KEY, " +
                               "metrica VARCHAR(50), " + 
                               "media DOUBLE, " +
                               "mediana DOUBLE, " +
                               "moda DOUBLE, " +
                               "variancia DOUBLE, " +
                               "desvio_padrao DOUBLE, " +
                               "data_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                               ")";
            stmt.executeUpdate(sqlTabela);
            
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar Banco de Dados: " + e.getMessage());
        }
    }

    /**
     * Método principal para salvar o relatório completo (Preço e KM)
     */
    public void salvarRelatorioCompleto(RelatorioConsolidado relatorio) {
        salvar(relatorio.statsPreco, "Preço");
        salvar(relatorio.statsKM, "Quilometragem");
    }

    private void salvar(ResultadoEstatistico stats, String tipoMetrica) {
        String urlCompleta = urlServidor + dbName;
        String sql = "INSERT INTO estatisticas (metrica, media, mediana, moda, variancia, desvio_padrao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(urlCompleta, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tipoMetrica);
            stmt.setDouble(2, stats.media);
            stmt.setDouble(3, stats.mediana);
            stmt.setDouble(4, stats.moda);
            stmt.setDouble(5, stats.variancia);
            stmt.setDouble(6, stats.desvioPadrao);

            stmt.executeUpdate();
            System.out.println("Métrica [" + tipoMetrica + "] salva no MySQL.");
            
        } catch (SQLException e) {
            System.err.println("Erro ao salvar [" + tipoMetrica + "] no MySQL: " + e.getMessage());
        }
    }
}