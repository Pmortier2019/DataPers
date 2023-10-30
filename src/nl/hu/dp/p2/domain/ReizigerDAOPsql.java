package nl.hu.dp.p2.domain;
import nl.hu.dp.p2.domain.Reiziger;
import nl.hu.dp.p2.domain.ReizigerDAO;
import nl.hu.dp.p3.Adres;
import nl.hu.dp.p3.AdresDAO;

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

            ResultSet myRs = myStmt.executeQuery("""
                                                        SELECT 
                                                            reiziger_id,
                                                            voorletters,
                                                            tussenvoegsel,
                                                            achternaam, 
                                                            geboortedatum 
                                                        FROM 
                                                                reiziger
                                                                 """);
            res = myRs;

        }
        catch (SQLException e) {
            System.out.println("database niet goed");
            e.printStackTrace();
        }
        catch (Exception exception) {
            if (res != null){
                System.out.println("Doet het nog niet");
                res.close();
            }
            exception.printStackTrace();
        }
    }
    private int findHighestReizigerId() throws SQLException {
        String query = "SELECT MAX(reiziger_id) FROM reiziger";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        // Geen bestaande reizigers in de database
        return 0;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            int hoogsteReizigerId = findHighestReizigerId();
            int nieuweReizigerId = hoogsteReizigerId + 1;
            String query = "INSERT INTO reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, nieuweReizigerId);
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
            String query = """
                            UPDATE reiziger 
                            SET voorletters = ?, 
                            tussenvoegsel = ?, 
                            achternaam = ?, 
                            geboortedatum = ? 
                            WHERE reiziger_id = ?
                            """;
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
            String query = """
                          SELECT 
                              reiziger_id,
                              voorletters,
                              tussenvoegsel,
                              achternaam, 
                              geboortedatum 
                          FROM 
                                  reiziger
                          WHERE 
                              reiziger_id = ?
                                   """;
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
            String query = """
                                                        SELECT 
                                                            reiziger_id,
                                                            voorletters,
                                                            tussenvoegsel,
                                                            achternaam, 
                                                            geboortedatum 
                                                        FROM 
                                                                reiziger
                                                        WHERE 
                                                            geboortedatum = ?
                                                                 """;
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
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();

            resultSet = statement.executeQuery( """
                                                        SELECT 
                                                            reiziger_id,
                                                            voorletters,
                                                            tussenvoegsel,
                                                            achternaam, 
                                                            geboortedatum 
                                                        FROM 
                                                                reiziger
                                                                 """);


            while (resultSet.next()) {
                int id = resultSet.getInt("reiziger_id");
                String voorletters = resultSet.getString(2);
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                if (resultSet.wasNull()) {
                    tussenvoegsel = "";
                }
                String achternaam = resultSet.getString(4);
                String geboortedatumString = resultSet.getString(5);

                // Gb naar een dateObject
                java.sql.Date geboortedatum = geboortedatumString.isEmpty() ? null : java.sql.Date.valueOf(geboortedatumString);

                reizigers.add(new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum));
            }
        } catch (SQLException e) {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            e.printStackTrace();
            throw new RuntimeException("Fout bij het ophalen van reizigers.", e); // Gooi de exceptie opnieuw
        }

        return reizigers;
    }

}
