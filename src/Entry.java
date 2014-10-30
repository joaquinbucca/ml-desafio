import java.util.List;

/**
 * Created by joaquin on 30/10/14.
 */
public class Entry {

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


    public Entry(List<String> elems) {
        this.proxy_host = elems.get(0);
        this.upstream = elems.get(1);
        this.status = elems.get(2) != null ? Integer.parseInt(elems.get(2)) : -1;
        this.request_time = elems.get(3) != null ? Float.parseFloat(elems.get(3)) : 0;
        this.upstream_status = elems.get(4);
        this.upstream_response_time = elems.get(5);
        this.upstrea_address = elems.get(6);
        this.client_ip = elems.get(7);
        this.request_length = elems.get(8) != null ? Long.parseLong(elems.get(8)) : 0;
        this.bytes_sent = elems.get(9) != null ? Long.parseLong(elems.get(9)) : 0;
        this.request_method = elems.get(10);
        this.scheme = elems.get(11);
        this.request_uri = elems.get(12);
        this.http_referer = elems.get(13);
        this.nginx_host = elems.get(14);
        this.http_host = elems.get(15);
        this.http_user_agent = elems.get(16);
        this.request_body = elems.get(17);
        this.http_x_ssl_early_termination = elems.get(18);
        this.http_cookie = elems.get(19);
        this.response_headers = elems.get(20);
        this.request_headers = elems.get(21);
        this.proxy_response_headers = elems.get(22);
        this.proxy_request_headers = elems.get(23);
        this.http_x_public = elems.get(24);
        this.ds = elems.get(25);
        this.nginx_pool = elems.get(26);
        this.x_request_id = elems.get(27);
    }

    public String getMinute() {
        return time;
    }

    public String getIpFrom(){
        return client_ip;
    }

    public String getNginx(){
        return nginx_host;
    }

    public String getIpTo(){
        return upstream != null ? upstream : upstrea_address;
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


}
