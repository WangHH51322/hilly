package cn.edu.cup.hilly.calculate.hilly.large;

import Jama.Matrix;
import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;
import cn.edu.cup.hilly.dataSource.model.mongo.outPut.DPL;
import cn.edu.cup.hilly.dataSource.model.mongo.outPut.PgHis;
import cn.edu.cup.hilly.dataSource.utils.SizeChange;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static java.lang.Math.*;

@IOElement(name = "hillyProject")
public class Project extends Thread implements Serializable {

    Conpara conPara=new Conpara();

    @InputField(name = "variableParameter", unit = "")
    Varpara varPara;

    @InputField(name = "pipe", unit = "")
    Pipeline pipeLine;

    @InputField(name = "mediumList", unit = "")
    List<Oil> oils;

    @InputField(name = "pigList", unit = "")
    List<Pig> pigs;

    @InputField(name = "valveList", unit = "")
    List<Valve> valves;

    @InputField(name = "pumpList", unit = "")
    private List<Pump> pumps;

    @InputField(name = "stationList", unit = "")
    private List<Stations> stations;

    @InputField(name = "message", unit = "")
    private Messages msg;

    /**
     * dpl
     */
    private Map<Integer, double[]> dPL;
    public Map<Integer, double[]> getDPL() {
        return dPL;
    }
    public void setDPL(Map<Integer, double[]> dPL) {
        this.dPL = dPL;
    }
    /**
     * allLineFP
     */
    private Map<Integer, double[]> aLineFP;
    public Map<Integer, double[]> getALineFP() {
        return aLineFP;
    }
    public void setALineFP(Map<Integer, double[]> aLineFP) {
        this.aLineFP = aLineFP;
    }
    /**
     * lg_his
     */
    private Map<Integer, double[]> lgHis;
    public Map<Integer, double[]> getLgHis() {
        return lgHis;
    }
    public void setLgHis(Map<Integer, double[]> lgHis) {
        this.lgHis = lgHis;
    }
    /**
     * m_his
     */
    private Map<Integer, double[]> mHis;
    public Map<Integer, double[]> getmHis() {
        return mHis;
    }
    public void setmHis(Map<Integer, double[]> mHis) {
        this.mHis = mHis;
    }
    /**
     * pg_his
     */
    private Map<Integer, double[]> pgHis;
    public Map<Integer, double[]> getPgHis() {
        return pgHis;
    }
    public void setPgHis(Map<Integer, double[]> pgHis) {
        this.pgHis = pgHis;
    }

    /**
     * allLineStaticP,pig_V,pig_L
     */
    private double[][] pigV;
    private double[][] pigL;
    private double[][] aLSP;
    public double[][] getPigV() {
        return pigV;
    }
    public void setPigV(double[][] pigV) {
        this.pigV = pigV;
    }
    public double[][] getPigL() {
        return pigL;
    }
    public void setPigL(double[][] pigL) {
        this.pigL = pigL;
    }
    public double[][] getaLSP() {
        return aLSP;
    }
    public void setaLSP(double[][] aLSP) {
        this.aLSP = aLSP;
    }
    /**
     * 线程暂停
     */
    private static boolean pause = false;
    public boolean isLocked() {
        return pause;
    }
    private static boolean release = false;
    private static Object lock = new Object();

