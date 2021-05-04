package cn.edu.cup.hilly.dataSource.model.mongo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * @author CodeChap
 * @date 2021-05-04- 19:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ResultAllLineFP {
    @Id
    private String _id;
    private Integer segments;
    private Double singleLength;
    private double[][] aLineFP;
    private Map<Integer, double[]> aLineFPMap;
}
