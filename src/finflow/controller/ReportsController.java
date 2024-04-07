package finflow.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import finflow.dao.DatabaseConnection;
import finflow.dao.TransactionDAO;
import finflow.dao.TransactionDAOImpl;
import finflow.model.Transaction;
import java.sql.SQLException;
import java.util.List;

public class ReportsController {

    @FXML
    private PieChart pieChartTotal;

    @FXML
    private PieChart pieChartIncome;

    @FXML
    private PieChart pieChartExpense;

    public void initialize() {
        // Initialize the pie charts with data from the database
        populatePieCharts();
    }

    private void populatePieCharts() {
        // Retrieve the active user ID
        int activeUser = LoginController.getInstance().activeID();

        // Instantiate the DAO and obtain the necessary data
        TransactionDAO transactionDAO = new TransactionDAOImpl(new DatabaseConnection());

        // Fetch total income and expense for the active user
        Double totalIncome = transactionDAO.getTotalIncomeUser(activeUser);
        Double totalExpense = transactionDAO.getTotalExpenseUser(activeUser);

        // Fetch transactions grouped by category for the active user
        List<Transaction> incomeTransactions = transactionDAO.getTransactionsGroupedByCategory(activeUser, "Income");
        List<Transaction> expenseTransactions = transactionDAO.getTransactionsGroupedByCategory(activeUser, "Expense");

        // Initialize pie chart data lists
        ObservableList<PieChart.Data> totalData = FXCollections.observableArrayList(
                new PieChart.Data("Income", totalIncome),
                new PieChart.Data("Expense", totalExpense)
        );
        ObservableList<PieChart.Data> incomeData = mapTransactionsToPieChartData(incomeTransactions);
        ObservableList<PieChart.Data> expenseData = mapTransactionsToPieChartData(expenseTransactions);

        // Populate pie charts with data
        pieChartTotal.setData(totalData);
        pieChartIncome.setData(incomeData);
        pieChartExpense.setData(expenseData);
    }

    // Helper method to map transactions to PieChart.Data
    private ObservableList<PieChart.Data> mapTransactionsToPieChartData(List<Transaction> transactions) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Transaction transaction : transactions) {
            data.add(new PieChart.Data(transaction.getTitle(), transaction.getAmount()));
        }
        return data;
    }
}
