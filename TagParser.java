import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

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
        String text = sb.toString();
        Map<Integer, Integer> openers = new TreeMap<>();
        int index = 0;

        while ((index = text.indexOf(tag, index + 1)) > -1) {
            char openOrClose = text.charAt(index - 1);
            if (openOrClose == '/') {
                int opener = lastNull(openers);
                openers.put(opener, index + tag.length() + 1);
            } else {
                openers.put(index - 1, null);
            }
        }

        openers.forEach((key, value) -> {
            System.out.println(text.substring(key, value));
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