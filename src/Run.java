import MainFiles.AST.Statements.Statement;
import MainFiles.Lexer;
import MainFiles.Parser;
import MainFiles.Token;
import MainFiles.Visitors.AssignValidator;
import MainFiles.Visitors.FunctionAdder;
import MainFiles.Visitors.Optimize.Optimizer;
import MainFiles.Visitors.UseExecute;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Run {
    static String code;

    public Run(String code) {
        this.code = code;
    }

    public static void run(boolean debug) throws IOException {
        List<Token> tokens = new Lexer(code).tokenize();

            Statement program = new Parser(tokens, debug).parse();

            program = Optimizer.optimize(program, 20, false);

            program.accept(new UseExecute());
            program.accept(new FunctionAdder());
            program.accept(new AssignValidator());

            program.execute();
    }

}