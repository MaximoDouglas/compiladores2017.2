# GimmeDaddy programing language

### Specification and implementation (in Java) of the lexical and syntactic parsers of the GimmeDaddy language.

## Build
### 1. In eclipse, right click on the gimmeDaddy project -> Run as -> Maven install.

### 2. If the build run successfully, you will see the BUILD SUCCESS in the console

## Running
### 3. Go to the project folder -> target. You will see a .jar file. This jar is the result of the build you made.

### 4. To run the analysis, you need some file to analyze. Create a file. For example, lets call it OlaMundo, and write the code you want to analyze.

### 5. Open a terminal. In the example is the Windows cmd. Navigate to the folder containing the .jar file and the code file and run the command: `java -jar <jarName>.jar <fileName>`. In the example is: `java -jar compilador-0.0.1-SNAPSHOT.jar OlaMundo`

### 6. If everything is ok, you will see your the tokens of your program, as well as the output like the outputs in the end of [THIS](https://github.com/MaximoDouglas/gimmedaddy_language/blob/master/docs/03%20-%20GimmeDaddy%20-%20Gramaticas%2C%20Tabela%2C%20Saida%20e%20Errata.pdf) document.
