package com.challenge.blog.entity;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String mail;
    private String password;

    //necesita constructor predeterminado para JSON parsear
    public JwtRequest()
    {

    }

    public JwtRequest(String mail, String password) {
        this.setMail(mail);
        this.setPassword(password);
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
