package at01;

public class CarroDTO {
    private final String fabricante;
    private final String modelo;
    private final double tamanhoMotor;
    private final String tipoCombustivel;
    private final int anoFabricacao;
    private final long quilometragem;
    private final double preco;

    public CarroDTO(String fabricante, String modelo, double tamanhoMotor, String tipoCombustivel,
                    int anoFabricacao, long quilometragem, double preco) {
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.tamanhoMotor = tamanhoMotor;
        this.tipoCombustivel = tipoCombustivel;
        this.anoFabricacao = anoFabricacao;
        this.quilometragem = quilometragem;
        this.preco = preco;
    }

    public String getFabricante() { return fabricante; }
    public String getModelo() { return modelo; }
    public double getTamanhoMotor() { return tamanhoMotor; }
    public String getTipoCombustivel() { return tipoCombustivel; }
    public int getAnoFabricacao() { return anoFabricacao; }
    public long getQuilometragem() { return quilometragem; }
    public double getPreco() { return preco; }
}