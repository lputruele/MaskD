package maskingDistance;

import java.util.*;
import graph.*;
import java.io.*;

public class GameNode implements Comparable{

	CompositeNode specState; // Current state of the Specification
	CompositeNode impState; // Current state of the Implementation
	String symbol; // The symbol of the action that lead to this state
	String player; // The player that has to play from here
	boolean mask; // True if the player has to mask this.symbol
	boolean visited; // Utility for graph traversal algorithms
	int distanceValue; // Value of the game for this node
	GameNode previousNodeInPath; // Previous Node in Shortest path to errState

	public GameNode(){
		visited = false;
		distanceValue = 0;
	}

	public GameNode(CompositeNode s, CompositeNode i, String sym, String p){
		specState = s;
		impState = i;
		symbol = sym;
		player = p;
		visited = false;
		distanceValue = 0;
	}

	public CompositeNode getSpecState(){
		return specState;
	}

	public CompositeNode getImpState(){
		return impState;
	}

	public String getSymbol(){
		return symbol;
	}

	public String getPlayer(){
		return player;
	}

	public boolean getMask(){
		return mask;
	}

	public void setMask(boolean m){
		mask = m;
	}

	public boolean getVisited(){
		return visited;
	}

	public void setVisited(boolean v){
		visited = v;
	}

	public int getDistanceValue(){
		return distanceValue;
	}

	public void setDistanceValue(int d){
		distanceValue = d;
	}

	public GameNode getPreviousNodeInPath(){
		return previousNodeInPath;
	}

	public void setPreviousNodeInPath(GameNode prev){
		previousNodeInPath = prev;
	}

	@Override
	public int compareTo(Object u) {
		if (u instanceof GameNode)
			if (this.equals((GameNode)u))
				return 0;
		return -1;
	}

	@Override
	public int hashCode(){
	    return Objects.hash(specState, impState, symbol, player);
	}


	public String toString(){
		String res;
		if (this.isErrState())
			res = "ERR_STATE";
		else
			res = "SPEC: "+specState.toString()+", SYMBOL: "+symbol+", IMP:"+impState.toString()+", PLAYER: "+player;
		return res;
	}

	public String toStringDot(){
		String res;
		if (this.isErrState())
			res = "ERR_STATE";
		else{
			String s = symbol.replaceAll("&","TAU");
			res = "SPEC"+specState.toStringDot()+"__"+s+"__"+"IMP"+impState.toStringDot()+"___"+player;
		}
		return res;
	}

	public boolean isErrState(){
		return symbol.equals("ERR");
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof GameNode){
			GameNode n = (GameNode)o;
			if (this.isErrState()){
				if (n.isErrState())
					return true;
				else
					return false;
			}
			if (n.isErrState()){
				return false;
			}
			return specState == n.getSpecState() && impState == n.getImpState() && symbol.equals(n.getSymbol()) && player.equals(n.getPlayer()) && mask == n.getMask();
		}
		return false;
	}
	
}