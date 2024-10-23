package org.sample.ssh.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class CMD extends JFrame {
	private static final long serialVersionUID = 1L; // for "Serializable" interface (JFrame) 
	
	private String userName;
	private String host; // or machine IP
    private String password;
    private int portNumber;

    private JTextField commandField;
    private JTextArea outputArea;
    
    private Session session;
    private ChannelShell channel;
    
	public CMD(String userName, String host, String password, int portNumber) {
		this.userName = userName;
		this.host = host;
		this.password = password;
		this.portNumber = portNumber;
    }

    public void init() {
        try {
        	// CMD System Interface 
        	this.setTitle("CMD System - Yunus");
        	
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(800, 500));
            panel.setLayout(new BorderLayout());
            
            outputArea = new JTextArea();
            outputArea.setBackground(Color.BLACK);
            outputArea.setForeground(Color.WHITE);
            JScrollPane scrollPane = new JScrollPane(outputArea);
            panel.add(scrollPane, BorderLayout.CENTER);
            
            commandField = new JTextField();
            commandField.setPreferredSize(new Dimension(panel.getWidth(), 40));
            commandField.setBackground(Color.LIGHT_GRAY);
            commandField.setForeground(Color.BLACK); 
            commandField.setFont(new Font("Arial", Font.PLAIN, 17));
            panel.add(commandField, BorderLayout.SOUTH);
            this.add(panel);
            
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.pack();
        	this.setLocationRelativeTo(null);
        	this.setVisible(true);
            
            // SSH Connection
            JSch jsch = new JSch();
            session = jsch.getSession(userName, host, portNumber);
            String passwd = password;
            
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            
            session.setPassword(passwd);
            session.connect(30 * 1000);
            System.out.println("Connected to " + host + "."); 

            channel = (ChannelShell) session.openChannel("shell");
            channel.setInputStream(new CustomInput(commandField));
            channel.setOutputStream(new CustomOutput(outputArea));
            channel.connect(3 * 1000);
            
            // Disconnection
            this.addWindowListener(new WindowAdapter() {
            	@Override
            	public void windowClosing(WindowEvent windowEvent) {
            		disconnect();
            		System.exit(0);
            	}
            });
            
        } catch (Exception exception) {
        	exception.printStackTrace();
        }       
    }
    
    
    private void disconnect() {
    	if(channel != null && channel.isConnected()) {
    		channel.disconnect();
    		System.out.println("Disconnected from channel.");
    	}
    	
    	if(session != null && session.isConnected()) {
    		session.disconnect();
    		System.out.println("Disconnected from session.");
    	}    		
    }
    
}
