# StreamTvTestNg

How to setup & launch the automation framework

Please clone this repository to your local machine with installed software :

Maven (version 3.1.1 and newer)
Java 7 and high
Firefox
To run tests please run in project folder command : _mvn test -Dsuite=testSuite.xml_.
To run only UI tests run command : _mvn test -Dsuite=uitestsuite.xml_
To run only API tests run command : _mvn test -Dsuite=apitestsuite.xml_

Allure reports. To generate html reports execute _mvn test site -Dsuite=testSuite.xml_, after that result can be found under /target/site/allure-maven-plugin folder. 

To execute tests in chrome add "-Ddriver=chrome" into command line. E.x. *mvn test site -Dsuite=testSuite.xml -Ddriver=chrome*. By default it starts firefox.
