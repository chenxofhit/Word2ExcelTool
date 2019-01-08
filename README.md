# Word2ExcelTool
A SWT application writing for my brother.

The program is to use Apache POI 4.8 to extract words from MS office Word supported *.docx file and output them in  another excel,
with the format according to my brother's request.

## Features
SWT + Maven + Apache  POI


## How to run ?

```git clone https://github.com/chenxofhit/Word2ExcelTool.git
cd Word2ExcelTool
mvn package -Dmaven.test.skip=true
cd ./target
java -jar tool-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```
