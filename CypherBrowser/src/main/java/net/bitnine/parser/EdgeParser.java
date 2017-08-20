package net.bitnine.parser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.postgresql.util.GT;
import org.postgresql.util.PGtokenizer;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

import net.bitnine.domain.Edge;
import net.bitnine.util.TopCommaTokenizer;

public class EdgeParser {
    private static Pattern _pattern;
    static {
        _pattern = Pattern.compile("(.+)\\[(\\d+)\\.(\\d+)\\]\\[(\\d+)\\.(\\d+),(\\d+)\\.(\\d+)\\](.*)");
    }

    /**
     * 파싱되지 않은 쿼리결과문자열을 파싱하여 VertextList를 생성하는 메소드
     * @param result
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public List<Edge> createParsedVertextList(String result) throws SQLException, ParseException {
        TopCommaTokenizer topCommaTokenizer = new TopCommaTokenizer(result);
        List<Edge> edgeList = new ArrayList<>();
         
        for (int i = 0; i < topCommaTokenizer.getSize(); i++) {
            String p = PGtokenizer.removeBox(topCommaTokenizer.getToken(i));
            
            Edge edge = createParsedEdge(p);
            edgeList.add(edge);
        }
        return edgeList;
    }

    /**
     * 
     * 쿼리를 실행한 결과값을 Edge로 변환
     * @param result
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public Edge createParsedEdge(String result) throws SQLException, ParseException {

        String id;
        String type = "edge"; 
        String name;
        String source;
        String target;
        JSONObject  props;

        JSONParser parser = new JSONParser();
        
        Matcher m = _pattern.matcher(result);
        if (m.find()) {             // 그룹별로 데이터를 가져와서 설정함.
            name = m.group(1);
            id = m.group(2) + "." + m.group(3);
            source = m.group(4) + "." + m.group(5);
            target = m.group(6) + "." + m.group(7);
            String properties = m.group(8);
            if (properties == null)
                props = new JSONObject();       // properties가 null일 경우 빈 JSONObject로 설정
            else
                props = (JSONObject) parser.parse(properties);
        }
        else {
            throw new PSQLException(GT.tr("Conversion to type {0} failed: {1}.", new Object[]{type, result}),
                    PSQLState.DATA_TYPE_MISMATCH);
        }
        
        return new Edge(id, type, name, source, target, props);
    }
}
