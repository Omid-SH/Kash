package com.example.nobintest.JsonDataTypes;

import java.util.List;

public class Packet {

    List<Data> data;
    Status StatusObject;

    // Getter Methods
    public List<Data> getData() {
        return data;
    }

    public Status getStatus() {
        return StatusObject;
    }

    // Setter Methods
    public void setData(List<Data> data) {
        this.data = data;
    }

    public void setStatus(Status statusObject) {
        this.StatusObject = statusObject;
    }
}
