import MainFiles.AST.Statements.Statement;
import MainFiles.Lexer;
import MainFiles.Parser;
import MainFiles.Token;
import MainFiles.Visitors.AssignValidator;
import MainFiles.Visitors.FunctionAdder;
import MainFiles.Visitors.Optimize.Optimizer;

import java.util.List;

public class Run {
    String code;

    public Run(String code) {
        this.code = code;
    }

    public void run(boolean debug) {
        List<Token> tokens = new Lexer(code).tokenize();

            Statement program = new Parser(tokens, debug).parse();

            program = Optimizer.optimize(program, 20, false);

            program.accept(new FunctionAdder());
            program.accept(new AssignValidator());

            program.execute();
    }
}