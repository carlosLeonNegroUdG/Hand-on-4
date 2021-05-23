package examples.handson4;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import net.sf.clipsrules.jni.*;


import java.util.*;

public class AgenteClips extends Agent {

    private AgenteClipsGui myAgenteClipsGui;
    
	public String hechos;
	public String reglas;
	public Environment clips;
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
    	addBehaviour(new MyAgenteClips());
		
    } 

	public void actualizacionDeClips(String hechos1,String reglas1) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				hechos=hechos1;
				reglas=reglas1;
				
				System.out.println( "El hecho " +hechos+" se ha insertado con  la regla "+reglas);
				clips.eval(hechos);
				clips.build(reglas);
			}
		} );
	}

	public void ejecutarClips(){
		String nuevosHechos="";
		String nuevasReglas="";
		nuevosHechos = hechos;
		nuevasReglas = reglas;
		
		if(nuevosHechos != null && nuevasReglas != null){
			clips.eval("(facts)");
			clips.eval("(rules)");
			clips.run();
			clips.eval("(facts)");
			clips.eval("(exit)");
		}
		else{
			System.out.println("No ha insertado hechos o reglas en la interfaz");
		}
	}

	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		// Close the GUI
		myAgenteClipsGui.dispose();
		// Printout a dismissal messag
		ejecutarClips();
		
		System.out.println("Agente de clips terminando.");
		clips.eval("(exit)");
	}

	
	//@override
    private class MyAgenteClips extends OneShotBehaviour {

        public void action() {

			try{
				clips.eval("(assert (sintoma a))");
				clips.eval("(facts)");
				clips.build("(defrule infeccionGarganta(sintoma a) => (assert(cuadro infecionGarganta)))");
				clips.eval("(rules)");
				clips.eval("(facts)");
			
			}
			catch(Exception ee){
				ee.printStackTrace();
			}

        }   		

		
		
    }    // END of inner class ...Behaviour

}