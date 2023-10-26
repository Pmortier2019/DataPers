package nl.hu.dp;
import nl.hu.dp.P5.Product;
import nl.hu.dp.P5.ProductDAOPsql;
import nl.hu.dp.p2.domain.Reiziger;
import nl.hu.dp.p2.domain.ReizigerDAO;
import nl.hu.dp.p2.domain.ReizigerDAOPsql;
import nl.hu.dp.p3.Adres;
import nl.hu.dp.p3.AdresDAOPsql;
import nl.hu.dp.p3.adresDAO;
import nl.hu.dp.p4.OVChipkaart;
import nl.hu.dp.p4.OVChipkaartDAO;
import nl.hu.dp.p4.OVChipkaartDAOPsql;

import java.sql.*;
import java.util.List;

public class Main {
    static Connection connection;

    public static void main(String[] args) throws SQLException{
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "Pietermortier1");

            try {

                ReizigerDAO rdao = new ReizigerDAOPsql(connection);
               adresDAO adao = new AdresDAOPsql(connection, rdao);
               OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(connection, rdao);
               //ProductDAOPsql pdao = new ProductDAOPsql(connection);
               //testProductDAO(pdao);
               // testOVChipkaartDAO(odao);
                testReizigerDAO(rdao);
                System.out.println();
               // testAdresDAO(adao, rdao);
            } catch (Exception exc) {
                exc.printStackTrace();
            }




        }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(6, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        sietske.setVoorletters("T");
        sietske.setTussenvoegsel("aa");
        sietske.setAchternaam("Pieter");
        System.out.println("Uitgevoerde SQL-query: " + sietske);
        System.out.println("Voorletters: " + sietske.getVoorletters());
        System.out.println("Tussenvoegsel: " + sietske.getTussenvoegsel());
        System.out.println("Achternaam: " + sietske.getAchternaam());
        System.out.println("Geboortedatum: " + sietske.getGeboortedatum());
        System.out.println("Reiziger ID: " + sietske.getReiziger_id());
        System.out.println("[Test] Na de Update met nieuwe naam " + sietske);
        if (rdao.update(sietske)) {
            System.out.println("[Test] ReizigerDAO.update() gelukt: " + sietske);
        } else {
            System.out.println("[Test] ReizigerDAO.update() gaat nog niet helemaal goed");
        }
        System.out.println();
        System.out.println("[Test] ReizigerDAO.findById() geeft de volgende reiziger:");
        Reiziger gevondenReiziger = rdao.findById(sietske.getReiziger_id());
        System.out.println(gevondenReiziger);
        System.out.println("[Test] ReizigerDAO.delete() probeert de bovenstaande reiziger te verwijderen...");
        if (rdao.delete(gevondenReiziger)) {
            System.out.println("[Test] ReizigerDAO.delete() geslaagd, reiziger verwijderd");
        } else {
            System.out.println("[Test] ReizigerDAO.delete() mislukt");
        }
        reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers na delete:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();
    }



    private static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }



    private static void testConnection() throws SQLException {
        String query = "SELECT * FROM reiziger;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        System.out.println("Alle reizigers:");
        while (set != null && set.next()) {
            System.out.println("    #" + set.getString("reiziger_id") + ": " + set.getString("voorletters") + ". " + set.getString("achternaam") + " (" + set.getString("geboortedatum") + ")");
        }
        closeConnection();
    }



}