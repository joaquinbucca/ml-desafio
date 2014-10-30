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


    public Statistics(Entry entry){
        this.ipFrom= entry.getIpFrom();
        this.ipTo= entry.getIpTo();
        this.minute= entry.getMinute();
        this.nginx= entry.getNginx();
        this.status= entry.getStatus();
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

    public void update(Entry entry) {
        if(true){ //todo: ver que quiere decir con q coincida el status
            totalOcurrencies++;
        }
        totalRequestTime += entry.getRequest_time();
        totalRequestBytes += entry.getRequest_length();
        totalBytesSent += entry.getBytes_sent();
        if (entry.getRequest_time() < 0.01) req10++;
        else if (entry.getRequest_time() < 0.050){
            req50++;
        }else if (entry.getRequest_time() < 0.100){
            req100++;
        }else if (entry.getRequest_time() < 0.300){
            req300++;
        }else if (entry.getRequest_time() < 1.000){
            req1000++;
        }else if (entry.getRequest_time() < 10.000){
            req10000++;
        }

    }

    public String getLine() {
        StringBuilder builder= new StringBuilder(minute).append("\t").append(nginx).append("\t").append(ipFrom).
                append("\t").append(ipTo).append("\t").append(status).append("\t").append(totalOcurrencies).
                append("\t").append(totalRequestTime).append("\t").append(totalRequestBytes).append("\t").
                append(totalBytesSent).append("\t").append(req10).append("\t").append(req50).append("\t").
                append(req100).append("\t").append(req300).append("\t").append(req1000).
                append("\t").append(req10000);
        return builder.toString();
    }
}
