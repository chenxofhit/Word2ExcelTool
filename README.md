# Word2ExcelTool
A SWT application writing for my brother.

The program is to use Apache POI 4.8 to extract words from MS office Word supported *.docx file and output them in  another excel,
with the format according to my brother's request.

## Features
SWT,  Maven, Apache  POI, Cross platform


## How to run ?

For running the application with your OS,  please select proper maven dependency jar in the pom.xml after you cloned the reponsitory. The following codes are recommended to compile the project.

```shell
git clone https://github.com/chenxofhit/Word2ExcelTool.git
cd Word2ExcelTool
mvn package -Dmaven.test.skip=true
cd ./target
java -jar tool-0.0.1-jar-with-dependencies.jar 
```

The following snapshot is taken from  a  Windows 32 bit machine after executing the codes:

![img](https://ws2.sinaimg.cn/large/006tNc79gy1fz03t8b077j30rb07rq4f.jpg)