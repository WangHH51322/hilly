package cn.edu.cup.hilly.dataSource.model.mongo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author CodeChap
 * @date 2021-07-17 20:15
 * @description KeyPoint
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class KeyPoint {

    @Id
    private String _id;

    private String projectId;   //项目id
    private String name;    //关键点名称
    private double mileage; //关键点里程
    private double mileageAfter;    //离关键点最近的里程点
    private int numb;   //关键点在第几列
    private double[][] value1;  //关键点输出数据1 --> 关键点随时间的压力变化
    private double[][] value2;  //关键点输出数据2 --> 关键点随时间的流量变化
    private double[][] value3;  //关键点输出数据3 --> 关键点随时间的排气量变化

    public KeyPoint(double mileage) {
        this.mileage = mileage;
    }

    public double MileageAfter() {
        int multiple = (int) (mileage / 0.50);
        double remainder = mileage % 0.50;
        if (remainder == 0.00) { // 关键点刚好在里程点上
            return mileage;
        } else if (remainder < 0.25) { // 关键点靠近上一个里程点
            return multiple * 0.50;
        }
        return (multiple + 1) * 0.50; // 关键点靠近下一个里程点
    }

    public int KeyPointNumb() {
        numb = (int) (MileageAfter() / 0.50);
        return numb;
    }

    public int getI(double[][] arrL){
        double mileageAfter = mileage * 1000.00;
        int z=0;
        for(int i=1;i<arrL.length;i++){
            if(mileageAfter < arrL[i][2] && mileageAfter >= arrL[i][1]){
                z=i;
            }else if(mileageAfter < arrL[i][3] && mileageAfter >= arrL[i][2]){
                z=i;
            }
        }
        if (z == 0) System.out.println("获取段数失败");
        return z;
    }
}
