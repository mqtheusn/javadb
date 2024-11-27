package Controller;

import View.Inicio_GUI;
import static View.Inicio_GUI.cod2_txt;
import static View.Inicio_GUI.cod_txt;
import static View.Inicio_GUI.email2_txt;
import static View.Inicio_GUI.email_txt;
import static View.Inicio_GUI.nome2_txt;
import static View.Inicio_GUI.nome_txt;
import static View.Inicio_GUI.tel2_txt;
import static View.Inicio_GUI.tel_txt;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Funcoes_DAO {

    public static int cod;
    public static String nom;
    public static String em;
    public static long tel;

    public static String url = "jdbc:mysql://localhost:3307/aprender"; // enderço do BD
    public static String username = "root";        //nome de um usuário de seu BD
    public static String password = "";  // senha do BD

    public static void cadastrar() {

        //--> Inicio
        nom = nome_txt.getText(); // recebendo o nome
        em = email_txt.getText(); // recebendo o email
        tel = Long.valueOf(tel_txt.getText());// recebendo o telefone

        Model.Conecta_DB.carregaDriver();

        try {

            Connection con = null;

            try {
                con = (Connection) DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {

                Logger.getLogger(Inicio_GUI.class.getName()).log(Level.SEVERE, null, ex);

            }

            // Recebendo os dados a serem inseridos na tabela
            String sql = "INSERT INTO cliente(cli_nome,cli_email,cli_tel) values('" + nom + "','" + em + "','" + tel + "')";

            try { // Tratamento de Erros para inserção

                // Criando varialvel que executara a inserção
                PreparedStatement inserir = (PreparedStatement) con.prepareStatement(sql);
                inserir.execute(); // Executando a inserção

                JOptionPane.showMessageDialog(null, "\nInserção realizada com sucesso!!!\n", "", -1);
                nome_txt.setText("");
                email_txt.setText("");
                tel_txt.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "\nErro na inserção!", "ERRO!", 0);
            }

        } catch (NumberFormatException erro) {
            // Tratamento de erro caso o usuario não digite o telefone corretamente
            JOptionPane.showMessageDialog(null, "Digite os dados corretamente", "ERRO", 0);
            tel_txt.setText("");
        }

        //--> Fim
    }

    public static void consultar() {

        //--> Inicio
        try {     //Iniciando o possivel tratamento de erros
            //Declarando a variavel código
            int codigo = Integer.valueOf(cod_txt.getText());
            Model.Conecta_DB.carregaDriver(); // Carregando o driver
            try {// Tratamento de erro para a conexao
                // Declarando  a variavel de conexão con
                // e estabelendo a conexão
                Connection con = null;

                try {
                    con = (Connection) DriverManager.getConnection(url, username, password);
                } catch (SQLException ex) {
                    Logger.getLogger(Inicio_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Declarando uma string com o comando mySQL para consulta
                String sql = "SELECT cli_nome,cli_email, cli_tel FROM cliente where cli_cod = " + codigo;
                // Criando variavel que executara o comando da string sql
                Statement stm = (Statement) con.createStatement();
                try//Tratamento de erro da consulta
                { //Criando variavel que exibira os resultados
                    //Executando o comando da string sql na variavel rs
                    ResultSet rs = stm.executeQuery(sql);

                    int i = 0; // Variavel utilizada para saber se ha dados cadastrados

                    while (rs.next()) {  // Criando variaveis que receberão os valores do banco de dados
                        String nome = rs.getString("cli_nome");
                        String email = rs.getString("cli_email");
                        String telefone = rs.getString("cli_tel");

                        i++;

                        //JOptionPane.showMessageDialog(null, "Nome: " + nome + "\nEmail: " + telefone + "\nTelefone: " + telefone, "Resultado", -1);
                        nome2_txt.setText(String.valueOf(nome));
                        email2_txt.setText(String.valueOf(email));
                        tel2_txt.setText(String.valueOf(telefone));

                    }

                    if (i == 0) { // Verificando se ha dados cadastrados atraves da variavel i

                        JOptionPane.showMessageDialog(null, "Dado não cadastrado", "Resultado", -1);

                    }

                } catch (Exception ex) { // Consulta mal sucedida
                    JOptionPane.showMessageDialog(null, "\nErro ao consultar!", "ERRO", 0);
                }

            } catch (SQLException ex) {
                // Conexão com servidor mal sucedida
                JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor", "ERRO!", 0);
            }

        } catch (NumberFormatException erro) {
            // Código fora do formato
            JOptionPane.showMessageDialog(null, "Digite o código corretamente", "ERRO", 0);
            cod_txt.setText("");
        }

        //--> Fim
    }

    public static void alterar() {

        //--> Inicio
        nom = nome2_txt.getText(); // recebendo o nome
        em = email2_txt.getText(); // recebendo o email         
        tel = Long.valueOf(tel2_txt.getText());// recebendo o telefone

        Model.Conecta_DB.carregaDriver();

        try {
            Connection con = null;
            try {
                con = (Connection) DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                Logger.getLogger(Inicio_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            String sql = "UPDATE cliente SET cli_nome='" + nom + "',cli_email='" + em + "',cli_tel='" + tel + "' WHERE cli_cod=" + cod_txt.getText();

            try {
                PreparedStatement inserir = (PreparedStatement) con.prepareStatement(sql);
                inserir.execute(); // Executando a inserção

                JOptionPane.showMessageDialog(null, "\nInserção realizada com sucesso!!!\n", "", -1);
                nome2_txt.setText("");
                email2_txt.setText("");
                tel2_txt.setText("");
                cod_txt.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "\nErro na inserção!", "ERRO!", 0);
            }

        } catch (NumberFormatException erro) {
            // Tratamento de erro caso o usuario não digite o telefone corretamente
            JOptionPane.showMessageDialog(null, "Digite os dados corretamente", "ERRO", 0);
            tel2_txt.setText("");
        }

        //--> Fim
    }

    public static void excluir() {

        //--> Inicio
        Model.Conecta_DB.carregaDriver(); // Carregando o driver

        // int codigo = Integer.valueOf(cod2_txt.getText()); // Recebendo o código
        try {// Tratamento de erro para a conexao
            // Declarando  a variavel de conexão con
            // e estabelendo a conexão
            Connection con = null;

            try {
                con = (Connection) DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                Logger.getLogger(Inicio_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Criando String com comando SQL para exclusão
            String sql = "DELETE FROM cliente WHERE cli_cod = " + cod2_txt.getText();

            try // Tratamento de erros para exclusão
            {// Criando Variavel para executar a ação
                PreparedStatement excluir = (PreparedStatement) con.prepareStatement(sql);
                excluir.execute();// Executando a exclusão

                JOptionPane.showMessageDialog(null, "\nExclusão realizada com sucesso!!!\n", "", -1);
                cod2_txt.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "\nErro na exclusão!", "ERRO!", 0);
            }

        } catch (NumberFormatException erro) { // Codigo digitado com caracteres não numericos
            JOptionPane.showMessageDialog(null, "Digite o código corretamante", "ERRO", 0);
            cod2_txt.setText("");

            //--> Fim
        }

    }

}
