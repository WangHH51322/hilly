package cn.edu.cup.hilly.dataSource.model.mongo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CodeChap
 * @date 2021-05-04- 19:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document()
public class ResultAllLineFP {
    @Id
    private String _id;
    private String projectId;
    private Integer segments;
    private Double singleLength;
    private Double timeFrame;
    private double[][] aLineFP;
    private double[][] lz;
    private Map<Double, double[]> allLineFPMap;

    public Map<Double, List<outPut>> convertALFP(double startMile) {
        Map<Double, List<outPut>> result = new HashMap<>();
        for (Map.Entry<Double, double[]> entry : allLineFPMap.entrySet()) {
            List<outPut> outPuts = new ArrayList<>();
            double[] value = entry.getValue();
            for (int i = 1; i < value.length; i++) {
                outPut outPut = new outPut();
                outPut.setStart(startMile + (i-1)*0.5);
                outPut.setEnd(startMile + (i)*0.5);
                outPut.setType((int) value[i]);
                outPuts.add(outPut);
            }
            result.put(entry.getKey(),outPuts);
        }
        return result;
    }

    public class outPut {
        private double start;
        private double end;
        private int type;

        public double getStart() {
            return start;
        }

        public void setStart(double start) {
            this.start = start;
        }

        public double getEnd() {
            return end;
        }

        public void setEnd(double end) {
            this.end = end;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
