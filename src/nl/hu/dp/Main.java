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


    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/ovchip";
        String username = "postgres";
        String password = "Pietermortier1";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement myStat =connection.createStatement();
            System.out.println("Alle reizigers: ");
            ResultSet myRs = myStat.executeQuery(""" 
                                                    SELECT 
                                                    reiziger_id,
                                                    voorletters,
                                                   achternaam,
                                                    geboortedatum 
                                                    FROM reiziger
                                                    """);


            while (myRs.next()) {
                int reizigerId = myRs.getInt("reiziger_id");
                String voorletters = myRs.getString("voorletters");
                String achternaam = myRs.getString("achternaam");
                Date geboortedatum = myRs.getDate("geboortedatum");

                String volledigeNaam = "#"+ reizigerId+" "+ voorletters + ". " + achternaam + "   " + geboortedatum;
                System.out.println(volledigeNaam);
            }
            connection.close();
        }
        catch (SQLException exc){
            System.out.println("Problemen met database");
            exc.printStackTrace();
            connection.close();

        }
        catch (Exception exc){
            System.out.println("Toegang tot database gaat goed maar ander foutje");
            exc.printStackTrace();
            connection.close();

        }
    }
}