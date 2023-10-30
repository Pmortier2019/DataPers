package nl.hu.dp.p2.domain;

import nl.hu.dp.p3.Adres;
import nl.hu.dp.p4.OVChipkaart;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
//
public class Reiziger {
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres reizigerAdres;

    private ArrayList<OVChipkaart> ovchipkaarten  = new ArrayList<>();


    public Reiziger(int rid, String vrl, String tsv, String atn, Date gbd) {
        reiziger_id = rid;
        voorletters = vrl;
        tussenvoegsel = tsv;
        achternaam = atn;
        geboortedatum = gbd;
    } public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, List<OVChipkaart> ovchipkaarten) {
        this.reiziger_id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.ovchipkaarten = (ArrayList<OVChipkaart>) ovchipkaarten;
    }

    public List<OVChipkaart> getOvChipkaarts() {
        return ovchipkaarten;
    }

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, Adres adres){
        this.reiziger_id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
//        this.adres = adres;
    }
    public Reiziger(){

    }

    @Override
    public String toString() {
        return "Reiziger{" +
                "reiziger_id=" + reiziger_id +
                ", " + reizigerAdres + ovchipkaarten+

                "}\n";}

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public void setVoorletters(String voorletters) {
        if (voorletters != null && !voorletters.trim().isEmpty()) {
            this.voorletters = voorletters;
        } else {
            this.voorletters = "";
        }
    }

    public String getTussenvoegsel() {

        if (tussenvoegsel !=null){
            return this.tussenvoegsel = tussenvoegsel;}
        else {return tussenvoegsel = " ";}
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        if (tussenvoegsel != null && !tussenvoegsel.trim().isEmpty()) {
            this.tussenvoegsel = tussenvoegsel;
        } else {
            this.tussenvoegsel = "";
        }
    }
    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }
    public void setAdres(Adres a) {
        reizigerAdres = a;
    }

    public Adres getAdres() {
        return reizigerAdres;
    }
    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public void addOvchipkaart(OVChipkaart ovchipkaart) {
        if (!ovchipkaarten.contains(ovchipkaart)) {
            ovchipkaarten.add(ovchipkaart);
        }
    }
    public String getVoorletters() {
        return voorletters;
    }

}