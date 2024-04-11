# FinFlow
A JavaFX GUI app to track all incoming and outgoing transactions efficiently. Its simple design makes it lightweight and straightforward.


## What is FinFlow?

- FinFlow allows users to record their expenses, including details such as date, category, amount, and description.
- FinFlow allows users to record their income, providing a comprehensive overview of financial activities.
- FinFlow maintains a history of all recorded expenses and income, providing a comprehensive overview of financial activities.
- FinFlow generates visual analytics and reports based on recorded expenses, offering insights into spending patterns, trends, and areas for potential savings.


## User Story:

- As a user, I should be able to open the FinFlow app and login using my credentials.
- As a user, I should be able to register to the FinFlow app.
- As a user, I should be able to create/update expenses.
- As a user, I should be able to create/update income.
- As a user, I should be able to view my transaction history.
- As a user, I should be able to view reports of my recorded expenses.

Certainly! Here's the updated README.md file with instructions for executing the database scripts before running the project:

---

# Setting up Finflow project with MySQL Connector, Scene Builder, and JRE 17+ in Eclipse

This guide will walk you through the steps required to set up a JavaFX project with MySQL Connector, Scene Builder, and JRE version 17 or higher in Eclipse.

## Prerequisites

- Eclipse IDE installed on your system.
- JRE 17 or higher installed on your system.
- MySQL Connector/J library.
- JavaFX Scene Builder installed on your system.
- MySQL database server installed on your system.

## Setup Instructions

1. **Create a JavaFX Project:**
   - Open Eclipse IDE.
   - Go to `File > New > Other`.
   - Select `JavaFX > JavaFX Project`.
   - Follow the wizard to create your JavaFX project.

2. **Add MySQL Connector/J to Your Project:**
   - Download the MySQL Connector/J library from [here](https://dev.mysql.com/downloads/connector/j/).
   - Extract the downloaded file.
   - In Eclipse, right-click on your project.
   - Select `Build Path > Configure Build Path`.
   - Click on the `Libraries` tab.
   - Click `Add External JARs` and select the `mysql-connector-java-x.x.x.jar` file from the extracted folder.
   - Click `Apply and Close`.

3. **Integrate Scene Builder with Eclipse:**
   - Download JavaFX Scene Builder from [here](https://gluonhq.com/products/scene-builder/).
   - Install Scene Builder on your system.
   - Open Eclipse.
   - Go to `Window > Preferences`.
   - Select `JavaFX`.
   - Browse to the Scene Builder executable path and click `Apply and Close`.

4. **Execute Database Scripts:**
   - Before running the project, execute `release_db.sql` to set up your database schema.

## Running the Project

1. **Running from Eclipse:**
   - Right-click on your JavaFX project.
   - Select `Run As > Java Application`.
   - Choose your main class if prompted.
   - Your JavaFX application should now run.

2. **Passing VM Arguments:**
   - If your project requires VM arguments, follow these steps:
     - Right-click on your JavaFX project.
     - Select `Run As > Run Configurations`.
     - Click on the `Arguments` tab.
     - In the `VM arguments` field, enter your required VM arguments.
     - Click `Apply` and then `Run`.

## Example VM Arguments

```bash
--module-path /path/to/javafx-sdk-17/lib --add-modules javafx.controls,javafx.fxml,javafx.web
```

Replace `/path/to/javafx-sdk-17/lib` with the actual path to your JavaFX SDK library.

---
