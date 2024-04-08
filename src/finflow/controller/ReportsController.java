package finflow.controller;

//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.chart.PieChart;
//import finflow.dao.DatabaseConnection;
//import finflow.dao.TransactionDAO;
//import finflow.dao.TransactionDAOImpl;
//import finflow.model.Transaction;
//import finflow.utils.Constants;
//
//import java.sql.SQLException;
//import java.util.List;
//
//public class ReportsController {
//
//    @FXML
//    private PieChart pieChartTotal;
//
//    @FXML
//    private PieChart pieChartIncome;
//
//    @FXML
//    private PieChart pieChartExpense;
//
//    public void initialize() {
//        // Initialize the pie charts with data from the database
//        populatePieCharts();
//    }
//
////    private void populatePieCharts() {
////        // Retrieve the active user ID
////        int activeUser = LoginController.getInstance().activeID();
////
////        // Instantiate the DAO and obtain the necessary data
////        TransactionDAO transactionDAO = new TransactionDAOImpl(new DatabaseConnection());
////
////        // Fetch total income and expense for the active user
////        Double totalIncome = transactionDAO.getTotalIncomeUser(activeUser);
////        Double totalExpense = transactionDAO.getTotalExpenseUser(activeUser);
////
////        // Fetch transactions grouped by category for the active user
////        List<Transaction> incomeTransactions = transactionDAO.getTransactionsGroupedByCategory(activeUser, "Income");
////        List<Transaction> expenseTransactions = transactionDAO.getTransactionsGroupedByCategory(activeUser, "Expense");
////
////        // Initialize pie chart data lists
////        ObservableList<PieChart.Data> totalData = FXCollections.observableArrayList(
////                new PieChart.Data("Income", totalIncome),
////                new PieChart.Data("Expense", totalExpense)
////        );
////        ObservableList<PieChart.Data> incomeData = mapTransactionsToPieChartData(incomeTransactions);
////        ObservableList<PieChart.Data> expenseData = mapTransactionsToPieChartData(expenseTransactions);
////
////        // Populate pie charts with data
////        pieChartTotal.setData(totalData);
////        pieChartIncome.setData(incomeData);
////        pieChartExpense.setData(expenseData);
////    }
//    
//    
////    private void populatePieCharts() {
////        // Retrieve the active user ID
////        int activeUser = LoginController.getInstance().activeID();
////
////        // Instantiate the DAO and obtain the necessary data
////        TransactionDAO transactionDAO = new TransactionDAOImpl(new DatabaseConnection());
////
////        // Fetch total income and expense for the active user
////        Double totalIncome = transactionDAO.getTotalIncomeUser(activeUser);
////        Double totalExpense = transactionDAO.getTotalExpenseUser(activeUser);
////
////        // Fetch transactions grouped by category for the active user
////        List<Transaction> incomeTransactions = transactionDAO.getTransactionsGroupedByCategory(activeUser, "Income");
////        List<Transaction> expenseTransactions = transactionDAO.getTransactionsGroupedByCategory(activeUser, "Expense");
////
////        // Initialize pie chart data lists
////        ObservableList<PieChart.Data> totalData = FXCollections.observableArrayList(
////                new PieChart.Data(Constants.ACTION_INCOME, totalIncome),
////                new PieChart.Data(Constants.ACTION_EXPENSE, totalExpense)
////        );
////        ObservableList<PieChart.Data> incomeData = mapTransactionsToPieChartData(incomeTransactions, Constants.ACTION_INCOME);
////        ObservableList<PieChart.Data> expenseData = mapTransactionsToPieChartData(expenseTransactions, Constants.ACTION_EXPENSE);
////
////        // Set headings for Income and Expense Pie Charts
////        pieChartIncome.setTitle(Constants.ACTION_INCOME);
////        pieChartExpense.setTitle(Constants.ACTION_EXPENSE);
////
////        // Add type labels to Income and Expense Pie Charts
////        for (PieChart.Data data : incomeData) {
////            data.setName("Income: " + data.getName());
////        }
////        for (PieChart.Data data : expenseData) {
////            data.setName("Expense: " + data.getName());
////        }
////
////        // Populate pie charts with data
////        pieChartTotal.setData(totalData);
////        pieChartIncome.setData(incomeData);
////        pieChartExpense.setData(expenseData);
////    }
////    
////    // Helper method to map transactions to PieChart.Data
////    private ObservableList<PieChart.Data> mapTransactionsToPieChartData(List<Transaction> transactions, String category) {
////        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
////        for (Transaction transaction : transactions) {
////            // Construct label with type information
////            String label = transaction.getTitle() + " (" + getCategoryName(transaction.getType(), category) + ")";
////            PieChart.Data pieData = new PieChart.Data(label, transaction.getAmount());
////            // Set name property for the data point
////            pieData.setName(label);
////            data.add(pieData);
////        }
////        return data;
////    }
////
////    // Helper method to get category name based on transaction type
////    private String getCategoryName(int type, String category) {
////        return getCategoryNameFromDatabase(type);
////    }
////
////    // Helper method to get category name from the database (You need to implement this)
////    private String getCategoryNameFromDatabase(int type) {
////        switch (type) {
////            case 1: return "Salary";
////            case 2: return "Education";
////            case 3: return "Grocery";
////            case 4: return "Rent";
////            case 5: return "Travel";
////            case 6: return "Food";
////            case 7: return "Entertainment";
////            case 8: return "Clothes";
////            case 9: return "Utilities";
////            default: return "Unknown";
////        }
////    }


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import finflow.dao.DatabaseConnection;
import finflow.dao.TransactionDAO;
import finflow.dao.TransactionDAOImpl;
import finflow.model.Transaction;
import finflow.utils.Constants;

