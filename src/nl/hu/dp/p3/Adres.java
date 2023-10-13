package nl.hu.dp.p3;

import nl.hu.dp.p2.domain.Reiziger;

public class Adres {
        private int adres_id;
        private String straat;
        private String postcode;
        private String huisnummer;
        private String woonplaats;
        private int reizigerId;
        private Reiziger reiziger;

        public Adres(int adres_id, String straat, String postcode, String huisnummer, String woonplaats, Reiziger reiziger){
            this.adres_id = adres_id;
            this.huisnummer = huisnummer;
            this.postcode = postcode;
            this.reizigerId = reizigerId;
            this.straat = straat;
            this.woonplaats = woonplaats;
        }
    @Override
    public String toString() {
        return "Adres {" +
                "#" + adres_id +
                " " + postcode +
                "-" + huisnummer + "}, "+ reiziger.toString();
    }
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getAdres_id() {
        return adres_id;
    }

    public void setAdres_id(int adres_id) {
        this.adres_id = adres_id;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public int getReizigerId() {
        return reizigerId;
    }

    public void setReizigerId(int reizigerId) {
        this.reizigerId = reizigerId;
    }
}
