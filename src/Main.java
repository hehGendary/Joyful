
import AST.Statements.Statement;
import Visitors.AssignValidator;
import Visitors.FunctionAdder;
import Visitors.Optimize.Optimizer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static String open(String filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        String for_ret = sb.toString();
        br.close();
        return for_ret;
    }
    public static void main(String[] args) throws IOException {
        boolean run = true;
        while (run) {
            List<String> code = new ArrayList<>();
            System.out.println("\nCode: (send name of file after send END for end");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("END".equals(line)) {
                    break;
                } else if ("STOP".equals(line)) {
                    run = false;
                    break;
                }
                code.add(line);
            }
            String filename = scanner.nextLine();

            filename = String.valueOf(Paths.get(filename));
            File file = new File(String.valueOf(Paths.get(filename)));

            String input = String.join("\n", code);

            if (!file.createNewFile() && input.isEmpty()){
                input = open(filename);
            }
            else{
                Files.write(Paths.get(file.toURI()), input.getBytes(), StandardOpenOption.APPEND);
            }

            List<Token> tokens = new Lexer(input).tokenize();
            Statement program = new Parser(tokens, true).parse();

            program = Optimizer.optimize(program, 20, false);

            program.accept(new FunctionAdder());
            program.accept(new AssignValidator());

            program.execute();
        }
    }
}
