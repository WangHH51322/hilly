package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Arrays;

@ToString
@Data
@AllArgsConstructor
public class Curve {
    private String name;
    private String unit;
    private String value;

    public Curve() {
        this.name = "流量-扬程曲线";
        this.unit = "";
//        this.value = convert(flowRate,waterHead);
        this.value = "[[0.0, 0.0], [1.0, 1.0], [2.0, 2.0], [3.0, 3.0]]";
    }

    private String convert(String flowRate,String waterHead) {
        double[] flow = blow(flowRate);
        double[] head = blow(waterHead);
        double[][] result = new double[flow.length][2];
//        String[][] result = new String[flow.length][2];
        for (int i = 0; i < flow.length; i++) {
//            result[i][0] = String.valueOf(flow[i]);
//            result[i][1] = String.valueOf(head[i]);
            result[i][0] = flow[i];
            result[i][1] = head[i];
        }
        return Arrays.deepToString(result);
    }

    private double[] blow(String s) {
        s = s.replace("[","");
        s = s.replace("]","");
        String[] ss = s.split(",");
        double[] ii = new double[ss.length];
        for (int i = 0; i < ii.length; i++) {
            ii[i] = Double.parseDouble(ss[i]);
        }
        return ii;
    }
}
