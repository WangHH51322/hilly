package cn.edu.cup.hilly.calculate.hilly.large;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputOld {

    private static final String OUT_PUT_PATH = "C:\\Users\\WangHH\\Desktop\\TempFile\\DaLuoCha\\output\\010";

    public static void OutToTXT000(double[][] arr,double[][] arrL,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file2 = new File(OUT_PUT_PATH + "\\"+ fileName +".txt"); //lg
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

    public static void OutToTXT(double[] arr,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file1 = new File(OUT_PUT_PATH+ fileName +".txt");
        FileWriter out1 = new FileWriter(file1);        //文件写入流
        for(int yy=0;yy<arr.length;yy++){
            //out1.write(yy + "\t");
            out1.write(yy/120.0 + "\t");
            out1.write(arr[yy] + "\t");
            out1.write("\n");
            yy=yy+11;
        }
        out1.close();
    }

    public static void OutToTXTLLL(double[] arr,String fileName,double Tpig) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file1 = new File(OUT_PUT_PATH +"\\"+ fileName +".txt");
        FileWriter out1 = new FileWriter(file1);        //文件写入流
        for(int yy=0;yy<arr.length;yy++){
            //out1.write(yy + "\t");
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

        File file1 = new File(OUT_PUT_PATH + "\\"+ fileName +".txt");
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

        File file1 = new File(OUT_PUT_PATH+"\\"+ fileName +".txt");
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

        File file2 = new File(OUT_PUT_PATH+"\\"+ fileName +".txt"); //lg
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
            for(int zz=1;zz<arr.length;zz++) {
                out1.write(arr[zz][yy] + "\t");
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

        File file2 = new File(OUT_PUT_PATH+"\\"+ fileName +".txt"); //lg
        FileWriter out1 = new FileWriter(file2);        //文件写入流
        for(int zz=0;zz<arr.length;zz++) {
            if (zz == 0){
                out1.write("时刻\t");
            }else{
                out1.write("第"+ zz + "个参数\t");
            }
        }
        out1.write("\n");
        for(int yy=0;yy<arr[0].length;yy++){    //////////////////////////////与原始不同         0124
            //out1.write(  yy+ "\t");
            out1.write(  yy*10/1.7+ "\t");/////////*10或*5
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

        File file2 = new File(OUT_PUT_PATH+"\\"+ fileName +".txt"); //lg
        FileWriter out1 = new FileWriter(file2);        //文件写入流

        for(int yy=0;yy< arr.length;yy++){
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

        File file2 = new File(OUT_PUT_PATH + "\\"+ fileName +".txt"); //lg
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


    public static void OutToTXTFP(double[][] arr,String fileName) throws IOException {
        File file=new File(OUT_PUT_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        File file2 = new File(OUT_PUT_PATH+"\\"+ fileName +".txt"); //lg
        FileWriter out1 = new FileWriter(file2);        //文件写入流

        for(int yy=0;yy< arr.length;yy++){
            out1.write(  yy*5.0/120.0 + "\t");
            for(int zz=1;zz<arr[0].length;zz++) {
                out1.write(arr[yy][zz] + "\t");
            }
            out1.write("\n");
            //if (arr[yy][5]==0&&arr[yy-1][5]!=0) break;
        }
        out1.close();
    }





}