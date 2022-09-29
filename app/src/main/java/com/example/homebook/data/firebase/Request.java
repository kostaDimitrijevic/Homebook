package com.example.homebook.data.firebase;

public class Request {
    private String from;
    private String to;
    private String firstname;
    private String lastname;

    public Request() {
    }

    public Request(String from, String to, String firstname, String lastname) {
        this.from = from;
        this.to = to;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
