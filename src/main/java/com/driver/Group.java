package com.driver;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private int numberOfParticipants;
    private List<User> users;

    private int numberOfMessages;

    private String admin;

    public Group() {
    }

    public Group(String name, int numberOfParticipants, List<User> users, int numberOfMessages) {
        this.name = name;
        this.numberOfParticipants = numberOfParticipants;
        this.users = new ArrayList<>();
        this.numberOfMessages = numberOfMessages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
