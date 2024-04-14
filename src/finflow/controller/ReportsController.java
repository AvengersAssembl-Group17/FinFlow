package finflow.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import finflow.dao.DatabaseConnection;
import finflow.dao.TransactionDAO;
import finflow.dao.TransactionDAOImpl;
import finflow.model.Transaction;
import finflow.utils.Constants;
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
        
        //adjusting legend
        customizeLegendLayout(pieChartTotal);
        customizeLegendLayout(pieChartIncome);
        customizeLegendLayout(pieChartExpense);
    }

    private void populatePieCharts() {
        // Retrieve the active user ID
        int activeUser = LoginController.getInstance().activeID();

        // Fetch total income and expense for the active user
        Double totalIncome = transactionDAO.getTotalIncomeUser(activeUser);
        Double totalExpense = transactionDAO.getTotalExpenseUser(activeUser);

        // Fetch transactions grouped by category for the active user
        List<Transaction> incomeTransactions = transactionDAO.getTransactionsGroupedByCategory(activeUser, Constants.ACTION_INCOME);
        List<Transaction> expenseTransactions = transactionDAO.getTransactionsGroupedByCategory(activeUser, Constants.ACTION_EXPENSE);

        // Initialize pie chart data lists
        ObservableList<PieChart.Data> totalData = FXCollections.observableArrayList(
                new PieChart.Data(Constants.ACTION_INCOME, totalIncome),
                new PieChart.Data(Constants.ACTION_EXPENSE, totalExpense)
        );

        // Populate pie charts with data
        pieChartTotal.setData(totalData);
        pieChartTotal.setTitle("Overall Income and Expense");

        if(!incomeTransactions.isEmpty()) {
            populatePieChartWithTransactions(pieChartIncome, incomeTransactions);
            pieChartIncome.setTitle(Constants.ACTION_INCOME);
        }
    
        if(!expenseTransactions.isEmpty()) {
        	populatePieChartWithTransactions(pieChartExpense, expenseTransactions);
            pieChartExpense.setTitle(Constants.ACTION_EXPENSE);
        }
        
    }

    private void populatePieChartWithTransactions(PieChart pieChart, List<Transaction> transactions) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Transaction transaction : transactions) {
        	String amount= String.format(Constants.CURRENCY_FORMAT,transaction.getAmount());
            String label = transaction.getTitle() + " - " + amount; 
            PieChart.Data pieData = new PieChart.Data(label, transaction.getAmount());
            data.add(pieData);
        }
        pieChart.setData(data);
    }
    
    private void customizeLegendLayout(PieChart pieChart) {
        pieChart.legendSideProperty().set(javafx.geometry.Side.BOTTOM);
        pieChart.setLabelsVisible(false); 
        pieChart.setLegendVisible(true); 
        pieChart.getStylesheets().add(getClass().getResource("/finflow/stylesheet/pie-chart.css").toExternalForm());
    }
}



