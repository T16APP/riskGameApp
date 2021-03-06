package com.risk.controller;

import com.risk.model.Continent;
import com.risk.model.Country;
import com.risk.model.Edge;
import com.risk.model.GameBoard;

import com.risk.utility.MapParser;
import com.risk.utility.staticApplicationVariables;

import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
//import org.jgraph.graph.GraphLayoutCache;

import com.risk.view.applicationWindow;

/**
 * An object of this class listens to presses of the "Open" menu item of the
 * "File" menu of the main user interface. It's responsible for requesting the
 * user for a file to open, and then tries to open it and display it in the main
 * user interface. author
 */
public class OpenListener implements ActionListener {
	/**
	 * Used to gain access to the globally shared graph, which needs to be
	 * altered in this class.
	 */
	private applicationWindow gui;

	/**
	 * Constructor. Initializes the variable that references the gui.
	 */
	public OpenListener() {
		gui = applicationWindow.getInstance();
	}

	/**
	 * Causes a new window to Pop-up. This window then asks the user to indicate
	 * the Continent name, the Control value. The control value must be set as
	 * Integer
	 * 
	 * @param actionEvent
	 *            Not used.
	 */

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		JFileChooser chooser = new JFileChooser();

		// Doesn't allow the user to select more than one file.
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// Allows the user to select files with the map extension only.
		FileNameExtensionFilter mapFileFilter = new FileNameExtensionFilter("Map files", "map");
		chooser.setFileFilter(mapFileFilter);
		int selection = chooser.showOpenDialog(gui.getContentPane());

		// If the user has selected a file.
		if (selection != JFileChooser.CANCEL_OPTION) {
			try {
				File openFromFile = chooser.getSelectedFile();
				System.out.println();
				staticApplicationVariables.FILENAME = openFromFile.getPath();
				GameBoard gameboard = GameBoard.GetGameBoard();
				// MapParser mp = new MapParser();
				gameboard.map = MapParser.MapParser(staticApplicationVariables.FILENAME);
				staticApplicationVariables.gb = gameboard;
				MapParser.WriteMapToFile(gameboard.map, "output.txt");

				/*
				 * Gives the user a chance to save the work he's presently
				 * working on. If the user chooses to proceed, all of his/her
				 * work is lost forever.
				 */
				int userResponse = JOptionPane.showConfirmDialog(gui.getContentPane(),
						"If you open this file without saving the one you're\n"
								+ "already working on, all of your work will be lost.\n"
								+ "Click yes if you don't want to save your work.\n",
						"Open Without Saving Confirmation", JOptionPane.YES_NO_OPTION);
				if (userResponse == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(gui.getContentPane(), "File has been Uploaded ", "MESSAGE",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(gui.getContentPane(), "Unable to Upload Map file", "MESSAGE",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (FileNotFoundException fnfe) {
				JOptionPane.showMessageDialog(gui.getContentPane(), fnfe.getMessage(), "ERROR",
						JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
