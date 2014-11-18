import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Map<String, Map<String, Statistics>> map= new HashMap<String, Map<String, Statistics>>();


    public static void main(String[] args) {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Structure.getInstance().initDB();
        routine(input);
    }

    public static void  routine(BufferedReader input){
        System.out.println("start:    "+System.currentTimeMillis());
        Buffer buffer= new Buffer(1000000);
        Reader reader= new Reader(buffer, input);
        reader.start();
        for (int i = 0; i < 19; i++) {
            LineProcessor lineProcessor = new LineProcessor(buffer);
            lineProcessor.start();
        }

    }
}



