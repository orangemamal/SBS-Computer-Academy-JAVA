package com.checkingAccount;

public class AuthenException extends Exception{
    private static final long serialVersionUID = 1L;
    public AuthenException(String message){
        super(message);
    }
}