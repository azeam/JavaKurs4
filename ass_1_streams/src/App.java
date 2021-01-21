import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws Exception {
        printAustinData();
        printDoubleData();
        MyObject[] fiftyRandom = make50Objects();
        MyObject[] arrayUnder20 = makeArrayUnder20(fiftyRandom); // objective is create array?
        calcAverage(fiftyRandom);
        changeNames(fiftyRandom);
    }

    private static void changeNames(MyObject[] fiftyRandom) {
        List<MyObject> listObjs = Arrays.asList(fiftyRandom);
        listObjs = listObjs
            .stream()
            .map(x -> {
                if (x.getBool()) {
                    x.setName("this is true");
                }
                return x;
            })
            .collect(Collectors.toList());
        
        listObjs.forEach(obj -> {
            System.out.println(obj.toString());
        });
    }

    private static void calcAverage(MyObject[] fiftyRandom) {
        List<MyObject> listObjs = Arrays.asList(fiftyRandom);
        double average = listObjs
            .stream()
            .mapToDouble(x -> x.getValue())
            .average()
            .getAsDouble();
        System.out.println("Average of values is: " + average);
    }

    private static MyObject[] makeArrayUnder20(MyObject[] fiftyRandom) {
        List<MyObject> listObjs = Arrays.asList(fiftyRandom);
        List<MyObject> under20Objs = listObjs
            .stream()
            .filter(x -> x.getValue() < 20)
            .collect(Collectors.toList());

        under20Objs.forEach(obj -> {
            System.out.println(obj.toString());
        });

        MyObject[] array = new MyObject[under20Objs.size()];
        under20Objs.toArray(array);
        return array;
    }

    private static MyObject[] make50Objects() {
        MyObject[] objs = new MyObject[50];
        int randomNum;
        for (int i = 0; i < 50; i++) {
            randomNum = ThreadLocalRandom.current().nextInt(0, 50 + 1);
            objs[i] = new MyObject((Math.random() < 0.5), randomNum, "name" + i);
        }
        return objs;
    }

    private static void printDoubleData() {
        Doubles doubles = new Doubles();
        doubles.listDoubles();

        System.out.println("Number of doubles above 1000: " + doubles.nAboveK());
        System.out.println("Doubles below 1000 whose pre-decimal value is divisible by 3: " + Arrays.toString(doubles.belowKDivisableBy3()));
        System.out.println("Sum of all numbers below 500 is: " + doubles.sumAbove500());
        System.out.println("Average of the numbers between 2000 and 3000 is: " + doubles.avrg2K3K());
        System.out.println("Smallest number is: " + doubles.smallestAndLargest()[0] + " and the largest is: " + doubles.smallestAndLargest()[1]);
    }

    private static void printAustinData() {
        Austin austin = new Austin();
        austin.listAustin();
        
        System.out.println("Number of words containing more than 8 characters: " + austin.moreThan8Chars());
        System.out.println("Number of unique words: " + austin.uniqueWordsCount());
        System.out.println("Number of words containing less than 4 characters: " + austin.lessThan4chars());
        System.out.println("Number of unique words containing more than 8 characters: " + austin.uniqueAbove8Chars());
        System.out.println("Average word length: " + austin.avrgWordLength() + " characters");
        System.out.println("Total character count: " + austin.totalWordLength() + " characters");
        System.out.println("All words in text are " + (austin.allShorterThan12() ? "not " : "") + "shorter than 12 characters");
        System.out.println("All words in text are " + (austin.allLongerThan2() ? "not " : "") + "longer than 2 characters");
    }

    

}
