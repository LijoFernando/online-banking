package com.myWeb.model.BankException;

public class CustomizedException extends Exception {
    //Exception Message
    public CustomizedException(String msg){
            super(msg);
        }
    //Exception Message with Error
    public CustomizedException(String s,Throwable error) { super(s, error); }
}
