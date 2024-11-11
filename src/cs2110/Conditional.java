package cs2110;

import java.util.HashSet;
import java.util.Set;

public class Conditional implements Expression {

    private Expression condition;
    private Expression trueBranch;
    private Expression falseBranch;

    /**
     * Constructs Conditional expression with given condition, true branch, and false branch.
     *
     * @param condition is  condition to evaluate. Can't be null.
     * @param trueBranch is expression to evaluate if the condition is true. Can't null.
     * @param falseBranch is expression to evaluate if the condition is false. Can't be null.
     */
    public Conditional(Expression condition, Expression trueBranch, Expression falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }


    /**
     * Evaluates Conditional expression by first evaluating the condition.
     * If the condition evaluates to 0.0, the false branch is evaluated. Otherwise, the true branch is evaluated.
     *
     * @param vars is the VarTable with defined values.
     * @return result of evaluating a branch based on condition.
     * @throws UnboundVariableException if evaluated expression refers to unbound variable.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        if (condition.eval(vars) == 0.0) { return falseBranch.eval(vars); }
        return trueBranch.eval(vars);
    }



    /**
     * Returns total count of operations for Conditional,
     * including the evaluating the condition and the more expensive branch.
     *
     * @return total count of operations, plus 1 for selecting.
     */
    @Override
    public int opCount() {
        return 1 + condition.opCount() + Math.max(trueBranch.opCount(), falseBranch.opCount());
    }

    /**
     * Returns infix string representation of Conditional,
     * with proper parentheses and spaces.
     *
     * @return infix representation of Conditional.
     */
    @Override
    public String infixString() {
        return "(" + condition.infixString() + " ? " + trueBranch.infixString() + " : " + falseBranch.infixString() + ")";
    }

    /**
     * Returns postfix string representation of Conditional,
     * with proper spaces.
     *
     * @return postfix representation of Conditional.
     */
    @Override
    public String postfixString() {
        return condition.postfixString() + " " + trueBranch.postfixString() + " " + falseBranch.postfixString() + " ?:";
    }

    /**
     * Checks if Conditional is equal to object.
     *
     * @param object is the object we compare Conditional with.
     * @return true if object a Conditional with the same condition and branches. Otherwise, false.
     */
    public boolean equals(Object object) {
        if (object instanceof Conditional) {
            Conditional cond = (Conditional) object;
            return condition.equals(cond.condition) && trueBranch.equals(cond.trueBranch) && falseBranch.equals(cond.falseBranch);
        }
        return false;
    }

    /**
     * Optimizes Conditional by optimizing its condition and branches,
     * replacing it with a Constant if condition can be evaluated.
     *
     * @param vars is VarTable for optimization.
     * @return a Constant if the result can be completely evaluated. Otherwise, optimized Conditional is returned.
     */
    @Override
    public Expression optimize(VarTable vars) {
        Expression cond = condition.optimize(vars);
        Expression trueB = trueBranch.optimize(vars);
        Expression falseB = trueBranch.optimize(vars);

        Expression newCondition = new Conditional(cond, trueB, falseB);
        try {
            return new Constant(newCondition.eval(vars));
        }
        catch (Exception e) {
            return newCondition;
        }
    }

    /**
     * Returns Set containing names variables in Conditional's expressions.
     *
     * @return a Set of variable names that Conditional relies on.
     */
    @Override
    public Set<String> dependencies() {
        Set<String> varNames = new HashSet<>();

        varNames.addAll(condition.dependencies());
        varNames.addAll(trueBranch.dependencies());
        varNames.addAll(falseBranch.dependencies());

        return varNames;
    }
}
