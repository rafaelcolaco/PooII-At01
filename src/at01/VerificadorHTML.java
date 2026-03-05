package at01;
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class VerificadorHTML {

    public boolean deveGerarRelatorio(int totalRegistros) {
        String caminho = "/home/rafael/eclipse-workspace/At01/relatorio_carros.html";
        Path arquivo = Paths.get(caminho);

        if (!Files.exists(arquivo)) {
            return true; // se não existe gerar
        }

        try {
            String conteudo = Files.readString(arquivo);
            // Verifica se o relatório é exatamente do mesmo conjunto de dados
            if (conteudo.contains("Total de registros: " + totalRegistros)) {
                System.out.println("O relatório HTML já existe e está atualizado com os mesmos dados!");
                Scanner sc = new Scanner(System.in);
                System.out.print("Deseja gerar novamente mesmo assim? (s/n): ");
                String resposta = sc.nextLine().trim().toLowerCase();
                sc.close();
                return resposta.equals("s");
                
            }
        } catch (IOException e) {
            // se der erro na leitura, gera novamente 
        }
        return true;
    }
}