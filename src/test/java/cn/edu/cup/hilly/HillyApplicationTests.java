package cn.edu.cup.hilly;

import cn.edu.cup.hilly.calculate.hilly.large.ExcelData;
import cn.edu.cup.hilly.dataSource.model.ExcelFile;
import cn.edu.cup.hilly.dataSource.model.mongo.result.KeyPoint;
import cn.edu.cup.hilly.dataSource.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HillyApplicationTests {

    @Autowired
    FileService fileService;


    @Test
    void contextLoads() {
        double [][] lz;
        lz = ExcelData.Graphic();       //运用地形简化程序得到的三点式地形
        for (int i = 0; i < lz.length; i++) {
            for (int j = 0; j < lz[i].length; j++) {
                System.out.print(lz[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("+====================================+");

        ExcelFile excelFile = fileService.find("60a47b10a5ef326e08ed1125");
        double[][] lz_sql = excelFile.getLz();  //三角式地形数据
        for (int i = 0; i < lz_sql.length; i++) {
            for (int j = 0; j < lz_sql[i].length; j++) {
                System.out.print(lz_sql[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("+======================================+");

        for (int i = 0; i < lz.length; i++) {
            for (int j = 0; j < lz[i].length; j++) {
                System.out.print((lz[i][j] - lz_sql[i][j]) + " ");
            }
            System.out.println();
        }

        System.out.println("ExcelData.inum = " + ExcelData.inum);
        System.out.println("excelFile.getInum() = " + excelFile.getInum());
    }

    @Test
    public void test01() {
        System.out.println("14650 % 60 = " + 12660.0 % 60.0);
    }

    @Test
    public void test02() {
        KeyPoint keyPoint = new KeyPoint(750.00);
        System.out.println("keyPoint.MileageAfter() = " + keyPoint.MileageAfter());
        System.out.println("keyPoint.KeyPointNumb() = " + keyPoint.KeyPointNumb());
    }

}