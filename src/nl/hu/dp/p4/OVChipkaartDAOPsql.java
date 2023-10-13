package nl.hu.dp.p4;

import nl.hu.dp.p2.domain.Reiziger;
import nl.hu.dp.p2.domain.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{
    private Connection conn = null;
    private ReizigerDAO rdao;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }
    public OVChipkaartDAOPsql(Connection conn, ReizigerDAO rdao) {
        this.conn = conn;
        this.rdao = rdao;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }


    public ReizigerDAO getRdao() {
        return rdao;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        boolean isSuccess = false;
        try {
            if (this.rdao != null) {
                this.rdao.save(ovChipkaart.getReiziger());
            }
            PreparedStatement statement = conn.prepareStatement("INSERT INTO ov_chipkaart VALUES (?,?,?,?,?)");
            statement.setInt(1, ovChipkaart.getKaart_nummer());
            statement.setDate(2, ovChipkaart.getGeldig_tot());
            statement.setInt(3, ovChipkaart.getKlasse());
            statement.setDouble(4, ovChipkaart.getSaldo());
            statement.setInt(5, ovChipkaart.getReiziger().getReiziger_id());
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
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        boolean isSuccess = false;
        try {

            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE " +
                            "ov_chipkaart " +
                            "SET " +
                            "kaart_nummer = ?, geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? " +
                            "WHERE " +
                            "kaart_nummer = ?");
            statement.setInt(1, ovChipkaart.getKaart_nummer());
            statement.setDate(2, ovChipkaart.getGeldig_tot());
            statement.setInt(3, ovChipkaart.getKlasse());
            statement.setDouble(4, ovChipkaart.getSaldo());
            statement.setInt(5, ovChipkaart.getReiziger().getReiziger_id());
            statement.setInt(6, ovChipkaart.getKaart_nummer());
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
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        boolean isSuccess = false;

        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer = ?");
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
                OVChipkaart ov = new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, rdao.findById(reiziger_id));
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
                OVChipkaart ovChipkaart = new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, rdao.findById(reiziger_id));
                ovChipkaarts.add(ovChipkaart);
            }
            statement.close();
            return ovChipkaarts;
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            throw e;
        }
    }
}

