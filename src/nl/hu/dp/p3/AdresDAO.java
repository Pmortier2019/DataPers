package nl.hu.dp.p3;

import nl.hu.dp.p2.domain.Reiziger;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface AdresDAO {
    public boolean save(Adres adres) throws SQLException;


    public boolean update(Adres adres)throws SQLException;
    public boolean delete(Adres adres)throws SQLException;
    Adres findByReiziger(Reiziger reiziger)throws SQLException;
    public List<Adres> findAll() throws SQLException;

}
