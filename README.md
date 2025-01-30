# PennyWise Finance-Tracker

To run the program, make sure you have sqlite installed

## Compiling everything altogether
```javac -cp "lib/*" -sourcepath src src/backend/FinanceBackend.java src/transactionModels/*.java src/frontend/*.java; ```

## Running the GUI application specific file:
```java -cp "src;lib/*" frontend.MainFrame```

## Combined command
```javac -cp "lib/*" -sourcepath src src/backend/FinanceBackend.java src/transactionModels/*.java src/frontend/*.java; java -cp "src;lib/*" frontend.MainFrame```


