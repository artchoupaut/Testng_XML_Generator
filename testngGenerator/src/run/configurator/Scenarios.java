package run.configurator;

import java.io.IOException;
import java.io.Writer;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import helper.RandomNumberGenerator;

/**
 * This class is designed to generate a XML file at the root of the project, containing the custom specifications for a testng run.
 * @author achoupaut
 *
 */
public class Scenarios {
	
	protected String env;
	protected ArrayList<String> tests;
	protected Mobile mobile;	
	
	/*
	 * *********************************************************************************************************************************************
	 * 
	 * XML MAIN METHOD
	 * 
	 * *********************************************************************************************************************************************
	 */
	
	/**
	 * This method generate the scenario xml.
	 * @throws IOException 
	 */
	protected Path generateXML() throws IOException {
		String fileName = RandomNumberGenerator.getHorodatedFileName();
		
		File scenarioFile = new File(fileName);
		if (scenarioFile.createNewFile()) {
	        System.out.println("File created: " + scenarioFile.getName());
	        } else {
	            System.out.println("File already exists.");
	        }

		Writer writeXML = new FileWriter(scenarioFile, true);
		writeXML.flush();
		
		// 
		
		/*
		 * 
		 *	SDTtestLib XML form module to re-use here and after. 
		 *  => add parameters, add class, etc.
		 * 
		 */
		// add the basic structure
		writeXML.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				+ "<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\">\r\n"
				+ "<suite name=\"Mobile Test Suite BrowserStack\">\r\n"
				+ "  <listeners>\r\n"
				+ "    <listener class-name=\"frmwrk.listeners.TestListener\" />\r\n"
				+ "    <listener class-name=\"listeners.UpdateBrowserstackStatusListener\" />\r\n"
				+ "    <listener class-name=\"listeners.CreatePatientListener\" />\r\n"
				+ "  </listeners>\r\n"
				+ "  <parameter name=\"ta.run.platform\" value=\"MOBILE\" />\r\n"
				+ "  <parameter name=\"ta.run.location\" value=\"BROWSERSTACK\" />\r\n"
				+ "  <parameter name=\"ta.mobile.apptype\" value=\"APP\" />\r\n"
				+ "  <parameter name=\"ta.browserstack.username\" value=\"username\" />\r\n"
				+ "  <parameter name=\"ta.browserstack.accesskey\" value=\"accesskey\" />\r\n"
				+ "  <parameter name=\"ta.browserstack.buildname\" value=\"buildname\" />\r\n");
		
		// add environment and meta dataset
		writeXML.write(writeTestEnvironment(env)+"\r\n");
		writeXML.write(writeTestMobile(mobile));
		// ADD TESTS
		writeXML.write("\r\n"+"  <test name=\"InitialTests\">\r\n"
				+ "    <classes>\r\n");
		System.out.println("test number : " + tests.size());
		for (int i = 0; i < tests.size(); i++) {
			System.out.println("test " + i + " : " + tests.get(i));
			writeXML.write(addTestClass(tests.get(i)));
			writeXML.write("\r\n");
		}
		writeXML.write("</classes>\r\n"
				+ "  </test>\r\n"
				+ "</suite>");
		writeXML.flush();
		writeXML.close();

		Path path = Paths.get("",(fileName));
		return path;
	}
	
	

	/*
	 * *********************************************************************************************************************************************
	 * 
	 * GET/SET & XML partial manipulation
	 * 
	 * *********************************************************************************************************************************************
	 */
	/**
	 * Returns the test name corresponding to the index number put in parameter
	 */
	protected String getTest(int i) {
		return tests.get(i);
	}

	/**
	 * 
	 * Returns the parameters environment and APIurl, based on the sent String
	 */
	private String writeTestEnvironment(String env) {
		switch (env) {
			case "preprod":
				return "	<parameter name=\"ta.run.environment\" value=\"PREPROD\" />\r\n"
						+"	<parameter name=\"apiUrl\" value=\"https://api-preprod.env.url/api/\" />";
			case "uat":
				return "	<parameter name=\"ta.run.environment\" value=\"UAT\" />\r\n"
						+ "    <parameter name=\"apiUrl\" value=\"https://api-uat.env.url/api/\" />";
			case "staging":
				return "	<parameter name=\"ta.run.environment\" value=\"STAGING\" />"+"\r\n"
						+"<parameter name=\"apiUrl\" value=\"https://api-staging.env.url/api/\" />";
			default:
				return null;
		}
	}
	
//	/**
//	 * Returns the xml parameter corresponding to the selected study 
//	 */
//	private String writeTestStudy(String study) {
//		switch (study) {
//		case "waitingForNewStudies":
//			return "";
//		default:
//			return "<parameter name=\"studyId\" value=\"52d02122-11c2-4b93-a1a7-a8bf4da0cf02\" />";
//		}
//	}
	
	/**
	 * Returns the xml parameter corresponding to the selected study
	 */
	private String writeTestMobile(Mobile mobile) {
			return "  <parameter name=\"ta.mobile.devicename\" value=\""+mobile.getName()+"\" />\r\n"
					+ "  <parameter name=\"ta.mobile.os\" value=\""+mobile.getOs()+"\" />\r\n"
					+ "  <parameter name=\"ta.mobile.os.version\" value=\""+mobile.getVersion()+"\" />";
	}
	
	/**
	 * returns the class string corresponding to the test name sent
	 */
	private String addTestClass(String test) {
		return "<class name=\"mobile.tests."+test+"Tests\" />";
	}
		
	/*
	 * *********************************************************************************************************************************************
	 * 
	 * CONSTRUCTOR
	 * 
	 * *********************************************************************************************************************************************
	 */
	/**
	 * Constructor used to launch an instance of the scenario generator.
	 * @param env
	 * @param tests
	 */
	public Scenarios(String env, ArrayList<String> tests, Mobile mobile) {
		super();
		this.env = env;
		this.tests = tests;
		this.mobile = mobile;
	}
}
