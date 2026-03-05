package at01;
import java.util.*;

public class CalculadoraEstatisticas {
    public ResultadoEstatistico calcular(List<Double> valores) {
        if (valores.isEmpty()) return new ResultadoEstatistico(0,0,0,0,0,0);
        
        int n = valores.size();
        double media = valores.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        
        List<Double> ordenados = new ArrayList<>(valores);
        Collections.sort(ordenados);
        double mediana = (n % 2 == 0) ? 
            (ordenados.get(n/2-1) + ordenados.get(n/2)) / 2.0 : ordenados.get(n/2);
        
        Map<Double, Integer> frequencia = new HashMap<>();
        valores.forEach(v -> frequencia.merge(v, 1, Integer::sum));
        double moda = frequencia.entrySet().stream()
            .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(0.0);
        
        double variancia = valores.stream()
            .mapToDouble(v -> Math.pow(v - media, 2)).sum() / n;
        double desvioPadrao = Math.sqrt(variancia);
        
        return new ResultadoEstatistico(media, mediana, moda, variancia, desvioPadrao, n);
    }
}