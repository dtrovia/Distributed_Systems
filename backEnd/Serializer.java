
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Serializer {
    public static String serializeList(List<String> list) {
        // Convert the list into a string representation
        // Here, we'll simply concatenate the elements with a delimiter (comma) between them
        String serialized = list.stream().collect(Collectors.joining(","));
        return serialized;
    }

    public static List<String> deserializeList(String serialized) {
        // Parse the string representation back into a list
        // Here, we'll split the string using the delimiter (comma) and convert it back into a list
        List<String> deserialized = Arrays.asList(serialized.split(","));
        return deserialized;
    }
}

