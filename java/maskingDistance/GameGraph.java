package maskingDistance;

import java.util.*;
import graph.*;
import java.io.*;

public class GameGraph{

	private HashMap<GameNode, TreeSet<GameNode>> succList; // Successors adjacency list
	private HashMap<GameNode, TreeSet<GameNode>> preList; // Predecessors adjacency list
	private HashMap<Pair, LinkedList<String>> labels; // Labels for edges
	private HashMap<Pair, LinkedList<Boolean>> faultyActions; // Which edges correspond to faulty actions
	private HashMap<Pair, LinkedList<Boolean>> tauActions;
	private GameNode initial; // Initial state
	private LinkedList<GameNode> nodes; // States
	private int numNodes;
	private int numEdges;
	private GameNode errState; // Special error state

	public GameGraph() {
		succList = new HashMap<GameNode, TreeSet<GameNode>>();
		preList = new HashMap<GameNode, TreeSet<GameNode>>();
		labels = new HashMap<Pair, LinkedList<String>>();
		faultyActions = new HashMap<Pair, LinkedList<Boolean>>();
		tauActions = new HashMap<Pair, LinkedList<Boolean>>();
		numNodes = numEdges = 0;
		nodes = new LinkedList<GameNode>();
		errState = new GameNode(null,null,"ERR","");
		addNode(errState);

	}


	public GameNode getErrState(){		
		return errState;
	}

	public int getNumNodes(){
		return numNodes;
	}

	public int getNumEdges(){
		return numEdges;
	}

	public void setInitial(GameNode v){
		initial = v;
	}

	public GameNode getInitial(){
		return initial;
	}

	public HashMap<Pair, LinkedList<String>> getLabels(){
		return labels;
	}

	public HashMap<Pair, LinkedList<Boolean>> getFaultyActions(){
		return faultyActions;
	}

	public void addNode(GameNode v) {
		nodes.add(v);
		succList.put(v, new TreeSet<GameNode>());
		preList.put(v, new TreeSet<GameNode>());
		numNodes += 1;
	}

	public GameNode search(GameNode v) {
		for (GameNode node : nodes){
			if (node.equals(v))
				return node;
		}
		return null;
	}

	public boolean hasNode(GameNode v) {
		return nodes.contains(v);
	}


	public boolean hasEdge(GameNode from, GameNode to, String lbl) {
		if (!hasNode(from) || !hasNode(to))
			return false;
		Pair transition = new Pair(from,to);
		if (labels.get(transition) == null)
			return false;
		return succList.get(from).contains(to) && preList.get(to).contains(from) && labels.get(transition).contains(lbl);
	}


	public void addEdge(GameNode from, GameNode to, String lbl, Boolean faulty, Boolean internal) {
		if (to != null){
			if (hasEdge(from, to, lbl))
				return;
			numEdges += 1;
			succList.get(from).add(to);
			preList.get(to).add(from);
			Pair transition = new Pair(from,to);
			if (labels.get(transition) == null){
				labels.put(transition,new LinkedList<String>());
				faultyActions.put(transition,new LinkedList<Boolean>());
				tauActions.put(transition,new LinkedList<Boolean>());
			}
			labels.get(transition).add(lbl);
			faultyActions.get(transition).add(faulty);
			tauActions.get(transition).add(internal);
			//check if label already added
			/*boolean addLabel = true;
			for (String l : labels.get(transition)){
				if (l.equals(lbl))
					addLabel = false;
			}
			if (addLabel){
				numEdges += 1;
				labels.get(transition).add(lbl);
				faultyActions.get(transition).add(faulty);
			}*/
		}
	}

	public LinkedList<GameNode> getNodes(){
		return nodes;
	}

	public TreeSet<GameNode> getSuccessors(GameNode v){
		return succList.get(v);
	}

	public TreeSet<GameNode> getPredecessors(GameNode v){
		return preList.get(v);
	}

	public String createDot(){
		String res = "digraph model {\n\n";
		res += "    node [style=filled];\n";
		for (GameNode v : nodes){
			if (v.getPlayer().equals("V"))
				res += "    "+v.toStringDot()+" [color=\"lightblue\"];\n";
			if (v.getPlayer().equals("R"))
				res += "    "+v.toStringDot()+" [color=\"grey\"];\n";
			if (v.getPlayer().equals(""))
				res += "    "+v.toStringDot()+" [color=\"red\"];\n";
			for (GameNode u : succList.get(v)){
				Pair edge = new Pair(v,u);
				if (labels.get(edge) != null)
					for (int i=0; i < labels.get(edge).size(); i++)			
						if (labels.get(edge).get(i).split("M")[0].equals(""))
							res += "    "+v.toStringDot()+" -> "+ u.toStringDot() +" [color=\"green\",label = \""+labels.get(edge).get(i)+"\"]"+";\n";
						else
							if (faultyActions.get(edge).get(i))
								res += "    "+v.toStringDot()+" -> "+ u.toStringDot() +" [color=\"red\",label = \""+labels.get(edge).get(i)+"\"]"+";\n";
							else
								if (tauActions.get(edge).get(i))
									res += "    "+v.toStringDot()+" -> "+ u.toStringDot() + " [style=dashed,label = \""+labels.get(edge).get(i)+"\"]"+";\n";
								else
									res += "    "+v.toStringDot()+" -> "+ u.toStringDot() +" [label = \""+labels.get(edge).get(i)+"\"]"+";\n";
			}
		}
		res += "\n}";
		try{
            File file = new File("../out/" + "GameGraph" +".dot");
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
}