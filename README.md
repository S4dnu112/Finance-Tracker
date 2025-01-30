# PennyWise Finance-Tracker

To run the program, make sure you have sqlite installed and is on root directory (above src)

## Compiling everything altogether
```javac -cp "lib/*" -sourcepath src src/backend/FinanceBackend.java src/transactionModels/*.java src/frontend/*.java; ```

## Running the GUI application:
```java -cp "src;lib/*" frontend.MainFrame```

## Combined command
```javac -cp "lib/*" -sourcepath src src/backend/FinanceBackend.java src/transactionModels/*.java src/frontend/*.java; java -cp "src;lib/*" frontend.MainFrame```


