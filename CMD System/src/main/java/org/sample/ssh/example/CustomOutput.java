package org.sample.ssh.example;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class CustomOutput extends OutputStream {
    private JTextArea outputArea;

    public CustomOutput(JTextArea outputArea) {
        this.outputArea = outputArea;
    }

    // Handles the writing of a single byte to the output stream.
    @Override
    public void write(int singleByte) throws IOException {
    	this.outputArea.append(String.valueOf((char) singleByte));
        scrollToBottom();
    }

    // Writes a subset of a byte array to the output stream.
    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException {
        String outputText = new String(buffer, offset, length, "UTF-8");
        this.outputArea.append(removeANSICodes(outputText));
        scrollToBottom();
    }

    // Writes the full byte array to the output stream.
    @Override
    public void write(byte[] buffer) throws IOException {
        write(buffer, 0, buffer.length);
    }
    
    // Scrolls the text area to the bottom.
    private void scrollToBottom() {
    	this.outputArea.setCaretPosition(outputArea.getText().length());
    }
    
    // Removes ANSI escape codes of the output.
    private String removeANSICodes(String text) {
    	// Regex to match ANSI escape sequences and OSC sequences.
    	String ansiPattern = "(\\u001B\\[[\\d;]*[^\\d;])|(\\u001B\\].*?\\u0007)";
    	return text.replaceAll(ansiPattern, "");
    }
    
}
