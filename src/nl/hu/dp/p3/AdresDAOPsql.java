package nl.hu.dp.p3;

import nl.hu.dp.p2.domain.Reiziger;
import nl.hu.dp.p2.domain.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements adresDAO{

    public ResultSet res;
    private ReizigerDAO rdao;

    static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "Pietermortier1");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public AdresDAOPsql(Connection conn) throws SQLException {
        try {
            Statement myStmt = conn.createStatement();

            ResultSet myRs = myStmt.executeQuery("SELECT * FROM adres");
            res = myRs;


        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

        @Override
        public boolean save(Adres adres) {
            try {
                String query = "INSERT INTO adres(straat, postcode, huisnummer, woonplaats, reiziger_id) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, adres.getStraat());
                preparedStatement.setString(2, adres.getPostcode());
                preparedStatement.setString(3, adres.getHuisnummer());
                preparedStatement.setString(4, adres.getWoonplaats());
                preparedStatement.setInt(5, adres.getReizigerId());

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            String query = "UPDATE adres SET straat = ?, postcode = ?, huisnummer = ?, woonplaats = ? WHERE adres_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, adres.getStraat());
            preparedStatement.setString(2, adres.getPostcode());
            preparedStatement.setString(3, adres.getHuisnummer());
            preparedStatement.setString(4, adres.getWoonplaats());
            preparedStatement.setInt(5, adres.getAdres_id());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            String query = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, adres.getAdres_id());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String query = "SELECT * FROM adres WHERE reiziger_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, reiziger.getReiziger_id());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int adresId = resultSet.getInt("adres_id");
                String straat = resultSet.getString("straat");
                String postcode = resultSet.getString("postcode");
                String huisnummer = resultSet.getString("huisnummer");
                String woonplaats = resultSet.getString("woonplaats");

                return new Adres(adresId, straat, postcode, huisnummer, woonplaats, reiziger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        try {
            ArrayList<Adres> adressen = new ArrayList<Adres>();
            String query = "SELECT * FROM adres;";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            while (set != null && set.next()) {
                int adres_id = (set.getInt("adres_id"));
                String postcode = (set.getString("postcode"));
                String huisnummer = (set.getString("huisnummer"));
                String straat = (set.getString("straat"));
                String woonplaats = (set.getString("woonplaats"));
                int reiziger_id = (set.getInt("reiziger_id"));
                Adres adres = new Adres(adres_id,postcode,huisnummer,straat,woonplaats,rdao.findById(reiziger_id));
                adressen.add(adres);
            }
            statement.close();
            return adressen;
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            throw e;
        }
    }
}
