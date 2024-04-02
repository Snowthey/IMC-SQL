public class IMC {
    private String nome;
    private double altura;
    private double peso;
    private double imc;

    public IMC(String nome, double altura, double peso) {
        this.nome = nome;
        this.altura = altura;
        this.peso = peso;
        this.imc = calcularIMC();
    }

    public IMC(String nome, double altura, double peso, double imc) {
        this.nome = nome;
        this.altura = altura;
        this.peso = peso;
        this.imc = imc;
    }

    public String getNome() {
        return nome;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltura() {
        return altura;
    }

    public double getIMC() {
        return imc;
    }

    double calcularIMC() {
        return peso / (altura * altura);
    }
}
