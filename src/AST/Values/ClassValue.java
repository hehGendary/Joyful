package AST.Values;

import AST.Library.Funcs.UserDefinedFunction;

import java.util.List;

public class ClassValue implements Value{

    public List<UserDefinedFunction> methods;


    @Override
    public float asNum() {
        return 0;
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public int arrLen() {
        return 0;
    }

    @Override
    public String asStr() {
        return null;
    }
}