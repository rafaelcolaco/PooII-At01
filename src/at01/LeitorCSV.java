package at01;
import java.io.*;
import java.util.*;

public class LeitorCSV {
    public List<CarroDTO> carregar() throws IOException {
        String caminho = "/home/rafael/eclipse-workspace/At01/src/at01/car_sales_data.csv";
        List<CarroDTO> lista = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            br.readLine(); // pula cabeçalho
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(",");
                if (dados.length >= 7) {
                    try {
                        lista.add(new CarroDTO(
                            dados[0].trim(), dados[1].trim(), Double.parseDouble(dados[2].trim()),
                            dados[3].trim(), Integer.parseInt(dados[4].trim()),
                            Long.parseLong(dados[5].trim()), Double.parseDouble(dados[6].trim())
                        ));
                    } catch (Exception ignored) {}
                }
            }
        }
        return lista;
    }
}