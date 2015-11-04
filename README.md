# Spordisemu
This is for "Software Project" class.
Spordisemu is an Android app for finding people to do sports together.
More information about the project you can find in our wiki page.

Team:
Triinu Liis Kelder, Ingrid Kaasik, Julia Kaas, Kelian Kaio

# Used technologies   

## Build process and deployment   
Since we write our app in Android Studio, we have build.gradle which automatically builds the application. After every iteration we upload the apk file of the app for our client and mentor to test. The apk file needs to be run on android device or android emulator. 

In order to run the app correctly, the device must be connected to internet and location (GPS) must be enabled.

## Staging environment
We are going to test our application in Android Studio's emulator or in our phones. After every sprint, we are going to send an apk file to customer so they can see it on [Bluestacks](http://www.bluestacks.com/) emulator.

## Continuous Integration  
We are using GreenhouseCI. For automated tests we use Espresso. 
