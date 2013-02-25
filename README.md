devkit-assist
=============

why?

I wanted a better starting place for all the copy/paste-y bits when exposing or 'porting' an existing or "legacy" class to mule as a "cloud connector" generated using devkit.

how - 

    1. run the mule devkit to generate the initial maven project.
    2. edit the java connector/sample xml and test files removing the pieces you won't use. 
        If this is the first time using the devkit, I recommend the tutorial on mulesofts site, however if you doin't care for their words and prefer mine, god help you - check out my mule_samples github site -
            https://github.com/patarleth/mule_samples/wiki and more specifically my tutorial on devkit 
            https://github.com/patarleth/mule_samples/wiki/Build-a-custom-mule-connector
    3. create a variable in the connector for the class you wish to expose to mule, mathVar in the example below
    4. run the silly class and generate the methods/sample xml/flows 
        * java com.espn.mule.devkit.Assist java.lang.Math Math-connector.xml.sample math mathVar
    5. paste into the appro. files 
    6. RENAME all variables, javadocs
    7. do not skip step 6.
    8. or skip step 7.
    9. attempt to compile.  YOU WILL GET ERRORS ON PURPOSE- see step 6. You will need to RENAME all variables and add javadocs describing whats passed/returned for each method wrapped in the connector.

what -

    * In the example provided I've 'ported' java.lang.Math exposed to mule as a cloud connector.  No reason, the Math was not particularly interesting or useful it was just there. Don't over think this. For this example
        1. Math-connector.xml.sample is the name of the sample xml config file in the docs folder of your connector
        2. math is the connector's namespace
        3. mathVar is the variable name of the instance of the class in the first argument

