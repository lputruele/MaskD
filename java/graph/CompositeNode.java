package graph;

import java.util.*;
import faulty.*;


public class CompositeNode implements Comparable{
	HashMap<String,Boolean> state; // global state bool vars
	HashMap<String,String> stateEnums; // global state enum vars
	ExplicitCompositeModel model; // Global model whose this state belongs to
	boolean isFaulty; // Is this state a faulty one?

	public CompositeNode(){

	}

	public CompositeNode(ExplicitCompositeModel m){
		model = m;
		state = new HashMap<String,Boolean>();
		stateEnums = new HashMap<String,String>();
		for (Var v : model.getSharedVars()){
			if (v.hasEnumType())
				stateEnums.put(v.getName(),"");
			else
				state.put(v.getName(),false);
		}
		for (int i=0; i < model.getProcDecls().size(); i++){
			for (Var v : model.getProcs().get(i).getVarBool()){
				if (v.hasEnumType())
					stateEnums.put(model.getProcDecls().get(i)+v.getName(),"");
				else
					state.put(model.getProcDecls().get(i)+v.getName(),false);
			}
		}
	}

	@Override
	public int compareTo(Object u) {
		if (u instanceof CompositeNode)
			if (this.equals((CompositeNode)u))
				return 0;
		return -1;
	}

	@Override
	public int hashCode(){
	    return Objects.hash(state, stateEnums, model, isFaulty);
	}

	public HashMap<String,Boolean> getState(){
		return state;
	}

	public HashMap<String,String> getStateEnums(){
		return stateEnums;
	}

	public boolean getIsFaulty(){
		return isFaulty;
	}

	public ExplicitCompositeModel getModel(){
		return model;
	}

	public boolean satisfies(Expression e, int procIndex){
		return evalBoolExpr(e, procIndex);
	}

	public void checkNormCondition(Expression e, int procIndex){
		if (!satisfies(e, procIndex))
            isFaulty = true;
	}

	public String toString(){
		String res = "<";
		for (Var v : model.getSharedVars()){
			if (state.get(v.getName()))
				res += v.getName() + ",";
			if (v.hasEnumType() && stateEnums.get(v.getName())!=null)
				res += v.getName() + "=" +stateEnums.get(v.getName())+ ",";
		}
		for (int i=0; i < model.getProcDecls().size(); i++){
			for (Var v : model.getProcs().get(i).getVarBool()){
				if (state.get(model.getProcDecls().get(i)+v.getName()))
					res += model.getProcDecls().get(i)+v.getName() + ",";
			}
			for (Var v : model.getProcs().get(i).getVarEnum()){
				if (v.hasEnumType() && stateEnums.get(model.getProcDecls().get(i)+v.getName())!=null)
					res += model.getProcDecls().get(i)+v.getName()+ "=" +stateEnums.get(model.getProcDecls().get(i)+v.getName()) + ",";
			}
		}
		return res+">";
	}

