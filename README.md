# TGTGNotificator

## Generally

TGTG Notificator is a simple console app to monitoring new packages in TooGoodToGo app.
Application sends notifications when it finds a new package in your area.
The following notification types are supported:
 - Console output,
 - Windows notifications.

## How to run?

Requirements:
 - Java 11
 - Java and Maven in environment variables

Before: 
 - Set necessary informations to config.properties file.

Run:
1. `mvn -U clean install` from main project directory
2. `java -jar target/TGTGNotificator-1.0.jar` from main project directory as administrator
