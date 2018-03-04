package com.firebase.dailyexpenses;

/**
 * Created by eunoiatechnologies on 15/09/16.
 */

public class ExpensesModel {
    private String amount;
    private String description;
    private String flag;
    private String datetostore;
    private String createdTime;
    private String modifiedTime;

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getDatetostore() {
        return datetostore;
    }

    public void setDatetostore(String datetostore) {
        this.datetostore = datetostore;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
