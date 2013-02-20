devkit-assist
=============

why?

I wanted a better starting place for all the copy/paste-y bits when exposing or 'porting' a class to a mule cloud connector generated using devkit.

how - 

    1. run mules devkit to generate the maven project.
    2. edit the java connector/sample xml and test files removing the pieces you don't want to use
    3. create a variable in the connector for the class you wish to expose to mule, mathVar in the example below
    4. run this silly class to generate the methods/sample xml/flows 
        * java com.espn.mule.devkit.Assist java.lang.Math Math-connector.xml.sample math mathVar
    5. paste into the appro. files 
    6. RENAME all variables, javadocs
    7. do not skip step 6.
    8. or skip step 7.

what -

    * java.lang.Math is the class you want to expose in mule
    * Math-connector.xml.sample is the name of the sample xml config file in the docs folder of your connector
    * math is the connector's namespace
    * mathVar is the variable name of the instance of the class in the first argument
