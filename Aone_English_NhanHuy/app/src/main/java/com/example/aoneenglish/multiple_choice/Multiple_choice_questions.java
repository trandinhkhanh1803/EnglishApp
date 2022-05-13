package com.example.aoneenglish.multiple_choice;

import java.io.Serializable;

public class Multiple_choice_questions implements Serializable {
    private int idcau;
    private int idbo;
    private String noidung;
    private String dapanA;
    private String dapanB;
    private String dapanC;
    private String dapanD;
    private String dapanTrue;

    public Multiple_choice_questions(int idcau, int idbo, String noidung, String dapanA, String dapanB, String dapanC, String dapanD, String dapanTrue) {
        this.idcau = idcau;
        this.idbo = idbo;
        this.noidung = noidung;
        this.dapanA = dapanA;
        this.dapanB = dapanB;
        this.dapanC = dapanC;
        this.dapanD = dapanD;
        this.dapanTrue = dapanTrue;
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

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getDapanA() {
        return dapanA;
    }

    public void setDapanA(String dapanA) {
        this.dapanA = dapanA;
    }

    public String getDapanB() {
        return dapanB;
    }

    public void setDapanB(String dapanB) {
        this.dapanB = dapanB;
    }

    public String getDapanC() {
        return dapanC;
    }

    public void setDapanC(String dapanC) {
        this.dapanC = dapanC;
    }

    public String getDapanD() {
        return dapanD;
    }

    public void setDapanD(String dapanD) {
        this.dapanD = dapanD;
    }

    public String getDapanTrue() {
        return dapanTrue;
    }

    public void setDapanTrue(String dapanTrue) {
        this.dapanTrue = dapanTrue;
    }
}
