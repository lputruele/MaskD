package graph;

import java.util.*;
import faulty.*;
import java.io.*;

public class ExplicitCompositeModel {
	private HashMap<CompositeNode, TreeSet<CompositeNode>> succList; // Succesors adjacency list
	private HashMap<CompositeNode, TreeSet<CompositeNode>> preList; // Predecessors adjacency list
	private HashMap<Pair, LinkedList<Action>> actions; // Edge actions
	private CompositeNode initial; // Initial State
	private LinkedList<Var> sharedVars; // Global variables
	private LinkedList<CompositeNode> nodes; // Global states
	private int numNodes;
	private int numEdges;
	private LinkedList<Proc> procs;
	private LinkedList<String> procDecls;
	private boolean isWeak;
	private boolean isSpec;

	public ExplicitCompositeModel(GlobalVarCollection svs, boolean isS) {
		sharedVars = svs.getBoolVars();
		sharedVars.addAll(svs.getEnumVars());
		sharedVars.addAll(svs.getIntVars());
		succList = new HashMap<CompositeNode, TreeSet<CompositeNode>>();
		preList = new HashMap<CompositeNode, TreeSet<CompositeNode>>();
		actions = new HashMap<Pair, LinkedList<Action>>();
		numNodes = numEdges = 0;
		nodes = new LinkedList<CompositeNode>();
		procs = new LinkedList<Proc>();
		procDecls = new LinkedList<String>();
		isWeak = false;
		isSpec = isS;
	}

	public void setInitial(CompositeNode v){
		initial = v;
	}

	public void setIsWeak(boolean b){
		isWeak = b;
	}

	public CompositeNode getInitial(){
		return initial;
	}

	public LinkedList<Var> getSharedVars(){
		return sharedVars;
	}

	public HashMap<Pair, LinkedList<Action>> getActions(){
		return actions;
	}

	public LinkedList<Proc> getProcs(){
		return procs;
	}

	public LinkedList<String> getProcDecls(){
		return procDecls;
	}

	public int getNumNodes(){
		return numNodes;
	}

	public int getNumEdges(){
		return numEdges;
	}


	public void addNode(CompositeNode v) {
		nodes.add(v);
		succList.put(v, new TreeSet<CompositeNode>());
		preList.put(v, new TreeSet<CompositeNode>());
		numNodes += 1;
	}

	public CompositeNode search(CompositeNode v) {
		for (CompositeNode node : nodes){
			if (node.equals(v))
				return node;
		};
		return null;
	}

	public boolean hasNode(CompositeNode v) {
		return nodes.contains(v);
	}


	public boolean hasEdge(CompositeNode from, CompositeNode to, Action a) {

		if (!hasNode(from) || !hasNode(to))
			return false;
		Pair transition = new Pair(from,to);
		if (actions.get(transition) == null){
			//System.out.println("a");
			return false;
		}
		

		/*for (Action a_ : actions.get(transition)){
			if (a.getLabel().equals(a_.getLabel())){
				return true;
			}
		}
		System.out.println(actions.get(transition));
		
		return false;*/
		return actions.get(transition).contains(a);
	}


	public void addEdge(CompositeNode from, CompositeNode to, Action a) {
		if (to != null){
			if (a.isTau() && a.getLabel().charAt(0)!='&')
				a.setLabel("&"+a.getLabel());
			if (hasEdge(from, to, a))
				return;
			numEdges += 1;
			succList.get(from).add(to);
			preList.get(to).add(from);
			Pair transition = new Pair(from,to);
			if (actions.get(transition) == null){
				actions.put(transition,new LinkedList<Action>());
			}
			actions.get(transition).add(a);
		}
	}

	/*public void rmEdge(CompositeNode from, CompositeNode to, int pos) {
		Pair t = new Pair(from,to);
		tauActions.get(t).remove(pos);
		faultyActions.get(t).remove(pos);
		actions.get(t).remove(pos);
		if (actions.get(t).isEmpty()){
			succList.get(from).remove(to);
			preList.get(to).remove(from);
		}

	}*/


	public LinkedList<CompositeNode> getNodes(){
		return nodes;
	}

