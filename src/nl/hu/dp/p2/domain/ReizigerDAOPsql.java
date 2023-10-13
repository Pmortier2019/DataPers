package nl.hu.dp.p2.domain;
import nl.hu.dp.p2.domain.Reiziger;
import nl.hu.dp.p2.domain.ReizigerDAO;
import nl.hu.dp.p3.Adres;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {

    public ResultSet res;
    public Adres adres;
    public Reiziger reiziger;
    Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "Pietermortier1");

    public ReizigerDAOPsql(Connection conn) throws SQLException {
        try {
            Statement myStmt = conn.createStatement();

            ResultSet myRs = myStmt.executeQuery("SELECT * FROM reiziger");
            res = myRs;


        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            int aantalReizigers = findAll().size();

            String query = "INSERT INTO reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, aantalReizigers+2);
            preparedStatement.setString(2, reiziger.getVoorletters());
            preparedStatement.setString(3, reiziger.getTussenvoegsel());
            preparedStatement.setString(4, reiziger.getAchternaam());
            preparedStatement.setDate(5, java.sql.Date.valueOf(reiziger.getGeboortedatum().toString()));

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            String query = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, reiziger.getVoorletters());
            preparedStatement.setString(2, reiziger.getTussenvoegsel());
            preparedStatement.setString(3, reiziger.getAchternaam());
            preparedStatement.setDate(4, java.sql.Date.valueOf(reiziger.getGeboortedatum().toString()));
            preparedStatement.setInt(5, reiziger.getReiziger_id());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, reiziger.getReiziger_id());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String voorletters = resultSet.getString("voorletters");
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                String achternaam = resultSet.getString("achternaam");
                java.sql.Date geboortedatum = resultSet.getDate("geboortedatum");

                return new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbDatum(java.util.Date geboortedatum) {
        List<Reiziger> reizigers = new ArrayList<>();

        try {
            String query = "SELECT * FROM reiziger WHERE geboortedatum = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDate(1, java.sql.Date.valueOf(String.valueOf(geboortedatum)));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("reiziger_id");
                String voorletters = resultSet.getString("voorletters");
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                String achternaam = resultSet.getString("achternaam");
                java.sql.Date gbDatum = resultSet.getDate("geboortedatum");

                reizigers.add(new Reiziger(id, voorletters, tussenvoegsel, achternaam, gbDatum));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reizigers;
    }


    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigers = new ArrayList<>();

        try {
            String query = "SELECT * FROM reiziger";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("reiziger_id");
                String voorletters = resultSet.getString("voorletters");
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                String achternaam = resultSet.getString("achternaam");
                java.sql.Date geboortedatum = java.sql.Date.valueOf(resultSet.getDate("geboortedatum").toLocalDate());

                reizigers.add(new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reizigers;
    }

   @Override
   public String toString() {
    String returnString = "";

    try {
        while (res.next()) {
            returnString += res.getString("voorletters") + "." + res.getString("achternaam") + " (" + res.getString("geboortedatum") + ")" + " ";
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return returnString;
// }
//   @Override
//   public String toString() {
//       if (adres != null) {
//           return String.format("Reiziger {#%d %s %s, geb. %s, Adres {#%d %s-%s}}",
//                   reiziger.getReiziger_id(), reiziger.getVoorletters(), reiziger.getAchternaam(), reiziger.getGeboortedatum().toString(), adres.getAdres_id(), adres.getPostcode(), adres.getHuisnummer());
//       } else {
//           return String.format("Reiziger {#%d %s %s, geb. %s}", reiziger.getReiziger_id(), reiziger.getVoorletters(), reiziger.getAchternaam(), reiziger.getGeboortedatum().toString());
//       }
//   }
}}
