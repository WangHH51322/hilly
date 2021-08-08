package cn.edu.cup.hilly.calculate.hilly.large;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Output {

    private static final String OUT_PUT_PATH = "C:\\Users\\WangHH\\Desktop\\TempFile\\DaLuoCha\\output\\011";
    private static final String OUT_PUT_PATH_PLUS = "C:\\Users\\WangHH\\Desktop\\TempFile\\DaLuoCha\\output\\011\\";

    public  void OutToTXT(double[][] arr,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        File file1 = new File(OUT_PUT_PATH_PLUS + fileName +".txt"); //lg
        FileWriter out1 = new FileWriter(file1);        //文件写入流
        for(int zz=0;zz<arr.length;zz++) {
            if (zz == 0){
                out1.write("时刻\t");
            }else{
                out1.write("第"+ zz + "个U型管段\t");
            }
        }
        out1.write("\n");
        for(int yy=1;yy<arr[0].length;yy++){
            out1.write( "第"+ yy + "步\t");
            for(int zz=1;zz<arr.length;zz++) {
                out1.write(arr[zz][yy] + "\t");
            }
            out1.write("\n");
        }
        out1.close();
    }
    public static void OutToTXT(double[] arr,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file1 = new File(OUT_PUT_PATH_PLUS + fileName +".txt");
        FileWriter out1 = new FileWriter(file1);        //文件写入流
        for(int yy=0;yy<arr.length;yy++){
            //out1.write(yy + "\t");
            //out1.write(yy/60.0 + "\t");
            out1.write(yy/120.0 + "\t");
            out1.write(arr[yy] + "\t");
            out1.write("\n");
            yy=yy+11;
        }
        out1.close();
    }
    public static void OutToTXTSimple(double[] arr,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file1 = new File(OUT_PUT_PATH_PLUS + fileName +".txt");
        FileWriter out1 = new FileWriter(file1);        //文件写入流
        for(int yy=0;yy<arr.length;yy++){
            //out1.write(yy + "\t");
            //out1.write(yy/60.0 + "\t");
            out1.write(arr[yy] + "\t");
            out1.write("\n");
        }
        out1.close();
    }

    public static void OutToTXTQ(List list,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file1 = new File(OUT_PUT_PATH_PLUS + fileName +".txt");
        FileWriter out1 = new FileWriter(file1);        //文件写入流
        for(int yy=0;yy<list.size();yy++){
            //out1.write(yy + "\t");
            //out1.write(yy/60.0 + "\t");
            out1.write(yy/24.0 + "\t");
            out1.write(list.get(yy) + "\t");
            out1.write("\n");
//            yy=yy+11;
        }
        out1.close();
    }

    public static void OutToTXTLLL(double[] arr,String fileName,double Tpig) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file1 = new File(OUT_PUT_PATH_PLUS + fileName +".txt");
        FileWriter out1 = new FileWriter(file1);        //文件写入流
        for(int yy=0;yy<arr.length;yy++){
            //out1.write(yy + "\t");
            //out1.write(((Tpig+yy)/12.0) + "\t");
            out1.write(((Tpig+yy)/24.0) + "\t");
            out1.write(arr[yy] + "\t");
            out1.write("\n");
        }
        out1.close();
    }

    public static void OutToTXTPPP(double[] arr,String fileName,double L) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file1 = new File(OUT_PUT_PATH_PLUS + fileName +".txt");
        FileWriter out1 = new FileWriter(file1);        //文件写入流
        for(int yy=0;yy<arr.length;yy++){
            L=L+500;
            //out1.write(yy + "\t");
            out1.write(L/1000.0 + "\t");
            out1.write(arr[yy]/1000000.0 + "\t");
            out1.write("\n");
        }
        out1.close();
    }
    public static void OutToTXTZZZ(double[] arr,String fileName,double L) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file1 = new File(OUT_PUT_PATH_PLUS + fileName +".txt");
        FileWriter out1 = new FileWriter(file1);        //文件写入流
        for(int yy=0;yy<arr.length;yy++){
            L=L+500;
            //out1.write(yy + "\t");
            out1.write(L/1000.0 + "\t");
            out1.write(arr[yy] + "\t");
            out1.write("\n");
        }
        out1.close();
    }
    public static void OutToTXT1(double[][] arr,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file2 = new File(OUT_PUT_PATH_PLUS + fileName +".txt"); //lg
        FileWriter out1 = new FileWriter(file2);        //文件写入流
        for(int zz=0;zz<arr.length;zz++) {
            if (zz == 0){
                out1.write("时刻\t");
            }else{
                out1.write("第"+ zz + "个U型管段\t");
            }
        }
        out1.write("\n");
        for(int yy=0;yy<arr[0].length;yy++){    //////////////////////////////与原始不同         0124
            out1.write(  yy/120.0 + "\t");
            //out1.write(  yy/60.0 + "\t");
            for(int zz=1;zz<arr.length;zz++) {
                out1.write(arr[zz][yy] + "\t");
            }
            yy=yy+11;
            out1.write("\n");
        }
        out1.close();
    }

    /**
     * 将第几个U型管段，替换为起始里程,数据转化为国际标准单位km→m
     *
     */
    public static void OutToTXT000(double[][] arr,double[][] arrL,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file2 = new File(OUT_PUT_PATH_PLUS + fileName +".txt"); //lg
        FileWriter out1 = new FileWriter(file2);        //文件写入流
        for(int zz=0;zz<arrL.length;zz++) {
            if (zz == 0){
                out1.write("时刻\t");
            }else{
                out1.write(arrL[zz][1] + "\t");
            }
        }
        out1.write("\n");
        for(int yy=0;yy<arr[0].length;yy++){    //////////////////////////////与原始不同         0124
            out1.write(  yy/120.0 + "\t");
            //out1.write(  yy/60.0 + "\t");
            for(int zz=1;zz<arr.length;zz++) {
                out1.write(arr[zz][yy]*1000 + "\t");
            }
            yy=yy+11;
            out1.write("\n");
        }
        out1.close();
    }
    public static void OutToTXT123(double[][] arr,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file2 = new File(OUT_PUT_PATH_PLUS + fileName +".txt"); //lg
        FileWriter out1 = new FileWriter(file2);        //文件写入流
        for(int zz=0;zz<arr.length;zz++) {
            if (zz == 0){
                out1.write("时刻\t");
            }else if (zz == 1){
                out1.write("总排气量\t");
            }else{
                out1.write("第"+ zz + "个排气点\t");
            }
        }
        out1.write("\n");
        for(int yy=0;yy<arr[0].length;yy++){    //////////////////////////////与原始不同         0124
            //out1.write(  yy+ "\t");
            out1.write(  yy/24.0+ "\t");/////////*10或*5
//            out1.write(  yy*10/1.66914+ "\t");/////////*10或*5
            for(int zz=0;zz<arr.length;zz++) {
                out1.write(arr[zz][yy] + "\t");
            }
            out1.write("\n");
        }
        out1.close();
    }
    public static void OutToTXT2(double[][] arr,double[][] arr1,double[][] arr2,double[][] arr3,double[][] arr4,double[][] arr5,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file2 = new File(OUT_PUT_PATH_PLUS + fileName +".txt"); //lg
        FileWriter out1 = new FileWriter(file2);        //文件写入流

        out1.write("时刻,s\t");
        out1.write("Pg\tlg\th1\th2\tdm\tPb\t");

        out1.write("\n");
        for(int yy=1;yy<10001;yy++){
            out1.write( yy*0.1+"\t");
            out1.write(arr[1][yy] + "\t");
            out1.write(arr1[1][yy] + "\t");
            out1.write(arr2[1][yy] + "\t");
            out1.write(arr3[1][yy] + "\t");
            out1.write(arr4[1][yy] + "\t");
            out1.write(arr5[1][yy] + "\t");
            yy=yy+99;
            out1.write("\n");
        }
        out1.close();
    }
    public static void OutToTXT11111(double[][] arr,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file2 = new File(OUT_PUT_PATH_PLUS + fileName +".txt"); //lg
        FileWriter out1 = new FileWriter(file2);        //文件写入流
        for(int zz=0;zz<arr.length;zz++) {
            if (zz == 0){
                out1.write("时刻\t");
            }else{
                out1.write("第"+ zz + "个U型管段\t");
            }
        }
        out1.write("\n");
        for(int yy=0;yy<arr[0].length;yy++){
            out1.write(  yy/360.0 + "\t");
            for(int zz=1;zz<arr.length;zz++) {
                out1.write(arr[zz][yy] + "\t");
            }
            out1.write("\n");
        }
        out1.close();
    }
    public static void OutToTXTL(double[][] arr,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file2 = new File(OUT_PUT_PATH_PLUS + fileName +".txt"); //lg
        FileWriter out1 = new FileWriter(file2);        //文件写入流

        for(int yy=0;yy< arr.length;yy++){
            //out1.write(  yy*5.0/60.0 + "\t");
            out1.write(  yy*5.0/120.0 + "\t");
            for(int zz=1;zz<arr[0].length;zz++) {
                if (yy!=0){
                    out1.write(arr[yy][zz]/1000000 + "\t");
                }else {
                    out1.write(arr[yy][zz] + "\t");
                }
            }
            out1.write("\n");
            //if (arr[yy][5]==0&&arr[yy-1][5]!=0) break;
        }
        out1.close();
    }

    public static void OutToTXTDHL(double[][] arr,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file2 = new File(OUT_PUT_PATH_PLUS + fileName +".txt"); //lg
        FileWriter out1 = new FileWriter(file2);        //文件写入流

        for(int yy=0;yy< arr.length;yy++){
            //out1.write(  yy*5.0/60.0 + "\t");
            out1.write(  yy*5.0/120.0 + "\t");
            for(int zz=1;zz<arr[0].length;zz++) {
                if (yy!=0){
                    out1.write(arr[yy][zz] + "\t");
                }else {
                    out1.write(arr[yy][zz] + "\t");
                }
            }
            out1.write("\n");
            //if (arr[yy][5]==0&&arr[yy-1][5]!=0) break;
        }
        out1.close();
    }
    public static void OutToTXTLbz(List<List<Double>> arrayList, String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在

            file.mkdir();//创建文件夹
        }

        File file2 = new File(OUT_PUT_PATH_PLUS + fileName +".txt"); //lg
        FileWriter out1 = new FileWriter(file2);        //文件写入流

        for(int yy=0;yy< arrayList.size();yy++){

            //out1.write(  yy*5.0/120.0 + "\t");
            for(int zz=1;zz< arrayList.get(yy).size();zz++) {
                out1.write(arrayList.get(yy).get(zz) + "\t");
            }
            out1.write("\n");
            //if (arr[yy][5]==0&&arr[yy-1][5]!=0) break;
        }
        out1.close();
    }
    public static void OutToTXTFP(double[][] arr,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file2 = new File(OUT_PUT_PATH_PLUS + fileName +".txt"); //lg
        FileWriter out1 = new FileWriter(file2);        //文件写入流

        for(int yy=0;yy< arr.length;yy++){
            out1.write(  yy*5.0/120.0 + "\t");
            //out1.write(  yy*5.0/60.0 + "\t");
            for(int zz=1;zz<arr[0].length;zz++) {
                out1.write(arr[yy][zz] + "\t");
            }
            out1.write("\n");
            //if (arr[yy][5]==0&&arr[yy-1][5]!=0) break;
        }
        out1.close();
    }





}