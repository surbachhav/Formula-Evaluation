package cs2110;

import java.util.HashSet;
import java.util.Set;

public class Operation implements Expression {

    /**
     * Defines what operation to perform on leftOperand and rightOperand.
     */
    private Operator op;
    /**
     * Left side of equation before operator.
     */
    private Expression leftOperand;
    /**
     * Right side of equation after operator.
     */
    private Expression rightOperand;


    /**
     * Constructs Operation with given operator and operands.
     *
     * @param op the operator to be applied. Can't be null.
     * @param leftOperand is the left operand of the operation. Can't be null.
     * @param rightOperand is the right operand of the operation. Can't be null.
     */
    public Operation (Operator op, Expression leftOperand, Expression rightOperand) {
        this.op = op;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    /**
     * Evaluates operation by evaluating the left and right operands and applying the operator.
     *
     * @param vars is the VarTable with variable values.
     * @return the result of applying operator to the evaluated operands.
     * @throws UnboundVariableException if either operand refers to an unbound variable.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        return op.operate(leftOperand.eval(vars), rightOperand.eval(vars));
    }


    /**
     * Returns total count of operations for Operation, including
     * the operation itself plus operation count in left and right operands.
     *
     * @return the total count of operations, including 1 for this operation.
     */
    @Override
    public int opCount() {
        return 1 + leftOperand.opCount() + rightOperand.opCount();
    }

    /**
     * Returns infix string version of the operation,
     * with proper parentheses and spaces.
     *
     * @return the infix form of the operation.
     */
    @Override
    public String infixString() {
        return "(" + leftOperand.infixString() + " " + op.symbol() + " " + rightOperand.infixString() + ")";
    }

    /**
     * Returns postfix string version of the operation,
     * with proper spaces.
     *
     * @return the postfix form of the operation.
     */
    @Override
    public String postfixString() {
        return leftOperand.postfixString() + " " + rightOperand.postfixString() + " " + op.symbol();
    }

    /**
     * Checks if this Operation is equal to object.
     *
     * @param object is object we compare with Operation.
     * @return true if the object is an Operation with the same operator and operands. Otherwise, false.
     */
    public boolean equals(Object object) {
        if (object instanceof Operation) {
            Operation oper = (Operation) object;
            return op.equals(oper.op) && leftOperand.equals(oper.leftOperand) && rightOperand.equals(oper.rightOperand);
        }
        return false;
    }

    /**
     * Optimizes operation by optimizing its operands and checking if the
     * result can be computed to a constant.
     *
     * @param vars is the VarTable.
     * @return a Constant if the operation can be completely evaluated. Otherwise, return the optimized operation.
     */
    @Override
    public Expression optimize(VarTable vars) {
        Expression left = leftOperand.optimize(vars);
        Expression right = rightOperand.optimize(vars);

        Expression operation = new Operation(op, left, right);
        try {
            return new Constant(operation.eval(vars));
        }
        catch (Exception e) {
            return operation;
        }
    }

    /**
     * Returns a Set containing the names of all the variables used in operation's operands.
     *
     * @return a Set of variable names operation relies on.
     */
    @Override
    public Set<String> dependencies() {
        Set<String> varNames = new HashSet<>();

        varNames.addAll(leftOperand.dependencies());
        varNames.addAll(rightOperand.dependencies());

        return varNames;
    }
}
