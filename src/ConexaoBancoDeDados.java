import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConexaoBancoDeDados {
    private Connection conexao;
    private String URL_Bancodedados;
    private String usuario;
    private String senha;

    public ConexaoBancoDeDados() {
        URL_Bancodedados = "jdbc:mysql://localhost:3306/imc";
        usuario = "root";
        senha = "admin";
    }


    private void IniciarConexao(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection(URL_Bancodedados, usuario, senha);
            System.out.println("Conexão realizada com sucesso!");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erro ao acessar o Banco de Dados!");
        }
    }
    private void EncerrarConexao() throws SQLException{
        if(conexao != null){
            conexao.close();
        }
    }

    public void InserirDados(IMC objetoPessoa) throws SQLException {
        IniciarConexao();

        if (conexao != null) {
            try {
                PreparedStatement psInsert = conexao.prepareStatement("INSERT INTO imc(Nome, Altura, Peso, IMC) VALUES (?, ?, ?, ?)");
                psInsert.setString(1, objetoPessoa.getNome());
                psInsert.setDouble(2, objetoPessoa.getAltura());
                psInsert.setDouble(3, objetoPessoa.getPeso());
                psInsert.setDouble(4, objetoPessoa.getIMC());
                psInsert.executeUpdate();

                EncerrarConexao();

                JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar dados.");
                e.printStackTrace();
            }
        }
    }

    public void AlterarDados(IMC objetoPessoa, int id) throws SQLException{
        IniciarConexao();

        if(conexao != null){
            try{
                PreparedStatement comandoupdate = conexao.prepareStatement("UPDATE imc SET Nome = ?, Altura = ?, Peso = ?, IMC = ? WHERE id = ?");
                comandoupdate.setString(1, objetoPessoa.getNome());
                comandoupdate.setDouble(2, objetoPessoa.getAltura());
                comandoupdate.setDouble(3, objetoPessoa.getPeso());
                comandoupdate.setDouble(4, objetoPessoa.getIMC());
                comandoupdate.setInt(5, id);
                comandoupdate.execute();

                EncerrarConexao();
                JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Erro ao alterar dados!");
                e.printStackTrace();
            }
        }
    }

    public void RemoverDados(int id) throws SQLException{
        IniciarConexao();

        if(conexao != null){
            try {
                PreparedStatement comandodelete = conexao.prepareStatement("DELETE FROM imc WHERE id = ?");
                comandodelete.setInt(1, id);
                comandodelete.execute();

                EncerrarConexao();
                JOptionPane.showMessageDialog(null, "Dados removidos com sucesso!");
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Erro ao remover dados!");
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> Listagem() throws SQLException {
        IniciarConexao();

        ArrayList<String> relatorioBancoDeDados = new ArrayList<String>();

        if (conexao != null) {
            try {
                Statement comandoconsulta = conexao.createStatement();
                ResultSet resultadoconsultaBD = comandoconsulta.executeQuery("SELECT * FROM imc");

                while (resultadoconsultaBD.next()) {
                    String nome = resultadoconsultaBD.getString("Nome");
                    double altura = resultadoconsultaBD.getDouble("Altura");
                    double peso = resultadoconsultaBD.getDouble("Peso");
                    double imc = resultadoconsultaBD.getDouble("IMC");
                    int id = resultadoconsultaBD.getInt("id");

                    String resultado = String.format("# ID: %d Nome: %s Altura: %.2f Peso: %.2f IMC: %.2f \n -------------------------------------------------------------------------------------", id, nome, altura, peso, imc);
                    relatorioBancoDeDados.add(resultado);
                }

                EncerrarConexao();

                return relatorioBancoDeDados;
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao obter dados. Tente novamente!");
            }
        }
        return null;
    }


    public void exibirEstatisticas() throws SQLException {
        IniciarConexao();
        if(conexao != null){
            try {
                Statement stmt = conexao.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM imc");

                double maiorPeso = Double.MIN_VALUE;
                String nomeMaiorPeso = "";
                double menorPeso = Double.MAX_VALUE;
                String nomeMenorPeso = "";
                double somaPeso = 0;
                int countPeso = 0;

                double maiorAltura = Double.MIN_VALUE;
                String nomeMaiorAltura = "";
                double menorAltura = Double.MAX_VALUE;
                String nomeMenorAltura = "";
                double somaAltura = 0;
                int countAltura = 0;

                double somaIMC = 0;
                int countIMC = 0;
                double maiorIMC = Double.MIN_VALUE;
                String nomeMaiorIMC = "";
                double menorIMC = Double.MAX_VALUE;
                String nomeMenorIMC = "";

                while (rs.next()) {
                    String nome = rs.getString("Nome");
                    double peso = rs.getDouble("Peso");
                    double altura = rs.getDouble("Altura");
                    double imc = rs.getDouble("IMC");

                    if (peso > maiorPeso) {
                        maiorPeso = peso;
                        nomeMaiorPeso = nome;
                    }
                    if (peso < menorPeso) {
                        menorPeso = peso;
                        nomeMenorPeso = nome;
                    }
                    somaPeso += peso;
                    countPeso++;

                    if (altura > maiorAltura) {
                        maiorAltura = altura;
                        nomeMaiorAltura = nome;
                    }
                    if (altura < menorAltura) {
                        menorAltura = altura;
                        nomeMenorAltura = nome;
                    }
                    somaAltura += altura;
                    countAltura++;

                    if (imc > maiorIMC) {
                        maiorIMC = imc;
                        nomeMaiorIMC = nome;
                    }
                    if (imc < menorIMC) {
                        menorIMC = imc;
                        nomeMenorIMC = nome;
                    }
                    somaIMC += imc;
                    countIMC++;
                }

                double mediaPeso = somaPeso / countPeso;
                double mediaAltura = somaAltura / countAltura;
                double mediaIMC = somaIMC / countIMC;

                StringBuilder estatisticas = new StringBuilder();
                estatisticas.append("Estatísticas sobre IMC:\n\n");
                estatisticas.append("Maior Peso: ").append(String.format("%.2f", maiorPeso)).append(", Nome: ").append(nomeMaiorPeso).append("\n");
                estatisticas.append("Menor Peso: ").append(String.format("%.2f", menorPeso)).append(", Nome: ").append(nomeMenorPeso).append("\n");
                estatisticas.append("Média de todos os Pesos: ").append(String.format("%.2f", mediaPeso)).append("\n\n");

                estatisticas.append("Maior Altura: ").append(String.format("%.2f", maiorAltura)).append(", Nome: ").append(nomeMaiorAltura).append("\n");
                estatisticas.append("Menor Altura: ").append(String.format("%.2f", menorAltura)).append(", Nome: ").append(nomeMenorAltura).append("\n");
                estatisticas.append("Média de todas as Alturas: ").append(String.format("%.2f", mediaAltura)).append("\n\n");

                estatisticas.append("Maior IMC: ").append(String.format("%.2f", maiorIMC)).append(", Nome: ").append(nomeMaiorIMC).append("\n");
                estatisticas.append("Menor IMC: ").append(String.format("%.2f", menorIMC)).append(", Nome: ").append(nomeMenorIMC).append("\n");
                estatisticas.append("Média de todos os IMC: ").append(String.format("%.2f", mediaIMC));

                EncerrarConexao();

                JOptionPane.showMessageDialog(null, estatisticas.toString(), "Estatísticas do IMC", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao recuperar estatísticas.");
                e.printStackTrace();
            }
        }
    }
}
