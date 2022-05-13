package com.example.aoneenglish.Learning_vocabulary;

import java.io.Serializable;

public class Vocabulary implements Serializable {
    private int idtu;
    private int idbo;
    private String dapan;
    private String dichnghia;
    private String loaitu;
    private String audio;
    private byte[] anh;

    public Vocabulary(int idtu, int idbo, String dapan, String dichnghia, String loaitu, String audio, byte[] anh) {
        this.idtu = idtu;
        this.idbo = idbo;
        this.dapan = dapan;
        this.dichnghia = dichnghia;
        this.loaitu = loaitu;
        this.audio = audio;
        this.anh = anh;
    }

    public int getIdtu() {
        return idtu;
    }

    public void setIdtu(int idtu) {
        this.idtu = idtu;
    }

    public int getIdbo() {
        return idbo;
    }

    public void setIdbo(int idbo) {
        this.idbo = idbo;
    }

    public String getDapan() {
        return dapan;
    }

    public void setDapan(String dapan) {
        this.dapan = dapan;
    }

    public String getDichnghia() {
        return dichnghia;
    }

    public void setDichnghia(String dichnghia) {
        this.dichnghia = dichnghia;
    }

    public String getLoaitu() {
        return loaitu;
    }

    public void setLoaitu(String loaitu) {
        this.loaitu = loaitu;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }
}
