package cn.edu.cup.hilly.calculate.hilly.large;

import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

@Data
public class ExcelData {
    private XSSFSheet sheet;
    public static int inum;
    private double[][] terrainData;

    public ExcelData() {
    }

    /**
     * 构造函数，初始化excel数据
     *
     //     * @param filePath  excel路径
     * @param sheetName sheet表名
     */
    ExcelData(File file, String sheetName) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XSSFWorkbook sheets = null;
        try {
            sheets = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取sheet
        sheet = sheets.getSheet(sheetName);
    }
    ExcelData(String filePath, String sheetName) {
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XSSFWorkbook sheets = null;
        try {
            sheets = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取sheet
        sheet = sheets.getSheet(sheetName);
    }

    /**
     * 根据行和列的索引获取单元格的数据
     *
     * @param row
     * @param column
     * @return
     */
    public String getExcelDateByIndex(int row, int column) {
        XSSFRow row1 = sheet.getRow(row);
        String cell = row1.getCell(column).toString();
        return cell;
    }

    /**
     * 根据某一列值为“******”的这一行，来获取该行第x列的值
     *
     * @param caseName
     * @param currentColumn 当前单元格列的索引
     * @param targetColumn  目标单元格列的索引
     * @return
     */
    public String getCellByCaseName(String caseName, int currentColumn, int targetColumn) {
        String operateSteps = "";
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            String cell = row.getCell(currentColumn).toString();
            if (cell.equals(caseName)) {
                operateSteps = row.getCell(targetColumn).toString();
                break;
            }
        }
        return operateSteps;
    }

    //打印excel数据
    public void readExcelData() {
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            //获取列数
            XSSFRow row = sheet.getRow(i);
            int columns = row.getPhysicalNumberOfCells();
            for (int j = 0; j < columns; j++) {
                String cell = row.getCell(j).toString();
                System.out.println(cell);
            }
        }
    }

    public void saveExcelData(double[][] l) {
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            //获取列数
            XSSFRow row = sheet.getRow(i);
            int columns = row.getPhysicalNumberOfCells();
            for (int j = 0; j < columns; j++) {
                //String cell = row.getCell(j).toString();

                l[i][j] = Double.valueOf(row.getCell(j).toString());
                //System.out.println(l[i*j+j]);
            }
        }
    }

