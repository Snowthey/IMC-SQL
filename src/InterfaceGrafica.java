import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;

public class InterfaceGrafica extends JFrame implements ActionListener{
    ConexaoBancoDeDados objBancoDeDados;
    private JButton btnCadastrar;
    private JButton btnRelatorio;
    private JButton btnAlterar;
    private JButton btnRemover;
    private JButton btnListagem;
    private JLabel lblTitulo;
    private JLabel lblSubTitulo;
    private JLabel lblNome;
    private JTextField txtNome;
    private JLabel lblPeso;
    private JLabel lblExemplo1;
    private JLabel lblExemplo2;
    private JTextField txtPeso;
    private JLabel lblAltura;
    private JTextField txtAltura;
    private JTextArea listaPesquisaBancoDeDados;
    private JScrollPane scrollPesquisaBancoDedados;
    private JLabel lblResultadoPesquisa;
    private JLabel lblid;
    private JTextField txtid;
    private JLabel lblResultado;
    private JLabel lblMSG;
    private JLabel lblIMC;
    private Container ctn;


    public InterfaceGrafica() {
        setSize(720, 450);
        setTitle("IMC");
        ctn = getContentPane();
        ctn.setLayout(null);

        lblTitulo = new JLabel("IMC");
        lblSubTitulo = new JLabel("Indice de Massa Corporal");
        lblNome = new JLabel("Nome");
        txtNome = new JTextField();
        lblPeso = new JLabel("Peso");
        lblAltura = new JLabel("Altura");
        lblExemplo1 = new JLabel("Exemplo: 1.70");
        lblExemplo2 = new JLabel("Exemplo: 90");
        txtid = new JTextField();
        lblid = new JLabel("ID");
        txtPeso = new JTextField();
        txtAltura = new JTextField();
        lblResultado = new JLabel();
        lblMSG = new JLabel("Mensagem: ");
        lblIMC = new JLabel("Seu IMC é: ");
        btnCadastrar = new JButton("Cadastrar");
        btnAlterar = new JButton("Alterar");
        btnRemover = new JButton("Remover");
        btnListagem = new JButton("Listagem");
        btnRelatorio = new JButton("Relatorio");
        listaPesquisaBancoDeDados = new JTextArea();
        scrollPesquisaBancoDedados = new JScrollPane(listaPesquisaBancoDeDados);
        lblResultadoPesquisa = new JLabel("Resultado da Pesquisa no Bando de Dados");


        lblTitulo.setBounds(10, 10, 100, 55);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 40));
        lblSubTitulo.setBounds(10, 30, 250, 75);
        lblSubTitulo.setFont(new Font("Arial", Font.PLAIN, 15));
        lblNome.setBounds(10, 80, 100, 25);
        txtNome.setBounds(50, 80, 250, 25);
        lblAltura.setBounds(10, 130, 100, 25);
        txtAltura.setBounds(50, 130, 100, 25);
        lblPeso.setBounds(10, 180, 100, 25);
        txtPeso.setBounds(50, 180, 100, 25);
        lblExemplo1.setBounds(50, 150, 100, 25);
        lblExemplo2.setBounds(50, 200, 100, 25);
        lblIMC.setBounds(10, 230, 120, 25);
        lblResultado.setBounds(80, 230, 250, 25);
        lblMSG.setBounds(10, 260, 250, 25);
        btnCadastrar.setBounds(50, 300, 200, 30);
        btnAlterar.setBounds(50, 335, 100, 30);
        btnRemover.setBounds(150, 335, 100, 30);
        btnListagem.setBounds(50, 370, 100, 30);
        btnRelatorio.setBounds(150, 370, 100, 30);
        lblid.setBounds(260, 300, 20, 30);
        txtid.setBounds(278, 300, 20, 30);
        scrollPesquisaBancoDedados.setBounds(350, 80, 350, 322);
        lblResultadoPesquisa.setBounds(350, 50, 250, 30);

        ctn.add(lblAltura);
        ctn.add(txtAltura);
        ctn.add(lblPeso);
        ctn.add(txtPeso);
        ctn.add(lblSubTitulo);
        ctn.add(lblTitulo);
        ctn.add(btnRelatorio);
        ctn.add(btnCadastrar);
        ctn.add(btnListagem);
        ctn.add(btnAlterar);
        ctn.add(btnRemover);
        ctn.add(lblid);
        ctn.add(txtid);
        ctn.add(scrollPesquisaBancoDedados);
        ctn.add(lblResultadoPesquisa);
        ctn.add(lblIMC);
        ctn.add(lblResultado);
        ctn.add(lblMSG);
        ctn.add(lblExemplo1);
        ctn.add(lblExemplo2);
        ctn.add(lblNome);
        ctn.add(txtNome);

        setVisible(true);
        btnRemover.addActionListener(this);
        btnAlterar.addActionListener(this);
        btnListagem.addActionListener(this);
        btnCadastrar.addActionListener(this);
        btnRelatorio.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Cadastrar")) {
            String nome = txtNome.getText();
            double altura = Double.parseDouble(txtAltura.getText());
            double peso = Double.parseDouble(txtPeso.getText());

            IMC pessoa = new IMC(nome, altura, peso);

            double imc = pessoa.calcularIMC();

            lblIMC.setText("Seu IMC é: " + String.format("%.2f", imc));

            if (imc >= 18.5 && imc <= 25) {
                lblMSG.setText("Você está no peso ideal.");
            } else if (imc > 25) {
                lblMSG.setText("Você está acima do peso ideal.");
            } else {
                lblMSG.setText("Você está abaixo do peso ideal.");
            }

            try {
                ConexaoBancoDeDados objBancoDeDados = new ConexaoBancoDeDados();
                objBancoDeDados.InserirDados(pessoa);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        if (e.getActionCommand().equals("Alterar")) {
            int id = Integer.parseInt(txtid.getText());
            String nome = txtNome.getText();
            double altura = Double.parseDouble(txtAltura.getText());
            double peso = Double.parseDouble(txtPeso.getText());

            double novoIMC = peso / (altura * altura);

            IMC pessoa = new IMC(nome, altura, peso, novoIMC);

            try {
                ConexaoBancoDeDados objBancoDeDados = new ConexaoBancoDeDados();
                objBancoDeDados.AlterarDados(pessoa, id);


            } catch (SQLException e1) {
                e1.printStackTrace();

            }
        }

        if (e.getActionCommand().equals("Remover")) {
            int id = Integer.parseInt(txtid.getText());

            try {
                ConexaoBancoDeDados objBancoDeDados = new ConexaoBancoDeDados();
                objBancoDeDados.RemoverDados(id);


            } catch (SQLException e1) {
                e1.printStackTrace();

            }
        }

        if (e.getActionCommand().equals("Listagem")) {
            try {
                ConexaoBancoDeDados objBancoDeDados = new ConexaoBancoDeDados();
                ArrayList<String> listaDados = objBancoDeDados.Listagem();
                StringBuilder dadosFormatados = new StringBuilder();
                for (String dado : listaDados) {
                    dadosFormatados.append(dado).append("\n");
                }

                JTextArea textArea = new JTextArea(dadosFormatados.toString());
                textArea.setEditable(false);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPesquisaBancoDedados.setViewportView(scrollPane);

                JOptionPane.showMessageDialog(this, "Listagem de Dados Realizada com Sucesso!", "Listagem de Dados", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao obter dados. Tente novamente!");
            }
        }


        if (e.getActionCommand().equals("Relatorio")) {
            try {
                ConexaoBancoDeDados objBancoDeDados = new ConexaoBancoDeDados();

                objBancoDeDados.exibirEstatisticas();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao gerar relatório. Tente novamente!");
            }
        }

    }
}
