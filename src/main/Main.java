package main;

import Controller.Press_DAO;
import Controller.Ticket_DAO;
import View.Inicio_GUI;

public class Main {

   
    public static void main(String[] args) throws Exception {
       new Inicio_GUI().setVisible(true);
        //Ticket_DAO.impressao();
        //Model.Conecta_DB.carregaDriver();
        //Press_DAO.imprime();
    }
}
