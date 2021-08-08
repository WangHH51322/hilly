package cn.edu.cup.hilly.dataSource.model.mongo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * @author CodeChap
 * @date 2021-05-04- 21:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ResultSimple {
    @Id
    private String _id;
    private double[][] lz;
    private double[][] pigV;
    private double[][] pigL;
    private double[][] aLSP;
    private double[][] dMgP;
    private Map<String,double[][]> dMgPK;
    private double[][] mG;
    private double[][] gasRa;
    private double[][] lineL;
    private double[][] Q;
}
