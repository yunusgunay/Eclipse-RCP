package org.example.ssh.channel.exec;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Channel_Exec {
    public static void main( String[] args ) {   	    	   	
        final JSch jSch = new JSch(); // Java Secure Channel, provides API to create and manage SSH connections
        final Session[] session = {null}; // Represents a single SSH connection to a remote server

        // Interface
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
        JLabel ip = new JLabel("Host:", JLabel.CENTER);
        JLabel passw = new JLabel("Password:", JLabel.CENTER);
        JLabel port = new JLabel("Port Number:", JLabel.CENTER);
        	
        JTextField jText_user = new JTextField();
        jText_user.setBackground(Color.LIGHT_GRAY);
        jText_user.setHorizontalAlignment(JTextField.CENTER);
        
        JTextField jText_ip = new JTextField();
        jText_ip.setBackground(Color.LIGHT_GRAY);
        jText_ip.setHorizontalAlignment(JTextField.CENTER);
        
        JTextField jText_passw = new JTextField();
        jText_passw.setBackground(Color.LIGHT_GRAY);
        jText_passw.setHorizontalAlignment(JTextField.CENTER);
        
        JTextField jText_port = new JTextField();
        jText_port.setBackground(Color.LIGHT_GRAY);
        jText_port.setHorizontalAlignment(JTextField.CENTER);
        	
        JButton button = new JButton("Connect >");
        button.setBackground(Color.PINK);
        	       	
        panel.add(user);
        panel.add(jText_user);
        panel.add(ip);
        panel.add(jText_ip);
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
        			String machineIP = jText_ip.getText();
        		    String password = jText_passw.getText();
        		    int portNumber = Integer.parseInt(jText_port.getText());
        		    		
        		    // SSH Connection
        	        session[0] = jSch.getSession(userName, machineIP, portNumber);
        	        session[0].setPassword(password); 
        	        	    	
        	        Properties config = new Properties();
        	        config.put("StrictHostKeyChecking", "no");
        	        session[0].setConfig(config); 
        	        	
        	        session[0].connect();
        	        System.out.println("Connected to the remote server.");
        	        	
        	        openCMD(session[0], jSch);        	        	
        	        
        	        frame.dispose(); 
        		
        		} catch (Exception exception) {
        			exception.printStackTrace();
        		}
        	}
        	
        	// Opens CMD screen after Connect Button
        	private void openCMD(Session session, JSch jSch) {
        		JFrame cmdFrame = new JFrame();
        		cmdFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        		cmdFrame.setSize(600, 400);
        		cmdFrame.setTitle("CMD System");
        		cmdFrame.setLocationRelativeTo(null);
        		cmdFrame.setLayout(null);

                JTextArea output = new JTextArea();
                output.setEditable(false);
                output.setBackground(Color.BLACK);
                output.setForeground(Color.WHITE);
                
                JScrollPane scrollPane = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setBounds(10, 10, 560, 250);

                JTextField cmdField = new JTextField();
                cmdField.setBounds(10, 270, 460, 30);
                cmdField.setBackground(Color.LIGHT_GRAY);

                JButton sendButton = new JButton("Run >");
                sendButton.setBounds(480, 270, 90, 30);
                sendButton.setBackground(Color.PINK);

                cmdFrame.add(scrollPane);
                cmdFrame.add(cmdField);
                cmdFrame.add(sendButton);       
                cmdFrame.setVisible(true);

                // Run Button
                sendButton.addActionListener(new ActionListener() {
                	@Override
                	public void actionPerformed(ActionEvent e) {
                		try {
                			String command = cmdField.getText();
                			sendCommand(command, session, output); 
                			cmdField.setText(""); // Clear input after sending command.
                		} catch (Exception exception) {
                			exception.printStackTrace();
                		}
                	}
                });

                // Enter key press
                cmdField.addActionListener(new ActionListener() {
            	   @Override
            	   public void actionPerformed(ActionEvent e) {
            		   try {
            			   String command = cmdField.getText();
            			   sendCommand(command, session, output);
            			   cmdField.setText(""); // Clear input after sending command.
            		   } catch (Exception exception) {
            			   exception.printStackTrace();
            		   }
            	   }
               });
               
        	}

        	
        	// Send command to SSH
        	private void sendCommand(String command, Session session, JTextArea outputArea) {
        		Thread commandThread = new Thread(() -> { 
        	        ChannelExec channel = null; 
        	        try {
        	            channel = (ChannelExec) session.openChannel("exec");
        	            channel.setCommand(command);
        	            
        	            InputStream in = channel.getInputStream();
        	            InputStream err = channel.getErrStream();

        	            // Timeout for unrecognized & failed commands
        	            channel.connect(3 * 1000);

        	            byte[] buffer = new byte[1024];
        	            int count;
        	            StringBuilder output = new StringBuilder();
        	            StringBuilder error = new StringBuilder();
        	            
        	            while((count = in.read(buffer)) > 0) {
        	                output.append(new String(buffer, 0, count));
        	            }
        	            
        	            while((count = err.read(buffer)) > 0) {
        	                error.append(new String(buffer, 0, count));
        	            }
        	            
        	            int exitStatus = channel.getExitStatus();
        	            outputArea.append("Command: " + command + "\n");
        	            if(exitStatus == 0) {
        	            	outputArea.append(output.toString() + "\n");
        	            } else {
        	            	outputArea.append("Error: Unrecognized or failed command.\n\n");
        	            }
        	            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        	        } 
        	        
        	        catch (Exception exception) {
        	        	outputArea.append("Error: Unrecognized or failed command: " + command + "\n\n");
        	        	outputArea.setCaretPosition(outputArea.getDocument().getLength());
        	        } 
        	        
        	        finally {
        	            if(channel != null && channel.isConnected()) {
        	                channel.disconnect();
        	            }
        	        }
        	    });
        	    
        	    commandThread.start();
        	}
        	
        		
        });  
        
    }   
}
