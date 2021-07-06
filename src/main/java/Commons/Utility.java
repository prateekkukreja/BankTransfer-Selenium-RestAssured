package Commons;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.Assert;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public void findHrefInXML(String html) {
        Pattern p = Pattern.compile("href=\"(.*)\"", Pattern.DOTALL);
        Matcher m = p.matcher(html);
        if (m.find()) {
            // Get all groups for this match
            for (int i = 0; i <= m.groupCount(); i++) {
                String groupStr = m.group(i);
                System.out.println(groupStr);

            }
        }
    }


    public String ReadXML(String html) {
        String token = null;
        try {
            Document document = Jsoup.parse(html);
            Element body = document.head();
            Elements link = document.select("a[href]");

            String[] arr = link.get(0).toString().split("=");
            String[] arr1 = arr[2].split("\">");
            token = arr1[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }


    public boolean AssertOpenNewAccount(String html) {
        boolean flag = false;
        try {
            Document document = Jsoup.parse(html);
            Elements links = document.select("select");

            Element link = links.get(0);
            Assert.assertEquals(link.childNode(1).childNode(0).toString(), "CHECKING");
            Assert.assertEquals(link.childNode(3).childNode(0).toString(), "SAVINGS");
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void ReadXMLWithParam(String html) {
        try {
            Document document = Jsoup.parse(html);
            Element body = document.body();

//            Elements link = document.select("a[href]");

            Map<String, Object> map;
            ObjectMapper mapper = new ObjectMapper();

            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            String bodyText = body.ownText();

            map = mapper.readValue(String.valueOf(bodyText), new TypeReference<Map<String, Object>>() {
            });

            System.out.println(map.get("serviceUrl"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
