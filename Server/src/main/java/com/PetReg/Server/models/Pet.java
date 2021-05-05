package com.PetReg.Server.models;

import java.util.Date;
import java.util.List;

public class Pet {
    private long id;
    private String name;
    private int birth;
    private String fio;
    private String address;
    private String tel;
    private List<Vaccination> vaccinations;

    public static class Vaccination{
        private String name;
        private Date date;

        public Vaccination(){}

        public Vaccination(String name, Date date) {
            this.name = name;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    public Pet(){}

    public Pet(long id, String name, int age, String fio, String address, String tel, List<Vaccination> vaccinations) {
        this.id = id;
        this.name = name;
        this.birth = age;
        this.fio = fio;
        this.address = address;
        this.tel = tel;
        this.vaccinations = vaccinations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirth() {
        return birth;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<Vaccination> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(List<Vaccination> vaccinations) {
        this.vaccinations = vaccinations;
    }
}
