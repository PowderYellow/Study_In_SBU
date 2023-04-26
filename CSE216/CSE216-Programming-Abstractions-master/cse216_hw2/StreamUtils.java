import java.util.*;
import java.util.stream.Collectors;

public class StreamUtils {



    public static Collection<String> capitalize(Collection<String> strings){
        return strings
                .stream()
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.toCollection(ArrayList<String>::new));
    }

    public static String longest(Collection<String> strings, boolean from_start){
        return from_start?

                strings.stream()
                        .reduce((string1,string2)-> string1.length() >= string2.length() ? string1:string2).orElse(null) : strings.stream()
                        .reduce((string1,string2)-> string2.length() >= string1.length() ? string2:string1).orElse(null);
    }


    public static <T extends Comparable<T>> T least(Collection<T> items, boolean from_start){
        return from_start?
                items.stream()
                        .reduce((item1,item2)-> item1.compareTo(item2) < 0 ? item1:item2).orElse(null) : items.stream()
                        .reduce((item1,item2)-> item2.compareTo(item1) < 0 ? item2:item1).orElse(null);
    }


    public static <K, V> List<String> flatten(Map<K, V> aMap){
        return aMap.entrySet().stream()
                .map(k -> k.getKey()+" -> "+k.getValue())
                .collect(Collectors.toList());
    }

}
