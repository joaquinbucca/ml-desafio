import java.util.*;

/**
 * Created by joaquin on 30/10/14.
 */
public class Process {

    private String actualMinute;
    private Map<String, Map<String, Statistics>> map= new HashMap<String, Map<String, Statistics>>();

    public void readFile(){

    }

    public Entry understandLine(String line){
        line= line.replaceAll(", ", ",");
        List<String> elems= new ArrayList<String>((line.split("\\s+")).length);
        Collections.addAll(elems, line.split("\\s+"));
        for (String elem: elems){
            if(elem.equals("-")) elem = null;
        }
        return new Entry(elems);
    }


    public void saveInMap(Entry entry) {
        Map<String, Statistics> statisticsMap = map.get(entry.getIpFrom());
        if (statisticsMap == null) {
            statisticsMap= new HashMap<String, Statistics>();
            Statistics statistics= new Statistics(entry);
            statistics.update(entry);
            statisticsMap.put(entry.getIpTo(), statistics);
            map.put(entry.getIpFrom(), statisticsMap);
        } else {
            Statistics statistics = statisticsMap.get(entry.getIpTo());
            if(statistics == null){
                statistics= new Statistics(entry);
            }
            statistics.update(entry);
            statisticsMap.put(entry.getIpTo(), statistics);
            map.put(entry.getIpFrom(), statisticsMap);
        }
    }

    public void processLine(String line) {
        String minute= line.split("\\s+")[0];
        if(actualMinute == null){
            actualMinute= minute;
        }
        if(!actualMinute.equals(minute)) {
            actualMinute= minute;
            saveInDBAndWriteInFile();
            map= new HashMap<String, Map<String, Statistics>>();
        }
        saveInMap(understandLine(line));

    }

    private void saveInDBAndWriteInFile() {
        // write each line of map in text
        for (Map<String, Statistics> statisticsMap: map.values()){
            for (Statistics statistics: statisticsMap.values()){
//                String lineToWrite= statistics.getLine();
//                System.out.println("lineToWrite = " + lineToWrite);
            }
        }
    }
}
