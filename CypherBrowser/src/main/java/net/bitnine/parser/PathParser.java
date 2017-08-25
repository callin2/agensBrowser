package net.bitnine.parser;

import net.bitnine.domain.Edge;
import net.bitnine.domain.Path;
import net.bitnine.domain.Vertex;
import net.bitnine.util.TopCommaTokenizer;
import org.json.simple.parser.ParseException;
import org.postgresql.util.GT;
import org.postgresql.util.PGtokenizer;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PathParser {
    
    /**
     * 
     * 쿼리를 실행한 결과값을 Path로 변환
     * @param result
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public Path createParsedPath (String result) throws SQLException, ParseException {
        String type = "path";
        List<Vertex> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        VertexParser vertexParser = new VertexParser();
        EdgeParser edgeParser = new EdgeParser();
        
        String p = PGtokenizer.removeBox(result);
        TopCommaTokenizer t;
        try {
            t = new TopCommaTokenizer(p);
        }
        catch (Exception e) {
            throw new PSQLException(GT.tr("Conversion to type {0} failed: {1}."
                        , new Object[]{type, result})
                    , PSQLState.DATA_TYPE_MISMATCH);
        }

        String source = "";
        String target = ""; 
        
        for (int i = 0; i < t.getSize(); ++i) {
            if (i % 2 == 0) {
                Vertex vertex = vertexParser.createParsedVertext(t.getToken(i));
                
                // source Id와 target Id를 설정
                if (i == 0) source = vertex.getId();
                if (i == t.getSize() - 1) target = vertex.getId();
                
                vertices.add(vertex);
            }
            else {
                edges.add(edgeParser.createParsedEdge(t.getToken(i)));
            }
        }
        return new Path(source, target, vertices, edges);
    }
}
