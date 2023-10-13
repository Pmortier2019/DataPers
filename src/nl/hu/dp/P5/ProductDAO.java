package nl.hu.dp.P5;

import nl.hu.dp.p4.OVChipkaart;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    boolean save(Product product) throws SQLException;
    boolean update(Product product)throws SQLException;
    boolean delete(Product product)throws SQLException;
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException;
    List<Product> findAll() throws SQLException;

}
