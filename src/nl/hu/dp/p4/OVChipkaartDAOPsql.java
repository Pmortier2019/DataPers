package nl.hu.dp.p4;

import nl.hu.dp.P5.Product;
import nl.hu.dp.P5.ProductDAO;
import nl.hu.dp.p2.domain.Reiziger;
import nl.hu.dp.p2.domain.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{
    private Connection conn;
    private ReizigerDAO rdao;
    private ProductDAO pdao;


    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }


    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    public void setPdao(ProductDAO pdao) {
        this.pdao = pdao;
    }
    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        try{
            String query = "INSERT INTO ov_chipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) " +
                    "values(?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, ovChipkaart.getKaart_nummer());
            st.setDate(2, (java.sql.Date) ovChipkaart.getGeldig_tot());
            st.setInt(3, ovChipkaart.getKlasse());
            st.setDouble(4, ovChipkaart.getSaldo());
            st.setInt(5, ovChipkaart.getReiziger_id());
            st.executeUpdate();

            st.close();

            return true;

        } catch (Exception e){

            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        boolean isSuccess = false;
        try {  if(ovChipkaart.getProducten().size() != 0){
            for(Product p : ovChipkaart.getProducten()){
                pdao.update(p);
            }
        }

            PreparedStatement statement = conn.prepareStatement("""
                    UPDATE 
                            ov_chipkaart 
                            SET 
                            kaart_nummer = ?, 
                            geldig_tot = ?, 
                            klasse = ?, 
                            saldo = ?, 
                            reiziger_id = ? 
                            WHERE  
                            kaart_nummer = ?""");
            statement.setInt(1, ovChipkaart.getKaart_nummer());
            statement.setDate(2, ovChipkaart.getGeldig_tot());
            statement.setInt(3, ovChipkaart.getKlasse());
            statement.setDouble(4, ovChipkaart.getSaldo());
            statement.setInt(5, ovChipkaart.getReiziger_id());
            statement.setInt(6, ovChipkaart.getKaart_nummer());
            statement.executeUpdate();

            statement.close();
            isSuccess = true;
            return isSuccess;

        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        boolean isSuccess = false;

        try {
            if(ovChipkaart.getProducten().size() != 0){
                for(Product p : ovChipkaart .getProducten()){
                    pdao.update(p);
                }
            }
            PreparedStatement statement = conn.prepareStatement("""
                                                                    DELETE 
                                                                    FROM 
                                                                    ov_chipkaart 
                                                                    WHERE 
                                                                    kaart_nummer = ?
                                                                    """);
            statement.setInt(1, ovChipkaart.getKaart_nummer());
            statement.executeUpdate();
            statement.close();
            isSuccess = true;
            return isSuccess;

        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            throw e;
        }
    }


    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<OVChipkaart> ovChipkaartList = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement(
                "SELECT " +
                        "kaart_nummer, geldig_tot, klasse, saldo, reiziger_id " +
                        "FROM " +
                        "ov_chipkaart " +
                        "WHERE " +
                        "reiziger_id = ?");
        statement.setInt(1, reiziger.getReiziger_id());

        try {
            ResultSet set = statement.executeQuery();
            while (set != null && set.next()) {
                int kaart_nummer = set.getInt("kaart_nummer");
                Date geldig_tot = set.getDate("geldig_tot");
                int klasse = set.getInt("klasse");
                double saldo = set.getDouble("saldo");
                int reiziger_id = set.getInt("reiziger_id");
                OVChipkaart ov = new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id);
                ovChipkaartList.add(ov);
            }
            statement.close();
            return ovChipkaartList;
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            throw e;

        }}

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        try {
            ArrayList<OVChipkaart> ovChipkaarts = new ArrayList<OVChipkaart>();
            String query =
                    "SELECT " +
                            "kaart_nummer, geldig_tot, klasse, saldo, reiziger_id " +
                            "FROM " +
                            "ov_chipkaart;";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            while (set != null && set.next()) {
                int kaart_nummer = (set.getInt("kaart_nummer"));
                Date geldig_tot = (set.getDate("geldig_tot"));
                int klasse = (set.getInt("klasse"));
                Double saldo = (set.getDouble("saldo"));
                int reiziger_id = (set.getInt("reiziger_id"));
                OVChipkaart ovChipkaart = new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id);
                ovChipkaarts.add(ovChipkaart);
            }
            statement.close();
            return ovChipkaarts;
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            throw e;
        }
    }}