	public TreeSet<CompositeNode> getSuccessors(CompositeNode v){
		return succList.get(v);
	}

	public TreeSet<CompositeNode> getPredecessors(CompositeNode v){
		return preList.get(v);
	}

	public String createDot(boolean isImp){
		String res = "digraph model {\n\n";
		for (CompositeNode v : nodes){
			//if (v.getIsFaulty())
			//	res += "    STATE"+v.toStringDot()+" [color=\"red\"];\n";
			for (CompositeNode u : succList.get(v)){
				Pair edge = new Pair(v,u);
				if (actions.get(edge) != null)
					for (int i=0; i < actions.get(edge).size(); i++){
						if (actions.get(edge).get(i).isFaulty())
							res += "    STATE"+v.toStringDot()+" -> STATE"+ u.toStringDot() +" [color=\"red\",label = \""+actions.get(edge).get(i).getLabel()+"\"]"+";\n";
						else
							if (actions.get(edge).get(i).isTau())
								res += "    STATE"+v.toStringDot()+" -> STATE"+ u.toStringDot() +" [style=dashed,label = \""+actions.get(edge).get(i).getLabel()+"\"]"+";\n";
							else
								res += "    STATE"+v.toStringDot()+" -> STATE"+ u.toStringDot() +" [label = \""+actions.get(edge).get(i).getLabel()+"\"]"+";\n";
					}
			}
		}
		res += "\n}";
		try{
			String path = "";
			if (isImp)
            	path ="../out/" + "ImpModel" +".dot";
            else
            	path ="../out/" + "SpecModel" +".dot";
            File file = new File(path);
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(res);
            bw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
		return res;
	}


	public void saturate(){
		//Add tau self-loops
		//if (!isWeak)
		//	return;
		for (CompositeNode p : nodes){
			addEdge(p,p,new Action("&",false,true,isSpec)); // p -> p is internal
		}
		if (!isWeak)
			return;
		boolean change = true;
		//this lists will share the same size
		LinkedList<CompositeNode> fsts;
		LinkedList<CompositeNode> snds;
		LinkedList<String> lbls;
		LinkedList<Boolean> isFs;
		LinkedList<Boolean> isTaus;


		//Saturate graph
		while (change){
			change = false;
			fsts = new LinkedList<CompositeNode>();
			snds = new LinkedList<CompositeNode>();
			lbls = new LinkedList<String>();
			//isFs = new LinkedList<Boolean>();
			isTaus = new LinkedList<Boolean>();

			for (CompositeNode p : nodes){
				for (CompositeNode p_ : succList.get(p)){
					Pair t0 = new Pair(p,p_);
					if (actions.get(t0) != null){
						for (int i = 0; i < actions.get(t0).size(); i++){
							if (actions.get(t0).get(i).isTau()){ // p -> p_ is internal
								for (CompositeNode q_ : succList.get(p_)){
									Pair t1 = new Pair(p_,q_);
									for (int j = 0; j < actions.get(t1).size(); j++){
										String lbl = actions.get(t1).get(j).getLabel();
										Boolean isF = actions.get(t1).get(j).isFaulty();
										Boolean isTau = actions.get(t1).get(j).isTau();
										if (!isF){ //don't saturate faulty actions
											for (CompositeNode q : succList.get(q_)){
												Pair t2 = new Pair(q_,q);
												if (actions.get(t2) != null){
													for (int k = 0; k < actions.get(t2).size(); k++){
														if (actions.get(t2).get(k).isTau()){ // q_ -> q is internal
															//add transition for later update
															if (!hasEdge(p,q,actions.get(t1).get(j))){
																fsts.add(p);
																snds.add(q);
																lbls.add(lbl);
																isTaus.add(isTau);
																//isFs.add(isF);
																change = true;		
															}	
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

			//update transition system
			for (int i = 0; i < fsts.size(); i++){
				//System.out.println(fsts.get(i) + "\n" + snds.get(i) + "\n" + lbls.get(i)+ "\n" + isTaus.get(i) + "\n=========================\n");
				addEdge(fsts.get(i), snds.get(i), new Action(lbls.get(i), false, isTaus.get(i), isSpec));
			}
		}

	
	}
	
}