package faulty;




/**
 * Class implementing the comparation int1 > int2
 */

public class GreaterBoolExp extends BoolExp{
	Expression int1; // left int
	Expression int2; // right int

    /**
     * Constructor, it takes the two integer in the comparison as parameters
     * @param	int1	left integer
     * @param	int2	right integer
     */
    public GreaterBoolExp(Expression int1, Expression int2){
    	super();
        this.int1 = int1;
        this.int2 = int2;
    }

    @Override
	public void accept(FaultyVisitor v){
	     v.visit(this);			
	}
    
    
}