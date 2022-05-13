package com.example.aoneenglish.Listening;

import java.io.Serializable;

public class Listening_Practice implements Serializable {
    private int idbai;
    private int idbo;
    private String dapanA;
    private String dapanB;
    private String dapanC;
    private String dapanD;
    private String dapanTrue;
    private byte[] hinhanh;
    private String audio;

    public Listening_Practice(int idbai, int idbo, String dapanA, String dapanB, String dapanC, String dapanD, String dapanTrue, byte[] hinhanh, String audio) {
        this.idbai = idbai;
        this.idbo = idbo;
        this.dapanA = dapanA;
        this.dapanB = dapanB;
        this.dapanC = dapanC;
        this.dapanD = dapanD;
        this.dapanTrue = dapanTrue;
        this.hinhanh = hinhanh;
        this.audio = audio;
    }

    public int getIdbai() {
        return idbai;
    }

    public void setIdbai(int idbai) {
        this.idbai = idbai;
    }

    public int getIdbo() {
        return idbo;
    }

    public void setIdbo(int idbo) {
        this.idbo = idbo;
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

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
