package run.configurator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import org.testng.TestNG;
import javax.swing.*;

public class FormFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	// dataset and collectors variables
	String[] activities;
	String[] env;
	ArrayList<Mobile> mobile;
	String generatedFile;
    ArrayList<JComboBox<String>> testsList = new ArrayList<JComboBox<String>>();
	// windows elements
    JPanel centerPanel = new JPanel();
    JPanel validate = new JPanel();
    JComboBox<String> envSelect;
    JComboBox<String> mobileSelect;
	
    //************************************************************
    // 
    //
    //	MAIN METHODS OF THE CLASS
    //
    //
    //************************************************************
	/**
	 * Initializes the window interface which allow to custom a XML testng file.
	 */
	public void initialize() {
		setMinimumSize(new Dimension(900,600));
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocation(500, 250);
	    
	    // Labels declarations
	    JLabel lblEnv = new JLabel("Select Your environment");
	    JLabel lblMob = new JLabel("Select Your mobile");
	    // Select boxes declarations
	    envSelect = new JComboBox<String>(env);
	    mobileSelect = new JComboBox<String>(getStringMobiles());

	    //********************************************************
	    // Panel composition
	    //********************************************************
	    
	 // Add the environment select box
	    JPanel panelFix = new JPanel();
	    panelFix.setLayout(new GridLayout(2, 2));
	    JPanel windowOrganizer = new JPanel();
	    windowOrganizer.setLayout(new BorderLayout(10,10));
	    windowOrganizer.add(panelFix, BorderLayout.NORTH);
	    add(windowOrganizer);
	    panelFix.add(lblEnv);
	    panelFix.add(envSelect);
	    panelFix.add(lblMob);
	    panelFix.add(mobileSelect);
	    panelFix.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    
	    // Add the Test selection part
	    JLabel lblTest = new JLabel("Select your test cases");
	    JButton nextTestBtn = new JButton("Add");
	    centerPanel.add(lblTest);
	    // Button to add a new test in the list
	    centerPanel.add(nextTestBtn);
		nextTestBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTestSelector();
			}
			
		});
	    addTestSelector();
	    windowOrganizer.add(centerPanel, BorderLayout.CENTER);

	    // Add the validation button
	    JButton validationBtn = new JButton("Validate");
	    validate.add(validationBtn);
	    JLabel applicatiVersion = new JLabel("Build version 1.2.0");
	    validate.add(applicatiVersion);
	    windowOrganizer.add(validate, BorderLayout.SOUTH);
	    validationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				collectScenario();
			}});
	  //Display the window.
	    setVisible(true);
	}
	
	/**
     * Launch the creation of the custom testngXML
     */
    public void collectScenario() {
    	String scenarioEnv = collectEnv();
    	Mobile scenarioMobile = mobile.get(collectMob());
    	ArrayList<String> tests = collectTests();
    	Scenarios customScenario = new Scenarios(scenarioEnv, tests, scenarioMobile);
    	try {
    		// Test of the collect and order of the tests.
    		System.out.println(scenarioEnv);
    		System.out.println(scenarioMobile);
    		for (int i = 0; i < tests.size(); i++) {
        		System.out.println(tests.get(i));
    		}
    		// Generation of the XML
    		generatedFile = customScenario.generateXML().getFileName().toString();
			System.out.println(generatedFile);
        	System.out.println("XML generated");	
        	// Generation of the executor button
        	addExecuterButton();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * Executes the generated testng file
     */
    private void executeScenario() {
    	TestNG runner=new TestNG();

    	// The runner need a list of String to run, not a single string.
    	ArrayList<String> suitefiles=new ArrayList<String>();
    	suitefiles.add(generatedFile);
    	runner.setTestSuites(suitefiles);
    	// GO
    	System.out.println("Run of the created xml:");	
    	runner.run();
    }
	
	//**************************************
	//
	// NEW ELEMENTS METHODS
	//
	//**************************************
	/**
	 * Generates a new select box to add a test 
	 */
	private void addTestSelector() {
	    JComboBox<String> testSelect = new JComboBox<String>(activities);
	    testsList.add(testSelect);
	    centerPanel.add(testSelect);
	    setVisible(true);
	}
	/**
	 * Generates the "execute" button on the window
	 */
	private void addExecuterButton() {
		JButton executeBtn = new JButton("Execute");
	    validate.add(executeBtn);
	    setVisible(true);
	    executeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeScenario();
			}});
	}
	
	//**************************************
	//
	// COLLECTOR METHODS
	//
	//**************************************
	/**
	 * returns the string item selected on the environment combobox 
	 */
    private String collectEnv() {
    	return envSelect.getItemAt(envSelect.getSelectedIndex());
    }
    /**
	 * returns the string item selected on the mobile combobox 
	 */
    private int collectMob() {
    	return mobileSelect.getSelectedIndex();
    }
    /**
	 * returns the string items selected from each test combobox 
	 */
    private ArrayList<String> collectTests(){
    	ArrayList<String> tests = new ArrayList<String>();
    	for(int i = 0; i < testsList.size(); i++){
    		tests.add(testsList.get(i).getItemAt(testsList.get(i).getSelectedIndex()));
    	}
    	return tests;
    }
    
    /**
     * Transform the list of Mobile into an array of toString() of the mobile List to display them in the UI
     * @return
     */
    private String[] getStringMobiles() {

    	ArrayList<String> mobiles = new ArrayList<String>();
    	String currentMobile;
    	for (int i = 0; i< mobile.size(); i++) {
    		currentMobile = mobile.get(i).toString();
    		mobiles.add(currentMobile);
    	}
		return mobiles.toArray(new String[0]);
	}
	
	//**************************************
	//
	// CONSTRUCTOR
	//
	//**************************************
	/**
	 * Complete constructor
	 * @param string
	 * @param activityStrings
	 * @param env
	 * @param mobile
	 */
    public FormFrame(String string, ArrayList<String> activityStrings, ArrayList<String> env, ArrayList<Mobile> mobile) {
    	super.setTitle(string);
    	this.activities = activityStrings.toArray(new String[0]);
    	this.env = env.toArray(new String[0]);
    	this.mobile = mobile;
	}
}
