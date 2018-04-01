package com.example.arzoo.chatbot.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arzoo on 3/29/2018.
 */

public class AckResponse {
    @SerializedName("name")
    String name;
    @SerializedName("age")
    String age;
    @SerializedName("symptoms")
    String symptoms;

    public AckResponse(String name, String age, String symptoms) {
        this.name = name;
        this.age = age;
        this.symptoms = symptoms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
}
