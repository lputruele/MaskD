package maskingDistance;

import java.util.*;
import graph.*;
import java.io.*;
import faulty.*;

public class MaskingDistance{

	private GameGraph g; // The masking distance game graph, undefined until buildGraph is called

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
		spec = specProgram.toGraph();
		imp = impProgram.toGraph();
		System.out.println("Saturating Models...");
		spec.saturate();
		imp.saturate();
		spec.createDot(false);
		imp.createDot(true);
		System.out.println("Building Game Graph...");
		g = new GameGraph();

        //calculate initial state
        GameNode init = new GameNode(spec.getInitial(), imp.getInitial(), "","R");
        g.addNode(init);
        g.setInitial(init);

        TreeSet<GameNode> iterSet = new TreeSet<GameNode>();
        iterSet.add(g.getInitial());

        //build the game graph
        while(!iterSet.isEmpty()){
            GameNode curr = iterSet.pollFirst();
            if (deadlockIsError && imp.getSuccessors(curr.getImpState()).size() == 1){ // this is a special deadlock case
            		g.addEdge(curr,g.getErrState(),"ERR", false, false);
            }
            if (curr.getPlayer() == "R"){ //if player is refuter we add its possible moves from current state
            	for (CompositeNode succ : imp.getSuccessors(curr.getImpState())){
            		Pair p = new Pair(curr.getImpState(),succ);
            		if (imp.getLabels().get(p) != null){
            			for (int i=0; i < imp.getLabels().get(p).size(); i++){
            				String lbl = imp.getLabels().get(p).get(i);
            				GameNode curr_ = new GameNode(curr.getSpecState(),succ,lbl, "V");
		            		GameNode toOld = g.search(curr_);
		            		boolean f = imp.getFaultyActions().get(p).get(i);
		            		boolean isTau = imp.getTauActions().get(p).get(i);
		                    if (toOld == null){
			            		g.addNode(curr_);
			            		if (f)
			            			curr_.setMask(true);
			            		g.addEdge(curr,curr_,curr_.getSymbol(), f, isTau); 
			            		iterSet.add(curr_);
			            	}
			            	else{
			            		g.addEdge(curr,toOld,toOld.getSymbol(), f, isTau);
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
            	if (curr.getMask()){ //this means the state has to mask a previous fault
            		GameNode curr_ = new GameNode(curr.getSpecState(),curr.getImpState(),"", "R");
            		GameNode toOld = g.search(curr_);
                    if (toOld == null){
	            		g.addNode(curr_);
	            		g.addEdge(curr,curr_,"M"+curr.getSymbol(), false, false); //add label may not be necessary
	            		iterSet.add(curr_);
	            	}
	            	else{
	            		g.addEdge(curr,toOld,"M"+curr.getSymbol(), false, false);
	            	}
	            }
	            else{
	            	boolean foundSucc = false;
	            	for (CompositeNode succ : spec.getSuccessors(curr.getSpecState())){
		            	Pair p = new Pair(curr.getSpecState(),succ);
		            	if (spec.getLabels().get(p) != null){
	            			for (int i=0; i < spec.getLabels().get(p).size(); i++){
	            				String lblImp = curr.getSymbol();
	            				String lblSpec = spec.getLabels().get(p).get(i);
	            				if (lblImp.equals(lblSpec) || (lblImp.charAt(0)=='&' && lblSpec.charAt(0)=='&')){
				            		GameNode curr_ = new GameNode(succ,curr.getImpState(),"", "R");
				            		GameNode toOld = g.search(curr_);
				            		boolean f = spec.getFaultyActions().get(p).get(i);
		            				boolean isTau = spec.getTauActions().get(p).get(i);
		            				String lbl = lblSpec;
				                    if (toOld == null){
					            		g.addNode(curr_);
					            		g.addEdge(curr,curr_,lbl, f, isTau); //add label may not be necessary
					            		iterSet.add(curr_);
					            	}
					            	else{
					            		g.addEdge(curr,toOld,lbl, f, isTau);
					            	}
					            	foundSucc = true;
					            	break;
					            }
	            			}
		            	}
	            	}
	            	if (!foundSucc){
	        			g.addEdge(curr,g.getErrState(),"ERR", false, false);
	            	}
	            }
            }
        }
        //System.out.println(g.createDot());
	}

	/*public void buildGraphOptimized(Program specProgram, Program impProgram){
		//This method builds a game graph for the Masking Distance Game, there are two players: the Refuter(R) and the Verifier(V)
		//The refuter plays with the implementation(imp), this means choosing any action available (faulty or not)
		//and the verifier plays with the specification(spec), he tries to match the action played by the refuter, if he can't then an error state is reached.
		//This version has some optimizations, namely: Got rid of M transitions
		ExplicitCompositeModel spec,imp;
		spec = specProgram.toGraph();
		imp = impProgram.toGraph();

		g = new GameGraph();

        //calculate initial state
        GameNode init = new GameNode(spec.getInitial(), imp.getInitial(), "","R");
        g.addNode(init);
        g.setInitial(init);

        TreeSet<GameNode> iterSet = new TreeSet<GameNode>();
        iterSet.add(g.getInitial());

        //build the game graph
        while(!iterSet.isEmpty()){
            GameNode curr = iterSet.pollFirst();
            if (curr.getPlayer() == "R"){ //if player is refuter we add its possible moves from current state
            	for (CompositeNode succ : imp.getSuccessors(curr.getImpState())){
            		Pair p = new Pair(curr.getImpState(),succ);
            		if (imp.getLabels().get(p) != null){
            			for (int i=0; i < imp.getLabels().get(p).size(); i++){
            				boolean f = imp.getFaultyActions().get(p).get(i);
		            		GameNode curr_;
		            		if (f)
		            			curr_ = new GameNode(curr.getSpecState(),succ,"", "R");
		            		else
		            			curr_ = new GameNode(curr.getSpecState(),succ,imp.getLabels().get(p).get(i), "V");
		            		GameNode toOld = g.search(curr_);
		                    if (toOld == null){
			            		g.addNode(curr_);
			            		g.addEdge(curr,curr_,imp.getLabels().get(p).get(i), f); 
			            		iterSet.add(curr_);
			            	}
			            	else{
			            		g.addEdge(curr,toOld,imp.getLabels().get(p).get(i), f);
			            	}
            			}
            		}
            	}
            }
            else{ //if player is verifier we add its matching move from current state or err transition if can't match
	            	boolean foundSucc = false;
	            	for (CompositeNode succ : spec.getSuccessors(curr.getSpecState())){
		            	Pair p = new Pair(curr.getSpecState(),succ);
		            	if (imp.getLabels().get(p) != null){
	            			for (int i=0; i < imp.getLabels().get(p).size(); i++){
	            				if (curr.getSymbol().equals(spec.getLabels().get(p).get(i))){
				            		GameNode curr_ = new GameNode(succ,curr.getImpState(),"", "R");
				            		GameNode toOld = g.search(curr_);
				                    if (toOld == null){
					            		g.addNode(curr_);
					            		g.addEdge(curr,curr_,spec.getLabels().get(p).get(i), spec.getFaultyActions().get(p).get(i)); //add label may not be necessary
					            		iterSet.add(curr_);
					            	}
					            	else{
					            		g.addEdge(curr,toOld,spec.getLabels().get(p).get(i), spec.getFaultyActions().get(p).get(i));
					            	}
					            	foundSucc = true;
					            	break;
					            }
	            			}
	            		}
	            	}
	            	if (!foundSucc){
			            g.addEdge(curr,g.getErrState(),"ERR", false);
	            	}
	            
            }
        }
        //System.out.println(g.createDot());
	}*/

	public double calculateDistance2(Program specProgram, Program impProgram, boolean deadlockIsError){
		int faultsMasked = Integer.MAX_VALUE;
    	buildGraph(specProgram, impProgram, deadlockIsError);
    	System.out.println("Calculating Distance...");

    	DynamicMatrix2D<TreeSet<GameNode>> uSets = new DynamicMatrix2D<TreeSet<GameNode>>(); //dynamic programming matrix
    	TreeSet<GameNode> initialSet = new TreeSet<GameNode>();
    	initialSet.add(g.getErrState());
    	uSets.set(1,1,initialSet); // base case: 1,1 has error State set
    	for (int i = 1; i < uSets.getRowLength()-1; i++){
    		for (int j = 1; j < uSets.getColLength()-1; j++){
    			TreeSet<GameNode> currSet = uSets.get(i,j);
    			TreeSet<GameNode> newSet = new TreeSet<GameNode>();
    			TreeSet<GameNode> newSetF = new TreeSet<GameNode>();
    			if (currSet != null){
    				for (GameNode gn : currSet){
    					//System.out.println(gn);
    					if (gn == g.getInitial() && i < faultsMasked)
    						faultsMasked = i;
    					if (gn.getPlayer().equals("R") || gn.isErrState()){
    						for (GameNode gnPre : g.getPredecessors(gn)){ //for each predecessor V see if all its successors are in some previously computed set
    							//boolean found = false;
    							if (gnPre.getMask()){ //a fault occurred
    								int founds = 0;
    								for (GameNode gnPreSucc : g.getSuccessors(gnPre)){
    									boolean found = false;
    									for (int k = i; k > 0 && !found; k--){
		    								for (int l = j; l > 0 && !found; l--){
		    									if (uSets.get(k,l)!= null){
	    											if (uSets.get(k,l).contains(gnPreSucc)){
			    										founds++;
			    										found = true;
	    											}
		    									}
		    								}
		    							}
    								}
    								if (founds == g.getSuccessors(gnPre).size()){
    									if (gnPre == g.getInitial() && i < faultsMasked)
    										faultsMasked = i;
    									System.out.println("añado Rs");
		    							newSetF.add(gnPre);
		    						}
    								/*for (int k = i; k > 0 && !found; k--){
	    								for (int l = j; l > 0 && !found; l--){
	    									if (uSets.get(k,l)!= null){
	    										for (GameNode gnPreSucc : g.getSuccessors(gnPre)){
	    											if (uSets.get(k,l).contains(gnPreSucc)){
			    										newSetF.add(gnPre);
			    										found = true;
	    											}
	    										}
	    									}
	    								}
	    							}*/
    							}
    							else{ //a fault didnt occur
    								int founds = 0;
    								for (GameNode gnPreSucc : g.getSuccessors(gnPre)){
    									boolean found = false;
	    								for (int l = j; l > 0 && !found; l--){
	    									if (uSets.get(i,l)!= null){
    											if (uSets.get(i,l).contains(gnPreSucc)){
		    										founds++;
		    										found = true;
    											}
	    									}
	    								}
    								}
    								if (founds == g.getSuccessors(gnPre).size()){
    									if (gnPre == g.getInitial() && i < faultsMasked)
    										faultsMasked = i;
    									System.out.println("añado R2s");
		    							newSet.add(gnPre);
		    						}
    							}
    							
	    					}
    					}
    					else{ //V
    						System.out.println("añado Vs");
    						newSet.addAll(g.getPredecessors(gn));
    					}
    				}
    				/*if (newSet.contains(g.getInitial()))
    					faultsMasked = i;
    				if (newSetF.contains(g.getInitial()))
    					faultsMasked = i+1;*/
    				if (!newSet.isEmpty()){
    					uSets.set(i,j+1,newSet); //case 1
    					if (newSet.contains(g.getInitial()) && i < faultsMasked)
    						faultsMasked = i;
    				}
    				else{
    					if (!newSetF.isEmpty()){
    						uSets.set(i+1,j+1,newSetF); //case 2
    						if (newSetF.contains(g.getInitial()) && i+1 < faultsMasked)
    							faultsMasked = i+1;
    					}
    				}	
    			}
    		}
    	}
    	//System.out.println(uSets.toString());
    	return Math.round((double)1/faultsMasked * Math.pow(10, 3)) / Math.pow(10, 3);
    }

    public double calculateDistance(Program specProgram, Program impProgram, boolean deadlockIsError){
		// We use dijsktra's algorithm to find the shortest path to an error state
		// This is the main method of this class
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
                	for (int i=0; i < g.getFaultyActions().get(p).size(); i++)
	            		if (g.getFaultyActions().get(p).get(i))
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

    public double calculateDistance3(Program specProgram, Program impProgram, boolean deadlockIsError){
    	buildGraph(specProgram, impProgram, deadlockIsError);
    	System.out.println("Calculating Distance...");

    	TreeSet<GameNode> rws = new TreeSet<GameNode>(); //refuter winning states
        LinkedList<GameNode> currs = new LinkedList<GameNode>();
        rws.add(g.getErrState());
        currs.add(g.getErrState());
        while (!currs.isEmpty()){
        	if (currs.getFirst() == g.getInitial())
        		break;
        	GameNode curr = currs.removeFirst();

        	curr.setVisited(true);
        	if (curr.getPlayer().equals("V")){ //add all refuter predecessors
	        	for (GameNode pre : g.getPredecessors(curr)){
	        		if (!pre.getVisited()){
		        		Pair p = new Pair(pre,curr);
	                	int addedCost = 0;
	                	for (int i=0; i < g.getFaultyActions().get(p).size(); i++)
		            		if (g.getFaultyActions().get(p).get(i))
								addedCost = 1;
						pre.setDistanceValue(curr.getDistanceValue()+addedCost);
		        		rws.add(pre);
		        		currs.add(pre);
		        	}
	        	}
	        }
	        else{ //only add verifier predecessors if its successors are all included in rws
	        	for (GameNode pre : g.getPredecessors(curr)){
	        		if (!pre.getVisited()){
		        		boolean valid = true;
		        		for (GameNode preSucc : g.getSuccessors(pre))
		        			if (!rws.contains(preSucc))
		        				valid = false;
		  				if (valid){
		  					pre.setDistanceValue(curr.getDistanceValue());
		        			rws.add(pre);
		        			currs.add(pre);
		        		}
	        		}
	        	}
	        }
	        /*for (GameNode next : g.getPredecessors(curr)){
	        	if (!next.getVisited())
	        		currs.add(next);
	        }*/
        }
        //System.out.println(g.getInitial());
        //System.out.println(g.getInitial().getDistanceValue());
        //System.out.println(rws.contains(g.getInitial()));
        //if (rws.contains(g.getInitial())){
        if (!currs.isEmpty()){
        	int minDistance = g.getInitial().getDistanceValue();
        	double res= Math.round((double)1/(1+minDistance) * Math.pow(10, 3)) / Math.pow(10, 3);
        	return res;
        }
        else
        	return 0;
    }
	
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
				if (g.getLabels().get(p) != null){
	            	for (int j=0; j < g.getLabels().get(p).size(); j++){
						System.out.println(i+". "+g.getLabels().get(p).get(j)+": "+"["+succ+"]");
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
				if (g.getLabels().get(p) != null){
	            	for (int j=0; j < g.getLabels().get(p).size(); j++){
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