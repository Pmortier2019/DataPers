package nl.hu.dp;
import nl.hu.dp.P5.Product;
import nl.hu.dp.P5.ProductDAOPsql;
import nl.hu.dp.p2.domain.Reiziger;
import nl.hu.dp.p2.domain.ReizigerDAO;
import nl.hu.dp.p2.domain.ReizigerDAOPsql;
import nl.hu.dp.p3.Adres;
import nl.hu.dp.p3.AdresDAO;
import nl.hu.dp.p3.AdresDAOPsql;
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
               AdresDAO adao = new AdresDAOPsql(connection);

               OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(connection);
               ProductDAOPsql pdao = new ProductDAOPsql(connection);
               testProductDAO(pdao);
                //testOVChipkaartDAO(odao);
               // testReizigerDAO(rdao);
                System.out.println();
               //testAdresDAO(adao, rdao);
            } catch (Exception exc) {
                exc.printStackTrace();
            }




        }




    private static void testOVChipkaartDAO(OVChipkaartDAO odao) throws SQLException {

        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        List<OVChipkaart> ovChipkaarts = odao.findAll();

        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende kaarten:");
        for (OVChipkaart ovChipkaart : ovChipkaarts) {

            System.out.println(ovChipkaart);
        }

        System.out.println();

        String gbdatum = "2000-09-29";
        String gdtot = "2025-09-29";

        Reiziger lucas = new Reiziger(5, "L", "", "Caslu", java.sql.Date.valueOf(gbdatum));

        OVChipkaart ovChipkaart = new OVChipkaart(18326, java.sql.Date.valueOf(gdtot), 2, 5.50, 5);
        System.out.print("[Test] Eerst " + ovChipkaarts.size() + " Ovchipkaarts, na ovchipkaart.save() ");
        odao.save(ovChipkaart);

        ovChipkaarts = odao.findAll();
        System.out.println(ovChipkaarts.size() + " reizigers\n");
        List<OVChipkaart> OvList = odao.findByReiziger(lucas);

        System.out.println("[Test] OVChipkaart.findByReiziger() geeft de lijst:");
        System.out.println(OvList);
        System.out.println();

        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        rdao.save(lucas);
        System.out.println("[Test] delete():    " + odao.delete(ovChipkaart));
        List<OVChipkaart> ovChipkaarts1 = odao.findAll();

        System.out.println("[Test] ovCHipkaarten.size() ");

        System.out.println(ovChipkaarts1.size()+ " kaarten");

        OVChipkaart ovChipkaart1 = new OVChipkaart(90537, java.sql.Date.valueOf(gdtot), 2, 5.50, 5);
        System.out.println(lucas);
        System.out.println("[Test] update():    " + odao.update(ovChipkaart1));
        System.out.println(odao.findByReiziger(lucas));
        closeConnection();
    }


    private static void testProductDAO(ProductDAOPsql pdao) throws SQLException {
        System.out.println("\n---------- Test ProductDao -------------");
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(connection);

        // Haal alle Producten op uit de database
        List<Product> productList = pdao.findAll();
        System.out.println("[Test] ProductDao.findAll() geeft de volgende reizigers:");
        for (Product product : productList) {
            System.out.println(product);
        }
        System.out.println(productList.size()+ "producten");
        // aanmaken product en ov
        System.out.println();
        String gbdatum = "2000-1-11";
        String geldigtot = "2025-10-13";
        Product product = new Product(10, "Gratis", "Fijne reis", 0.00);
        Reiziger pedro = new Reiziger(12, "P", "", "Ordep", java.sql.Date.valueOf(gbdatum));
        OVChipkaart ovChipkaart = new OVChipkaart(1836, java.sql.Date.valueOf(geldigtot), 2, 5.50, 12);
        product.voegChipkaartToe(ovChipkaart);




        System.out.println(product);
        pdao.save(product);

        List<Product> productList1 = pdao.findAll();
        System.out.println(productList1.size()+ " producten");
        System.out.println();
        Product product1 = new Product(3, "Dal Voordeel 100%", "10 korting ", 40.00);
        System.out.println("[Test] update():    " + pdao.update(product1));

        System.out.println("[Test] findByOVChipkaart():    " + pdao.findByOVChipkaart(ovChipkaart));


        System.out.println("[Test] delete():    " + pdao.delete(product));

        List<Product> productList2 = pdao.findAll();
        System.out.println(productList2.size()+ "producten");

        closeConnection();
    }





    private static void testAdresDAO(AdresDAO adao,ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");
//
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende reizigers:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        // Eerst een nieuwe reiziger maken en opslaan
        String gbdatum = "2000-03-14";
        Reiziger sietske = new Reiziger(7, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        rdao.save(sietske);


        // Een adres maken met de opgeslagen reiziger en opslaan
        Adres a = new Adres(6, "1234TB", "25A", "HUStraat", "Groningen", 12);
        System.out.println("[Test] AdresDAO.save() ");
        System.out.println(adressen.size() + " adressen\n");

        adao.save(a);
        sietske.setAdres(a);
        rdao.update(sietske);


        adressen = adao.findAll();
        System.out.println(adressen.size() + "  adressen na de test\n");
//
        Adres aa = adao.findByReiziger(sietske);
        System.out.println(sietske);
        System.out.println("[Test] AdresDAO.findByReiziger() geeft de adres:");
        System.out.println(aa);
        System.out.println();
        System.out.println(adressen.size() + " adressen\n");

        System.out.println("[Test] delete():    " + adao.delete(a));
        List<Adres> aaa = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende reizigers:");
        System.out.println(aaa.size());
        System.out.println();
        Adres b = new Adres(2, "16e64TB", "voorstraat", "49", "Utrecht", 12);

        System.out.println("[Test] update():    " + adao.update(b));
        System.out.println(b);
//
        closeConnection();
//
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