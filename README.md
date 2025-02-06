# PennyWise Finance-Tracker

PennyWise is a simple finance tracker that allows users to track their income, expenses, and money from different accounts. It encourages users to take full control over their finances and thus make informed decisions.

## Screenshots

![Home_Panel](docs/HomePanel.png)

![AddIncome_Panel](docs/AddIncomePanel.png)

![AddExpense_Panel](docs/AddExpensePanel.png)

![Transaction_History](docs/TransactionsHistory.png)

## Features
- Add an income data
- Add an expense data
- Transfer money between different accounts
- Specify recurrence transactions (income, expense transfer)
- Set a future transaction
- Delete a transaction
- End a recurring transaction
- View aggregates (such as balances per account and expenses per category)
- View transaction history

## Requirements
> **Note:** External dependencies are already in the lib folder 
- Sqlite
- Java

## How to Run the Program
Compile
```
javac -cp "lib/*" -sourcepath src src/backend/FinanceBackend.java src/transactionModels/*.java src/frontend/*.java src/Finance_Tracker.java;
```

Run
```
java -cp "src;lib/*" Finance_Tracker
```

Combined
```
javac -cp "lib/*" -sourcepath src src/backend/FinanceBackend.java src/transactionModels/*.java src/frontend/*.java src/Finance_Tracker.java; java -cp "src;lib/*" Finance_Tracker
```

## Contributors
<table>
<tr>
    <td align="center">
        <a href="https://github.com/S4dnu112">
            <img src="https://github.com/S4dnu112.png" width="100px;"/>
            <br />
            <sub><b>S4dnu112</b></sub>
        </a>
    </td>
    <td align="center">
      <a href="https://github.com/neo-geroda">
          <img src="https://github.com/neo-geroda.png" width="100px;"/>
          <br />
          <sub><b>neo-geroda</b></sub>
      </a>
    </td>
    <td align="center">
        <a href="https://github.com/DLJocson">
            <img src="https://github.com/DLJocson.png" width="100px;"/>
            <br />
            <sub><b>DLJocson</b></sub>
        </a>
    </td>
    <td align="center">
        <a href="https://github.com/EmsGarbino">
            <img src="https://github.com/EmsGarbino.png" width="100px;"/>
            <br />
            <sub><b>EmsGarbino</b></sub>
        </a>
    </td>
    <td align="center">
      <a href="https://github.com/kristinebae">
          <img src="https://github.com/kristinebae.png" width="100px;"/>
          <br />
          <sub><b>kristinebae</b></sub>
      </a>
    </td>
    <td align="center">
        <a href="https://github.com/Felicityjoy">
            <img src="https://github.com/Felicityjoy.png" width="100px;"/>
            <br />
            <sub><b>Fel</b></sub>
        </a>
    </td>
</tr>
</table>








