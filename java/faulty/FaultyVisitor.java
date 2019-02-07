package faulty;



public interface FaultyVisitor {

	public void visit(Program a);
    public void visit(EnumType a);
	public void visit(GlobalVarCollection a);
	public void visit(ProcessCollection a);
	public void visit(ChannelCollection a);
	public void visit(Proc a);
	public void visit(Param a);
    public void visit(Channel a);
	public void visit(Branch a);
	public void visit(ChanAssign a);
	public void visit(ChanAccess a);
	public void visit(VarAssign a);
	public void visit(Var a);
	public void visit(AndBoolExp a);
	public void visit(BiimpBoolExp a);
	public void visit(OrBoolExp a);
	public void visit(NegBoolExp a);
	public void visit(GreaterBoolExp a);
	public void visit(LessBoolExp a);
	public void visit(EqBoolExp a);
	public void visit(ConsBoolExp a);
	public void visit(NegIntExp a);
	public void visit(SumIntExp a);
	public void visit(DivIntExp a);
	public void visit(MultIntExp a);
	public void visit(ConsIntExp a);
	public void visit(Main a);
	public void visit(ProcessDecl a);
    public void visit(InvkProcess a);
	
	
	
}
