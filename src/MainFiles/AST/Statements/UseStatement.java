package MainFiles.AST.Statements;

import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;
import MainFiles.AST.Library.Funcs.Modules.*;

public class UseStatement implements Statement {
    public String string;

    public UseStatement(String expr) {
        this.string = expr;
    }

    public void execute() {
        switch (string) {
            case "canvas": canvas.init();
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return visitor.visit(this, input);
    }
}