package com.niketica.gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;

import javax.swing.JTextArea;

/**
 * The DisplayField is basically a JTextArea with a few preset settings.
 * @author Alexander
 *
 */
public class DisplayField extends JTextArea {
	private static final long serialVersionUID = 1L;

	public DisplayField(Font font){
		setBackground(Color.WHITE);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		setEditable(false);
		setFont(font);
	}
}
