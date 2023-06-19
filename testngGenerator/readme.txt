/**
 * This module is a Java Application to generate testng xml files with an integrated UI, based on an integration file containing the available test classes, environments and mobiles for your project.
 * So anyone can easily configure its personal case and generate custom horodated test files then run them with a Browserstack account. The login into browserstack have to be written in the scenarios.java code, 
 * as your specific nomenclature should be modified to match with your project.
 * @author achoupaut
 */
 
 To add this module to your project, add the java package to your src folders, then move the env_integration folder to the root of your project.
 The XML integration file inside it can be modified by anyone, as a text file.
 When your modifications to the scenarios.java methods will be achieved to write the testng files following your own project rules, you'll be able to build your project directly into a runable JAR then build testng files and instantly run them.
 The testng files will be entitled "customScenario_ + horodated (following a french format "DD-MM-YYYY-HH-mm-ss" => editable method in the helper package). They are generated at the root of the project, like standard testng XML in maven projects.
 
 The current version give the following objetcs for integration:
 - environments (preprod, uat, staging)
 - mobiles (defined by name, os and os version)
 - test classes (the java classes containing the tests suites)
 
Feel free to add any type of parameters or variables needed in your own project.