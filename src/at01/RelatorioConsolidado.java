package at01;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RelatorioConsolidado {
    public ResultadoEstatistico statsPreco;
    public ResultadoEstatistico statsKM;

    public RelatorioConsolidado() {} 

    public RelatorioConsolidado(ResultadoEstatistico preco, ResultadoEstatistico km) {
        this.statsPreco = preco;
        this.statsKM = km;
    }
}