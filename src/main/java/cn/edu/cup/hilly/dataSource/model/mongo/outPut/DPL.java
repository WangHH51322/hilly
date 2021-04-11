package cn.edu.cup.hilly.dataSource.model.mongo.outPut;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class DPL {
    private String time;
    private double[] value;

    public static List<DPL> getDPL(double[][] arr) {
        List<DPL> dplList = new ArrayList<>();

        int countY = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i+2][5]==0 && arr[i][5]!=0) {
                countY = i + 1;
                break;
            }
        }

        int countX = 0;
        for (int i = 0; i < arr[0].length; i++) {
            if (arr[countY - 1][i] != 0) {
                countX ++;
            }
        }
        System.out.println("countX:" + countX);
        System.out.println("countY" + countY);

        for (int i = 0; i < countY; i++) {
            DPL dpl = new DPL();
            double[] value = new double[countX - 1];
            for (int j = 1; j < countX; j++) {
                value[j - 1] = arr[i][j]/1000000;
            }
            dpl.setTime(String.valueOf(i*5.0/60.0));
            dpl.setValue(value);
            i += 11;
            dplList.add(dpl);
        }
        return dplList;
    }
}
