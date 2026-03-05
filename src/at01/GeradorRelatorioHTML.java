package at01;
import java.io.*;
import java.util.*;

public class GeradorRelatorioHTML {

    public void gerar(List<CarroDTO> carros, ResultadoEstatistico statsPreco, 
                      ResultadoEstatistico statsKM, Map<String, Long> porFabricante) throws IOException {

        String caminho = "/home/rafael/eclipse-workspace/At01/relatorio_carros.html";

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html lang='pt-BR'><head><meta charset='UTF-8'>");
        html.append("<title>Relatório Estatístico de Vendas de Carros</title>");
        html.append("<script src='https://www.gstatic.com/charts/loader.js'></script>");
        html.append("<style>body{font-family:Arial,sans-serif;margin:40px;background:#f9f9f9}");
        html.append("h1,h2{color:#333} table{border-collapse:collapse;width:100%;margin:20px 0}");
        html.append("th,td{border:1px solid #999;padding:10px;text-align:center} th{background:#4CAF50;color:white}</style>");
        html.append("</head><body>");

        html.append("<h1> Relatório Estatístico - Vendas de Carros</h1>");
        html.append("<p><strong>Total de registros: ").append(carros.size()).append("</strong></p>");

        // Estatísticas Preço
        html.append("<h2> Estatísticas de Preço</h2>");
        html.append(criarTabelaEstatistica(statsPreco, "Preço (R$)"));

        // Estatísticas Quilometragem
        html.append("<h2>Estatísticas de Quilometragem</h2>");
        html.append(criarTabelaEstatistica(statsKM, "Quilometragem (km)"));

        // Top 10 Fabricantes
        html.append("<h2> Top 10 Fabricantes</h2><table><tr><th>Fabricante</th><th>Quantidade</th></tr>");
        porFabricante.entrySet().stream()
            .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
            .limit(10)
            .forEach(e -> html.append("<tr><td>").append(e.getKey())
                              .append("</td><td>").append(e.getValue()).append("</td></tr>"));
        html.append("</table>");

        // Gráficos Google Charts
        html.append("<h2> Gráficos</h2>");
        html.append("<div id='graficoBarras' style='width:100%;height:500px'></div>");
        html.append("<div id='graficoDispersao' style='width:100%;height:500px;margin-top:40px'></div>");

        html.append("<script>google.charts.load('current', {packages:['corechart']});");
        html.append("google.charts.setOnLoadCallback(desenharGraficos);");
        html.append("function desenharGraficos() {");

        // Gráfico de Barras - Top Fabricantes
        html.append("var dadosBarras = google.visualization.arrayToDataTable([['Fabricante','Quantidade']");
        porFabricante.entrySet().stream()
            .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
            .limit(8)
            .forEach(e -> html.append(",['").append(e.getKey()).append("',").append(e.getValue()).append("]"));
        html.append("]);");
        html.append("new google.visualization.BarChart(document.getElementById('graficoBarras')).draw(dadosBarras, {title:'Top Fabricantes', colors:['#4CAF50']});");

        // Gráfico de Dispersão - Preço x Quilometragem (limitado a 300 pontos)
        html.append("var dadosDispersao = google.visualization.arrayToDataTable([['Quilometragem','Preço']");
        carros.stream().limit(300).forEach(c ->
            html.append(",[").append(c.getQuilometragem()).append(",").append(c.getPreco()).append("]"));
        html.append("]);");
        html.append("new google.visualization.ScatterChart(document.getElementById('graficoDispersao')).draw(dadosDispersao, {");
        html.append("title:'Relação Preço × Quilometragem', hAxis:{title:'Quilometragem (km)'}, vAxis:{title:'Preço ($ Dollar)'}, colors:['#2196F3']});");

        html.append("}</script></body></html>");

        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {
            pw.print(html);
        }
        System.out.println("Relatório HTML gerado com sucesso: relatorio_carros.html");
    }

    private String criarTabelaEstatistica(ResultadoEstatistico r, String titulo) {
        return "<table><tr><th>Métrica</th><th>" + titulo + "</th></tr>" +
               "<tr><td>Média</td><td>" + String.format("%,.2f", r.media) + "</td></tr>" +
               "<tr><td>Mediana</td><td>" + String.format("%,.2f", r.mediana) + "</td></tr>" +
               "<tr><td>Moda</td><td>" + String.format("%,.2f", r.moda) + "</td></tr>" +
               "<tr><td>Variância</td><td>" + String.format("%,.2f", r.variancia) + "</td></tr>" +
               "<tr><td>Desvio Padrão</td><td>" + String.format("%,.2f", r.desvioPadrao) + "</td></tr></table>";
    }
}