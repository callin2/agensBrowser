package net.bitnine.parser;

import net.bitnine.domain.Vertex;
import net.bitnine.util.TopCommaTokenizer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.postgresql.util.GT;
import org.postgresql.util.PGtokenizer;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VertexParser {
    private static final Pattern _pattern;
    
    static {
        _pattern = Pattern.compile("(.+)\\[(\\d+)\\.(\\d+)\\](.*)");
    }

    /**
     * 
     * 쿼리를 실행한 결과값을 vertextList로 변환
     * @param result
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public List<Vertex> createParsedVertextList(String result) throws SQLException, ParseException {
        TopCommaTokenizer topCommaTokenizer = new TopCommaTokenizer(result);
        List<Vertex> vertextList = new ArrayList<>();
         
        for (int i = 0; i < topCommaTokenizer.getSize(); i++) {
//            System.out.println("topCommaTokenizer.getToken: " + topCommaTokenizer.getToken(i));

            String p = PGtokenizer.removeBox(topCommaTokenizer.getToken(i));
            
            Vertex vertex = createParsedVertext(p);
            vertextList.add(vertex);
        }
        return vertextList;
    }
    
    /**
     * 파싱되지 않은 쿼리결과문자열을 파싱하여 VertextList를 생성하는 메소드
     * @param result
     * @return
     * @throws SQLException 
     * @throws ParseException 
     */
    public Vertex createParsedVertext(String result) throws SQLException, ParseException {
        String id;
        String name;
        String type = "vertex";
        JSONObject props;

        JSONParser parser = new JSONParser();
        
        Matcher m = _pattern.matcher(result);
        if (m.find()) {
            name = m.group(1);
            id = m.group(2) + "." + m.group(3);
            String property = m.group(4);
            if (property == null)
                props = new JSONObject();   // DEFAULT: {}
            else
                props = (JSONObject) parser.parse(property);
        } else {
            throw new PSQLException(GT.tr("Conversion to type {0} failed: {1}.", new Object[]{type, result}), PSQLState.DATA_TYPE_MISMATCH);
        }

        return new Vertex(id, type, name, props);
    }
}
