import java.util.List;

/**
 * Created by joaquin on 30/10/14.
 */
public class EntryData {

    private String time;
    private String proxy_host;
    private String upstream; // ip to
    private int status;     // http code
    private float request_time;
    private String upstream_status;
    private String upstream_response_time;
    private String upstrea_address; //ip to si upstream es null
    private String client_ip; //ip from
    private long request_length;
    private long bytes_sent;

    private String request_method;
    private String scheme ;
    private String request_uri ;
    private String http_referer ;
    private String nginx_host ; //nginx
    private String  http_host;
    private String http_user_agent ;
    private String request_body ;
    private String http_x_ssl_early_termination ;
    private String http_cookie ;
    private String response_headers ;
    private String request_headers ;
    private String proxy_response_headers ;
    private String proxy_request_headers ;
    private String http_x_public ;
    private String ds ;
    private String nginx_pool ;
    private String x_request_id ;
    private int ipFromHashed;
    private int ipToHashed;


    public EntryData(List<String> elems) {
        this.time = elems.get(0);
        this.proxy_host = elems.get(1);
        this.upstream = elems.get(2);
        this.status = elems.get(3) != null ? Integer.parseInt(elems.get(3)) : -1;
        this.request_time = elems.get(4) != null ? Float.parseFloat(elems.get(4)) : 0;
        this.upstream_status = elems.get(5);
        this.upstream_response_time = elems.get(6);
        this.upstrea_address = elems.get(7);
        this.client_ip = elems.get(8);
        int index= client_ip.lastIndexOf(",");
        if(index != -1){
            this.client_ip= client_ip.substring(index+1, client_ip.length());
        }
        this.request_length = elems.get(9) != null ? Long.parseLong(elems.get(9)) : 0;
        this.bytes_sent = elems.get(10) != null ? Long.parseLong(elems.get(10)) : 0;
        this.request_method = elems.get(11);
        this.scheme = elems.get(12);
        this.request_uri = elems.get(13);
        this.http_referer = elems.get(14);
        this.nginx_host = elems.get(15);
        this.http_host = elems.get(16);
        this.http_user_agent = elems.get(17);
        this.request_body = elems.get(18);
        this.http_x_ssl_early_termination = elems.get(19);
        this.http_cookie = elems.get(20);
        this.response_headers = elems.get(21);
        this.request_headers = elems.get(22);
        this.proxy_response_headers = elems.get(23);
//        this.proxy_request_headers = elems.get(24);
        this.http_x_public = elems.get(24);
        this.ds = elems.get(25);
//        this.nginx_pool = elems.get(27);
        this.x_request_id = elems.get(26);
        this.ipFromHashed= getIpFrom().hashCode();
        this.ipToHashed= getIpTo().hashCode();
    }

    public String getMinute() {
        return time.substring(0, 16);
    }

    public String getIpFrom(){
        return client_ip;
    }

    public String getNginx(){
        return nginx_host;
    }

    public String getIpTo(){
        return !upstream.equals("-") ? upstream : upstrea_address;
    }

    public int getStatus(){
        return status;
    }

    public float getRequest_time(){
        return request_time;
    }

    public long getRequest_length(){
        return request_length;
    }

    public long getBytes_sent(){
        return bytes_sent;
    }


    public int getIpToHashed() {
        return ipToHashed;
    }

    public int getIpFromHashed() {
        return ipFromHashed;
    }
}
