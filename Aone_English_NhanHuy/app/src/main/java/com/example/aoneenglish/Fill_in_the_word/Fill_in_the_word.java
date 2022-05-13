package com.example.aoneenglish.Fill_in_the_word;

import java.io.Serializable;

public class Fill_in_the_word implements Serializable {
    private int idcau;
    private int idbo;
    private String Dapan;

    public Fill_in_the_word(int idcau, int idbo, String dapan) {
        this.idcau = idcau;
        this.idbo = idbo;
        Dapan = dapan;
    }

    public int getIdcau() {
        return idcau;
    }

    public void setIdcau(int idcau) {
        this.idcau = idcau;
    }

    public int getIdbo() {
        return idbo;
    }

    public void setIdbo(int idbo) {
        this.idbo = idbo;
    }

    public String getDapan() {
        return Dapan;
    }

    public void setDapan(String dapan) {
        Dapan = dapan;
    }
}
