package gui;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.Highlighter.HighlightPainter;

import utilities.Konsole;

public class Gui {

	private static JTextField textField_urlAdress;
	public static String content;
	private static int seiten;
	public static List<String> reviewList = new ArrayList<String>();
	public static List<String> words = new ArrayList<String>();
	String columnNames[] = { "Column 1", "Column 2" };

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void gui() throws IOException, InterruptedException {

		final JFrame f = new JFrame("Amazon Reviews");
		f.setSize(1173, 772);
		f.setLocation(300, 200);
		f.getContentPane().setLayout(null);

		JButton btnGetTextFrom = new JButton("Read URL");
		btnGetTextFrom.setBounds(1064, 6, 85, 29);
		f.getContentPane().add(btnGetTextFrom);

		textField_urlAdress = new JTextField();
		textField_urlAdress.setBounds(16, 6, 606, 30);
		f.getContentPane().add(textField_urlAdress);
		textField_urlAdress.setColumns(10);
		// textField_urlAdress.setText("url eingeben:");

		textField_urlAdress.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textField_urlAdress.getText().equals("URL eingeben:")) {
					textField_urlAdress.setForeground(Color.BLACK);
					textField_urlAdress.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (textField_urlAdress.getText().isEmpty()) {
					textField_urlAdress.setForeground(Color.GRAY);
					textField_urlAdress.setText("URL eingeben:");
				}
			}
		});

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 33, 777, 12);
		f.getContentPane().add(separator);
		String stringGood = "";
		int countGood = 0;
		final String[] good = new String[2007];
		BufferedReader brGood = new BufferedReader(new FileReader("src/WordLists/positive-words.txt"));
		while ((stringGood = brGood.readLine()) != null) {
			good[countGood] = stringGood;
			countGood++;
		}

		String stringBad = "";
		int countBad = 0;
		final String[] bad = new String[4784];
		BufferedReader brBad = new BufferedReader(new FileReader("src/WordLists/negative-words.txt"));
		while ((stringBad = brBad.readLine()) != null) {
			bad[countBad] = stringBad;
			countBad++;
		}
		
		// BAD WORDS
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(1041, 47, 120, 661);
		f.getContentPane().add(scrollPane_2);
		
		final JList listBad = new JList(bad);
		listBad.setModel(new DefaultListModel<String>());
		final DefaultListModel<String> modelBad = (DefaultListModel) listBad.getModel();
		
		scrollPane_2.setViewportView(listBad);
		

		// GOOD WORDS
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(890, 57, 135, 661);
		f.getContentPane().add(scrollPane);
		
		final JList listGood = new JList();
		listGood.setModel(new DefaultListModel<String>());
		scrollPane.setViewportView(listGood);
		final DefaultListModel<String> modelGood = (DefaultListModel) listGood.getModel();
		
		
		JLabel lblNewLabel = new JLabel("Reviews");
		lblNewLabel.setBackground(new Color(255, 255, 0));
		lblNewLabel.setBounds(16, 46, 490, 16);
		f.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(16, 74, 862, 644);
		f.getContentPane().add(scrollPane_1);
		
		final JTextPane textPane = new JTextPane();
		scrollPane_1.setViewportView(textPane);

		JLabel label = new JLabel("Sentiment Table");
		label.setBackground(Color.YELLOW);
		label.setBounds(890, 33, 259, 16);
		f.getContentPane().add(label);

		String comboBoxListe[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

		final JComboBox comboBox = new JComboBox(comboBoxListe);
		comboBox.setBounds(994, 7, 72, 27);

		f.getContentPane().add(comboBox);
		
		

		
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				seiten = Integer.parseInt((String) comboBox.getSelectedItem());
			}
		});

		btnGetTextFrom.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// int seiten =
				// Integer.parseInt(textField_seitenZahl.getText());

				// String link = textField_urlAdress.getText();
				String link = "https://www.amazon.com/gp/product/0385541198/ref=s9_acsd_hps_bw_c_x_3_w?pf_rd_m=ATVPDKIKX0DER&pf_rd_s=merchandised-search-4&pf_rd_r=ZKTQN8GNZHRSBJY25237&pf_rd_r=ZKTQN8GNZHRSBJY25237&pf_rd_t=101&pf_rd_p=1877692d-9c16-4231-b349-b0a5b82d7791&pf_rd_p=1877692d-9c16-4231-b349-b0a5b82d7791&pf_rd_i=283155";
				try {
					Konsole.control(link, 1); // FIXME: statischer Link und
												// Seitenzahl
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				// SENTIMENTLISTEN befüllen
				content = "";
				for (String string : reviewList) {
					content += string;
				}
				textPane.setText(content);
				for(int i = 0; i < bad.length; i++)
				{
					String b = bad[i];
					if(words.contains(b))
						modelBad.addElement(b);
				}
				for(int i = 0; i < good.length; i++)
				{
					String g = good[i];
					if(words.contains(g))
						modelGood.addElement(g);
				}
			}
		});

		f.setVisible(true);

		// Inhalt aus Ordner Amazon/scr/texts löschen, Dateien beibehalten
		f.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				JCheckBox deleteContent = new JCheckBox("Delete file content?");

				if (JOptionPane.showConfirmDialog(f, deleteContent, "Exit?", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					if (deleteContent.isSelected()) {
						try {
							FileOutputStream writer = new FileOutputStream("src/texts/Quelltext.txt");
							writer.close();
							FileOutputStream writer1 = new FileOutputStream("src/texts/reviews.txt");
							writer1.close();
							FileOutputStream writer2 = new FileOutputStream("src/texts/reviewsList.txt");
							writer2.close();
							System.exit(0);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

					} else
						System.exit(0);
				}
			}
		});
		
		// HIGHLIGHTER GOOD
		listGood.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String selected = (String) listGood.getSelectedValue();
				Highlighter highlighter = textPane.getHighlighter();
				Document doc = textPane.getDocument();
				DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
				
				textPane.getHighlighter().removeAllHighlights();
				
				try {
				    Pattern pattern = Pattern.compile(selected);
				    if (pattern != null) {
				      Matcher matcher = pattern.matcher(doc.getText(0, doc.getLength()));
				      int pos = 0;
				      while (matcher.find(pos)) {
				        int start = matcher.start();
				        int end   = matcher.end();
				        
						highlighter.addHighlight(start, end, highlightPainter);
				        pos = end;
				      }
				    }
				} catch (BadLocationException e1) { e1.printStackTrace();
				} finally {
					
				}
			}
		});
		
		// HIGHLIGHTER BAD
		listBad.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String selected = (String) listBad.getSelectedValue();
				Highlighter highlighter = textPane.getHighlighter();
				Document doc = textPane.getDocument();
				DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
				
				textPane.getHighlighter().removeAllHighlights();
				
				try {
				    Pattern pattern = Pattern.compile(selected);
				    if (pattern != null) {
				      Matcher matcher = pattern.matcher(doc.getText(0, doc.getLength()));
				      int pos = 0;
				      while (matcher.find(pos)) {
				        int start = matcher.start();
				        int end   = matcher.end();
				        
						highlighter.addHighlight(start, end, highlightPainter);
				        pos = end;
				      }
				    }
				} catch (BadLocationException e1) { e1.printStackTrace();
				} finally {
					
				}
			}
		});
	}
}
