import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by joaquin on 30/10/14.
 */
public class Structure {

    private static Structure structure;

    private String actualMinute;
    private ConcurrentMap<Integer, ConcurrentMap<Integer, Statistics>> map= new ConcurrentHashMap<Integer, ConcurrentMap<Integer, Statistics>>();
    private MongoClient mongo;
    private DB db;
    private DBCollection collection;

    public DB getDb() {
        return db;
    }

    public DBCollection getCollection() {
        return collection;
    }

    public void initDB(){
        try {
            mongo= new MongoClient("localhost", 27017);
            db = mongo.getDB("testDB");
            collection= db.getCollection("logs");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private Structure(){
        if (structure != null){
            //todo: alguna excepcion de q no
        }
    }

    public static Structure getInstance(){
        if(structure == null){
            structure= new Structure();
        }
        return structure;
    }


    public  String getActualMinute(){
        return actualMinute;
    }

    public void setActualMinute(String actualMinut){
        this.actualMinute= actualMinut;
    }

    public ConcurrentMap<Integer, ConcurrentMap<Integer, Statistics>> getMap() {
        return map;
    }

    public void setMap(ConcurrentMap<Integer, ConcurrentMap<Integer, Statistics>> map) {
        this.map = map;
    }
}
