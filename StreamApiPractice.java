import java.util.*;
import java.util.stream.Collectors;

public class StreamApiPractice {
    public static void main(String[] args) {
        String s = "rdgaeteradsgaer";
//        countFreqOfStringChars(s);
//        firstNonRepeatedChar(s);
//        identifyDuplicateInList();

        //Group a list of strings based on their length
//        groupListOfStrings();
        //Concatenate all strings in a list into a single string.
//        concatenateStringsIntoSingle();

        //Find the 3rd largest element in a list.
//        findThirdLargestElement();

        //Find all strings that are palindromes in a list.
//        findPalindromesStrings();

        //Partition strings into palindromes and non-palindromes.
//        partitionPalindromeNonPalindromeStrings();

        //Find the longest word in a sentence using Streams.
//        findLongestWordInSentence();
        //Find the median of a list of integers.
//        findListMedian();
        //From a list of employees, find the highest-paid employee in each department.
//        findThirdHighestSalaryEmployee();

        //Group words by their length and sort each group alphabetically.
//        groupWordsByLengthAndSort();

        //Detect all anagrams in a list of strings.
//        detectAllAnagrams();
        //Find Non-Repeating Characters in a String
//        findNonRepeatingCharacter();
        //Convert a map to a list of “key=value” strings.
//        convertMapIntoList();
        //Calculate Average of Numbers
        calculateAvgOfNumbers();
        //Find the Most Frequent Character in a String
//        findMostFrequentCharacter(s);
    }

    private static void findMostFrequentCharacter(String s) {
        Character result= Optional.ofNullable(s).map(String::trim).filter(s1 -> !s.isBlank()).map(
                s1 -> s1.chars().mapToObj(c->(char)c).collect(Collectors.groupingBy(
                        c->c, Collectors.counting()
                )).entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).map(Map.Entry::getKey).orElseThrow()
        ).orElseThrow(()-> new RuntimeException("String can not be null or empty"));
        System.out.println(result);
    }

    private static void calculateAvgOfNumbers() {
        List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50);
        Double average = Optional.ofNullable(numbers).filter(list-> !list.isEmpty()).orElseThrow(()-> new RuntimeException("List can not be null or empty"))
        .stream().mapToInt(Integer::valueOf).average().orElse(0.0);
        System.out.println(average);
    }

    private static void convertMapIntoList() {
        Map<String, Integer> map = Map.of("A", 1, "B", 2, "C", 3);
        List<String> result = Optional.of(map).filter(input-> !input.isEmpty()).orElseThrow(()-> new RuntimeException("Map can not be null or empty")).entrySet().stream().collect(Collectors.collectingAndThen(
                Collectors.toList(),
                entries -> entries.stream().map(entry-> entry.getKey() + "=" + entry.getValue()).toList()
        ));
        System.out.println(result);
    }

    private static void findNonRepeatingCharacter() {
        String input = "abcadsb";
        input.chars().filter(c-> input.indexOf(c)==input.lastIndexOf(c)).mapToObj(
                c->(char)c
        ).forEach(System.out::println);
    }

    private static void detectAllAnagrams() {
        List<String> words = Arrays.asList("listen", "silent", "enlist", "google", "elbow", "below");
        Map<String, List<String>> result = words.stream().collect(Collectors.groupingBy(
                str-> str.chars().sorted().mapToObj(c-> String.valueOf((char) c)).collect(
                        Collectors.joining()
                )
        ));
        System.out.println(result);
    }

    private static void groupWordsByLengthAndSort() {
        List<String> words = Arrays.asList("java", "stream", "api", "example", "code", "test");
        Map<Integer, List<String>> result1 = words.stream().sorted().collect(Collectors.groupingBy(
                String::length
        ));

        //OR

        Map<Integer, List<String>> result2 = words.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> list.stream().sorted().collect(Collectors.toList())
                )));
        System.out.println(result1);
        System.out.println(result2);
    }

    private static void findThirdHighestSalaryEmployee() {
        Employee e1 = new Employee("A", 100.0, "test1");
        Employee e2 = new Employee("B", 200.32, "test1");
        Employee e3 = new Employee("C", 500.3243, "test1");

        List<Employee> list = List.of(e1, e2, e3);
        Map<String, String> result = list.stream().collect(
                Collectors.groupingBy(Employee::getDepartment, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Employee::getSalary)), opt->opt.get().getName()))
        );
        System.out.println(result);

    }

    private static void findListMedian() {
        List<Integer> list = List.of(4, 5, 8, 9, 10, 17);
        List<Integer> sortedList = list.stream().sorted().toList();
        //find Median from sorted List
    }

    private static void findLongestWordInSentence() {
        String s = "This is java practice";

        String result = Arrays.stream(s.split(" ")).reduce((word1, word2) -> word1.length()> word2.length() ? word1 : word2).orElse(null);
        System.out.println(result);
    }


    private static void partitionPalindromeNonPalindromeStrings() {
        List<String> lists = List.of("abc", "hello", "aba", "fsdf", "abcba");
        Map<Boolean, List<String>> partitionedList = lists.stream().collect(Collectors.partitioningBy(s-> s.contentEquals(new StringBuilder(s).reverse())));
        System.out.println(partitionedList);
    }

    private static void findPalindromesStrings() {
        List<String> lists = List.of("abc", "hello", "aba", "fsdf", "abcba");

        List<String> palindromes = lists.stream().filter(s -> s.equals(new StringBuilder(s).reverse().toString())).toList();
        System.out.println(palindromes);
    }

    private static void findThirdLargestElement() {
        List<Integer> list = List.of(124, 160, 200, 432, 223, 643);

        Integer result = list.stream().sorted().skip(2).findFirst().orElse(null);
        System.out.println(result);
    }

    private static void concatenateStringsIntoSingle() {
        List<String> list = List.of("A", "AB", "ABC", "B", "CD");
        String result = list.stream().collect(Collectors.joining(""));
        System.out.println(result);
    }

    private static void groupListOfStrings() {
        List<String> list = List.of("A", "AB", "ABC", "B", "CD");
        Map<Integer, List<String>>listGroup = list.parallelStream().collect(Collectors.groupingBy(String::length));
        System.out.println(listGroup);
    }

    private static void identifyDuplicateInList() {
        List<Integer> list = List.of(1, 2, 3, 5, 3, 4, 1, 2, 6);
        Map<Integer, Long> countMap = list.parallelStream().collect(
                Collectors.groupingBy(x->x, Collectors.counting()));
        System.out.println(countMap);
        List<Integer> duplicateList = countMap.entrySet().stream().filter(x->x.getValue()>1).map(Map.Entry::getKey).toList();
        System.out.println(duplicateList);
    }

    private static void firstNonRepeatedChar(String s) {
       Optional<Character> character =  s.chars().filter(c-> s.indexOf(c) == s.lastIndexOf(c)).mapToObj(c->(char)c).findFirst();
       System.out.println(character.orElse(null));
    }

    private static void countFreqOfStringChars(String s) {
       Map<Character, Long> list = s.chars().mapToObj(c-> (char)c).collect((Collectors.groupingBy(c->c, Collectors.counting())));
       System.out.println(list);
    }

}
