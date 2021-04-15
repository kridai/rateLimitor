package com.example.demo.core;

import java.util.Objects;

public class Client {
    private String name;

    public Client(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