	public String toStringDot(){
		String res = "";
		for (Var v : model.getSharedVars()){
			if (state.get(v.getName()))
				res += v.getName() + "_";
			if (v.hasEnumType() && stateEnums.get(v.getName())!=null)
				res += v.getName() + "" +stateEnums.get(v.getName())+ "_";
		}
		for (int i=0; i < model.getProcDecls().size(); i++){
			for (Var v : model.getProcs().get(i).getVarBool()){
				if (state.get(model.getProcDecls().get(i)+v.getName()))
					res += model.getProcDecls().get(i)+v.getName() + "_";
			}
			for (Var v : model.getProcs().get(i).getVarEnum()){
				if (v.hasEnumType() && stateEnums.get(model.getProcDecls().get(i)+v.getName())!=null)
					res += model.getProcDecls().get(i)+v.getName()+ "" +stateEnums.get(model.getProcDecls().get(i)+v.getName()) + "_";
			}
		}
		return res;
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof CompositeNode){
			CompositeNode n = (CompositeNode)o;
			for (Var var: model.getSharedVars()){
				if (state.get(var.getName()) != n.getState().get(var.getName()))
					return false;
				if (stateEnums.get(var.getName()) != null && !stateEnums.get(var.getName()).equals(n.getStateEnums().get(var.getName())))
					return false;
			}
			for (int i=0; i < model.getProcDecls().size(); i++){
				for (Var v : model.getProcs().get(i).getVarBool()){
					if (n.getState().get(model.getProcDecls().get(i)+v.getName()) != state.get(model.getProcDecls().get(i)+v.getName()))
						return false;
				}
				for (Var v : model.getProcs().get(i).getVarEnum()){
					if (!n.getStateEnums().get(model.getProcDecls().get(i)+v.getName()).equals(stateEnums.get(model.getProcDecls().get(i)+v.getName())))
						return false;
				}
			}
			return true;
		}
		return false;
	}

	public CompositeNode clone(){
		CompositeNode n = new CompositeNode();
		n.model = model;
		n.state = new HashMap<String,Boolean>();
		n.stateEnums = new HashMap<String,String>();
		for (Var v : model.getSharedVars()){
			if (v.hasEnumType())
				n.getStateEnums().put(v.getName(),stateEnums.get(v.getName()));
			else
				n.getState().put(v.getName(),state.get(v.getName()));
		}
		for (int i=0; i < model.getProcDecls().size(); i++){
			for (Var v : model.getProcs().get(i).getVarBool()){
				n.getState().put(model.getProcDecls().get(i)+v.getName(),state.get(model.getProcDecls().get(i)+v.getName()));
			}
			for (Var v : model.getProcs().get(i).getVarEnum()){
				n.getStateEnums().put(model.getProcDecls().get(i)+v.getName(),stateEnums.get(model.getProcDecls().get(i)+v.getName()));
			}
		}
		return n;
	}

	public void evalInit(Expression e, int procIndex){
		HashMap<String,Boolean> st = new HashMap<String,Boolean>();
		HashMap<String,String> stEnum = new HashMap<String,String>();
		evalExprInit(e, false, st, stEnum, procIndex);
		stateEnums.putAll(stEnum);
		state.putAll(st);
	}

	private Var instanciateIfParam(Var v, int procIndex){
		Proc proc = model.getProcs().get(procIndex);
		String processName = model.getProcDecls().get(procIndex);
		for (int i=0;i < proc.getParamList().size();i++){
			if (v.getName().equals(proc.getParamList().get(i).getDeclarationName()) && proc.getInvkParametersList(processName).get(i) instanceof Var){
				v = (Var)proc.getInvkParametersList(processName).get(i);
				break;
			}
		}
		return v;
	}

	private boolean checkGlobalVar(Var v){
		for (Var gv : model.getSharedVars()){
			if (v.getName().equals(gv.getName()))
				return true;
		}
		return false;
	}

	private void evalExprInit(Expression e, boolean neg, HashMap<String,Boolean> st, HashMap<String,String> stEnum, int procIndex){
		if (e instanceof Var){
			Var e_ = (Var)e;
			e_ = instanciateIfParam(e_,procIndex);
			if (checkGlobalVar(e_)){ //global
				if (neg)
					st.put(e_.getName(),false);
				else
					st.put(e_.getName(),true);
			}
			else{ //local
				if (neg)
					st.put(model.getProcDecls().get(procIndex)+e_.getName(),false);
				else
					st.put(model.getProcDecls().get(procIndex)+e_.getName(),true);
			}
		}
		if (e instanceof NegBoolExp){
			NegBoolExp e_ = (NegBoolExp)e;
			evalExprInit(e_.getExp(),!neg,st,stEnum,procIndex);
		}
		if (e instanceof AndBoolExp){
			AndBoolExp e_ = (AndBoolExp)e;
			evalExprInit(e_.getExp1(),neg,st,stEnum,procIndex);
			evalExprInit(e_.getExp2(),neg,st,stEnum,procIndex);
		}
		if (e instanceof EqBoolExp){
			EqBoolExp e_ = (EqBoolExp)e;
			String varName = model.getProcDecls().get(procIndex)+((Var)e_.getInt1()).getName();
			if (e_.getInt2() instanceof Var) //then its an enum
				stEnum.put(varName, ((Var)e_.getInt2()).getName());
			else //its a bool*/
				st.put(varName, ((ConsBoolExp)e_.getInt2()).getValue());
		}
	}

	private boolean evalBoolExpr(Expression e, int procIndex){
		if (e instanceof ConsBoolExp){
			ConsBoolExp e_ = (ConsBoolExp)e;
			return e_.getValue();
		}
		if (e instanceof Var){
			Var e_ = (Var)e;
			e_ = instanciateIfParam(e_,procIndex);
			if (checkGlobalVar(e_)){
				return state.get(e_.getName());
			}
			else
				return state.get(model.getProcDecls().get(procIndex)+(e_.getName()));
		}
		if (e instanceof NegBoolExp){
			NegBoolExp e_ = (NegBoolExp)e;
			return !evalBoolExpr(e_.getExp(),procIndex);
		}
		if (e instanceof AndBoolExp){
			AndBoolExp e_ = (AndBoolExp)e;
			return evalBoolExpr(e_.getExp1(),procIndex) && evalBoolExpr(e_.getExp2(),procIndex);
		}
		if (e instanceof OrBoolExp){
			OrBoolExp e_ = (OrBoolExp)e;
			return evalBoolExpr(e_.getExp1(),procIndex) || evalBoolExpr(e_.getExp2(),procIndex);
		}
		if (e instanceof EqBoolExp){
			EqBoolExp e_ = (EqBoolExp)e;
			if (e_.getInt2() instanceof Var){ //check if its an Enum Eq
				Var value = (Var)e_.getInt2();
				if (value.isEnumValue() && e_.getInt1() instanceof Var){
					Var var = instanciateIfParam((Var)e_.getInt1(),procIndex);
					if (checkGlobalVar(var)){
						return stateEnums.get(var.getName()).equals(value.getName());
					}
					else{
						return stateEnums.get(model.getProcDecls().get(procIndex)+var.getName()).equals(value.getName());
					}
				}
			}
			return evalBoolExpr(e_.getInt1(),procIndex) == evalBoolExpr(e_.getInt2(),procIndex);
		}
		return false;
	}

	public CompositeNode createSuccessor(LinkedList<Code> assigns, int procIndex){
		CompositeNode succ = clone();
		for (Code c : assigns){
			succ.updateSingleVar(c,procIndex);
		}
		return succ;
	}

	public void update(LinkedList<Code> assigns, int procIndex){
		for (Code c : assigns){
			this.updateSingleVar(c,procIndex);
		}
	}

	private void updateSingleVar(Code c, int procIndex){
		boolean isEnumAssign;
		if (c instanceof VarAssign){
			VarAssign assign = (VarAssign)c;
			Var var = instanciateIfParam(assign.getVar(),procIndex);
			String name = var.getName();
			isEnumAssign = false;
			//enum assign treatment
			if (assign.getExp() instanceof Var){
				Var exp = (Var) assign.getExp();
				if (exp.hasEnumType()){
					isEnumAssign = true;
					String value = exp.getName();
					if (checkGlobalVar(var))
						this.getStateEnums().put(name,value);
					else
						this.getStateEnums().put(model.getProcDecls().get(procIndex)+name,value);
				}
			}
			//boolean assign treatment
			if (!isEnumAssign){
				Boolean value = evalBoolExpr(assign.getExp(),procIndex);
				if (checkGlobalVar(var))
					this.getState().put(name,value);
				else
					this.getState().put(model.getProcDecls().get(procIndex)+name,value);
			}
		}
	}

}