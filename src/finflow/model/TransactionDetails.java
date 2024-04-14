package finflow.model;

import java.time.LocalDate;

public class TransactionDetails {
    private String title;
    private double amount;
    private LocalDate date;

    public TransactionDetails(String title, double amount, LocalDate date) {
        this.title = title;
        this.amount = amount;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
