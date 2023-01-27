package agents;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.TrayIcon.MessageType;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BuyerGui extends JFrame {
	private Buyer buyerAgent;
	private JTextField item;
	private JButton btnNewButton;
	private JList list;
	private JLabel resultLabel = new JLabel("");
	
	public void showResult(String msg) {
		JOptionPane.showMessageDialog(this, msg);

	}

	public void setBuyerAgent(Buyer b) {
		buyerAgent = b;
		
	}
	public JButton getSearchBtn() {
		return btnNewButton;
	}
	
	public String getItem() {
		return this.item.getText();
	}
	
	public void setItem(String new_item) {
		this.item.setText(new_item);
	}
	
	public void setResult(String msg, Color color) {
		this.resultLabel.setForeground(color);
		this.resultLabel.setText(msg);
	}
	public void onSearchClick() {
		String item = this.item.getText();
		String result = this.buyerAgent.sendRequestToMainAgent(item);
		if(result.toString().equals("no item specfied")) {
			setResult(result, Color.BLUE);
		}else {
			setResult("", Color.black);
		}
	}
	
	
	public void showGui() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					BuyerGui frame = new BuyerGui(buyerAgent);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public BuyerGui(Buyer b) {
		// set buyer agent to our private attribute 'buyerAgent'
		setBuyerAgent(b);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 535, 332);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome in agents world");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(5, 10, 509, 60);
		lblNewLabel.setFont(new Font("Roboto Black", Font.BOLD, 35));
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search by typing item name", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setToolTipText("User input");
		panel.setBounds(5, 81, 509, 79);
		contentPane.add(panel);
		panel.setLayout(null);
		
		item = new JTextField();
		item.setFont(new Font("Roboto Medium", Font.PLAIN, 15));
		item.setHorizontalAlignment(SwingConstants.CENTER);
		item.setBounds(10, 30, 370, 38);
		panel.add(item);
		item.setColumns(10);
		
		btnNewButton = new JButton("Search");
		btnNewButton.setFont(new Font("Roboto Slab SemiBold", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onSearchClick();
			}
		});
		btnNewButton.setBounds(390, 30, 109, 38);
		
		panel.add(btnNewButton);
		/*java swing jframe update jtextfield while running
		 * 
		 */

		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setFont(new Font("Roboto Black", Font.PLAIN, 30));
		resultLabel.setBounds(0, 192, 509, 60);
		contentPane.add(resultLabel);
		
	}
	
	

}
