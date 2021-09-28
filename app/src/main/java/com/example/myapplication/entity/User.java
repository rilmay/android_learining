package com.example.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String name;
    private String company;
    private int age;

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            String name = source.readString();
            String company = source.readString();
            int age = source.readInt();
            return new User(name, company, age);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(String name, String company, int age){
        this.name = name;
        this.company = company;
        this.age = age;
    }

    public User(String name, int age){
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(company);
        dest.writeInt(age);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", age=" + age +
                '}';
    }
}