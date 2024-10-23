package org.sample.ssh.example;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SSH {
    public static void main( String[] args ) {   	    	   	
        // User Interface
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setTitle("Connect Remote Machine - Yunus");
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        	
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBounds(40, 75, 300, 200);
        	
        JLabel user = new JLabel("Username:", JLabel.CENTER);
        JLabel host = new JLabel("Host:", JLabel.CENTER);
        JLabel passw = new JLabel("Password:", JLabel.CENTER);
        JLabel port = new JLabel("Port Number:", JLabel.CENTER);
        	
        JTextField jText_user = new JTextField("");
        jText_user.setBackground(Color.LIGHT_GRAY);
        jText_user.setHorizontalAlignment(JTextField.CENTER);
        
        JTextField jText_host = new JTextField("");
        jText_host.setBackground(Color.LIGHT_GRAY);
        jText_host.setHorizontalAlignment(JTextField.CENTER);
        
        JPasswordField jText_passw = new JPasswordField("");
        jText_passw.setBackground(Color.LIGHT_GRAY);
        jText_passw.setHorizontalAlignment(JTextField.CENTER);
        
        JTextField jText_port = new JTextField("");
        jText_port.setBackground(Color.LIGHT_GRAY);
        jText_port.setHorizontalAlignment(JTextField.CENTER);
        	
        JButton button = new JButton("Connect >");
        button.setBackground(Color.PINK);
        	       	
        panel.add(user);
        panel.add(jText_user);
        panel.add(host);
        panel.add(jText_host);
        panel.add(passw);
        panel.add(jText_passw);
        panel.add(port);
        panel.add(jText_port);
        panel.add(button);
        	
        frame.add(panel);
        frame.setVisible(true);
        	
        // Connect Button
        button.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		try {
        		    String userName = jText_user.getText();
        			String machineIP = jText_host.getText();
        		    String password = new String (jText_passw.getPassword());
        		    int portNumber = Integer.parseInt(jText_port.getText());
        		    		
        		    // Opens CMD System
        	        CMD cmd = new CMD(userName, machineIP, password, portNumber);
        			frame.setVisible(false);
        	        cmd.setVisible(true);
        	        cmd.init();
        	               		
        		} catch (Exception exception) {
        			exception.printStackTrace();
        		}
        	}       		
        });  
       
    }   
}
