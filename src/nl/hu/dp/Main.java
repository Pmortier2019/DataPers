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
               //adresDAO adao = new AdresDAOPsql(connection);
               //OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(connection, rdao);
               //ProductDAOPsql pdao = new ProductDAOPsql(connection);
               //testProductDAO(pdao);
                testReizigerDAO(rdao);
                System.out.println();

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
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }


}