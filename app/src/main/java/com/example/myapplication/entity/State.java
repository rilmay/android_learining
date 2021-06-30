package com.example.myapplication.entity;

public class State {

    private String name; // название
    private String capital;  // столица
    private int flagResource; // ресурс флага
    private int count;

    public State(String name, String capital, int flag, int count){

        this.name=name;
        this.capital=capital;
        this.flagResource=flag;
        this.count = count;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return this.capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public int getFlagResource() {
        return this.flagResource;
    }

    public void setFlagResource(int flagResource) {
        this.flagResource = flagResource;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
