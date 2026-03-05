package at01;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Ler o CSV
            LeitorCSV leitor = new LeitorCSV();
            List<CarroDTO> carros = leitor.carregar();
            System.out.println( carros.size() + " carros carregados do CSV!");

            // 2. Verificação do HTML
            VerificadorHTML verificador = new VerificadorHTML();
            if (!verificador.deveGerarRelatorio(carros.size())) {
                System.out.println(" Programa encerrado conforme sua escolha (nenhuma persistência foi realizada).");
                return; 
            }

            // 3. Início do processamento
            System.out.println("Iniciando processamento (estatísticas + persistência + HTML)...");

            // 4. Calcular estatísticas
            CalculadoraEstatisticas calc = new CalculadoraEstatisticas();
            
            ResultadoEstatistico statsPreco = calc.calcular(
                carros.stream().map(c -> (double) c.getPreco()).toList());
            
            ResultadoEstatistico statsKM = calc.calcular(
                carros.stream().map(c -> (double) c.getQuilometragem()).toList());

            // Agrupar os dados em um único objeto ---
            RelatorioConsolidado relatorioCompleto = new RelatorioConsolidado(statsPreco, statsKM);

            // 5. Contagem por fabricante 
            Map<String, Long> porFabricante = carros.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    CarroDTO::getFabricante, 
                    java.util.stream.Collectors.counting()
                ));

            // 6. Persistências (
            String nomeArquivoBase = "relatorio_estatistico_completo";

            // Persistência em JSON 
            new PersistenciaJSON().salvar(relatorioCompleto, nomeArquivoBase);
            
            // Persistência em XML 
            new PersistenciaXML().salvar(relatorioCompleto, nomeArquivoBase);
            
            // Persistência em MySQL 
            PersistenciaMySQL mysql = new PersistenciaMySQL();
            mysql.salvarRelatorioCompleto(relatorioCompleto);

            // 7. Gerar relatório HTML
            new GeradorRelatorioHTML().gerar(carros, statsPreco, statsKM, porFabricante);

            System.out.println("\nPROCESSAMENTO CONCLUÍDO COM SUCESSO!");

        } catch (Exception e) {
            System.err.println("Ocorreu um erro durante o processamento:");
            e.printStackTrace();
        }
    }
}