package nl.hu.dp.p2.domain;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ReizigerDAO {
    public boolean save(Reiziger reiziger);
    public boolean update(Reiziger reiziger);
    public boolean delete(Reiziger reiziger);
    public Reiziger findById(int id);
    public List<Reiziger> findByGbDatum(Date geboortedatum);
    public List<Reiziger> findAll();
}//