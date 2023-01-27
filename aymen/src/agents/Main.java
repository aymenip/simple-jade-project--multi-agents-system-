package agents;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JLabel;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Main extends JFrame{
	
		private JPanel contentPane;
		private JSpinner buyersNumber, sellersNumber;
		private JButton startBtn;
		public void onStartBtnClick() {
			
			
			Runtime rt = Runtime.instance();
			Profile pf = new ProfileImpl();
			pf.setParameter(Profile.MAIN_HOST, "localhost");
			pf.setParameter(Profile.GUI, "true");
			ContainerController cc = rt.createMainContainer(pf);
			
			// create new container for Buyer agent
			Profile pfBuyer = new ProfileImpl(false);
			pfBuyer.setParameter(Profile.MAIN_HOST, "localhost");
			ContainerController cc2 = rt.createAgentContainer(pfBuyer);
			AgentController buyerAgent, sellerAgent, mainAgent;
			
			// create main agent
			mainAgent = createAgent(cc, "MainAgent" , "agents.MainAgent", new Object[] {(int) this.sellersNumber.getValue()});
			// sellers creation
			for(int i=1; i<= (int) this.sellersNumber.getValue();  i++) {
				sellerAgent = createAgent(cc, "Seller" + i, "agents.Seller", null);
			}
			// buyers creation
			for(int i=1; i<= (int) this.buyersNumber.getValue();  i++) {
				buyerAgent = createAgent(cc2, "Buyer" + i, "agents.Buyer", null);
			}
			// close window
			this.dispose();
		}
		
		public static void main(String args[]) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Main frame = new Main();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
		}
		
		public static AgentController createAgent(ContainerController cc2, String name, String className, Object[] args) {
			AgentController ac = null;
			try {
				ac = cc2.createNewAgent(name, className, args);
				ac.start();
				
			} catch (StaleProxyException e) {
				e.printStackTrace();
			}
			return ac;
		}
		
		
		public Main() {
			
			setTitle("Agents world");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 203);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Agents configuaration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel.setBounds(10, 11, 414, 85);
			contentPane.add(panel);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Sellers number");
			lblNewLabel.setFont(new Font("Roboto", Font.PLAIN, 15));
			lblNewLabel.setBounds(10, 38, 105, 14);
			panel.add(lblNewLabel);
			
			sellersNumber = new JSpinner();
			sellersNumber.setBounds(125, 30, 34, 31);
			panel.add(sellersNumber);
			sellersNumber.setModel(new SpinnerNumberModel(1, 1, 3, 1));
			sellersNumber.setFont(new Font("Roboto", Font.PLAIN, 13));
			
			buyersNumber = new JSpinner();
			buyersNumber.setModel(new SpinnerNumberModel(1, 1, 3, 1));
			buyersNumber.setFont(new Font("Roboto", Font.PLAIN, 13));
			buyersNumber.setBounds(370, 30, 34, 31);
			panel.add(buyersNumber);
			
			JLabel lblBuyersNumber = new JLabel("Buyers number");
			lblBuyersNumber.setFont(new Font("Roboto", Font.PLAIN, 15));
			lblBuyersNumber.setBounds(255, 38, 105, 14);
			panel.add(lblBuyersNumber);
			
			startBtn = new JButton("Start agents");
			startBtn.setBounds(10, 107, 414, 49);
			contentPane.add(startBtn);
			startBtn.setFont(new Font("Roboto Medium", Font.PLAIN, 16));
			startBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					onStartBtnClick();
				}
			});
		}
		

	
	}
		
		



	
	

