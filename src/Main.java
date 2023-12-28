
import MainFiles.AST.Statements.Statement;
import MainFiles.Lexer;
import MainFiles.Parser;
import MainFiles.Token;
import MainFiles.Visitors.AssignValidator;
import MainFiles.Visitors.FunctionAdder;
import MainFiles.Visitors.Optimize.Optimizer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int MAJOR = 1;
    static int Minor = 0;
    static int micro = 0;
    static int nano = 0;


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
        System.out.println("Joyful programming language");
        System.out.println("version: " + MAJOR + "." + Minor + "." + micro + "." + nano);
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

            Scanner sc = new Scanner(System.in);
            System.out.print("debug? y fo yes: ");
            String question = sc.nextLine();
            System.out.println("");

            Run r = new Run(input);
            r.run((question == "y"));
        }
    }

}
