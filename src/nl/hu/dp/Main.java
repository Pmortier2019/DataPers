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
               //OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(connection, rdao);
               //ProductDAOPsql pdao = new ProductDAOPsql(connection);
               //testProductDAO(pdao);
                System.out.println();
                testAdresDAO(adao, rdao);
            } catch (Exception exc) {
                exc.printStackTrace();
            }




        }  private static void testAdresDAO(adresDAO adao,ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");
//
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende reizigers:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        // Eerst een nieuwe reiziger maken en opslaan
        String gbdatum = "2000-03-14";
        Reiziger jaap = new Reiziger(28, "J", "", "Arstrong", java.sql.Date.valueOf(gbdatum));


        // Een adres maken met de opgeslagen reiziger en opslaan
        Adres a = new Adres(20, "1234TB", "25A", "HUStraat", "Groningen", jaap);
        System.out.println("[Test] AdresDAO.save() ");
        adao.save(a, jaap);


        adressen = adao.findAll();
        System.out.println(adressen.size() + " reizigers\n");
//
        Adres aa = adao.findByReiziger(jaap);
        System.out.println("[Test] AdresDAO.findByReiziger() geeft de adres:");
        System.out.println(aa);
        System.out.println();
//
        System.out.println("[Test] delete():    " + adao.delete(a));
        System.out.println();
        Adres b = new Adres(2, "1664TB", "voorstraat", "49", "Utrecht", jaap);

        System.out.println("[Test] update():    " + adao.update(b));
//
        closeConnection();
//
    }

    private static void testProductDAO(ProductDAOPsql pdao) throws SQLException {
        System.out.println("\n---------- Test ProductDao -------------");

        // Haal alle Producten op uit de database
        List<Product> productList = pdao.findAll();
        System.out.println("[Test] ProductDao.findAll() geeft de volgende reizigers:");
        for (Product product : productList) {
            System.out.println(product);
        }

        // aanmaken product en ov
        System.out.println();
        String gbdatum = "2000-1-11";
        String geldigtot = "2025-10-13";
        Product product = new Product(7, "Gratis", "Fijne reis", 0.00);
        Reiziger pedro = new Reiziger(12, "P", "", "Ordep", java.sql.Date.valueOf(gbdatum));
        OVChipkaart ovChipkaart = new OVChipkaart(18326, java.sql.Date.valueOf(geldigtot), 2, 5.50, pedro);
        product.voegChipkaartToe(ovChipkaart);
        pdao.save(product);

        System.out.println();
        Product product1 = new Product(3, "Dal Voordeel 100%", "100% korting ", 40.00);
        System.out.println("[Test] update():    " + pdao.update(product1));

        System.out.println("[Test] findByOVChipkaart():    " + pdao.findByOVChipkaart(ovChipkaart));

        System.out.println("[Test] delete():    " + pdao.delete(product));
        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        closeConnection();
    }

    private static void closeConnection() {
    }


}