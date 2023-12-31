package nl.hu.dp.p4;

import nl.hu.dp.p2.domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovChipkaart) throws SQLException;
    boolean update(OVChipkaart ovChipkaart)throws SQLException;
    boolean delete(OVChipkaart ovChipkaart)throws SQLException;
    List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    List<OVChipkaart> findAll() throws SQLException;

}
