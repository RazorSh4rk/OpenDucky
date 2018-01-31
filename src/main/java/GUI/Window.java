package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;

import Encoder.Encoder;

import javax.swing.JPanel;
import javax.swing.JEditorPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Window {

	private JFrame frmOpenduckyIde;

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOpenduckyIde = new JFrame();
		frmOpenduckyIde.setTitle("OpenDucky IDE");
		frmOpenduckyIde.setMinimumSize(new Dimension(400, 600));
		frmOpenduckyIde.getContentPane().setFont(new Font("Monospaced", Font.PLAIN, 12));
		frmOpenduckyIde.getContentPane().setBackground(Color.DARK_GRAY);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBorderPainted(false);
		toolBar.setAlignmentY(0.5f);
		toolBar.setBackground(Color.GRAY);
		frmOpenduckyIde.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnFile = new JButton("file");
		btnFile.setBorderPainted(false);
		btnFile.setBackground(Color.LIGHT_GRAY);
		btnFile.setFont(new Font("Monospaced", Font.PLAIN, 11));
		toolBar.add(btnFile);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setMinimumSize(new Dimension(10, 0));
		horizontalStrut.setPreferredSize(new Dimension(10, 0));
		toolBar.add(horizontalStrut);
		
		JButton btnHowTo = new JButton("how to");
		btnHowTo.setBorderPainted(false);
		btnHowTo.setBackground(Color.LIGHT_GRAY);
		btnHowTo.setFont(new Font("Monospaced", Font.PLAIN, 11));
		toolBar.add(btnHowTo);
		
		final JButton btnSave = new JButton("save");
		btnSave.setBorderPainted(false);
		btnSave.setFont(new Font("Monospaced", Font.PLAIN, 11));
		btnSave.setBackground(Color.LIGHT_GRAY);
		btnSave.setVisible(false);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut_1);
		toolBar.add(btnSave);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut_2);
		
		final JButton btnLoad = new JButton("load");
		btnLoad.setBorderPainted(false);
		btnLoad.setVisible(false);
		btnLoad.setFont(new Font("Monospaced", Font.PLAIN, 11));
		btnLoad.setBackground(Color.LIGHT_GRAY);
		toolBar.add(btnLoad);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut_3);
		
		final JButton btnGenerateSource = new JButton("generate source");
		btnGenerateSource.setVisible(false);
		btnGenerateSource.setFont(new Font("Monospaced", Font.PLAIN, 11));
		btnGenerateSource.setBackground(Color.LIGHT_GRAY);
		btnGenerateSource.setBorderPainted(false);
		toolBar.add(btnGenerateSource);
		
		JScrollPane scrollPane = new JScrollPane();
		frmOpenduckyIde.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		final JEditorPane codeArea = new JEditorPane();
		codeArea.setForeground(Color.GREEN);
		codeArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		codeArea.setCaretColor(Color.WHITE);
		codeArea.setBackground(Color.DARK_GRAY);
		
		btnFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnSave.setVisible(!btnSave.isVisible());
				btnLoad.setVisible(!btnLoad.isVisible());
				btnGenerateSource.setVisible(!btnGenerateSource.isVisible());
			}
		});
		
		btnGenerateSource.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String code = (Encoder.beginFile() + Encoder.encode(codeArea.getText()) + Encoder.endFile());
				boolean success = (new File("./OpenDuckyScript")).mkdirs();
				if (!success) {
				    // Directory creation failed
				}
				try{					
				    PrintWriter writer = new PrintWriter("./OpenDuckyScript/OpenDuckyScript.ino", "UTF-8");
				    writer.print(code);
				    writer.close();
				} catch (IOException e) {
				   e.printStackTrace();
				}
			}
		});
		
		scrollPane.setViewportView(codeArea);
		frmOpenduckyIde.setBackground(Color.DARK_GRAY);
		frmOpenduckyIde.setBounds(100, 100, 450, 300);
		frmOpenduckyIde.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOpenduckyIde.setVisible(true);
	}

}