    /**
     * 调用该方法实现线程的暂停
     */
    public static void pauseThread(){
        pause = true;
        //return varPara;
    }
    /**
     * 释放此进程
     */
    public static void releaseThread(){
        pause = false;
        synchronized (lock){
            lock.notify();
        }
        release = true;
    }
    /**
     * 将release参数恢复为false
     */
    public static void recoverThread(){
        release = false;
    }
    /**
     * 调用该方法实现恢复线程的运行
     */
    public static void resumeThread(){
        pause = false;
        synchronized (lock){
            lock.notify();
        }
    }
    /**
     * 这个方法只能在run 方法中实现，不然会阻塞主线程，导致页面无响应
     */
    void onPause() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @SneakyThrows
    public void run() {
        conPara.Ql = varPara.Qh/3600.0;
        Oil oil = oils.get(0);
        int pumpStationsNum=0;
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2.0;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        double [][] lz;
        lz = ExcelData.Graphic();       //运用地形简化程序得到的三点式地形
        //varPara.i= 33;
        varPara.stationListZ.add(0);
        varPara.stationListL.add(0);
        for (int u=0;u<stations.size();u++){
            varPara.stationListZ.add(stations.get(u).getStationZ());
            varPara.stationListL.add(stations.get(u).getStationL());
        }


        varPara.i= lz.length+varPara.stationListZ.size()-1;
        varPara.setArr();       //通过get方法用i，k给varPara中的数组赋值

        double t_all,dt=0.2;
        int tt=0,rr=0,pp=0,ff=0,ttt=0,uyt=0;      //时步切换标准
        int o;   //破碎结束的段数
        varPara.num=0;
        double ssr=0,ssr1=0,ssr2=0,ssr3=0;  //累计压降
        double wL=0;  //水头尾部处理
        double []AddMg=new double[varPara.getI()];  //清管器导致的附加压降
        double [][]AddMg_his=new double[varPara.getI()][varPara.getK()];  //清管器导致的附加压降

        t_all= 0;      //总运行时间，单位是秒，从零开始
        double LLt;//压降计算里程标记
        double flagFP;//流型计算里程标记

        LLt=varPara.line_l[1][1]+500;


        int i,n=1,num0,num1=1,num2,flagpigT=0; //i为水头所在段，n为当前计算管段编号

        double Ha,Hb;

        //////////////////////0402   lz = ExcelData.Graphic();       //运用地形简化程序得到的三点式地形
        //加入固定地形点位

        for (num2 = 1; num2 < ExcelData.inum/2+1; num2++) {     //原管道地形的所有管段
            for (int b=0;b<4;b++){
                varPara.line_lll[num2][b] = lz[num2][b];
                varPara.line_ddd[num2][b] = lz[num2][b+4];
            }
        }
        for (int num=1;num<=varPara.stationListZ.size()-1;num++) {      //插入点数量    、、、、、、、、、、、、插入点限制，两点间距不小于10km
            for (num0 = num1-1; num0 < ExcelData.inum/2+1; num0++) {     //原管道地形的所有管段
                if (lz[num0][1] < (double)varPara.stationListL.get(num) && lz[num0][2] > (double)varPara.stationListL.get(num)) {//插入点在下坡段
                    num0=num0+num-1;
                    varPara.line_lll[num0][1] = lz[num0-num+1][1];
                    varPara.line_lll[num0][2] = (double)varPara.stationListL.get(num);
                    varPara.line_lll[num0][3] = 0.5*((double)varPara.stationListL.get(num) + lz[num0-num+1][2]);
                    varPara.line_lll[num0 + 1][1] = 0.5*((double)varPara.stationListL.get(num) + lz[num0-num+1][2]);
                    varPara.line_lll[num0 + 1][2] = lz[num0-num+1][2];
                    varPara.line_lll[num0 + 1][3] = lz[num0-num+1][3];
                    varPara.line_ddd[num0][1] = lz[num0-num+1][5];
                    varPara.line_ddd[num0][2] = (double)varPara.stationListZ.get(num);

                    if (varPara.line_ddd[num0][1]<varPara.line_ddd[num0][2])//插入点高于高点的处理方法，调高插入点前一点的高程
                    {
                        varPara.line_ddd[num0][1]=varPara.line_ddd[num0][2]+50;
                        varPara.line_ddd[num0-1][3]=varPara.line_ddd[num0][2]+50;
                    }

                    varPara.line_ddd[num0][3] = (double)varPara.stationListZ.get(num) + 50;

                    varPara.line_ddd[num0 + 1][1] = (double)varPara.stationListZ.get(num) + 50;
                    varPara.line_ddd[num0 + 1][2] = lz[num0-num+1][6];
                    varPara.line_ddd[num0 + 1][3] = lz[num0-num+1][7];
                    num1=num0;
                    for (num2 = num0+2; num2 < ExcelData.inum/2+1; num2++) {     //插入点后，将位于插入点后的原管道地形的所有管段位置进行更新
                        for (int b=0;b<4;b++){
                            if (num2-num<ExcelData.inum/2+1){
                                varPara.line_lll[num2][b] = lz[num2-num][b];
                                varPara.line_ddd[num2][b] = lz[num2-num][b+4];
                            }
                        }
                    }
                    break;
                } else if (lz[num0][3] > (double)varPara.stationListL.get(num) && lz[num0][2] < (double)varPara.stationListL.get(num)) {//插入点在上坡段
                    num0=num0+num-1;
                    varPara.line_lll[num0][1] = lz[num0-num+1][1];
                    varPara.line_lll[num0][2] = lz[num0-num+1][2];
                    varPara.line_lll[num0][3] = (double)varPara.stationListL.get(num);

                    varPara.line_lll[num0 + 1][1] = (double)varPara.stationListL.get(num);
                    varPara.line_lll[num0 + 1][2] = 0.5*((double)varPara.stationListL.get(num) + lz[num0-num+1][3]);
                    varPara.line_lll[num0 + 1][3] = lz[num0-num+1][3];

                    varPara.line_ddd[num0][1] = lz[num0-num+1][5];
                    varPara.line_ddd[num0][2] = lz[num0-num+1][6];
                    varPara.line_ddd[num0][3] = (double)varPara.stationListZ.get(num);

                    varPara.line_ddd[num0 + 1][1] = (double)varPara.stationListZ.get(num);
                    varPara.line_ddd[num0 + 1][2] = (double)varPara.stationListZ.get(num) - 50;
                    varPara.line_ddd[num0 + 1][3] = lz[num0-num+1][7];
                    num1=num0;
                    for (num2 = num0+2; num2 < (ExcelData.inum/2+(double)varPara.stationListZ.size()); num2++) {     //插入点后，将位于插入点后的原管道地形的所有管段位置进行更新
                        for (int b=0;b<4;b++){
                            if (num2-num<ExcelData.inum/2+1){
                                varPara.line_lll[num2][b] = lz[num2-num][b];
                                varPara.line_ddd[num2][b] = lz[num2-num][b+4];
                            }

                        }
                    }
                    break;
                } else if (lz[num0][1] == (double)varPara.stationListL.get(num) || lz[num0][2] == (double)varPara.stationListL.get(num) || lz[num0][3] == (double)varPara.stationListL.get(num)) {//插入点与原点重合
                    System.out.println("里程高程插入异常！");
                }

            }
        }
        for (int a=1;a< varPara.line_l.length;a++){          ////////////控制地形里程在395-640km  i==64  Q=1660
        //for (int a=61;a< 108;a++){          ////////////控制地形里程在397-608km   i=48  Q=0.483333
            if (varPara.line_lll[a][1]!=0 || a==1){
                System.out.print("第"+(a)+"管段"+"\t");
                for (int b=0;b<4;b++){
                    varPara.line_l[a][b]=varPara.line_lll[a][b];
                    System.out.print(varPara.line_l[a][b]+"\t");
                    varPara.line_d[a][b]=varPara.line_ddd[a][b];
                    System.out.print(varPara.line_d[a][b]+"\t");
                }
                System.out.println();
            }
        }
        System.out.println("varPara.line_d[1][1]="+varPara.line_d[1][1]);

        varPara.slopeD = calSlopeD(varPara.line_d, varPara.line_l);         //计算下坡角度
        varPara.slopeU = calSlopeU(varPara.line_d, varPara.line_l);         //计算上坡角度
        for (i = 1; i < varPara.i; i++) {

            varPara.delta[i] = calDelta(varPara.slopeD[i][0]);      //下坡段的初始液相圆心角计算，明渠流动
            varPara.Al[i] = Math.pow(r, 2) * (varPara.delta[i] - 0.5 * Math.sin(2 * varPara.delta[i]));    //液相的流动截面积
            varPara.Sl[i] = 2 * varPara.delta[i] * r;                                                //液相湿周
            varPara.vll[i] = 1.49 / conPara.n * Math.pow((varPara.Al[i] / varPara.Sl[i]), (2.0 / 3.0)) * Math.pow(varPara.slopeD[i][0], 0.5);      //液相流速计算
            varPara.t2[i] = (varPara.line_l[i][2] - varPara.line_l[i][1]) / varPara.vll[i];        //明渠流动下坡时间，也就是液相从高点到低点的时间
            varPara.gasRatio[i] = (Math.PI * Math.pow(r, 2) - varPara.Al[i]) / (Math.PI * Math.pow(r, 2));      //初始截面含气率计算

            varPara.V0[i] = Math.PI * Math.pow(r, 2) * (varPara.line_l[i][2] - varPara.line_l[i][1]) * varPara.gasRatio[i];      //初始积气段的气体体积
            varPara.M0[i] = varPara.V0[i] * 1.205;        //初始积气段的气体质量，密度取1.205kg/m3
            varPara.h1k[i] = 2 * r;               //下坡段初始液位高度---------距低点的高差
            varPara.h2k[i] = 2 * r;               //上坡段初始液位高度
            varPara.Pgk[i] = conPara.p0;                //初始气相压力，大气压
            varPara.backPressure[i] = conPara.p0;                //初始气相压力，大气压,水头所在管段处的背压
            varPara.Hgk[i] = varPara.gasRatio[i];       //初始截面含气率
            varPara.uk[i] = conPara.Ql / (2 * A);           //水头在上坡段的前进速度
            varPara.lgk[i] = varPara.line_l[i][2] - varPara.line_l[i][1]; //初始气段长度，即下坡段长度
            varPara.MGk[i] = varPara.M0[i];              //下坡段的初始气体质量
        }
        varPara.waterHeadLocation[0]=varPara.line_l[1][1];   //水头初始位置等于首个下坡段低点
        System.out.println("总模拟长度："+(varPara.line_l[varPara.i-1][3]-varPara.line_l[1][1])/1000+"km");

        cal_l0();//计算各管段的气段尾部估计位置，也计算了满管流动的水力坡降

        varPara.Time[0] = t_all;       //时步与时间的整合对应

        //1、从第一个U型管段的低点开始进行时间循环
        long start1,end1,end3;
        start1 = System.currentTimeMillis();        //开始计算的时间标记

        /**
         * 开始时间循环
         */
        /**
         * 循环开始
         */
        System.out.println("开始循环!!!");
        int kk = 0;
        int count = varPara.kt + 1;
        loop:while(true){
            while (pause){
                onPause();
            }
            while (release) {
                break loop;
            }
            try {
                kk ++;
                if (kk == count) {
                    break;
                }
               //时间循环，从第一个u型管低点开始，到水头遇终点结束。

                //计算水头位置
                conPara.Ql = varPara.Qh/3600.0;
                uyt++;
                if (uyt%180000==0){
                    System.out.println(0.2 * kk / 3600.0 + "h的流量为" +varPara.Qh);
                }
                for (i=1;i<=n;i++)      //每当水头越过高点则n加一，i为当前计算段的编号
                {
                    //破碎的计算，运行得到一个时步的破碎结果,返回修正后的Lg、Pg、Hg的值
                    if (varPara.ssr[i][0] != 1) {     //20201105加入后半个判定条件，让破碎结束后不再运行，前半个条件是预设的破碎开始情况

                        if (varPara.Pgk[i] < 0.1*oil.getDensity()*conPara.g*(varPara.line_l[i][3]-varPara.line_l[i][2])){
                            YaSuo(i, varPara.lgk[i]+varPara.lgk[i]*(varPara.dMg_in[i] - varPara.dMg_out[i]-AddMg[i])/varPara.MGk[i], varPara.Pgk[i], varPara.Hgk[i], varPara.slopeU[i][0],
                                    varPara.slopeD[i][0], varPara.uk[i], varPara.h1k[i], varPara.h2k[i], varPara.backPressure[i],
                                    varPara.dMg_in[i] - varPara.dMg_out[i]-AddMg[i]);
                            varPara.Dengk[i] = 1.205 / 1.01e5 * varPara.Pgk[i];
                            try {
                                dtPosui(kk, i, varPara.l0[i], varPara.line_l[i][1], varPara.line_d[i][1], varPara.line_l[i][2], varPara.line_d[i][2],
                                        conPara.Ql, D, 1.205,varPara.MGk[i]-AddMg[i] , varPara.Pgk[i], varPara.lgk[i],
                                        varPara.Dengk[i], varPara.Hgk[i], varPara.MM, varPara.Pgk, varPara.lgk, varPara.Dengk, varPara.Hgk,
                                        oil.density, 1e-6);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            varPara.h2k[i] = varPara.h2_his[i][(kk) / 300 - 1] + conPara.Ql / A;
                        }
                    }
                    //计算单位管长下的分层流（只有下坡有分层流）压降，带重位压差
                    varPara.Hfk_f[i] = dpf_cal(-varPara.slopeD[i][0], varPara.Hgk[i], A, oil.density, 1.205, conPara.Ql / A, varPara.f_l[i], 0.0143, varPara.Sl[i]);
                    //计算单位管长下的下坡段分层流压降，带重位压差
                    varPara.Hfk_b[i] = dPb_cal(A, -varPara.slopeD[i][0], varPara.Hgbk[i], A, oil.density, 1.205, 0.001* oil.getViscosity(), conPara.Ql / A, conPara.Ql / A, conPara.Ql / A, 0.0001);
                    varPara.Hfk_dU[i] = dPd_cal(varPara.slopeU[i][0], varPara.Hgbk[i], conPara.Ql, A, A, oil.density, conPara.Ql / A, conPara.Ql / A, 0.0001);
                    //计算单位管长下的上坡段分层流压降，带重位压差
                    varPara.Hfk_bU[i] = dPbU_cal(A, varPara.slopeU[i][0], varPara.Hgbk[i], A, oil.density, 1.205, 0.001* oil.getViscosity(), conPara.Ql / A, conPara.Ql / A, conPara.Ql / A, 0.0001);
                    //某管段在当前时刻的总压降
                    varPara.Hfk[i] = dP_cal(varPara.vll[i], varPara.vll[i], varPara.Hgbk[i], 3, A, oil.getDensity(), 1.205, varPara.Hfk_b[i], varPara.Hfk_bU[i], varPara.Hfk_f[i], varPara.lg_f[i][kk / 300], varPara.lp_b[i][kk / 300], varPara.lp_bU[i][kk / 300])[3];
                    varPara.Hfk_jj[i] = dP_cal(varPara.vll[i], varPara.vll[i], varPara.Hgbk[i], 3, A, oil.getDensity(), 1.205, varPara.Hfk_b[i], varPara.Hfk_bU[i], varPara.Hfk_f[i], varPara.lg_f[i][kk / 300], varPara.lp_b[i][kk / 300], varPara.lp_bU[i][kk / 300])[2];
                    //记录参数，背压向上游传递，气量向下游传递
                    FlowConversion(kk, i);
                    //paiqi(i,varPara.Pgk,300000,eValve,varPara.h1k,varPara.h2k,varPara.lgk,varPara.Hgk,varPara.line_d,varPara.line_l);
                    if (varPara.ssr[i][0] == 1) {
                        varPara.Pgk[i] = varPara.Pg_his[i][(kk) / 300 - 5];
    //                        varPara.lgk[i] = 0.1;                //气体长度为零
                        varPara.lgk[i] = varPara.lg_his[i][(kk) / 300 - 5];
                        varPara.h1k[i] = varPara.line_d[i][1] - varPara.line_d[i][2];

                        varPara.h2k[i] = varPara.h2_his[i][(kk) / 300 - 5] + conPara.Ql / A;
                        varPara.dMg_out[i] = 0.00;            //净流出为零
                        varPara.MGk[i] = varPara.M_his[i][(kk) / 300 - 5];                         //气体质量为零
                        //气体质量为零
                    }
                    //if (i > 1 && varPara.ssr[i][0] == 0) {
                    if (i > 1 && varPara.ssr[i][0] == 0) {
                        varPara.backPressure[i - 1] = varPara.Pgk[i];                     //开始封闭后才有这个关系，要考虑明渠流动的时间
                        varPara.dMg_in[i] = varPara.dMg_out[i - 1]+ AddMg[i-1];           //上游段的净流出等于下游段的净流入
                        ////////////////////////////////当下一段破碎结束后，质量传向下下段 ///////////////////////////////////////////////////////
                    }
                    for (o=0;o<n-i;o++){
                        if (i > 1 && varPara.ssr[i+o][0] != 0) {
                            o++;
                            varPara.dMg_in[i + o] = varPara.dMg_out[i - 1] + AddMg[i - 1];
                            o--;
                        }
                    }
                    //判定过高点后，加一个运行的管段。
                    if (varPara.h2k[n] > (varPara.line_d[n][3] - varPara.line_d[n][2])) {//水头进入下一个管段
                        for (i = 1; i <= n; i++) {
                            varPara.h2k[i] = varPara.line_d[i][3] - varPara.line_d[i][2];
                        }
                        if (pp == 0) {
                            if (n < varPara.i - 1) {
                                System.out.println("水头在第" + 0.2 * kk / 3600.0 + "h到达第" + (n) + "个U型管高点");
                                //pp++;
                                n++;
                            } else if (n == varPara.i - 1) {
                                System.out.println("水头在第" + 0.2 * kk / 3600.0 + "h到达第" + (n) + "个U型管高点");
                                //System.out.println("水头在第" + 0.2*kk/3600.0 + "h到达第"+ (n) + "个U型管高点");
                                pp++;
                            }
                        }
                    }
                    if (i == n) tt++;
                    if (tt > 299) {
                        rr++;
                        varPara.Time[rr] = varPara.Time[rr - 1] + varPara.deltaT * 300;
                        Save_his(i, n, rr);
                        //Hf[i]是第i段在某时刻的总压降，所以累加后即为
                        for (int kkk = 1; kkk < varPara.i; kkk++) {
                            //Hf[rr]=Hfk[kkk]+Hf[rr];
                            ssr = varPara.Hfk[kkk] + ssr;
                            //Hf[rr]=Hfk[kkk];
                            ssr1 = varPara.Hfk_bb[kkk] + ssr1;
                            ssr2 = varPara.Hfk_ff[kkk] + ssr2;
                            ssr3 = varPara.Hfk_jj[kkk] + ssr3;
                            if (n > 1 && varPara.ssr[kkk][0] == 1) {
                                varPara.lp_b[kkk][rr] = varPara.line_l[kkk][2] - varPara.line_l[kkk][1];              //当前段破碎结束后，下坡段全按气泡流计算
                                varPara.lp_bU[kkk][rr] = varPara.line_l[kkk][3] - varPara.line_l[kkk][2];             //当前段破碎结束后，上坡段全按气泡流计算
                                varPara.lg_f[kkk][rr] = 0;                                       //当前段破碎结束后，全按气泡流计算，分层流长度为零
                            } else if (varPara.ssr[kkk][1] == 1) {                               //水头到达高点后
                                varPara.lp_b[kkk][rr] = varPara.line_l[kkk][2] - varPara.line_l[kkk][1] - varPara.lgk[kkk];
                                varPara.lp_bU[kkk][rr] = varPara.line_l[kkk][3] - varPara.line_l[kkk][2];
                                varPara.lg_f[kkk][rr] = varPara.lgk[kkk];
                            }
                        }
                        varPara.Hf[rr] = ssr;
                        varPara.Hf_b[rr] = ssr1;
                        varPara.Hf_f[rr] = ssr2;
                        varPara.Hf_j[rr] = ssr3;
                        ssr = 0;
                        ssr1 = 0;
                        ssr2 = 0;
                        ssr3 = 0;
                        tt = 0;
                        if (varPara.waterHeadLocation[rr - 1] < varPara.line_l[n][2] && (varPara.waterHeadLocation[rr - 1] != 0.0 || rr-1==0))                       //到达某段低点前
                        {
                            varPara.waterHeadLocation[rr] = varPara.waterHeadLocation[rr - 1] + dt * varPara.vll[n] * 300;        //用明渠流流速计算水头位置
                            varPara.lg_f[n][rr] = varPara.lg_f[n][rr - 1] + dt * varPara.vll[n] * 300;                            //用明渠流流速计算分层流长度
                            varPara.lp_b[n][rr] = 0;        //水头抵达低点前，下坡段气团气泡流长度为零
                            varPara.lp_bU[n][rr] = 0;       //水头抵达低点前，上坡段气团气泡流长度为零
                        } else if (varPara.waterHeadLocation[rr - 1] > varPara.line_l[n][2] && varPara.waterHeadLocation[rr - 1] <= varPara.line_l[n][3]) {     //低点到首段翻越点之间
                            //varPara.waterHeadLocation[rr] = varPara.waterHeadLocation[rr - 1] + conPara.Ql/A*dt * 300;                      //用压缩模型的上坡段液位高度计算水头位置
                            varPara.waterHeadLocation[rr] = varPara.line_l[n][2] + varPara.h2k[n] / varPara.slopeU[n][0];                      //用压缩模型的上坡段液位高度计算水头位置
                            //wL=varPara.waterHeadLocation[rr];
                            varPara.lg_f[n][rr] = varPara.lgk[n];         //开始压缩后，等于压缩期间的实时长度
                            varPara.lp_b[n][rr] = varPara.line_l[n][2] - varPara.line_l[n][1] - varPara.lgk[n];        //下坡段气团气泡流长度等于下坡段分层流以外的长度
                            varPara.lp_bU[n][rr] = varPara.h2k[n] / varPara.slopeU[n][0];                              //上坡段水头处的长度
                        }
                        if (varPara.waterHeadLocation[rr-1] >= varPara.line_l[varPara.i-2][3]-100 &&varPara.line_l[varPara.i-2][3]!=0){
                            varPara.waterHeadLocation[rr] = varPara.line_l[varPara.i-2][3];///////////////原本是i-1
                            //System.out.println("ces");
                        }
                        varPara.allLine[1][rr]=varPara.waterHeadLocation[rr];       //水头位置
                        varPara.allLine[2][rr]=getZ(varPara.line_l, varPara.line_d,varPara.waterHeadLocation[rr]);      //水头位置所处点高程
                        //varPara.allLine[2][rr]=getFlowPattern(getI(varPara.line_l, varPara.waterHeadLocation[rr]),varPara.waterHeadLocation[rr],varPara.pigL[rr/5]);       //当前点流型1为分层流，0为满管流，2为气泡流，3为段塞或气泡流
                        varPara.allLine[3][rr]=getI(varPara.line_l, varPara.waterHeadLocation[rr]);                     //当前点流型
                    }
                    if (i == n) ff++;
                    if (ff > 1499) {
                        varPara.num++;
                        varPara.lg_fff[1][0] = 0;
                        for (int cc = 1; cc <= n; cc++) {
                            varPara.lg_fff[cc][varPara.num] = varPara.lg_f[cc][varPara.num * 5];
                        }
                        varPara.waterL[varPara.num] = varPara.waterHeadLocation[varPara.num * 5];
                        for (int sL=0;sL<stations.size();sL++){     //读取各站点位置
                                varPara.stationLLL[sL] =stations.get(sL).getStationL();
                        }
                        dpL(n, varPara.T, varPara.num, varPara.deltaX, varPara.waterL, varPara.Hfk_b, varPara.Hfk_bU, varPara.Hfk_f, varPara.lg_fff,varPara.vll,varPara.stationLLL,varPara.Hd);
                        /**
                         * 存储dpl
                         */
                        SizeChange dPLChange = new SizeChange(varPara.dPL);
                        Map<Integer, double[]> dPLAfterChange = dPLChange.ResultAfterChange();
//                        System.out.println("varPara.dPL: " + dPLAfterChange);
                        setDPL(dPLAfterChange);

                        ff = 0;
                        startPump(varPara.num, varPara.dPL[varPara.num],varPara.waterL[varPara.num]);
                        for (int cc = 1; cc <= n; cc++) {
                            if (varPara.checkP != 0 && varPara.num%10==0) {       //气段超压或者全线某点超压后，开始停泵、计算静压、高点排气
                                //停泵
                                stopPump(varPara.num, varPara.waterL[varPara.num]);
                                //静压
                                staticP(n, varPara.waterL[varPara.num], varPara.deltaX);
                                varPara.kkstop=kk;
                                varPara.numstop=varPara.num;
                                varPara.rrstop=rr;
                                System.out.println("在"+varPara.num/12.0+"h进行"+"第"+varPara.stopT+"次停输排气");
                                //varPara.Pgk[i] = 0.5*varPara.Pgk[i];
                                //varPara.lgk[i] = 0.5*varPara.lgk[i];
                                //if (varPara.Pgk[i]<101325||varPara.lgk[i]<5) varPara.ssr[i][0]=0;
                                varPara.stopT++;
                                varPara.checkP = 0;
                                //停输排气
                                for (int stationNum=0;stationNum<stations.size();stationNum++){//查询当前点所处的站点位置
                                    //if (stations.get(stationNum).getStationType()==2.0 && varPara.line_l[cc][1]+varPara.lgk[cc] >= varPara.stationLLL[stationNum] && varPara.line_l[cc][1] <= varPara.stationLLL[stationNum+1]){
                                    //if (stations.get(stationNum).getStationType()==2.0 && varPara.line_l[cc][1]+varPara.lgk[cc] >= varPara.stationLLL[stationNum]){
                                    if (stations.get(stationNum).getStationType()==2.0 && varPara.line_l[cc][2] >= varPara.stationLLL[stationNum] && varPara.line_l[cc][1] <= varPara.stationLLL[stationNum]){
                                        exitGas(cc,stationNum,varPara.M_his[cc][rr-2],1000000*varPara.Pg_his[cc][rr-2],1000*varPara.lg_his[cc][rr-2],varPara.h1_his[cc][rr-2],varPara.h2_his[cc][rr-2]);
                                    }
                                }
                            }
                        }
                        for (int stationNum00=0;stationNum00<stations.size();stationNum00++){//查询当前点所处的站点位置
                            if (false && varPara.waterL[varPara.num] > stations.get(stationNum00).getStationL()+20000 && stations.get(stationNum00).getStationType()==1.0 && varPara.pigflag==0){//水头流动至距首站后20km
                                if (flagpigT==0)
                                {
                                    flagpigT=varPara.num;
                                    System.out.println("清管器在"+flagpigT/12.0+"h," + "从"+stations.get(stationNum00).getStationL()+"m处投放");
                                }
                                varPara.pigV[0]=conPara.Ql/A;      //初始速度设为1
                                varPara.pigL[0]=stations.get(stationNum00).getStationL();  //初始位置设为250.35km
                                //double []dPLLL=varPara.dPL[varPara.num];//传递当前时步的压力值，进行插值
                                double []dPLLL=chazhi(varPara.dPL[varPara.num],499);//传递当前时步的压力值，进行插值
                                //未分上下坡，夹角不对
                                //getHb等，高程不对
                                //压力的单位！！！
                                varPara.pigNum++;
                                if ((int)(varPara.pigL[varPara.pigNum-1]-LLt)<0||(int)(varPara.pigL[varPara.pigNum-1]-LLt)>500){
                                    Ha = 1e5+20000;
                                    Hb = 1e5;
                                }else{
                                    //Ha
                                    Ha = dPLLL[(int)(varPara.pigL[varPara.pigNum-1]-LLt)-1];
                                    //Hb
                                    Hb = dPLLL[(int)(varPara.pigL[varPara.pigNum-1]-LLt)];
                                }
                                //
                                varPara.pigV[varPara.pigNum]=getPigV(Ha,Hb,varPara.pigV[varPara.pigNum-1],varPara.pigL[varPara.pigNum-1]
                                        ,varPara.pigL[varPara.pigNum-1]+500,varPara.getPgk()[i],3000,varPara.pigL[0],getPhi(varPara.line_l,varPara.line_d,varPara.pigL[varPara.pigNum-1]))[2];
                                varPara.pigL[varPara.pigNum]= varPara.pigL[varPara.pigNum-1]+0.5*(varPara.pigV[varPara.pigNum-1]+varPara.pigV[varPara.pigNum])*300;
                                varPara.pigZ[varPara.pigNum]= getZ(varPara.line_l,varPara.line_d,varPara.pigL[varPara.pigNum]);
                                for (int stationNum11=stationNum00;stationNum11<stations.size();stationNum11++){
                                    if (varPara.pigL[varPara.pigNum]>stations.get(stationNum00+1).getStationL() && stations.get(stationNum00+1).getStationType()==1.0)
                                    {
                                        varPara.pigflag=1;
                                        System.out.println("清管器在"+(varPara.pigNum+flagpigT)/12.0+"h," + "从"+stations.get(stationNum00+1).getStationL()+"m处回收");
                                        break;
                                    }
                                }
                            }
                        }
                        //清管器投放
                        for (int rrr=1;rrr<=n;rrr++){
                            if (varPara.MGk[rrr]>1.0 && varPara.pigL[varPara.pigNum]>=varPara.line_l[rrr][1] && varPara.pigL[varPara.pigNum]<varPara.line_l[rrr][2]){//当积气段气体质量剩余，且清管器位于积气段10km内时
                                //AddMg[rrr]=varPara.Dengk[rrr]*varPara.deltaT*(varPara.pigV[varPara.pigNum] - conPara.Ql/A)*varPara.Hgk[rrr]*A;       //添加清管器赶气的附加破碎质量流量
                                //AddMg[rrr]=varPara.Dengk[rrr]*varPara.deltaT*(varPara.pigV[varPara.pigNum])*varPara.Hgk[rrr]*A;       //添加清管器赶气的附加破碎质量流量
                                AddMg[rrr]=varPara.MGk[rrr]*(varPara.pigL[varPara.pigNum]-varPara.line_l[rrr][1])/(varPara.line_l[rrr][2]-varPara.line_l[rrr][1]);       //添加清管器赶气的附加破碎质量流量
                                //System.out.println("测试00001");

                            }
                            AddMg_his[rrr][varPara.num] = AddMg[rrr];
                        }
                        //全线流型计算
                        flagFP = varPara.line_l[1][1]+500;
                        for (int xNum = 1; flagFP < varPara.waterL[varPara.num]; xNum++) {
                            varPara.allLineFP[varPara.num][xNum] = getFlowPattern(getI(varPara.line_l, flagFP), flagFP, varPara.pigL[0],varPara.pigL[varPara.pigNum], flagpigT);       //当前点流型1为分层流，0为满管流，2为气泡流，3为段塞或气泡流
                            flagFP = flagFP + 500;
                        }
                        /**
                         * 存储allLineFP
                         */
                        SizeChange allLineFPChange = new SizeChange(varPara.allLineFP);
                        Map<Integer, double[]> allLineFPAfterChange = allLineFPChange.ResultAfterChange();
//                        System.out.println("varPara.allLineFP: " + allLineFPAfterChange);
                        setALineFP(allLineFPAfterChange);
                    }
                }
                if ((varPara.ssr[1][0]==1 && varPara.ssr[2][0]==1 && varPara.ssr[3][0]==1 && varPara.ssr[varPara.i-1][0]==1)){
                    System.out.println("结束!");
                    break;
                }
                ttt++;
                if (n==varPara.i-1){
                    if (ttt>180000){
                        staticP(n, varPara.waterL[varPara.num], varPara.deltaX);
                        System.out.println("水头到末点后，再运行10h，结束计算!");
                        break;
                    }
                }
                //结束处算一次静压
                if (kk== varPara.kt){
                    staticP(n, varPara.waterL[varPara.num], varPara.deltaX);
                }

            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
        /**
         * 循环结束,存储lg_his pg_his mg_his
         */
        double[][] lgHisReverse = SizeChange.reverse(varPara.lg_his);
        SizeChange lgHisChange = new SizeChange(lgHisReverse);
        Map<Integer, double[]> lgHisAfterChange = lgHisChange.ResultAfterChange();
        setLgHis(lgHisAfterChange);
        double[][] pgHisReverse = SizeChange.reverse(varPara.Pg_his);
        SizeChange pgHisChange = new SizeChange(pgHisReverse);
        Map<Integer, double[]> pgHisAfterChange = pgHisChange.ResultAfterChange();
        setPgHis(pgHisAfterChange);
        double[][] mHisReverse = SizeChange.reverse(varPara.M_his);
        SizeChange mHisChange = new SizeChange(mHisReverse);
        Map<Integer, double[]> mHisAfterChange = mHisChange.ResultAfterChange();
        setmHis(mHisAfterChange);
        /**
         * 全部循环结束,存储pig_V pig-L allLineStaticP
         * 起始时刻:flagpigT
         */
        SizeChange pigVChange = new SizeChange(varPara.pigV,flagpigT/12,(1.0/12));
        double[][] pigVAfterChange = pigVChange.ResultAfterChange2();
        setPigV(pigVAfterChange);
        SizeChange pigLChange = new SizeChange(varPara.pigL,flagpigT/12,(1.0/12));
        double[][] pigLAfterChange = pigLChange.ResultAfterChange2();
        setPigL(pigLAfterChange);
        SizeChange aLSPChange = new SizeChange(varPara.allLineStaticP[0],0.5,0.5);
        double[][] aLSPAfterChange = aLSPChange.ResultAfterChange2();
        setaLSP(aLSPAfterChange);


        end1 = System.currentTimeMillis();
        System.out.println("计算时间 start time:" + start1+ "; end time:" + end1+ "; Run Time:" + (end1 - start1) + "(ms)");
        end3 = System.currentTimeMillis();
        System.out.println("TXT输出时间 start time:" + end1+ "; end time:" + end3+ "; Run Time:" + (end3 - end1) + "(ms)");
    }
    //迭代计算明渠流动公式
    public  double calDelta(double slopeD) {

        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        double xa = 0.1;
        double xb = Math.PI - 0.1;
        double delta = xa;
        double fa = 1.49/conPara.n*Math.pow((Math.pow(r,2)*(delta - 0.5*sin(2*delta))),(5.0/3.0))*Math.pow((1.0/(2*r*delta)),(2.0/3.0))*Math.pow(slopeD,0.5) - conPara.Ql;
        for(int i=0;i<1000;i++)
        {
            double xc = (xa + xb)/2.0;
            delta = xc;
            double fc = 1.49/conPara.n*Math.pow((Math.pow(r,2)*(delta - 0.5*sin(2*delta))),(5.0/3.0))*Math.pow((1.0/(2*r*delta)),(2.0/3.0))*Math.pow(slopeD,0.5) - conPara.Ql;
            if(fa*fc < 0){
                xb = xc;
            }else
            {
                xa = xc;
            }
            if((xb - xa) < 1e-5){
                break;
            }
        }

        return delta;
    }

    public double cal0(double slopeD, double Hgk, double MG, double Lpaiqi, double H1, double H2){
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        double deltaa = -10.0;
        double deltab = 10.0;
        double delta = deltaa;
        double resulta = (Lpaiqi - delta/slopeD)*Hgk*Math.PI*Math.pow(A,2)/4.0*(oil.density*conPara.g*(H2 - H1 - 2*delta) + conPara.p0) - MG/0.029*8.314* oil.temperature;
        for(int i=0;i<200000;i++)
        {
            double deltac = (deltaa + deltab)/2.0;
            delta = deltac;
            double resultc = (Lpaiqi - delta/slopeD)*Hgk*Math.PI*Math.pow(A,2)/4.0*(oil.density*conPara.g*(H2 - H1 - 2*delta) + conPara.p0) - MG/0.029*8.314* oil.temperature;

            if(resulta*resultc < 0){
                deltab = deltac;
            }else
            {
                deltaa = deltac;
            }
            if((deltab - deltaa) < 0.000001){
                break;
            }
        }


        return delta;
    }
    //迭代计算每个下坡段的液位----
    public  double cal3(double h2, double Hgk, double za, double zb, double slopeD, double slopeU, double MG, double P2){

        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        double h1a = 0.1;
        double h1b = za - zb;
        double h1 = h1a;
        double fh1a = MG/0.029* oil.temperature*8.314/(A*Hgk*(za-zb-h1))*slopeD + oil.density*conPara.g*(h1-h2) - P2 - J* oil.density*conPara.g*(h1/slopeD + h2/slopeU);
        for(int i=0;i<1000;i++)
        {
            double h1c=(h1a+h1b)/2.0;
            h1 = h1c;
            double fh1c = MG/0.029* oil.temperature*8.314/(A*Hgk*(za-zb-h1))*slopeD + oil.density*conPara.g*(h1-h2) - P2 - J* oil.density*conPara.g*(h1/slopeD + h2/slopeU);
            if(fh1a*fh1c < 0){
                h1b = h1c;
            }else
            {
                h1a = h1c;
            }
            if((h1b - h1a) < 0.001){
                break;
            }
        }

        return h1;
    }

    // 计算每个U形管的下坡和上坡的正弦值
//i是U形管的数量，取值范围是1到a，a是U形管的总数
    public static double[][] calSlopeD(double[][] z, double[][] l) {
        double[][] slopeD = new double[l.length][1];
        for(int i=1;i<l.length;i++)
        {
            slopeD[i][0] = (z[i][1] - z[i][2])/(l[i][2] - l[i][1]);
        }
        return slopeD;

    }
    public static double[][] calSlopeU(double[][] z, double[][] l) {
        double[][] slopeU = new double[l.length][1];
        for(int i=1;i<l.length;i++)
        {
            slopeU[i][0] = (z[i][3] - z[i][2])/(l[i][3] - l[i][2]);
        }
        return slopeU;
    }
    //-----------------迭代计算新的delta--------------------------
    //--------------delta是液相的圆周角，rad --------
    public  double calVen(double slopeD,double Pgk) {
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        double rhoG = Pgk*0.029/ oil.temperature/8.314;
        double ya = 0.1;
        double yb = Math.PI - 0.1;
        double delta = ya;
        double Sl = 2*delta*r;
        double Si = 2*r*sin(delta);
        double Hgk = 1.0 - 1.0/Math.PI*(delta - 0.5*sin(2*delta));
        double Dhl = 4* A*(1.0 - Hgk)/Sl;
        double vl = conPara.Ql/(A*(1.0 - Hgk));
        double Rel = Dhl*vl/(1.006e-6);
        double fl = 0.001375*(1 + Math.pow((2*Math.pow(10,4)*pipeLine.getRoughness()/Dhl + 1e6/Rel),(1.0/3.0)));
        double tauW = Math.pow(fl* oil.density*vl,2)/2.0;
        double tauI = Math.pow(conPara.phi*rhoG*vl,2)/2.0;
        double qa = (oil.density - rhoG)*conPara.g*slopeD - tauW*Sl/ A/(1.0 - Hgk) - tauI*Si*(1.0/ A/(1.0 - Hgk) + 1.0/ A/Hgk);
        for(int i=0;i<1000;i++)
        {
            double yc = (ya + yb)/2.0;

            delta = yc;
            Sl = 2*delta*r;
            Si = 2*r*sin(delta);
            Hgk = 1.0 - 1.0/Math.PI*(delta - 0.5*sin(2*delta));
            Dhl = 4* A*(1.0 - Hgk)/Sl;
            vl = conPara.Ql/(A*(1.0 - Hgk));
            Rel = Dhl*vl/(1.006e-6);
            fl = 0.001375*(1.0 + Math.pow((2*Math.pow(10,4)*pipeLine.getRoughness()/Dhl + 1e6/Rel),(1.0/3.0)));
            tauW = Math.pow(fl*oil.density*vl,2)/2.0;
            tauI = Math.pow(conPara.phi*rhoG*vl,2)/2.0;
            double qc = (oil.density - rhoG)*conPara.g*slopeD - tauW*Sl/ A/(1.0 - Hgk) - tauI*Si*(1.0/ A/(1.0 - Hgk) + 1.0/ A/Hgk);

            if(qa*qc < 0){
                yb = yc;
            }else
            {
                ya = yc;
            }
            if((yb - ya) < 0.00001){
                break;
            }
        }

        return delta;
    }
    //
    public   double[][] matrixAssemblyX(double lgk,double Pgk,double Hgk, double slopeU,double slopeD, double uk,double h1k,double h2k){
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        double a11 = lgk;
        double a12 = Pgk;

        double a22 = -Hgk/ varPara.deltaT;
        double a24 = 1.0/(slopeU* varPara.deltaT);
        double a33 = oil.density* A*uk/slopeD/ varPara.deltaT;
        double a34 = oil.density* A*uk/slopeU/ varPara.deltaT;
        double a35 = oil.density* A*(h1k/slopeD/ varPara.deltaT + h2k/slopeU/ varPara.deltaT);
        double a44 = 1.0;
        double a52 = 1.0;
        double a53 = 1.0/slopeD;
        double[][] X = new double[][]{{a11,a12,0,0,0},{0,a22,0,a24,0},{0,0,a33,a34,a35},{0,0,0,a44,0},{0,a52,a53,0,0}};
        return X;
    }

    /*形参含义：lgk：当前U型管段,在第k时步的积气段长度；
     *          backPressure_k：当前U型管段,在第k时步的背压；
     *          mgk_k：当前U型管段,在第k时步的净流入的气体的质量，等于上个U型管流入的减去本U型管段流出的；
     *          pgk：当前U型管段,在第k时步的积气段压力；
     *          Hgk：当前U型管段,在第k时步的积气段截面含气率；
     *          slopeU：当前U型管段上坡段的夹角的sin值；
     *          slopeD：当前U型管段下坡段的夹角的sin值；
     *          uk：当前U型管段,在第k时步的液塞生长速度；
     *          h1k：当前U型管段,在第k时步，下坡段的液位高度；
     *          h2k：当前U型管段,在第k时步，上坡段的液位高度；
     * */
    public  double[][] matrixAssemblyZ(double lgk, double backPressure_k, double mgk, double Pgk, double Hgk, double slopeU, double slopeD, double uk, double h1k, double h2k){
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        double b1 = 2*Pgk*lgk + varPara.deltaT*mgk* r* oil.temperature/(conPara.Mg*A);
        double b2 = conPara.Ql/ A + h2k/slopeU/ varPara.deltaT - Hgk*lgk/ varPara.deltaT;
        double b3 = (Pgk - backPressure_k)*A - F*(h1k/slopeD + h2k/slopeU) - oil.density*A*(h2k - h1k)*conPara.g
                + oil.density*Math.pow(conPara.Ql,2)/ A/(1.0 - Hgk) + 2* oil.density*A*(uk*h1k/slopeD/varPara.deltaT + uk*h2k/slopeU/varPara.deltaT);
        double b4 = h2k + uk*slopeU*varPara.deltaT;
        double b5 = lgk + h1k/slopeD;
        double[][] Z = new double[][]{{b1},{b2},{b3},{b4},{b5}};      //五行一列
        return Z;
    }


    public  void Save_his(int i,int n, int k) {

        for (i=1;i<=n;i++){
            varPara.Pg_his[i][k] = varPara.Pgk[i]/1000000.0;           //气体压力，Pa,转化为MPa
            varPara.lg_his[i][k]= varPara.lgk[i]/1000.0;             //下坡段气段长度，m，转化为km
            varPara.h1_his[i][k] = varPara.h1k[i];             //下坡段液高，m
            varPara.h2_his[i][k] = varPara.h2k[i];             //上坡段液高，m
            //varPara.u_his[i][k] = varPara.uk[i];              //上坡段积液速度，m/s
            varPara.Deng_his[i][k] = varPara.Dengk[i];
            varPara.dMg_out_his[i][k] = 5.0*varPara.dMg_out[i];     //原为0.2s下的破碎质量，转化为1s下的
            varPara.dMg_in_his[i][k] = 5.0*varPara.dMg_in[i];       //原为0.2s下的破碎质量，转化为1s下的
            varPara.backPressure_his[i][k] = varPara.backPressure[i]/1000000.0;//背压，Pa,转化为MPa
            varPara.M_his[i][k] = varPara.MGk[i];
            //varPara.hgs_his[i][k] = varPara.hgs_k[i];
            //varPara.hgw_his[i][k] = varPara.hgw_k[i];
            //varPara.hl_his[i][k] = 1-varPara.Hgk[i];
            varPara.HFB[i][k] = varPara.Hfk_b[i];
            varPara.HFF[i][k] = varPara.Hfk_f[i];
            varPara.HFBU[i][k] = varPara.Hfk_bU[i];

        }





    }
    public  void cal_l0() {
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        //水顶气满管流的水力坡降
        double nta = 0.11*Math.pow((pipeLine.getRoughness()/A),0.25);         //粗糙区的公式
        double beta=0.0826*nta;
        double m1=0.0;
        varPara.ipj=beta*Math.pow(conPara.Ql,2-m1)*Math.pow(1e-6,m1)/Math.pow(A,5-m1);//粗糙区的水力坡降

        //各管段的气段尾部估计位置
        /*
         * 压力作用下的气段破碎预计位置
         * 是否需要根据压力变化，进行调整？实时更新？
         * */
        for (int n=1;n<varPara.line_l.length;n++){
            varPara.l0[n]=varPara.line_d[n][3] + 0.2*1e6/oil.density/conPara.g + (varPara.ipj)*varPara.line_l[n][3]-varPara.line_d[n][1];
            varPara.l0[n]=varPara.l0[n]+(varPara.line_d[n][1]-varPara.line_d[n][2])/(varPara.line_l[n][1]-varPara.line_l[n][2])* varPara.line_l[n][1];
            varPara.l0[n]=varPara.l0[n]/((varPara.line_d[n][1]-varPara.line_d[n][2])/(varPara.line_l[n][1]-varPara.line_l[n][2]) + varPara.ipj);
        }

    }


    public  void dtPosui(int timeStepNum, int kkk,double l0, double l1, double z1, double l2, double z2, double q,
                         double d, double dg0, double M0, double Pgp, double Lgp, double dgp, double Hgp, double[] M,
                         double[] Pg, double[] Lg, double[] deng, double[] Hg, double dl, double nd) throws IOException
    {
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        //1、定义初始参数
        double hg,hl,dg,lg,pg,Mg,nums=kkk;
        //继承形参
        hg = Hgp;
        hl = 1-hg;
        dg = dgp;
        lg = Lgp;
        pg = Pgp;
        Mg = M0;

        double vlf;  //液膜速度
        double Glf;  //相对液膜速度
        double sita,sita1; //夹角角度
        double sinsita;
        double dt=varPara.deltaT;

        double EoD;         //直径为D时的Eotvos数
        double dcrit;       //最大气泡直径，由气泡临界变形尺寸计算
        double CJ=1.0;      //可调参数，由湍流场强度和临界气泡尺寸大小进行调整
        double C1=2.0/3;    //可调参数
        double we,wec;      //韦伯数、临界韦伯数
        double GGe;         //破碎速度

        //标记的s和w采用了不同的初始值
        double c0s=0.9;     //？？？下游液塞区域速度分布系数，破碎气泡主要在管壁区域集中时，C0<1,常为水平管道
        double hgs=0.0;     //初始气相含气率？？？？？
        double v0s;         //小气泡漂移速度
        double vgs;
        double vm;          //混合速度
        double c0w = 0.5;   //长气泡尾部区域速度分布系数，破碎气泡主要在管壁区域集中时，C0<1,常为水平管道，HG推荐值
        double hgw = 0.0;   //截面含气率
        double v0w;         //小气泡漂移速度
        double vgw = 0.0;     //长段塞尾部气相速度----------加了初始化
        double f;             //隐式迭代判定参数
        double tao_l,sl,Resl;       //液相剪切应力、液相湿周、液相
        double b;               //含气段hl对应的湿周半角
        double fi = 0.0142;     //相间摩阻系数，泰勒、杜克勒推荐值
        double tao_i;       //相间剪切应力
        double si;          //湿周
        double dp;          //未破碎前的含气段的压力
        double pgw;         //长气泡尾部气体压力
        double dgw;         //长气泡尾部气体密度
        double dM;      //破碎质量变化
        double fl=0.001,vl=0.001;               //液相摩阻、液相流速
        int n;
        int k;
        int flag = 0;        //---------加了初始化



        double dL;           //气段长度差

        //2、计算部分参数
        sinsita =(z1-z2)/(l2-l1);   //化为锐角，始终保持为四十五度以内，公式（4.69）
        sita=asin(sinsita);

        if(Math.abs(sita)<=Math.PI/4)
            sita1=Math.abs(sita);
        else
            sita1=Math.PI/2-Math.abs(sita);

        Resl=q*d/A/nd;              //液相雷诺数，不是没有密度，而是用密度将动力粘度转换成了运动粘度，nd为粘度，远大于2100，直接用（4.36）的二式

        //3、判定是否破碎*****是否会完全破碎
        if(l0-l1<1e-2)//含气段会完全破碎
            dL=1e-2;
        else
            dL=l0-l1;

        vlf=q/A/hl;                                                 //液膜平均速度
        Glf=vlf*hl;                                                 //相对液膜速度
        EoD= abs((dl-dg)*9.8*d*d/8/conPara.FT);       //计算破碎速度    FT为水的表面张力
        //System.out.println(EoD);
        dcrit=0.224/Math.pow(Math.abs(Math.cos(sita1)*EoD),0.5);    //计算临界气泡直径
        we=dl*d*Math.pow(vlf-Glf,2)/conPara.FT;           //计算韦伯数
        //System.out.println(we);
        wec=100*C1/dcrit;                                           //临界韦伯数


        if (we-wec < 0){

            if (timeStepNum%300==0){
                if (nums==kkk)
//                System.out.println("未破碎！i="+kkk);
                  nums++;

            }
        }else{

            GGe=1.0/400/CJ*dcrit*Math.abs(we-wec)*Glf;                  //计算破碎速度
            vm=q/A+GGe;                                                 //------泡状流混合速度
            //System.out.println(vm);

            for(k=0;k<3;k++)   //计算hgs      长气泡尾部处的截面含气率
            {
                hgs=0.001*Math.pow(0.9,k);
                n=0;
                do
                {
                    //System.out.println("破碎计算2已启动");
                    n++;
                    hgs=hgs+0.001;
                    if(hgs>=0.9)
                        break;
                    v0s= conPara.FT * 9.8 * Math.abs(dl-dg)/dl/dl;     //
                    v0s=Math.pow(v0s,0.25);             //气泡漂移速度，公式4.74
                    v0s=1.53*v0s*sinsita;               //
                    v0s=v0s*Math.pow(1-hgs,1.5);        //
                    vgs=c0s*vm-v0s;                     //
                    f=GGe/vgs-hgs;
                    if(Math.abs(f)<1e-2)
                    {
                        //System.out.println("满足误差");
                        flag=1;
                        break;
                    }

                }while(n<1000);

                if(flag==1)
                    break;
            }
            varPara.hgs_k[kkk]=hgs;

            flag=0;

            for(k=0;k<10;k++)   //计算hgw     长气泡尾部处的截面含气率
            {
                hgw=0.001*Math.pow(0.85,k);
                n = 0;
                do  //破碎模拟，计算hgw和vgw
                {
                    n++;
                    hgw=hgw+0.001;
                    if(hgw>=0.9)
                        break;
                    v0w=conPara.FT*9.8*Math.abs(dl-dg)/dl/dl;
                    v0w=Math.pow(v0w,0.25);
                    v0w=1.53*v0w*sinsita;
                    v0w=v0w*Math.pow(1-hgw,1.5);            //气泡漂移速度
                    vgw=c0w*vm-v0w;                         //式4.72
                    f=GGe/vgw-hgw;                          //f是判定循环结束的标志
                    if(Math.abs(f)<1e-2)                    //式4.77的迭代求解
                    {
                        //System.out.println("满足误差");
                        flag=1;
                        break;
                    }

                }while(n<1000);
                if(flag==1)
                    break;
            }
            varPara.hgw_k[kkk]=hgw;

            flag=0;

            //计算dM,Mg,pc
            tao_i = fi*dg*Math.pow(q/A/hl,2)/2;                 //相界面剪切力？摩阻
            b=1-2*hl+Math.pow(hl,1.0/3)-Math.pow(hg,1.0/3);
            b=b*Math.pow(3*Math.PI/2,1.0/3);
            b=Math.PI*hl+b+0.002;                               //破碎前含气段的湿周半角

            si=d*sin(b);


            dp=tao_i*si/hg/A*lg;                     //Pa，相界面造成的压力损失
            pgw=pg-dp;                               //气段尾的压力等于气段头减摩阻损失
            //dgw=dg0/conPara.p0*pg;                   //dgw长气泡尾部密度，大气压（0.1MPa）下的密度dg0作为依据，计算而出


            dM=0.001*pgw*A*hg*(hgw-hgs)*vgw*dt/conPara.R/oil.temperature;         //计算dM,pc   //dgw长气泡尾部密度   hgw截面含气率  vgw尾部气体速度  dt计算时间步长
            //dM=0.002*pgw*A*hg*(hgw-hgs)*vgw*dt/conPara.R/oil.temperature;         //计算dM,pc   //dgw长气泡尾部密度   hgw截面含气率  vgw尾部气体速度  dt计算时间步长
            //dM=0;         //计算dM,pc   //dgw长气泡尾部密度   hgw截面含气率  vgw尾部气体速度  dt计算时间步长
            varPara.dMg_out[kkk]=dM;                    //净流出的质量
            lg=lg-(dM-varPara.dMg_in[kkk])/Mg*lg;
            dg=Mg/(lg*A*hg);
            //dg=dg-dM/Mg*dg;
            //lg=lg-dM/Mg*lg;
            Mg=varPara.MGk[kkk]-varPara.dMg_out[kkk]+varPara.dMg_in[kkk];       //20201103更新气段质量表达式


            for(k=0;k<10;k++)  //计算下一时步的hl       液膜持液率
            {
                b=0.1*Math.pow(0.9,k);
                n=0;
                do  //以Mg、pc为基础参数进行稳态计算，计算下一时步的hg、dg、L、Pg
                {
                    n++;
                    b=b+0.01;
                    if(b>Math.PI)
                        break;

                    hl=(b-0.5*sin(2*b))/Math.PI;
                    hg=1-hl;

                    fl=0.0262*Math.pow(hl*Resl,-0.139);
                    vl=q/A/hl;
                    tao_l=fl*dl*vl*vl/2;
                    sl=d*b;

                    tao_i=fi*dg*vl*vl/2;
                    si=d*sin(b);


                    f=tao_l*sl/A/hl+tao_i*si/A*(1/hl+1/hg)-9.8*(dl-dg)*sinsita; //原为式4.18，现以拟合的关系式做修改
                    //f=9.8*(dl)*sinsita*A*hl-tao_l*sl-tao_i*si-A*hl*dp; //原为式4.18，现以拟合的关系式做修改


                    if(Math.abs(f)<50)
                    {
                        flag=1;
                        break;
                    }

                }while(n<1000);

                if(flag==1)
                    break;
            }

            //hl = hl*(1+Math.abs(dp/varPara.Pgk[kkk]));
//                        if (hl>0.995)
//                            hl=0.995;


            varPara.Hgk[kkk] = 1-hl;

            pg = dg/dg0*conPara.p0;      //不同压力密度进行折算，实时气段压力等于气体带压密度除以气体在大气压下的密度，再乘以大气压

            //5、参数传递
            varPara.Pgk[kkk] = pg;
            //varPara.lgk[kkk] = lg;
            varPara.Dengk[kkk] = dg;


            varPara.MGk[kkk] = Mg;
            varPara.lgk[kkk] = lg;


            //传递当前时步的液相范宁摩阻系数,2021.01.03
            varPara.f_l[kkk]=fl;
            varPara.f_i[kkk]=fi;

            //气泡流持液率变化
            varPara.Hgbk[kkk]=hgw*0.1;



        }



    }

    public  void YaSuo(int i, double lgk,double Pgk,double Hgk,double slopeU,double slopeD,double uk,double h1k,double h2k,double backPressure,double dMg) {
        //功能：计算一个时步的压缩过程
        double X[][] = matrixAssemblyX(lgk, Pgk, Hgk, slopeU, slopeD, uk, h1k, h2k);
        double Z[][] = matrixAssemblyZ(lgk, backPressure,dMg, Pgk, Hgk, slopeU, slopeD, uk, h1k, h2k);
        Matrix matrixX = new Matrix(X);//新建矩阵，导入二维数组X
        Matrix matrixZ = new Matrix(Z);//新建矩阵，导入二维数组Z
        Matrix matrixY = matrixX.inverse().times(matrixZ);
        double YY[][] = matrixY.getArray();  //矩阵转二维数组
        for(int j=1; j<6; j++){
            varPara.Y[j][0] = YY[j-1][0];
        }


//        }
        varPara.Pgk[i] = varPara.Y[1][0];
        varPara.lgk[i]= varPara.Y[2][0];
        varPara.h1k[i]= varPara.Y[3][0];
        varPara.h2k[i]= varPara.Y[4][0];
        varPara.uk[i]= varPara.Y[5][0];



    }

    public  void flowPressure(int i, double lgk,double Pgk,double Hgk,double slopeU,double slopeD,double uk,double h1k,double h2k,double backPressure,double dMg) {
        //功能：计算流动过程的压力变化
        double X[][] = matrixAssemblyX(lgk, Pgk, Hgk, slopeU, slopeD, uk, h1k, h2k);
        double Z[][] = matrixAssemblyZ(lgk, backPressure,dMg, Pgk, Hgk, slopeU, slopeD, uk, h1k, h2k);
        Matrix matrixX = new Matrix(X);//新建矩阵，导入二维数组X
        Matrix matrixZ = new Matrix(Z);//新建矩阵，导入二维数组Z
        Matrix matrixY = matrixX.inverse().times(matrixZ);
        double YY[][] = matrixY.getArray();  //矩阵转二维数组
        for(int j=1; j<6; j++){
            varPara.Y[j][0] = YY[j-1][0];
        }
        if(varPara.Y[2][0]<0) System.out.println("varPara.Y[2][0]="+varPara.Y[2][0]+",varPara.lgk[i]="+varPara.lgk[i]);
        varPara.Pgk[i] = varPara.Y[1][0];
        varPara.lgk[i]= varPara.Y[2][0];
        varPara.h1k[i]= varPara.Y[3][0];
        varPara.h2k[i]= varPara.Y[4][0];
        varPara.uk[i]= varPara.Y[5][0];

    }





    /**
     * 破碎结束需要固定气段质量
     * 设置一个标记，ssr【i】不为零则表示本段计算中止。
     */
    public  void FlowConversion(int kk,int j) {


        if (varPara.MGk[j] < 5 && varPara.MGk[j] > 0.01)
        {
            while (varPara.ssr[j][0] == 0.0)
            {
                System.out.println("第" + j + "段在第" + 0.2*kk/3600.0 + "h压缩、破碎结束！");

                varPara.ssr[j][0]++;
            }
        }


    }


    /**
     * 接入瞬态程序的气泡和气团流比例
     * @param degree    角度，如果是弧度需要先用Math.degree方法处理
     * @param dz    上坡段高程差
     * @return  整体长度中气团流的比例，气团流采用新方法计算压降，或者用段塞流方法计算压降
     */
    public static double xishu(double degree,double dz){
        double xishu=0;
        if (Math.abs(degree)<30 && Math.abs(degree)>20 ){
            if (Math.abs(dz)<150 && Math.abs(dz)>0 ){
                xishu=0.31;
            }else if (Math.abs(dz)<300 && Math.abs(dz)>150 ){
                xishu=0.38;
            }else if (Math.abs(dz)<500 && Math.abs(dz)>300 ){
                xishu=0.425;
            }
        }else if (Math.abs(degree)<20 && Math.abs(degree)>10 ){
            if (Math.abs(dz)<150 && Math.abs(dz)>0 ){
                xishu=0.27;
            }else if (Math.abs(dz)<300 && Math.abs(dz)>150 ){
                xishu=0.34;
            }else if (Math.abs(dz)<500 && Math.abs(dz)>300 ){
                xishu=0.385;
            }
        }else if (Math.abs(degree)<10 && Math.abs(degree)>0 ){
            if (Math.abs(dz)<150 && Math.abs(dz)>0 ){
                xishu=0.235;
            }else if (Math.abs(dz)<300 && Math.abs(dz)>150 ){
                xishu=0.305;
            }else if (Math.abs(dz)<500 && Math.abs(dz)>300 ){
                xishu=0.35;
            }
        }
        return xishu;
    }

    //总摩阻计算

    /**
     * 根据分层流和气泡流的单位压降和各流型长度计算总压降
     * @param vsl
     * @param vsg
     * @param x
     * @param num
     * @param A
     * @param dl
     * @param dg
     * @param dpb
     * @param dpf
     * @param lg
     * @param lp
     * @return
     */
    public double [] dP_cal(double vsl,double vsg,double x,int num,double A,double dl,double dg,double dpb,double dpbU,double dpf,double lg,double lp,double lpU)
    {
        double G,dpj,dp;//气泡流混合项参数

        double []dpk=new double[5];
        //混合相的质量流量
        G=conPara.Ql*dl*(1-x)+A*vsg*dl*x;
        //局部摩阻系数
        double j=1+1.3*(x*(1-x)*(1+829.187)*Math.pow((1-0.001206),0.5)/(1+x*828.187));
        //局部摩阻计算式
        dpj=j*G*G/(2*A*A*dl)*(1+x*(dl/dg-1));

        //总摩阻输出
        dp=dpf*lg+dpb*lp+dpbU*lpU+dpj*num;

        dpk[0]=dpf;
        dpk[1]=dpb;
        dpk[2]=dpj;
        dpk[3]=dp;

        //反算摩阻
        return dpk;

    }


    //泡状流压降计算，重位压降与摩阻压降异号为下坡段
    public double dPb_cal(double A,double slope, double x,double D,double dl,double dg,double u,double Um,double vsl,double vsg,double e)
    {
        double G,Rem,rhom;//混合质量流量

        //混合相的质量流量
        G=A*vsl*dl*(1-x)+A*vsg*dg*x;
        //dl还是dg？？？

        //混合相的密度
        rhom=dl*(1-x)+dg*x;

        //混合项雷诺数
        //Rem=4*G/((Math.PI)*D*u);          //混合物动力粘度取水的，u=1mPa·s
        Rem=rhom*Um*D/(u);          //混合物动力粘度取水的，u=1mPa·s



        //气泡流摩阻系数
        double fF;
        fF=0.001375*(1+Math.pow((2*10000)*e/D+(10e6/Rem),1/3));
        //气泡流摩阻式

        //double dpb=2*fF*rhom*Um*Um/D+rhom*9.81*slope;
        //不带重位压差
        double dpb=2*fF*rhom*Um*Um/D+rhom*9.81*slope;//0.9为重力转化系数
        return dpb;

    }

    public static  double dPbU_cal(double A,double slope, double x,double D,double dl,double dg,double u,double Um,double vsl,double vsg,double e)
    {
        double G,Rem,rhom;//混合质量流量

        //混合相的质量流量
        G=A*vsl*dl*(1-x)+A*vsg*dg*x;
        //dl还是dg？？？

        //混合相的密度
        rhom=dl*(1-x)+dg*x;

        //混合项雷诺数
        //Rem=4*G/((Math.PI)*D*u);          //混合物动力粘度取水的，u=1mPa·s
        Rem=rhom*Um*D/(u);          //混合物动力粘度取水的，u=1mPa·s



        //气泡流摩阻系数
        double fF;
        fF=0.001375*(1+Math.pow((2*10000)*e/D+(10e6/Rem),1/3));
        //气泡流摩阻式

        //double dpb=2*fF*rhom*Um*Um/D+rhom*9.81*slope;
        //不带重位压差
        double dpb=2*fF*rhom*Um*Um/D+rhom*9.81*slope;
        return dpb;

    }


    public static  double dPd_cal(double slope,double x,double Q,double A,double D,double dl,double vsl,double vsg,double e)
    {
        double G,Re0,rhom,c0,fl,vb=1.4,vpj=Q/A;//混合质量流量
        //混合相的质量流量
        G=A*vsl*dl*(1-x)+A*vsg*1.205*x;

        c0=0.00252*Math.log(1000*0.001)/pow(D,1.38)-0.782+0.232*Math.log(vpj)-0.428*log(D);
        if (c0<-0.213*vpj) c0=-0.213*vpj;

        //混合相的密度
        rhom=G+dl*vb*A/(Q+vb*A)+c0*dl;

        Re0=D*vpj*dl/0.001;//纯液相雷诺数
        fl=0.11*pow((e/D+68/Re0),0.25);

        //段塞流摩阻式
        double dpd=fl*dl*vpj*vpj*(1+c0)/2/D+rhom*9.81*slope;
        return dpd;
    }
    //分层流压降计算

    /**
     * 分层流的压降计算
     * @param slope 角度
     * @param x 含气率
     * @param D 管道直径
     * @param dl 液相密度
     * @param dg 气相密度
     * @param vsl 液相流速
     * @param fl 液相摩阻系数
     * @param fi 相间摩阻系数
     * @param sl 液相湿周
     * @return  某时刻分层流的单位摩阻
     */
    public double dpf_cal(double slope, double x,double D,double dl,double dg,double vsl,double fl,double fi,double sl){

        double rhom;

        //混合相的质量流量
        //G=A*vsl*dl*(1-x)+A*vsg*dl*x;

        //混合相的密度
        rhom=dl*(1-x)+dg*x;

        //分层流摩阻式
        //double dpf=0.01*(dl*vsl*vsl*2/Math.PI*D)*(2*fi*sin(sl/D)*D+fl*sl/(1-x)/(1-x));//无重力项
        double dpf=(dl*vsl*vsl*2/Math.PI*D)*(2*fi*sin(sl/D)*D+fl*sl/(1-x)/(1-x))+rhom*9.81*slope;
        return dpf;

    }





    /**
     * 计算某时刻全线压力的方法,在其他函数全部计算完毕后进行
     * @param n  当前总运行段数
     * @param dx 距步
     * @param T  总模拟时长，用来计算数组大小
     * @param num 时步数，控制，衔接各管段
     * @param waterHeadLocation 水头位置，要改为3000s每步输入，在现有的rr基础上每300个存储一次3000*0.5=1500m，保证水头记录一次的长度大于距步
     * @param dpb 气泡流下坡段单位长度压降
     * @param dpbU  气泡流上坡段单位长度压降
     * @param dpf   分层流单位长度压降
     * @param lg_f  各段分层流实时长度
     */
    public void dpL(int n,double T,int num,double dx,double[] waterHeadLocation,double[] dpb,double[] dpbU,double[] dpf,double [][]lg_f,double []vll,double []stationL,double []stationP){
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        //double [][]dPL=new double[500][800];//当前时步下,各点的压力【时步】【距步】500*300,10s,300,,*30
        int nn=1,ii=1;
        int kkk=num;
        //for (int kkk=num;kkk<(int)T*12+1;kkk++)//时间循环
        //{
            varPara.dPL[kkk][0]=varPara.Hs;       //首站进站压力，前期自流

            varPara.Lt=varPara.line_l[1][1];
            for (int x=1;x<=(waterHeadLocation[kkk]-varPara.line_l[1][1])/dx+1;x++)//遍历水头前的各点
            {
                varPara.Lt=varPara.Lt+dx;

                for (int i=ii;i<=nn;i++)//遍历水头所在段前的各段
                {
                    if (varPara.ssr[i][0]!=0) {//破碎结束，按满管计算{

                        if (varPara.Lt - varPara.line_l[i][2] <= 0)  //在下坡段时的满管压降计算
                        {
                            varPara.dPL[kkk][x] = oil.getDensity()*9.81*(varPara.slopeD[i][0]-varPara.ipj) * dx + varPara.dPL[kkk][x - 1];      //第kkk时步时的距首点第x个距步处的压降；
                        } else if (varPara.Lt - varPara.line_l[i][2] > 0 && varPara.Lt <= varPara.line_l[i][3]) {     //在上坡时
                            if (varPara.flag0==0){
                                //加入转折处波速变化，积气系数取0.03
                                varPara.dPL[kkk][x-1]=varPara.dPL[kkk][x-1]-0.015*oil.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/A);
                                varPara.flag0++;
                            }
                            varPara.dPL[kkk][x] = varPara.dPL[kkk][x - 1] - oil.density*9.81*(varPara.slopeU[i][0]+varPara.ipj) * dx;
                        } else {
                            if (n > nn) {
                                ii++;
                                nn++;
                                varPara.flag0=0;
                            }
                        }
                            //varPara.dPL[kkk][x-1]=varPara.dPL[kkk][x];
                    }else if (varPara.ssr[i][0]==0){
                        if (varPara.Lt-varPara.line_l[i][1] <= lg_f[i][kkk])  //在小于分层流长度时
                        {
                            varPara.dPL[kkk][x]=-dpf[i]*dx+varPara.dPL[kkk][x-1];      //第kkk时步时的距首点第x个距步处的压降；
                        } else if (varPara.Lt-varPara.line_l[i][1] > lg_f[i][kkk] && varPara.Lt-varPara.line_l[i][1] <= (varPara.line_l[i][2]-varPara.line_l[i][1])) {     //在下坡气泡流时
                            varPara.dPL[kkk][x]=varPara.dPL[kkk][x-1]-dpb[i]*dx;
                        } else if (varPara.Lt-varPara.line_l[i][1] > (varPara.line_l[i][2]-varPara.line_l[i][1]) && varPara.Lt-varPara.line_l[i][2] <= (varPara.line_l[i][3]-varPara.line_l[i][2])){//在上坡气泡流时
                            if (varPara.flag0==0){
                                //加入转折处波速变化，积气系数取0.03
                                varPara.dPL[kkk][x-1]=varPara.dPL[kkk][x-1]-0.025*oil.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/A);
                                varPara.flag0++;
                            }
                            varPara.dPL[kkk][x]=varPara.dPL[kkk][x-1]-dpbU[i]*dx;
                        } else{
                            if (n>nn){
                                ii++;
                                nn++;
                                varPara.flag0=0;
                            }
                        }
                    }
                }
                for (int s=0;s<stationL.length;s++){        //改变站点压力值
                    if (varPara.Lt>=stationL[s] && varPara.Lt-dx <=stationL[s] && stationL[s]!=0){
                        varPara.dPL[kkk][x]=stationP[s]+varPara.dPL[kkk][x-1];
                        //if (varPara.dPL[kkk][x]>20000000) varPara.dPL[kkk][x]=20000000;//限制出站压力12MPa，后续加调压阀或者调整变频泵
                        if (varPara.dPL[kkk][x]>16000000)
                        {
                            //varPara.checkP=1;
                            //valveCalDp1(varPara.Qh/3600.0,D,5.68,1,1000);/////////////////////////////////////////////////////////////////
                            //varPara.Qh=valveCal(valveCalDp1(varPara.Qh,D,5.68, 1,1000),varPara.dPL[kkk][x]-16000000,varPara.Qh);
                        }
                        //varPara.checkP=1;
                        varPara.Hss[s]=varPara.dPL[kkk][x-1];//记录各站的进站压力
                    }

                }

                if(varPara.dPL[kkk][x]>20000000){
                    varPara.checkP=1;
                    System.out.println("在第"+num/12.0+"h超压,超压点位置为"+x*0.5+"km,压力为"+varPara.dPL[kkk][x]/1.0e6+"MPa");
                }
            }


//            if (varPara.Lt>waterHeadLocation[kkk]){
//                varPara.num=kkk;
//                break;
//            }
        //}

    }

    public double [][]line(double []l){
        double [][]line_l=new double[varPara.getI()][varPara.getI()];
       int j=0;
       line_l[1][1]=l[0];
       line_l[varPara.getI()-1][varPara.getI()-1]=l[l.length-1];

        for (int i=0;i<l.length-1;i++){
            line_l[j+1][2]=l[i+1];
            line_l[j+1][3]=l[i+2];

            if(j+2<varPara.getI())
            {
                line_l[j+2][1]=l[i+2];
            }
            i++;
            j++;
        }

        return line_l;
    }


    //getHb即求Hb的方法；输入x是清管器位置，y是清管器速度,dv是速度变化量,dt是时步,s是水头位置（气液界面位置）,Pg是气相压力
    double getHb(double x,double y,double dv,double dt,double s,double Pg){
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        double Hb;
        double hf;
        double xishu=1;
        double hl=getZ(varPara.line_l,varPara.line_d,s); ;          //****水头位置对应的高程
        double hg=getZ(varPara.line_l,varPara.line_d,x); ;          //清管器位置对应的高程

        //水力光滑区的液塞项摩阻，即清管器前的液塞摩阻
        hf=0.002174*Math.PI* oil.density*conPara.g*Math.pow(y,1.75)*Math.pow(oil.viscosity, 0.25)*(s-x)/Math.pow(A,1.25);
        Hb= oil.density*conPara.g*(hl-hg)+hf+Pg+xishu*conPara.g*dv*dt;
        return Hb;
    }
    //getHa，即求清管器上游纯液相区的压力Ha的方法；x是清管器位置，y是发球位置，dv是速度变化量；z是清管器速度,p是泵站的能头压力
    double getHa(double x,double y,double z,double dv,double dt,double p){
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        double h0=getZ(varPara.line_l,varPara.line_d,y);          //****位置对应的高程
        double hp=getZ(varPara.line_l,varPara.line_d,x);            //清管器位置对应的高程
        double Ha;
        double hf;
        double l=x-y;
        double xishu=1;//惯性项系数

        hf= 0.002174*Math.PI*oil.density*conPara.g*Math.pow(z, 1.75)*Math.pow(oil.viscosity, 0.25)*l/Math.pow(A, 1.25);

        Ha=p- oil.density*conPara.g*(hp-h0)-hf-xishu*conPara.g*dv*dt;           //大气压

        return Ha;
    }

    //shuntai为清管器的瞬态计算函数，x是上一时刻清管器后的压力，y是上一时刻清管器前的压力，z是上一时刻清管器的速度，a是清管器的位置，s是气液界面的位置
    //Pg是清管器后气段压力，p为投球位置提供的能头，f发球位置,phi管道夹角
    double[] getPigV(double x,double y,double z,double a,double s,double Pg,double p,double f,double phi){

        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Pig pig =pigs.get(0);
        double[] fanhui=new double[3];
        double vv;      //速度变化值
        double vpig;    //当前清管器速度
        double wucha;   //假设值与实际值的误差
        if((x-y<=20000*z*z)&&(x-y>=-20000*z*z)){//压力差在20kPa内时（清管器与管壁的静摩擦作用，清管器静止）

            if (z < conPara.Ql/A){
                fanhui[2]=conPara.Ql/A;
            }else if (z>5){
                fanhui[2]=5;
            }else{
                fanhui[2] = z-110*z*z/pig.pigM-varPara.deltaT*conPara.g*phi;
                if (fanhui[2] < conPara.Ql/A)
                    fanhui[2]=conPara.Ql/A;
                //fanhui[2] = z+300*(conPara.g*phi*varPara.deltaT);
            }
            vv=fanhui[2]-z;     //速度差为现速度与上一时刻速度之差
            fanhui[0]=getHb(a,fanhui[2],vv,varPara.deltaT,s,Pg);    //计算清管器下游压力
            fanhui[1]=getHa(a,f,fanhui[2],vv,varPara.deltaT,p);     //计算清管器上游压力
        }else{

                if (z < conPara.Ql/A && z >=0){
                    fanhui[2]=conPara.Ql/A;
                    if (fanhui[2] < conPara.Ql/A)
                        fanhui[2]=conPara.Ql/A;
                }else if (z>5){
                    fanhui[2]=5;
                }else{
                    //vpig=z+varPara.deltaT*(A*(x-y-2000))/pig.pigM+conPara.g*phi*varPara.deltaT;//清管器的质量取700kg
                    vpig=z+varPara.deltaT*(A*(x-y-20000*z*z))/pig.pigM-varPara.deltaT*conPara.g*phi;//清管器的质量取700kg
                    fanhui[2]=vpig;
                }

        }
        return fanhui;//fanhui 2是清管器当前速度，1清管器上游（a区）压力,0是清管器下游（b区）压力
    }

    /**
     * 对压力数组进行插值
     * @param arr 原压力数组 500m一个点
     * @param num 插值点数  1m，，499
     * @return 新压力数组
     */
    public double []chazhi(double []arr,int num){
        double []arrL = new double[((arr.length-1)*(num-1))];
        arrL[1]=arr[1];
        for (int i=2;i<arr.length;i++){
            if (arr[i]!=0) {
                arrL[(i-1)*(num+1)] = arr[i];
                for (int numi = 1; numi < num; numi++) {
                    arrL[(i-2)*(num+1) + 1 + numi] = arr[i - 1] + numi * (arr[i] - arr[i - 1]) / 500.0;
                }
            }else{
                //System.out.println("第"+i+"段");
            }
        }
        return arrL;
    }

    /**
     * 获取某点高程,单位全部是m
     * @param arrL  里程数组，三点式
     * @param arrZ  高程数组，三点式
     * @param l 需要高程的位置
     * @return 高程值
     */
    public double getZ(double [][]arrL,double [][]arrZ,double l){
        double z=0;
        for(int i=1;i<arrL.length;i++){
            if(l<arrL[i][2] && l>=arrL[i][1]){
                z=arrZ[i][1]+(l-arrL[i][1])*((arrZ[i][2]-arrZ[i][1])/(arrL[i][2]-arrL[i][1]));
            }else if(l<arrL[i][3] && l>=arrL[i][2]){
                z=arrZ[i][2]+(l-arrL[i][2])*((arrZ[i][3]-arrZ[i][2])/(arrL[i][3]-arrL[i][2]));
            }
        }
        if (z == 0) System.out.println("获取高程失败");
        return z;
    }
    /**
     * 获取某点所处段的角度,单位全部是m
     * @param arrL  里程数组，三点式
     * @param arrZ  高程数组，三点式
     * @param l 需要角度的位置
     * @return 高程差除以里程差，可作为tanφ或sinφ直接使用
     */
    public double getPhi(double [][]arrL,double [][]arrZ,double l){
        double Phi=0;
        for(int i=1;i<arrL.length;i++){
            if(l<arrL[i][2] && l>=arrL[i][1]){
                Phi=((arrZ[i][2]-arrZ[i][1])/(arrL[i][2]-arrL[i][1]));
            }else if(l<arrL[i][3] && l>=arrL[i][2]) {
                Phi = ((arrZ[i][3] - arrZ[i][2]) / (arrL[i][3] - arrL[i][2]));
            }
        }
        if (Phi == 0) System.out.println("获取角度失败");
        return Phi;
    }


    /**
     * 获得某里程所在的管段编号数
     * @param arrL
     * @param l
     * @return 管段编号，从1开始
     */
    public int getI(double [][]arrL,double l){
        int z=0;
        for(int i=1;i<arrL.length;i++){
            if(l<arrL[i][2] && l>=arrL[i][1]){
                z=i;
            }else if(l<arrL[i][3] && l>=arrL[i][2]){
                z=i;
            }
        }
        if (z == 0) System.out.println("获取段数失败");
        return z;
    }


    /**
     * 获取某里程L处的流型，5为满管流，1为分层流，2为气泡流，3为气泡,4为段塞,6为未赋值
     * @param i L点所处管段数
     * @param L 里程，水头位置吧
     * @param pigL
     * @param flagpig 清管器投放标志，1为已投放
     * @return
     */
    public double getFlowPattern(int i,double L,double pigStart,double pigL,double flagpig){
        double p=6;  //流型，初始未赋值时取6

        if (varPara.line_l[i][1]<=L && varPara.line_l[i][2]>L){

            if (L-varPara.line_l[i][1]<varPara.lgk[i]){
                p=1;
            }else{
                p=2;
            }
        }else if (varPara.line_l[i][2]+0.5*(varPara.line_l[i][3]-varPara.line_l[i][2])<=L && varPara.line_l[i][3]>=L){
            p=4;
        }else if (varPara.line_l[i][2]<=L && varPara.line_l[i][2]+0.5*(varPara.line_l[i][3]-varPara.line_l[i][2])>=L){
            p=3;
        }
        if (L>pigStart && pigL > L && flagpig!=0){//清管器越过计算点后，流型为满管流
            p=5;
        }else{
            if (varPara.line_l[i][1]<=L && varPara.line_l[i][2]>L){

                if (L-varPara.line_l[i][1]<varPara.lgk[i]){
                    p=1;
                }else{
                    p=2;
                }
            }else if (varPara.line_l[i][2]<=L && varPara.line_l[i][3]>=L){
                p=3;
            }
        }

        return p;
    }



    //泵站的特性方程

    /**
     * 泵站方法
     * @param way 串、并联
     * @param n 启泵数
     * @return 泵站总的能头
     */
    public double pumpstation(int way, int n) {
        Pump pump = pumps.get(0);
        double h;
        double a[];
        pump.initPumpFunction();
        a = pump.getHead();
        //流量转化为m³/小时
        double Q_h = conPara.Ql * 3600;
        if (way == 1) {
            h = n * (a[0] + a[1] * Q_h + a[2] * Math.pow(Q_h, 2));
        } else {
            h = a[0] + a[1] / n * Q_h + a[2] / (Math.pow(n, 2)) * Math.pow(Q_h, 2);
        }
        return h;
    }

    //单位流量的水力坡降,稳态水力坡降为一个常数,局部损失按1%计算
    public double[] frictionLoss(double re) {
        Oil oil = oils.get(0);
        double a[] = new double[2];
        double m;
        double d = (pipeLine.getDiameter() - 2 * pipeLine.getThinkness());
        double D = (pipeLine.getDiameter() - 2 * pipeLine.getThinkness()) * Math.pow(10, -3);
        double re1 = 59.7 / Math.pow((2 * pipeLine.getRoughness() / d), (8 / 7d));//临界雷诺数
        double re2 = (665 - 765 * Math.log10(2 * pipeLine.getRoughness() / d)) / (2 * pipeLine.getRoughness() / d);
        double f;
        if (re <= 2000) {
            f = 1.01 * 4.15 * Math.pow((oil.getViscosity() / oil.getDensity()), 1) / Math.pow(D, (5 - 1)) / Math.pow(3600, 2 - 1);
            m = 1;
            a[0] = f;
            a[1] = m;
        } else if (2000 < re && re <= re1) {
            f = 1.01 * 0.0246 * Math.pow((oil.getViscosity() / oil.getDensity()), 0.25) / Math.pow(D, (5 - 0.25)) / Math.pow(3600, 2 - 0.25);
            m = 0.25;
            a[0] = f;
            a[1] = m;
        } else if (re1 < re && re <= re2) {
            double A = Math.pow(10, (0.127 * Math.log10(2 * pipeLine.getRoughness() / d) - 0.627));
            f = 1.01 * 0.0802 * A * Math.pow((oil.getViscosity() / oil.getDensity()), 0.123) / Math.pow(D, (5 - 0.123)) / Math.pow(3600, 2 - 0.123);
            m = 0.123;
            a[0] = f;
            a[1] = m;
        } else {
            double lanmda = 0.11 * Math.pow((pipeLine.getRoughness() / D), 0.25);
            f = 1.01 * 0.0802 * lanmda / Math.pow(D, 5) / Math.pow(3600, 2);
            m = 0;
            a[0] = f;
            a[1] = m;
        }
        //System.out.println(m+"\t"+B);
        //System.out.println(f);
        return a;
    }


    /**
     * way这个泵站的串并联情况，n泵站台数,
     * m哪个泵站出问题
     * a1泵站停运方式(1/0)表示是否全泵站停运
     * 若泵站内各别泵停运，则b表示停运的台数
     **/
    public void pumustation_time(int way, int n, int m, int a1, int b1) {
        Pump pump = pumps.get(0);
        double h;
        double a[];
        pump.initPumpFunction();
        a = pump.getHead();
        //正常运转情况下泵的特性
        double Q_h = conPara.Ql * 3600;
        //首站
        if (m == 0) {
            //全泵站停运
            if (a1 == 1) {
                //串联
                if (way == 1) {

                }
            }
        }


    }


    //泵模块

    /**
     * 根据设计流量算出的压降配泵，启泵顺序的设计
     * @param num 时步数，与管段压降计算同步
     * @param dpL 某时步下的全线压降
     * @param waterL 水头位置
     */
    public void startPump(int num,double[] dpL,double waterL){
        int stationNow = 0; //需要优先启泵的当前泵站序号
        //double Lx=varPara.line_l[1][1];
        //for (int x=0;x<=(waterL-varPara.line_l[1][1])/500.0+1;x++)//遍历水头前的各点，500m一步
        //{


        if (num%10==0)  varPara.startPumpFlag=0;
            for (int stationNum=0;stationNum<stations.size();stationNum++){//查询当前点所处的站点位置
                if (stations.get(stationNum).getStationType()==1.0 && waterL >= varPara.stationLLL[stationNum] && waterL <= varPara.stationLLL[stationNum+1]){
                    //采用就近启泵原则，优先启离水头近的泵站的泵
                    stationNow=stationNum;
//                    for (int iu=0;iu<varPara.Hpump.length;iu++){
//                        varPara.Hpump[iu]=0.0;
//                    }

                }
            }


            //if (varPara.startPumpFlag[num-1]==0){//判断是否已有启泵动作



            if (dpL[(int)((waterL-varPara.line_l[1][1])/500.0)] < 500000  && varPara.startPumpFlag ==0) {         //计算点处压头小于0.5MPa，需要启泵
                for (int pumpNum=0;pumpNum<getStations().get(stationNow).getPumps().size();pumpNum++){          //遍历当前站点的所有泵
                    if (getStations().get(stationNow).getPumps().get(pumpNum).getPumpType()==2 && varPara.startPumpFlag1==0){        //存在变频泵且未完全打开
                        getStations().get(stationNow).getPumps().get(pumpNum).setState(1);            //打开变频泵
                        varPara.startPumpFlag=1;
                        //PumpRev = getStations().get(stationNow).getPumps().get(pumpNum).getRev();     //获取变频泵额定转速
                        varPara.PumpRev = 0.05+varPara.PumpRev;
                        if (varPara.PumpRev >1.2){
                            varPara.startPumpFlag1=1;
                        }
                        getStations().get(stationNow).getPumps().get(pumpNum).initPumpFunction();
                        varPara.a[pumpNum]=getStations().get(stationNow).getPumps().get(pumpNum).getHead();
                        varPara.Hpump[stationNow]=varPara.Hpump[stationNow]+(varPara.a[pumpNum][0]*pow(varPara.PumpRev,2) + varPara.a[pumpNum][1]*pow(varPara.PumpRev,2) * conPara.Ql*3600 + varPara.a[pumpNum][2] *pow(varPara.PumpRev,2)* Math.pow(conPara.Ql*3600, 2))
                                -(varPara.a[pumpNum][0]*pow(varPara.PumpRev-0.05,2) + varPara.a[pumpNum][1]*pow(varPara.PumpRev-0.05,2) * conPara.Ql*3600 + varPara.a[pumpNum][2] *pow(varPara.PumpRev-0.05,2)* Math.pow(conPara.Ql*3600, 2));
                        //dpL[(int)(varPara.stationLLL[stationNow]/0.5)]=varPara.Hpump[stationNow]*oils.get(0).getDensity()*conPara.g+dpL[(int)(varPara.stationLLL[stationNow]/0.5)];
                        //varPara.Hs=dpL[(int)(station.getStationL()[stationNow]/0.5)];
                        //varPara.Hd[stationNow]=dpL[(int)(station.getStationL()[stationNow]/0.5)];
                        varPara.Hd[stationNow]=varPara.Hpump[stationNow]*oils.get(0).getDensity()*conPara.g;
                        System.out.println("在"+num/12.0+"h,启"+(stationNow+1)+"号泵站的"+(pumpNum+1)+"号泵,"+ (int)(varPara.PumpRev/0.01)+"%转速");
                        break;
                    }else if(getStations().get(stationNow).getPumps().get(pumpNum).getPumpType()==1 && getStations().get(stationNow).getPumps().get(pumpNum).getState()==0){        //存在主泵且未打开
                        getStations().get(stationNow).getPumps().get(pumpNum).setState(1);      //打开泵
                        varPara.startPumpFlag=1;
                        getStations().get(stationNow).getPumps().get(pumpNum).initPumpFunction();
                        varPara.a[pumpNum]=getStations().get(stationNow).getPumps().get(pumpNum).getHead();
                        varPara.Hpump[stationNow]=varPara.Hpump[stationNow]+(varPara.a[pumpNum][0] + varPara.a[pumpNum][1] * conPara.Ql*3600 + varPara.a[pumpNum][2] * Math.pow(conPara.Ql*3600, 2));
                        //dpL[(int)(varPara.stationLLL[stationNow]/0.5)]=varPara.Hpump[stationNow]*oils.get(0).getDensity()*conPara.g+dpL[(int)(varPara.stationLLL[stationNow]/0.5)];
                        //varPara.Hd[stationNow]=dpL[(int)(varPara.stationLLL[stationNow]/0.5)];
                        varPara.Hd[stationNow]=varPara.Hpump[stationNow]*oils.get(0).getDensity()*conPara.g;
                        //varPara.Hs=dpL[(int)(station.getStationL()[stationNow]/0.5)];
                        System.out.println("在"+num/12.0+"h,启"+(stationNow+1)+"号泵站的"+(pumpNum+1)+"号泵");
                        break;
                    }else {
//
                    }
                }

//
            }
    }


    /**
     * 停泵的计算，水头前所有泵站全部泵状态归为0，同时，清零泵站的出站压力
     * @param waterL 水头位置
     * @param num 时步数
     */
    public void stopPump(int num, double waterL) {
        int stationNow = 0; //当前泵站序号
        for (int stationNum = 0; stationNum < stations.size(); stationNum++) {//查询当前点所处的站点位置
            if (getStations().get(stationNow).getStationType() == 1.0 && waterL >= varPara.stationLLL[stationNum]) {
                //采用依次停泵原则，从首站依次停泵
                stationNow = stationNum;
                if(varPara.stopT%2==0) varPara.stopPumpFlag=0;
                if (varPara.stopPumpFlag == 0) {         //停泵间隔标记
                    for (int pumpNum = 0; pumpNum < getStations().get(stationNow).getPumps().size(); pumpNum++) {          //遍历当前站点的所有泵
                        if (getStations().get(stationNow).getPumps().get(pumpNum).getState() == 1) {        //存在打开的泵
                            //getStations().get(stationNow).getPumps().get(pumpNum).setState(2);            //设置泵状态为半开，
                            //varPara.Hd[stationNow]=0.5*varPara.Hd[stationNow];
                            //varPara.Hpump[stationNow]=0.5*varPara.Hpump[stationNow];
                            System.out.println("在"+num/12.0+"h,开始关闭"+(stationNow+1)+"号泵站的"+(pumpNum+1)+"号泵");
                            varPara.stopPumpFlag = 2;
                        }
                    }
                }else if (varPara.stopPumpFlag == 2) {         //停泵间隔标记
                    for (int pumpNum = 0; pumpNum < getStations().get(stationNow).getPumps().size(); pumpNum++) {          //遍历当前站点的所有泵
                        if (getStations().get(stationNow).getPumps().get(pumpNum).getState() == 2) {        //存在打开的泵
                            //getStations().get(stationNow).getPumps().get(pumpNum).setState(0);            //关闭泵
                            //varPara.Hd[stationNow]=0;
                            //varPara.Hpump[stationNow]=0;
                            System.out.println("在"+((num+1)/12.0)+"h,完全关闭"+(stationNow+1)+"号泵站的"+(pumpNum+1)+"号泵");
                            //varPara.stopPumpFlag = 1;
                        }
                    }
                }
                //varPara.Hd[stationNow]=0;
            }
        }
    }


    public void staticP(int n, double waterL,double dx) {
        Oil oil= oils.get(0);
        int i = 0; //管段号
        double Lx = waterL; //累计里程
        varPara.allLineStaticP[0][(int)((waterL-varPara.line_l[1][1])/dx)]=conPara.p0;
        varPara.allLineStaticP[1][(int)((waterL-varPara.line_l[1][1])/dx)]=getZ(varPara.line_l, varPara.line_d,waterL);
        if (waterL >= varPara.line_l[n][2] && waterL <= varPara.line_l[n][3]) {//水头在上坡段
            //从水头至首站，依次计算静压
            for (int x=(int)((waterL-varPara.line_l[1][1])/dx);x>=1;x--)//遍历水头前的各点,从后往前
            {
                Lx=varPara.line_l[1][1]+x*dx;
                varPara.allLineStaticP[1][x]=getZ(varPara.line_l, varPara.line_d,Lx);
                i=getI(varPara.line_l, Lx);
                if (Lx >= varPara.line_l[i][2] && Lx <= varPara.line_l[i][3]) {//当前管段的上坡段
                    varPara.allLineStaticP[0][(int)((waterL-varPara.line_l[1][1])/dx)]=0.5*((Lx-varPara.line_l[i][2])*
                            varPara.slopeU[i][0]+(varPara.line_l[i][2]-varPara.line_l[i][1]-varPara.lgk[i])
                            *varPara.slopeD[i][0])*oil.getDensity()*conPara.g+conPara.p0;
                    varPara.allLineStaticP[0][x]=varPara.allLineStaticP[0][x+1]+dx*varPara.slopeU[i][0]*oil.getDensity()*conPara.g;
                }else if (Lx >= varPara.line_l[i][1] && Lx <= varPara.line_l[i][1]+varPara.lgk[i]){
                    varPara.allLineStaticP[0][x]=varPara.allLineStaticP[0][x+1]-dx*varPara.slopeD[i][0]*(1-varPara.Hgk[i])*oil.getDensity()*conPara.g;
                }else if (Lx >= varPara.line_l[i][1]+varPara.lgk[i] && Lx <= varPara.line_l[i][2]){
                    varPara.allLineStaticP[0][x]=varPara.allLineStaticP[0][x+1]-dx*varPara.slopeD[i][0]*oil.getDensity()*conPara.g;
                }
                if(varPara.allLineStaticP[0][x]<1705){      //水在15℃时的饱和蒸气压
                    varPara.allLineStaticP[0][x]=1705;
                }
            }
        }else if (waterL >= varPara.line_l[n][1] && waterL <= varPara.line_l[n][2]) {//水头在下坡段

            for (int x=(int)((waterL-varPara.line_l[1][1])/dx);x>=1;x--)//遍历水头前的各点,从后往前
            {
                Lx=varPara.line_l[1][1]+x*dx;
                i=getI(varPara.line_l, Lx);
                varPara.allLineStaticP[1][x]=getZ(varPara.line_l, varPara.line_d,Lx);
                if (Lx >= varPara.line_l[i][2] && Lx <= varPara.line_l[i][3]) {
                    varPara.allLineStaticP[0][x]=varPara.allLineStaticP[0][x+1]+dx*varPara.slopeU[i][0]*oil.getDensity()*conPara.g;
                }else if (Lx >= varPara.line_l[i][1] && Lx <= varPara.line_l[i][1]+varPara.lgk[i]){
                    varPara.allLineStaticP[0][x]=varPara.allLineStaticP[0][x+1]+dx*varPara.slopeD[i][0]*(1-varPara.Hgk[i])*oil.getDensity()*conPara.g;
                }else if (Lx >= varPara.line_l[i][1]+varPara.lgk[i] && Lx <= varPara.line_l[i][2]){
                    if (i==n) {
                        varPara.allLineStaticP[0][x]=conPara.p0;
                    }else{
                        varPara.allLineStaticP[0][x]=varPara.allLineStaticP[0][x+1]-dx*varPara.slopeD[i][0]*oil.getDensity()*conPara.g;
                    }
                }
                if(varPara.allLineStaticP[0][x]<1705){//水在15℃时的饱和蒸气压
                    varPara.allLineStaticP[0][x]=1705;
                }
            }
        }else{
            System.out.println("静压计算 水头位置判定异常");
        }
    }

    /**
     * 求排气的质量和排气后的气段压力、长度变化
     * @param i 管段数
     * @param lgk 当前管段气段长度
     * @param Pgk 下一段的积气段压力
     * @param h1 下坡段液位
     * @param h2 上坡段液位
     * @return 排气的相关参数，2为排气时间
     */

    public double [] exitGas(int i,int StationsNum,double Mg,double Pgk,double lgk,double h1,double h2){
        Oil oil = oils.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double []exitGas=new double[5];
        double []Ppaiqi=new double[varPara.k+1];
        double []Lpaiqi=new double[varPara.k+1];
        double []rhoV=new double[varPara.k+1];
        double []PCR=new double[varPara.k+1];
        double []H1=new double[varPara.k+1];
        double []H2=new double[varPara.k+1];
        double []zlll=new double[varPara.k+1];
        double []MG=new double[varPara.k+1];
        double []his=new double[varPara.k+1];

        double rhoe,ue,Mae,Te,deth,eTime=0.2,t_record=0,Flag=0;
        H1[0]=h1;
        H2[0]=h2;
        Lpaiqi[0]=lgk;
        MG[0]=Mg;
        Ppaiqi[0]=Pgk;

        for (int iter = 0; iter < varPara.k; iter++){

            rhoV[iter] = Ppaiqi[iter]*29/1000/conPara.T0/8.314;
            PCR[iter] = Ppaiqi[iter]*Math.pow((2/2.4),(1.4/0.4));

            if (PCR[iter] > conPara.p0 || PCR[iter] == conPara.p0){
                rhoe = rhoV[iter]*Math.pow((2/2.4),(1/0.4));
                ue = Math.sqrt((2*1.4/2.4*8.314*conPara.T0));
            }else{
                Mae = Math.pow((2/0.4*(Math.pow((Ppaiqi[iter]/conPara.p0),(0.4/1.4))-1)),0.5);
                Te = conPara.T0/(1+0.4/2*Math.pow(Mae,2));
                ue = Math.sqrt((1.4*8.314*Te))*Mae;
                rhoe = rhoV[iter]/Math.pow((1 + 0.4/2*Math.pow(Mae,2)),(1/0.4));
            }

            zlll[iter] = rhoe*ue*(Math.PI*Math.pow(stations.get(StationsNum).getValves().get(0).getValveD(),2)/4.0);
            MG[iter+1] = MG[iter] - zlll[iter]*30*varPara.deltaT;
            Ppaiqi[iter+1] = Ppaiqi[iter]-(Pgk*(zlll[iter]*30*varPara.deltaT)/Mg);

            Lpaiqi[iter+1] = Lpaiqi[iter]-(lgk*(zlll[iter]*30*varPara.deltaT)/Mg);
//
            if ((Ppaiqi[iter+1] - conPara.p0) < 1){

                System.out.println("varPara.Pgk["+i+"]="+Pgk);
                varPara.Pgk[i] = Ppaiqi[iter];
                System.out.println("varPara.Pgk["+i+"]="+varPara.Pgk[i]);
                System.out.println("varPara.lgk["+i+"]="+lgk);
                varPara.lgk[i] = Lpaiqi[iter+1];
                System.out.println("varPara.lgk["+i+"]="+varPara.lgk[i]);
//
                exitGas[2] = iter*30*eTime;
                System.out.println("第" + (i+1) + "个U型管下坡段高点第" + varPara.num/12.0 + "h开始排气" + exitGas[2] + "秒");
                varPara.ssr[i][0]=1;   //排气后加入破碎、压缩结束条件
                //t_record=t_record+t3[i];
                break;
            }



        }




        return exitGas;
    }

    public double valveCalDp1(double Q1,double D,double Cd,double x,double rho){
        double dP1;
        dP1=Math.pow((Q1/(Cd* PI*D*x)),2)*rho/2.0;
        return dP1;
    }

    /**
     * 小孔流动计算调压阀流量变化
     * @param dP1 原流量下的压降
     * @param dP2 现流量下的压降
     * @param Q1 原流量
     * @return 现流量
     */
    public double valveCal(double dP1,double dP2,double Q1){
        double Q2;
        Q2=Q1*(dP2)/(dP1);
        return Q2;
    }







    public Pipeline getPipeline() {
        return pipeLine;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeLine = pipeline;
    }


    public List<Pump> getPumps() {
        return pumps;
    }

    public void setPumps(List<Pump> pumps) {
        this.pumps = pumps;
    }

    public Conpara getConPara() {
        return conPara;
    }

    public void setConPara(Conpara conPara) {
        this.conPara = conPara;
    }

    public Varpara getVarPara() {
        return varPara;
    }

    public void setVarPara(Varpara varPara) {
        this.varPara = varPara;
    }

    public Pipeline getPipeLine() {
        return pipeLine;
    }

    public void setPipeLine(Pipeline pipeLine) {
        this.pipeLine = pipeLine;
    }


    public List<Oil> getOils() {
        return oils;
    }

    public void setOils(List<Oil> oils) {
        this.oils = oils;
    }

    public List<Pig> getPigs() {
        return pigs;
    }

    public void setPigs(List<Pig> pigs) {
        this.pigs = pigs;
    }

    public List<Valve> getValves() {
        return valves;
    }

    public void setValves(List<Valve> valves) {
        this.valves = valves;
    }

    public List<Stations> getStations() {
        return stations;
    }

    public void setStations(List<Stations> stations) {
        this.stations = stations;
    }

    public Messages getMsg() {
        return msg;
    }

    public void setMsg(Messages msg) {
        this.msg = msg;
    }
}



