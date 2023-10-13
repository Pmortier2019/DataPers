package nl.hu.dp;
import nl.hu.dp.P5.Product;
import nl.hu.dp.P5.ProductDAOPsql;
import nl.hu.dp.p2.domain.Reiziger;
import nl.hu.dp.p2.domain.ReizigerDAO;
import nl.hu.dp.p2.domain.ReizigerDAOPsql;
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
                adresDAO adao = new AdresDAOPsql(connection);
                OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(connection, rdao);
                ProductDAOPsql pdao = new ProductDAOPsql(connection);
                testProductDAO(pdao);

                System.out.println();

            } catch (Exception exc) {
                exc.printStackTrace();
            }




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
//
//
//
//
//    private static void testOVChipkaartDAO(OVChipkaartDAO odao) throws SQLException {
//
//
//
//
//        System.out.println("\n---------- Test OVChipkaartDAO -------------");
//




//
//
//
//
// Haal alle OVChipkaarten op uit de database
//
//
//
//
//        List<OVChipkaart> ovChipkaarts = odao.findAll();
//
//
//
//
//        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende reizigers:");
//
//
//
//
//        for (OVChipkaart ovChipkaart : ovChipkaarts) {
//
//
//
//
//            System.out.println(ovChipkaart);
//
//
//
//
//        }
//
//
//
//
//        System.out.println();
//
//
//
//
//        String gbdatum = "2000-09-29";
//
//
//
//
//        String gdtot = "2025-09-29";
//
//
//
//
//        Reiziger lucas = new Reiziger(9, "L", "", "Caslu", java.sql.Date.valueOf(gbdatum));
//
//
//
//
//        OVChipkaart ovChipkaart = new OVChipkaart(39432, java.sql.Date.valueOf(gdtot), 2, 5.50, lucas);
//
//
//
//
//        System.out.print("[Test] Eerst " + ovChipkaarts.size() + " Ovchipkaarts, na AdresDAO.save() ");
//
//
//
//
//        odao.save(ovChipkaart);
//
//
//
//
//        ovChipkaarts = odao.findAll();
//
//
//
//
//        System.out.println(ovChipkaarts.size() + " reizigers\n");
//




//
//
//
//
//        List<OVChipkaart> OvList = odao.findByReiziger(lucas);
//
//
//
//
//        System.out.println("[Test] OVChipkaart.findByReiziger() geeft de lijst:");
//
//
//
//
//        System.out.println(OvList);
//
//
//
//
//        System.out.println();
//




//
//
//
//
//        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
//
//
//
//
//        System.out.println("[Test] delete():    " + odao.delete(ovChipkaart));
//
//
//
//
//        rdao.delete(rdao.findById(ovChipkaart.getReiziger().getReiziger_id()));
//




//




//
//
//
//
//        System.out.println();
//
//
//
//
//        OVChipkaart ovChipkaart1 = new OVChipkaart(89432, java.sql.Date.valueOf(gdtot), 2, 5.50, lucas);
//
//
//
//
//        System.out.println("[Test] update():    " + odao.update(ovChipkaart1));
//




//
//
//
//
// Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
//
//
//
//
//        closeConnection();
//    }


//    private static void testAdresDAO(adresDAO adao) throws SQLException {
//        System.out.println("\n---------- Test AdresDAO -------------");
//
//        List<Adres> adressen = adao.findAll();
//        System.out.println("[Test] AdresDAO.findAll() geeft de volgende reizigers:");
//        for (Adres a : adressen) {
//            System.out.println(a);
//        }
//        System.out.println();
//        String gbdatum = "2000-03-14";
//        Reiziger jaap = new Reiziger(6, "J", "", "Armstrong", java.sql.Date.valueOf(gbdatum));
//        Adres a = new Adres(6,"1234TB", "25A","HUStraat", "Groningen", jaap);
//        System.out.print("[Test] Eerst " + adressen.size() + " reizigers, na AdresDAO.save() ");
//        adao.save(a);
//        adressen = adao.findAll();
//        System.out.println(adressen.size() + " reizigers\n");
//
//        Adres aa = adao.findByReiziger(jaap);
//        System.out.println("[Test] AdresDAO.findByReiziger() geeft de adres:");
//        System.out.println(aa);
//        System.out.println();
//
//        System.out.println("[Test] delete():    " + adao.delete(a));
//        System.out.println();
//        Reiziger jens = new Reiziger(7, "J", "", "Armweak", java.sql.Date.valueOf(gbdatum));
//        Adres b = new Adres(7,"4321BT", "30B","HUStraat123", "Alkmaar", jens);
//        System.out.println("[Test] update():    " + adao.update(b));
//
//        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
//        closeConnection();
//
//    }

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

 //  private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
 //      System.out.println("\n---------- Test ReizigerDAO -------------");

///        // Haal alle reizigers op uit de database
 //      List<Reiziger> reizigers = rdao.findAll();
 //      System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
 //      for (Reiziger r : reizigers) {
 //          System.out.println(r);
 //      }
 //      System.out.println();

 //      // Maak een nieuwe reiziger aan en persisteer deze in de database
 //      String gbdatum = "1981-03-14";
 //      Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
 //      System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
 //      rdao.save(sietske);
 //      reizigers = rdao.findAll();
 //      System.out.println(reizigers.size() + " reizigers\n");

 //      // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
///         Zoek een reiziger en update deze
 //      String nieuweGdDatum = "1982-03-14";
 //      Reiziger sietske2 = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(nieuweGdDatum));
 //      List<Reiziger> reizigerToUpdate = rdao.findByGbDatum(sietske.getGeboortedatum());
 //      System.out.println("[Test] Eerst was de geboortedatum van de reiziger: " + reizigers.get(reizigers.size() -1 ).getGeboortedatum()
 //              + " ReizigerDAO.update() ");
 //      rdao.update(sietske2);
 //      reizigers = rdao.findAll();
 //      System.out.println(reizigers.get(reizigers.size()-1).getGeboortedatum());

 //      // Zoek een reiziger en delete deze
 //      List<Reiziger> reizigersToDelete = rdao.findByGbDatum(sietske.getGeboortedatum());
 //      Reiziger reizigerToDelete = reizigersToDelete.get(0);
 //      System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
 //      rdao.delete(reizigerToDelete);
 //      reizigers = rdao.findAll();
 //      System.out.println(reizigers.size() + " reizigers\n");
 //  }

}