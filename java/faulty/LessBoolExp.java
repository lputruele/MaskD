package faulty;



/**
 * This class implements the comparation int1 < int2
 */
public class LessBoolExp extends Expression{
	Expression int1; // the left integer
	Expression int2; // the right integer

    /**
     * Basic constructor of the class
     * @param int1	the left integer
     * @param int2	the right integer
     */
    public LessBoolExp(Expression int1, Expression int2){
    	super();
        this.int1 = int1;
        this.int2 = int2;
    }

   
    @Override
	public void accept(FaultyVisitor v){
	     v.visit(this);			
	}
}