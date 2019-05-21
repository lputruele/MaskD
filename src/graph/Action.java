package graph;

import java.util.*;
import faulty.*;


public class Action {

	private String label;
	private Boolean isFaulty;
	private Boolean isTau;
	private Boolean isFromSpec;
	private Boolean isMask;

	public Action(String l, Boolean isF, Boolean isT, Boolean ifs){
		label = l;
		isFaulty = isF;
		isTau = isT;
		isFromSpec = ifs;
		isMask = false;
	}

	public Action(String l, Boolean isF, Boolean isT, Boolean ifs, Boolean isM){
		label = l;
		isFaulty = isF;
		isTau = isT;
		isFromSpec = ifs;
		isMask = isM;
	}

	public String getLabel(){
		return label;
	}

	public void setLabel(String l){
		label = l;
	}

	public void setIsMask(Boolean m){
		isMask = m;
	}

	public Boolean isMask(){
		return isMask;
	}

	public Boolean isFaulty(){
		return isFaulty;
	}

	public Boolean isTau(){
		return isTau;
	}

	public Boolean isFromSpec(){
		return isFromSpec;
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Action){
			Action a = (Action)o;
			return label.equals(a.getLabel()) && isFaulty.equals(a.isFaulty()) && isTau.equals(a.isTau()) && isFromSpec.equals(a.isFromSpec());
		}
		return false;
	}

}