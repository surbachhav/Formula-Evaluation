package cs2110;

import java.util.HashSet;
import java.util.Set;

public class Variable implements Expression {

    /**
     * Name of variable
     */
    private String name;

    /**
     * Constructs a Variable with the specified name.
     *
     * @param name the name of the variable. Must not be empty.
     */
    public Variable (String name) {
        this.name = name;
    }

    /**
     * Evaluates Variable by finding its value in VarTable.
     *
     * @param vars is the VarTable containing defined variable values.
     * @return the value of the variable.
     * @throws UnboundVariableException if the variable is not found in the VarTable.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        return vars.get(name);
    }


    /**
     * Returns the count of operations for Variable, which is always 0 because Variable
     * nodes are leaf nodes.
     *
     * @return 0.
     */
    @Override
    public int opCount() {
        return 0;
    }

    /**
     * Returns the infix string representation of Variable, otherwise known as its  name.
     *
     * @return name.
     */
    @Override
    public String infixString() {
        return name;
    }

    /**
     * Returns the postfix string representation of Variable, otherwise known as its name.
     *
     * @return name.
     */
    @Override
    public String postfixString() {
        return name;
    }


    /**
     * Checks if Variable is equal to a provided object.
     *
     * @param object is the object to compare this Variable with.
     * @return true if the object is a Variable with the same name; false otherwise.
     */
    public boolean equals(Object object) {
        if (object instanceof Variable) {
            Variable var = (Variable) object;
            return name.equals(var.name);
        }
        return false;
    }

    /**
     * If the variable has a value in VarTable,
     * it returns a new Constant with that value. Otherwise, it returns itself.
     *
     * @param vars is the VarTable that we check against for existing values
     * @return a Constant if the variable has a value. Otherwise, just return this Variable.
     */
    @Override
    public Expression optimize(VarTable vars) {
        try {
            return new Constant(vars.get(name));
        }
        catch (Exception e) {
            //System.err.println("Variable " + name + " is not in VarTable.");
            return this;
        }
    }

    /**
     * Returns a Set containing the variable's name.
     *
     * @return a Set containing variable's name.
     */
    @Override
    public Set<String> dependencies() {
        Set<String> varNames = new HashSet<>();
        varNames.add(name);

        return varNames;
    }
}
