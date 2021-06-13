package com.daofab.transactions.exception;

public class DaoFabException extends Exception {

    public int responseCode;
    public String error;

    public DaoFabException(int responseCode, String error){
        this.responseCode = responseCode;
        this.error = error;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
