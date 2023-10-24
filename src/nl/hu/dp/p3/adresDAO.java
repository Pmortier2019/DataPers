package nl.hu.dp.p3;

import nl.hu.dp.p2.domain.Reiziger;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface adresDAO {
    public boolean save(Adres adres, Reiziger reiziger) throws SQLException;


    public boolean update(Adres adres);
    public boolean delete(Adres adres);
    Adres findByReiziger(Reiziger reiziger);
    public List<Adres> findAll() throws SQLException;

}
