package cn.edu.cup.hilly.dataSource.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.*;

@Data
@NoArgsConstructor
@ToString
public class SizeChange {
    private double[][] resultDPL;

    public SizeChange(double[][] resultDPL) {
        this.resultDPL = resultDPL;
    }

    public Map<Integer,double[]> DPLAfterChange() throws NoSuchFieldException, IllegalAccessException {
        /**
         * 中间参数
         */
        Map<Integer,double[]> dPLs = new HashMap<>();
        List<double[]> dPLsLists = new ArrayList<>();
        /**
         * 将输入的double[][]进行行截取
         */
        for (int j = 0; j < resultDPL.length; j+=2) {
            double sum = 0;
            double[] dPL = new double[resultDPL[0].length];
            for (int k = 0; k < resultDPL[j].length; k++) {
                dPL[k] = resultDPL[j][k];
                sum += resultDPL[j][k];
            }
            if (j == 0) {
                dPLsLists.add(dPL);
            }
            if (j != 0 && sum != 0.0) {
                dPLsLists.add(dPL);
            }
        }
        /**
         * 判断是否为初始时刻,此时全线的压力数据全部为0,直接输出
         */
        if (dPLsLists.size() == 0) {
            double[] lastDPL = dPLsLists.get(0);
            dPLs.put(0,lastDPL);
            return dPLs;
        }
        /**
         * 根据每个时刻最后一行数据的长度,对此前时刻全部数据进行截取
         * 这样会造成一种结果,在计算的初期,由于后续管段还没有压力,因此是没有数据的
         */
        int size = 0;
        double[] lastDPL = dPLsLists.get(dPLsLists.size() - 1);
        for (int i = 0; i < lastDPL.length; i++) {
            if (lastDPL[i] == 0) {
                size = i;
                break;
            }
        }
        for (int i = 0; i < dPLsLists.size(); i++) {
            double[] dPLList = dPLsLists.get(i);
            double[] dPL = new double[size];
            for (int j = 0; j < size; j++) {
                dPL[j] = dPLList[j];
            }
            dPLs.put(i*10,dPL);
        }
        /**
         * 将Map排序并输出
         */
        return new TreeMap(dPLs);
    }

}
