package agents;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;

import jade.domain.introspection.AddedBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Seller extends Agent{
	private SellerGui sellerGui = new SellerGui(this, this.getName());
	private ArrayList<String> items = new ArrayList<String>();
	private ArrayList<Float> prices = new ArrayList<Float>();
	
	public void updateItems(String name, float price) {
		this.items.add(name);
		this.prices.add(price);
	}
	
	@Override
	protected void setup(){
		if(!sellerGui.isActive()) {
			sellerGui.showGui();


		}
		addBehaviour(new receiveBuyersAndSendResponse());
	}
		
	private class receiveBuyersAndSendResponse extends CyclicBehaviour {

		public void action() {
		    MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			ACLMessage message = receive(template);		
			if(message != null) {
				float bestPrice = Float.POSITIVE_INFINITY;
				String content = (String) message.getContent();
				
				String itemToBuy = content.split(",")[1].replace(" ", "").toLowerCase();
				System.out.print(itemToBuy);
				ACLMessage response = message.createReply();
				response.setPerformative(ACLMessage.PROPOSE);
				for(int i=0; i<items.size(); i++) {
					if(itemToBuy.equals(items.get(i).toString().replace(" ", "").toLowerCase())){
						bestPrice = Float.parseFloat(prices.get(i).toString());
						break;
					}
				}
				content = content.split(",")[0] +","+ String.valueOf(bestPrice);
				response.setContent(content);
				send(response);
				
			}else {
				block();
			}
			
		}
	}
	

}
