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
public class PgHis {
    private String uTube;
    private double[] uTime;
    private double[] value;

    public static List<PgHis> getPgHis(double[][] arr) {
        List<PgHis> pgHisList = new ArrayList<>();
        int length = 0;
        for (int j = 0; j < arr[0].length; j++) {
            double sum = 0.0;
            for (int i = 0; i < arr.length; i++) {
                sum += arr[i][j];
            }
            if (j != 0 && sum != 0.0) {
                length ++;
            }
            j += 11;
        }
        //length = 49;

        for (int i = 1; i < arr.length; i++) {
            PgHis pgHis = new PgHis();
            double[] time = new double[length+1];
            double[] value = new double[length+1];
            int count = 0;
            for (int j = 0; j < arr[i].length; j++) {
                time[count] = j / 60.0;
                value[count] = arr[i][j];
                count ++;
                j += 11;
                if (count == 50) {
                    break;
                }
            }
            pgHis.setUTube("第" + i + "段U型管");
            pgHis.setUTime(time);
            pgHis.setValue(value);
            pgHisList.add(pgHis);
        }
        return pgHisList;
    }
}