import java.sql.SQLException;
import java.util.List;

public class ReportsController {

    @FXML
    private PieChart pieChartTotal;

    @FXML
    private PieChart pieChartIncome;

    @FXML
    private PieChart pieChartExpense;

    private TransactionDAO transactionDAO;

    public void initialize() {
        // Instantiate the DAO
        transactionDAO = new TransactionDAOImpl(new DatabaseConnection());

        // Initialize the pie charts with data from the database
        populatePieCharts();
    }

    private void populatePieCharts() {
        // Retrieve the active user ID
        int activeUser = LoginController.getInstance().activeID();

        // Fetch total income and expense for the active user
        Double totalIncome = transactionDAO.getTotalIncomeUser(activeUser);
        Double totalExpense = transactionDAO.getTotalExpenseUser(activeUser);

        // Fetch transactions grouped by category for the active user
        List<Transaction> incomeTransactions = transactionDAO.getTransactionsGroupedByCategory(activeUser, "Income");
        List<Transaction> expenseTransactions = transactionDAO.getTransactionsGroupedByCategory(activeUser, "Expense");

        // Initialize pie chart data lists
        ObservableList<PieChart.Data> totalData = FXCollections.observableArrayList(
                new PieChart.Data(Constants.ACTION_INCOME, totalIncome),
                new PieChart.Data(Constants.ACTION_EXPENSE, totalExpense)
        );

        // Populate pie charts with data
        pieChartTotal.setData(totalData);
        pieChartTotal.setTitle("Total");

        populatePieChartWithTransactions(pieChartIncome, incomeTransactions);
        pieChartIncome.setTitle("Income");

        populatePieChartWithTransactions(pieChartExpense, expenseTransactions);
        pieChartExpense.setTitle("Expense");
    }

    private void populatePieChartWithTransactions(PieChart pieChart, List<Transaction> transactions) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Transaction transaction : transactions) {
            String typeName = transactionDAO.getTransactionTypeNameById(transaction.getType());
            String label = transaction.getTitle() + " (" + typeName + ")";
            PieChart.Data pieData = new PieChart.Data(label, transaction.getAmount());
            data.add(pieData);
        }
        pieChart.setData(data);
        // Set labels visible
        pieChart.setLabelsVisible(true);
    }
}