//    public static double[][] Graphic(File file, String sheetName) {
//        ExcelData sheet1 = new ExcelData(file,sheetName);
//        //创建储存里程高程数据的数组
//        double[][] lll = new double[sheet1.sheet.getPhysicalNumberOfRows()][2];
//        double[] l = new double[sheet1.sheet.getPhysicalNumberOfRows()];
//        double[] z = new double[sheet1.sheet.getPhysicalNumberOfRows()];
//
//        //读取Excel数据到数组
//        sheet1.saveExcelData(lll);
//
//        for (int i = 0; i < lll.length; i++) {
//            l[i] = lll[i][0];
//            z[i] = lll[i][1];
//        }
//
//        double[] l_st = new double[((int)l[l.length - 1] - (int)l[0])+2];   //*****当原始地形首末点为整数时，l_st倒数第二个数空值
//        double[] z_st = new double[((int)l[l.length - 1] - (int)l[0])+2];   //*****当原始地形首末点为整数时，z_st倒数第二个数空值
//        double[] j_st = new double[((int)l[l.length - 1] - (int)l[0])+2];   //坡度，当前点与前一点的
//        double[] jj_st = new double[((int)l[l.length - 1] - (int)l[0])+2];   //坡度差
//        double[][] lll_st = new double[((int)l[l.length - 1] - (int)l[0])+2][2];   //*****当原始地形首末点为整数时，z_st倒数第二个数空值
//        double[][] lll_new = new double[((int)l[l.length - 1] - (int)l[0])*20][4];   //*****初次简化，剔除千分之五坡度的段
//        double[][] lll_new1 = new double[((int)l[l.length - 1] - (int)l[0])+5][4];   //*****二次简化
//        l_st[0] = l[0];
//        z_st[0] = z[0];
//        if ((int) l[l.length - 1]-l[l.length - 1]!=0.0){      //末点不是整数
//            l_st[(int) (l[l.length - 1] - l[0]) +1] = l[l.length - 1];
//            z_st[(int) (l[l.length - 1] - l[0]) +1] = z[z.length - 1];
//        }else{
//            l_st[(int) (l[l.length - 1] - l[0])] = l[l.length - 1];
//            z_st[(int) (l[l.length - 1] - l[0])] = z[z.length - 1];
//        }
//
//        if (l[l.length - 1] - l[0] > 0) {
//            for (int i = (int) l[0] + 1; i < l[l.length - 1] - l[0]; i++) {
//                l_st[i] = i;
//            }
//        }
//        int k = 1, jjj, j = 1;//l和l_st的首末值相同，中间值不同，因此计数和遍历从1开始
//        double zzz = 0;
//
//        //原始地形高程点密集的处理，1km取平均
//        for (int i = 1; i < l.length; i++) {
//            if (l[i] > l_st[k] && l[i] - l_st[k] <= 1) {      //判断1km终点位置，且不需要插值时的计算
//                for (jjj = j; jjj < i; jjj++) {
//                    zzz = z[jjj] + zzz;
//                }
//                z_st[k] = zzz / (i - j);  //求1km内高程的平均值，如，里程1km处高程平均值为0-1内的高程的平均
//                zzz = 0;
//                k++;
//                j = i;                //记录下1km的起点编号
//            }
//            if (l[i] > l_st[k] && l[i] - l_st[k] > 1) {      //判断1km终点位置，且需要插值时的计算
//                int uuu = (int) (l[i] - l_st[k]);      //计算插值长度
//                for (int u = 1; u <= uuu; u++)            //插值//////////////
//                {
//                    z_st[k] = z_st[k - 1] + (z[i] - z[i - 1]) / (l[i] - l[i - 1]);
//                    k++;
//                }
//                //给空点赋值
//                if(z_st[k]==0){
//                    z_st[k] = z_st[k - 1] + (z[i] - z[i - 1]) / (l[i] - l[i - 1]);
//                }
//            }
//        }
//        for (int kkk = 0; kkk < z_st.length; kkk++) {
//            lll_st[kkk][0] = l_st[kkk];
//            lll_st[kkk][1] = z_st[kkk];
//        }
//        for (int kkk = 1; kkk < z_st.length; kkk++) {//计算坡度
//            j_st[kkk] = (z_st[kkk] - z_st[kkk - 1]) / (l_st[kkk] - l_st[kkk - 1]);
//        }
//        for (int kkk = 1; kkk < z_st.length - 1; kkk++) {//计算坡度差
//            jj_st[kkk] = j_st[kkk] - j_st[kkk + 1];
//        }
//        jj_st[l_st.length - 1] = j_st[l_st.length - 1];
//
//        lll_new[0][0] = l_st[0];
//        lll_new[0][1] = z_st[0];
//        int num = 1;
//        for (int kkk = 1; kkk < z_st.length; kkk++) {//初次简化，剔除千分之五坡度的段
//            if ((j_st[kkk] > 5 || j_st[kkk] < -5)) {
//                lll_new[num][0] = l_st[kkk];//里程
//                lll_new[num][1] = z_st[kkk];//高程
//                num++;
//            }else if ( kkk%5==0 || kkk==z_st.length-1 ){
//                lll_new[num][0] = l_st[kkk];//里程
//                lll_new[num][1] = z_st[kkk];//高程
//                num++;
//            }
//        }
//        for (int kkk = 1; kkk < lll_new.length; kkk++) {//求新简化的地形的坡度
//            if (lll_new[kkk][0] != 0 && lll_new[kkk][1] != 0) {
//                lll_new[kkk][2] = (lll_new[kkk][1] - lll_new[kkk - 1][1]) / (lll_new[kkk][0] - lll_new[kkk - 1][0]);
//            }
//        }
//
//        lll_new1[0][0] = lll_new[0][0];//首点里程保留
//        lll_new1[0][1] = lll_new[0][1];//首点高程保留
//        int num1 = 1, num2 = 0, kkk = 1;
//        do {
//            if (lll_new[kkk][2] * lll_new[kkk - 1][2] < 0) {//存在转折处
//                lll_new1[num1][0] = lll_new[kkk - 1][0];//里程保留
//                lll_new1[num1][1] = lll_new[kkk - 1][1];//高程保留
//                lll_new1[num1 + 1][0] = lll_new[kkk][0];//里程保留
//                lll_new1[num1 + 1][1] = lll_new[kkk][1];//高程保留
//                num1 = num1 + 1;
//            }
//            kkk++;
//            if (lll_new[kkk][2]==0 && lll_new[kkk - 1][2] != 0){
//                lll_new1[num1][0] = lll_new[kkk-1][0];//里程保留
//                lll_new1[num1][1] = lll_new[kkk-1][1];//高程保留
//            }
//        } while (lll_new[kkk][0] != 0 && lll_new[kkk][1] != 0);
//
//        double [][]lj=new double[lll_new1.length][lll_new1[0].length];
//        for (int ui=0;ui<lll_new1.length;ui++){
//            for (int uii=0;uii<lll_new1[0].length;uii++){
//                lj[ui][uii]=lll_new1[ui][uii];
//            }
//        }
//
//        for (int iii = 1; iii < lll_new1.length - 2; iii++) {
//            if (lj[iii-1][0] != 0 && lj[iii][0] == 0)
//            {
//                break;
//            }
//            lll_new1[iii+1][0] = lj[iii][0];//从第二点起，各自向后移动一位，里程
//            lll_new1[iii+1][1] = lj[iii][1];//从第二点起，各自向后移动一位，高程
//        }
//        lll_new1[1][0] = 0.5*(lll_new1[0][0]+lll_new1[2][0]);
//        if (0.5*(lll_new1[0][1]+lll_new1[2][1])-20>0){
//            lll_new1[1][1] = 0.5*(lll_new1[0][1]+lll_new1[2][1])-20;//插入首点至第二点间的点的高程
//        }else if(0.5*(lll_new1[0][1]+lll_new1[2][1])-5>0){
//            lll_new1[1][1] = 0.5*(lll_new1[0][1]+lll_new1[2][1])-5;//插入首点至第二点间的点的高程,防止地形高程变为负值
//        }else if(0.5*(lll_new1[0][1]+lll_new1[2][1])-1>0){
//            lll_new1[1][1] = 0.5*(lll_new1[0][1]+lll_new1[2][1])-1;//插入首点至第二点间的点的高程
//        }
//        inum = 1;
//        for (int iii = 0; iii < lll_new1.length - 1; iii++) {
//            if (lll_new1[iii][0] != 0 && lll_new1[iii + 1][0] == 0)
//            {
//                inum = iii+1;
//                //System.out.println("ggggg");
//                lll_new1[iii+1][0]=lll_new1[iii][0];
//                lll_new1[iii+1][1]=lll_new1[iii][1];
//                lll_new1[iii][0]=0.5*(lll_new1[iii+1][0]+lll_new1[iii-1][0]);
//                if (0.5*(lll_new1[iii+1][1]+lll_new1[iii-1][1])-20>0){
//                    lll_new1[iii][1]=0.5*(lll_new1[iii+1][1]+lll_new1[iii-1][1])-20;
//                }else if(0.5*(lll_new1[iii+1][1]+lll_new1[iii-1][1])-5>0){
//                    lll_new1[iii][1]=0.5*(lll_new1[iii+1][1]+lll_new1[iii-1][1])-5;//插入首点至第二点间的点的高程,防止地形高程变为负值
//                }else if(0.5*(lll_new1[iii+1][1]+lll_new1[iii-1][1])-1>0){
//                    lll_new1[iii][1]=0.5*(lll_new1[iii+1][1]+lll_new1[iii-1][1])-1;//插入首点至第二点间的点的高程
//                }else{
//                    System.out.println("地形高程可能出现了负值");
//                }
//
//                break;
//            }
//        }
//
//        if(inum%2==1) inum++;
//
//        double[][] ll = new double[(inum) / 2+1][4];
//        double[][] zz = new double[(inum) / 2+1][4];
//        double[][] lz = new double[(inum) / 2+1][8];
//        ll[1][1] = lll_new1[0][0];
//        zz[1][1] = lll_new1[0][1];
//        for (int kk = 0; kk < inum; kk++) {
//            ll[num2 + 1][2] = lll_new1[kk + 1][0];
//            ll[num2 + 1][3] = lll_new1[kk + 2][0];
//            zz[num2 + 1][2] = lll_new1[kk + 1][1];
//            zz[num2 + 1][3] = lll_new1[kk + 2][1];
//            if (num2 + 2 < (inum / 2)+1) {
//                ll[num2 + 2][1] = lll_new1[kk + 2][0];
//                zz[num2 + 2][1] = lll_new1[kk + 2][1];
//            }
//            kk++;
//            num2++;
//        }
//        //System.out.println("里程数组：");
//        for (int i = 0; i < ll.length; i++) {
//            for (int jj = 0; jj < 4; jj++) {
//                //System.out.print(ll[i][jj]+"\t");
//                lz[i][jj] = ll[i][jj] * 1000;
//            }
//        }
//        for (int i = 0; i < ll.length; i++) {
//            for (int jj = 0; jj < 4; jj++) {
//                //System.out.print(zz[i][jj]+"\t");
//                lz[i][jj + 4] = zz[i][jj];
//            }
//        }
//        return lz;
//    }

    public static double[][] Graphic() {


        ExcelData sheet1 = new ExcelData("config/dxsj.xlsx", "Sheet1");

        //创建储存里程高程数据的数组
        double[][] lll = new double[sheet1.sheet.getPhysicalNumberOfRows()][2];
        double[] l = new double[sheet1.sheet.getPhysicalNumberOfRows()];
        double[] z = new double[sheet1.sheet.getPhysicalNumberOfRows()];

        //读取Excel数据到数组
        sheet1.saveExcelData(lll);

        for (int i = 0; i < lll.length; i++) {
            l[i] = lll[i][0];
            z[i] = lll[i][1];
            //System.out.println("l="+l[i]);
            //System.out.println("z="+z[i]);
        }


        //地形数据处理，按1km一段输出
//        if (l[l.length - 1]%1==0 && l[0]%1==0) {
//            l[l.length - 1]=l[l.length - 1]-1;
//        }
        double[] l_st = new double[((int)l[l.length - 1] - (int)l[0])+2];   //*****当原始地形首末点为整数时，l_st倒数第二个数空值
        double[] z_st = new double[((int)l[l.length - 1] - (int)l[0])+2];   //*****当原始地形首末点为整数时，z_st倒数第二个数空值
        double[] j_st = new double[((int)l[l.length - 1] - (int)l[0])+2];   //坡度，当前点与前一点的
        double[] jj_st = new double[((int)l[l.length - 1] - (int)l[0])+2];   //坡度差
        double[][] lll_st = new double[((int)l[l.length - 1] - (int)l[0])+2][2];   //*****当原始地形首末点为整数时，z_st倒数第二个数空值
        double[][] lll_new = new double[((int)l[l.length - 1] - (int)l[0])*20][4];   //*****初次简化，剔除千分之五坡度的段
        double[][] lll_new1 = new double[((int)l[l.length - 1] - (int)l[0])+5][4];   //*****二次简化
//        if (l[l.length - 1]%1==0 && l[0]%1==0) {
//            l[l.length - 1]=l[l.length - 1]+1;
//        }
        l_st[0] = l[0];
        z_st[0] = z[0];
        if ((int) l[l.length - 1]-l[l.length - 1]!=0.0){      //末点不是整数
            l_st[(int) (l[l.length - 1] - l[0]) +1] = l[l.length - 1];
            z_st[(int) (l[l.length - 1] - l[0]) +1] = z[z.length - 1];
        }else{
            l_st[(int) (l[l.length - 1] - l[0])] = l[l.length - 1];
            z_st[(int) (l[l.length - 1] - l[0])] = z[z.length - 1];
        }


        //}
        if (l[l.length - 1] - l[0] > 0) {
            for (int i = (int) l[0] + 1; i < l[l.length - 1] - l[0]; i++) {
                l_st[i] = i;
            }
        }
        int k = 1, jjj, j = 1;//l和l_st的首末值相同，中间值不同，因此计数和遍历从1开始
        double zzz = 0;

        //原始地形高程点密集的处理，1km取平均
        for (int i = 1; i < l.length; i++) {
            if (l[i] > l_st[k] && l[i] - l_st[k] <= 1) {      //判断1km终点位置，且不需要插值时的计算
                for (jjj = j; jjj < i; jjj++) {
                    zzz = z[jjj] + zzz;
                }
                z_st[k] = zzz / (i - j);  //求1km内高程的平均值，如，里程1km处高程平均值为0-1内的高程的平均
                zzz = 0;
                k++;
                j = i;                //记录下1km的起点编号

            }
            if (l[i] > l_st[k] && l[i] - l_st[k] > 1) {      //判断1km终点位置，且需要插值时的计算
                int uuu = (int) (l[i] - l_st[k]);      //计算插值长度
                for (int u = 1; u <= uuu; u++)            //插值//////////////
                {
                    z_st[k] = z_st[k - 1] + (z[i] - z[i - 1]) / (l[i] - l[i - 1]);
                    k++;
                }

                //给空点赋值
                if(z_st[k]==0){
                    z_st[k] = z_st[k - 1] + (z[i] - z[i - 1]) / (l[i] - l[i - 1]);
                }
            }



        }


        for (int kkk = 0; kkk < z_st.length; kkk++) {
            //System.out.println(l_st[kkk]);
            //System.out.println(z_st[kkk]);
            lll_st[kkk][0] = l_st[kkk];
            lll_st[kkk][1] = z_st[kkk];

        }
        for (int kkk = 1; kkk < z_st.length; kkk++) {//计算坡度
            j_st[kkk] = (z_st[kkk] - z_st[kkk - 1]) / (l_st[kkk] - l_st[kkk - 1]);
        }
        for (int kkk = 1; kkk < z_st.length - 1; kkk++) {//计算坡度差
            jj_st[kkk] = j_st[kkk] - j_st[kkk + 1];
        }
        jj_st[l_st.length - 1] = j_st[l_st.length - 1];

        lll_new[0][0] = l_st[0];
        lll_new[0][1] = z_st[0];
        int num = 1;
        for (int kkk = 1; kkk < z_st.length; kkk++) {//初次简化，剔除千分之五坡度的段
//            if (z_st[kkk]<0){
//                z_st[kkk]=z_st[kkk-1];
//            }
            if ((j_st[kkk] > 5 || j_st[kkk] < -5)) {
                lll_new[num][0] = l_st[kkk];//里程
                lll_new[num][1] = z_st[kkk];//高程
                num++;
            }else if ( kkk%5==0 || kkk==z_st.length-1 ){
                lll_new[num][0] = l_st[kkk];//里程
                lll_new[num][1] = z_st[kkk];//高程
                num++;
            }


        }
        for (int kkk = 1; kkk < lll_new.length; kkk++) {//求新简化的地形的坡度
            if (lll_new[kkk][0] != 0 && lll_new[kkk][1] != 0) {
                lll_new[kkk][2] = (lll_new[kkk][1] - lll_new[kkk - 1][1]) / (lll_new[kkk][0] - lll_new[kkk - 1][0]);
            }
        }

        lll_new1[0][0] = lll_new[0][0];//首点里程保留
        lll_new1[0][1] = lll_new[0][1];//首点高程保留
        int num1 = 1, num2 = 0, kkk = 1;
        do {
            if (lll_new[kkk][2] * lll_new[kkk - 1][2] < 0) {//存在转折处
                lll_new1[num1][0] = lll_new[kkk - 1][0];//里程保留
                lll_new1[num1][1] = lll_new[kkk - 1][1];//高程保留
                lll_new1[num1 + 1][0] = lll_new[kkk][0];//里程保留
                lll_new1[num1 + 1][1] = lll_new[kkk][1];//高程保留
                num1 = num1 + 1;
            }
            kkk++;
            if (lll_new[kkk][2]==0 && lll_new[kkk - 1][2] != 0){
                lll_new1[num1][0] = lll_new[kkk-1][0];//里程保留
                lll_new1[num1][1] = lll_new[kkk-1][1];//高程保留
            }
        } while (lll_new[kkk][0] != 0 && lll_new[kkk][1] != 0);
        //lll_new1[num1 + 1][0] = lll_new[kkk-1][0];//末点里程保留
        //lll_new1[num1 + 1][1] = lll_new[kkk-1][1];//末点高程保留

        double [][]lj=new double[lll_new1.length][lll_new1[0].length];
        for (int ui=0;ui<lll_new1.length;ui++){
            for (int uii=0;uii<lll_new1[0].length;uii++){
                lj[ui][uii]=lll_new1[ui][uii];
            }
        }

        for (int iii = 1; iii < lll_new1.length - 2; iii++) {
            if (lj[iii-1][0] != 0 && lj[iii][0] == 0)
            {
                break;
            }
            lll_new1[iii+1][0] = lj[iii][0];//从第二点起，各自向后移动一位，里程
            lll_new1[iii+1][1] = lj[iii][1];//从第二点起，各自向后移动一位，高程
        }
        lll_new1[1][0] = 0.5*(lll_new1[0][0]+lll_new1[2][0]);
        if (0.5*(lll_new1[0][1]+lll_new1[2][1])-20>0){
            lll_new1[1][1] = 0.5*(lll_new1[0][1]+lll_new1[2][1])-20;//插入首点至第二点间的点的高程
        }else if(0.5*(lll_new1[0][1]+lll_new1[2][1])-5>0){
            lll_new1[1][1] = 0.5*(lll_new1[0][1]+lll_new1[2][1])-5;//插入首点至第二点间的点的高程,防止地形高程变为负值
        }else if(0.5*(lll_new1[0][1]+lll_new1[2][1])-1>0){
            lll_new1[1][1] = 0.5*(lll_new1[0][1]+lll_new1[2][1])-1;//插入首点至第二点间的点的高程
        }


//        for (int ui=0;ui<lll_new1.length;ui++){
//            for (int uii=0;uii<lll_new1[0].length;uii++){
//                System.out.print(lll_new1[ui][uii]+"\t");
//            }
//            System.out.println();
//        }

        inum = 1;
        for (int iii = 0; iii < lll_new1.length - 1; iii++) {
            if (lll_new1[iii][0] != 0 && lll_new1[iii + 1][0] == 0)
            {
                inum = iii+1;
                //System.out.println("ggggg");
                lll_new1[iii+1][0]=lll_new1[iii][0];
                lll_new1[iii+1][1]=lll_new1[iii][1];
                lll_new1[iii][0]=0.5*(lll_new1[iii+1][0]+lll_new1[iii-1][0]);
                if (0.5*(lll_new1[iii+1][1]+lll_new1[iii-1][1])-20>0){
                    lll_new1[iii][1]=0.5*(lll_new1[iii+1][1]+lll_new1[iii-1][1])-20;
                }else if(0.5*(lll_new1[iii+1][1]+lll_new1[iii-1][1])-5>0){
                    lll_new1[iii][1]=0.5*(lll_new1[iii+1][1]+lll_new1[iii-1][1])-5;//插入首点至第二点间的点的高程,防止地形高程变为负值
                }else if(0.5*(lll_new1[iii+1][1]+lll_new1[iii-1][1])-1>0){
                    lll_new1[iii][1]=0.5*(lll_new1[iii+1][1]+lll_new1[iii-1][1])-1;//插入首点至第二点间的点的高程
                }else{
                    System.out.println("地形高程可能出现了负值");
                }

                break;
            }
        }
//        int iii=0;
//        double[][] lll_stb = new double[lll_st.length*2-1][2];   //*****当原始地形首末点为整数时，z_st倒数第二个数空值
//        for (int i = 0; i < lll_stb.length; i++) {
//            lll_stb[i][0]=lll_st[iii][0];
//            lll_stb[i][1]=lll_st[iii][1];
//            i++;
//            iii++;
//        }
//        for (int ii = 1; ii < lll_stb.length; ii++) {
//            lll_stb[ii][0] = 0.5*lll_stb[ii-1][0]+0.5*lll_stb[ii+1][0];
//            lll_stb[ii][1] = 0.5*lll_stb[ii-1][1]+0.5*lll_stb[ii+1][1];
//            ii++;
//        }




//        //导出到excel中
//        writeArrayToExcel(lll_st, "config/lll_st.xls");
////        writeArrayToExcel(lll_stb, "config/lll_stb.xls");
//        writeArrayToExcel(lll_new, "config/lll_new.xls");
//        writeArrayToExcel(lll_new1, "config/lll_new1.xls");

        if(inum%2==1) inum++;

        double[][] ll = new double[(inum) / 2+1][4];
        double[][] zz = new double[(inum) / 2+1][4];
        double[][] lz = new double[(inum) / 2+1][8];
//        double[][] ll = new double[inum+1][4];
//        double[][] zz = new double[inum+1][4];
//        double[][] lz = new double[inum+1][8];

        ll[1][1] = lll_new1[0][0];
        zz[1][1] = lll_new1[0][1];
        for (int kk = 0; kk < inum; kk++) {
            ll[num2 + 1][2] = lll_new1[kk + 1][0];
            ll[num2 + 1][3] = lll_new1[kk + 2][0];
            zz[num2 + 1][2] = lll_new1[kk + 1][1];
            zz[num2 + 1][3] = lll_new1[kk + 2][1];
            if (num2 + 2 < (inum / 2)+1) {
                ll[num2 + 2][1] = lll_new1[kk + 2][0];
                zz[num2 + 2][1] = lll_new1[kk + 2][1];
            }
            kk++;
            num2++;
        }
        //System.out.println("里程数组：");
        for (int i = 0; i < ll.length; i++) {
            for (int jj = 0; jj < 4; jj++) {
                //System.out.print(ll[i][jj]+"\t");
                lz[i][jj] = ll[i][jj] * 1000;
            }
            //System.out.println();
        }
        //System.out.println("高程数组：");
        for (int i = 0; i < ll.length; i++) {
            for (int jj = 0; jj < 4; jj++) {
                //System.out.print(zz[i][jj]+"\t");
                lz[i][jj + 4] = zz[i][jj];
            }
            //System.out.println();
        }

        return lz;
    }

    public double[][] Graphic(File file, String sheetName) {
        ExcelData sheet1 = new ExcelData(file, sheetName);
        //创建储存里程高程数据的数组
        double[][] lll = new double[sheet1.sheet.getPhysicalNumberOfRows()][2];
        double[] l = new double[sheet1.sheet.getPhysicalNumberOfRows()];
        double[] z = new double[sheet1.sheet.getPhysicalNumberOfRows()];

        //读取Excel数据到数组
        sheet1.saveExcelData(lll);

        for (int i = 0; i < lll.length; i++) {
            l[i] = lll[i][0];
            z[i] = lll[i][1];
        }

        double[] l_st = new double[((int) l[l.length - 1] - (int) l[0]) + 2];   //*****当原始地形首末点为整数时，l_st倒数第二个数空值
        double[] z_st = new double[((int) l[l.length - 1] - (int) l[0]) + 2];   //*****当原始地形首末点为整数时，z_st倒数第二个数空值
        double[] j_st = new double[((int) l[l.length - 1] - (int) l[0]) + 2];   //坡度，当前点与前一点的
        double[] jj_st = new double[((int) l[l.length - 1] - (int) l[0]) + 2];   //坡度差
        double[][] lll_st = new double[((int) l[l.length - 1] - (int) l[0]) + 2][2];   //*****当原始地形首末点为整数时，z_st倒数第二个数空值
        double[][] lll_new = new double[((int) l[l.length - 1] - (int) l[0]) * 20][4];   //*****初次简化，剔除千分之五坡度的段
        double[][] lll_new1 = new double[((int) l[l.length - 1] - (int) l[0]) + 5][4];   //*****二次简化

        l_st[0] = l[0];
        z_st[0] = z[0];
        if ((int) l[l.length - 1] - l[l.length - 1] != 0.0) {      //末点不是整数
            l_st[(int) (l[l.length - 1] - l[0]) + 1] = l[l.length - 1];
            z_st[(int) (l[l.length - 1] - l[0]) + 1] = z[z.length - 1];
        } else {
            l_st[(int) (l[l.length - 1] - l[0])] = l[l.length - 1];
            z_st[(int) (l[l.length - 1] - l[0])] = z[z.length - 1];
        }

        if (l[l.length - 1] - l[0] > 0) {
            for (int i = (int) l[0] + 1; i < l[l.length - 1] - l[0]; i++) {
                l_st[i] = i;
            }
        }
        int k = 1, jjj, j = 1;//l和l_st的首末值相同，中间值不同，因此计数和遍历从1开始
        double zzz = 0;

        //原始地形高程点密集的处理，1km取平均
        for (int i = 1; i < l.length; i++) {
            if (l[i] > l_st[k] && l[i] - l_st[k] <= 1) {      //判断1km终点位置，且不需要插值时的计算
                for (jjj = j; jjj < i; jjj++) {
                    zzz = z[jjj] + zzz;
                }
                z_st[k] = zzz / (i - j);  //求1km内高程的平均值，如，里程1km处高程平均值为0-1内的高程的平均
                zzz = 0;
                k++;
                j = i;                //记录下1km的起点编号

            }
            if (l[i] > l_st[k] && l[i] - l_st[k] > 1) {      //判断1km终点位置，且需要插值时的计算
                int uuu = (int) (l[i] - l_st[k]);      //计算插值长度
                for (int u = 1; u <= uuu; u++)            //插值//////////////
                {
                    z_st[k] = z_st[k - 1] + (z[i] - z[i - 1]) / (l[i] - l[i - 1]);
                    k++;
                }
                //给空点赋值
                if (z_st[k] == 0) {
                    z_st[k] = z_st[k - 1] + (z[i] - z[i - 1]) / (l[i] - l[i - 1]);
                }
            }
        }


        for (int kkk = 0; kkk < z_st.length; kkk++) {
            lll_st[kkk][0] = l_st[kkk];
            lll_st[kkk][1] = z_st[kkk];
        }
        double[][] data = new double[lll_st.length][2];
        for (int i = 0; i < lll_st.length; i++) {
            data[i][0] = lll_st[i][0];
            System.out.print("data[i][0] = " + data[i][0] + " ");
            data[i][1] = lll_st[i][1];
            System.out.print("data[i][1] = " + data[i][1]);
            System.out.println();
        }
        /**
         * 存储地形数据
         */
        setTerrainData(data);

        for (int kkk = 1; kkk < z_st.length; kkk++) {//计算坡度
            j_st[kkk] = (z_st[kkk] - z_st[kkk - 1]) / (l_st[kkk] - l_st[kkk - 1]);
        }
        for (int kkk = 1; kkk < z_st.length - 1; kkk++) {//计算坡度差
            jj_st[kkk] = j_st[kkk] - j_st[kkk + 1];
        }
        jj_st[l_st.length - 1] = j_st[l_st.length - 1];

        lll_new[0][0] = l_st[0];
        lll_new[0][1] = z_st[0];
        int num = 1;
        for (int kkk = 1; kkk < z_st.length; kkk++) {//初次简化，剔除千分之五坡度的段
            if ((j_st[kkk] > 5 || j_st[kkk] < -5)) {
                lll_new[num][0] = l_st[kkk];//里程
                lll_new[num][1] = z_st[kkk];//高程
                num++;
            } else if (kkk % 5 == 0 || kkk == z_st.length - 1) {
                lll_new[num][0] = l_st[kkk];//里程
                lll_new[num][1] = z_st[kkk];//高程
                num++;
            }
        }
        for (int kkk = 1; kkk < lll_new.length; kkk++) {//求新简化的地形的坡度
            if (lll_new[kkk][0] != 0 && lll_new[kkk][1] != 0) {
                lll_new[kkk][2] = (lll_new[kkk][1] - lll_new[kkk - 1][1]) / (lll_new[kkk][0] - lll_new[kkk - 1][0]);
            }
        }

        lll_new1[0][0] = lll_new[0][0];//首点里程保留
        lll_new1[0][1] = lll_new[0][1];//首点高程保留
        int num1 = 1, num2 = 0, kkk = 1;
        do {
            if (lll_new[kkk][2] * lll_new[kkk - 1][2] < 0) {//存在转折处
                lll_new1[num1][0] = lll_new[kkk - 1][0];//里程保留
                lll_new1[num1][1] = lll_new[kkk - 1][1];//高程保留
                lll_new1[num1 + 1][0] = lll_new[kkk][0];//里程保留
                lll_new1[num1 + 1][1] = lll_new[kkk][1];//高程保留
                num1 = num1 + 1;
            }
            kkk++;
            if (lll_new[kkk][2] == 0 && lll_new[kkk - 1][2] != 0) {
                lll_new1[num1][0] = lll_new[kkk - 1][0];//里程保留
                lll_new1[num1][1] = lll_new[kkk - 1][1];//高程保留
            }
        } while (lll_new[kkk][0] != 0 && lll_new[kkk][1] != 0);

        double[][] lj = new double[lll_new1.length][lll_new1[0].length];
        for (int ui = 0; ui < lll_new1.length; ui++) {
            for (int uii = 0; uii < lll_new1[0].length; uii++) {
                lj[ui][uii] = lll_new1[ui][uii];
            }
        }

        for (int iii = 1; iii < lll_new1.length - 2; iii++) {
            if (lj[iii - 1][0] != 0 && lj[iii][0] == 0) {
                break;
            }
            lll_new1[iii + 1][0] = lj[iii][0];//从第二点起，各自向后移动一位，里程
            lll_new1[iii + 1][1] = lj[iii][1];//从第二点起，各自向后移动一位，高程
        }
        lll_new1[1][0] = 0.5 * (lll_new1[0][0] + lll_new1[2][0]);
        if (0.5 * (lll_new1[0][1] + lll_new1[2][1]) - 20 > 0) {
            lll_new1[1][1] = 0.5 * (lll_new1[0][1] + lll_new1[2][1]) - 20;//插入首点至第二点间的点的高程
        } else if (0.5 * (lll_new1[0][1] + lll_new1[2][1]) - 5 > 0) {
            lll_new1[1][1] = 0.5 * (lll_new1[0][1] + lll_new1[2][1]) - 5;//插入首点至第二点间的点的高程,防止地形高程变为负值
        } else if (0.5 * (lll_new1[0][1] + lll_new1[2][1]) - 1 > 0) {
            lll_new1[1][1] = 0.5 * (lll_new1[0][1] + lll_new1[2][1]) - 1;//插入首点至第二点间的点的高程
        }

        inum = 1;
        for (int iii = 0; iii < lll_new1.length - 1; iii++) {
            if (lll_new1[iii][0] != 0 && lll_new1[iii + 1][0] == 0) {
                inum = iii + 1;
                //System.out.println("ggggg");
                lll_new1[iii + 1][0] = lll_new1[iii][0];
                lll_new1[iii + 1][1] = lll_new1[iii][1];
                lll_new1[iii][0] = 0.5 * (lll_new1[iii + 1][0] + lll_new1[iii - 1][0]);
                if (0.5 * (lll_new1[iii + 1][1] + lll_new1[iii - 1][1]) - 20 > 0) {
                    lll_new1[iii][1] = 0.5 * (lll_new1[iii + 1][1] + lll_new1[iii - 1][1]) - 20;
                } else if (0.5 * (lll_new1[iii + 1][1] + lll_new1[iii - 1][1]) - 5 > 0) {
                    lll_new1[iii][1] = 0.5 * (lll_new1[iii + 1][1] + lll_new1[iii - 1][1]) - 5;//插入首点至第二点间的点的高程,防止地形高程变为负值
                } else if (0.5 * (lll_new1[iii + 1][1] + lll_new1[iii - 1][1]) - 1 > 0) {
                    lll_new1[iii][1] = 0.5 * (lll_new1[iii + 1][1] + lll_new1[iii - 1][1]) - 1;//插入首点至第二点间的点的高程
                } else {
                    System.out.println("地形高程可能出现了负值");
                }

                break;
            }
        }

        if (inum % 2 == 1) inum++;

        double[][] ll = new double[(inum) / 2 + 1][4];
        double[][] zz = new double[(inum) / 2 + 1][4];
        double[][] lz = new double[(inum) / 2 + 1][8];

        ll[1][1] = lll_new1[0][0];
        zz[1][1] = lll_new1[0][1];
        for (int kk = 0; kk < inum; kk++) {
            ll[num2 + 1][2] = lll_new1[kk + 1][0];
            ll[num2 + 1][3] = lll_new1[kk + 2][0];
            zz[num2 + 1][2] = lll_new1[kk + 1][1];
            zz[num2 + 1][3] = lll_new1[kk + 2][1];
            if (num2 + 2 < (inum / 2) + 1) {
                ll[num2 + 2][1] = lll_new1[kk + 2][0];
                zz[num2 + 2][1] = lll_new1[kk + 2][1];
            }
            kk++;
            num2++;
        }
        //System.out.println("里程数组：");
        for (int i = 0; i < ll.length; i++) {
            for (int jj = 0; jj < 4; jj++) {
                //System.out.print(ll[i][jj]+"\t");
                lz[i][jj] = ll[i][jj] * 1000;
            }
            //System.out.println();
        }
        //System.out.println("高程数组：");
        for (int i = 0; i < ll.length; i++) {
            for (int jj = 0; jj < 4; jj++) {
                //System.out.print(zz[i][jj]+"\t");
                lz[i][jj + 4] = zz[i][jj];
            }
            //System.out.println();
        }

        return lz;
    }
}