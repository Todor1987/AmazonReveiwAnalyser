package gui;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import utilities.Konsole;
import utilities.Url;

import javax.swing.JComboBox;

public class Gui {
	
	private static JTextField textField_urlAdress;
	public static String content;
	private static JTable table;
	private static int seiten;
	String columnNames[] = { "Column 1", "Column 2" };

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void gui() throws IOException, InterruptedException {

		final JFrame f = new JFrame("Amazon Reviews");
		f.setSize(800, 600);
		f.setLocation(300, 200);
		f.getContentPane().setLayout(null);

		JButton btnGetTextFrom = new JButton("Read URL");
		btnGetTextFrom.setBounds(698, 6, 85, 29);
		f.getContentPane().add(btnGetTextFrom);

		textField_urlAdress = new JTextField();
		textField_urlAdress.setBounds(16, 4, 606, 30);
		f.getContentPane().add(textField_urlAdress);
		textField_urlAdress.setColumns(10);
//		textField_urlAdress.setText("url eingeben:");
		
		textField_urlAdress.addFocusListener(new FocusListener() {
	        @Override
	        public void focusGained(FocusEvent e) {
	        	if(textField_urlAdress.getText().equals("URL eingeben:")){
	        		textField_urlAdress.setForeground(Color.BLACK);
	        		textField_urlAdress.setText("");
	        	}
	        }
	        @Override
	        public void focusLost(FocusEvent e) {
	        	if(textField_urlAdress.getText().isEmpty()){
	        		textField_urlAdress.setForeground(Color.GRAY);
	        		textField_urlAdress.setText("URL eingeben:");
	        	}
	        }
	    });

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 33, 777, 12);
		f.getContentPane().add(separator);
		final JTextArea textArea = new JTextArea();

		//Text area content
		final javax.swing.text.Document fileContent = textArea.getDocument();
		final String compareP[] = fileContent.toString().split(" ");
		String stringGood = "";
		int countGood = 0;
		String[] good = new String[2007];
		BufferedReader brGood = new BufferedReader(new FileReader("src/WordLists/positive-words.txt"));
		 while((stringGood = brGood.readLine()) != null) {
			 good[countGood] = stringGood;
			 countGood++;
		 }
		 
		 String stringBad = "";
			int countBad= 0;
			String[] bad = new String[4784];
			BufferedReader brBad = new BufferedReader(new FileReader("src/WordLists/negative-words.txt"));
			 while((stringBad = brBad.readLine()) != null) {
				 bad[countBad] = stringBad;
				 countBad++;
			 }
			 
			 //TODO: rowDate Array automatisch bef��llen lassen
		Object rowData[][] = { { "good", bad[1] }, { good[2], bad[2] }, { good[3], bad[3] }, { good[4], bad[4] }, { good[5], bad[5] }, { good[6], bad[6] }, { good[7], bad[7] }, { good[8], bad[8] }, { good[9], bad[9] }, { good[10], bad[10] }, };
		Object columnNames[] = { "Positive", "Negative" };

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(519, 74, 259, 377);
		f.getContentPane().add(scrollPane);

		table = new JTable(rowData, columnNames);
		scrollPane.setViewportView(table);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);

		JLabel lblNewLabel = new JLabel("Reviews");
		lblNewLabel.setBackground(new Color(255, 255, 0));
		lblNewLabel.setBounds(16, 46, 490, 16);
		f.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(16, 74, 491, 377);
		f.getContentPane().add(scrollPane_1);

		textArea.setEditable(false);
		scrollPane_1.setViewportView(textArea);
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					System.out.println(table.getValueAt(row, column));
					String searchText = table.getValueAt(row, column).toString();

					textArea.getHighlighter().removeAllHighlights();

					try {

						for (int i = 0; i + searchText.length() < fileContent.getLength(); i++) {

							String match = fileContent.getText(i,searchText.length());

							if (searchText.equals(match)) {

								System.out.println("!");
								javax.swing.text.DefaultHighlighter.DefaultHighlightPainter highlightPainter = new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
								textArea.getHighlighter().addHighlight(i,i + searchText.length(),highlightPainter);
							}
						}
					} catch (Exception ex) {

						ex.printStackTrace();
					}
				}
			}
		});

		JLabel label = new JLabel("Sentiment Table");
		label.setBackground(Color.YELLOW);
		label.setBounds(518, 46, 259, 16);
		f.getContentPane().add(label);

		String comboBoxListe[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

		final JComboBox comboBox = new JComboBox(comboBoxListe);
		comboBox.setBounds(628, 7, 72, 27);
		
		f.getContentPane().add(comboBox);
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				seiten = Integer.parseInt((String)comboBox.getSelectedItem());
			}
		});
		
		btnGetTextFrom.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
//				int seiten = Integer.parseInt(textField_seitenZahl.getText());

				String link = textField_urlAdress.getText();
				try {
					Konsole.control(link, seiten);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textArea.setText(content);
			}
		});
		
		f.setVisible(true);
		
		// Inhalt aus Ordner Amazon/scr/texts löschen, Dateien beibehalten
		f.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	JCheckBox deleteContent = new JCheckBox("Delete file content?");
		    	
		        if (JOptionPane.showConfirmDialog(f, deleteContent, "Exit?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		        	
		        	if(deleteContent.isSelected()){
						try {
							FileOutputStream writer = new FileOutputStream("src/texts/Quelltext.txt");
							writer.close();
							FileOutputStream writer1 = new FileOutputStream("src/texts/reviews.txt");
							writer1.close();
			        		FileOutputStream writer2 = new FileOutputStream("src/texts/reviewsList.txt");
			        		writer2.close();
			        		System.exit(0);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		
		        	}
		        	else
		            System.exit(0);
		        }
		    }
		});
	}
}
