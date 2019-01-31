import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagParser {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader fileReader = new BufferedReader(new FileReader(reader.readLine()));
        reader.close();
        StringBuilder sb = new StringBuilder();

        while (fileReader.ready()) {
            sb.append(fileReader.readLine());
        }

        fileReader.close();
        String tag = args[0];
        Map<Integer, Integer> openers = new TreeMap<>();
        Matcher matcher = Pattern.compile("(<\\s*/?\\s*" + tag + ".*?>)").matcher(sb);

        while (matcher.find()) {
            if (matcher.group().matches("<\\s*" + tag + ".*?>")) {
                openers.put(matcher.start(), null);
            } else {
                openers.put(lastNull(openers), matcher.end());
            }
        }

        openers.forEach((key, value) -> {
            System.out.println(sb.substring(key, value));
        });
    }

    static int lastNull(Map<Integer, Integer> map) {
        int rez = -1;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            if (value == null) {
                rez = key;
            }
        }

        return rez;
    }
}