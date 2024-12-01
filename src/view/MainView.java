package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JButton btnNewUser;
	public JButton btnImportDB;
	public JButton btnSendQ;
	public JTextField name;
	public JTextField population;
	public JTextField density;
	public JTextField area;
	public JTextField fertility;
	public JTextField age;
	public JTextField urban;
	public JTextField share;
	public JComboBox modPopulation;
	public JComboBox modDensity;
	public JComboBox modArea;
	public JComboBox modFertility;
	public JComboBox modAge;
	public JComboBox modUrban;
	public JComboBox modShare;
	public JCheckBox chckbxDensity;
	public JTextArea txtrDensity;
	public JCheckBox chckbxArea;
	public JTextArea txtrArea;
	public JCheckBox chckbxFertility;
	public JTextArea Fertility;
	public JCheckBox chckbxAge;
	public JTextArea Age;
	public JCheckBox chckbxUrban;
	public JTextArea txtrUrban;
	public JCheckBox chckbxShare;
	public JTextArea txtrShare;
	public JTable table;
	private JScrollPane scrollPane;
	public JCheckBox chckbxPopulation;
	public JTextArea textArea;
	public JButton btnSaveAsCvs;
	private JPanel panel;
	public JTextField usernamesearch;
	public JButton btnSearchUser;
	private JScrollPane scrollPane_1;
	public JButton btnLogout;
	public JTextField tablename;
	private JTextArea txtrName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1176, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnNewUser = new JButton("New user");
	
		btnNewUser.setBounds(877, 11, 125, 28);
		contentPane.add(btnNewUser);
		
		btnImportDB = new JButton("import scv");
		btnImportDB.setBounds(732, 11, 125, 28);
		contentPane.add(btnImportDB);
		
		panel = new JPanel();
		panel.setBounds(732, 102, 418, 357);
		contentPane.add(panel);
		panel.setLayout(null);
		
		btnSendQ = new JButton("search");
		btnSendQ.setBounds(228, 320, 190, 37);
		panel.add(btnSendQ);
		
		name = new JTextField();
		name.setBounds(228, 0, 190, 28);
		panel.add(name);
		name.setColumns(10);
		
		population = new JTextField();
		population.setBounds(228, 39, 190, 28);
		panel.add(population);
		population.setColumns(10);
		
		density = new JTextField();
		density.setBounds(228, 78, 190, 29);
		panel.add(density);
		density.setColumns(10);
		
		area = new JTextField();
		area.setBounds(228, 118, 190, 29);
		panel.add(area);
		area.setColumns(10);
		
		fertility = new JTextField();
		fertility.setBounds(228, 158, 190, 29);
		panel.add(fertility);
		fertility.setColumns(10);
		
		age = new JTextField();
		age.setBounds(228, 200, 190, 29);
		panel.add(age);
		age.setColumns(10);
		
		urban = new JTextField();
		urban.setBounds(228, 240, 190, 29);
		panel.add(urban);
		urban.setColumns(10);
		
		share = new JTextField();
		share.setBounds(228, 280, 190, 29);
		panel.add(share);
		share.setColumns(10);
		
		modPopulation = new JComboBox();
		modPopulation.setBounds(159, 39, 59, 28);
		panel.add(modPopulation);
		
		modPopulation.setModel(new DefaultComboBoxModel(new String[] {"<", ">", "=", "<=", ">="}));
		
		modDensity = new JComboBox();
		modDensity.setBounds(159, 78, 59, 29);
		panel.add(modDensity);
		modDensity.setModel(new DefaultComboBoxModel(new String[] {"<", ">", "=", "<=", ">="}));
		
		modArea = new JComboBox();
		modArea.setBounds(159, 118, 59, 29);
		panel.add(modArea);
		modArea.setModel(new DefaultComboBoxModel(new String[] {"<", ">", "=", "<=", ">="}));
		
		modFertility = new JComboBox();
		modFertility.setBounds(159, 158, 59, 29);
		panel.add(modFertility);
		modFertility.setModel(new DefaultComboBoxModel(new String[] {"<", ">", "=", "<=", ">="}));
		
		modAge = new JComboBox();
		modAge.setBounds(159, 200, 59, 29);
		panel.add(modAge);
		modAge.setModel(new DefaultComboBoxModel(new String[] {"<", ">", "=", "<=", ">="}));
		
		modUrban = new JComboBox();
		modUrban.setBounds(159, 240, 59, 29);
		panel.add(modUrban);
		modUrban.setModel(new DefaultComboBoxModel(new String[] {"<", ">", "=", "<=", ">="}));
		
		modShare = new JComboBox();
		modShare.setBounds(159, 280, 59, 29);
		panel.add(modShare);
		modShare.setModel(new DefaultComboBoxModel(new String[] {"<", ">", "=", "<=", ">="}));
		
		chckbxPopulation = new JCheckBox("", true);
		chckbxPopulation.setBounds(0, 39, 21, 28);
		panel.add(chckbxPopulation);
		
		JTextArea txtrXxxxxxxxx = new JTextArea();
		txtrXxxxxxxxx.setBounds(43, 39, 106, 28);
		panel.add(txtrXxxxxxxxx);
		txtrXxxxxxxxx.setText("Population");
		
		chckbxDensity = new JCheckBox("", true);
		chckbxDensity.setBounds(0, 78, 21, 23);
		panel.add(chckbxDensity);
		
		txtrDensity = new JTextArea();
		txtrDensity.setBounds(43, 78, 106, 29);
		panel.add(txtrDensity);
		txtrDensity.setText("Density");
		
		chckbxArea = new JCheckBox("", true);
		chckbxArea.setBounds(0, 118, 21, 23);
		panel.add(chckbxArea);
		
		txtrArea = new JTextArea();
		txtrArea.setBounds(43, 118, 106, 29);
		panel.add(txtrArea);
		txtrArea.setText("Area");
		
		chckbxFertility = new JCheckBox("", true);
		chckbxFertility.setBounds(0, 158, 21, 23);
		panel.add(chckbxFertility);
		
		Fertility = new JTextArea();
		Fertility.setBounds(43, 158, 106, 29);
		panel.add(Fertility);
		Fertility.setText("Fertility");
		
		chckbxAge = new JCheckBox("", true);
		chckbxAge.setBounds(0, 200, 21, 23);
		panel.add(chckbxAge);
		
		Age = new JTextArea();
		Age.setBounds(43, 200, 106, 29);
		panel.add(Age);
		Age.setText("Age");
		
		chckbxUrban = new JCheckBox("", true);
		chckbxUrban.setBounds(0, 240, 21, 23);
		panel.add(chckbxUrban);
		
		txtrUrban = new JTextArea();
		txtrUrban.setBounds(43, 240, 106, 29);
		panel.add(txtrUrban);
		txtrUrban.setText("Urban");
		
		chckbxShare = new JCheckBox("", true);
		chckbxShare.setBounds(0, 280, 21, 23);
		panel.add(chckbxShare);
		
		txtrShare = new JTextArea();
		txtrShare.setBounds(43, 280, 106, 29);
		panel.add(txtrShare);
		txtrShare.setText("Share");
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 398, 393);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			}, 
			new String[] {
			}
		));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(418, 11, 287, 452);
		contentPane.add(scrollPane_1);
		
		textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
		btnSaveAsCvs = new JButton("save as cvs");
		btnSaveAsCvs.setBounds(10, 438, 398, 45);
		contentPane.add(btnSaveAsCvs);
		
		usernamesearch = new JTextField();
		usernamesearch.setBounds(732, 51, 217, 28);
		contentPane.add(usernamesearch);
		usernamesearch.setColumns(10);
		
		btnSearchUser = new JButton("Search User");
		btnSearchUser.setBounds(959, 50, 191, 28);
		contentPane.add(btnSearchUser);
		
		btnLogout = new JButton("logout");
		btnLogout.setBounds(1025, 11, 125, 28);
		contentPane.add(btnLogout);
		
		tablename = new JTextField();
		tablename.setBounds(81, 408, 327, 25);
		contentPane.add(tablename);
		tablename.setColumns(10);
		
		txtrName = new JTextArea();
		txtrName.setText("Name:");
		txtrName.setBounds(10, 407, 61, 28);
		contentPane.add(txtrName);
	}
}

