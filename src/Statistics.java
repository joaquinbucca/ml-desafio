/**
 * Created by joaquin on 29/10/14.
 */
public class Statistics {

    private String ipFrom;
    private String ipTo;
    private String minute;
    private String nginx;
    private int status;
    private int totalOcurrencies;
    private float totalRequestTime;
    private long totalRequestBytes;
    private long totalBytesSent;
    private int req10;
    private int req50;
    private int req100;
    private int req300;
    private int req1000;
    private int req10000;


    public Statistics(EntryData entryData){
        this.ipFrom= entryData.getIpFrom();
        this.ipTo= entryData.getIpTo();
        this.minute= entryData.getMinute();
        this.nginx= entryData.getNginx();
        this.status= entryData.getStatus();
        this.totalOcurrencies = 0;
        this.totalRequestBytes = 0;
        this.totalRequestTime = 0;
        this.totalBytesSent = 0;
        this.req10 = 0;
        this.req50 = 0;
        this.req100 = 0;
        this.req300 = 0;
        this.req1000 = 0;
        this.req10000 = 0;
    }

    public void update(EntryData entryData) {
        if(true){ //todo: ver que quiere decir con q coincida el status
            totalOcurrencies++;
        }
        totalRequestTime += entryData.getRequest_time();
        totalRequestBytes += entryData.getRequest_length();
        totalBytesSent += entryData.getBytes_sent();
        if (entryData.getRequest_time() < 0.01) req10++;
        else if (entryData.getRequest_time() < 0.050){
            req50++;
        }else if (entryData.getRequest_time() < 0.100){
            req100++;
        }else if (entryData.getRequest_time() < 0.300){
            req300++;
        }else if (entryData.getRequest_time() < 1.000){
            req1000++;
        }else if (entryData.getRequest_time() < 10.000){
            req10000++;
        }

    }

    public String getLine() {
        return minute + "\t" + nginx + "\t" + ipFrom + "\t" + ipTo + "\t" + status + "\t" + totalOcurrencies + "\t" + totalRequestTime + "\t" + totalRequestBytes + "\t" + totalBytesSent + "\t" + req10 + "\t" + req50 + "\t" + req100 + "\t" + req300 + "\t" + req1000 + "\t" + req10000;
    }
}
