package agents;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.Color;

public class SellerGui extends JFrame {
	private Seller sellerAgent;
	private JPanel contentPane;
	private JTextField textField; // name
	private JSpinner spinner; // price
	private JTable table;

	private JLabel sellerName;

	public void setSellerAgent(Seller s, String t) {
		sellerAgent = s;
		try {
			sellerName.setText(t);
		} catch (Exception e) {

		}

	}

	public void onAddClick() {
		float price = (float) this.spinner.getValue();
		String name = this.textField.getText();
		if (name != "" && !name.isEmpty() && !name.isBlank()) {
			DefaultTableModel tbModel = (DefaultTableModel) this.table.getModel();
			Object[] row = new Object[] { (String) name, (float) price };
			ArrayList<String> numdata = new ArrayList<String>();
			for (int count = 0; count < tbModel.getRowCount(); count++) {
				numdata.add(tbModel.getValueAt(count, 0).toString().toLowerCase());
			}

			if (numdata.contains(name.toLowerCase())) {
				JOptionPane.showMessageDialog(this, "Item already exists !");
			} else {
				try {
					this.sellerAgent.updateItems(name, price);
				} catch (Exception e) {
					System.out.print(e.getMessage());
					return;
				}
				tbModel.addRow(row);

				JOptionPane.showMessageDialog(this, "item: " + name + " was added !!");
			}

		} else {
			JOptionPane.showMessageDialog(this, "Enter a valid data");
		}
	}

	public void showGui() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SellerGui frame = new SellerGui(sellerAgent, sellerAgent.getLocalName());
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
	public SellerGui(Seller s, String t) {
		setSellerAgent(s, t);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 485, 523);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		sellerName = new JLabel(t);
		sellerName.setFont(new Font("Roboto", Font.PLAIN, 28));
		sellerName.setHorizontalAlignment(SwingConstants.CENTER);
		sellerName.setBounds(10, 11, 449, 43);
		contentPane.add(sellerName);

		textField = new JTextField();
		textField.setFont(new Font("Roboto", Font.PLAIN, 17));
		textField.setBounds(142, 74, 255, 28);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel itemName = new JLabel("Name");
		itemName.setFont(new Font("Roboto", Font.PLAIN, 17));
		itemName.setBounds(52, 65, 80, 43);
		contentPane.add(itemName);

		JLabel itemPrice = new JLabel("Price");
		itemPrice.setFont(new Font("Roboto", Font.PLAIN, 17));
		itemPrice.setBounds(52, 105, 80, 43);
		contentPane.add(itemPrice);

		spinner = new JSpinner();
		spinner.setModel(
				new SpinnerNumberModel(Float.valueOf(1), Float.valueOf(1), Float.valueOf(9999), Float.valueOf(1)));
		spinner.setFont(new Font("Roboto", Font.PLAIN, 17));
		spinner.setBounds(142, 113, 255, 28);
		contentPane.add(spinner);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
				new Object[][] {
						{ "Name", "Price" },
				},
				new String[] {
						"name", "price"
				}) {
			Class[] columnTypes = new Class[] {
					String.class, String.class
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setMinWidth(28);
		table.setBounds(0, 234, 469, 250);
		contentPane.add(table);

		JButton btnAdd = new JButton("Add item");
		btnAdd.setBounds(142, 151, 255, 36);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onAddClick();

			}
		});
		contentPane.add(btnAdd);
	}
}
