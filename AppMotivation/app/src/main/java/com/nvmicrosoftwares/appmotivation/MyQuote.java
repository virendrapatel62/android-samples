package com.nvmicrosoftwares.appmotivation;

public class MyQuote {
    String text ;
    String from ;



    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public String getText() {

        return text;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public MyQuote(String text, String from) {

        this.text = text;
        this.from = from;
    }
}
