package agents;
import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class MainAgent extends Agent {
  private int budget = Integer.MAX_VALUE;
  private int sellersNumber;
  private AID[] sellerAgents;
  private String item;
  
  public void setItem(String item) {
	  this.item = item;
  }
  
  public String getItem() {
	  return this.item;
  }
  
  public int getSellersNumber() {
	  return this.sellersNumber;
  }
  
  public void setSellersNumber(int n) {
	  this.sellersNumber = n;
  }
  
  @Override
  protected void setup() {
	  
	setSellersNumber((int) this.getArguments()[0]);
    // Set the seller agents
    sellerAgents = new AID[getSellersNumber()];
    for(int i=0; i< getSellersNumber(); i++) {
    	sellerAgents[i] = new AID("Seller" + (i + 1), AID.ISLOCALNAME);
    }
    // Add the request sender and response receiver behaviors
    ParallelBehaviour parallelBehaviour = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
    parallelBehaviour.addSubBehaviour(new ResponseReceiverBehaviour());
    addBehaviour(parallelBehaviour);
    addBehaviour(new buyerRequestReciever());
  }
  
  
 
  private class buyerRequestReciever extends CyclicBehaviour{
	private MessageTemplate template; 
	@Override
	public void action() {
		template = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		ACLMessage request = receive(template);
	    if(request != null) {
	    	  String item = request.getContent().toString().toLowerCase();
	          ACLMessage msg = new ACLMessage(ACLMessage.CFP);
	          for (AID sellerAgent : sellerAgents) {
	            msg.addReceiver(sellerAgent);
	          }
	          String content = request.getSender().getLocalName().toString() + "," + item;
	          msg.setContent(content);
	          send(msg);
	      }else {
	    	  block();
	      }
	}
  }


  private class ResponseReceiverBehaviour extends CyclicBehaviour {

	private MessageTemplate template;
	  private List<ACLMessage> proposals = new ArrayList<ACLMessage>();
	  private boolean receivedAll = false;


	  public void action() {
		template = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
	    // Receive a proposal from a seller agent
	    ACLMessage msg = receive(template);
	    if (msg != null) {
	      proposals.add(msg);
	    } else {
	      block();
	    }
	    
	    // Check if we have received proposals from all seller agents
	    if (proposals.size() == getSellersNumber() && !receivedAll) {
	      receivedAll = true;

	      // Select the best proposal
	      ACLMessage bestProposal = proposals.get(0);
	      float bestPrice = Float.POSITIVE_INFINITY;
	      for (ACLMessage proposal : proposals) {
	        float price = Float.parseFloat(proposal.getContent().split(",")[1]);
	        if (price < bestPrice) {
	          bestPrice = price;
	          bestProposal = proposal;
	        }
	      }
	      

	      ACLMessage replyToBuyer = new ACLMessage(ACLMessage.INFORM);
	      replyToBuyer.addReceiver(new AID(bestProposal.getContent().split(",")[0], AID.ISLOCALNAME));
	      if(bestProposal != null && Float.isFinite(bestPrice)) {
	    	  String resultFrom = bestProposal.getSender().getLocalName().toString();
		      String result = resultFrom + "," + String.valueOf(bestPrice);
		      replyToBuyer.setContent(result);
		      
	      }else {
	    	  replyToBuyer.setContent("not found");
	      }
	      send(replyToBuyer);
	      proposals.clear();
	      receivedAll=false;
	    }else {
	    	block();
	    }
	  }

	}


}

