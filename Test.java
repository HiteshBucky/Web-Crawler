import java.net.URL;
import java.util.*;
import java.io.*;
import java.util.regex.*;

class WebCrawler {

    private Queue<String> queue;
    private List<String > discoveredWebsitesList;

    public WebCrawler(){
        this.queue=new LinkedList<>();
        this.discoveredWebsitesList=new ArrayList<>();
    }

    public void discoverWeb(String root){

        this.queue.add(root);
        this.discoveredWebsitesList.add(root);

        while(!queue.isEmpty()){

            String v=this.queue.remove();
            String rawHtml=readURL(v);

            String regexp="http://(\\w+\\.)*(\\w+)";

            Pattern pattern=Pattern.compile(regexp);
            Matcher matcher=pattern.matcher(rawHtml);

            while(matcher.find()){
                String actualUrl=matcher.group();

                if(!discoveredWebsitesList.contains(actualUrl)){
                    discoveredWebsitesList.add(actualUrl);
                    System.out.println("The web site has been visited: "+actualUrl);
                    queue.add(actualUrl);
                }
            }

        }
    }

    private String readURL(String v){
        String rawHtml="";

        try {

            URL url=new URL(v);
            BufferedReader in=new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine="";

            while((inputLine=in.readLine()) !=null){
                rawHtml +=inputLine;
            }

            in.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return rawHtml;
    }

}


public class Test{
	public static void main(String[] args) {
		WebCrawler crawler=new WebCrawler();
        String rootUrl="http://www.google.com";
        crawler.discoverWeb(rootUrl);
	}
}