package nl.hu.dp.P5;

import nl.hu.dp.p4.OVChipkaart;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO{
    private Connection conn = null;

    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        boolean isSuccess = false;
        try {

            PreparedStatement statement = conn.prepareStatement("INSERT INTO product VALUES (?,?,?,?)");
            statement.setInt(1, product.getProduct_nummer());
            statement.setString(2, product.getNaam());
            statement.setString(3, product.getBeschrijving());
            statement.setDouble(4, product.getPrijs());
            statement.executeUpdate();

            statement.close();

            for (OVChipkaart ovChipkaart : product.getOvChipkaarts()){
                statement = conn.prepareStatement("INSERT INTO ov_chipkaart_product VALUES (?,?,?,?)");
                statement.setInt(1, ovChipkaart.getKaart_nummer());
                statement.setInt(2, product.getProduct_nummer());
                statement.setString(3, "actief");
                statement.setDate(4, Date.valueOf(LocalDate.now()));
                statement.execute();
            }
            statement.close();

            isSuccess = true;
            return isSuccess;

        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            throw e;
        }
    }


    @Override
    public boolean update(Product product) throws SQLException {
        boolean isSuccess = false;
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE " +
                            "product " +
                            "SET " +
                            "product_nummer = ?, naam = ?, beschrijving = ?, prijs = ? " +
                            "WHERE product_nummer = ?");
            statement.setInt(1, product.getProduct_nummer());
            statement.setString(2, product.getNaam());
            statement.setString(3, product.getBeschrijving());
            statement.setDouble(4, product.getPrijs());
            statement.setInt(5, product.getProduct_nummer());
            statement.executeUpdate();

            statement.close();

            for (OVChipkaart ovChipkaart : product.getOvChipkaarts()){
                statement = conn.prepareStatement("UPDATE " +
                        "ov_chipkaart_product " +
                        "SET kaart_nummer = ?, product_nummer = ? " +
                        "WHERE kaart_nummer = ?");
                statement.setInt(1, ovChipkaart.getKaart_nummer());
                statement.setInt(2, product.getProduct_nummer());
                statement.execute();
            }
            statement.close();
            isSuccess = true;
            return isSuccess;

        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        boolean isSuccess = false;
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?");
            for (OVChipkaart ovChipkaart : product.getOvChipkaarts()){
                statement.setInt(1, ovChipkaart.getKaart_nummer());
                statement.execute();
            }
            statement.close();

            PreparedStatement statements = conn.prepareStatement("DELETE FROM product WHERE product_nummer = ?");
            statements.setInt(1, product.getProduct_nummer());
            statements.executeUpdate();
            statements.close();



            isSuccess = true;
            return isSuccess;

        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        try {
            List<Product> productList = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT p.product_nummer, p.naam, p.beschrijving, p.prijs " +
                            "FROM ov_chipkaart_product ocp " +
                            "JOIN product p ON ocp.product_nummer = p.product_nummer " +
                            "WHERE ocp.kaart_nummer = ?");
            statement.setInt(1, ovChipkaart.getKaart_nummer());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int productNummer = resultSet.getInt("product_nummer");
                String naam = resultSet.getString("naam");
                String beschrijving = resultSet.getString("beschrijving");
                double prijs = resultSet.getDouble("prijs");

                Product product = new Product(productNummer, naam, beschrijving, prijs);
                productList.add(product);
            }
            resultSet.close();
            statement.close();
            return productList;
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Product> findAll() throws SQLException {
        try {
            ArrayList<Product> productList = new ArrayList<Product>();
            String query = "SELECT " +
                    "product_nummer," +
                    "naam," +
                    "beschrijving," +
                    "prijs " +
                    "FROM " +
                    "product;";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            while (set != null && set.next()) {
                int product_nummer = (set.getInt("product_nummer"));
                String naam = (set.getString("naam"));
                String beschrijving = (set.getString("beschrijving"));
                Double prijs = (set.getDouble("prijs"));
                Product product = new Product(product_nummer, naam, beschrijving, prijs);
                productList.add(product);
            }
            statement.close();
            return productList;
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            throw e;
        }
    }
}
