package nl.hu.dp.p2.domain;

import nl.hu.dp.p4.OVChipkaart;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private List<OVChipkaart> ovChipkaarts = new ArrayList<>();


    public Reiziger(int rid, String vrl, String tsv, String atn, Date gbd) {
        reiziger_id = rid;
        voorletters = vrl;
        tussenvoegsel = tsv;
        achternaam = atn;
        geboortedatum = gbd;
    }
    public Reiziger(int rid, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, List<OVChipkaart> ovChipkaarts) {
        this.reiziger_id = rid;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.ovChipkaarts = ovChipkaarts;
    }

    @Override
    public String toString() {
        String s = "";
        s = s + "naam:" + this.getVoorletters() + " " + this.getTussenvoegsel() + " "
                + this.getAchternaam() + "\t geboortedatum: " + this.getGeboortedatum();

        return s;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
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

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getVoorletters() {
        return voorletters;
    }

}