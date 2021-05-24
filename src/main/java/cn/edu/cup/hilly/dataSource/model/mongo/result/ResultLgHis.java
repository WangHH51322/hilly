package cn.edu.cup.hilly.dataSource.model.mongo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ResultLgHis {

    @Id
    private String _id;
    private Integer segments;
    private Double singleLength;
    private double[][] lgHis;
    private Map<Double, double[]> lgHisMap;
}
