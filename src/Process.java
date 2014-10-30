import java.util.*;

/**
 * Created by joaquin on 30/10/14.
 */
public class Process {

    private Map<String, Map<String, Statistics>> map= new HashMap<String, Map<String, Statistics>>();

    public void readFile(){

    }

    public Entry understandLine(String line){
        List<String> elems= new ArrayList<String>((line.split(" ")).length);
        Collections.addAll(elems, line.split(" "));
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

}
