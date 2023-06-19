package run.configurator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//import java.util.List;
//import mobile.tests.*;

public class Runner {
	
	final private static File xmlFile = new File("env_integration//integration.xml");
	
	/**
	 * Main of the Java Application
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
//		MobileBaseTest baseTest = new MobileBaseTest();
//		baseTest.before();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
		});
	}
	
	/**
	 * Creates the user interface of the java application
	 * need to be modify to make easier maintenance. The environment, mobile and test lists should be written in separate text files.
	 */
	private static void createAndShowGUI() {
		
		// List of existing activities
		try{
			ArrayList<String> activityStrings = getActivities();
			ArrayList<String> environmentStrings = getEnvironments();
			ArrayList<Mobile> mobileStrings = getMobiles();
			
	        //Create and set up the window.
			FormFrame window = new FormFrame("Config Selector", activityStrings, environmentStrings, mobileStrings);
			window.initialize();
		}catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	

	//**************************************************************************************************************************************************
	// 
	//	EXTRACTION METHODS FOR INTEGRATION DATAS
	//
	//**************************************************************************************************************************************************

	/**
	 * extracts the mobile datas from the integration.xml file
	 * @return
	 */
	private static ArrayList<Mobile> getMobiles() {
		ArrayList<Mobile> availableDevices = new ArrayList<Mobile>();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			// read XML
			NodeList parameters = doc.getElementsByTagName("device");
			Element gotParameter = null;
			
			for (int i = 0; i < parameters.getLength(); i++) {
				gotParameter = (Element) parameters.item(i);
				String current_name = gotParameter.getAttribute("name");
				String current_os = gotParameter.getAttribute("os");
				String current_version = gotParameter.getAttribute("version");
				
				Mobile currentElement = new Mobile(current_name, current_os, current_version);
				
				availableDevices.add(currentElement);
				}
			
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
			System.out.println("Mobiles availables : "+ availableDevices.size());
			return availableDevices;
	}

	/**
	 * extracts the environment datas from the integration.xml file
	 * @return
	 */
	private static ArrayList<String> getEnvironments() {
		return getXMLElements("environment");
	}

	/**
	 * extracts the activities datas from the integration.xml file
	 * @return
	 */
	private static ArrayList<String> getActivities() {
		return getXMLElements("testcase");
	}
	
	
	private static ArrayList<String> getXMLElements(String node){
		ArrayList<String> askedElements = new ArrayList<String>();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			// read XML
			NodeList parameters = doc.getElementsByTagName(node);
			Element gotParameter = null;
			
			for (int i = 0; i < parameters.getLength(); i++) {
				gotParameter = (Element) parameters.item(i);
				String current_name = gotParameter.getAttribute("value");
				askedElements.add(current_name);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
			System.out.println("Elements read : "+ askedElements.size());
			return askedElements;
	}

	//**************************************************************************************************************************************************
	// 
	//	Prospection for a direct run without use of testng xml file => unused until futures developments
	//
	//**************************************************************************************************************************************************
//	List<String> scenario;
//	String versionNumber;
//	
//	/**
//	 * Prospection to a possible future version where the Interface directly run the custom scenario without the creation of a testng XML
//	 * @param scenario
//	 * @throws Exception
//	 */
//	protected void peekScenario(String scenario) throws Exception {
//		switch (scenario){
//			case "pinch":
//				PinchBalloonsTests pinch = new PinchBalloonsTests();
//				pinch.pinchBalloonsActivityCompletedSuccessfully();
//				break;
//			case "draw":
//				DrawingTests draw = new DrawingTests();
//				draw.drawingActivityCompletedSuccessfully();
//				break;
//		}
//	}
//
//	/**
//	 * Launches the scenario after triggered by a listener
//	 * @throws Exception
//	 */
//	void executeButton() throws Exception {
//		for (int i=0; i<scenario.size();i++) {
//			peekScenario(scenario.get(i));
//		}
//	}
}
