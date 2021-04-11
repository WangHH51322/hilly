package cn.edu.cup.hilly.calculate.hilly.large;

import Jama.Matrix;
import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;
import cn.edu.cup.hilly.dataSource.model.mongo.outPut.DPL;
import cn.edu.cup.hilly.dataSource.model.mongo.outPut.PgHis;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.TempTest;
import cn.edu.cup.hilly.dataSource.service.mongo.ResultDPLService;
import cn.edu.cup.hilly.dataSource.service.mongo.TempTestSer;
import cn.edu.cup.hilly.dataSource.utils.SizeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static java.lang.Math.*;

@Service
@IOElement(name = "hillyProject")
public class Project extends Thread implements Serializable {
    Conpara conPara;

    @InputField(name = "variableParameter", unit = "")
    static Varpara varPara;

    @InputField(name = "pipe", unit = "")
    Pipeline pipeLine;

    @InputField(name = "siteInfo", unit = "")
    Station station;

    @InputField(name = "mediumList", unit = "")
    List<Oil> oils;

    @InputField(name = "pigList", unit = "")
    static List<Pig> pigs;

    private String projectId;
    public String getProjectId() {
        return projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * Temp Data
     */
    @Autowired
    TempTestSer tempService;
    @Autowired
    ResultDPLService resultDPLService;
    /**
     * 线程暂停
     */
    private static boolean pause = false;
    private static boolean release = false;
    private static Object lock = new Object();

    /**
     * 调用该方法实现线程的暂停
     */
    public static Varpara pauseThread(){
        pause = true;
        return varPara;
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

    private List<PgHis> Pg_his;
    public List<PgHis> getPg_his() {
        return Pg_his;
    }
    public void setPg_his(List<PgHis> pg_his) {
        Pg_his = pg_his;
    }

    private List<DPL> dpl;
    public List<DPL> getDpl() {
        return dpl;
    }
    public void setDpl(List<DPL> dpl) {
        this.dpl = dpl;
    }

    public void run() {

        Oil oil = oils.get(0);

        varPara.setArr();       //通过get方法用i，k给varPara中的数组赋值

        conPara.D=pipeLine.getDiameter()-2*pipeLine.getThinkness();//内径
        conPara.r=conPara.D/2;//
        conPara.A=Math.PI*conPara.D*conPara.D/4.0;//截面积

        conPara.J= 0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(conPara.D,4.75);
        conPara.F = conPara.A*oil.getDensity()*conPara.g*conPara.J;

        double t_all,dt=0.2;
        int tt=0,rr=0,pp=0,ff=0,ttt=0;      //时步切换标准
        varPara.num=0;
        double ssr=0,ssr1=0,ssr2=0,ssr3=0;  //累计压降
        double wL=0;  //水头尾部处理
        double AddMg[]=new double[varPara.getI()];  //清管器导致的附加压降
        double AddMg_his[][]=new double[varPara.getI()][varPara.getK()];  //清管器导致的附加压降

        t_all= 0;      //总运行时间，单位是秒，从零开始
        double LLt;//压降计算里程标记
        double flagFP;//流型计算里程标记

        LLt=varPara.line_l[1][1]+500;

        int i,n=1,num0,num1=1,num2,flagpigT=0; //i为水头所在段，n为当前计算管段编号
        double [][] lz;
        double Ha,Hb;


        lz = ExcelData.Graphic();       //运用地形简化程序得到的三点式地形
        //加入固定地形点位

        for (num2 = 1; num2 < 104; num2++) {     //原管道地形的所有管段
            for (int b=0;b<4;b++){
                varPara.line_lll[num2][b] = lz[num2][b];
                varPara.line_ddd[num2][b] = lz[num2][b+4];
            }
        }
        for (int num=1;num<=10;num++) {      //插入点数量    、、、、、、、、、、、、插入点限制，两点间距不小于10km

            for (num0 = num1; num0 < 104; num0++) {     //原管道地形的所有管段
                if (lz[num0][1] < 1000 * station.getStationL()[num] && lz[num0][2] > 1000 * station.getStationL()[num]) {//插入点在下坡段
                    num0=num0+num-1;
                    varPara.line_lll[num0][1] = lz[num0-num+1][1];
                    varPara.line_lll[num0][2] = station.getStationL()[num] * 1000;
                    varPara.line_lll[num0][3] = station.getStationL()[num] * 1000 + 500;

                    varPara.line_lll[num0 + 1][1] = station.getStationL()[num] * 1000 + 500;

                    varPara.line_lll[num0 + 1][2] = lz[num0-num+1][2];
                    if (varPara.line_lll[num0 + 1][1]>varPara.line_lll[num0 + 1][2])
                    {
                        varPara.line_lll[num0 + 1][2]=varPara.line_lll[num0 + 1][2]+500;
                        System.out.println("111111"+(num0+1));
                    }

                    varPara.line_lll[num0 + 1][3] = lz[num0-num+1][3];


                    varPara.line_ddd[num0][1] = lz[num0-num+1][5];
                    varPara.line_ddd[num0][2] = station.getStationZ()[num];

                    if (varPara.line_ddd[num0][1]<varPara.line_ddd[num0][2])
                    {
                        varPara.line_ddd[num0][1]=varPara.line_ddd[num0][2]+50;
                        varPara.line_ddd[num0-1][3]=varPara.line_ddd[num0][2]+50;
                        System.out.println("22222  "+(num0));
                    }

                    varPara.line_ddd[num0][3] = station.getStationZ()[num] + 50;
                    varPara.line_ddd[num0 + 1][1] = station.getStationZ()[num] + 50;
                    varPara.line_ddd[num0 + 1][2] = lz[num0-num+1][6];

                    varPara.line_ddd[num0 + 1][3] = lz[num0-num+1][7];
                    num1=num0;
                    for (num2 = num0+2; num2 < 114; num2++) {     //原管道地形的所有管段
                        for (int b=0;b<4;b++){
                            if (num2-num<104){
                                varPara.line_lll[num2][b] = lz[num2-num][b];
                                varPara.line_ddd[num2][b] = lz[num2-num][b+4];
                            }
                        }
                    }

                    break;
                } else if (lz[num0][3] > 1000 * station.getStationL()[num] && lz[num0][2] < 1000 * station.getStationL()[num]) {//插入点在上坡段
                    num0=num0+num-1;
                    varPara.line_lll[num0][1] = lz[num0-num+1][1];
                    varPara.line_lll[num0][2] = lz[num0-num+1][2];
                    varPara.line_lll[num0][3] = station.getStationL()[num] * 1000;

                    varPara.line_lll[num0 + 1][1] = station.getStationL()[num] * 1000;
                    varPara.line_lll[num0 + 1][2] = station.getStationL()[num] * 1000 + 500;
                    varPara.line_lll[num0 + 1][3] = lz[num0-num+1][3];

                    varPara.line_ddd[num0][1] = lz[num0-num+1][5];
                    varPara.line_ddd[num0][2] = lz[num0-num+1][6];
                    varPara.line_ddd[num0][3] = station.getStationZ()[num];

                    varPara.line_ddd[num0 + 1][1] = station.getStationZ()[num];
                    varPara.line_ddd[num0 + 1][2] = station.getStationZ()[num] - 50;
                    varPara.line_ddd[num0 + 1][3] = lz[num0-num+1][7];
                    num1=num0;
                    for (num2 = num0+2; num2 < 114; num2++) {     //原管道地形的所有管段
                        for (int b=0;b<4;b++){
                            if (num2-num<104){
                                varPara.line_lll[num2][b] = lz[num2-num][b];
                                varPara.line_ddd[num2][b] = lz[num2-num][b+4];
                            }
                        }
                    }
                    break;
                } else if (lz[num0][1] == 1000 * station.getStationL()[num] || lz[num0][2] == 1000 * station.getStationL()[num] || lz[num0][3] == 1000 * station.getStationL()[num]) {//插入点与原点重合
                    System.out.println("里程高程插入异常！");
                }
            }
        }

        varPara.line_lll[45][2]=271000;
        varPara.line_ddd[45][2]=1800;

        System.out.println("里程数组：");
        for (int ii=0;ii<varPara.line_lll.length;ii++){
            System.out.print("第"+ii+"个管段"+"\t");
            for (int jj=0;jj<4;jj++){
                System.out.print(varPara.line_lll[ii][jj]+"\t");
            }
            System.out.println();
        }
        System.out.println("高程数组：");
        for (int ii=0;ii<varPara.line_ddd.length;ii++){
            System.out.print("第"+ii+"个管段"+"\t");
            for (int jj=0;jj<4;jj++){
                System.out.print(varPara.line_ddd[ii][jj]+"\t");
            }
            System.out.println();
        }
        for (int a=60;a< 71;a++){          ////////////控制地形里程在395-640km  i==64  Q=1660
        //for (int a=61;a< 108;a++){          ////////////控制地形里程在397-608km   i=48  Q=0.483333
            System.out.print("第"+(a-59)+"管段"+"\t");
            for (int b=0;b<4;b++){
                varPara.line_l[a-59][b]=varPara.line_lll[a][b];
                System.out.print(varPara.line_l[a-59][b]+"\t");


                varPara.line_d[a-59][b]=varPara.line_ddd[a][b];
                System.out.print(varPara.line_d[a-59][b]+"\t");

            }
            System.out.println();
        }

        flagFP = varPara.line_l[1][1]+500;
        System.out.println("varPara.line_d[1][1]="+varPara.line_d[1][1]);

        varPara.slopeD = calSlopeD(varPara.line_d, varPara.line_l);         //计算下坡角度
        varPara.slopeU = calSlopeU(varPara.line_d, varPara.line_l);         //计算上坡角度
        for (i = 1; i < varPara.i; i++) {

            varPara.delta[i] = calDelta(varPara.slopeD[i][0]);      //下坡段的初始液相圆心角计算，明渠流动
            varPara.Al[i] = Math.pow(conPara.r, 2) * (varPara.delta[i] - 0.5 * Math.sin(2 * varPara.delta[i]));    //液相的流动截面积
            varPara.Sl[i] = 2 * varPara.delta[i] * conPara.r;                                                //液相湿周
            varPara.vll[i] = 1.49 / conPara.n * Math.pow((varPara.Al[i] / varPara.Sl[i]), (2.0 / 3.0)) * Math.pow(varPara.slopeD[i][0], 0.5);      //液相流速计算
            varPara.t2[i] = (varPara.line_l[i][2] - varPara.line_l[i][1]) / varPara.vll[i];        //明渠流动下坡时间，也就是液相从高点到低点的时间
            varPara.gasRatio[i] = (Math.PI * Math.pow(conPara.r, 2) - varPara.Al[i]) / (Math.PI * Math.pow(conPara.r, 2));      //初始截面含气率计算

            varPara.V0[i] = Math.PI * Math.pow(conPara.r, 2) * (varPara.line_l[i][2] - varPara.line_l[i][1]) * varPara.gasRatio[i];      //初始积气段的气体体积
            varPara.M0[i] = varPara.V0[i] * 1.205;        //初始积气段的气体质量，密度取1.205kg/m3
            varPara.h1k[i] = 2 * conPara.r;               //下坡段初始液位高度---------距低点的高差
            varPara.h2k[i] = 2 * conPara.r;               //上坡段初始液位高度
            varPara.Pgk[i] = conPara.p0;                //初始气相压力，大气压
            varPara.backPressure[i] = conPara.p0;                //初始气相压力，大气压,水头所在管段处的背压
            varPara.Hgk[i] = varPara.gasRatio[i];       //初始截面含气率
            varPara.uk[i] = conPara.Ql / (2 * conPara.A);           //水头在上坡段的前进速度
            varPara.lgk[i] = varPara.line_l[i][2] - varPara.line_l[i][1]; //初始气段长度，即下坡段长度
            varPara.MGk[i] = varPara.M0[i];              //下坡段的初始气体质量
            //System.out.println(varpara.Hgk[i]);
        }
        for (int jjj=9;jjj<9+1;jjj++){
            System.out.println("delta["+jjj+"]="+varPara.delta[jjj]);
            System.out.println("vll["+jjj+"]="+varPara.vll[jjj]);
            System.out.println("gasRatio["+jjj+"]="+varPara.gasRatio[jjj]);
            System.out.println("lgk["+jjj+"]="+varPara.lgk[jjj]);
        }

        varPara.waterHeadLocation[0]=varPara.line_l[1][1];   //水头初始位置等于首个下坡段低点

        System.out.println("总模拟长度："+(varPara.line_l[varPara.i-1][3]-varPara.line_l[1][1])/1000+"km");

        cal_l0();//计算各管段的气段尾部估计位置，也计算了满管流动的水力坡降

        varPara.Time[0] = t_all;       //时步与时间的整合对应

        //1、从第一个U型管段的低点开始进行时间循环
        long start1,end1,end3;

        start1 = System.currentTimeMillis();        //开始计算的时间标记

        /**
         * 循环开始
         */
        System.out.println("开始循环!!!");
        super.run();
        int kk = 0;
        int count = varPara.kt + 1;
        while(true){
            while (pause){
                onPause();
            }
            if (release) {
                break;
            }
            try {
                kk ++;
                if (kk == count) {
                    break;
                }
                    for (i = 1; i <= n; i++) {//每当水头越过高点则n加一，i为当前计算段的编号
                        //破碎的计算，运行得到一个时步的破碎结果,返回修正后的Lg、Pg、Hg的值
                        if (varPara.ssr[i][0] != 1) {     //20201105加入后半个判定条件，让破碎结束后不再运行，前半个条件是预设的破碎开始情况
                            if (varPara.Pgk[i] < 5e6) {
                                YaSuo(i, varPara.lgk[i], varPara.Pgk[i], varPara.Hgk[i], varPara.slopeU[i][0],
                                        varPara.slopeD[i][0], varPara.uk[i], varPara.h1k[i], varPara.h2k[i], varPara.backPressure[i],
                                        varPara.dMg_in[i] - varPara.dMg_out[i] - AddMg[i]);
                            } else {
                                varPara.h2k[i] = varPara.h2_his[i][(kk) / 300 - 1] + conPara.Ql / conPara.A;
                            }
                            varPara.Dengk[i] = 1.205 / 1.01e5 * varPara.Pgk[i];
                            try {
                                dtPosui(kk, i, varPara.l0[i], varPara.line_l[i][1], varPara.line_d[i][1], varPara.line_l[i][2], varPara.line_d[i][2],
                                        conPara.Ql, conPara.D, conPara.A, 1.205, varPara.MGk[i] - AddMg[i], varPara.Pgk[i], varPara.lgk[i],
                                        varPara.Dengk[i], varPara.Hgk[i], varPara.MM, varPara.Pgk, varPara.lgk, varPara.Dengk, varPara.Hgk,
                                        oil.density, 1e-6);//
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        //计算单位管长下的分层流（只有下坡有分层流）压降，带重位压差
                        varPara.Hfk_f[i] = dpf_cal(-varPara.slopeD[i][0], varPara.Hgk[i], conPara.D, oil.density, 1.205, conPara.Ql / conPara.A, varPara.f_l[i], 0.0143, varPara.Sl[i]);
                        //计算单位管长下的下坡段分层流压降，带重位压差
                        varPara.Hfk_b[i] = dPb_cal(conPara.A, -varPara.slopeD[i][0], varPara.Hgbk[i], conPara.D, oil.density, 1.205, 0.001 * oil.getViscosity(), conPara.Ql / conPara.A, conPara.Ql / conPara.A, conPara.Ql / conPara.A, 0.0001);
                        varPara.Hfk_dU[i] = dPd_cal(varPara.slopeU[i][0], varPara.Hgbk[i], conPara.Ql, conPara.A, conPara.D, oil.density, conPara.Ql / conPara.A, conPara.Ql / conPara.A, 0.0001);
                        //计算单位管长下的上坡段分层流压降，带重位压差
                        varPara.Hfk_bU[i] = dPbU_cal(conPara.A, varPara.slopeU[i][0], varPara.Hgbk[i], conPara.D, oil.density, 1.205, 0.001 * oil.getViscosity(), conPara.Ql / conPara.A, conPara.Ql / conPara.A, conPara.Ql / conPara.A, 0.0001);
                        //某管段在当前时刻的总压降
                        varPara.Hfk[i] = dP_cal(varPara.vll[i], varPara.vll[i], varPara.Hgbk[i], 3, conPara.A, oil.getDensity(), 1.205, varPara.Hfk_b[i], varPara.Hfk_bU[i], varPara.Hfk_f[i], varPara.lg_f[i][kk / 300], varPara.lp_b[i][kk / 300], varPara.lp_bU[i][kk / 300])[3];
                        varPara.Hfk_jj[i] = dP_cal(varPara.vll[i], varPara.vll[i], varPara.Hgbk[i], 3, conPara.A, oil.getDensity(), 1.205, varPara.Hfk_b[i], varPara.Hfk_bU[i], varPara.Hfk_f[i], varPara.lg_f[i][kk / 300], varPara.lp_b[i][kk / 300], varPara.lp_bU[i][kk / 300])[2];
                        //记录参数，背压向上游传递，气量向下游传递
                        FlowConversion(kk, i);

                        if (varPara.ssr[i][0] == 1) {
                            varPara.Pgk[i] = varPara.Pg_his[i][(kk) / 300 - 1];
                            varPara.lgk[i] = varPara.lg_his[i][(kk) / 300 - 1];
                            varPara.h1k[i] = varPara.line_d[i][1] - varPara.line_d[i][2];

                            varPara.h2k[i] = varPara.h2_his[i][(kk) / 300 - 1] + conPara.Ql / conPara.A;
                            varPara.dMg_out[i] = 0.00;            //净流出为零
                            varPara.MGk[i] = varPara.M_his[i][(kk) / 300 - 1];                         //气体质量为零
                        }
                        if (i > 1 && varPara.ssr[i][0] == 0) {
                            varPara.backPressure[i - 1] = varPara.Pgk[i];         //开始封闭后才有这个关系，要考虑明渠流动的时间
                            varPara.dMg_in[i] = varPara.dMg_out[i - 1];           //上游段的净流出等于下游段的净流入
                        }
                        //判定过高点后，加一个运行的管段。
                        if (varPara.h2k[n] > (varPara.line_d[n][3] - varPara.line_d[n][2])) {//水头进入下一个管段

                            for (i = 1; i <= n; i++) {

                                varPara.h2k[i] = varPara.line_d[i][3] - varPara.line_d[i][2];
                            }
                            if (pp == 0) {
                                if (n < varPara.i - 1) {
                                    System.out.println("水头在第" + 0.2 * kk / 3600.0 + "h到达第" + (n) + "个U型管高点");
//                                    TempTest temp = new TempTest();
//                                    temp.setLocation("the" + n + "pipe");
//                                    temp.setHis(varPara.Pg_his);
//                                    tempService.add(temp);
                                    n++;
                                } else if (n == varPara.i - 1) {
                                    System.out.println("水头在第" + 0.2 * kk / 3600.0 + "h到达第" + (n) + "个U型管高点");
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
                                ssr = varPara.Hfk[kkk] + ssr;
                                ssr1 = varPara.Hfk_bb[kkk] + ssr1;
                                ssr2 = varPara.Hfk_ff[kkk] + ssr2;
                                ssr3 = varPara.Hfk_jj[kkk] + ssr3;
                                if (n > 1 && varPara.ssr[kkk][0] == 1) {
                                    varPara.lp_b[kkk][rr] = varPara.line_l[kkk][2] - varPara.line_l[kkk][1];             //当前段破碎结束后，下坡段全按气泡流计算
                                    varPara.lp_bU[kkk][rr] = varPara.line_l[kkk][3] - varPara.line_l[kkk][2];             //当前段破碎结束后，上坡段全按气泡流计算
                                    varPara.lg_f[kkk][rr] = 0;                                  //当前段破碎结束后，全按气泡流计算，分层流长度为零
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

                            if (varPara.waterHeadLocation[rr - 1] < varPara.line_l[n][2] && varPara.waterHeadLocation[rr - 1] != 0.0)                       //到达某段低点前
                            {
                                varPara.waterHeadLocation[rr] = varPara.waterHeadLocation[rr - 1] + dt * varPara.vll[n] * 300;        //用明渠流流速计算水头位置
                                varPara.lg_f[n][rr] = varPara.lg_f[n][rr - 1] + dt * varPara.vll[n] * 300;        //用明渠流流速计算分层流长度
                                varPara.lp_b[n][rr] = 0;        //水头抵达低点前，下坡段气团气泡流长度为零
                                varPara.lp_bU[n][rr] = 0;       //水头抵达低点前，上坡段气团气泡流长度为零
                            } else if (varPara.waterHeadLocation[rr - 1] > varPara.line_l[n][2] && varPara.waterHeadLocation[rr - 1] <= varPara.line_l[n][3]) {     //低点到首段翻越点之间
                                varPara.waterHeadLocation[rr] = varPara.line_l[n][2] + varPara.h2k[n] / varPara.slopeU[n][0];                      //用压缩模型的上坡段液位高度计算水头位置
                                //wL=varPara.waterHeadLocation[rr];
                                varPara.lg_f[n][rr] = varPara.lgk[n];         //开始压缩后，等于压缩期间的实时长度
                                varPara.lp_b[n][rr] = varPara.line_l[n][2] - varPara.line_l[n][1] - varPara.lgk[n];        //下坡段气团气泡流长度等于下坡段分层流以外的长度
                                varPara.lp_bU[n][rr] = varPara.h2k[n] / varPara.slopeU[n][0];                         //上坡段水头处的长度
                            }
                            if (varPara.waterHeadLocation[rr - 1] >= varPara.line_l[varPara.i - 1][3] - 100) {
                                varPara.waterHeadLocation[rr] = varPara.line_l[varPara.i - 1][3];
                                System.out.println("ces");
                            }

                            varPara.allLine[1][rr] = varPara.waterHeadLocation[rr];       //水头位置
                            varPara.allLine[2][rr] = getZ(varPara.line_l, varPara.line_d, varPara.waterHeadLocation[rr]);      //水头位置所处点高程
                            varPara.allLine[3][rr] = getI(varPara.line_l, varPara.waterHeadLocation[rr]);       //当前点流型

                            /**
                             * OutPut:1min
                             */

                        }

                        if (i == n) ff++;
                        if (ff > 1499) {
                            varPara.num++;

                            varPara.lg_fff[1][0] = 0;
                            for (int cc = 1; cc <= n; cc++) {
                                varPara.lg_fff[cc][varPara.num] = varPara.lg_f[cc][varPara.num * 5];
                            }
                            varPara.waterL[varPara.num] = varPara.waterHeadLocation[varPara.num * 5];

                            dpL(n, varPara.T, varPara.num, varPara.deltaX, varPara.waterL, varPara.Hfk_b, varPara.Hfk_bU, varPara.Hfk_f, varPara.lg_fff, varPara.vll);
                            /**
                             * 存储dpl
                             */
//                            int countCount = 0;
//                            for (int j = 0; j < varPara.dPL.length; j+=4) {
//                                for (int k = 0; k < varPara.dPL[j].length; k++) {
//                                    varPara.dPL2[countCount][k] = varPara.dPL[j][k];
//                                }
//                                countCount ++;
//                            }
//                            ResultDPL resultDPL = new ResultDPL();
//                            resultDPL.setProjectId(this.projectId);
//                            resultDPL.setDPL(varPara.dPL2);
//                            resultDPLService.update(resultDPL);
                            SizeChange sizeChange = new SizeChange(varPara.dPL);
                            Map<Integer, double[]> dPLAfterChange = sizeChange.DPLAfterChange();
                            System.out.println(dPLAfterChange);
                            ResultDPL resultDPL = new ResultDPL();
                            resultDPL.setProjectId(this.projectId);
                            resultDPL.setDPLMap(dPLAfterChange);
                            resultDPLService.updateMap(resultDPL);

                            ff = 0;
                            //清管器投放
                            if (varPara.waterL[varPara.num] > 423470 && varPara.pigflag == 0) {//水头流动至距首站后20km
                                if (flagpigT == 0) {
                                    flagpigT = varPara.num;
                                    System.out.println("清管器在" + flagpigT / 12.0 + "h," + "从" + 403470 + "m处投放");
                                }

                                varPara.pigV[0] = conPara.Ql / conPara.A;      //初始速度设为1
                                varPara.pigL[0] = 403470;  //初始位置设为250.35km

                                double[] dPLLL = chazhi(varPara.dPL[varPara.num], 499);//传递当前时步的压力值，进行插值

                                varPara.pigNum++;
                                //Ha
                                Ha = dPLLL[(int) (varPara.pigL[varPara.pigNum - 1] - LLt) - 1];
                                //Hb
                                Hb = dPLLL[(int) (varPara.pigL[varPara.pigNum - 1] - LLt)];

                                varPara.pigV[varPara.pigNum] = shuntai(Ha, Hb, varPara.pigV[varPara.pigNum - 1], varPara.pigL[varPara.pigNum - 1]
                                        , varPara.pigL[varPara.pigNum - 1] + 500, varPara.getPgk()[i], 3000, varPara.pigL[0], getPhi(varPara.line_l, varPara.line_d, varPara.pigL[varPara.pigNum - 1]))[2];

                                varPara.pigL[varPara.pigNum] = varPara.pigL[varPara.pigNum - 1] + 0.5 * (varPara.pigV[varPara.pigNum - 1] + varPara.pigV[varPara.pigNum]) * 300;
                                varPara.pigZ[varPara.pigNum] = getZ(varPara.line_l, varPara.line_d, varPara.pigL[varPara.pigNum]);
                                if (varPara.pigL[varPara.pigNum] > 605930) {
                                    varPara.pigflag = 1;
                                    System.out.println("清管器在" + (varPara.pigNum + flagpigT) / 12.0 + "h," + "从" + 605930 + "m处回收");
                                }
                            }

                            for (int rrr = 1; rrr <= n; rrr++) {
                                if (varPara.MGk[rrr] > 5.0 && varPara.pigL[varPara.pigNum] < varPara.line_l[rrr][1] && varPara.pigL[varPara.pigNum] > varPara.line_l[rrr][1] - 5000) {//当积气段气体质量剩余，且清管器位于积气段10km内时
                                    AddMg[rrr] = varPara.Dengk[rrr] * varPara.deltaT * (varPara.pigV[varPara.pigNum] - conPara.Ql / conPara.A) * varPara.Hgk[rrr] * conPara.A;       //添加清管器赶气的附加破碎质量流量
                                    //System.out.println("测试00001");
                                }
                            }


                            //全线流型计算
                            for (int kkk = varPara.num; kkk < (int) varPara.T * 12 + 1; kkk++)//时间循环
                            {

                                flagFP = varPara.line_l[1][1] + 500;
                                for (int xNum = 1; flagFP < varPara.waterL[kkk]; xNum++) {
                                    varPara.allLineFP[kkk][xNum] = getFlowPattern(getI(varPara.line_l, flagFP), flagFP, varPara.pigL[0], varPara.pigL[varPara.pigNum], flagpigT);       //当前点流型1为分层流，0为满管流，2为气泡流，3为段塞或气泡流
                                  flagFP = flagFP + 500;
                                }
                            }
                        }
                        //在时步300s一次更新压力和流型，只改变破碎压力，可能会导致失稳
                        if (i == n) ttt++;
                        if (ttt > 299) {

                            AddMg_his[i][rr] = AddMg[i];
                            ttt = 0;
                        }
                        /**
                         * broken:save data here
                         */
                    }
                /**
                 * outPut data
                 */

                    if ((varPara.ssr[1][0] == 1 && varPara.ssr[2][0] == 1 && varPara.ssr[3][0] == 1 && varPara.ssr[varPara.i - 1][0] == 1)) {
                        System.out.println("结束!");
//                        TempTest temp = new TempTest();
//                        temp.setLocation("the" + n + "pipe");
//                        temp.setHis(varPara.Pg_his);
//                        tempService.add(temp);
                        break;
                    }
//                }
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }

        end1 = System.currentTimeMillis();
        System.out.println("计算时间 start time:" + start1+ "; end time:" + end1+ "; Run Time:" + (end1 - start1) + "(ms)");

        end3 = System.currentTimeMillis();
        System.out.println("TXT输出时间 start time:" + end1+ "; end time:" + end3+ "; Run Time:" + (end3 - end1) + "(ms)");


    }


    //迭代计算明渠流动公式
    public  double calDelta(double slopeD) {

        double xa = 0.1;
        double xb = Math.PI - 0.1;
        double delta = xa;
        double fa = 1.49/conPara.n*Math.pow((Math.pow(conPara.r,2)*(delta - 0.5*sin(2*delta))),(5.0/3.0))*Math.pow((1.0/(2*conPara.r*delta)),(2.0/3.0))*Math.pow(slopeD,0.5) - conPara.Ql;
        for(int i=0;i<1000;i++)
        {
            double xc = (xa + xb)/2.0;
            delta = xc;
            double fc = 1.49/conPara.n*Math.pow((Math.pow(conPara.r,2)*(delta - 0.5*sin(2*delta))),(5.0/3.0))*Math.pow((1.0/(2*conPara.r*delta)),(2.0/3.0))*Math.pow(slopeD,0.5) - conPara.Ql;
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
    //
    public   double[][] matrixAssemblyX(double lgk,double Pgk,double Hgk, double slopeU,double slopeD, double uk,double h1k,double h2k){
        Oil oil = oils.get(0);
        double a11 = lgk;
        double a12 = Pgk;

        double a22 = -Hgk/ varPara.deltaT;
        double a24 = 1.0/(slopeU* varPara.deltaT);
        double a33 = oil.density* conPara.A*uk/slopeD/ varPara.deltaT;
        double a34 = oil.density* conPara.A*uk/slopeU/ varPara.deltaT;
        double a35 = oil.density* conPara.A*(h1k/slopeD/ varPara.deltaT + h2k/slopeU/ varPara.deltaT);
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
        Oil oil = oils.get(0);
        double b1 = 2*Pgk*lgk + varPara.deltaT*mgk* conPara.R* oil.temperature/(conPara.Mg*conPara.A);
        double b2 = conPara.Ql/ conPara.A + h2k/slopeU/ varPara.deltaT - Hgk*lgk/ varPara.deltaT;
        double b3 = (Pgk - backPressure_k)*conPara.A - conPara.F*(h1k/slopeD + h2k/slopeU) - oil.density*conPara.A*(h2k - h1k)*conPara.g
                + oil.density*Math.pow(conPara.Ql,2)/ conPara.A/(1.0 - Hgk) + 2* oil.density*conPara.A*(uk*h1k/slopeD/varPara.deltaT + uk*h2k/slopeU/varPara.deltaT);
        double b4 = h2k + uk*slopeU*varPara.deltaT;
        double b5 = lgk + h1k/slopeD;
        double[][] Z = new double[][]{{b1},{b2},{b3},{b4},{b5}};      //五行一列
        return Z;
    }

    public  void Save_his(int i,int n, int k) {

        for (i=1;i<=n;i++){
            varPara.Pg_his[i][k] = varPara.Pgk[i]/1000000.0;           //气体压力，Pa,转化为MPa
            varPara.lg_his[i][k]= varPara.lgk[i]/1000.0;             //下坡段气段长度，m，转化为km
            //varPara.h1_his[i][k] = varPara.h1k[i];             //下坡段液高，m
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
        Oil oil = oils.get(0);
        //水顶气满管流的水力坡降
        double nta = 0.11*Math.pow((pipeLine.getRoughness()/conPara.D),0.25);         //粗糙区的公式
        double beta=0.0826*nta;
        double m1=0.0;
        varPara.ipj=beta*Math.pow(conPara.Ql,2-m1)*Math.pow(1e-6,m1)/Math.pow(conPara.D,5-m1);//粗糙区的水力坡降

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
    public  void dtPosui(int timeStepNum, int kkk,double l0, double l1, double z1, double l2, double z2, double q, double d, double A, double dg0, double M0, double Pgp, double Lgp, double dgp, double Hgp, double[] M, double[] Pg, double[] Lg, double[] deng, double[] Hg, double dl, double nd) throws IOException
    {
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

        //System.out.println("破碎计算0已启动");
        //4、破碎后参数计算
        //if(lg>dL)    //9.13//-----------------------while改成了if------------只计算单一时步   //20201105 去掉陈的判断
        //{
        //System.out.println("破碎计算1已启动");
        vlf=q/A/hl;                                                 //液膜平均速度
        Glf=vlf*hl;                                                 //相对液膜速度
        EoD=Math.abs((dl-dg)*9.8*d*d/8/conPara.FT);       //计算破碎速度    FT为水的表面张力
        //System.out.println(EoD);
        dcrit=0.224/Math.pow(Math.abs(Math.cos(sita1)*EoD),0.5);    //计算临界气泡直径
        we=dl*d*Math.pow(vlf-Glf,2)/conPara.FT;           //计算韦伯数
        //System.out.println(we);
        wec=100*C1/dcrit;                                           //临界韦伯数


        if (we-wec < 0){

            if (timeStepNum%300==0){
                if (nums==kkk)
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

            //气体质量变化会导致压力变化吗？体积在减小，，破碎会改变压力吗？
            //假定破碎时体积不变，则有：
            //理想气体状态方程,pv=nRT
            //pgw=Mg*R*T0/29/hg/A/lg;
            dp=tao_i*si/hg/A*lg;                     //Pa，相界面造成的压力损失
            pgw=pg-dp;                               //气段尾的压力等于气段头减摩阻损失
            //dgw=dg0/conPara.p0*pg;                   //dgw长气泡尾部密度，大气压（0.1MPa）下的密度dg0作为依据，计算而出


            dM=0.01*pgw*A*hg*(hgw-hgs)*vgw*dt/conPara.R/oil.temperature;         //计算dM,pc   //dgw长气泡尾部密度   hgw截面含气率  vgw尾部气体速度  dt计算时间步长
            //dM=0;         //计算dM,pc   //dgw长气泡尾部密度   hgw截面含气率  vgw尾部气体速度  dt计算时间步长
            varPara.dMg_out[kkk]=dM;                    //净流出的质量
            lg=lg-dM/Mg*lg;
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


            varPara.Hgk[kkk] = 1-hl;

            pg = dg/dg0*conPara.p0;      //不同压力密度进行折算，实时气段压力等于气体带压密度除以气体在大气压下的密度，再乘以大气压

            //5、参数传递
            varPara.Pgk[kkk] = pg;
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
    public void dpL(int n,double T,int num,double dx,double[] waterHeadLocation,double[] dpb,double[] dpbU,double[] dpf,double [][]lg_f,double []vll){
        Oil oil = oils.get(0);
        //double [][]dPL=new double[500][800];//当前时步下,各点的压力【时步】【距步】500*300,10s,300,,*30
        int nn=1,ii=1;


        for (int kkk=num;kkk<(int)T*12+1;kkk++)//时间循环
        {
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
                                varPara.dPL[kkk][x-1]=varPara.dPL[kkk][x-1]-0.015*oil.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/conPara.A);
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
                                varPara.dPL[kkk][x-1]=varPara.dPL[kkk][x-1]-0.025*oil.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/conPara.A);
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

            }


            if (varPara.Lt>waterHeadLocation[kkk]){
                varPara.num=kkk;
                break;
            }
        }

    }


    //getHb即求Hb的方法；输入x是清管器位置，y是清管器速度,dv是速度变化量,dt是时步,s是水头位置（气液界面位置）,Pg是气相压力
    double getHb(double x,double y,double dv,double dt,double s,double Pg){

        Oil oil = oils.get(0);
        double Hb;
        double hf;
        double xishu=1;
        double hl=getZ(varPara.line_l,varPara.line_d,s); ;          //****水头位置对应的高程
        double hg=getZ(varPara.line_l,varPara.line_d,x); ;          //清管器位置对应的高程

        //水力光滑区的液塞项摩阻，即清管器前的液塞摩阻
        hf=0.002174*Math.PI* oil.density*conPara.g*Math.pow(y,1.75)*Math.pow(oil.viscosity, 0.25)*(s-x)/Math.pow(conPara.D,1.25);
        Hb= oil.density*conPara.g*(hl-hg)+hf+Pg+xishu*conPara.g*dv*dt;
        return Hb;
    }
    //getHa，即求清管器上游纯液相区的压力Ha的方法；x是清管器位置，y是发球位置，dv是速度变化量；z是清管器速度,p是泵站的能头压力
    double getHa(double x,double y,double z,double dv,double dt,double p){
        Oil oil = oils.get(0);
        double h0=getZ(varPara.line_l,varPara.line_d,y);          //****位置对应的高程
        double hp=getZ(varPara.line_l,varPara.line_d,x);            //清管器位置对应的高程
        double Ha;
        double hf;
        double l=x-y;
        double xishu=1;//惯性项系数

        hf= 0.002174*Math.PI*oil.density*conPara.g*Math.pow(z, 1.75)*Math.pow(oil.viscosity, 0.25)*l/Math.pow(conPara.D, 1.25);

        Ha=p- oil.density*conPara.g*(hp-h0)-hf-xishu*conPara.g*dv*dt;           //大气压

        return Ha;
    }

    //shuntai为清管器的瞬态计算函数，x是上一时刻清管器后的压力，y是上一时刻清管器前的压力，z是上一时刻清管器的速度，a是清管器的位置，s是气液界面的位置
    //Pg是清管器后气段压力，p为投球位置提供的能头，f发球位置,phi管道夹角
    double[] shuntai(double x,double y,double z,double a,double s,double Pg,double p,double f,double phi){

        Pig pig = pigs.get(0);
        double[] fanhui=new double[3];
        double vv;      //速度变化值
        double vpig;    //当前清管器速度
        double wucha;   //假设值与实际值的误差
        double A=conPara.A;    //管道截面积
        if((x-y<=20000*z*z)&&(x-y>=-20000*z*z)){//压力差在20kPa内时（清管器与管壁的静摩擦作用，清管器静止）
//            if (z < conPara.Ql/conPara.A && z >=0){
//                fanhui[2]=conPara.Ql/conPara.A;
//            }else if (z>-conPara.Ql/conPara.A&&z<0){
//                fanhui[2]=-conPara.Ql/conPara.A;        //速度设为零
//            }else if (z>5){
//                fanhui[2]=5;
//            }else if (z<-5){
//                fanhui[2]=-5;
//            }else{
//                fanhui[2] = z-conPara.g*phi;
//                //fanhui[2] = z+300*(conPara.g*phi*varPara.deltaT);
//            }
            if (z < conPara.Ql/conPara.A){
                fanhui[2]=conPara.Ql/conPara.A;
            }else if (z>5){
                fanhui[2]=5;
            }else{

                fanhui[2] = z-97*z*z/pig.pigM-varPara.deltaT*conPara.g*phi;
                if (fanhui[2] < conPara.Ql/conPara.A)
                    fanhui[2]=conPara.Ql/conPara.A;
                //fanhui[2] = z+300*(conPara.g*phi*varPara.deltaT);
            }
            vv=fanhui[2]-z;     //速度差为现速度与上一时刻速度之差
            fanhui[0]=getHb(a,fanhui[2],vv,varPara.deltaT,s,Pg);    //计算清管器下游压力
            fanhui[1]=getHa(a,f,fanhui[2],vv,varPara.deltaT,p);     //计算清管器上游压力
        }else{
            //vpig=0.5*(10+0.5);  //初始化为0.5倍清管器速度的上下限之和
            //do{//朱论文3.2.3计算方法3-6步
                //vv=fanhui[2]-z;
                //////fanhui[0]=getHb(a,fanhui[2],vv,varPara.deltaT,s,Pg);
                //////fanhui[1]=getHa(a,f,fanhui[2],vv,varPara.deltaT,p);
                if (z < conPara.Ql/conPara.A && z >=0){
                    fanhui[2]=conPara.Ql/conPara.A;
                    if (fanhui[2] < conPara.Ql/conPara.A)
                        fanhui[2]=conPara.Ql/conPara.A;
                }else if (z>5){
                    fanhui[2]=5;
                }else{
                    //vpig=z+varPara.deltaT*(A*(x-y-2000))/pig.pigM+conPara.g*phi*varPara.deltaT;//清管器的质量取700kg
                    vpig=z+varPara.deltaT*(A*(x-y-20000*z*z))/pig.pigM-varPara.deltaT*conPara.g*phi;//清管器的质量取700kg
                    fanhui[2]=vpig;
                }
                //wucha=(fanhui[2])/fanhui[2];
            //}while(wucha==0);
            //}while(wucha<=0.05&&wucha>=-0.05);
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
     * 获取某里程L处的流型，5为满管流，1为分层流，2为气泡流，3为段塞或气泡,4为未赋值
     * @param i L点所处管段数
     * @param L 里程，水头位置吧
     * @param pigL
     * @param flagpig 清管器投放标志，1为已投放
     * @return
     */
    public double getFlowPattern(int i,double L,double pigStart,double pigL,double flagpig){
        double p=4;  //流型，初始未赋值时取4

        if (varPara.line_l[i][1]<=L && varPara.line_l[i][2]>L){

            if (L-varPara.line_l[i][1]<varPara.lgk[i]){
                p=1;
            }else{
                p=2;
            }
        }else if (varPara.line_l[i][2]<=L && varPara.line_l[i][3]>=L){
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

    public Pipeline getPipeline() {
        return pipeLine;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeLine = pipeline;
    }

}



