package org.sample.ssh.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;

public class CustomInput extends InputStream implements ActionListener {
    private final JTextField cmdField;
    private final List<String> commands;
    
    private String command;
    private int position;

    public CustomInput(JTextField cmdField) {
        this.cmdField = cmdField;
        commands = new ArrayList<String>();
        cmdField.addActionListener(this);
    }

    // Reads the next character from the command entered by the user.
    @Override
    public synchronized int read() throws IOException {
        while(command == null || command.length() <= position) {
            waitForCommand();
			position = 0;
        }
        return command.charAt(position);
    }
    
    // Waits for a new command to be entered by the user.
    private synchronized void waitForCommand() {
    	while(commands.size() == 0) {
    		try {
				this.wait(); // Waits until a command is added.
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
    	}
    	command = commands.remove(0);
    }

    
    // Reads multiple characters (bytes) from the command entered by the user and stores them.
    public int read(byte[] buffer, int offset, int length) throws IOException {
        int bytesCopied = 0;
        while(bytesCopied < 1) {
            if(command == null || command.length() <= position) {
            	waitForCommand();
				position = 0;
            }
            int bytesToCopy = Math.min(length, command.length() - position);
            System.arraycopy(command.getBytes(), position, buffer, offset, bytesToCopy);
            position += bytesToCopy;
            bytesCopied += bytesToCopy;
        }
        return bytesCopied;
    }

    
    /*  
     * The "synchronized" keyword ensures that only one thread at a time can access shared resources 
     * like the "commands" array list and "command" variable. 
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
    	synchronized(this) {
    		commands.add(cmdField.getText() + "\r\n");
    		cmdField.setText("");
    		this.notifyAll();
    	}
    }

}
