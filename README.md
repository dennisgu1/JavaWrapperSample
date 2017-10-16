# JavaWrapperSample
Veracode Java Wrapper API Sample

This JavaWrapperSample.java include FIVE Java wrapper APIs:
getAppList()
getAppInfo()
getBuildList()
getBuildInfo()
getDetailedReport()



Please change the following String values in JavaWrapperSample.java

final String username = "xxxxxxxx";
final String password = "xxxxxxxx";
final String appName = "xxxxxxxx";

Unzip the zip file into a directory, run java compiler:

$ javac -cp ".;./vosp-api-wrappers-java-17.9.4.6.jar" JavaWrapperSample.java

Run Java class:

$ java -cp ".;./vosp-api-wrappers-java-17.9.4.6.jar" JavaWrapperSample
