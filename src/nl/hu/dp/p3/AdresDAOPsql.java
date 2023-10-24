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


    public AdresDAOPsql(Connection conn,ReizigerDAO rdao) throws SQLException {
        this.rdao = rdao;

        try {
            Statement myStmt = conn.createStatement();


            ResultSet myRs = myStmt.executeQuery("""
                            SELECT 
                                adres_id, 
                                postcode, 
                                huisnummer, 
                                straat, 
                                woonplaats, 
                                reiziger_id 
                            FROM 
                                adres;
                            """);


            res = myRs;


       }catch (SQLException e) {
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

    @Override
    public boolean save(Adres adres, Reiziger reiziger) throws SQLException {
        boolean isSuccess = false;
        try {
            // Eerst reiziger opslaan
            if (this.rdao != null) {
                this.rdao.save(reiziger);
            }

            // Adres opslaan met de ID van de opgeslagen reiziger
            PreparedStatement statement = conn.prepareStatement("INSERT INTO adres VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, adres.getAdres_id());
            statement.setString(2, adres.getPostcode());
            statement.setString(3, adres.getHuisnummer());
            statement.setString(4, adres.getStraat());
            statement.setString(5, adres.getWoonplaats());
            statement.setInt(6, reiziger.getReiziger_id());

            int rowsAffected = statement.executeUpdate();

            statement.close();

            isSuccess = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return isSuccess;
    }


    @Override
    public boolean update(Adres adres) {
        try {
            String postcode = adres.getPostcode().length() > 7 ? adres.getPostcode().substring(0, 7) : adres.getPostcode();
            String huisnummer = adres.getHuisnummer().length() > 10 ? adres.getHuisnummer().substring(0, 10) : adres.getHuisnummer();
            String woonplaats = adres.getWoonplaats().length() > 255 ? adres.getWoonplaats().substring(0, 255) : adres.getWoonplaats();

            String query = "UPDATE adres SET straat = ?, postcode = ?, huisnummer = ?, woonplaats = ? WHERE adres_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, adres.getStraat());
            preparedStatement.setString(2, postcode);
            preparedStatement.setString(3, huisnummer);
            preparedStatement.setString(4, woonplaats);
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
            String query = """
                            SELECT 
                                adres_id, 
                                postcode, 
                                huisnummer, 
                                straat, 
                                woonplaats, 
                                reiziger_id 
                            FROM 
                                adres
                            WHERE 
                                reiziger_id = ?
                            """;

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
            String query = """
                            SELECT 
                                adres_id, 
                                postcode, 
                                huisnummer, 
                                straat, 
                                woonplaats, 
                                reiziger_id 
                            FROM 
                                adres;
                            """;
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
    }}
