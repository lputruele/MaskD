package maskingDistance;

import java.util.*;
import graph.*;
import java.io.*;
import faulty.*;

public class MaskingDistance{

	private GameGraph g; // The masking distance game graph, undefined until buildGraph is called
    private boolean bisim = true;

	public MaskingDistance(){

	}

	public GameGraph getG(){
		return g;
	}

	public void buildGraph(Program specProgram, Program impProgram, boolean deadlockIsError){
		//This method builds a game graph for the Masking Distance Game, there are two players: the Refuter(R) and the Verifier(V)
		//The refuter plays with the implementation(imp), this means choosing any action available (faulty or not)
		//and the verifier plays with the specification(spec), he tries to match the action played by the refuter, if he can't then an error state is reached.
		ExplicitCompositeModel spec,imp;
		System.out.println("Building Models...");
		spec = specProgram.toGraph(true);
		imp = impProgram.toGraph(false);
		System.out.println("Saturating Models...");
		spec.saturate();
		imp.saturate();
		spec.createDot(false);
		imp.createDot(true);
		System.out.println("Building Game Graph...");
		g = new GameGraph();

        //calculate initial state
        GameNode init = new GameNode(spec.getInitial(), imp.getInitial(),new Action("", false, false, false) ,"R");
        g.addNode(init);
        g.setInitial(init);

        TreeSet<GameNode> iterSet = new TreeSet<GameNode>();
        iterSet.add(g.getInitial());

        //build the game graph
        while(!iterSet.isEmpty()){
            GameNode curr = iterSet.pollFirst();
            if (deadlockIsError && imp.getSuccessors(curr.getImpState()).size() == 1){ // this is a special deadlock case
            		g.addEdge(curr,g.getErrState(),new Action("ERR", false, false, false));
            }
            if (curr.getPlayer() == "R"){ //if player is refuter we add its possible moves from current state
                //IMP MOVES
            	for (CompositeNode succ : imp.getSuccessors(curr.getImpState())){
            		Pair p = new Pair(curr.getImpState(),succ);
            		if (imp.getActions().get(p) != null){
            			for (int i=0; i < imp.getActions().get(p).size(); i++){
                            GameNode curr_ = new GameNode(curr.getSpecState(),succ,imp.getActions().get(p).get(i), "V");
                            GameNode toOld = g.search(curr_);
		                    if (toOld == null){
			            		g.addNode(curr_);
			            		if (curr_.getSymbol().isFaulty())
			            			curr_.setMask(true); //ahora quedo medio irrelevante el mask
			            		g.addEdge(curr,curr_, curr_.getSymbol()); 
			            		iterSet.add(curr_);
			            	}
			            	else{
			            		g.addEdge(curr,toOld, toOld.getSymbol());
			            	}
            			}
            		}
            	}
                //SPEC MOVES
                if (bisim){
                    for (CompositeNode succ : spec.getSuccessors(curr.getSpecState())){
                        Pair p = new Pair(curr.getSpecState(),succ);
                        if (spec.getActions().get(p) != null){
                            for (int i=0; i < spec.getActions().get(p).size(); i++){
                                GameNode curr_ = new GameNode(succ,curr.getImpState(),spec.getActions().get(p).get(i), "V");
                                GameNode toOld = g.search(curr_);
                                if (toOld == null){
                                    g.addNode(curr_);
                                    g.addEdge(curr,curr_, curr_.getSymbol()); 
                                    iterSet.add(curr_);
                                }
                                else{
                                    g.addEdge(curr,toOld,toOld.getSymbol()); 
                                }
                            }
                        }
                    }
                }
            }
            else{ //if player is verifier we add its matching move from current state or err transition if can't match
            	/*if (spec.getSuccessors(curr.getSpecState()).size() == 1 && spec.getSuccessors(curr.getSpecState()).first() == curr.getSpecState()
            		&& curr.getSpecState().getIsFaulty()){
            		g.addEdge(curr,g.getErrState(),"ERR", false);
            	}*/
            	boolean foundSucc = false;
                //SPEC MOVES
                if (!curr.getSymbol().isFromSpec()){
                    if (curr.getMask()){ //this means the state has to mask a previous fault
                        GameNode curr_ = new GameNode(curr.getSpecState(),curr.getImpState(),new Action("",false,false,false), "R");
                        GameNode toOld = g.search(curr_);
                        if (toOld == null){
                            g.addNode(curr_);
                            g.addEdge(curr,curr_,new Action("M"+curr.getSymbol().getLabel(),false,false,true,true));
                            iterSet.add(curr_);
                        }
                        else{
                            g.addEdge(curr,toOld,new Action("M"+curr.getSymbol().getLabel(),false,false,true,true));
                        }
                        foundSucc = true;
                    }
                    else{
    	            	for (CompositeNode succ : spec.getSuccessors(curr.getSpecState())){
    		            	Pair p = new Pair(curr.getSpecState(),succ);
    		            	if (spec.getActions().get(p) != null){
    	            			for (int i=0; i < spec.getActions().get(p).size(); i++){
    	            				Action lblImp = curr.getSymbol();
    	            				Action lblSpec = spec.getActions().get(p).get(i);
    	            				if (lblImp.getLabel().equals(lblSpec.getLabel()) || (lblImp.isTau() && lblSpec.isTau())){
    				            		GameNode curr_ = new GameNode(succ,curr.getImpState(), new Action("",false,false,false), "R");
    				            		GameNode toOld = g.search(curr_);
    				                    if (toOld == null){
    					            		g.addNode(curr_);
    					            		g.addEdge(curr,curr_, lblSpec); //add label may not be necessary
    					            		iterSet.add(curr_);
    					            	}
    					            	else{
    					            		g.addEdge(curr,toOld, lblSpec);
    					            	}
    					            	foundSucc = true;
    					            	break;
    					            }
    	            			}
    		            	}
    	            	}
                    }
                }
                else{//IMP MOVES
                    if (bisim){
                        for (CompositeNode succ : imp.getSuccessors(curr.getImpState())){
                            Pair p = new Pair(curr.getImpState(),succ);
                            if (imp.getActions().get(p) != null){
                                for (int i=0; i < imp.getActions().get(p).size(); i++){
                                    Action lblSpec = curr.getSymbol();
                                    Action lblImp = imp.getActions().get(p).get(i);
                                    if (lblImp.getLabel().equals(lblSpec.getLabel()) || (lblImp.isTau() && lblSpec.isTau())){
                                        GameNode curr_ = new GameNode(curr.getSpecState(),succ, new Action("",false,false,false), "R");
                                        GameNode toOld = g.search(curr_);
                                        if (toOld == null){
                                            g.addNode(curr_);
                                            g.addEdge(curr,curr_, lblImp); //add label may not be necessary
                                            iterSet.add(curr_);
                                        }
                                        else{
                                            g.addEdge(curr,toOld, lblImp);
                                        }
                                        foundSucc = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            	if (!foundSucc){
        			g.addEdge(curr,g.getErrState(),new Action("ERR", false, false, false));
            	}
            }
        }
        //System.out.println(g.createDot());
	}

	

	public double calculateDistance(Program specProgram, Program impProgram, boolean deadlockIsError, boolean noBisim){
        bisim = !noBisim;
		int faultsMasked = Integer.MAX_VALUE;
        boolean found = false;
    	buildGraph(specProgram, impProgram, deadlockIsError);
    	System.out.println("Calculating Distance...");
    	DynamicMatrix2D<TreeSet<GameNode>> uSets = new DynamicMatrix2D<TreeSet<GameNode>>(); //dynamic programming matrix
    	TreeSet<GameNode> initialSet = new TreeSet<GameNode>();

        //BASE CASES
        for (int i = 0; i < uSets.getRowLength(); i++){
            uSets.set(0,i, new TreeSet<GameNode>());
            uSets.set(i,0, new TreeSet<GameNode>());
        }
    	initialSet.add(g.getErrState());
    	uSets.set(1,1,initialSet); // base case: 1,1 has error State set

        TreeSet<GameNode> currSet,newSet;

        //INDUCTIVE CASE
        //while (!found){
        	for (int j = 1; j < uSets.getColLength() && !found; j++){ //estos ciclos estan mal
        		for (int i = 1; i < uSets.getRowLength() && !found; i++){
                    if (uSets.get(i,j) == null){
            			currSet = uSets.get(i,j-1);
            			newSet = new TreeSet<GameNode>();
            			//TreeSet<GameNode> newSetF = new TreeSet<GameNode>();
            			if (currSet != null){
                        //System.out.println(uSets.get(i,j));
            				for (GameNode gn : currSet){
            					//if (gn == g.getInitial() && i < faultsMasked)
            					//	faultsMasked = i;
                                if (gn.getPlayer().equals("V")){
                                    for (GameNode gnPre : g.getPredecessors(gn)){
                                        newSet.add(gnPre);
                                    }
                                }
                                else{ //V
                                    for (GameNode gnPre : g.getPredecessors(gn)){
                                        boolean doAdd = true;
                                        for (GameNode gnPrePos : g.getSuccessors(gnPre)){
                                            int j_ = j-1;
                                            if (uSets.get(i,j_) != null){
                                                while (j_ > 0 && !uSets.get(i,j_).contains(gnPrePos)){
                                                    j_--;
                                                }
                                            }
                                            if (j_ == 0){
                                                doAdd = false;
                                            }
                                        }
                                        if (!gnPre.getSymbol().isFaulty() && doAdd)
                                            newSet.add(gnPre);
                                    }
                                }
                            }
                        }
                        currSet = uSets.get(i-1,j-1);
                        if (currSet != null){
                            for (GameNode gn : currSet){
                                if (gn.getPlayer().equals("R") || gn.isErrState()){
                                    for (GameNode gnPre : g.getPredecessors(gn)){
                                        boolean doAdd = true;
                                        for (GameNode gnPrePos : g.getSuccessors(gnPre)){
                                            int j_ = j-1;
                                            while (j_ > 0){
                                                int i_ = i-1;
                                                if (uSets.get(i_,j_) != null){
                                                    while (i_ > 0 && !uSets.get(i_,j_).contains(gnPrePos)){
                                                        i_--;
                                                    }
                                                }
                                                if (i_ > 0)
                                                    break;
                                                j_--;
                                            }
                                            if (j_ == 0){
                                                doAdd = false;
                                            }
                                        }
                                        if (gnPre.getSymbol().isFaulty() && doAdd)
                                            newSet.add(gnPre);
                                    }
                                    for (GameNode gnPre : g.getPredecessors(gn)){
                                        if (gnPre.getSymbol().isFaulty())
                                            newSet.add(gnPre);
                                    }
                                }
                            }
                        }
                        //System.out.println(newSet);
                        if (newSet.contains(g.getInitial()) && !found){
                            //System.out.println("step:"+j+" faults:"+i);
                            faultsMasked = i;
                            found = true;
                        }
                        
            			uSets.set(i,j,newSet);
                    }
        		} 
        	}
      //  }
    	//System.out.println(uSets.toString());
    	return Math.round((double)1/faultsMasked * Math.pow(10, 3)) / Math.pow(10, 3);
    }

    /*public double calculateDistancePaper(Program specProgram, Program impProgram, boolean deadlockIsError){
        int faultsMasked = Integer.MAX_VALUE;
        boolean found = false;
        buildGraph(specProgram, impProgram, deadlockIsError);
        System.out.println("Calculating Distance...");
        DynamicMatrix2D<TreeSet<GameNode>> uSets = new DynamicMatrix2D<TreeSet<GameNode>>(); //dynamic programming matrix
        TreeSet<GameNode> initialSet = new TreeSet<GameNode>();

        //BASE CASES
        for (int i = 0; i < uSets.getRowLength(); i++){
            uSets.set(0,i, new TreeSet<GameNode>());
            uSets.set(i,0, new TreeSet<GameNode>());
        }
        initialSet.add(g.getErrState());
        uSets.set(1,1,initialSet); // base case: 1,1 has error State set

        TreeSet<GameNode> currSet,newSet;

        //INDUCTIVE CASE
        //while (!found){
            for (int j = 1; j < uSets.getColLength() && !found; j++){ //estos ciclos estan mal
                for (int i = 1; i < uSets.getRowLength() && !found; i++){
                    if (uSets.get(i,j) == null){
                        currSet = uSets.get(i,j-1);
                        newSet = new TreeSet<GameNode>();
                        //TreeSet<GameNode> newSetF = new TreeSet<GameNode>();
                        if (currSet != null){
                        //System.out.println(uSets.get(i,j));
                            for (GameNode gn : currSet){
                                //if (gn == g.getInitial() && i < faultsMasked)
                                //  faultsMasked = i;
                                if (gn.getPlayer().equals("V")){
                                    for (GameNode gnPre : g.getPredecessors(gn)){
                                        newSet.add(gnPre);
                                    }
                                }
                                else{ //V
                                    for (GameNode gnPre : g.getPredecessors(gn)){
                                        boolean doAdd = true;
                                        for (GameNode gnPrePos : g.getSuccessors(gnPre)){
                                            int j_ = j-1;
                                            if (uSets.get(i,j_) != null){
                                                while (j_ > 0 && !uSets.get(i,j_).contains(gnPrePos)){
                                                    j_--;
                                                }
                                            }
                                            if (j_ == 0){
                                                doAdd = false;
                                            }
                                        }
                                        if (!gnPre.getSymbol().isFaulty() && doAdd)
                                            newSet.add(gnPre);
                                    }
                                }
                            }
                        }
                        currSet = uSets.get(i-1,j-1);
                        if (currSet != null){
                            for (GameNode gn : currSet){
                                if (gn.getPlayer().equals("R") || gn.isErrState()){
                                    for (GameNode gnPre : g.getPredecessors(gn)){
                                        boolean doAdd = true;
                                        for (GameNode gnPrePos : g.getSuccessors(gnPre)){
                                            int j_ = j-1;
                                            while (j_ > 0){
                                                int i_ = i-1;
                                                if (uSets.get(i_,j_) != null){
                                                    while (i_ > 0 && !uSets.get(i_,j_).contains(gnPrePos)){
                                                        i_--;
                                                    }
                                                }
                                                if (i_ > 0)
                                                    break;
                                                j_--;
                                            }
                                            if (j_ == 0){
                                                doAdd = false;
                                            }
                                        }
                                        if (gnPre.getSymbol().isFaulty() && doAdd)
                                            newSet.add(gnPre);
                                    }
                                    for (GameNode gnPre : g.getPredecessors(gn)){
                                        if (gnPre.getSymbol().isFaulty())
                                            newSet.add(gnPre);
                                    }
                                }
                            }
                        }
                        //System.out.println(newSet);
                        if (newSet.contains(g.getInitial()) && !found){
                            System.out.println("step:"+j+" faults:"+i);
                            faultsMasked = i;
                            found = true;
                        }
                        
                        uSets.set(i,j,newSet);
                        System.out.println("% " + "step:"+j+" faults:"+i );
                    }
                } 
            }
      //  }
        //System.out.println(uSets.toString());
        return Math.round((double)1/faultsMasked * Math.pow(10, 3)) / Math.pow(10, 3);
    }*/

    public double calculateDistanceBFS(Program specProgram, Program impProgram, boolean deadlockIsError, boolean noBisim){
        bisim = !noBisim;
        buildGraph(specProgram, impProgram, deadlockIsError);
        System.out.println("Calculating Distance...");

        g.getInitial().setDistanceValue(0);
        
        LinkedList<GameNode> q = new LinkedList<GameNode>() ;
        q.addFirst(g.getInitial());
        while (!q.isEmpty()) {
            GameNode v = q.getFirst();
            q.removeFirst();
            for (GameNode adj : g.getSuccessors(v)) {
                Pair p = new Pair(v,adj);
                for (Action a : g.getActions().get(p)){
                    int w = a.isFaulty()?1:0;
                    if (v.getDistanceValue() + w < adj.getDistanceValue()) {
                        adj.setDistanceValue(v.getDistanceValue() + w);
                        if (w == 1)
                            q.addLast(adj);
                        else
                            q.addFirst(adj);
                    }
                }
            }
        }

        int minDistance = g.getErrState().getDistanceValue();
        
        double res= Math.round((double)1/(1+minDistance) * Math.pow(10, 3)) / Math.pow(10, 3);
        return res;
    }


    public double calculateDistanceDeterministic(Program specProgram, Program impProgram, boolean deadlockIsError, boolean noBisim){
		// We use dijsktra's algorithm to find the shortest path to an error state
		// This is the main method of this class
        bisim = !noBisim;
    	buildGraph(specProgram, impProgram, deadlockIsError);
    	System.out.println("Calculating Distance...");
    	//System.out.println("State space: "+g.getNumNodes());
    	//System.out.println("Number of edges: "+g.getNumEdges());

        for(GameNode n : g.getNodes()){
        	n.setDistanceValue(Integer.MAX_VALUE);
        	n.setVisited(false);
        	n.setPreviousNodeInPath(null);
        }
        g.getInitial().setDistanceValue(0);
        //System.out.println("Nodes:"+g.getNodes().size());
        // Find shortest path for all vertices
        for (int count = 0; count < g.getNodes().size(); count++){
        	int min = Integer.MAX_VALUE;
        	int minIndex = 0;
        	GameNode from;
        	for (int i = 0;i<g.getNodes().size();i++){
        		if (!g.getNodes().get(i).getVisited() && g.getNodes().get(i).getDistanceValue() < min){
        			min = g.getNodes().get(i).getDistanceValue();
        			minIndex = i;
        		}
        	}
            from = g.getNodes().get(minIndex);
            from.setVisited(true);
            for (GameNode to : g.getSuccessors(from)){
                if (!to.getVisited()){
                	Pair p = new Pair(from,to);
                	int addedCost = 0;
                	for (int i=0; i < g.getActions().get(p).size(); i++)
	            		if (g.getActions().get(p).get(i).isFaulty())
							addedCost = 1;
                	if (from.getDistanceValue()+addedCost < to.getDistanceValue()){
                    	to.setDistanceValue(from.getDistanceValue() + addedCost);
                    	to.setPreviousNodeInPath(from);
                    }
                }
            }
        }

        int minDistance = g.getErrState().getDistanceValue();
        
        double res= Math.round((double)1/(1+minDistance) * Math.pow(10, 3)) / Math.pow(10, 3);
		return res;
    }
	
    //Only works together with -det
	public void printTraceToError(){
		System.out.println("\n·····ERROR PATH·····\n");
		GameNode curr = g.getErrState();
		int i = 0;
		while (curr != null){
			System.out.println(i+"."+curr.toString());
			curr = curr.getPreviousNodeInPath();
			i++;
		}
	}

	public void createDot(){
		g.createDot();
	}

	public void simulateGame(){
		GameNode curr;
		Stack<GameNode> track = new Stack<GameNode>();
		track.push(g.getInitial());
		Scanner sc = new Scanner(System.in);
		String c = "";
		System.out.println("\n·····SIMULATION·····\n");
		while (!c.equals("X") && !c.equals("x")){
			curr = track.peek();
			System.out.println("\n\nCURRENT STATE: ["+curr+"]\nChoose an action... (action : [nextstate])\n");
			Integer i = 0;
			for (GameNode succ : g.getSuccessors(curr)){
				Pair p = new Pair(curr,succ);
				if (g.getActions().get(p) != null){
	            	for (int j=0; j < g.getActions().get(p).size(); j++){
						System.out.println(i+". "+g.getActions().get(p).get(j).getLabel()+": "+"["+succ+"]");
						i++;
					}
				}
			}
			if (track.size()>1){
				System.out.println("Z. BACKTRACK");
			}
			System.out.println("X. EXIT");
			c = sc.next();

			i = 0;
			for (GameNode succ : g.getSuccessors(curr)){
				Pair p = new Pair(curr,succ);
				if (g.getActions().get(p) != null){
	            	for (int j=0; j < g.getActions().get(p).size(); j++){
						if (c.equals(i.toString()))
							track.push(succ);
						i++;
					}
				}
			}

			if (c.equals("Z") || c.equals("z")){
				if (track.size()>1)
				track.pop();
			}
		}
	}
}