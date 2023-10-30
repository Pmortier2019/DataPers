package nl.hu.dp.p3;

import nl.hu.dp.p2.domain.Reiziger;
import nl.hu.dp.p2.domain.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO{

    public ResultSet res;
    private ReizigerDAO rdao;

    Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "Pietermortier1");
    public AdresDAOPsql(Connection conn) throws SQLException {
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
    public boolean save(Adres adres) throws SQLException {
        try{
            String query = "INSERT INTO Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id ) " +
                    "values(?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, adres.getAdres_id());
            st.setString(2, adres.getPostcode());
            st.setString(3, adres.getHuisnummer());
            st.setString(4, adres.getStraat());
            st.setString(5, adres.getWoonplaats());
            st.setInt(6, adres.getReiziger_id());
            st.executeQuery();

            st.close();
            return true;
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }


    @Override
    public boolean update(Adres adres) throws SQLException{
        try{
            String query = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?" +
                    "WHERE adres_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, adres.getPostcode());
            statement.setString(2, adres.getHuisnummer());
            statement.setString(3, adres.getStraat());
            statement.setString(4, adres.getWoonplaats());
            statement.setInt(5, adres.getAdres_id());
            statement.executeUpdate();
            statement.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        } return true;
    }


    @Override
    public boolean delete(Adres adres) throws SQLException{
        try{
            String query = "DELETE from adres WHERE adres_id = ? ";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, adres.getAdres_id());
            statement.executeUpdate();
            statement.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
        }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
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
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, reiziger.getReiziger_id());
            ResultSet rs = statement.executeQuery();

            Adres reizigerAdres = null;
            while(rs.next()){
                reizigerAdres = new Adres(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6));

            }
            rs.close();
            statement.close();
            return reizigerAdres;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        List<Adres> adressen = new ArrayList<>();
        try{
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
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                adressen.add(new Adres(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6)));
            }
            rs.close();
            statement.close();
            return adressen;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    }

