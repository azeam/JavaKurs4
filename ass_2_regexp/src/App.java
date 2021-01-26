import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) throws Exception {   
        String[] patterns = new String[10];     
        patterns[0] = "(?i)abcdefghijlklmnopqrstuvwxyz";
        // abcdefghijlklmnopqstuvxyz exists, proper English alphabet does not
        patterns[1] = "(?i)Ola";
        patterns[2] = "[a]{3,5}";
        patterns[3] = "[^\\w]{2,}+";
        patterns[4] = "([a-zA-Z0-9+._%\\-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z]{2,3}+)";
        patterns[5] = "[0-9]{8}|[0-9 /-]{4}+[0-9]{6}";
        patterns[6] = "([a-zA-Z])\\1\\1\\1(?!1)+";
        patterns[7] = "[a-z]{3}+[_/]+[0-9]{3}";
        patterns[8] = "[(]+[a-zA-Z]+[)]|[(]+[0-9]+[)]";
        patterns[9] = "if\\(|for\\(";

        File text = new File("text.txt");
         
        int lineNumber;
        for (int i = 0; i <patterns.length; i++) {
            Scanner scanner = new Scanner(text);
            lineNumber = 1;
            if (i != 3) {
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    finder(line, patterns[i], lineNumber);
                    lineNumber++;
                }      
                scanner.close();    
            }
            else {
                int count = 0;
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    count = count + counter(line, patterns[i]);
                }      
                System.out.println("Number of non-alphanumeric strings: " + count);
                scanner.close();
            }
        }
    }

    private static int counter(String inputString, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(inputString);
        int i = 0;
        while(matcher.find()){ //searches for string
            if (matcher.group().length() != 0) {
                i++;
            }
        }
        return i;
    }

    public static void finder(String inputString, String pattern, int lineNumber) {
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(inputString);
        while(matcher.find()){ //searches for string
            if(matcher.group().length() != 0 ) { //print unless length is empty.
                System.out.println("Match found at line " + lineNumber + ": " + matcher.group());
            }
        }
    }
}
