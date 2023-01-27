package agents;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Buyer extends Agent{
	public BuyerGui buyerGui = new BuyerGui(this);
	
	public String sendRequestToMainAgent(String item) {
		if(item != null && !item.isBlank() && !item.isEmpty()) {
			
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(new AID("MainAgent", AID.ISLOCALNAME));
		    msg.setContent(item);
		    this.addBehaviour(new receiveSellersResopnses());
		    send(msg);
		    return "done";
		}else {
			System.out.print("\n no item specfied");
			return "no item specfied";
			
		}
	}
	
	@Override
	protected void setup() {
		
		if(!buyerGui.isActive()) {
			buyerGui.showGui();
		}
		
		
	    addBehaviour(new receiveSellersResopnses());

}

	
	@Override
	protected void takeDown() {
		System.out.print("I'm going\n");
	}
	
		
	  private class receiveSellersResopnses extends CyclicBehaviour {
			
			@Override
		    public void action() {
		      // Wait for a message from the main agent with the best offer
		      MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		      ACLMessage msg = receive(template);
		      if (msg != null) {
		          // Split the message content to get the seller and price
		    	  if(msg.getContent().equals("not found")) {
		    		  System.out.print("\nNot found");
		    		  buyerGui.showResult("Not found");
		    		  
		    	  }else {
			          String[] parts = msg.getContent().split(",");
			          String seller = parts[0];
			          float price = Float.parseFloat(parts[1]);
			          String _msg = "Best offer :" + price + "$ from : " + seller;
				      System.out.print("\n"+_msg);
				      buyerGui.showResult(_msg);
				      this.getAgent().removeBehaviour(this);
		    	  }
		      } else {
		        block();
		      }
		    }
		  }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
