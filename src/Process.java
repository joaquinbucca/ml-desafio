import com.mongodb.BasicDBObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by joaquin on 30/10/14.
 */
public class Process extends Thread {

    private Structure structure;
    private Buffer buffer;

    public Process(Buffer buffer) {
        structure = Structure.getInstance();
        this.buffer = buffer;
    }

    @Override
    public void run() {
//        while (true) {
//            try {
            String line = null;
        while ((line = buffer.processLineFromBuffer()) != null)
                processLine(line);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        System.out.println("System.currentTimeMillis() = " + System.currentTimeMillis());
    }

    public EntryData understandLine(String line) {
        line = line.replaceAll(", ", ",");
//        System.out.println("line = " + line);
        String[] list= line.split("\\s+");
        List<String> elems = new ArrayList<String>(list.length);
        Collections.addAll(elems, list);
        for (String elem : elems) {
            if (elem.equals("-")) elem = null;
        }
        if (elems.size() == 1) {
            System.out.println(System.currentTimeMillis());
            System.out.println(structure.getCounter());
            System.out.println("line = " + line);
        }
        return new EntryData(elems);
    }


    public void saveInConcurrentMap(EntryData entryData) {
        structure.increment();
        ConcurrentMap<Integer, ConcurrentMap<Integer, Statistics>> map = structure.getMap();
        ConcurrentMap<Integer, Statistics> statisticsConcurrentMap = map.get(entryData.getIpFromHashed());
        Statistics statistics;
        if (statisticsConcurrentMap == null) { // si para la ip de donde viene tdvia no hay nada
            statisticsConcurrentMap = new ConcurrentHashMap<Integer, Statistics>();
            statistics = new Statistics(entryData);
            statistics.update(entryData);
            statisticsConcurrentMap.put(entryData.getIpToHashed(), statistics);
            map.put(entryData.getIpFromHashed(), statisticsConcurrentMap);
            structure.setMap(map);
        } else { // si ya habia para la ip de donde viene
            statistics = statisticsConcurrentMap.get(entryData.getIpToHashed());
            if (statistics == null) { // si para la combinacion de ips no habia nada
                statistics = new Statistics(entryData);
            }
            statistics.update(entryData);
            statisticsConcurrentMap.put(entryData.getIpToHashed(), statistics);
            map.put(entryData.getIpFromHashed(), statisticsConcurrentMap);
            structure.setMap(map);
        }

//        try {
//            Mongo mongo= new Mongo("localhost", 27017);
//            DB db = mongo.getDB("testDB");
//            DBCollection collection= db.getCollection("logs");
//            BasicDBObject query= getSearchDBObject(statistics);
//            BasicDBObject newObj= getDBObjectFromStatistics(statistics);
//            if(structure.getCollection().find(query).hasNext()){
//                System.out.println("asdas");
//                structure.getCollection().update(query, newObj);
//            } else {
//                System.out.println("asdas1");
//                structure.getCollection().insert(newObj);
//            }
//        } catch (UnknownHostException e) {
//            System.out.println("e = " + e);
//            e.printStackTrace();
//        }
    }

    private BasicDBObject getSearchDBObject(Statistics statistics){
        BasicDBObject object= new BasicDBObject();
        object.append("minute", statistics.getMinute());
        object.append("ipFrom", statistics.getIpFrom());
        object.append("ipTo", statistics.getIpTo());
        return object;
    }

    private BasicDBObject getDBObjectFromStatistics(Statistics statistics) {
        BasicDBObject object= new BasicDBObject();
        object.append("minute", statistics.getMinute());
        object.append("ipFrom", statistics.getIpFrom());
        object.append("ipTo", statistics.getIpTo());
        object.append("nginx", statistics.getNginx());
        object.append("status", statistics.getStatus());
        object.append("totalOcurrencies", statistics.getTotalOcurrencies());
        object.append("totalRequestTime", statistics.getTotalRequestTime());
        object.append("totalRequestBytes", statistics.getTotalRequestBytes());
        object.append("totalBytesSent", statistics.getTotalBytesSent());
        object.append("req10", statistics.getReq10());
        object.append("req50", statistics.getReq50());
        object.append("req100", statistics.getReq100());
        object.append("req300", statistics.getReq300());
        object.append("req1000", statistics.getReq1000());
        object.append("req10000", statistics.getReq10000());
        return object;
    }

    public List<String> splitString(String wordToSplit, char splitBy){
        List<String> list = new ArrayList<String>();
        int pos = 0, end;
        while ((end = wordToSplit.indexOf(splitBy, pos)) >= 0) {
            list.add(wordToSplit.substring(pos, end));
            pos = end + 1;
        }
        return list;
    }

    public String getMinute(String line){
        int pos= 0;
        return line.substring(pos, line.indexOf(' ', pos));
    }


    public void processLine(final String line) {
        String minute = line.split("\\s+")[0].substring(0, 16);
        System.out.println("structure.getCounter() = " + structure.getCounter());
//        String minute = line.split("\\s+")[0];
        if (structure.getActualMinute() == null) {
            changeMinute(minute);
        }
        if (!structure.getActualMinute().equals(minute)) {
            changeMinute(minute);
            saveInDBAndWriteInFile();
            structure.setMap(new ConcurrentHashMap<Integer, ConcurrentMap<Integer, Statistics>>());
        }
        saveInConcurrentMap(understandLine(line));
    }

    private void changeMinute(String minute) {
        structure.setActualMinute(minute);
    }

    private void saveInDBAndWriteInFile() {
        Iterator<ConcurrentMap.Entry<Integer, ConcurrentMap<Integer, Statistics>>> iterator = ((structure.getMap()).entrySet()).iterator();
        // write each line of structure.getConcurrentMap() in text
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("output.log", "UTF-8");
            while (iterator.hasNext()) {
                ConcurrentMap.Entry<Integer, ConcurrentMap<Integer, Statistics>> entry = iterator.next();
                Iterator<ConcurrentMap.Entry<Integer, Statistics>> statIt = (entry.getValue().entrySet()).iterator();
                while (statIt.hasNext()) {
                    ConcurrentMap.Entry<Integer, Statistics> statEntry = statIt.next();
                    String lineToWrite = statEntry.getValue().getLine();
                    writer.println(lineToWrite);
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
