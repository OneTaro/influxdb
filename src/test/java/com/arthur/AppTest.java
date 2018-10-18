package com.arthur;

import static org.junit.Assert.assertTrue;

import com.arthur.util.InfluxDbUtil;
import org.influxdb.dto.QueryResult;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testInfluxDb() {
        InfluxDbUtil influxDbUtil = InfluxDbUtil.setUp();
        Map<String, String> tags = new HashMap<>();
        Map<String, Object> fields = new HashMap<>();

        tags.put("tag_name", "mointor_name");
        fields.put("tag_value", "vvvvvv");
        fields.put("tag_value1", "wwwww");

        influxDbUtil.insert(tags, fields);

        QueryResult query = influxDbUtil.query("select * from test");
        for(QueryResult.Result result : query.getResults()) {
            List<QueryResult.Series> series = result.getSeries();
            for (QueryResult.Series ser : series) {
                List<String> columns = ser.getColumns();
                columns.forEach(str -> {
                    System.out.print(str + "\t");
                });
                System.out.println();

                List<List<Object>> values = ser.getValues();
                values.forEach(objects -> {
                    objects.forEach(object -> {
                        System.out.print(object + "\t");
                    });
                    System.out.println();
                });

            }
        }
        System.out.println(query);
    }
}
