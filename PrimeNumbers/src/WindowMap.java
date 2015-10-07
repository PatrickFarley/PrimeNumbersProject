import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * The WindowMap class is responsible for displaying a GUI window, checking user inputs, 
 * calculating all prime numbers in the given range, and displaying them.
 * @author Patrick D. Farley
 */
public class WindowMap {
	
	// these objects must belong to the class object itself
	JTextField lowT;
	JTextField highT;
	JTextArea answerT;
	JButton calc;


	/**
	 * Public constructor: responsible for creating and displaying the initial GUI of the program.
	 */
	public void WindowMapCreate() {
		JFrame window = new JFrame(); // the frame
		window.setMinimumSize(new Dimension(300,190)); // set a minimum resize limit
		window.setLocationRelativeTo(null);

		JPanel top = new JPanel(); // panel to contain text and text fields

		JPanel directionsP = new JPanel(); // panel to contain directions
		JLabel directionsL = new JLabel("Enter a range and click \"Find Prime Numbers\""); // app directions
		directionsP.add(directionsL,BorderLayout.PAGE_START); // add directionsL to the top of its panel
		directionsP.setBorder(new EmptyBorder(10,10,10,10)); // give a buffer of 10 units each side

		JPanel vals = new JPanel(); // panel to contain text fields
		
		JPanel highP = new JPanel(); // panel to contain high text field and its label
		highP.setBorder(new EmptyBorder(0,20,0,20)); // give buffer on sides
		JPanel lowP = new JPanel(); // panel to contain low text field and its label
		lowP.setBorder(new EmptyBorder(0,20,0,20)); // give buffer on sides

		
		JLabel fromL = new JLabel("From"); // label for low val
		lowT = new JTextField(3); // text field for low val
		JLabel toL = new JLabel("To"); // label for high val
		highT = new JTextField(3); // text field for high val
		
		// add high and low val components to their panels
		lowP.add(fromL,BorderLayout.LINE_START); 
		lowP.add(lowT,BorderLayout.LINE_END);
		highP.add(toL,BorderLayout.LINE_START);
		highP.add(highT,BorderLayout.LINE_END);
		
		// add the high and low panels to vals panel
		vals.setLayout(new BorderLayout());
		vals.add(lowP,BorderLayout.LINE_START);
		vals.add(highP,BorderLayout.LINE_END);
		
		// panel to hold calculating button
		JPanel calcP = new JPanel();
		
		// create calculating button and its actionlistener
		calc = new JButton("Find Prime Numbers");
		calc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculate();
			}
		});
		
		// add calculating button to its panel
		calcP.add(calc,BorderLayout.CENTER);

		// add directions panel, vals panel, and calc panel to top panel
		top.setLayout(new BorderLayout());
		top.add(directionsP,BorderLayout.PAGE_START);
		top.add(vals, BorderLayout.CENTER);
		top.add(calcP, BorderLayout.PAGE_END);

		JPanel answerP = new JPanel(); // create panel for answer text field
		answerP.setBorder(new EmptyBorder(5,5,5,5)); // give panel a buffer on every side
		
		// create text area for answer
		answerT = new JTextArea(0,27);
		answerT.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		answerT.setLineWrap(true);
		answerT.setWrapStyleWord(true);
		
		// create a scroll pane around the text area
		JScrollPane answerSP = new JScrollPane(answerT);
		
		// add scroll pane to its panel
		answerP.setLayout(new BorderLayout());
		answerP.add(answerSP,BorderLayout.CENTER);
		
		// add top panel, button panel, and answer panel to frame, in order
		window.getContentPane().add(top,BorderLayout.PAGE_START);		
		window.getContentPane().add(answerP,BorderLayout.CENTER);

		window.pack(); // size window appropriately
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program quits on window close
		
	}
	
	
	/**
	 * method for calculating prime numbers in the specified range.
	 */
	public void calculate(){
		int lowVal,highVal;
		String answer = "";
		
		// check that the entered values are ints:
		try {
		    lowVal = Integer.parseInt(lowT.getText());
		    highVal = Integer.parseInt(highT.getText());
		} catch (NumberFormatException e) {
		    answerT.setText("Enter positive integers in the high and low fields.");
		    return;
		}
	
		// make sure lowVal is actually the lower value:
		if (lowVal > highVal) {
			int temp = lowVal;
			lowVal = highVal;
			highVal = temp;
		}	
		// for primes: lowest possible value must be 2:
		if (lowVal < 2)
			lowVal = 0;
		if (highVal < 2) 
			highVal = 0;
		
		// create a working boolean array from 0 to highVal
		boolean[] all = new boolean[highVal+1];
		for(int i = 2; i<=highVal;i++){
			all[i] = true;
		}
		
		// begin Sieve of Eratosthenes method:
		// for each index in all:
		for(int i=2;i*i<=highVal;i++) {
			
			if(all[i]) {
				// if the value is still recorded as a prime (has 'true' value),
				// then go through all its multiples and mark them 'false': they are not primes
				// only do multiples up until we are past highVal
				// and the first number whose square > highVal will skip the loop entirely
				for (int j=i;i*j<=highVal;j++) 
					// the given multiples are recorded as not prime
					all[j*i]=false;
			}
		}
		
		// chop off beginning numbers based on the lowVal given.
		for(int i=2;i< lowVal;i++){
			all[i]=false;
		}
		
		
		// print the remaining true indices to a string
		for (int i =2;i<=highVal;i++) {
		    if (all[i])
		    	answer += i + " ";
		}
		answerT.setText(answer); // print answer string to the text area.
	}
	
}
