package examples.handson5;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


import java.util.*;
import net.sf.clipsrules.jni.*;

public class AgenteClips5 extends Agent {

    private AgenteClipsGui myAgenteClipsGui;
    Environment clips;
    private String hechos;
	private String reglas;
    private String templates;
    protected void setup() {
        myAgenteClipsGui = new AgenteClipsGui(this);
        myAgenteClipsGui.showGui();
		clips = new Environment();
        DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("CLIPS");
		sd.setName("servico de clips");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		System.out.println("Agent "+getLocalName()+" started.");
    	addBehaviour(new TellAgenteClips());
		
    } 

    private class TellAgenteClips extends OneShotBehaviour {

        public void action() {
            try{
                
                cargarClipsPersons();
                cargarClipsProdCust();
                cargarClipsMarket();
               
            }
			catch(Exception ee){
				ee.printStackTrace();
			}
        }	
        ///*
		public void cargarClipsPersons(){
			clips.eval("(clear)");
            System.out.println("Cargando y ejecutando el direcctorio de Persons\n");
			clips.load("C:\\CLIPS 6.31\\clips_jni_051\\test\\src\\examples\\handson5\\load-persons.clp");
            clips.eval("(reset)");
			clips.load("C:\\CLIPS 6.31\\clips_jni_051\\test\\src\\examples\\handson5\\load-persons-rules.clp");
			clips.eval("(facts)");
			clips.eval("(rules)");
            clips.run();
			
		}//*/
        ///*
		public void cargarClipsProdCust(){
            clips.eval("(clear)");
            System.out.println("\nCargando y ejecutando el direcctorio de Prodcust\n");
			clips.load("C:\\CLIPS 6.31\\clips_jni_051\\test\\src\\examples\\handson5\\load-prod-cust.clp");
            clips.eval("(reset)");
			clips.load("C:\\CLIPS 6.31\\clips_jni_051\\test\\src\\examples\\handson5\\load-prodcust-rules.clp");
            clips.eval("(reset)");
			clips.eval("(facts)");
			clips.eval("(rules)");
            clips.run();
		}//*/
        ///*
		public void cargarClipsMarket(){
            clips.eval("(clear)");
            System.out.println("\nCargando y ejecutando el direcctorio de Market\n");
			clips.load("C:\\CLIPS 6.31\\clips_jni_051\\test\\src\\examples\\handson5\\templates.clp");
			clips.load("C:\\CLIPS 6.31\\clips_jni_051\\test\\src\\examples\\handson5\\facts.clp");
            clips.eval("(reset)");
			clips.load("C:\\CLIPS 6.31\\clips_jni_051\\test\\src\\examples\\handson5\\rules.clp");
            clips.eval("(reset)");
			clips.eval("(facts)");
			clips.eval("(rules)");
            clips.run();
            clips.eval("(exit)");
		}//*/
		
        public int onEnd() {
            myAgent.doDelete();   
            return super.onEnd();
        } 
		
    }    // END of inner class ...Behaviour


}