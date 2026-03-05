package at01;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class ResultadoEstatistico {
    public double media;
    public double mediana;
    public double moda;
    public double variancia;
    public double desvioPadrao;
    public int quantidade;
    
    // Construtor vazio (para JAXB e Jackson)
    public ResultadoEstatistico() {}

   
    public ResultadoEstatistico(double media, double mediana, double moda, 
                                double variancia, double desvioPadrao, int quantidade) {
        this.media = media;
        this.mediana = mediana;
        this.moda = moda;
        this.variancia = variancia;
        this.desvioPadrao = desvioPadrao;
        this.quantidade = quantidade;
    }
}