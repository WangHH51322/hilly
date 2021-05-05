package cn.edu.cup.hilly.dataSource.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.*;

@Data
@NoArgsConstructor
@ToString
public class SizeChange {
    public static void main(String[] args) {
        double[][] d = new double[3][2];
        double[][] reverse = reverse(d);
        for (int i = 0; i < reverse.length; i++) {
            System.out.println(" ");
            for (int j = 0; j < reverse[i].length; j++) {
                System.out.print(" " + reverse[i][j]);
            }
        }
    }
    private double[][] resultDPL;
    private double[][] resultsData;
    private double[] resultData;
    private double startTime;
    private double delta;

//    public SizeChange(double[][] resultDPL) {
//        this.resultDPL = resultDPL;
//    }

    public SizeChange(double[][] resultsData) {
        this.resultsData = resultsData;
    }
    public SizeChange(double[] resultData,double startTime,double delta) {
        this.resultData = resultData;
        this.startTime = startTime;
        this.delta = delta;
    }

    public static double[][] reverse(double[][] data) {
        double[][] doubles = new double[data[0].length][data.length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                doubles[j][i] = data[i][j];
            }
        }
        return doubles;
    }

    public double[][] ResultAfterChange2() {
        int size = 0;
        for (int i = 0; i < resultData.length; i++) {
            Double re1 = resultData[i];
            Double re2 = resultData[i+1];
            if ((re1 == 0.0&&re2 == 0.0) || re1.isNaN()) {
                size = i;
                break;
            }
        }
        double[][] results = new double[size][2];
        for (int i = 0; i < size; i++) {
            results[i][0] = startTime;
            results[i][1] = resultData[i];
            startTime += delta;
        }
        return results;
    }

//    public Map<Integer,double[]> DPLAfterChange() throws NoSuchFieldException, IllegalAccessException {
//        /**
//         * 中间参数
//         */
//        Map<Integer,double[]> dPLs = new HashMap<>();
//        List<double[]> dPLsLists = new ArrayList<>();
//        /**
//         * 将输入的double[][]进行行截取
//         */
//        for (int j = 0; j < resultDPL.length; j+=2) {
//            double sum = 0;
//            double[] dPL = new double[resultDPL[0].length];
//            for (int k = 0; k < resultDPL[j].length; k++) {
//                dPL[k] = resultDPL[j][k];
//                sum += resultDPL[j][k];
//            }
//            if (j == 0) {
//                dPLsLists.add(dPL);
//            }
//            if (j != 0 && sum != 0.0) {
//                dPLsLists.add(dPL);
//            }
//        }
//        /**
//         * 判断是否为初始时刻,此时全线的压力数据全部为0,直接输出
//         */
//        if (dPLsLists.size() == 0) {
//            double[] lastDPL = dPLsLists.get(0);
//            dPLs.put(0,lastDPL);
//            return dPLs;
//        }
//        /**
//         * 根据每个时刻最后一行数据的长度,对此前时刻全部数据进行截取
//         * 这样会造成一种结果,在计算的初期,由于后续管段还没有压力,因此是没有数据的
//         */
//        int size = 0;
//        double[] lastDPL = dPLsLists.get(dPLsLists.size() - 1);
//        for (int i = 0; i < lastDPL.length; i++) {
//            if (lastDPL[i] == 0) {
//                size = i;
//                break;
//            }
//        }
//        for (int i = 0; i < dPLsLists.size(); i++) {
//            double[] dPLList = dPLsLists.get(i);
//            double[] dPL = new double[size];
//            for (int j = 0; j < size; j++) {
//                dPL[j] = dPLList[j];
//            }
//            dPLs.put(i*10,dPL);
//        }
//        /**
//         * 将Map排序并输出
//         */
//        return new TreeMap(dPLs);
//    }

    public Map<Integer,double[]> ResultAfterChange() throws NoSuchFieldException, IllegalAccessException {
        /**
         * 中间参数
         */
        Map<Integer,double[]> results = new HashMap<>();
        List<double[]> resultsLists = new ArrayList<>();
        /**
         * 将输入的double[][]进行行截取
         */
        loop:for (int j = 0; j < resultsData.length; j+=2) {
            double sum = 0;
            double[] dPL = new double[resultsData[0].length];
            for (int k = 0; k < resultsData[j].length; k++) {
                Double re = resultsData[j][k];
                if (re.isNaN()) {
                    break loop;
                }
                dPL[k] = resultsData[j][k];
                sum += resultsData[j][k];
            }
            if (j == 0) {
                resultsLists.add(dPL);
            }
            if (j != 0 && sum != 0.0) {
                resultsLists.add(dPL);
            }
        }
        /**
         * 判断是否为初始时刻,此时全线的压力数据全部为0,直接输出
         */
        if (resultsLists.size() == 0) {
            double[] lastDPL = resultsLists.get(0);
            results.put(0,lastDPL);
            return results;
        }
        /**
         * 根据每个时刻最后一行数据的长度,对此前时刻全部数据进行截取
         * 这样会造成一种结果,在计算的初期,由于后续管段还没有压力,因此是没有数据的
         */
        int size = 0;
        double[] lastDPL = resultsLists.get(resultsLists.size() - 1);
//        System.out.println("resultsLists:"+ resultsLists.size());
        for (int i = 0; i < lastDPL.length; i++) {
            if (lastDPL[i] == 0 && lastDPL[i+1] == 0) {
                size = i;
                break;
            }
        }
//        System.out.println("size:" + size);
//        System.out.println("转换之后的值: ");
        for (int i = 0; i < resultsLists.size(); i++) {
            double[] dPLList = resultsLists.get(i);
//            System.out.println("resultsLists:");
            for (double v : dPLList) {
//                System.out.print("dPLList_" + v);
            }
            double[] dPL = new double[size];
            for (int j = 0; j < size; j++) {
                dPL[j] = dPLList[j];
//                System.out.print("dPL+" + dPL[j] + " ");
            }
            results.put(i*10,dPL);
        }
        /**
         * 将Map排序并输出
         */
        return new TreeMap(results);
    }

}
