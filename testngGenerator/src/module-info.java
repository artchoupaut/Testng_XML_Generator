/**
 * This module is a Java Application to generate testng xml files with an integrated UI, based on an integration file containing the available test classes, environments and mobiles for your project.
 * So anyone can easily configure its personal case and generate custom horodated test files then run them with a Browserstack account. The login into browserstack have to be written in the scenarios.java code, 
 * as your specific nomenclature should be modified to match with your project. 
 * @author achoupaut
 */
module testngGenerator {
	requires java.desktop;
	requires org.testng;
}