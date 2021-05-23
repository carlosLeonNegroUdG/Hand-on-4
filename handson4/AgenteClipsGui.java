package examples.handson4;

import jade.core.AID;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
  @author Giovanni Caire - TILAB
 */
class AgenteClipsGui extends JFrame {	
	private AgenteClips myAgent;
	
	private JTextField hechosField, reglasField;
	
	AgenteClipsGui(AgenteClips a) {
		super(a.getLocalName());
		
		myAgent = a;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 2));
		p.add(new JLabel("Hechos:"));
		hechosField = new JTextField(100);
		p.add(hechosField);
		p.add(new JLabel("Reglas:"));
		reglasField = new JTextField(100);
		p.add(reglasField);
		getContentPane().add(p, BorderLayout.CENTER);
		
		JButton addButton = new JButton("Agregar a clips");
		addButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String hechos = hechosField.getText().trim().toString();
					String reglas = reglasField.getText().trim().toString();
					myAgent.actualizacionDeClips(hechos,reglas);
					hechosField.setText("");
					reglasField.setText("");
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(AgenteClipsGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );
		p = new JPanel();
		p.add(addButton);
		getContentPane().add(p, BorderLayout.SOUTH);
		
		// Make the agent terminate when the user closes 
		// the GUI using the button on the upper right corner	
		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		} );
		
		setResizable(false);
	}
	
	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}	
}
