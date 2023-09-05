package nl.hu.dp;
import java.sql.*;
public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/ovchip";
        String username = "postgres";
        String password = "Pietermortier1";
    try {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement myStat =connection.createStatement();
        System.out.println("Alle reizigers: ");
        ResultSet myRs = myStat.executeQuery("select * from reiziger");
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
    catch (Exception exc){
        exc.printStackTrace();
    }
    }
}
