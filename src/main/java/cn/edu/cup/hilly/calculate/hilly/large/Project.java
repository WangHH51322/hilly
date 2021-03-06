package cn.edu.cup.hilly.calculate.hilly.large;

import Jama.Matrix;
import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;
import cn.edu.cup.hilly.dataSource.utils.SizeChange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.*;
import static java.lang.Math.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IOElement(name = "hillyProject")
public class Project extends Thread {

    Conpara conPara=new Conpara();

    @InputField(name = "variableParameter", unit = "")
    Varpara varPara;

    @InputField(name = "pipeList", unit = "")
    List<Pipeline> pipeLines;

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
     * lz_out
     */
    private double[][] lz_out;
    public double[][] getLz_out() {
        return lz_out;
    }
    public void setLz_out(double[][] lz_out) {
        this.lz_out = lz_out;
    }
    /**
     * dpl
     */
    private Map<Double, double[]> dPL;
    private double[][] varPara_dPL;
    public Map<Double, double[]> getDPL() {
        return dPL;
    }
    public void setDPL(Map<Double, double[]> dPL) {
        this.dPL = dPL;
    }
    public double[][] getVarPara_dPL() {
        return varPara_dPL;
    }
    public void setVarPara_dPL(double[][] varPara_dPL) {
        this.varPara_dPL = varPara_dPL;
    }

    /**
     * dhl
     */
    private Map<Double, double[]> dHL;
    private double[][] varPara_dHL;
    public Map<Double, double[]> getDHL() {
        return dHL;
    }

    public double[][] getVarPara_dHL() {
        return varPara_dHL;
    }

    public void setVarPara_dHL(double[][] varPara_dHL) {
        this.varPara_dHL = varPara_dHL;
    }

    /**
     * hss
     */
    private Map<String, Map<Double, double[]>> hSS;
    public Map<String, Map<Double, double[]>> getHSS() {
        return hSS;
    }
    public void setHSS(Map<String, Map<Double, double[]>> hSS) {
        this.hSS = hSS;
    }
    /**
     * allLineFP
     */
    private Map<Double, double[]> allLineFP;
    private double[][] varPara_allLineFP;
    public Map<Double, double[]> getALineFP() {
        return allLineFP;
    }
    public void setALineFP(Map<Double, double[]> aLineFP) {
        this.allLineFP = aLineFP;
    }

    public double[][] getVarPara_allLineFP() {
        return varPara_allLineFP;
    }

    public void setVarPara_allLineFP(double[][] varPara_allLineFP) {
        this.varPara_allLineFP = varPara_allLineFP;
    }

    /**
     * line_l dMg_out_his
     */
    private double[][] Line_L;
    public double[][] getLine_L() {
        return Line_L;
    }
    public void setLine_L(double[][] line_L) {
        Line_L = line_L;
    }

    private Map<Double, double[]> dMgHis;
    public Map<Double, double[]> getdMgHis() {
        return dMgHis;
    }
    public void setdMgHis(Map<Double, double[]> dMgHis) {
        this.dMgHis = dMgHis;
    }

    /**
     * lg_his
     */
    private Map<Double, double[]> lgHis;
    public Map<Double, double[]> getLgHis() {
        return lgHis;
    }
    public void setLgHis(Map<Double, double[]> lgHis) {
        this.lgHis = lgHis;
    }
    /**
     * m_his
     */
    private Map<Double, double[]> mHis;
    public Map<Double, double[]> getmHis() {
        return mHis;
    }
    public void setmHis(Map<Double, double[]> mHis) {
        this.mHis = mHis;
    }
    /**
     * pg_his
     */
    private Map<Double, double[]> pgHis;
    public Map<Double, double[]> getPgHis() {
        return pgHis;
    }
    public void setPgHis(Map<Double, double[]> pgHis) {
        this.pgHis = pgHis;
    }
    /**
     * allLineStaticP,pig_V,pig_L
     */
    private double[][] pigV;
    private double[][] pigL;
    private double[][] aLSP;
    private double[][] dMgP;
    private Map<String,double[][]> dMgPK;
    private double[][] mG;
    private double[][] gasRa;
    private double[] Q;
    public double[][] getmG() {
        return mG;
    }
    public void setmG(double[][] mG) {
        this.mG = mG;
    }
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
    public double[][] getDMgP() {
        return dMgP;
    }
    public void setDMgP(double[][] dMgP) {
        this.dMgP = dMgP;
    }

    public Map<String, double[][]> getDMgPK() {
        return dMgPK;
    }

    public void setDMgPK(Map<String, double[][]> dMgPK) {
        this.dMgPK = dMgPK;
    }

    public double[][] getaLSP() {
        return aLSP;
    }
    public void setaLSP(double[][] aLSP) {
        this.aLSP = aLSP;
    }
    public double[] getQ() {
        return Q;
    }
    public void setQ(double[] q) {
        Q = q;
    }
    public double[][] getGasRa() {
        return gasRa;
    }
    public void setGasRa(double[][] gasRa) {
        this.gasRa = gasRa;
    }

    /**
     * ????????????
     */
    private static boolean pause = false;
    public boolean isLocked() {
        return pause;
    }
    private static boolean release = false;
    private static Object lock = new Object();

    /**
     * ????????????????????????????????????
     */
    public static void pauseThread(){
        pause = true;
        //return varPara;
    }
    /**
     * ???????????????
     */
    public static void releaseThread(){
        pause = false;
        synchronized (lock){
            lock.notify();
        }
        release = true;
    }
    /**
     * ???release???????????????false
     */
    public static void recoverThread(){
        release = false;
    }
    /**
     * ??????????????????????????????????????????
     */
    public static void resumeThread(){
        pause = false;
        synchronized (lock){
            lock.notify();
        }
    }
    /**
     * ?????????????????????run ??????????????????????????????????????????????????????????????????
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
    /**
     * ???????????????
     */
    private double [][] lz;
    public double[][] getLz() {
        return lz;
    }
    public void setLz(double[][] lz) {
        this.lz = lz;
    }

    /**
     * ??????inum
     */
    private Integer inum;
    public Integer getInum() {
        return inum;
    }
    public void setInum(Integer inum) {
        this.inum = inum;
    }

    /**
     * U???????????????
     */
    private double[] ULocation;
    public double[] getULocation() {
        return ULocation;
    }
    public void setULocation(double[] ULocation) {
        this.ULocation = ULocation;
    }

    @SneakyThrows
    @Override
    public void run() {
        Pipeline pipeLine = pipeLines.get(0);
        conPara.Ql = varPara.Qh/3600.0;
        Oil oil = oils.get(0);
        Oil oil1 = oils.get(1);
        double message=0;
        int num000=0;
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2.0;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
//        double [][] lz;
//        lz = ExcelData.Graphic();       //????????????????????????????????????????????????
//        System.out.println("lz : ");

//        varPara.i= ExcelData.inum/2+1;
        varPara.i= inum/2+1;
//        varPara.i= 66;
        varPara.setArr();       //??????get?????????i???k???varPara??????????????????
        for (int i = 0; i < lz.length; i++) {
            for (int i1 = 0; i1 < lz[i].length; i1++) {
                System.out.print(lz[i][i1] + " ");
            }
            System.out.println();
        }
        //varPara.i= 33;
        varPara.stationListZ.add(0);
        varPara.stationListL.add(0);
        for (int u=0;u<stations.size();u++){
            varPara.stationListZ.add(stations.get(u).getStationZ());
            varPara.stationListL.add(stations.get(u).getStationL());
            if (stations.get(u).getStationType()==2){
                varPara.staV.add(stations.get(u).getStationL()/1000.0);
            }
        }




        double t_all,dt=0.2;
        int tt=0,rr=0,pp=0,ff=0,ttt=0,uyt=0;      //??????????????????
        int rrr0=0;
        int o,op=0,opp=0;   //?????????????????????,??????????????????
        int []p={1,1,1,1,1,1,1,1,1,1};   //????????????????????????,??????????????????????????????
        varPara.num=0;
        double ssr=0,ssr1=0,ssr2=0,ssr3=0;  //????????????
        double wL=0;  //??????????????????
        double []AddMg=new double[varPara.getI()];  //??????????????????????????????
        double [][]AddMg_his=new double[varPara.getI()][varPara.getK()];  //??????????????????????????????

        t_all= 0;      //?????????????????????????????????????????????
        double LLt;//????????????????????????
        double flagFP;//????????????????????????

        LLt=varPara.line_l[1][1]+500;


        int i,n=1,num0,num1=1,num2; //i?????????????????????n???????????????????????????

        double []Ha=new double[10];
        double []Hb=new double[10];
        double []flagpigT=new double[10];



        //////////////////////0402   lz = ExcelData.Graphic();       //????????????????????????????????????????????????
        //????????????????????????

        for (num2 = 1; num2 < inum/2+1; num2++) {     //??????????????????????????????
            for (int b=0;b<4;b++){
                varPara.line_lll[num2][b] = lz[num2][b];
                varPara.line_ddd[num2][b] = lz[num2][b+4];
            }
        }
//////////////////////////////////////////////////////////////////////////////????????????????????????????????????????????????????????????????????????

        for (int a=1;a< varPara.line_l.length;a++){          ///////////??????
//        for (int a=1;a< 66;a++){          ///////////??????
            //for (int a=44;a< 114;a++){          ///////////??????
            if (varPara.line_lll[a][1]!=0 || a==1){
                System.out.print("???"+(a)+"??????"+"\t");
                //System.out.print("???"+(a-43)+"??????"+"\t");
                for (int b=0;b<4;b++){
                    varPara.line_l[a][b]=varPara.line_lll[a][b];
                    System.out.print(varPara.line_l[a][b]+"\t");
                    varPara.line_d[a][b]=varPara.line_ddd[a][b];
                    System.out.print(varPara.line_d[a][b]+"\t");
                }
                System.out.println();
            }
        }

        //varPara.line_d[1][1]=varPara.line_d[1][1]-603.1529;//??????402.4????????????
        /**
         * ??????U??????????????????????????????
         */
        double[] line = new double[varPara.line_l.length - 1];
        System.out.println("ULocation");
        for (int j = 1; j < varPara.line_l.length; j++) {
            line[j-1] = varPara.line_l[j][1];
            System.out.print(line[j-1] + " ");
        }
        setULocation(line);

        System.out.println("varPara.line_d[1][1]="+varPara.line_d[1][1]);

        varPara.slopeD = calSlopeD(varPara.line_d, varPara.line_l);         //??????????????????
        varPara.slopeU = calSlopeU(varPara.line_d, varPara.line_l);         //??????????????????
        for (i = 1; i < varPara.i; i++) {

            varPara.delta[i] = calDelta(varPara.slopeD[i][0]);      //??????????????????????????????????????????????????????
            varPara.Al[i] = Math.pow(r, 2) * (varPara.delta[i] - 0.5 * Math.sin(2 * varPara.delta[i]));    //????????????????????????
            varPara.Sl[i] = 2 * varPara.delta[i] * r;                                                //????????????
            varPara.vll[i] = 1.49 / conPara.n * Math.pow((varPara.Al[i] / varPara.Sl[i]), (2.0 / 3.0)) * Math.pow(varPara.slopeD[i][0], 0.5);      //??????????????????
            varPara.t2[i] = (varPara.line_l[i][2] - varPara.line_l[i][1]) / varPara.vll[i];        //?????????????????????????????????????????????????????????????????????
            varPara.gasRatio[i] = (Math.PI * Math.pow(r, 2) - varPara.Al[i]) / (Math.PI * Math.pow(r, 2));      //???????????????????????????

            varPara.V0[i] = Math.PI * Math.pow(r, 2) * (varPara.line_l[i][2] - varPara.line_l[i][1]) * varPara.gasRatio[i];      //??????????????????????????????
            varPara.M0[i] = varPara.V0[i] * 1.205;        //??????????????????????????????????????????1.205kg/m3
            varPara.h1k[i] = 2 * r;               //???????????????????????????---------??????????????????
            varPara.h2k[i] = 2 * r;               //???????????????????????????
            varPara.Pgk[i] = conPara.p0;                //??????????????????????????????
            varPara.backPressure[i] = conPara.p0;                //??????????????????????????????,??????????????????????????????
            varPara.Hgk[i] = varPara.gasRatio[i];       //?????????????????????
            varPara.uk[i] = conPara.Ql / (2 * A);           //?????????????????????????????????
            varPara.lgk[i] = varPara.line_l[i][2] - varPara.line_l[i][1]; //???????????????????????????????????????
            varPara.MGk[i] = varPara.M0[i];              //??????????????????????????????
            //System.out.println(varpara.Hgk[i]);
        }
//        for (int jjj=9;jjj<9+1;jjj++){
//            System.out.println("delta["+jjj+"]="+varPara.delta[jjj]);
//            System.out.println("vll["+jjj+"]="+varPara.vll[jjj]);
//            System.out.println("gasRatio["+jjj+"]="+varPara.gasRatio[jjj]);
//            System.out.println("lgk["+jjj+"]="+varPara.lgk[jjj]);
//        }


        for (int j = 0; j < stations.size(); j++) {
            if (stations.get(j).getStationType()==1||stations.get(j).getStationType()==4){
                for (int pumpNum0 = 0; pumpNum0 < 10; pumpNum0++) {
                    varPara.pumpRev[j][pumpNum0]=0.40;
                }
            }
        }

        /**
         * dHL??????0??????????????????txt????????????248.5
         * lz_new??????0??????248.????????????248.5
         */
        double [][] lz_new = new double[(int)((varPara.line_l[varPara.i-1][3]-varPara.line_l[1][1])/500+1)][2];
        lz_new[0][0]=varPara.line_l[1][1];
        lz_new[0][1]=varPara.line_d[1][1];
        varPara.dHL[0][0]=varPara.line_l[1][1]/1000.0;
        for (int x = 1; x <=(varPara.line_l[varPara.i-1][3]-varPara.line_l[1][1])/500; x++) {
            varPara.dHL[0][x]=0.5+varPara.dHL[0][x-1];//???????????????
            lz_new[x][0]= varPara.dHL[0][x];
        }
        for (int x = 1; x <=(varPara.line_l[varPara.i-1][3]-varPara.line_l[1][1])/500; x++) {
            varPara.dHL[varPara.dHL.length-2][x]=getZ(varPara.line_l, varPara.line_d,varPara.dHL[0][x]*1000);
            lz_new[x][1]= varPara.dHL[varPara.dHL.length-2][x];
        }
//        System.out.println("lz_new = " + Arrays.deepToString(lz_new));
//        System.out.println("lz_new = " + (lz_new == null));
        /**
         * ??????lz_new
         */
        this.lz_out = lz_new;

        varPara.waterHeadLocation[0]=varPara.line_l[1][1];   //?????????????????????????????????????????????

        System.out.println("??????????????????"+(varPara.line_l[varPara.i-1][3]-varPara.line_l[1][1])/1000+"km");
        //System.out.println("????????????????????????"+waterHeadLocation[0]/1000+"km");


        cal_l0();//????????????????????????????????????????????????????????????????????????????????????
        for (int s = 0; s < stations.size(); s++) {
            if (stations.get(s).getStationType()==1 || stations.get(s).getStationType()==4){
                varPara.pumpSta.add(stations.get(s).getStationL());
            }else{
                varPara.pumpSta.add(0.0);
            }
        }

        varPara.Time[0] = t_all;       //??????????????????????????????
        double timeStop=0;
        int flag05 = 1;
        //1???????????????U??????????????????????????????????????????
        long start1,end1,end3;

        start1 = System.currentTimeMillis();        //???????????????????????????
        conPara.Ql = varPara.Qh/3600.0;
        /**
         * ??????????????????
         */
        /**
         * ????????????
         */
        System.out.println("????????????!!!");
        int kk = 0;
        int count = varPara.kt + 1;
        loop:while(true) {
            while (pause) {
                onPause();
            }
            while (release) {
                break loop;
            }
            try {
                kk++;
                if (kk == count) {
                    break;
                }
                //???????????????????????????u????????????????????????????????????????????????
                //??????????????????
                uyt++;
                if (uyt%360000==0){
                    System.out.println(0.2 * kk / 7200.0 + "h????????????" +conPara.Ql*3600);
                    //System.out.println(0.2 * kk / 3600.0 + "h????????????" +varPara.Qh);
                }


                for (i=1;i<=n;i++)      //???????????????????????????n?????????i???????????????????????????
                {
                    if (varPara.ssr[i][0] != 1) {     //20201105????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        if (varPara.Pgk[i] < 0.1*oil.getDensity()*conPara.g*(varPara.line_l[i][3]-varPara.line_l[i][2])&&varPara.Pgk[i] <200000 ){
                            YaSuo(i, varPara.lgk[i]+varPara.lgk[i]*(varPara.dMg_in[i] - varPara.dMg_out[i]-AddMg[i])/varPara.MGk[i], varPara.Pgk[i], varPara.Hgk[i], varPara.slopeU[i][0],
                                    varPara.slopeD[i][0], varPara.uk[i], varPara.h1k[i], varPara.h2k[i], varPara.backPressure[i],
                                    varPara.dMg_in[i] - varPara.dMg_out[i]-AddMg[i]);
                            //if (i==4) System.out.println("varPara.Pgk 1 ="+varPara.Pgk[i]);

                            varPara.Dengk[i] = 1.205 / 1.01e5 * varPara.Pgk[i];
                            try {
                                dtPosui(kk, i, varPara.l0[i], varPara.line_l[i][1], varPara.line_d[i][1], varPara.line_l[i][2], varPara.line_d[i][2],
                                        conPara.Ql, D, 1.205,varPara.MGk[i]-AddMg[i] , varPara.Pgk[i], varPara.lgk[i],
                                        varPara.Dengk[i], varPara.Hgk[i], varPara.MM, varPara.Pgk, varPara.lgk, varPara.Dengk, varPara.Hgk,
                                        oil.density, 1e-6);//
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            if ((kk) / 300 - 1<0){
                                varPara.h2k[i] = varPara.h2_his[i][0] + conPara.Ql / A;
                            }else{
                                varPara.h2k[i] = varPara.h2_his[i][(kk) / 300 - 1] + conPara.Ql / A;
                            }
                        }
                    }
                    //???????????????????????????????????????????????????????????????????????????????????????
                    varPara.Hfk_f[i] = dpf_cal(-varPara.slopeD[i][0], varPara.Hgk[i], A, oil.density, 1.205, conPara.Ql / A, varPara.f_l[i], 0.0143, varPara.Sl[i]);
                    varPara.Hfk_f1[i] = dHf_cal(-varPara.slopeD[i][0], varPara.Hgk[i], A, oil.density, 1.205, conPara.Ql / A, varPara.f_l[i], 0.0143, varPara.Sl[i]);
                    varPara.Hfk_f_oil[i] = dpf_cal(-varPara.slopeD[i][0], varPara.Hgk[i], A, oil1.density, 1.205, conPara.Ql / A, varPara.f_l[i], 0.0143, varPara.Sl[i]);
                    varPara.Hfk_f1_oil[i] = dHf_cal(-varPara.slopeD[i][0], varPara.Hgk[i], A, oil1.density, 1.205, conPara.Ql / A, varPara.f_l[i], 0.0143, varPara.Sl[i]);
                    //??????????????????????????????????????????????????????????????????
                    varPara.Hfk_b[i] = dPb_cal(A, -varPara.slopeD[i][0], varPara.Hgbk[i], A, oil.density, 1.205, 0.001* oil.getViscosity(), conPara.Ql / A, conPara.Ql / A, conPara.Ql / A, 0.0001);
                    varPara.Hfk_b_oil[i] = dPb_cal(A, -varPara.slopeD[i][0], varPara.Hgbk[i], A, oil1.density, 1.205, 0.001* oil1.getViscosity(), conPara.Ql / A, conPara.Ql / A, conPara.Ql / A, 0.0001);
                    varPara.Hfk_b1[i] = dHb_cal(A, -varPara.slopeD[i][0], varPara.Hgbk[i], A, oil.density, 1.205, 0.001* oil.getViscosity(), conPara.Ql / A, conPara.Ql / A, conPara.Ql / A, 0.0001);
                    varPara.Hfk_b1_oil[i] = dHb_cal(A, -varPara.slopeD[i][0], varPara.Hgbk[i], A, oil1.density, 1.205, 0.001* oil1.getViscosity(), conPara.Ql / A, conPara.Ql / A, conPara.Ql / A, 0.0001);
                    varPara.Hfk_dU[i] = dPd_cal(varPara.slopeU[i][0], varPara.Hgbk[i], conPara.Ql, A, A, oil.density, conPara.Ql / A, conPara.Ql / A, 0.0001);
                    //??????????????????????????????????????????????????????????????????
                    varPara.Hfk_bU[i] = dPbU_cal(A, varPara.slopeU[i][0], varPara.Hgbk[i], A, oil.density, 1.205, 0.001* oil.getViscosity(), conPara.Ql / A, conPara.Ql / A, conPara.Ql / A, 0.0001);
                    varPara.Hfk_bU_oil[i] = dPbU_cal(A, varPara.slopeU[i][0], varPara.Hgbk[i], A, oil1.density, 1.205, 0.001* oil1.getViscosity(), conPara.Ql / A, conPara.Ql / A, conPara.Ql / A, 0.0001);
                    varPara.Hfk_bU1[i] = dHbU_cal(A, varPara.slopeU[i][0], varPara.Hgbk[i], A, oil.density, 1.205, 0.001* oil.getViscosity(), conPara.Ql / A, conPara.Ql / A, conPara.Ql / A, 0.0001);
                    varPara.Hfk_bU1_oil[i] = dHbU_cal(A, varPara.slopeU[i][0], varPara.Hgbk[i], A, oil1.density, 1.205, 0.001* oil1.getViscosity(), conPara.Ql / A, conPara.Ql / A, conPara.Ql / A, 0.0001);
                    //????????????????????????????????????
                    varPara.Hfk[i] = dP_cal(varPara.vll[i], varPara.vll[i], varPara.Hgbk[i], 3, A, oil.getDensity(), 1.205, varPara.Hfk_b[i], varPara.Hfk_bU[i], varPara.Hfk_f[i], varPara.lg_f[i][kk / 300], varPara.lp_b[i][kk / 300], varPara.lp_bU[i][kk / 300])[3];
                    varPara.Hfk_jj[i] = dP_cal(varPara.vll[i], varPara.vll[i], varPara.Hgbk[i], 3, A, oil.getDensity(), 1.205, varPara.Hfk_b[i], varPara.Hfk_bU[i], varPara.Hfk_f[i], varPara.lg_f[i][kk / 300], varPara.lp_b[i][kk / 300], varPara.lp_bU[i][kk / 300])[2];
                    //????????????????????????????????????????????????????????????
                    FlowConversion(kk, i);
                    //paiqi(i,varPara.Pgk,300000,eValve,varPara.h1k,varPara.h2k,varPara.lgk,varPara.Hgk,varPara.line_d,varPara.line_l);

                    if (varPara.ssr[i][0] == 1) {
                        if ((kk) / 300 - 5<0){
                            varPara.Pgk[i] = varPara.Pg_his[i][0];
                            varPara.lgk[i] = varPara.lg_his[i][0];
                            varPara.h1k[i] = varPara.line_d[i][1] - varPara.line_d[i][2];
                            varPara.h2k[i] = varPara.h2_his[i][0] + conPara.Ql / A;
                            varPara.dMg_out[i] = 0.00;            //???????????????
                            varPara.MGk[i] = varPara.M_his[i][0];
                        }else{
                            varPara.Pgk[i] = varPara.Pg_his[i][(kk) / 300 - 5];
                            varPara.lgk[i] = varPara.lg_his[i][(kk) / 300 - 5];
                            varPara.h1k[i] = varPara.line_d[i][1] - varPara.line_d[i][2];

                            varPara.h2k[i] = varPara.h2_his[i][(kk) / 300 - 5] + conPara.Ql / A;
                            varPara.dMg_out[i] = 0.00;            //???????????????
                            varPara.MGk[i] = varPara.M_his[i][(kk) / 300 - 5];
                        }
                    }
                    //if (i > 1 && varPara.ssr[i][0] == 0) {
                    if (i > 1 && varPara.ssr[i][0] == 0) {
                        varPara.backPressure[i - 1] = varPara.Pgk[i];                          //??????????????????????????????????????????????????????????????????
                        varPara.dMg_in[i] = varPara.dMg_out[i - 1]+ AddMg[i-1];           //????????????????????????????????????????????????
                        ////////////////////////////////??????????????????????????????????????????????????? ///////////////////////////////////////////////////////
                    }

                    for (o=0;o<n-i;o++){

                        if (i > 1 && varPara.ssr[i+o][0] != 0) {
                            o++;
                            varPara.dMg_in[i + o] = varPara.dMg_out[i - 1] + AddMg[i - 1];
                            o--;
                        }
                    }
                    //????????????????????????????????????????????????
                    if (varPara.h2k[n] > (varPara.line_d[n][3] - varPara.line_d[n][2])) {//???????????????????????????
                        for (i = 1; i <= n; i++) {

                            varPara.h2k[i] = varPara.line_d[i][3] - varPara.line_d[i][2];
                        }

                        if (pp == 0) {
                            if (n < varPara.i - 1) {
                                System.out.println("????????????" + 0.2 * kk / 7200.0 + "h?????????" + (n) + "???U????????????????????????" + varPara.line_l[n][3]/1000.0 + "km");
                                num000++;
                                //System.out.println("????????????" + 0.2 * kk / 3600.0 + "h?????????" + (n) + "???U????????????");
                                //pp++;
                                n++;
                            } else if (n == varPara.i - 1) {
                                //System.out.println("????????????" + 0.2 * kk / 3600.0 + "h?????????" + (n) + "???U????????????");
                                System.out.println("????????????" + 0.2 * kk / 7200.0 + "h?????????" + (n) + "???U????????????????????????" + varPara.line_l[n][3]/1000.0 + "km");
                                num000++;
                                pp++;
                            }
                        }
                    }

                    if (i == n) tt++;
                    if (tt > 299) {
                        rr++;
                        varPara.Time[rr] = varPara.Time[rr - 1] + varPara.deltaT * 300;
                        Save_his(i, n, rr);


                        //Hf[i]??????i???????????????????????????????????????????????????
                        for (int kkk = 1; kkk < varPara.i; kkk++) {
                            //Hf[rr]=Hfk[kkk]+Hf[rr];
                            ssr = varPara.Hfk[kkk] + ssr;

                            //Hf[rr]=Hfk[kkk];
                            ssr1 = varPara.Hfk_bb[kkk] + ssr1;
                            ssr2 = varPara.Hfk_ff[kkk] + ssr2;
                            ssr3 = varPara.Hfk_jj[kkk] + ssr3;

                            if (n > 1 && varPara.ssr[kkk][0] == 1) {
                                varPara.lp_b[kkk][rr] = varPara.line_l[kkk][2] - varPara.line_l[kkk][1];              //?????????????????????????????????????????????????????????
                                varPara.lp_bU[kkk][rr] = varPara.line_l[kkk][3] - varPara.line_l[kkk][2];             //?????????????????????????????????????????????????????????
                                varPara.lg_f[kkk][rr] = 0;                                       //????????????????????????????????????????????????????????????????????????
                            } else if (varPara.ssr[kkk][1] == 1) {                               //?????????????????????
                                varPara.lp_b[kkk][rr] = varPara.line_l[kkk][2] - varPara.line_l[kkk][1] - varPara.lgk[kkk];
                                varPara.lp_bU[kkk][rr] = varPara.line_l[kkk][3] - varPara.line_l[kkk][2];
                                varPara.lg_f[kkk][rr] = varPara.lgk[kkk];
                                if (varPara.lgk[kkk]<0 || varPara.lgk[kkk]>(varPara.line_l[kkk][2] - varPara.line_l[kkk][1])){
                                    varPara.lg_f[kkk][rr]=0.9*varPara.lg_f[kkk][rr-1];
                                }
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

                        if (varPara.waterHeadLocation[rr - 1] < varPara.line_l[n][2] && (varPara.waterHeadLocation[rr - 1] != 0.0 || rr-1==0))                       //?????????????????????
                        {
                            varPara.waterHeadLocation[rr] = varPara.waterHeadLocation[rr - 1] + dt * varPara.vll[n] * 300;        //????????????????????????????????????
                            varPara.lg_f[n][rr] = varPara.lg_f[n][rr - 1] + dt * varPara.vll[n] * 300;                            //???????????????????????????????????????
                            varPara.lp_b[n][rr] = 0;        //????????????????????????????????????????????????????????????
                            varPara.lp_bU[n][rr] = 0;       //????????????????????????????????????????????????????????????
                        } else if (varPara.waterHeadLocation[rr - 1] > varPara.line_l[n][2] && varPara.waterHeadLocation[rr - 1] <= varPara.line_l[n][3]) {     //??????????????????????????????
                            //varPara.waterHeadLocation[rr] = varPara.waterHeadLocation[rr - 1] + conPara.Ql/A*dt * 300;                      //?????????????????????????????????????????????????????????
                            varPara.waterHeadLocation[rr] = varPara.line_l[n][2] + varPara.h2k[n] / varPara.slopeU[n][0];                      //?????????????????????????????????????????????????????????
                            //wL=varPara.waterHeadLocation[rr];
                            varPara.lg_f[n][rr] = varPara.lgk[n];         //???????????????????????????????????????????????????
                            varPara.lp_b[n][rr] = varPara.line_l[n][2] - varPara.line_l[n][1] - varPara.lgk[n];
                            if (varPara.lgk[n]<0 || varPara.lgk[n]>(varPara.line_l[n][2] - varPara.line_l[n][1])){
                                varPara.lg_f[n][rr]=0.9*varPara.lg_f[n][rr-1];
                                varPara.lp_b[n][rr]=(varPara.line_l[n][2] - varPara.line_l[n][1])-varPara.lg_f[n][rr];
                            }
                            //?????????????????????????????????????????????????????????????????????
                            varPara.lp_bU[n][rr] = varPara.h2k[n] / varPara.slopeU[n][0];                              //???????????????????????????
                        } else if (varPara.waterHeadLocation[rr]==0 && varPara.waterHeadLocation[rr-1]!=0){
                            varPara.waterHeadLocation[rr]=varPara.waterHeadLocation[rr-1];
                            System.out.println(varPara.waterHeadLocation[rr-1]);
                            System.out.println(rr-1);
                            System.out.println("-------------------------------------------------------");
                        }
                        if (varPara.waterHeadLocation[rr-1] >= varPara.line_l[varPara.i-1][3]-100 &&varPara.line_l[varPara.i-2][3]!=0){
                            varPara.waterHeadLocation[rr] = varPara.line_l[varPara.i-1][3];///////////////?????????i-1
                            //System.out.println("ces");
                        }
                        if (varPara.waterHeadLocation[rr]>=varPara.waterLength*1000){
                            varPara.oilHeadLocation[rr]=varPara.waterHeadLocation[rr]-varPara.waterLength*1000;
                        }

                        varPara.allLine[1][rr]=varPara.waterHeadLocation[rr];       //????????????
                        varPara.allLine[2][rr]=getZ(varPara.line_l, varPara.line_d,varPara.waterHeadLocation[rr]);      //???????????????????????????
                        //varPara.allLine[2][rr]=getFlowPattern(getI(varPara.line_l, varPara.waterHeadLocation[rr]),varPara.waterHeadLocation[rr],varPara.pigL[rr/5]);       //???????????????1???????????????0???????????????2???????????????3?????????????????????
                        varPara.allLine[3][rr]=getI(varPara.line_l, varPara.waterHeadLocation[rr]);                     //???????????????
                    }


                    if (i == n) ff++;
                    if (ff > 1499) {
                        varPara.num++;
                        varPara.waterL[varPara.num] = varPara.waterHeadLocation[varPara.num * 5];
                        varPara.oilL[varPara.num] = varPara.oilHeadLocation[varPara.num * 5];
                        varPara.lg_fff[1][0] = 0;
                        for (int cc = 1; cc <= n; cc++) {
                            varPara.lg_fff[cc][varPara.num] = varPara.lg_f[cc][varPara.num * 5];
                            //varPara.Mg[varPara.num]=(varPara.lg_fff[cc][varPara.num]-varPara.lg_fff[cc][varPara.num-1])*varPara.Hgk[cc]*A*varPara.Dengk[cc]+varPara.Mg[varPara.num-1];
                            varPara.Mg[varPara.num]=(varPara.M_his[cc][varPara.num * 5]-varPara.M_his[cc][varPara.num * 5 - 5])+varPara.Mg[varPara.num-1];
                            varPara.gasRa[varPara.num]=varPara.Mg[varPara.num]/varPara.waterL[varPara.num]*A;
                            if (cc==n){
                                if (varPara.pigFlag==1 && varPara.Mg[varPara.num]>20){
                                    varPara.Mg[varPara.num]=(varPara.M_his[cc][varPara.num * 5]-varPara.M_his[cc][varPara.num * 5 - 5])+varPara.Mg[varPara.num-1]-varPara.dMg_out[n]*1000*30;
                                }else if (varPara.Mg[varPara.num]>20){
                                    varPara.Mg[varPara.num]=(varPara.M_his[cc][varPara.num * 5]-varPara.M_his[cc][varPara.num * 5 - 5])+varPara.Mg[varPara.num-1]-varPara.dMg_out[n]*1000*15;
                                }else{
                                    varPara.Mg[varPara.num]=varPara.Mg[varPara.num]*1.5;
                                }

                                if (varPara.dMg_out[n]==0){
                                    if (varPara.pigFlag==1){
                                        varPara.Mg[varPara.num]=(varPara.M_his[cc][varPara.num * 5]*0.003)+varPara.Mg[varPara.num-1];
                                    }else {
                                        varPara.Mg[varPara.num]=(varPara.M_his[cc][varPara.num * 5]*0.0015)+varPara.Mg[varPara.num-1];
                                    }
                                }
                                varPara.gasRa[varPara.num]=varPara.Mg[varPara.num]/varPara.waterL[varPara.num]*A;
                            }
                            if (varPara.Mg[varPara.num]<0) {
                                varPara.Mg[varPara.num]=varPara.Mg[varPara.num-1];
                                varPara.gasRa[varPara.num]=varPara.Mg[varPara.num]/varPara.waterL[varPara.num]*A;
                            }
                        }

                        for (int sL=0;sL<stations.size();sL++){     //?????????????????????
                            varPara.stationLLL[sL] =stations.get(sL).getStationL();

                        }
                        dpL(n, varPara.T, varPara.num, varPara.deltaX, varPara.waterL, varPara.Hfk_b, varPara.Hfk_bU, varPara.Hfk_f, varPara.lg_fff,varPara.vll,varPara.stationLLL,varPara.Hd);
                        dHL(n, varPara.T, varPara.num, varPara.deltaX, varPara.waterL, varPara.Hfk_b1, varPara.Hfk_bU1, varPara.Hfk_f1, varPara.lg_fff,varPara.vll,varPara.stationLLL,varPara.Hd);
                        dHL_new(n, varPara.T, varPara.num, varPara.deltaX, varPara.waterL, varPara.Hfk_b1, varPara.Hfk_bU1, varPara.Hfk_f1, varPara.lg_fff,varPara.vll,varPara.pumpSta,varPara.Hd);

                        /**
                         * ??????dpl  dHL
                         */
//                        SizeChange dPLChange = new SizeChange(varPara.dPL);
//                        Map<Double, double[]> dPLAfterChange = dPLChange.ResultAfterChange(2,2.5);
//                        setDPL(dPLAfterChange);
                        this.varPara_dPL = varPara.dPL;
//                        SizeChange dHLChange = new SizeChange(varPara.dHL);
//                        Map<Double, double[]> dHLAfterChange = dHLChange.ResultAfterChange(2, 2.5);
//                        setDHL(dHLAfterChange);
                        this.varPara_dHL = varPara.dHL;

                        ff = 0;
                        startPump(varPara.num, varPara.dHL[varPara.num],varPara.dPL[varPara.num],varPara.waterL[varPara.num]);
                        highPointExhaust(varPara.highOpen, varPara.num,varPara.waterL[varPara.num]);
//                            if (varPara.waterL[varPara.num]>=248100){
//                                for (int x = 248100/500+1; x <=402400/500+1; x++) {
//                                    varPara.dHL[varPara.num][x]=varPara.dHL[varPara.num][x]-100;
//                                }
//                            }

                        //if (varPara.num%10==0){

                        if (varPara.checkP != 0 && message==0 && varPara.ok==0) {       //????????????????????????????????????????????????????????????????????????????????????

                            //if (varPara.ssr[varPara.i-13][0] !=0 && varPara.checkP != 0) {       //????????????????????????????????????????????????????????????????????????????????????

                            //do{//??????????????????
                            //??????
                            stopPump1(varPara.num, varPara.waterL[varPara.num]);
                            //??????
//                        staticP_new(n, varPara.waterL[varPara.num], varPara.deltaX);
                            varPara.kkstop=kk;//??????????????????
                            varPara.numstop=varPara.num;//??????????????????
                            varPara.rrstop=rr;//??????????????????
                            System.out.println("???"+varPara.num/24.0+"h??????"+"???"+varPara.stopT+"???????????????");
                            if (varPara.stopT>10)  varPara.ok=1;
                            //System.out.println("???"+varPara.num/12.0+"h??????"+"???"+varPara.stopT+"???????????????");
//                        //????????????
//                        for (int cc = 1; cc < n-2; cc++) {//????????????????????????????????????????????????????????????
//                            if (varPara.lgk[(int)varPara.maxThree[0][0]]<varPara.lgk[cc]){
//                                varPara.maxThree[0][0]=cc;
//                                varPara.maxThree[0][1]=varPara.lgk[cc];
//                            }
//                        }
//                        for (int cc = 1; cc < n-2; cc++) {//??????????????????????????????????????????????????????????????????
//                            if (cc!=varPara.maxThree[0][0]&&varPara.lgk[(int)varPara.maxThree[1][0]]<varPara.lgk[cc]){
//                                varPara.maxThree[1][0]=cc;
//                                varPara.maxThree[1][1]=varPara.lgk[cc];
//                            }
//                        }
//                        for (int cc = 1; cc < n-2; cc++) {//??????????????????????????????????????????????????????????????????
//                            if (varPara.lgk[(int)varPara.maxThree[2][0]]<varPara.lgk[cc] && cc!=varPara.maxThree[0][0] && cc!=varPara.maxThree[1][0]){
//                                varPara.maxThree[2][0]=cc;
//                                varPara.maxThree[2][1]=varPara.lgk[cc];
//                            }
//                        }
//
//                        System.out.println("?????????????????????"+varPara.maxThree[0][0]+"???,?????????"+varPara.maxThree[0][1]);
//                        System.out.println("???????????????????????????"+varPara.maxThree[1][0]+"???,?????????"+varPara.maxThree[1][1]);
//                        System.out.println("???????????????????????????"+varPara.maxThree[2][0]+"???,?????????"+varPara.maxThree[2][1]);
////                        System.out.println("rr-6 = " + (rr-6));
////                        for (int j = 0; j < varPara.Pg_his[(int)varPara.maxThree[0][0]].length; j++) {
////                            varPara.sumTest=varPara.Pg_his[(int)varPara.maxThree[0][0]][j]+varPara.sumTest;
////                            if (varPara.Pg_his[(int)varPara.maxThree[0][0]][j]>0 && varPara.Pg_his[(int)varPara.maxThree[0][0]][j+1]==0){
////                                System.out.println("rr-6 jjjj= " + j);
////                            }
////                        }
////                        System.out.println("1000000*varPara.Pg_his[(int)varPara.maxThree[0][0]][rr-25] = " + 1000000*varPara.Pg_his[(int)varPara.maxThree[0][0]][rr-2]);
////                        System.out.println(" varPara.sumTest = " +  varPara.sumTest);
//
//                        if (varPara.Pg_his[(int)varPara.maxThree[0][0]][rr-2]==0.0){
//                            for (rrr0=0; rrr0<varPara.Pg_his[0].length-2;rrr0++){
//                                if (varPara.Pg_his[(int)varPara.maxThree[0][0]][rrr0]>0.0 && varPara.Pg_his[(int)varPara.maxThree[0][0]][rrr0+1]==0){
////                                    System.out.println("??????1111111111111111111111111111111111111111111111111111");
//                                    break;
//                                    }
//                            }
//                        }else{
//                            rrr0=rr-3;
////                            System.out.println("??????2222222222222");
//                        }

                            for (int j = 0; j < varPara.staV.size(); j++) {
                                if (varPara.waterL[varPara.num]>(double)varPara.staV.get(j)*1000){
                                    timeStop=exitGas_stop((j+1),getI(varPara.line_l,(double)varPara.staV.get(j)*1000),varPara.num, varPara.M_his[getI(varPara.line_l,(double)varPara.staV.get(j)*1000)][rrr0],
                                            1000000*varPara.Pg_his[getI(varPara.line_l,(double)varPara.staV.get(j)*1000)][rrr0],
                                            1000*varPara.lg_his[getI(varPara.line_l,(double)varPara.staV.get(j)*1000)][rrr0])[2];
                                    message++;
                                }
                            }





                            startPumpAgain(varPara.num,timeStop);

                            varPara.stopT++;
                            varPara.checkP = 0;
//                        //????????????
//                        loop: for (int cc = 1; cc <= n; cc++) {
//                            for (int stationNum=0;stationNum<stations.size();stationNum++){//????????????????????????????????????
//                                if (stations.get(stationNum).getStationType()==2.0 && varPara.line_l[cc][2] >= varPara.stationLLL[stationNum] && varPara.line_l[cc][1] <= varPara.stationLLL[stationNum]){
//                                    exitGas(cc,stationNum,varPara.M_his[cc][rr-2],1000000*varPara.Pg_his[cc][rr-2],1000*varPara.lg_his[cc][rr-2],varPara.h1_his[cc][rr-2],varPara.h2_his[cc][rr-2]);
//                                    message++;
//                                    break loop;
//                                }
//                            }
//                        }
                        }

                        /**
                         *
                         */
                        for (int pigNumber=0;pigNumber<pigs.size();pigNumber++){
                            if (pigNumber==0 && varPara.waterL[varPara.num] > stations.get(0).getStationL()+50000
                                    && stations.get(0).getStationType()==1.0 && varPara.pigFlag==1 && varPara.pigflag2[pigNumber]==0)
                            {//???????????????????????????50km
                                varPara.pigflag1=1;
                                if (flagpigT[pigNumber]==0)
                                {
                                    flagpigT[pigNumber]=varPara.num;
                                    System.out.println("????????????"+flagpigT[pigNumber]/24.0+"h," + "???"+stations.get(0).getStationName()+stations.get(0).getStationL()+"m?????????1");
                                    //System.out.println("????????????"+flagpigT/12.0+"h," + "???"+stations.get(stationNum00).getStationL()+"m?????????");
                                }

                                varPara.pigV[0][0]=conPara.Ql/A;      //??????????????????????????????
                                varPara.pigL[0][0]=stations.get(0).getStationL();  //??????????????????????????????


                                //double []dPLLL=varPara.dPL[varPara.num];//?????????????????????????????????????????????
                                double []dPLLL=chazhi(varPara.dPL[varPara.num],499);//?????????????????????????????????????????????

                                varPara.pigNum[pigNumber]++;


                                if ((int)(varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]-LLt)<0||(int)(varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]-LLt)>500){
                                    Ha[pigNumber] = 1e5+5000;
                                    Hb[pigNumber] = 1e5;
                                }else{
                                    if ((int)(varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]-LLt)-1<0){
                                        //Ha
                                        Ha[pigNumber] = dPLLL[0];
                                        //Hb
                                        Hb[pigNumber] = dPLLL[1];
                                    }else{
                                        //Ha
                                        Ha[pigNumber] = dPLLL[(int)(varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]-LLt)-1];
                                        //Hb
                                        Hb[pigNumber] = dPLLL[(int)(varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]-LLt)];
                                    }

                                }


                                //
                                varPara.pigV[pigNumber][varPara.pigNum[pigNumber]]=getPigV(Ha[pigNumber],Hb[pigNumber],varPara.pigV[pigNumber][varPara.pigNum[pigNumber]-1],varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]
                                        ,varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]+500,varPara.getPgk()[i],
                                        3000,varPara.pigL[0][0],getPhi(varPara.line_l,varPara.line_d,varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]))[2];
                                if(varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]>varPara.waterL[varPara.num]-15000){
                                    varPara.pigV[pigNumber][varPara.pigNum[pigNumber]]=0.95*conPara.Ql/A;
                                }else if (varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]>varPara.waterL[varPara.num]-20000) {
                                    varPara.pigV[pigNumber][varPara.pigNum[pigNumber]]=conPara.Ql/A;
                                }
                                varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]= varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]+
                                        0.5*(varPara.pigV[pigNumber][varPara.pigNum[pigNumber]-1]+varPara.pigV[pigNumber][varPara.pigNum[pigNumber]-1])*200;/////////////////?????????300
                                varPara.pigZ[pigNumber][varPara.pigNum[pigNumber]]= getZ(varPara.line_l,varPara.line_d,varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]);



                                for (int stationNum11=p[pigNumber];stationNum11<varPara.pumpSta.size();stationNum11++){
                                    if ((double)varPara.pumpSta.get(stationNum11)!=0 && varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]>
                                            (double)varPara.pumpSta.get(stationNum11))
                                    {
                                        p[pigNumber]++;
                                        if (varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]>(double)varPara.pumpSta.get(stationNum11)){//?????????????????????????????????
                                            //varPara.pigFlag=1;//?????????????????????????????????
                                            varPara.pigflag1=0;//?????????????????????????????????save???????????????
                                        }
                                        varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]=(double)varPara.pumpSta.get(stationNum11);
                                        varPara.pigZ[pigNumber][varPara.pigNum[pigNumber]]= getZ(varPara.line_l,varPara.line_d,varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]);
                                        //????????????????????????????????????????????????????????????????????????1h???20210617
                                        varPara.pigflag2[pigNumber]=1;
                                        System.out.println("????????????"+(varPara.pigNum[pigNumber]+flagpigT[pigNumber])/24.0+"h," + "???"+stations.get(stationNum11).getStationL()+"m?????????");
                                        //System.out.println("????????????"+(varPara.pigNum[pigNumber+flagpigT)/12.0+"h," + "???"+stations.get(stationNum00+1).getStationL()+"m?????????");
                                        break;
                                    }else if( stationNum11==varPara.pumpSta.size()-1 && varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]>(double)varPara.pumpSta.get(stationNum11)-1000){
                                        //???????????????
                                        p[pigNumber]++;
                                        varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]=(double)varPara.pumpSta.get(stationNum11);
                                        //????????????????????????????????????????????????????????????????????????1h???20210617
                                        //varPara.pigflag2=1;
                                        System.out.println("????????????"+(varPara.pigNum[pigNumber]+flagpigT[pigNumber])/24.0+"h," + "???"+stations.get(stationNum11).getStationL()+"m?????????");
                                        //System.out.println("????????????"+(varPara.pigNum[pigNumber+flagpigT)/12.0+"h," + "???"+stations.get(stationNum00+1).getStationL()+"m?????????");

                                        varPara.pigFlag=1;
                                        break;
                                    }
                                }


//                            for (int j = 1; j < varPara.line_l.length; j++) {
//                                if (varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]>varPara.line_l[j][2]){
//                                    varPara.ssr[j][0]=1;
//                                }
//                            }
                            }else if(varPara.pigflag2[pigNumber]==1)
                            {
                                flagpigT[pigNumber]++;
                                varPara.pigNum[pigNumber]++;
                                varPara.pigTflag[pigNumber]++;
                                varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]=varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1];
                                varPara.pigZ[pigNumber][varPara.pigNum[pigNumber]]= getZ(varPara.line_l,varPara.line_d,varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]);
                                varPara.pigV[pigNumber][varPara.pigNum[pigNumber]]=0;
                                if (varPara.pigTflag[pigNumber]>5){
                                    varPara.pigTflag[pigNumber]=0;
                                    varPara.pigflag2[pigNumber]=0;//??????????????????????????????5?????????????????????????????????
                                    System.out.println("????????????"+(varPara.pigNum[pigNumber]+flagpigT[pigNumber])/24.0+"h," + "???"+varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]+"m?????????");
                                }
                            }

                            if (pigNumber>0 && varPara.pigNum[pigNumber-1]>1200 && varPara.pigFlag==1)
                            {

                                if (varPara.pigNum[pigNumber]==0)
                                {
                                    varPara.pigNum[pigNumber]=varPara.num;
                                    System.out.println("?????????" +(pigNumber+1)+"???"+varPara.pigNum[pigNumber]/24.0+"h," + "???"+stations.get(0).getStationName()+stations.get(0).getStationL()+"m?????????1");
                                    //System.out.println("????????????"+flagpigT/12.0+"h," + "???"+stations.get(stationNum00).getStationL()+"m?????????");
                                }

                                varPara.pigV[0][0]=conPara.Ql/A;      //??????????????????????????????
                                varPara.pigL[0][0]=stations.get(0).getStationL();  //??????????????????????????????


                                //double []dPLLL=varPara.dPL[varPara.num];//?????????????????????????????????????????????
                                double []dPLLL=chazhi(varPara.dPL[varPara.num],499);//?????????????????????????????????????????????

                                varPara.pigNum[pigNumber]++;


                                if ((int)(varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]-LLt)<0||(int)(varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]-LLt)>500){
                                    Ha[pigNumber] = 1e5+5000;
                                    Hb[pigNumber] = 1e5;
                                }else{
                                    if ((int)(varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]-LLt)-1<0){
                                        //Ha
                                        Ha[pigNumber] = dPLLL[0];
                                        //Hb
                                        Hb[pigNumber] = dPLLL[1];
                                    }else{
                                        //Ha
                                        Ha[pigNumber] = dPLLL[(int)(varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]-LLt)-1];
                                        //Hb
                                        Hb[pigNumber] = dPLLL[(int)(varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]-LLt)];
                                    }

                                }


                                //
                                varPara.pigV[pigNumber][varPara.pigNum[pigNumber]]=getPigV(Ha[pigNumber],Hb[pigNumber],varPara.pigV[pigNumber][varPara.pigNum[pigNumber]-1],varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]
                                        ,varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]+500,varPara.getPgk()[i],
                                        3000,varPara.pigL[0][0],getPhi(varPara.line_l,varPara.line_d,varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]))[2];

                                varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]= varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1]+
                                        0.5*(varPara.pigV[pigNumber][varPara.pigNum[pigNumber]-1]+varPara.pigV[pigNumber][varPara.pigNum[pigNumber]-1])*200;/////////////////?????????300
                                varPara.pigZ[pigNumber][varPara.pigNum[pigNumber]]= getZ(varPara.line_l,varPara.line_d,varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]);

                                if(varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]>varPara.waterL[varPara.num]-15000){
                                    varPara.pigV[pigNumber][varPara.pigNum[pigNumber]]=0.95*conPara.Ql/A;
                                }else if (varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]>varPara.waterL[varPara.num]-20000) {
                                    varPara.pigV[pigNumber][varPara.pigNum[pigNumber]]=conPara.Ql/A;
                                }

                                for (int stationNum11=p[pigNumber];stationNum11<varPara.pumpSta.size();stationNum11++){
                                    if ((double)varPara.pumpSta.get(stationNum11)!=0 && varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]>
                                            (double)varPara.pumpSta.get(stationNum11))
                                    {
                                        p[pigNumber]++;
                                        if (varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]>(double)varPara.pumpSta.get(stationNum11)){//?????????????????????????????????
                                            //varPara.pigFlag=1;//?????????????????????????????????
                                            varPara.pigflag1=0;//?????????????????????????????????save???????????????
                                        }
                                        varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]=(double)varPara.pumpSta.get(stationNum11);
                                        varPara.pigZ[pigNumber][varPara.pigNum[pigNumber]]= getZ(varPara.line_l,varPara.line_d,varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]);
                                        //????????????????????????????????????????????????????????????????????????1h???20210617
                                        varPara.pigflag2[pigNumber]=1;
                                        System.out.println("?????????" +(pigNumber+1)+"???"+(varPara.pigNum[pigNumber])/24.0+"h," + "???"+stations.get(stationNum11).getStationL()+"m?????????");
                                        //System.out.println("????????????"+(varPara.pigNum[pigNumber+flagpigT)/12.0+"h," + "???"+stations.get(stationNum00+1).getStationL()+"m?????????");
                                        break;
                                    }else if((double)varPara.pumpSta.get(stationNum11)!=0 && stationNum11==varPara.pumpSta.size()-1 && varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]>(double)varPara.pumpSta.get(stationNum11)-1000){
                                        //???????????????
                                        p[pigNumber]++;
                                        varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]=(double)varPara.pumpSta.get(stationNum11);
                                        //????????????????????????????????????????????????????????????????????????1h???20210617
                                        //varPara.pigflag2=1;
                                        System.out.println("?????????" +(pigNumber+1)+"???"+(varPara.pigNum[pigNumber])/24.0+"h," + "???"+stations.get(stationNum11).getStationL()+"m?????????");
                                        //System.out.println("????????????"+(varPara.pigNum[pigNumber+flagpigT)/12.0+"h," + "???"+stations.get(stationNum00+1).getStationL()+"m?????????");

                                        varPara.pigFlag=1;
                                        break;
                                    }
                                }
                                if (pigNumber>0){
                                    varPara.Mg[varPara.num] = varPara.Mg[varPara.num]-varPara.Mg[varPara.num]*(varPara.pigL[pigNumber][varPara.num]-varPara.pigL[pigNumber][varPara.num-1])/(varPara.line_l[varPara.line_l.length-1][3]-varPara.line_l[1][1]);
                                }
                            }else if(varPara.pigflag2[pigNumber]==1)
                            {
                                flagpigT[pigNumber]++;
                                varPara.pigNum[pigNumber]++;
                                varPara.pigTflag[pigNumber]++;
                                varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]=varPara.pigL[pigNumber][varPara.pigNum[pigNumber]-1];
                                varPara.pigZ[pigNumber][varPara.pigNum[pigNumber]]= getZ(varPara.line_l,varPara.line_d,varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]);
                                varPara.pigV[pigNumber][varPara.pigNum[pigNumber]]=0;
                                if (varPara.pigTflag[pigNumber]>5){
                                    varPara.pigTflag[pigNumber]=0;
                                    varPara.pigflag2[pigNumber]=0;//??????????????????????????????5?????????????????????????????????
                                    System.out.println("?????????" +(pigNumber+1)+"???"+(varPara.pigNum[pigNumber])/24.0+"h," + "???"+varPara.pigL[pigNumber][varPara.pigNum[pigNumber]]+"m?????????");
                                }
                            }





                        }


                        //???????????????
                        for (int rrr=1;rrr<=n;rrr++){
                            if (varPara.MGk[rrr]>1.0 && varPara.pigL[0][varPara.pigNum[0]]>=varPara.line_l[rrr][1] && varPara.pigL[0][varPara.pigNum[0]]<varPara.line_l[rrr][2]){//????????????????????????????????????????????????????????????10km??????
                                AddMg[rrr]=varPara.MGk[rrr]*(varPara.pigV[0][varPara.pigNum[0]]*300)/(varPara.line_l[rrr][2]-varPara.line_l[rrr][1]);       //????????????????????????????????????????????????
                            }
                            AddMg_his[rrr][varPara.num] = AddMg[rrr];
                        }

                        //??????????????????
                        //                    for (int kkk=varPara.num;kkk<(int)varPara.T*12+1;kkk++)//????????????
                        //                    {

                        flagFP = varPara.line_l[1][1]+500;
                        for (int xNum = 1; flagFP < varPara.waterL[varPara.num]; xNum++) {
                            varPara.allLineFP[varPara.num][xNum] = getFlowPattern(getI(varPara.line_l, flagFP), flagFP, varPara.pigL[0][0],varPara.pigL[0][varPara.pigNum[0]], flagpigT[0],varPara.num);       //???????????????1???????????????0???????????????2???????????????3?????????????????????
                            varPara.allLineStaticP[varPara.num][xNum] = getLiquidFlowRate(getI(varPara.line_l, flagFP), flagFP, varPara.pigL[0][0],varPara.pigL[0][varPara.pigNum[0]], flagpigT[0]);       //???????????????1???????????????0???????????????2???????????????3?????????????????????


                            flagFP = flagFP + 500;

                            /**
                             * ??????allLineFP
                             */
//                            SizeChange allLineFPChange = new SizeChange(varPara.allLineFP);
//                            Map<Double, double[]> allLineFPAfterChange = allLineFPChange.ResultAfterChange(2,2.5);
                            //                        System.out.println("varPara.allLineFP: " + allLineFPAfterChange);
//                            setAllLineFP(allLineFPAfterChange);
                            this.varPara_allLineFP = varPara.allLineFP;
//                            this.allLineFP = allLineFPAfterChange;
//                            setAllLineFP(allLineFPAfterChange);
                        }
                        for (int ii = 0; ii <= n; ii++) {
                            varPara.dMg_sum=AddMg[ii]+varPara.dMg_sum;
                        }

                        for (int ii = 1; ii < varPara.dMg_p.length; ii++) {
                            varPara.dMg_p[0][varPara.num]=varPara.dMg_p[ii][varPara.num]+varPara.dMg_p[0][varPara.num];
                        }

                        if (timeStop!=0){

                            if (varPara.dMg_p[0][varPara.num]!=0 && varPara.dMg_p[0][varPara.num]>varPara.dMg_p[0][varPara.num-1]){
                                if (varPara.Mg[varPara.num]>50) {
                                    varPara.Mg[varPara.num] = varPara.Mg[varPara.num]-(varPara.dMg_p[0][varPara.num]-varPara.dMg_p[0][varPara.num-1]);
                                }else{
                                    varPara.Mg[varPara.num] = 0.99*varPara.Mg[varPara.num];
                                }
                            }
                            varPara.gasRa[varPara.num]=varPara.Mg[varPara.num]/varPara.waterL[varPara.num]*A;
                        }
                    }
                }

                for (int x = 1; x <=(varPara.line_l[varPara.i-1][3]-varPara.line_l[1][1])/500; x++) {
                    if (varPara.dPL[varPara.num][x]<101325){
                        varPara.dPL[varPara.num][x]=101325;
                    }
                }
                for (int x = 1; x <=(varPara.waterL[varPara.num]-varPara.line_l[1][1])/500; x++) {
                    if (varPara.dPL[varPara.num][x]<301326){
                        varPara.dPL[varPara.num][x]=varPara.dPL[varPara.num][x-1]*0.90;
                    }
                }
                //System.out.println("???????????????"+waterHeadLocation[kk]/1000+"km");
//                if ((varPara.ssr[1][0]==1 && varPara.ssr[2][0]==1 && varPara.ssr[3][0]==1 && varPara.ssr[varPara.i-1][0]==1)){
//                    System.out.println("??????!");
//                    break;
//                }
                ttt++;
                if (n==varPara.i-1){
                    if (ttt>180000){
                        staticP_new(n, varPara.waterL[varPara.num], varPara.deltaX);
                        System.out.println("??????????????????????????????80h???????????????!");
                        break;
                    }
                }
                //????????????????????????
                if (kk== varPara.kt){
                    staticP_new(n, varPara.waterL[varPara.num], varPara.deltaX);
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        for (int j = 0; j < varPara.num+2500; j++) {
            if (varPara.Mg[j]!=0&&varPara.Mg[j+1]==0){
                op=j+1;
                System.out.println("op ============================= " + op);
                break;
            }
        }
        for (int j = 0; j < varPara.num+2500; j++) {
            if (varPara.oilL[j]!=0&&varPara.oilL[j+1]==0){
                opp=j+1;
                System.out.println("opp ============================= " + opp);
                System.out.println("varPara.oilL[opp-1] ============================= " + varPara.oilL[opp-1]);
                break;
            }
        }
        for (int k=opp;k<opp+3000;k++){
            double flagFP_new=0;
            if (k==0) k=1;
            varPara.oilL[k] = varPara.oilL[k-1] + 1500*varPara.Qh/3600.0/A;
            varPara.waterL[k] = varPara.waterL[k-1] + 1500*varPara.Qh/3600.0/A;
            if (varPara.oilL[k]>varPara.line_l[varPara.line_l.length-1][3]){
                varPara.oilL[k]=varPara.line_l[varPara.line_l.length-1][3];
                break;
            }
            if (varPara.waterL[k]>varPara.line_l[varPara.line_l.length-1][3]){
                varPara.waterL[k]=varPara.line_l[varPara.line_l.length-1][3];
            }
            for (int xNum = 1; flagFP_new < varPara.waterL[k]; xNum++) {
                //varPara.allLine[0][rr]=varPara.waterHeadLocation[rr];       //????????????
                //varPara.allLine[1][rr]=getZ(varPara.line_l, varPara.line_d,varPara.waterHeadLocation[rr]);      //???????????????????????????
                varPara.allLineFP[k][xNum] = varPara.allLineFP[k-1][xNum];
                varPara.allLineFP[k][xNum] = getFlowPattern(getI(varPara.line_l, flagFP_new), flagFP_new, varPara.pigL[0][0],varPara.pigL[0][varPara.pigNum[0]], flagpigT[0],k);       //???????????????1???????????????0???????????????2???????????????3?????????????????????
                //System.out.println("varPara.allLineFP[" + kkk + "][" + xNum + "]=" + varPara.allLineFP[kkk][xNum]);
                flagFP_new = flagFP_new + 500;
                //varPara.allLine[3][rr]=getI(varPara.line_l, varPara.waterHeadLocation[rr]);       //???????????????
                //varPara.allLineFP[varPara.num][xNum]=getFlowPattern(getI(varPara.line_l, varPara.waterHeadLocation[rr]),varPara.waterHeadLocation[rr],varPara.pigL[varPara.num]);;

            }

        }
        for (int j = op/4+1; j < varPara.k; j++) {
            if(j>op-1){
//                if (varPara.Mg[j-1]>0.1*conPara.Ql*3600/869.0*varPara.Mg[op-1]){
                if (varPara.Mg[j-1]>0&&varPara.Mg[j-1]<180000){
                    varPara.Mg[j] = varPara.Mg[j-1]*(0.9995+0.0001*(100000.0/varPara.Mg[op-1]));
                    if ((0.9995+0.0001*(100000.0/varPara.Mg[op-1]))>=0.9999){
                        varPara.Mg[j] = varPara.Mg[j-1]*(0.9999);
                    }
                }else{
                    varPara.Mg[j] = varPara.Mg[j-2]*(0.9995+0.0001*(100000.0/varPara.Mg[op-1]));
                    if ((0.9995+0.0001*(100000.0/varPara.Mg[op-1]))>=0.9999){
                        varPara.Mg[j] = varPara.Mg[j-1]*(0.9999);
                    }
                }
                varPara.gasRa[j]=varPara.Mg[j]/varPara.waterL[varPara.num]*A;

                if (varPara.Mg[j]<5){
                    break;
                }

            }
            if (varPara.pigL[0][j-op/4]>0){
                if (varPara.Mg[j-1]>0&&varPara.Mg[j-1]<180000){
                    varPara.Mg[j] = varPara.Mg[j]-0.7*varPara.Mg[j]*(varPara.pigL[0][j-op/4]-varPara.pigL[0][j-op/4-1])/(varPara.line_l[varPara.line_l.length-1][3]-varPara.line_l[1][1]);
                }else{
                    varPara.Mg[j] = varPara.Mg[j]-0.7*varPara.Mg[j]*(varPara.pigL[0][j-op/4]-varPara.pigL[0][j-op/4-1])/(varPara.line_l[varPara.line_l.length-1][3]-varPara.line_l[1][1]);
                }
                varPara.gasRa[j]=varPara.Mg[j]/varPara.waterL[varPara.num]*A;
            }


        }

        varPara.dPL[0][0]=varPara.line_l[1][1]/1000;
        varPara.allLineFP[0][0]=varPara.line_l[1][1]/1000;
        for (int x = 1; x <=(varPara.line_l[varPara.i-1][3]-varPara.line_l[1][1])/500+1; x++) {
            varPara.dPL[0][x]=0.5+varPara.dPL[0][x-1];//???????????????
            //varPara.dHL[0]                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      [x]=getZ(varPara.line_l, varPara.line_d,varPara.dHL[0][x]*1000);
//            varPara.dPL[varPara.num+3][x]=2499.385+varPara.dPL[varPara.num][x]/9.81/1000;
            varPara.allLineFP[0][x]=0.5+varPara.allLineFP[0][x-1];
        }
        // dHL_static(varPara.num, varPara.deltaX, varPara.waterL);//????????????????????????
        dHL_static_new(varPara.dPL);//????????????????????????

        this.varPara_dHL = varPara.dHL;



        try {
            Output.OutToTXT1(varPara.Pg_his, "Pg_his");
            Output.OutToTXTL(varPara.Hss,"Hss");
            Output.OutToTXTDHL(varPara.dHL,"dHL");
            Output.OutToTXTDHL(this.varPara_dHL,"_dHL");
            Output.OutToTXTDHL(varPara.dHL_new,"dHL_new");
            Output.OutToTXTLLL(varPara.Mg,"Mg",0);
            Output.OutToTXTL(varPara.dPL, "dPL");

            /**
             * simple+++
             */
            Output.OutToTXTQ(varPara.allQ, "Q");

            Output.OutToTXTFP(varPara.allLineFP, "allLineFP");
            Output.OutToTXT1(varPara.lg_his, "lg_his");
            Output.OutToTXT(varPara.waterHeadLocation,"waterHeadLocation");
            Output.OutToTXT(varPara.oilHeadLocation,"oilLocation");
            Output.OutToTXT1(varPara.dMg_out_his, "dMg_out_his");
            Output.OutToTXT1(varPara.M_his, "M_his");

            /**
             * ????????????????????????+++
             */
            Output.OutToTXTLLL(varPara.pigL[0], "pigL", flagpigT[0]);

            Output.OutToTXT123(varPara.dMg_p, "dMg_p");


            /**
             * M_his+++   ??????1s
             */
            Output.OutToTXT123(varPara.dMg_pk, "dMg_pk");

            Output.OutToTXTLLL(varPara.pigV[0], "pigV", flagpigT[0]);
            Output.OutToTXTLLL(varPara.pigZ[0], "pigZ", flagpigT[0]);
            Output.OutToTXTPPP(varPara.allLineStaticP[0], "allLineStaticP", varPara.line_l[1][1]);
            Output.OutToTXTZZZ(varPara.allLineStaticP[1], "allLineZ", varPara.line_l[1][1]);
            Output.OutToTXT1(AddMg_his,"AddMg_his");
            Output.OutToTXTLLL(varPara.Mg,"Mg",0);

            /**
             * simple+++   ==mG
             */
            Output.OutToTXTLLL(varPara.gasRa,"gasRa",0);


            Output.OutToTXTLLL(varPara.oilL,"oilL",0);
            Output.OutToTXTLLL(varPara.waterL,"waterL",0);
            Output.OutToTXTFP(varPara.allLineStaticP,"allLineYS");
        }catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * ????????????,??????lg_his pg_his mg_his line_l dMg_out_his
         */
        double[][] lgHisReverse = SizeChange.reverse(varPara.lg_his);
        SizeChange lgHisChange = new SizeChange(lgHisReverse);
        Map<Double, double[]> lgHisAfterChange = lgHisChange.ResultAfterChange(10,0.5);
        setLgHis(lgHisAfterChange);
        double[][] pgHisReverse = SizeChange.reverse(varPara.Pg_his);
        SizeChange pgHisChange = new SizeChange(pgHisReverse);
        Map<Double, double[]> pgHisAfterChange = pgHisChange.ResultAfterChange(10,0.5);
        setPgHis(pgHisAfterChange);
        double[][] mHisReverse = SizeChange.reverse(varPara.M_his);
        SizeChange mHisChange = new SizeChange(mHisReverse);
        Map<Double, double[]> mHisAfterChange = mHisChange.ResultAfterChange(10,0.5);
        setmHis(mHisAfterChange);

        double[][] dMgHisReverse = SizeChange.reverse(varPara.dMg_out_his);
        SizeChange dMgHisChange = new SizeChange(dMgHisReverse);
        Map<Double, double[]> dMgHisAfterChange = dMgHisChange.ResultNoChange(10,0.5);
        setdMgHis(dMgHisAfterChange);

        setLine_L(varPara.line_l);


        /**
         * ??????Hss
         */
        List<String> stationName = new ArrayList<>();
        for (Stations station : stations) {
            stationName.add(station.getStationName());
        }
        SizeChange hSSChange = new SizeChange(varPara.Hss);
        Map<String, Map<Double, double[]>> hSSAfterChange = hSSChange.ResultAfterChange(2, 2.5, stationName);
        for (String s : stationName) {
            System.out.print(s + " ");
        }
        setHSS(hSSAfterChange);

        /**
         * ??????????????????,??????pig_V pig-L allLineStaticP Q dMgPK
         * Mg
         * ????????????:flagpigT
         */
        SizeChange mGChange = new SizeChange(varPara.Mg,0.0,(1.0/24));
        double[][] mGAfterChange = mGChange.ResultAfterChange2();
        setmG(mGAfterChange);
        SizeChange gasRaChange = new SizeChange(varPara.gasRa,0.0,(1.0/24));
        double[][] gasRaAfterChange = gasRaChange.ResultAfterChange2();
        setGasRa(gasRaAfterChange);
        SizeChange pigLChange = new SizeChange(varPara.pigL[0],flagpigT[0]/24,(1.0/24));
        double[][] pigLAfterChange = pigLChange.ResultAfterChange2();
        setPigL(pigLAfterChange);
        SizeChange pigVChange = new SizeChange(varPara.pigV[0],flagpigT[0]/24,(1.0/24));
        double[][] pigVAfterChange = pigVChange.ResultAfterChange2(pigLAfterChange);
        setPigV(pigVAfterChange);
        SizeChange dMgPChange = new SizeChange(varPara.dMg_p);
        double[][] dMgPAfterChange = dMgPChange.ResultAfterChange3();
        setDMgP(dMgPAfterChange);
        SizeChange dMgPKChange = new SizeChange(varPara.dMg_pk);
        Map<String, double[][]> dMgPKAfterChange = dMgPKChange.ResultAfterChange4();
        setDMgPK(dMgPKAfterChange);
        SizeChange aLSPChange = new SizeChange(varPara.allLineStaticP[0],248,0.5);
        double[][] aLSPAfterChange = aLSPChange.ResultAfterChange2();
        setaLSP(aLSPAfterChange);
        SizeChange qChange = new SizeChange(varPara.allQ);
        double[] arr = qChange.List2Array();
        setQ(arr);

        end1 = System.currentTimeMillis();
        System.out.println("???????????? start time:" + start1+ "; end time:" + end1+ "; Run Time:" + (end1 - start1) + "(ms)");
        end3 = System.currentTimeMillis();
        System.out.println("TXT???????????? start time:" + end1+ "; end time:" + end3+ "; Run Time:" + (end3 - end1) + "(ms)");

    }
    //??????????????????????????????
    public  double calDelta(double slopeD) {
        Pipeline pipeLine = pipeLines.get(0);
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
        Pipeline pipeLine = pipeLines.get(0);
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
    //????????????????????????????????????----
    public  double cal3(double h2, double Hgk, double za, double zb, double slopeD, double slopeU, double MG, double P2){
        Pipeline pipeLine = pipeLines.get(0);
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

    // ????????????U????????????????????????????????????
//i???U?????????????????????????????????1???a???a???U???????????????
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
    //-----------------??????????????????delta--------------------------
    //--------------delta????????????????????????rad --------
    public  double calVen(double slopeD,double Pgk) {
        Pipeline pipeLine = pipeLines.get(0);
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
        Pipeline pipeLine = pipeLines.get(0);
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
        double[][]   X = new double[][]{{a11,a12,0,0,0},{0,a22,0,a24,0},{0,0,a33,a34,a35},{0,0,0,a44,0},{0,a52,a53,0,0}};
        return X;
    }

    /*???????????????lgk?????????U?????????,??????k???????????????????????????
     *          backPressure_k?????????U?????????,??????k??????????????????
     *          mgk_k?????????U?????????,??????k???????????????????????????????????????????????????U????????????????????????U?????????????????????
     *          pgk?????????U?????????,??????k???????????????????????????
     *          Hgk?????????U?????????,??????k????????????????????????????????????
     *          slopeU?????????U??????????????????????????????sin??????
     *          slopeD?????????U??????????????????????????????sin??????
     *          uk?????????U?????????,??????k??????????????????????????????
     *          h1k?????????U?????????,??????k????????????????????????????????????
     *          h2k?????????U?????????,??????k????????????????????????????????????
     * */
    public  double[][] matrixAssemblyZ(double lgk, double backPressure_k, double mgk, double Pgk, double Hgk, double slopeU, double slopeD, double uk, double h1k, double h2k){
        Pipeline pipeLine = pipeLines.get(0);
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
        double[][] Z = new double[][]{{b1},{b2},{b3},{b4},{b5}};      //????????????
        return Z;
    }


    public  void Save_his(int i,int n, int k) {

        for (i=1;i<=n;i++){
            varPara.Pg_his[i][k] = varPara.Pgk[i]/1000000.0;           //???????????????Pa,?????????MPa
            if (varPara.Pg_his[i][k-1]*varPara.Pgk[i]<0 || Double.isNaN(varPara.Pgk[i]))  {
                varPara.Pg_his[i][k]=varPara.Pg_his[i][k-1];
                varPara.lg_his[i][k]=varPara.lg_his[i][k-1];
                varPara.M_his[i][k]=varPara.M_his[i][k-1];
            }
            if (varPara.Pg_his[i][k]>0.5e6)  varPara.Pg_his[i][k]=0.5e6;
            varPara.lg_his[i][k]= varPara.lgk[i]/1000.0;             //????????????????????????m????????????km
            if (varPara.lg_his[i][k-1]*varPara.lgk[i]<0 || Double.isNaN(varPara.lgk[i]))  {
                varPara.Pg_his[i][k]=varPara.Pg_his[i][k-1];
                varPara.lg_his[i][k]=varPara.lg_his[i][k-1];
                varPara.M_his[i][k]=varPara.M_his[i][k-1];
            }
            if (varPara.lg_his[i][k]*1000>3*(varPara.line_l[i][2]-varPara.line_l[i][1])){
                varPara.lg_his[i][k]=(varPara.line_l[i][2]-varPara.line_l[i][1])/1000;
            }
            varPara.h1_his[i][k] = varPara.h1k[i];             //??????????????????m
//            if (varPara.h1_his[i][k]!=0 && varPara.pigflag1==1 &&
//                    (varPara.h1_his[i][k]/varPara.h1_his[i][k-1]>5||varPara.h1_his[i][k]/varPara.h1_his[i][k-1]<-5))  {
//                varPara.h1_his[i][k]=varPara.h1_his[i][k-1];
//                //varPara.h1k[i]=varPara.h1_his[i][k-1];
//            }
            varPara.h2_his[i][k] = varPara.h2k[i];             //??????????????????m
//            if (varPara.h2_his[i][k]!=0 && varPara.pigflag1==1 &&
//                    (varPara.h2_his[i][k]/varPara.h2_his[i][k-1]>5||varPara.h2_his[i][k]/varPara.h2_his[i][k-1]<-5))  {
//                varPara.h2_his[i][k]=varPara.h2_his[i][k-1];
//                //varPara.h2k[i]=varPara.h2_his[i][k-1];
//            }
            //varPara.u_his[i][k] = varPara.uk[i];              //????????????????????????m/s
            varPara.Deng_his[i][k] = varPara.Dengk[i];
            if (varPara.Deng_his[i][k]!=0 && varPara.pigflag1==1 &&
                    (varPara.Deng_his[i][k]/varPara.Deng_his[i][k-1]>5||varPara.Deng_his[i][k]/varPara.Deng_his[i][k-1]<-5))  {
                varPara.Deng_his[i][k]=varPara.Deng_his[i][k-1];
                //varPara.Dengk[i]=varPara.Deng_his[i][k-1];
            }
            varPara.dMg_out_his[i][k] = 5.0*varPara.dMg_out[i];     //??????0.2s??????????????????????????????1s??????
            varPara.dMg_in_his[i][k] = 5.0*varPara.dMg_in[i];       //??????0.2s??????????????????????????????1s??????
            varPara.backPressure_his[i][k] = varPara.backPressure[i]/1000000.0;//?????????Pa,?????????MPa
            varPara.M_his[i][k] = varPara.MGk[i];
            if (varPara.M_his[i][k-1]*varPara.MGk[i]<0 || Double.isNaN(varPara.MGk[i]))  {
                varPara.Pg_his[i][k]=varPara.Pg_his[i][k-1];
                varPara.lg_his[i][k]=varPara.lg_his[i][k-1];
                varPara.M_his[i][k]=varPara.M_his[i][k-1];
            }
            //varPara.hgs_his[i][k] = varPara.hgs_k[i];
            //varPara.hgw_his[i][k] = varPara.hgw_k[i];
            //varPara.hl_his[i][k] = 1-varPara.Hgk[i];
            varPara.HFB[i][k] = varPara.Hfk_b[i];
            varPara.HFF[i][k] = varPara.Hfk_f[i];
            varPara.HFBU[i][k] = varPara.Hfk_bU[i];

        }





    }
    public  void cal_l0() {
        Pipeline pipeLine = pipeLines.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        //?????????????????????????????????
        double nta = 0.11*Math.pow((pipeLine.getRoughness()/A),0.25);         //??????????????????
        double beta=0.0826*nta;
        double m1=0.0;
        varPara.ipj=beta*Math.pow(conPara.Ql,2-m1)*Math.pow(1e-6,m1)/Math.pow(A,5-m1);//????????????????????????

        //????????????????????????????????????
        /*
         * ??????????????????????????????????????????
         * ???????????????????????????????????????????????????????????????
         * */
        for (int n=1;n<varPara.line_l.length;n++){
            varPara.l0[n]=varPara.line_d[n][3] + 0.2*1e6/oil.density/conPara.g + (varPara.ipj)*varPara.line_l[n][3]-varPara.line_d[n][1];
            varPara.l0[n]=varPara.l0[n]+(varPara.line_d[n][1]-varPara.line_d[n][2])/(varPara.line_l[n][1]-varPara.line_l[n][2])* varPara.line_l[n][1];
            varPara.l0[n]=varPara.l0[n]/((varPara.line_d[n][1]-varPara.line_d[n][2])/(varPara.line_l[n][1]-varPara.line_l[n][2]) + varPara.ipj);
        }

    }
    public  double cal_l0(double Ql,Pipeline pipeLine,Oil oil) {
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double A=Math.PI*D*D/4.0;
        //?????????????????????????????????
        double nta = 0.11*Math.pow((pipeLine.getRoughness()/A),0.25);         //??????????????????
        double beta=0.0826*nta;
        double m1=0.0;
        double ipj=beta*Math.pow(Ql,2-m1)*Math.pow(oil.getViscosity()*0.000001,m1)/Math.pow(A,5-m1);//????????????????????????

        //????????????????????????????????????
        /*
         * ??????????????????????????????????????????
         * ???????????????????????????????????????????????????????????????
         * */
//        for (int n=1;n<varPara.line_l.length;n++){
//            varPara.l0[n]=varPara.line_d[n][3] + 0.2*1e6/oil.density/conPara.g + (varPara.ipj)*varPara.line_l[n][3]-varPara.line_d[n][1];
//            varPara.l0[n]=varPara.l0[n]+(varPara.line_d[n][1]-varPara.line_d[n][2])/(varPara.line_l[n][1]-varPara.line_l[n][2])* varPara.line_l[n][1];
//            varPara.l0[n]=varPara.l0[n]/((varPara.line_d[n][1]-varPara.line_d[n][2])/(varPara.line_l[n][1]-varPara.line_l[n][2]) + ipj);
//        }

        return ipj;
    }

    public  void dtPosui(int timeStepNum, int kkk,double l0, double l1, double z1, double l2, double z2, double q,
                         double d, double dg0, double M0, double Pgp, double Lgp, double dgp, double Hgp, double[] M,
                         double[] Pg, double[] Lg, double[] deng, double[] Hg, double dl, double nd) throws IOException
    {
        Pipeline pipeLine = pipeLines.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        //1?????????????????????
        double hg,hl,dg,lg,pg,Mg,nums=kkk;
        //????????????
        hg = Hgp;
        hl = 1-hg;
        dg = dgp;
        lg = Lgp;
        pg = Pgp;
        Mg = M0;

        double vlf;  //????????????
        double Glf;  //??????????????????
        double sita,sita1; //????????????
        double sinsita;
        double dt=varPara.deltaT;

        double EoD;         //?????????D??????Eotvos???
        double dcrit;       //??????????????????????????????????????????????????????
        double CJ=1.0;      //????????????????????????????????????????????????????????????????????????
        double C1=2.0/3;    //????????????
        double we,wec;      //???????????????????????????
        double GGe;         //????????????

        //?????????s???w???????????????????????????
        double c0s=0.9;     //?????????????????????????????????????????????????????????????????????????????????????????????C0<1,??????????????????
        double hgs=0.0;     //????????????????????????????????????
        double v0s;         //?????????????????????
        double vgs;
        double vm;          //????????????
        double c0w = 0.5;   //???????????????????????????????????????????????????????????????????????????????????????C0<1,?????????????????????HG?????????
        double hgw = 0.0;   //???????????????
        double v0w;         //?????????????????????
        double vgw = 0.0;     //???????????????????????????----------???????????????
        double f;             //????????????????????????
        double tao_l,sl,Resl;       //??????????????????????????????????????????
        double b;               //?????????hl?????????????????????
        double fi = 0.0142;     //????????????????????????????????????????????????
        double tao_i;       //??????????????????
        double si;          //??????
        double dp;          //?????????????????????????????????
        double pgw;         //???????????????????????????
        double dgw;         //???????????????????????????
        double dM;      //??????????????????
        double fl=0.001,vl=0.001;               //???????????????????????????
        int n;
        int k;
        int flag = 0;        //---------???????????????



        double dL;           //???????????????

        //2?????????????????????
        sinsita =(z1-z2)/(l2-l1);   //????????????????????????????????????????????????????????????4.69???
        sita=asin(sinsita);

        if(Math.abs(sita)<=Math.PI/4)
            sita1=Math.abs(sita);
        else
            sita1=Math.PI/2-Math.abs(sita);

        Resl=q*d/A/nd;              //????????????????????????????????????????????????????????????????????????????????????????????????nd?????????????????????2100???????????????4.36????????????

        //3?????????????????????*****?????????????????????
        if(l0-l1<1e-2)//????????????????????????
            dL=1e-2;
        else
            dL=l0-l1;

        vlf=q/A/hl;                                                 //??????????????????
        Glf=vlf*hl;                                                 //??????????????????
        EoD=Math.abs((dl-dg)*9.8*d*d/8/conPara.FT);       //??????????????????    FT?????????????????????
        //System.out.println(EoD);
        dcrit=0.224/Math.pow(Math.abs(Math.cos(sita1)*EoD),0.5);    //????????????????????????
        we=dl*d*Math.pow(vlf-Glf,2)/conPara.FT;           //???????????????
        //System.out.println(we);
        wec=100*C1/dcrit;                                           //???????????????


        if (we-wec < 0){

            if (timeStepNum%300==0){
                if (nums==kkk)
//                System.out.println("????????????i="+kkk);
                    nums++;

            }
        }else{

            GGe=1.0/400/CJ*dcrit*Math.abs(we-wec)*Glf;                  //??????????????????
            vm=q/A+GGe;                                                 //------?????????????????????
            //System.out.println(vm);

            for(k=0;k<3;k++)   //??????hgs      ????????????????????????????????????
            {
                hgs=0.001*Math.pow(0.9,k);
                n=0;
                do
                {
                    //System.out.println("????????????2?????????");
                    n++;
                    hgs=hgs+0.001;
                    if(hgs>=0.9)
                        break;
                    v0s= conPara.FT * 9.8 * Math.abs(dl-dg)/dl/dl;     //
                    v0s=Math.pow(v0s,0.25);             //???????????????????????????4.74
                    v0s=1.53*v0s*sinsita;               //
                    v0s=v0s*Math.pow(1-hgs,1.5);        //
                    vgs=c0s*vm-v0s;                     //
                    f=GGe/vgs-hgs;
                    if(Math.abs(f)<1e-2)
                    {
                        //System.out.println("????????????");
                        flag=1;
                        break;
                    }

                }while(n<1000);

                if(flag==1)
                    break;
            }
            varPara.hgs_k[kkk]=hgs;

            flag=0;

            for(k=0;k<10;k++)   //??????hgw     ????????????????????????????????????
            {
                hgw=0.001*Math.pow(0.85,k);
                n = 0;
                do  //?????????????????????hgw???vgw
                {
                    n++;
                    hgw=hgw+0.001;
                    if(hgw>=0.9)
                        break;
                    v0w=conPara.FT*9.8*Math.abs(dl-dg)/dl/dl;
                    v0w=Math.pow(v0w,0.25);
                    v0w=1.53*v0w*sinsita;
                    v0w=v0w*Math.pow(1-hgw,1.5);            //??????????????????
                    vgw=c0w*vm-v0w;                         //???4.72
                    f=GGe/vgw-hgw;                          //f??????????????????????????????
                    if(Math.abs(f)<1e-2)                    //???4.77???????????????
                    {
                        //System.out.println("????????????");
                        flag=1;
                        break;
                    }

                }while(n<1000);
                if(flag==1)
                    break;
            }
            varPara.hgw_k[kkk]=hgw;

            flag=0;

            //??????dM,Mg,pc
            tao_i = fi*dg*Math.pow(q/A/hl,2)/2;                 //???????????????????????????
            b=1-2*hl+Math.pow(hl,1.0/3)-Math.pow(hg,1.0/3);
            b=b*Math.pow(3*Math.PI/2,1.0/3);
            b=Math.PI*hl+b+0.002;                               //?????????????????????????????????

            si=d*sin(b);


            dp=tao_i*si/hg/A*lg;                     //Pa?????????????????????????????????
            pgw=pg-dp;                               //????????????????????????????????????????????????
            //dgw=dg0/conPara.p0*pg;                   //dgw????????????????????????????????????0.1MPa???????????????dg0???????????????????????????


            dM=0.001*pgw*A*hg*(hgw-hgs)*vgw*dt/conPara.R/oil.temperature;         //??????dM,pc   //dgw?????????????????????   hgw???????????????  vgw??????????????????  dt??????????????????
            //dM=0.002*pgw*A*hg*(hgw-hgs)*vgw*dt/conPara.R/oil.temperature;         //??????dM,pc   //dgw?????????????????????   hgw???????????????  vgw??????????????????  dt??????????????????
            //dM=0;         //??????dM,pc   //dgw?????????????????????   hgw???????????????  vgw??????????????????  dt??????????????????
            varPara.dMg_out[kkk]=dM;                    //??????????????????
            if (varPara.dMg_out[kkk]<0){
                varPara.dMg_out[kkk]=0.0002;
            }

            if (dM-varPara.dMg_in[kkk]<0) {
                if (lg<varPara.line_l[kkk][2]-varPara.line_l[kkk][1]){
                    lg=lg*1.003;
                }else{
                    lg=varPara.line_l[kkk][2]-varPara.line_l[kkk][1];
                }
            }else{
                lg=lg-(dM-varPara.dMg_in[kkk])/Mg*lg;
            }
            dg=Mg/(lg*A*hg);
            //dg=dg-dM/Mg*dg;
            //lg=lg-dM/Mg*lg;
            Mg=varPara.MGk[kkk]-varPara.dMg_out[kkk]+varPara.dMg_in[kkk];       //20201103???????????????????????????


            for(k=0;k<10;k++)  //?????????????????????hl       ???????????????
            {
                b=0.1*Math.pow(0.9,k);
                n=0;
                do  //???Mg???pc?????????????????????????????????????????????????????????hg???dg???L???Pg
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


                    f=tao_l*sl/A/hl+tao_i*si/A*(1/hl+1/hg)-9.8*(dl-dg)*sinsita; //?????????4.18????????????????????????????????????
                    //f=9.8*(dl)*sinsita*A*hl-tao_l*sl-tao_i*si-A*hl*dp; //?????????4.18????????????????????????????????????


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

            pg = dg/dg0*conPara.p0;      //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????

            //5???????????????
            varPara.Pgk[kkk] = pg;
            //varPara.lgk[kkk] = lg;
            varPara.Dengk[kkk] = dg;


            varPara.MGk[kkk] = Mg;
            varPara.lgk[kkk] = lg;


            //?????????????????????????????????????????????,2021.01.03
            varPara.f_l[kkk]=fl;
            varPara.f_i[kkk]=fi;

            //????????????????????????
            varPara.Hgbk[kkk]=hgw*0.1;



        }



    }

    public  void YaSuo(int i, double lgk,double Pgk,double Hgk,double slopeU,double slopeD,double uk,double h1k,double h2k,double backPressure,double dMg) {
        //??????????????????????????????????????????
        double X[][] = matrixAssemblyX(lgk, Pgk, Hgk, slopeU, slopeD, uk, h1k, h2k);
        double Z[][] = matrixAssemblyZ(lgk, backPressure,dMg, Pgk, Hgk, slopeU, slopeD, uk, h1k, h2k);
        Matrix matrixX = new Matrix(X);//?????????????????????????????????X
        Matrix matrixZ = new Matrix(Z);//?????????????????????????????????Z
        Matrix matrixY = matrixX.inverse().times(matrixZ);
        double YY[][] = matrixY.getArray();  //?????????????????????
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
        //??????????????????????????????????????????
        double X[][] = matrixAssemblyX(lgk, Pgk, Hgk, slopeU, slopeD, uk, h1k, h2k);
        double Z[][] = matrixAssemblyZ(lgk, backPressure,dMg, Pgk, Hgk, slopeU, slopeD, uk, h1k, h2k);
        Matrix matrixX = new Matrix(X);//?????????????????????????????????X
        Matrix matrixZ = new Matrix(Z);//?????????????????????????????????Z
        Matrix matrixY = matrixX.inverse().times(matrixZ);
        double YY[][] = matrixY.getArray();  //?????????????????????
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
     * ????????????????????????????????????
     * ?????????????????????ssr???i??????????????????????????????????????????
     */
    public  void FlowConversion(int kk,int j) {


        if (varPara.MGk[j] < 5 && varPara.MGk[j] > 0.01)
        {
            while (varPara.ssr[j][0] == 0.0)
            {
                System.out.println("???" + j + "?????????" + 0.2*kk/3600.0 + "h????????????????????????");

                varPara.ssr[j][0]++;
            }
        }


    }


    /**
     * ?????????????????????????????????????????????
     * @param degree    ????????????????????????????????????Math.degree????????????
     * @param dz    ??????????????????
     * @return  ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
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

    //???????????????

    /**
     * ???????????????????????????????????????????????????????????????????????????
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
        double G,dpj,dp;//????????????????????????

        double []dpk=new double[5];
        //????????????????????????
        G=conPara.Ql*dl*(1-x)+A*vsg*dl*x;
        //??????????????????
        double j=1+1.3*(x*(1-x)*(1+829.187)*Math.pow((1-0.001206),0.5)/(1+x*828.187));
        //?????????????????????
        dpj=j*G*G/(2*A*A*dl)*(1+x*(dl/dg-1));

        //???????????????
        dp=dpf*lg+dpb*lp+dpbU*lpU+dpj*num;

        dpk[0]=dpf;
        dpk[1]=dpb;
        dpk[2]=dpj;
        dpk[3]=dp;

        //????????????
        return dpk;

    }


    //?????????????????????????????????????????????????????????????????????
    public double dPb_cal(double A,double slope, double x,double D,double dl,double dg,double u,double Um,double vsl,double vsg,double e)
    {
        double G,Rem,rhom;//??????????????????

        //????????????????????????
        G=A*vsl*dl*(1-x)+A*vsg*dg*x;
        //dl??????dg?????????

        //??????????????????
        rhom=dl*(1-x)+dg*x;
        if (rhom>900){
            rhom=900;
        }
        //??????????????????
        //Rem=4*G/((Math.PI)*D*u);          //?????????????????????????????????u=1mPa??s
        Rem=rhom*Um*D/(u);          //?????????????????????????????????u=1mPa??s



        //?????????????????????
        double fF;
        fF=0.001375*(1+Math.pow((2*10000)*e/D+(10e6/Rem),1/3));
        //??????????????????

        //double dpb=2*fF*rhom*Um*Um/D+rhom*9.81*slope;
        //??????????????????
        //double dpb=2*fF*rhom*Um*Um/D;//0.9?????????????????????
        double dpb=2*fF*rhom*Um*Um/D+rhom*9.81*slope;//0.9?????????????????????
        return dpb;

    }
    public double dHb_cal(double A,double slope, double x,double D,double dl,double dg,double u,double Um,double vsl,double vsg,double e)
    {
        double G,Rem,rhom;//??????????????????

        //????????????????????????
        G=A*vsl*dl*(1-x)+A*vsg*dg*x;
        //dl??????dg?????????

        //??????????????????
        rhom=dl*(1-x)+dg*x;
        if (rhom>900){
            rhom=900;
        }
        //??????????????????
        //Rem=4*G/((Math.PI)*D*u);          //?????????????????????????????????u=1mPa??s
        Rem=rhom*Um*D/(u);          //?????????????????????????????????u=1mPa??s



        //?????????????????????
        double fF;
        fF=0.001375*(1+Math.pow((2*10000)*e/D+(10e6/Rem),1/3));
        //??????????????????

        //double dpb=2*fF*rhom*Um*Um/D+rhom*9.81*slope;
        //??????????????????
        double dpb=2*fF*rhom*Um*Um/D;//0.9?????????????????????
        //double dpb=2*fF*rhom*Um*Um/D+rhom*9.81*slope;//0.9?????????????????????
        return dpb;

    }
    public static  double dPbU_cal(double A,double slope, double x,double D,double dl,double dg,double u,double Um,double vsl,double vsg,double e)
    {
        double G,Rem,rhom;//??????????????????

        //????????????????????????
        G=A*vsl*dl*(1-x)+A*vsg*dg*x;
        //dl??????dg?????????

        //??????????????????
        rhom=dl*(1-x)+dg*x;

        //??????????????????
        //Rem=4*G/((Math.PI)*D*u);          //?????????????????????????????????u=1mPa??s
        Rem=rhom*Um*D/(u);          //?????????????????????????????????u=1mPa??s



        //?????????????????????
        double fF;
        fF=0.001375*(1+Math.pow((2*10000)*e/D+(10e6/Rem),1/3));
        //??????????????????

        //double dpb=2*fF*rhom*Um*Um/D+rhom*9.81*slope;
        //??????????????????
        double dpb=2*fF*rhom*Um*Um/D+rhom*9.81*slope;
        //double dpb=2*fF*rhom*Um*Um/D;
        return dpb;

    }

    public static  double dHbU_cal(double A,double slope, double x,double D,double dl,double dg,double u,double Um,double vsl,double vsg,double e)
    {
        double G,Rem,rhom;//??????????????????

        //????????????????????????
        G=A*vsl*dl*(1-x)+A*vsg*dg*x;
        //dl??????dg?????????

        //??????????????????
        rhom=dl*(1-x)+dg*x;

        //??????????????????
        //Rem=4*G/((Math.PI)*D*u);          //?????????????????????????????????u=1mPa??s
        Rem=rhom*Um*D/(u);          //?????????????????????????????????u=1mPa??s



        //?????????????????????
        double fF;
        fF=0.001375*(1+Math.pow((2*10000)*e/D+(10e6/Rem),1/3));
        //??????????????????

        //double dpb=2*fF*rhom*Um*Um/D+rhom*9.81*slope;
        //??????????????????
        //double dpb=2*fF*rhom*Um*Um/D+rhom*9.81*slope;
        double dpb=2*fF*rhom*Um*Um/D;
        return dpb;

    }


    public static  double dPd_cal(double slope,double x,double Q,double A,double D,double dl,double vsl,double vsg,double e)
    {
        double G,Re0,rhom,c0,fl,vb=1.4,vpj=Q/A;//??????????????????
        //????????????????????????
        G=A*vsl*dl*(1-x)+A*vsg*1.205*x;

        c0=0.00252*Math.log(1000*0.001)/pow(D,1.38)-0.782+0.232*Math.log(vpj)-0.428*log(D);
        if (c0<-0.213*vpj) c0=-0.213*vpj;

        //??????????????????
        rhom=G+dl*vb*A/(Q+vb*A)+c0*dl;

        Re0=D*vpj*dl/0.001;//??????????????????
        fl=0.11*pow((e/D+68/Re0),0.25);

        //??????????????????
        //double dpd=fl*dl*vpj*vpj*(1+c0)/2/D;
        double dpd=fl*dl*vpj*vpj*(1+c0)/2/D+rhom*9.81*slope;
        return dpd;
    }

    public static  double dHd_cal(double slope,double x,double Q,double A,double D,double dl,double vsl,double vsg,double e)
    {
        double G,Re0,rhom,c0,fl,vb=1.4,vpj=Q/A;//??????????????????
        //????????????????????????
        G=A*vsl*dl*(1-x)+A*vsg*1.205*x;

        c0=0.00252*Math.log(1000*0.001)/pow(D,1.38)-0.782+0.232*Math.log(vpj)-0.428*log(D);
        if (c0<-0.213*vpj) c0=-0.213*vpj;

        //??????????????????
        rhom=G+dl*vb*A/(Q+vb*A)+c0*dl;

        Re0=D*vpj*dl/0.001;//??????????????????
        fl=0.11*pow((e/D+68/Re0),0.25);

        //??????????????????
        double dpd=fl*dl*vpj*vpj*(1+c0)/2/D;
        //double dpd=fl*dl*vpj*vpj*(1+c0)/2/D+rhom*9.81*slope;
        return dpd;
    }
    //?????????????????????

    /**
     * ????????????????????????
     * @param slope ??????
     * @param x ?????????
     * @param D ????????????
     * @param dl ????????????
     * @param dg ????????????
     * @param vsl ????????????
     * @param fl ??????????????????
     * @param fi ??????????????????
     * @param sl ????????????
     * @return  ?????????????????????????????????
     */
    public double dpf_cal(double slope, double x,double D,double dl,double dg,double vsl,double fl,double fi,double sl){

        double rhom;

        //????????????????????????
        //G=A*vsl*dl*(1-x)+A*vsg*dl*x;

        //??????????????????
        rhom=dl*(1-x)+dg*x;

        //??????????????????
        //double dpf=0.01*(dl*vsl*vsl*2/Math.PI*D)*(2*fi*sin(sl/D)*D+fl*sl/(1-x)/(1-x));//????????????


        /**
         * 20210527  ??????????????????
         */
        double dpf=(dl*vsl*vsl*2/Math.PI*D)*(2*fi*sin(sl/D)*D+fl*sl/(1-x)/(1-x))+rhom*9.81*slope;
        //double dpf=(dl*vsl*vsl*2/Math.PI*D)*(2*fi*sin(sl/D)*D+fl*sl/(1-x)/(1-x));
        return dpf;

    }
    public double dHf_cal(double slope, double x,double D,double dl,double dg,double vsl,double fl,double fi,double sl){

        double rhom;

        //????????????????????????
        //G=A*vsl*dl*(1-x)+A*vsg*dl*x;

        //??????????????????
        rhom=dl*(1-x)+dg*x;

        //??????????????????
        //double dpf=0.01*(dl*vsl*vsl*2/Math.PI*D)*(2*fi*sin(sl/D)*D+fl*sl/(1-x)/(1-x));//????????????


        /**
         * 20210526  ??????????????????
         */
        //double dpf=(dl*vsl*vsl*2/Math.PI*D)*(2*fi*sin(sl/D)*D+fl*sl/(1-x)/(1-x))+rhom*9.81*slope;
        double dpf=(dl*vsl*vsl*2/Math.PI*D)*(2*fi*sin(sl/D)*D+fl*sl/(1-x)/(1-x));
        return dpf;

    }





    /**
     * ????????????????????????????????????,??????????????????????????????????????????
     * @param n  ?????????????????????
     * @param dx ??????
     * @param T  ??????????????????????????????????????????
     * @param num ????????????????????????????????????
     * @param waterHeadLocation ????????????????????????3000s???????????????????????????rr????????????300???????????????3000*0.5=1500m????????????????????????????????????????????????
     * @param dpb ????????????????????????????????????
     * @param dpbU  ????????????????????????????????????
     * @param dpf   ???????????????????????????
     * @param lg_f  ???????????????????????????
     */
    public void dpL(int n,double T,int num,double dx,double[] waterHeadLocation,double[] dpb,double[] dpbU,double[] dpf,double [][]lg_f,double []vll,double []stationL,double []stationP){
        Pipeline pipeLine = pipeLines.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        Oil oil1 = oils.get(1);
        //double [][]dPL=new double[500][800];//???????????????,???????????????????????????????????????500*300,10s,300,,*30
        int nn=1,ii=1;
        int kkk=num;
        //for (int kkk=num;kkk<(int)T*12+1;kkk++)//????????????
        //{
        varPara.dPL[kkk][0]=varPara.Hs;       //?????????????????????????????????

        varPara.Lt=varPara.line_l[1][1];
        for (int x=1;x<=(waterHeadLocation[kkk]-varPara.line_l[1][1])/dx+1;x++)//????????????????????????
        {
            varPara.Lt=varPara.Lt+dx;

            for (int i=ii;i<=nn;i++)//?????????????????????????????????
            {
                if (varPara.ssr[i][0]!=0) {//??????????????????????????????{

                    if (varPara.Lt - varPara.line_l[i][2] <= 0)  //????????????????????????????????????
                    {
                        if (varPara.Lt > varPara.waterLength*1000 && x<varPara.Lt-((varPara.waterLength*1000)/dx)){//????????????
                            varPara.dPL[kkk][x] = oil1.getDensity()*9.81*(varPara.slopeD[i][0]-cal_l0(conPara.Ql, pipeLine,oil1)) * dx + varPara.dPL[kkk][x - 1];      //???kkk????????????????????????x????????????????????????
                            varPara.Hfk_lbz_D[i] = -oil.getDensity()*9.81*(varPara.slopeD[i][0]-cal_l0(conPara.Ql, pipeLine,oil1));      //???kkk????????????????????????x????????????????????????
                        }else{//?????????
                            varPara.dPL[kkk][x] = oil.getDensity()*9.81*(varPara.slopeD[i][0]-varPara.ipj) * dx + varPara.dPL[kkk][x - 1];      //???kkk????????????????????????x????????????????????????
                        }

                    } else if (varPara.Lt - varPara.line_l[i][2] > 0 && varPara.Lt <= varPara.line_l[i][3]) {     //????????????

                        if (varPara.Lt>varPara.waterLength*1000 && x<varPara.Lt-((varPara.waterLength*1000)/dx)){//??????
                            if (varPara.flag0==0){
                                //?????????????????????????????????????????????0.03
                                varPara.dPL[kkk][x-1]=varPara.dPL[kkk][x-1]-0.015*oil1.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/A);
                                varPara.flag0++;
                            }
                            varPara.dPL[kkk][x] = varPara.dPL[kkk][x - 1] - 0.8*oil1.density*9.81*(varPara.slopeU[i][0]+cal_l0(conPara.Ql, pipeLine,oil1)) * dx;
                            varPara.Hfk_lbz_U[i] =0.8*oil1.density*9.81*(varPara.slopeU[i][0]+cal_l0(conPara.Ql, pipeLine,oil1));
                        }else{//?????????
                            if (varPara.flag0==0){
                                //?????????????????????????????????????????????0.03
                                varPara.dPL[kkk][x-1]=varPara.dPL[kkk][x-1]-0.015*oil.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/A);
                                varPara.flag0++;
                            }
                            varPara.dPL[kkk][x] = varPara.dPL[kkk][x - 1] - 0.8*oil.density*9.81*(varPara.slopeU[i][0]+cal_l0(conPara.Ql, pipeLine,oil)) * dx;
                        }


                    } else {
                        if (n > nn) {
                            ii++;
                            nn++;
                            varPara.flag0=0;
                        }
                    }
                    //varPara.dPL[kkk][x-1]=varPara.dPL[kkk][x];
                }else if (varPara.ssr[i][0]==0){
                    if (varPara.Lt-varPara.line_l[i][1] <= lg_f[i][kkk])  //???????????????????????????
                    {
                        if (varPara.Lt>varPara.waterLength*1000 && x<varPara.Lt-((varPara.waterLength*1000)/dx)){//????????????
                            /**
                             * //////////////20210720  ????????????????????????run?????????
                             */

                            varPara.dPL[kkk][x]=-varPara.Hfk_f_oil[i]*dx+varPara.dPL[kkk][x-1];      //???kkk????????????????????????x????????????????????????
                        }else{//?????????
                            varPara.dPL[kkk][x]=-dpf[i]*dx+varPara.dPL[kkk][x-1];      //???kkk????????????????????????x????????????????????????
                        }


//                            varPara.dPL[kkk][x]=-dpf[i]*dx+varPara.dPL[kkk][x-1];      //???kkk????????????????????????x????????????????????????
                    } else if (varPara.Lt-varPara.line_l[i][1] > lg_f[i][kkk] && varPara.Lt-varPara.line_l[i][1] <= (varPara.line_l[i][2]-varPara.line_l[i][1])) {     //?????????????????????
                        if (varPara.Lt>varPara.waterLength*1000 && x<varPara.Lt-((varPara.waterLength*1000)/dx)){//????????????
                            /**
                             * //////////////20210720  ????????????????????????run?????????
                             */

                            varPara.dPL[kkk][x]=varPara.dPL[kkk][x-1]-varPara.Hfk_b_oil[i]*dx;
                        }else{//?????????
                            varPara.dPL[kkk][x]=varPara.dPL[kkk][x-1]-dpb[i]*dx;
                        }
//                            varPara.dPL[kkk][x]=varPara.dPL[kkk][x-1]-dpb[i]*dx;
                    } else if (varPara.Lt-varPara.line_l[i][1] > (varPara.line_l[i][2]-varPara.line_l[i][1]) && varPara.Lt-varPara.line_l[i][2] <= (varPara.line_l[i][3]-varPara.line_l[i][2])){//?????????????????????
                        if (varPara.Lt>varPara.waterLength*1000 && x<varPara.Lt-((varPara.waterLength*1000)/dx)){//????????????
                            /**
                             * //////////////20210720  ????????????????????????run?????????
                             */

                            if (varPara.flag0==0){
                                //?????????????????????????????????????????????0.025
                                varPara.dPL[kkk][x-1]=varPara.dPL[kkk][x-1]-0.025*oil1.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/A);
                                varPara.flag0++;
                            }
                            varPara.dPL[kkk][x]=varPara.dPL[kkk][x-1]-varPara.Hfk_bU_oil[i]*dx;
                        }else{//?????????
                            if (varPara.flag0==0){
                                //?????????????????????????????????????????????0.03
                                varPara.dPL[kkk][x-1]=varPara.dPL[kkk][x-1]-0.025*oil.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/A);
                                varPara.flag0++;
                            }
                            varPara.dPL[kkk][x]=varPara.dPL[kkk][x-1]-dpbU[i]*dx;
                        }


//                            if (varPara.flag0==0){
//                                //?????????????????????????????????????????????0.03
//                                varPara.dPL[kkk][x-1]=varPara.dPL[kkk][x-1]-0.025*oil.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/A);
//                                varPara.flag0++;
//                            }
//                            varPara.dPL[kkk][x]=varPara.dPL[kkk][x-1]-dpbU[i]*dx;
                    } else{
                        if (n>nn){
                            ii++;
                            nn++;
                            varPara.flag0=0;
                        }
                    }
                }
            }
            for (int s=0;s<stationL.length;s++){        //?????????????????????
                if (varPara.Lt>stationL[s] && varPara.Lt-dx <=stationL[s] && stationL[s]!=0){
                    if (varPara.Pm==1 && stations.get(s).getAppointOutletPressure()!=0){
                        stationP[s]=stations.get(s).getAppointOutletPressure()*1e6;
                    }
                    if (varPara.Lt>varPara.waterLength*1000 && x<varPara.Lt-((varPara.waterLength*1000)/dx)){//????????????
                        varPara.dPL[kkk][x]=stationP[s]*oil1.getDensity()/oil.getDensity()+varPara.dPL[kkk][x-1];
                    }else{//?????????
                        varPara.dPL[kkk][x]=stationP[s]+varPara.dPL[kkk][x-1];
                    }
//                        varPara.dPL[kkk][x]=stationP[s]+varPara.dPL[kkk][x-1];
//                        System.out.println("stationP["+s+"] = " + stationP[s]);
//                        if (varPara.dPL[kkk][x]>12500000) //??????????????????12MPa??????????????????????????????????????????
////                        if (T%200==0)
                    if (varPara.dPL[kkk][x]>stations.get(s).getMaxOutletPressure()*1e6)
                    {
                        varPara.checkP=1;
//                            if (T%50==0) System.out.println(stations.get(s).getStationName()+"??????11111111111111,???????????????"+varPara.dPL[kkk][x]);
                        varPara.dPL[kkk][x]=stations.get(s).getMaxOutletPressure()*1e6;
                        //valveCalDp1(varPara.Qh/3600.0,D,5.68,1,1000);/////////////////////////////////////////////////////////////////
//                            varPara.Qh=valveCal(20000,varPara.dHL[kkk][x]-12500000,varPara.Qh);
                    }
////                        if (varPara.dPL[kkk][x]>12500000 && T%200==0)
//                        {
//                            System.out.println("stationP111111["+s+"] = " + stationP[s]);
////                            varPara.checkP=1;
//                            varPara.dPL[kkk][x]=12500001;
//                            //valveCalDp1(varPara.Qh/3600.0,D,5.68,1,1000);/////////////////////////////////////////////////////////////////
////                            varPara.Qh=valveCal(20000,varPara.dPL[kkk][x]-12500000,varPara.Qh);
//                        }
                    //varPara.checkP=1;
                    varPara.Hss[kkk][s]=varPara.dPL[kkk][x-1];//???????????????????????????
                    varPara.Hss[kkk][s+49]=varPara.dPL[kkk][x];//???????????????????????????
                    if (varPara.Hss[kkk][s+49]<varPara.Hss[kkk-1][s+49]&&varPara.Hss[kkk][s+49]!=0&&varPara.Hss[kkk-1][s+49]!=0) {
                        varPara.times++;
                        varPara.Hss[kkk][s+49]=varPara.Hss[kkk-1][s+49];
                        varPara.Hss[kkk][s]=varPara.Hss[kkk-1][s];
                        //System.out.println("?????????????????????"+varPara.times+"???");
                    }
                }

            }

//                if(varPara.dPL[kkk][x]>13500000){
//                    varPara.checkP=1;
////                    System.out.println("??????"+num/12.0+"h??????,??????????????????"+x*0.5+"km,?????????"+varPara.dPL[kkk][x]/1.0e6+"MPa");
//                }
//                if(varPara.dPL[kkk][x]>12500000 && T%200==0){
////                    varPara.checkP=1;
//                    System.out.println("??????"+num/12.0+"h??????,??????????????????"+x*0.5+"km,?????????"+varPara.dPL[kkk][x]/1.0e6+"MPa");
//                }
//                if(varPara.dPL[num][x]<500000){
//                    if (conPara.Ql*3600>0.9*varPara.Qh) conPara.Ql=0.9995*conPara.Ql;
//                }

            if(varPara.dPL[num][x]<101325){
//                    varPara.startPumpFlag111=1;
//                    conPara.Ql=1.05*varPara.Qh/3600.0;
//                    System.out.println("--------------------------------------------------????????????????????????"+varPara.num*5/120.0+"h,????????????"+varPara.dPL[num][x]+"Pa????????????"+x*dx/1000.0+"km");
                varPara.dPL[num][x]=101325;
//                    break;
            }
        }


//            if (varPara.Lt>waterHeadLocation[kkk]){
//                varPara.num=kkk;
//                break;
//            }
        //}

    }



    /**
     * ????????????????????????????????????,
     * @param n  ?????????????????????
     * @param dx ??????
     * @param T  ??????????????????????????????????????????
     * @param num ????????????????????????????????????
     * @param waterHeadLocation ????????????????????????3000s???????????????????????????rr????????????300???????????????3000*0.5=1500m????????????????????????????????????????????????
     * @param dpb ????????????????????????????????????
     * @param dpbU  ????????????????????????????????????
     * @param dpf   ???????????????????????????
     * @param lg_f  ???????????????????????????
     */
    public void dHL(int n,double T,int num,double dx,double[] waterHeadLocation,double[] dpb,double[] dpbU,double[] dpf,double [][]lg_f,double []vll,double []stationL,double []stationP){
        Pipeline pipeLine = pipeLines.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        Oil oil1 = oils.get(1);
        //double [][]dPL=new double[500][800];//???????????????,???????????????????????????????????????500*300,10s,300,,*30
        int nn=1,ii=1;
        int kkk=num;
        //for (int kkk=num;kkk<(int)T*12+1;kkk++)//????????????
        //{
        varPara.dHL[kkk][0]=varPara.Hs;
        varPara.Lt=varPara.line_l[1][1];
        List<Double> allLineLbzB = new ArrayList<>();
        for (int x=1;x<=(waterHeadLocation[kkk]-varPara.line_l[1][1])/dx+1;x++)//????????????????????????
        {
            varPara.Lt=varPara.Lt+dx;

            varPara.dHL[kkk][x]=varPara.dHL[varPara.dHL.length-2][x];       //??????????????????

            for (int i=ii;i<=nn;i++)//?????????????????????????????????
            {
                if (varPara.ssr[i][0]!=0) {//??????????????????????????????{

                    if (varPara.Lt - varPara.line_l[i][2] <= 0)  //????????????????????????????????????
                    {
                        if (varPara.Lt > varPara.waterLength*1000 && x<varPara.Lt-((varPara.waterLength*1000)/dx)){//????????????
                            varPara.dHL[kkk][x] = oil1.getDensity()*9.81*(-cal_l0(conPara.Ql, pipeLine,oil1)) * dx + varPara.dHL[kkk][x - 1];      //???kkk????????????????????????x????????????????????????
                        }else{//?????????
                            varPara.dHL[kkk][x] = oil.getDensity()*9.81*(-varPara.ipj) * dx + varPara.dHL[kkk][x - 1];      //???kkk????????????????????????x????????????????????????
                        }

//                            varPara.dHL[kkk][x] = oil.getDensity()*9.81*(-varPara.ipj) * dx + varPara.dHL[kkk][x - 1];      //???kkk????????????????????????x????????????????????????
                    } else if (varPara.Lt - varPara.line_l[i][2] > 0 && varPara.Lt <= varPara.line_l[i][3]) {     //????????????
                        if (varPara.Lt>varPara.waterLength*1000 && x<varPara.Lt-((varPara.waterLength*1000)/dx)){//??????
                            if (varPara.flag0==0){
                                //?????????????????????????????????????????????0.03
                                varPara.dHL[kkk][x-1]=varPara.dHL[kkk][x-1]-0.015*oil1.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/A);
                                varPara.flag0++;
                            }
                            varPara.dHL[kkk][x] = varPara.dHL[kkk][x - 1] - 0.8*oil1.density*9.81*(cal_l0(conPara.Ql, pipeLine,oil1)) * dx;
                        }else{//?????????
                            if (varPara.flag0==0){
                                //?????????????????????????????????????????????0.03
                                varPara.dHL[kkk][x-1]=varPara.dHL[kkk][x-1]-0.015*oil.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/A);
                                varPara.flag0++;
                            }
                            varPara.dHL[kkk][x] = varPara.dHL[kkk][x - 1] - 0.8*oil.density*9.81*(cal_l0(conPara.Ql, pipeLine,oil)) * dx;
                        }


//                            if (varPara.flag1==0){
//                                //?????????????????????????????????????????????0.03
//                                varPara.dHL[kkk][x-1]=varPara.dHL[kkk][x-1]-0.015*oil.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/A);
//                                varPara.flag1++;
//                            }
//                            varPara.dHL[kkk][x] = varPara.dHL[kkk][x - 1] - 0.8*oil.density*9.81*(varPara.ipj) * dx;
                    } else {
                        if (n > nn) {
                            ii++;
                            nn++;
                            varPara.flag1=0;
                        }
                    }
                    //varPara.dPL[kkk][x-1]=varPara.dPL[kkk][x];
                }else if (varPara.ssr[i][0]==0){
                    if (varPara.Lt-varPara.line_l[i][1] <= lg_f[i][kkk])  //???????????????????????????
                    {
                        if (varPara.Lt>varPara.waterLength*1000 && x<varPara.Lt-((varPara.waterLength*1000)/dx)){//????????????
                            /**
                             * //////////////20210720  ????????????????????????run?????????
                             */

                            varPara.dHL[kkk][x]=-varPara.Hfk_f1_oil[i]*dx+varPara.dHL[kkk][x-1];      //???kkk????????????????????????x????????????????????????
                        }else{//?????????
                            varPara.dHL[kkk][x]=-dpf[i]*dx+varPara.dHL[kkk][x-1];      //???kkk????????????????????????x????????????????????????
                        }
//                            varPara.dHL[kkk][x]=-dpf[i]*dx+varPara.dHL[kkk][x-1];      //???kkk????????????????????????x????????????????????????
                    } else if (varPara.Lt-varPara.line_l[i][1] > lg_f[i][kkk] && varPara.Lt-varPara.line_l[i][1] <= (varPara.line_l[i][2]-varPara.line_l[i][1])) {     //?????????????????????
                        if (varPara.Lt>varPara.waterLength*1000 && x<varPara.Lt-((varPara.waterLength*1000)/dx)){//????????????
                            /**
                             * //////////////20210720  ????????????????????????run?????????
                             */

                            varPara.dHL[kkk][x]=varPara.dHL[kkk][x-1]-varPara.Hfk_b1_oil[i]*dx;
                        }else{//?????????
                            varPara.dHL[kkk][x]=varPara.dHL[kkk][x-1]-dpb[i]*dx;
                        }
//                            varPara.dHL[kkk][x]=varPara.dHL[kkk][x-1]-dpb[i]*dx;
                    } else if (varPara.Lt-varPara.line_l[i][1] > (varPara.line_l[i][2]-varPara.line_l[i][1]) && varPara.Lt-varPara.line_l[i][2] <= (varPara.line_l[i][3]-varPara.line_l[i][2])){//?????????????????????
                        if (varPara.Lt>varPara.waterLength*1000 && x<varPara.Lt-((varPara.waterLength*1000)/dx)){//????????????
                            /**
                             * //////////////20210720  ????????????????????????run?????????
                             */

                            if (varPara.flag0==0){
                                //?????????????????????????????????????????????0.025
                                varPara.dHL[kkk][x-1]=varPara.dHL[kkk][x-1]-0.025*oil1.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/A);
                                varPara.flag0++;
                            }
                            varPara.dHL[kkk][x]=varPara.dHL[kkk][x-1]-varPara.Hfk_bU1_oil[i]*dx;
                        }else{//?????????
                            if (varPara.flag0==0){
                                //?????????????????????????????????????????????0.03
                                varPara.dHL[kkk][x-1]=varPara.dHL[kkk][x-1]-0.025*oil.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/A);
                                varPara.flag0++;
                            }
                            varPara.dHL[kkk][x]=varPara.dHL[kkk][x-1]-dpbU[i]*dx;
                        }


//                            if (varPara.flag1==0){
//                                //?????????????????????????????????????????????0.03
//                                varPara.dHL[kkk][x-1]=varPara.dHL[kkk][x-1]-0.025*oil.getDensity()*1000*(vll[i]*Math.cos(toDegrees(Math.asin(varPara.slopeD[i][0]/1000))+toDegrees(Math.asin(varPara.slopeU[i][0]/1000)))- conPara.Ql/A);
//                                varPara.flag1++;
//                            }
//                            varPara.dHL[kkk][x]=varPara.dHL[kkk][x-1]-dpbU[i]*dx;
                    } else{
                        if (n>nn){
                            ii++;
                            nn++;
                            varPara.flag1=0;
                        }
                    }
                }
            }




            for (int s=0;s<stationL.length;s++){        //?????????????????????
                if (varPara.Lt>=stationL[s] && varPara.Lt-dx <=stationL[s] && stationL[s]!=0){
                    varPara.dHL[kkk][x]=stationP[s]+varPara.dHL[kkk][x-1];
                    //if (varPara.dHL[kkk][x]>14000000) //??????????????????12MPa??????????????????????????????????????????
                    if (varPara.dHL[kkk][x]>12500000)
                    {
//                            varPara.checkP=1;
//                            if (T%50==0) System.out.println(stations.get(s).getStationName()+"??????,???????????????"+varPara.dHL[kkk][x]);
                        varPara.dHL[kkk][x]=12500001;
                        //valveCalDp1(varPara.Qh/3600.0,D,5.68,1,1000);/////////////////////////////////////////////////////////////////
//                            varPara.Qh=valveCal(20000,varPara.dHL[kkk][x]-12500000,varPara.Qh);
                    }
//                        if (T%200==0)
//                        {
//                            System.out.println("stationP222222["+s+"] = " + stationP[s]);
//                        }
                    //varPara.checkP=1;
                }

            }

            if(varPara.dHL[kkk][x]>12500000){
                varPara.checkP=1;
                //System.out.println("??????"+num/12.0+"h??????,??????????????????"+x*0.5+"km,?????????"+varPara.dPL[kkk][x]/1.0e6+"MPa");
            }

            if(varPara.dHL[num][x]<101325){
                varPara.dHL[num][x]=101325;
            }


            varPara.allLineLbz[num][x]=pow(D,4.75)*(varPara.dHL[num][x]-varPara.dHL[num][x-1])/9.81/1000.0/500.0/ pow(conPara.Ql,1.75)/pow(0.000001,0.25);
            varPara.allLineDP[num][x]=varPara.dHL[num][x-1]-varPara.dHL[num][x];


            allLineLbzB.add(-varPara.allLineLbz[num][x]);
            //System.out.println(varPara.allLineLbzB[num].get(x-1));




//                for (int pipeNum = 1; varPara.line_l[pipeNum][1]<waterHeadLocation[kkk] ; pipeNum++) {
//
//
//                }

        }
        varPara.allLineLbzBs.add(allLineLbzB);

        double Lxx;
        int x1=0;
        double []yy=new double[stationL.length];
        double []zz=new double[stationL.length];
        for (int xx = 0; xx <(varPara.line_l[varPara.i-1][3]-varPara.line_l[1][1])/dx; xx++) {
            Lxx=varPara.line_l[1][1]+xx*500;
            if (xx <= (waterHeadLocation[kkk] - varPara.line_l[1][1]) / dx + 1)//????????????????????????
            {
                for (int s=0;s<stationL.length-1;s++){        //????????????????????????????????????????????????
                    if (Lxx>stationL[s] && Lxx <=stationL[s+1] && stationL[s]!=0 && stations.get(s).getPumps().size()!=0){
                        x1=s;
//                        for (int i = 0; varPara.line_l[i][1] < stationL[s+1]; i++) {
//                        }
//                        if (varPara.dHL[num][xx]-varPara.dHL[varPara.dHL.length-2][xx]>50){
//                            yy[s]=varPara.dHL[num][xx]-varPara.dHL[varPara.dHL.length-2][xx];
//                            if (yy[s]>zz[s]){
//                                zz[s]=yy[s];
//                            }
//                        }
                    }
                }
                varPara.dHL[num][xx] = varPara.dHL[num][xx] / 9.81 / 1000;
                if (x1==0){
                    varPara.dHL[num][xx] = varPara.dHL[num][xx] + varPara.line_d[1][1];
                }else{
                    if (T%200==0)
//                    {
//                        System.out.println("stations.get(x1).getStationZ() = " + stations.get(x1).getStationZ());
//                    }
                        varPara.dHL[num][xx] = varPara.dHL[num][xx] + stations.get(x1).getStationZ();
                }


            } else {
                varPara.dHL[num][xx] = varPara.dHL[num][xx] / 9.81 / 1000;
                varPara.dHL[num][xx] = varPara.dHL[num][xx] + varPara.dHL[varPara.dHL.length-2][xx];
            }

        }





//            if (varPara.Lt>waterHeadLocation[kkk]){
//                varPara.num=kkk;
//                break;
//            }
        //}

    }

    public void dHL_static(int num,double dx,double[] waterHeadLocation){
        Pipeline pipeLine = pipeLines.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        Oil oil = oils.get(0);
        double Lx;
        List<Double> maxZ = new ArrayList();
        List<Double> max0 = new ArrayList();
        List<Integer> maxZZ = new ArrayList();
        List<Double> sta = new ArrayList();

        for (int s=0;s<varPara.pumpSta.size()-1;s++){        //?????????????????????
            if ((double)varPara.pumpSta.get(s)!=0.0){
                sta.add((double)varPara.pumpSta.get(s));
                System.out.println(stations.get(s).getStationName());
                maxZ.add(0.0);
                max0.add(0.0);
                maxZZ.add(0);
            }
        }

        //?????????????????????????????????????????????
        for (int x = 0;x< (int) ((waterHeadLocation[num] - varPara.line_l[1][1]) / dx +1 );  x++)//????????????????????????,????????????
        {
            Lx = varPara.line_l[1][1] + x * dx;
            for (int s=0;s<sta.size()-1;s++){        //?????????????????????
                if (Lx>=sta.get(s) && Lx <=sta.get(s+1)){
                    max0.set(s,varPara.dHL_new[varPara.dHL_new.length-2][x]);
                    if (max0.get(s)>maxZ.get(s)){
                        maxZ.set(s,max0.get(s));
                        maxZZ.set(s,x);
                    }
                }else if (Lx<sta.get(s)&&s==0){
                    max0.set(s,varPara.dHL_new[varPara.dHL_new.length-2][x]);
                    if (max0.get(s)>maxZ.get(s)){
                        maxZ.set(s,max0.get(s));
                        maxZZ.set(s,x);
                    }
                }else if (Lx>sta.get(s+1)&&s==sta.size()-2){
                    max0.set(s,varPara.dHL_new[varPara.dHL_new.length-2][x]);
                    if (max0.get(s)>maxZ.get(s)){
                        maxZ.set(s,max0.get(s));
                        maxZZ.set(s,x);
                    }
                }
            }
        }
        for (int s = 0; s < maxZ.size(); s++) {
            System.out.println("s = " + s);
            System.out.println("maxZ = " + maxZ.get(s));
            System.out.println("maxZZ = " + maxZZ.get(s));
            System.out.println("max0 = " + max0.get(s));
        }

        for (int x = 0;x< (int) ((waterHeadLocation[num] - varPara.line_l[1][1]) / dx +1 );  x++)//????????????????????????,????????????
        {
            Lx = varPara.line_l[1][1] + x * dx;
            varPara.dHL[num+1][x] = varPara.dHL[num][x];
            for (int s=1;s<sta.size()-1;s++){        //?????????????????????
                if (Lx>=sta.get(s) && Lx <=sta.get(s+1)){
                    varPara.dHL[num+1][x] = varPara.dHL[num][x]-(varPara.dHL[num][maxZZ.get(s)]-maxZ.get(s))+80;
                }else if (Lx>sta.get(s+1)&&s==sta.size()-2){
                    varPara.dHL[num+1][x] = varPara.dHL[num][x]-(varPara.dHL[num][maxZZ.get(s)]-maxZ.get(s))+80;
                }
            }
        }
        varPara.dHL[num+2]=varPara.dHL[num+1];
        varPara.dHL[num+3]=varPara.dHL[num+1];
        varPara.dHL[num+4]=varPara.dHL[num+1];
        varPara.dHL[num+5]=varPara.dHL[num+1];
        varPara.dHL[num+6]=varPara.dHL[num+1];
    }


    public void dHL_static_new(double [][]dPL){
        Pipeline pipeLine = pipeLines.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        Oil oil = oils.get(0);
        double Lx;

        for (int i = 0; i < dPL.length; i++) {
            if (dPL[i][2]==0 && i>1){
                for (int j = 0; j < dPL[0].length; j++) {
                    varPara.dHL[i][j] = varPara.dHL[i-1][j];
                    varPara.dHL[i+1][j] = varPara.dHL[i-1][j];
                    varPara.dHL[i+2][j] = varPara.dHL[i-1][j];
                    varPara.dHL[i+3][j] = varPara.dHL[i-1][j];
                }
                break;
            }
            for (int j = 0; j < dPL[0].length; j++) {
                varPara.dHL[i][j] = dPL[i][j] / oil.getDensity() / 9.81 + varPara.dHL[dPL.length-2][j];
            }
        }



    }


    public void dHL_new(int n,double T,int num,double dx,double[] waterHeadLocation,double[] dpb,double[] dpbU,double[] dpf,double [][]lg_f,double []vll,List stationL,double []stationP){
        Pipeline pipeLine = pipeLines.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        //double [][]dPL=new double[500][800];//???????????????,???????????????????????????????????????500*300,10s,300,,*30
        int nn,ii=1;
        int kkk=num;
        //for (int kkk=num;kkk<(int)T*12+1;kkk++)//????????????
        //{
        varPara.dHL_new[kkk][0]=varPara.Hs;
        varPara.Lt_new=varPara.line_l[1][1];
        nn=getI(varPara.line_l,waterHeadLocation[kkk]);
        if (nn==0){
            System.out.println("waterHeadLocation["+kkk+"] = " + waterHeadLocation[kkk]);
        }
        varPara.dHL_new[kkk][(int)((waterHeadLocation[kkk]-varPara.line_l[1][1])/dx+1)]=varPara.dHL_new[varPara.dHL_new.length-2][(int)((waterHeadLocation[kkk]-varPara.line_l[1][1])/dx+1)];       //??????????????????

        for (int x=(int)((waterHeadLocation[kkk]-varPara.line_l[1][1])/dx);x>=0;x--)//????????????????????????
        {
            varPara.Lt_new=x*dx;
            if (varPara.Lt_new-varPara.line_l[nn][1] <= lg_f[nn][kkk])  //???????????????????????????
            {
                varPara.dHL_new[kkk][x]=dpf[nn]*dx/oil.getDensity()/9.81+varPara.dHL_new[kkk][x+1];
//                    varPara.dHL[kkk][x-1]=varPara.dHL[varPara.dHL.length-2][x];
            } else if (varPara.Lt_new-varPara.line_l[nn][1] > lg_f[nn][kkk] && varPara.Lt_new-varPara.line_l[nn][1] <= (varPara.line_l[nn][2]-varPara.line_l[nn][1])) {     //?????????????????????
//                    varPara.dHL[kkk][x-1]=varPara.dHL[varPara.dHL.length-2][x];
                varPara.dHL_new[kkk][x]=varPara.dHL_new[kkk][x+1]+dpb[nn]*dx/oil.getDensity()/9.81;
            } else if (varPara.Lt_new-varPara.line_l[nn][1] > (varPara.line_l[nn][2]-varPara.line_l[nn][1]) && varPara.Lt_new-varPara.line_l[nn][2] <= (varPara.line_l[nn][3]-varPara.line_l[nn][2])){//?????????????????????
                if (varPara.flag11==0){
                    //?????????????????????????????????????????????0.03
                    varPara.dHL_new[kkk][x]=varPara.dHL_new[kkk][x+1]+0.025*oil.getDensity()*1000*(vll[nn]*Math.cos(toDegrees(Math.asin(varPara.slopeD[nn][0]/1000))+toDegrees(Math.asin(varPara.slopeU[nn][0]/1000)))- conPara.Ql/A);
                    varPara.flag11++;
                }
                varPara.dHL_new[kkk][x]=varPara.dHL_new[kkk][x+1]+dpbU[nn]*dx/oil.getDensity()/9.81;
            } else if (nn>0){
                nn--;
                varPara.flag11=0;
                if (varPara.Lt_new-varPara.line_l[nn][1] <= lg_f[nn][kkk])  //???????????????????????????
                {
                    varPara.dHL_new[kkk][x]=dpf[nn]*dx/oil.getDensity()/9.81+varPara.dHL_new[kkk][x+1];
//                    varPara.dHL[kkk][x-1]=varPara.dHL[varPara.dHL.length-2][x];
                } else if (varPara.Lt_new-varPara.line_l[nn][1] > lg_f[nn][kkk] && varPara.Lt_new-varPara.line_l[nn][1] <= (varPara.line_l[nn][2]-varPara.line_l[nn][1])) {     //?????????????????????
//                    varPara.dHL[kkk][x-1]=varPara.dHL[varPara.dHL.length-2][x];
                    varPara.dHL_new[kkk][x]=varPara.dHL_new[kkk][x+1]+dpb[nn]*dx/oil.getDensity()/9.81;
                } else if (varPara.Lt_new-varPara.line_l[nn][1] > (varPara.line_l[nn][2]-varPara.line_l[nn][1]) && varPara.Lt_new-varPara.line_l[nn][2] <= (varPara.line_l[nn][3]-varPara.line_l[nn][2])){//?????????????????????
                    if (varPara.flag11==0){
                        //?????????????????????????????????????????????0.03
                        varPara.dHL_new[kkk][x]=varPara.dHL_new[kkk][x+1]+0.025*oil.getDensity()*1000*(vll[nn]*Math.cos(toDegrees(Math.asin(varPara.slopeD[nn][0]/1000))+toDegrees(Math.asin(varPara.slopeU[nn][0]/1000)))- conPara.Ql/A);
                        varPara.flag11++;
                    }
                    varPara.dHL_new[kkk][x]=varPara.dHL_new[kkk][x+1]+dpbU[nn]*dx/oil.getDensity()/9.81;
                }
            }else{
                System.out.println("???2510????????????????????????");
                break;
            }


            if (varPara.dHL_new[kkk][x]<=varPara.dHL_new[varPara.dHL_new.length-2][x]){//???????????????????????????????????????
                varPara.dHL_new[kkk][x]=varPara.dHL_new[varPara.dHL_new.length-2][x];
                for (int xx = 1; xx <= (varPara.Lt_new-varPara.line_l[nn][1])/500+2; xx++) {
                    varPara.dHL_new[kkk][x-xx]=varPara.dHL_new[varPara.dHL_new.length-2][x-xx];
                    if (varPara.line_l[nn][1]-(x-xx)*500<=0){
                        varPara.Lt_new=(x-xx)*500;
                        varPara.flag11=0;
                        x=x-xx;
                        break;
                    }
                }
            }

            for (int s=0;s<stationL.size();s++){        //?????????????????????
                if (varPara.Lt_new>=(double)stationL.get(s) && varPara.Lt_new-dx <=(double)stationL.get(s) && (double)stationL.get(s)!=0.0 && stations.get(s).getPumps().size()>0){
                    varPara.dHL_new[kkk][x]=varPara.dHL_new[varPara.dHL_new.length-2][x]+50;
//                        varPara.dHL[kkk][x]=stationP[s]+varPara.dHL[kkk][x+1];
                    //if (varPara.dHL[kkk][x]>14000000) //??????????????????12MPa??????????????????????????????????????????
//                        if (false && varPara.dHL[kkk][x]>12500000 && T%10==0)
//                        {
//                            varPara.checkP=1;
//                            varPara.dHL[kkk][x]=12500001;
//                            //valveCalDp1(varPara.Qh/3600.0,D,5.68,1,1000);/////////////////////////////////////////////////////////////////
//                            varPara.Qh=valveCal(20000,varPara.dHL[kkk][x]-12500000,varPara.Qh);
//                        }
                    //varPara.checkP=1;
                }
            }
        }
        for (int xx = 0; xx <(varPara.line_l[varPara.i-1][3]-varPara.line_l[1][1])/dx; xx++) {
            if (xx <= (waterHeadLocation[kkk] - varPara.line_l[1][1]) / dx + 1)//????????????????????????
            {

            } else {
                varPara.dHL_new[kkk][xx] = varPara.dHL_new[varPara.dHL_new.length-2][xx];
            }

        }

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


    //getHb??????Hb??????????????????x?????????????????????y??????????????????,dv??????????????????,dt?????????,s???????????????????????????????????????,Pg???????????????
    double getHb(double x,double y,double dv,double dt,double s,double Pg){
        Pipeline pipeLine = pipeLines.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        double Hb;
        double hf;
        double xishu=1;
        double hl=getZ(varPara.line_l,varPara.line_d,s); ;          //****???????????????????????????
        double hg=getZ(varPara.line_l,varPara.line_d,x); ;          //??????????????????????????????

        //??????????????????????????????????????????????????????????????????
        hf=0.002174*Math.PI* oil.density*conPara.g*Math.pow(y,1.75)*Math.pow(oil.viscosity, 0.25)*(s-x)/Math.pow(A,1.25);
        Hb= oil.density*conPara.g*(hl-hg)+hf+Pg+xishu*conPara.g*dv*dt;
        return Hb;
    }
    //getHa?????????????????????????????????????????????Ha????????????x?????????????????????y??????????????????dv?????????????????????z??????????????????,p????????????????????????
    double getHa(double x,double y,double z,double dv,double dt,double p){
        Pipeline pipeLine = pipeLines.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        double h0=getZ(varPara.line_l,varPara.line_d,y);          //****?????????????????????
        double hp=getZ(varPara.line_l,varPara.line_d,x);            //??????????????????????????????
        double Ha;
        double hf;
        double l=x-y;
        double xishu=1;//???????????????

        hf= 0.002174*Math.PI*oil.density*conPara.g*Math.pow(z, 1.75)*Math.pow(oil.viscosity, 0.25)*l/Math.pow(A, 1.25);

        Ha=p- oil.density*conPara.g*(hp-h0)-hf-xishu*conPara.g*dv*dt;           //?????????

        return Ha;
    }

    //shuntai????????????????????????????????????x???????????????????????????????????????y???????????????????????????????????????z????????????????????????????????????a????????????????????????s????????????????????????
    //Pg??????????????????????????????p?????????????????????????????????f????????????,phi????????????
    double[] getPigV(double x,double y,double z,double a,double s,double Pg,double p,double f,double phi){
        Pipeline pipeLine = pipeLines.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Pig pig =pigs.get(0);
        double[] fanhui=new double[3];
        double vv;      //???????????????
        double vpig;    //?????????????????????
        double wucha;   //??????????????????????????????
        if((x-y<=20000*z*z)&&(x-y>=-20000*z*z)){//????????????20kPa??????????????????????????????????????????????????????????????????

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
            vv=fanhui[2]-z;     //????????????????????????????????????????????????
            fanhui[0]=getHb(a,fanhui[2],vv,varPara.deltaT,s,Pg);    //???????????????????????????
            fanhui[1]=getHa(a,f,fanhui[2],vv,varPara.deltaT,p);     //???????????????????????????
        }else{

            if (z < conPara.Ql/A && z >=0){
                fanhui[2]=conPara.Ql/A;
                if (fanhui[2] < conPara.Ql/A)
                    fanhui[2]=conPara.Ql/A;
            }else if (z>5){
                fanhui[2]=5;
            }else{
                //vpig=z+varPara.deltaT*(A*(x-y-2000))/pig.pigM+conPara.g*phi*varPara.deltaT;//?????????????????????700kg
                vpig=z+varPara.deltaT*(A*(x-y-20000*z*z))/pig.pigM-varPara.deltaT*conPara.g*phi;//?????????????????????700kg
                if (vpig < 0) vpig=conPara.Ql/A;
                fanhui[2]=vpig;
            }

        }
        return fanhui;//fanhui 2???????????????????????????1??????????????????a????????????,0?????????????????????b????????????
    }

    /**
     * ???????????????????????????
     * @param arr ??????????????? 500m?????????
     * @param num ????????????  1m??????499
     * @return ???????????????
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
                //System.out.println("???"+i+"???");
            }
        }
        return arrL;
    }

    /**
     * ??????????????????,???????????????m
     * @param arrL  ????????????????????????
     * @param arrZ  ????????????????????????
     * @param l ?????????????????????
     * @return ?????????
     */
    public double getZ(double [][]arrL,double [][]arrZ,double l){
        double z=0;
        for(int i=1;i<arrL.length;i++){
            if(l<arrL[i][2] && l>=arrL[i][1]){
                z=arrZ[i][1]+(l-arrL[i][1])*((arrZ[i][2]-arrZ[i][1])/(arrL[i][2]-arrL[i][1]));
            }else if(l<=arrL[i][3] && l>=arrL[i][2]){
                z=arrZ[i][2]+(l-arrL[i][2])*((arrZ[i][3]-arrZ[i][2])/(arrL[i][3]-arrL[i][2]));
            }
        }
        if (z == 0) System.out.println("??????????????????"+l);
        return z;
    }
    /**
     * ??????????????????????????????,???????????????m
     * @param arrL  ????????????????????????
     * @param arrZ  ????????????????????????
     * @param l ?????????????????????
     * @return ????????????????????????????????????tan?????sin??????????????
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
        if (Phi == 0) System.out.println("??????????????????");
        return Phi;
    }


    /**
     * ???????????????????????????????????????
     * @param arrL
     * @param l
     * @return ??????????????????1??????
     */
    public int getI(double[][] arrL,double l){
        int z=0;
        for(int i=1;i<arrL.length;i++){
            if(l<arrL[i][2] && l>=arrL[i][1]){
                z=i;
            }else if(l<arrL[i][3] && l>=arrL[i][2]){
                z=i;
            }
        }
        if (z == 0) System.out.println("??????????????????");
        return z;
    }


    /**
     * ???????????????L???????????????5???????????????1???????????????2???????????????3?????????,4?????????,6????????????
     * @param i L??????????????????
     * @param L ????????????????????????
     * @param pigL
     * @param flagpig ????????????????????????1????????????
     * @return
     */
    public double getFlowPattern(int i,double L,double pigStart,double pigL,double flagpig ,int num){
        int p=6;  //??????????????????????????????6

        if (varPara.line_l[i][1]<=L && varPara.line_l[i][2]>L){

            if (L-varPara.line_l[i][1]<varPara.lgk[i]){
                p=1;
            }else{
                p=2;
            }
        }else if (varPara.line_l[i][2]+0.5*(varPara.line_l[i][3]-varPara.line_l[i][2])<=L && varPara.line_l[i][3]>=L){
            p=2;
        }else if (varPara.line_l[i][2]<=L && varPara.line_l[i][2]+0.5*(varPara.line_l[i][3]-varPara.line_l[i][2])>=L){
            p=2;
        }
        if (L>pigStart && pigL > L && flagpig!=0){//????????????????????????????????????????????????
            p=3;
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

        if (L<varPara.oilL[num] && varPara.oilL[num]!=0){
            p=4;
        }




        return p;
    }

    public double getLiquidFlowRate(int i,double L,double pigStart,double pigL,double flagpig){
        double p=6;  //??????????????????????????????6
        Pipeline pipeLine = pipeLines.get(0);
        Oil oil = oils.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        if (varPara.line_l[i][1]<=L && varPara.line_l[i][2]>L){

            if (L-varPara.line_l[i][1]<varPara.lgk[i]){
                p=varPara.vll[i];
            }else{
                p=conPara.Ql/A;
            }
        }else if (varPara.line_l[i][2]+0.5*(varPara.line_l[i][3]-varPara.line_l[i][2])<=L && varPara.line_l[i][3]>=L){
            p=conPara.Ql/A;
        }else if (varPara.line_l[i][2]<=L && varPara.line_l[i][2]+0.5*(varPara.line_l[i][3]-varPara.line_l[i][2])>=L){
            p=conPara.Ql/A;
        }
        if (L>pigStart && pigL > L && flagpig!=0){//????????????????????????????????????????????????
            p=conPara.Ql/A;
        }else{
            if (varPara.line_l[i][1]<=L && varPara.line_l[i][2]>L){

                if (L-varPara.line_l[i][1]<varPara.lgk[i]){
                    p=varPara.vll[i];
                }else{
                    p=conPara.Ql/A;
                }
            }else if (varPara.line_l[i][2]<=L && varPara.line_l[i][3]>=L){
                p=conPara.Ql/A;
            }
        }

        return p;
    }

    //?????????????????????

    /**
     * ????????????
     * @param way ????????????
     * @param n ?????????
     * @return ??????????????????
     */
    public double pumpstation(int way, int n) {
        Pump pump = pumps.get(0);
        double h;
        double a[];
        pump.initPumpFunction();
        a = pump.getHead();
        //???????????????m??/??????
        double Q_h = conPara.Ql * 3600;
        if (way == 1) {
            h = n * (a[0] + a[1] * Q_h + a[2] * Math.pow(Q_h, 2));
        } else {
            h = a[0] + a[1] / n * Q_h + a[2] / (Math.pow(n, 2)) * Math.pow(Q_h, 2);
        }
        return h;
    }

    //???????????????????????????,?????????????????????????????????,???????????????1%??????
    public double[] frictionLoss(double re) {
        Pipeline pipeLine = pipeLines.get(0);
        Oil oil = oils.get(0);
        double a[] = new double[2];
        double m;
        double d = (pipeLine.getDiameter() - 2 * pipeLine.getThinkness());
        double D = (pipeLine.getDiameter() - 2 * pipeLine.getThinkness()) * Math.pow(10, -3);
        double re1 = 59.7 / Math.pow((2 * pipeLine.getRoughness() / d), (8 / 7d));//???????????????
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
     * way?????????????????????????????????n????????????,
     * m?????????????????????
     * a1??????????????????(1/0)???????????????????????????
     * ?????????????????????????????????b?????????????????????
     **/
    public void pumustation_time(int way, int n, int m, int a1, int b1) {
        Pump pump = pumps.get(0);
        double h;
        double a[];
        pump.initPumpFunction();
        a = pump.getHead();
        //?????????????????????????????????
        double Q_h = conPara.Ql * 3600;
        //??????
        if (m == 0) {
            //???????????????
            if (a1 == 1) {
                //??????
                if (way == 1) {

                }
            }
        }


    }


    //?????????

    /**
     * ???????????????????????????????????????????????????????????????
     * @param num ???????????????????????????????????????
     * @param dpL ???????????????????????????
     * @param waterL ????????????
     */
    public void startPump(int num,double[] dpL,double[] dpL_new,double waterL){
        int stationNow = 0; //???????????????????????????????????????
        int XXX = 0; //
        //double Lx=varPara.line_l[1][1];
        //for (int x=0;x<=(waterL-varPara.line_l[1][1])/500.0+1;x++)//???????????????????????????500m??????
        //{


//     if (num%10==0)
        varPara.startPumpFlag=0;


        for (int stationNum=varPara.p;stationNum<=varPara.pumpSta.size()-1;stationNum++){//????????????????????????????????????
            if ((double)varPara.pumpSta.get(stationNum)!=0 && (stations.get(stationNum).getStationType()==1|| stations.get(stationNum).getStationType()==4) &&
                    waterL >= (double)varPara.pumpSta.get(stationNum)){
                //waterL <= (double)varPara.pumpSta.get(stationNum+1)){
//                    if (varPara.p!=stationNum) {
//                        varPara.p1=0;
//                    }
//                    if (varPara.p1==0) {
//                        varPara.p1++;
//                        varPara.p++;
//                    }
                //???????????????????????????????????????????????????????????????
                stationNow=stationNum;
//                    if (stationNum==2){
//                        stationNow=4;
//                    }else if (stationNum==3){
//                        stationNow=6;
//                    }
                //break;

//                    for (int iu=0;iu<varPara.Hpump.length;iu++){
//                        varPara.Hpump[iu]=0.0;
//                    }

            }
//                else if(waterL > (double)varPara.pumpSta.get(varPara.pumpSta.size()-1)){
//                    stationNow=6;
//                }


        }


        //if (varPara.startPumpFlag[num-1]==0){//??????????????????????????????



//        if ((int)((waterL-varPara.line_l[1][1])/500.0)<0) {
//            System.out.println(waterL);
//            System.out.println(num/24.0);
//        }
        //if (varPara.startPumpFlag ==0) {         //????????????????????????0.5MPa???????????????
        if ((int)((waterL-varPara.line_l[1][1])/500.0)-2>0){
            XXX=(int)((waterL-varPara.line_l[1][1])/500.0)-2;
        }else{
            XXX=(int)((waterL-varPara.line_l[1][1])/500.0);
        }
//            if ((varPara.startPumpFlag111==1 || dpL_new[XXX] < 300000) && varPara.startPumpFlag ==0) {         //????????????????????????0.5MPa???????????????
        if ((dpL_new[XXX] < 300000) && varPara.startPumpFlag ==0) {         //????????????????????????0.5MPa???????????????
//            if (conPara.Ql*3600<0.9*varPara.Qh || varPara.startPumpFlag111==1 || (dpL[(int)((waterL-varPara.line_l[1][1])/500.0)]-getZ(varPara.line_l,varPara.line_d,waterL) < 30 || dpL_new[(int)((waterL-varPara.line_l[1][1])/500.0)] < 300000) && varPara.startPumpFlag ==0) {         //????????????????????????0.5MPa???????????????
//            if ((dpL[XXX]-getZ(varPara.line_l,varPara.line_d,waterL) < 80) && varPara.startPumpFlag ==0) {         //????????????????????????0.5MPa???????????????
            System.out.println("(dpL[(int)((waterL-varPara.line_l[1][1])/500.0)-1]-getZ(varPara.line_l,varPara.line_d,waterL-500) = " + (dpL[(int)((waterL-varPara.line_l[1][1])/500.0)-1]-getZ(varPara.line_l,varPara.line_d,waterL-500)));
            System.out.println("(dpL[(int)((waterL-varPara.line_l[1][1])/500.0)]-getZ(varPara.line_l,varPara.line_d,waterL) = " + (dpL[(int)((waterL-varPara.line_l[1][1])/500.0)]-getZ(varPara.line_l,varPara.line_d,waterL)));
            System.out.println("dpL_new[(int)((waterL-varPara.line_l[1][1])/500.0)-1] = " + dpL_new[(int)((waterL-varPara.line_l[1][1])/500.0)-1]);
            System.out.println("dpL_new[(int)((waterL-varPara.line_l[1][1])/500.0)] = " + dpL_new[(int)((waterL-varPara.line_l[1][1])/500.0)]);
//                varPara.startPumpFlag111=0;
            if (getStations().get(stationNow).getPumps().size()>=1&&varPara.startPumpFlag1[stationNow][getStations().get(stationNow).getPumps().size()-1]==1 && stationNow>=1){
                stationNow--;
                System.out.println(getStations().get(stationNow+1).getStationName()+"???????????????????????????????????????");
                if (getStations().get(stationNow).getPumps().size()>=1&&varPara.startPumpFlag1[stationNow][getStations().get(stationNow).getPumps().size()-1]==1 && stationNow>=1){
                    stationNow--;
                    System.out.println(getStations().get(stationNow+1).getStationName()+"?????????????????????????????????????????????");
                }
            }
            for (int pumpNum=0;pumpNum<getStations().get(stationNow).getPumps().size();pumpNum++){          //??????????????????????????????
                if ((getStations().get(stationNow).getPumps().get(pumpNum).getPumpType()==2||getStations().get(stationNow).getPumps().get(pumpNum).getPumpType()==1) && varPara.startPumpFlag1[stationNow][pumpNum]==0){        //?????????????????????????????????
                    getStations().get(stationNow).getPumps().get(pumpNum).setState(1);            //???????????????
                    varPara.startPumpFlag=1;
                    //pumpRev[stationNow][pumpNum] = getStations().get(stationNow).getPumps().get(pumpNum).getRev();     //???????????????????????????
                    varPara.pumpRev[stationNow][pumpNum] = 0.05+varPara.pumpRev[stationNow][pumpNum];

                    getStations().get(stationNow).getPumps().get(pumpNum).initPumpFunction();
                    varPara.a[pumpNum]=getStations().get(stationNow).getPumps().get(pumpNum).getHead();


                    varPara.Hpump[stationNow]=varPara.Hpump[stationNow]+(varPara.a[pumpNum][0]*pow(varPara.pumpRev[stationNow][pumpNum],2) + varPara.a[pumpNum][1]*pow(varPara.pumpRev[stationNow][pumpNum],2) * conPara.Ql*3600 + varPara.a[pumpNum][2] *pow(varPara.pumpRev[stationNow][pumpNum],2)* Math.pow(conPara.Ql*3600, 2))
                            -(varPara.a[pumpNum][0]*pow(varPara.pumpRev[stationNow][pumpNum]-0.05,2) + varPara.a[pumpNum][1]*pow(varPara.pumpRev[stationNow][pumpNum]-0.05,2) * conPara.Ql*3600 + varPara.a[pumpNum][2] *pow(varPara.pumpRev[stationNow][pumpNum]-0.05,2)* Math.pow(conPara.Ql*3600, 2));
//                        System.out.println("varPara.Hpump["+stationNow+"] = " + varPara.Hpump[stationNow]);
//                        System.out.println("varPara.a["+pumpNum+"][0] = " + varPara.a[pumpNum][0]);
//                        System.out.println("varPara.a["+pumpNum+"][1] = " + varPara.a[pumpNum][1]);
//                        System.out.println("varPara.a["+pumpNum+"][2] = " + varPara.a[pumpNum][2]);
                    //dpL[(int)(varPara.stationLLL[stationNow]/0.5)]=varPara.Hpump[stationNow]*oils.get(0).getDensity()*conPara.g+dpL[(int)(varPara.stationLLL[stationNow]/0.5)];
                    //varPara.Hs=dpL[(int)(station.getStationL()[stationNow]/0.5)];
                    //varPara.Hd[stationNow]=dpL[(int)(station.getStationL()[stationNow]/0.5)];
                    varPara.Hd[stationNow]=varPara.Hpump[stationNow]*oils.get(0).getDensity()*conPara.g;
                    System.out.println("???"+num/24.0+"h,???"+stationNow+"??????????????????????????????"+waterL/1000.0+"km???????????????"+stations.get(stationNow).getStationName()+"???"+(pumpNum+1)+"??????,"+ (int)(varPara.pumpRev[stationNow][pumpNum]/0.01)+"%??????");
                    //System.out.println("???"+num/12.0+"h,???"+(stationNow+1)+"????????????"+(pumpNum+1)+"??????,"+ (int)(varPara.pumpRev[stationNow][pumpNum]/0.01)+"%??????");
                    if (varPara.pumpRev[stationNow][pumpNum] >1.0){
                        varPara.startPumpFlag1[stationNow][pumpNum]=1;
//                            varPara.pumpRev[stationNow][pumpNum] = 0.4;
                    }
                    break;
                }else if(getStations().get(stationNow).getPumps().get(pumpNum).getPumpType()==5 && getStations().get(stationNow).getPumps().get(pumpNum).getState()==0){        //????????????????????????
                    getStations().get(stationNow).getPumps().get(pumpNum).setState(1);      //?????????
                    varPara.startPumpFlag=1;
                    getStations().get(stationNow).getPumps().get(pumpNum).initPumpFunction();
                    varPara.a[pumpNum]=getStations().get(stationNow).getPumps().get(pumpNum).getHead();
                    varPara.Hpump[stationNow]=varPara.Hpump[stationNow]+(varPara.a[pumpNum][0] + varPara.a[pumpNum][1] * conPara.Ql*3600 + varPara.a[pumpNum][2] * Math.pow(conPara.Ql*3600, 2));
                    //dpL[(int)(varPara.stationLLL[stationNow]/0.5)]=varPara.Hpump[stationNow]*oils.get(0).getDensity()*conPara.g+dpL[(int)(varPara.stationLLL[stationNow]/0.5)];
                    //varPara.Hd[stationNow]=dpL[(int)(varPara.stationLLL[stationNow]/0.5)];
                    varPara.Hd[stationNow]=varPara.Hpump[stationNow]*oils.get(0).getDensity()*conPara.g;
                    //varPara.Hs=dpL[(int)(station.getStationL()[stationNow]/0.5)];
                    System.out.println("???"+num/24.0+"h,???"+stations.get(stationNow).getStationName()+"?????????"+(pumpNum+1)+"??????");
                    //System.out.println("???"+num/12.0+"h,???"+(stationNow+1)+"????????????"+(pumpNum+1)+"??????");
                    break;
                }else {
//
                }


            }

            /**
             * ???????????????
             */



        }



        for (int s = 0; s < stations.size(); s++) {
            varPara.wL=varPara.Hpump[s]+varPara.wL;
            if (varPara.Hpump[s]*1000*9.81>stations.get(s).getMaxOutletPressure()*1e6){
                varPara.Hpump[s]=stations.get(s).getMaxOutletPressure()*1e6/(1000*9.81);
            }
            varPara.wL=varPara.Hpump[s]+varPara.wL;
        }
        if (varPara.wL>0){
            if (3600*pow((varPara.wL*1000.0/9.81-(getZ(varPara.line_l,varPara.line_d,waterL)-
                    getStations().get(0).getStationZ()))/(waterL-varPara.line_l[1][1])/5,1/1.75)>1.5*varPara.Qh) {
                varPara.allQ.add((1+(Math.log10(3600*pow((varPara.wL*1000.0/9.81-(getZ(varPara.line_l,varPara.line_d,waterL)-
                        getStations().get(0).getStationZ()))/(waterL-varPara.line_l[1][1])/5,1/1.75))/8))*varPara.Qh);
            }else if (3600*pow((varPara.wL*1000.0/9.81-(getZ(varPara.line_l,varPara.line_d,waterL)-
                    getStations().get(0).getStationZ()))/(waterL-varPara.line_l[1][1])/5,1/1.75)<0.8*varPara.Qh){
                varPara.allQ.add((1-(Math.log10(3600*pow((varPara.wL*1000.0/9.81-(getZ(varPara.line_l,varPara.line_d,waterL)-
                        getStations().get(0).getStationZ()))/(waterL-varPara.line_l[1][1])/5,1/1.75))/8))*varPara.Qh);
            }else{
                varPara.allQ.add(3600*pow((varPara.wL*1000.0/9.81-(getZ(varPara.line_l,varPara.line_d,waterL)-
                        getStations().get(0).getStationZ()))/(waterL-varPara.line_l[1][1])/5,1/1.75));
            }

//            conPara.Ql=;
        }

        cal_l0();

//        if (((double)varPara.allQ.get(varPara.num-1)-pow((varPara.wL*1000.0/9.81-(getZ(varPara.line_l,varPara.line_d,waterL)-
//                getStations().get(0).getStationZ()))/(waterL-varPara.line_l[1][1])/5,1/1.75))/conPara.Ql<5&&
//                (conPara.Ql-pow((varPara.wL*1000.0/9.81-(getZ(varPara.line_l,varPara.line_d,waterL)-
//                getStations().get(0).getStationZ()))/(waterL-varPara.line_l[1][1])/5,1/1.75))/conPara.Ql>0.5){
//            System.out.println("newQ = " + (pow((varPara.Hpump[0]*1000.0/9.81-(getZ(varPara.line_l,varPara.line_d,waterL)-
//                    getStations().get(0).getStationZ()))/(waterL-varPara.line_l[1][1])/5,1/1.75)));
//            conPara.Ql=pow((varPara.wL*1000.0/9.81-(getZ(varPara.line_l,varPara.line_d,waterL)-
//                    getStations().get(0).getStationZ()))/(waterL-varPara.line_l[1][1])/5,1/1.75);
//            System.out.println("Q1---------------------------------------------Q2");
//        }
//        else if (conPara.Ql-pow((varPara.wL*1000.0/9.81-(getZ(varPara.line_l,varPara.line_d,waterL)-
//                getStations().get(0).getStationZ()))/(waterL-varPara.line_l[1][1])/5,1/1.75)>0){
//            System.out.println("newQ??? = " + (pow((varPara.Hpump[0]*1000.0/9.81-(getZ(varPara.line_l,varPara.line_d,waterL)-
//                    getStations().get(0).getStationZ()))/(waterL-varPara.line_l[1][1])/5,1/1.75)));
//            conPara.Ql=conPara.Ql*0.9999;
//
//            if (varPara.num%50==0)System.out.println("Q???");
//        }else if (conPara.Ql-pow((varPara.wL*1000.0/9.81-(getZ(varPara.line_l,varPara.line_d,waterL)-
//                getStations().get(0).getStationZ()))/(waterL-varPara.line_l[1][1])/5,1/1.75)<0){
//            conPara.Ql=conPara.Ql*1.0001;
//            System.out.println("newQ??? = " + (pow((varPara.wL*1000.0/9.81-(getZ(varPara.line_l,varPara.line_d,waterL)-
//                    getStations().get(0).getStationZ()))/(waterL-varPara.line_l[1][1])/5,1/1.75)));
//            if (varPara.num%50==0)System.out.println("Q???");
//        }
        varPara.wL=0;
//        varPara.allQ.add(pow((varPara.Hpump[stationNow]-(getZ(varPara.line_l,varPara.line_d,waterL)- getStations().get(stationNow).getStationZ()))/(waterL-varPara.line_l[1][1])/0.008,1/1.75));

//        varPara.allQ.add(0.01);
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????
     * @param open ???????????????1?????????????????????0??????????????????
     * @param num ?????????
     * @param waterL ????????????
     */
    public void highPointExhaust(int open, int num,double waterL){
        Pipeline pipeLine = pipeLines.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double J=0.0246*Math.pow(conPara.Ql,1.75)*Math.pow(1.006e-6,0.25)/Math.pow(D,4.75);
        double F = A*oils.get(0).getDensity()*conPara.g*J;
        Oil oil = oils.get(0);
        int rrr0;//
        int numPipe;//
        if (open==1){
            for (int s = varPara.Vflag1; s < varPara.staV.size(); s++) {
                numPipe=getI(varPara.line_l,(double)varPara.staV.get(s)*1000);
                if (numPipe==0){
                    System.out.println("99999999999999999numpipe = " + numPipe +",stav=" +(double)varPara.staV.get(s)*1000 );
                }
                if (waterL>varPara.line_l[numPipe][3]){
                    if ((double)varPara.staV.get(s)*1000>=varPara.line_l[numPipe][1]){
                        /**
                         * 20210723 ???????????????????????????????????????????????????????????????????????????????????????????????????
                         */
                        varPara.Vflag1++;
                        varPara.numV++;
                        if (varPara.Pg_his[numPipe][(varPara.num/50-1)*5]==0.0){
                            for (rrr0=0; rrr0<varPara.Pg_his[0].length-2;rrr0++){
                                if (varPara.Pg_his[numPipe][rrr0]>0.0 && varPara.Pg_his[numPipe][rrr0+1]==0){
//                                    System.out.println("??????1111111111111111111111111111111111111111111111111111");
                                    break;
                                }
                            }
                        }else{
                            rrr0=(varPara.num/50-1)*5;
//                            System.out.println("cg2222222222222");
                        }
                        if (varPara.num/50>1){
//                            if (varPara.M_his[numPipe][rrr0]<0.1*1.206*A*(varPara.line_l[numPipe][2]-varPara.line_l[numPipe][1])){
//                                varPara.M_his[numPipe][rrr0]=0.1*1.206*A*(varPara.line_l[numPipe][2]-varPara.line_l[numPipe][1]);
//                            }
                            exitGas(varPara.numV,numPipe,varPara.num, varPara.M_his[numPipe][rrr0],
                                    1000000*varPara.Pg_his[numPipe][rrr0],
                                    1000*varPara.lg_his[numPipe][rrr0]);
                            exitGask(varPara.numV,numPipe,varPara.num, varPara.M_his[numPipe][rrr0],
                                    1000000*varPara.Pg_his[numPipe][rrr0],
                                    1000*varPara.lg_his[numPipe][rrr0]);

//                            System.out.println("varPara.num = " + varPara.num);
//                            System.out.println("numPipe = " + numPipe);
//                            System.out.println("numV = " + varPara.numV);
//                            System.out.println("varPara.M_his[numPipe][(varPara.num/50-1)*5] = " + varPara.M_his[numPipe][rrr0]);
//                            System.out.println("varPara.Pg_his[numPipe][(varPara.num/50-1)*5] = " + varPara.Pg_his[numPipe][rrr0]);
                        }else{
                            System.out.println("????????????????????????????????????????????????");
                        }

                    }else if ((double)varPara.staV.get(s)*1000>=varPara.line_l[numPipe][1] ){
                        System.out.println("???"+s+"??????????????????????????????");
                    }else{
                        System.out.println("???3495??????");
//                        System.out.println("s = " + s);
//                        System.out.println("numPipe = " + numPipe);
//                        System.out.println("varPara.line_l[numPipe][1] = " + varPara.line_l[numPipe][1]);
                    }

                }else{
                    break;
                }
            }
        }
    }
    /**
     * ????????????????????????????????????????????????????????????0???????????????????????????????????????
     * @param waterL ????????????
     * @param num ?????????
     */
    public void stopPump(int num, double waterL) {
        int stationNow = 0; //??????????????????
        for (int stationNum = 0; stationNum < stations.size(); stationNum++) {//????????????????????????????????????
            if (getStations().get(stationNow).getStationType() == 1.0 && waterL >= varPara.stationLLL[stationNum]) {
                //????????????????????????????????????????????????
                stationNow = stationNum;
                if(varPara.stopT%2==0) varPara.stopPumpFlag=0;
                if (varPara.stopPumpFlag == 0) {         //??????????????????
                    for (int pumpNum = 0; pumpNum < getStations().get(stationNow).getPumps().size(); pumpNum++) {          //??????????????????????????????
                        if (getStations().get(stationNow).getPumps().get(pumpNum).getState() == 1) {        //??????????????????
                            //getStations().get(stationNow).getPumps().get(pumpNum).setState(2);            //???????????????????????????
                            //varPara.Hd[stationNow]=0.5*varPara.Hd[stationNow];
                            //varPara.Hpump[stationNow]=0.5*varPara.Hpump[stationNow];
                            System.out.println("???"+num/12.0+"h,????????????"+(stationNow+1)+"????????????"+(pumpNum+1)+"??????");
                            varPara.stopPumpFlag = 2;
                        }
                    }
                }else if (varPara.stopPumpFlag == 2) {         //??????????????????
                    for (int pumpNum = 0; pumpNum < getStations().get(stationNow).getPumps().size(); pumpNum++) {          //??????????????????????????????
                        if (getStations().get(stationNow).getPumps().get(pumpNum).getState() == 2) {        //??????????????????
                            //getStations().get(stationNow).getPumps().get(pumpNum).setState(0);            //?????????
                            //varPara.Hd[stationNow]=0;
                            //varPara.Hpump[stationNow]=0;
                            System.out.println("???"+((num+1)/12.0)+"h,????????????"+(stationNow+1)+"????????????"+(pumpNum+1)+"??????");
                            //varPara.stopPumpFlag = 1;
                        }
                    }
                }
                //varPara.Hd[stationNow]=0;
            }
        }
    }
    public void stopPump1(int num, double waterL){
        System.out.println("???"+((num+1)/24.0)+"h,????????????");
        //System.out.println("???"+((num+1)/12.0)+"h,????????????");
    }
    public void startPumpAgain(int num, double timeStop){
        System.out.println("???"+((num+18+timeStop/300)/24.0)+"h,????????????");
        //System.out.println("???"+((num+12+timeStop/300)/12.0)+"h,????????????");
    }

    public void staticP(int n, double waterL,double dx) {
        Oil oil= oils.get(0);
        int i,ii=1; //
        double Lx = waterL; //????????????
        varPara.allLineStaticP[0][(int)((waterL-varPara.line_l[1][1])/dx)]=conPara.p0;
        varPara.allLineStaticP[1][(int)((waterL-varPara.line_l[1][1])/dx)]=2520-getZ(varPara.line_l, varPara.line_d,waterL);



        if (waterL >= varPara.line_l[n][2] && waterL <= varPara.line_l[n][3]) {//??????????????????
            //???????????????????????????????????????
            for (int x=(int)((waterL-varPara.line_l[1][1])/dx);x>=1;x--)//????????????????????????,????????????
            {
                Lx=varPara.line_l[1][1]+x*dx;

                varPara.allLineStaticP[1][x]=getZ(varPara.line_l, varPara.line_d,Lx);
                i=getI(varPara.line_l, Lx);
                //if (varPara.line_l[i][3]-0.5*varPara.lgk[i]*varPara.Hgk[i]>varPara.line_l[i][2]){
                if (Lx >= varPara.line_l[i][2] && Lx <= varPara.line_l[i][3]) {//?????????????????????????????????
                    if(ii==1) {
                        varPara.allLineStaticP[0][(int)((waterL-varPara.line_l[1][1])/dx)]=0.5*((Lx-varPara.line_l[i][2])*
                                varPara.slopeU[i][0]+(varPara.line_l[i][2]-varPara.line_l[i][1]-varPara.lgk[i])
                                *varPara.slopeD[i][0])*oil.getDensity()*conPara.g+conPara.p0;
                        ii=0;
                    }
                    varPara.allLineStaticP[0][x]=varPara.allLineStaticP[0][x+1]+dx*varPara.slopeU[i][0]*oil.getDensity()*conPara.g;
                }else if (Lx >= varPara.line_l[i][1] && Lx <= varPara.line_l[i][1]+varPara.lgk[i]){
                    varPara.allLineStaticP[0][x]=varPara.allLineStaticP[0][x+1]-dx*varPara.slopeD[i][0]*oil.getDensity()*conPara.g;
                }else if (Lx >= varPara.line_l[i][1]+varPara.lgk[i] && Lx <= varPara.line_l[i][2]){
                    varPara.allLineStaticP[0][x]=varPara.allLineStaticP[0][x+1]-dx*varPara.slopeD[i][0]*oil.getDensity()*conPara.g;
                }
                if(varPara.allLineStaticP[0][x]<1705){      //??????15????????????????????????
                    varPara.allLineStaticP[0][x]=1705;
                }
                for (int stationNum=0;stationNum<stations.size();stationNum++){//????????????????????????????????????
                    //if (stations.get(stationNum).getStationType()==3.0 && Lx >= varPara.stationLLL[stationNum]-250 && Lx <= varPara.stationLLL[stationNum]+250){
                    if (Lx >= varPara.stationLLL[stationNum]-250 && Lx <= varPara.stationLLL[stationNum]+250){
                        varPara.allLineStaticP[0][x]=conPara.p0;
                    }
                }
                //}else{
                //varPara.allLineStaticP[0][x] = varPara.allLineStaticP[0][x + 1];
                //}

            }
        }else if (waterL >= varPara.line_l[n][1] && waterL <= varPara.line_l[n][2]) {//??????????????????

            for (int x=(int)((waterL-varPara.line_l[1][1])/dx);x>=1;x--)//????????????????????????,????????????
            {
                Lx=varPara.line_l[1][1]+x*dx;

                i=getI(varPara.line_l, Lx);
                varPara.allLineStaticP[1][x]=getZ(varPara.line_l, varPara.line_d,Lx);
                //if (varPara.line_l[i][3]-0.5*varPara.lgk[i]*varPara.Hgk[i]>varPara.line_l[i][2]) {
                if (Lx >= varPara.line_l[i][2] && Lx <= varPara.line_l[i][3]) {//?????????????????????????????????
                    varPara.allLineStaticP[0][x] = varPara.allLineStaticP[0][x + 1] + dx * varPara.slopeU[i][0] * oil.getDensity() * conPara.g;
                } else if (Lx >= varPara.line_l[i][1] && Lx <= varPara.line_l[i][1] + varPara.lgk[i]) {
                    varPara.allLineStaticP[0][x] = varPara.allLineStaticP[0][x + 1] - dx * varPara.slopeD[i][0] * oil.getDensity() * conPara.g;
                    ;
                } else if (Lx >= varPara.line_l[i][1] + varPara.lgk[i] && Lx <= varPara.line_l[i][2]) {
                    if (i == n) {
                        varPara.allLineStaticP[0][x] = conPara.p0;
                    } else {
                        varPara.allLineStaticP[0][x] = varPara.allLineStaticP[0][x + 1] - dx * varPara.slopeD[i][0] * oil.getDensity() * conPara.g;
                    }
                }
                for (int stationNum=0;stationNum<stations.size();stationNum++){//????????????????????????????????????
                    //if (stations.get(stationNum).getStationType()==3.0 && Lx >= varPara.stationLLL[stationNum]-250 && waterL <= varPara.stationLLL[stationNum]+250){
                    if (Lx >= varPara.stationLLL[stationNum]-250 && waterL <= varPara.stationLLL[stationNum]+250){
                        varPara.allLineStaticP[0][x]=conPara.p0;
                    }
                }
                if (varPara.allLineStaticP[0][x] < 1705) {//??????15????????????????????????
                    varPara.allLineStaticP[0][x] = 1705;
                }
                //}else{
                //varPara.allLineStaticP[0][x] = varPara.allLineStaticP[0][x + 1];
                //}
            }
        }else{
            System.out.println("???????????? ????????????????????????");
        }
    }

    public void staticP_new(int n, double waterL,double dx) {
        Oil oil= oils.get(0);
        double Lx;
        double []MaxZ=new double[50]; //
        List<Double> maxZ = new ArrayList();
        List<Double> max0 = new ArrayList();
        List<Double> sta = new ArrayList();

        for (int s=0;s<varPara.pumpSta.size()-1;s++){        //?????????????????????
            if ((double)varPara.pumpSta.get(s)!=0.0){
                sta.add((double)varPara.pumpSta.get(s));
                System.out.println(stations.get(s).getStationName());
                maxZ.add(0.0);
                max0.add(0.0);
            }
        }

        //???????????????????????????????????????
        for (int x = 0;x< (int) ((waterL - varPara.line_l[1][1]) / dx +1 );  x++)//????????????????????????,????????????
        {
            Lx = varPara.line_l[1][1] + x * dx;
            for (int s=0;s<sta.size()-1;s++){        //?????????????????????
                if (Lx>=sta.get(s) && Lx <=sta.get(s+1)){
                    max0.set(s,varPara.dHL[varPara.dHL.length-2][x]+10);
                    if (max0.get(s)>maxZ.get(s)){
                        maxZ.set(s,max0.get(s));
                    }
                }else if (Lx<sta.get(s)&&s==0){
                    max0.set(s,varPara.dHL[varPara.dHL.length-2][x]+10);
                    if (max0.get(s)>maxZ.get(s)){
                        maxZ.set(s,max0.get(s));
                    }
                }else if (Lx>sta.get(s+1)&&s==sta.size()-2){
                    max0.set(s,varPara.dHL[varPara.dHL.length-2][x]+10);
                    if (max0.get(s)>maxZ.get(s)){
                        maxZ.set(s,max0.get(s));
                    }
                }
            }
        }
        for (int s = 0; s < maxZ.size(); s++) {
            System.out.println("s = " + s);
            System.out.println("maxZ = " + maxZ.get(s));
        }

        for (int x = 0;x< (int) ((waterL - varPara.line_l[1][1]) / dx +1 );  x++)//????????????????????????,????????????
        {
            Lx = varPara.line_l[1][1] + x * dx;
            for (int s=0;s<sta.size()-1;s++){        //?????????????????????
                if (Lx>=sta.get(s) && Lx <=sta.get(s+1)){
                    varPara.allLineStaticP[0][x] = (maxZ.get(s) - getZ(varPara.line_l, varPara.line_d, Lx)) * oil.getDensity() * 9.81;
                }else if (Lx<sta.get(s)&&s==0){
                    varPara.allLineStaticP[0][x] = (maxZ.get(s) - getZ(varPara.line_l, varPara.line_d, Lx)) * oil.getDensity() * 9.81;
                }else if (Lx>sta.get(s+1)&&s==sta.size()-2){
                    varPara.allLineStaticP[0][x] = (maxZ.get(s) - getZ(varPara.line_l, varPara.line_d, Lx)) * oil.getDensity() * 9.81;
                }
            }
        }
    }
//    public void staticP_new(int n, double waterL,double dx) {
//        Oil oil= oils.get(0);
//        int i,ii=1; //
//        double Lx = waterL; //????????????
//        varPara.allLineStaticP[0][(int)((waterL-varPara.line_l[1][1])/dx)]=(2520-getZ(varPara.line_l, varPara.line_d,waterL))*oil.getDensity()*9.81;
//        varPara.allLineStaticP[1][(int)((waterL-varPara.line_l[1][1])/dx)]=getZ(varPara.line_l, varPara.line_d,waterL);
//
//
//
//
//        //???????????????????????????????????????
//        for (int x = (int) ((waterL - varPara.line_l[1][1]) / dx); x >= 1; x--)//????????????????????????,????????????
//        {
//            Lx = varPara.line_l[1][1] + x * dx;
//
//            varPara.allLineStaticP[0][x] = (2520 - getZ(varPara.line_l, varPara.line_d, Lx)) * oil.getDensity() * 9.81;
//
//
//        }
//    }
    /**
     * ????????????????????????????????????????????????????????????
     * @param i ?????????
     * @param lgk ????????????????????????
     * @param Pgk ???????????????????????????
     * @return ????????????????????????2???????????????
     */

    public double [] exitGas(int numV, int i,int num,double Mg,double Pgk,double lgk){

        Pipeline pipeLine = pipeLines.get(0);
        Oil oil = oils.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double []exitGas=new double[5];
        double []Ppaiqi=new double[varPara.k+1];
        double []Lpaiqi=new double[varPara.k+1];
        double []rhoV=new double[varPara.k+1];
        double []PCR=new double[varPara.k+1];
        double []zlll=new double[varPara.k+1];
        double []MG=new double[varPara.k+1];
        double []his=new double[varPara.k+1];
        if (Mg<1.206*A*(varPara.line_l[i][2]-varPara.line_l[i][1]) || lgk<(varPara.line_l[i][2]-varPara.line_l[i][1]) || Pgk<150000){
            Mg=1.206*A*(varPara.line_l[i][2]-varPara.line_l[i][1]);
            lgk=(varPara.line_l[i][2]-varPara.line_l[i][1]);
            Pgk=250000;
        }
        double rhoe,ue,Mae,Te,deth,eTime=0.2,Flag=0;
        int t_record=0;
        Lpaiqi[varPara.num]=lgk;
        MG[varPara.num]=Mg;
        Ppaiqi[varPara.num]=Pgk;

        for (int iter = varPara.num; iter < ((int)varPara.T*12*2+2); iter++){

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

            zlll[iter] = rhoe*ue*(Math.PI*Math.pow(0.1,2)/4.0);///////////////////////////////////

            //zlll[iter] = rhoe*ue*(Math.PI*Math.pow(stations.get(StationsNum).getValves().get(0).getValveD(),2)/4.0);
            MG[iter+1] = MG[iter] - zlll[iter]*1500*varPara.deltaT;


            varPara.dMg_p[numV][iter+1]=varPara.dMg_p[numV][iter]+zlll[iter]*1500*varPara.deltaT;


            Ppaiqi[iter+1] = Ppaiqi[iter]-(Pgk*(zlll[iter]*1500*varPara.deltaT)/Mg);

            Lpaiqi[iter+1] = Lpaiqi[iter]-(lgk*(zlll[iter]*1500*varPara.deltaT)/Mg);

//            if (Double.isNaN(zlll[iter])){
//                if (varPara.dMg_p[numV][iter]<=Mg){
//                    varPara.dMg_p[numV][iter+1]=varPara.dMg_p[numV][iter]+0.01*(Mg-varPara.dMg_p[numV][iter]);
//                }else{
//                    varPara.dMg_p[numV][iter+1]=0;
//                }
//            }
//                System.out.println("varPara.Pgk["+i+"]="+Pgk);
//                varPara.Pgk[i] = Ppaiqi[iter];
//                System.out.println("varPara.Pgk["+i+"]="+varPara.Pgk[i]);
//                System.out.println("varPara.lgk["+i+"]="+lgk);
//                varPara.lgk[i] = Lpaiqi[iter+1];
//                System.out.println("varPara.lgk["+i+"]="+varPara.lgk[i]);
//
            if ((Ppaiqi[iter+1] - conPara.p0) < 1 || Lpaiqi[iter+1] < 5){

                if (Ppaiqi[iter]>0){
//                    System.out.println("varPara.Pgk["+i+"]="+Pgk);
                    varPara.Pgk[i] = Ppaiqi[iter];
//                    System.out.println("varPara.Pgk["+i+"]="+varPara.Pgk[i]);
                }else{
                    varPara.Pgk[i]=101325;
                }
                if (Ppaiqi[iter]>0){
//                    System.out.println("varPara.lgk["+i+"]="+lgk);
                    varPara.lgk[i] = Lpaiqi[iter+1];
//                    System.out.println("varPara.lgk["+i+"]="+varPara.lgk[i]);
                }else{
                    varPara.lgk[i]=5;
                }
//
                exitGas[2] = (iter-varPara.num)*1500*eTime;
                for (int num22=num;num22<varPara.lg_fff[i].length-(int)exitGas[2]/300-1;num22++){
                    varPara.lg_fff[i][(int)exitGas[2]/300+num22]=Lpaiqi[iter+1];
//                    System.out.println("varPara.lg_fff["+i+"]["+((int)exitGas[2]/300+num22)+"]="+varPara.lg_fff[i][(int)exitGas[2]/300+num22]);
                }

                System.out.println("???" + (i+1) + "???U????????????????????????" + varPara.num/24.0 + "h????????????" + exitGas[2] + "???");
//                varPara.dMg_p[2]=Ppaiqi;
//                varPara.dMg_p[3]=Lpaiqi;
//                varPara.dMg_p[4][0]=exitGas[2];
                //System.out.println("???" + (i+1) + "???U????????????????????????" + varPara.num/12.0 + "h????????????" + exitGas[2] + "???");
                varPara.ssr[i][0]=1;   //??????????????????????????????????????????
                //t_record=t_record+t3[i];
                break;
            }




        }



        return exitGas;
    }
    public double [] exitGask(int numV, int i,int num,double Mg,double Pgk,double lgk){

        Pipeline pipeLine = pipeLines.get(0);
        Oil oil = oils.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double []exitGas=new double[5];
        double []Ppaiqi=new double[varPara.k+1];
        double []Lpaiqi=new double[varPara.k+1];
        double []rhoV=new double[varPara.k+1];
        double []PCR=new double[varPara.k+1];
        double []zlll=new double[varPara.k+1];
        double []MG=new double[varPara.k+1];
        double []his=new double[varPara.k+1];
        if (Mg<1.206*A*(varPara.line_l[i][2]-varPara.line_l[i][1]) || lgk<(varPara.line_l[i][2]-varPara.line_l[i][1]) || Pgk<150000){
            Mg=1.206*A*(varPara.line_l[i][2]-varPara.line_l[i][1]);
            lgk=(varPara.line_l[i][2]-varPara.line_l[i][1]);
            Pgk=250000;
        }
        double rhoe,ue,Mae,Te,deth,eTime=0.2,Flag=0;
        int t_record=0;
        Lpaiqi[0]=lgk;
        MG[0]=Mg;
        Ppaiqi[0]=Pgk;

        for (int iter = 0; iter < varPara.k-1; iter++){

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

            zlll[iter] = rhoe*ue*(Math.PI*Math.pow(0.1,2)/4.0);///////////////////////////////////

            //zlll[iter] = rhoe*ue*(Math.PI*Math.pow(stations.get(StationsNum).getValves().get(0).getValveD(),2)/4.0);
            MG[iter+1] = MG[iter] - zlll[iter]*5*varPara.deltaT;


            varPara.dMg_pk[numV][iter+1]=varPara.dMg_pk[numV][iter]+zlll[iter]*5*varPara.deltaT;


            Ppaiqi[iter+1] = Ppaiqi[iter]-(Pgk*(zlll[iter]*5*varPara.deltaT)/Mg);

            Lpaiqi[iter+1] = Lpaiqi[iter]-(lgk*(zlll[iter]*5*varPara.deltaT)/Mg);

//            if (Double.isNaN(zlll[iter])){
//                if (varPara.dMg_p[numV][iter]<=Mg){
//                    varPara.dMg_p[numV][iter+1]=varPara.dMg_p[numV][iter]+0.01*(Mg-varPara.dMg_p[numV][iter]);
//                }else{
//                    varPara.dMg_p[numV][iter+1]=0;
//                }
//            }
//                System.out.println("varPara.Pgk["+i+"]="+Pgk);
//                varPara.Pgk[i] = Ppaiqi[iter];
//                System.out.println("varPara.Pgk["+i+"]="+varPara.Pgk[i]);
//                System.out.println("varPara.lgk["+i+"]="+lgk);
//                varPara.lgk[i] = Lpaiqi[iter+1];
//                System.out.println("varPara.lgk["+i+"]="+varPara.lgk[i]);
//
            if ((Ppaiqi[iter+1] - conPara.p0) < 1 || Lpaiqi[iter+1] < 5){

                if (Ppaiqi[iter]>0){
//                    System.out.println("varPara.Pgk["+i+"]="+Pgk);
                    varPara.Pgk[i] = Ppaiqi[iter];
//                    System.out.println("varPara.Pgk["+i+"]="+varPara.Pgk[i]);
                }else{
                    varPara.Pgk[i]=101325;
                }
                if (Ppaiqi[iter]>0){
//                    System.out.println("varPara.lgk["+i+"]="+lgk);
                    varPara.lgk[i] = Lpaiqi[iter+1];
//                    System.out.println("varPara.lgk["+i+"]="+varPara.lgk[i]);
                }else{
                    varPara.lgk[i]=5;
                }
//
                exitGas[2] = (iter)*5*eTime;
//                for (int num22=num;num22<varPara.lg_fff[i].length-(int)exitGas[2]/300-1;num22++){
//                    varPara.lg_fff[i][(int)exitGas[2]/300+num22]=Lpaiqi[iter+1];
////                    System.out.println("varPara.lg_fff["+i+"]["+((int)exitGas[2]/300+num22)+"]="+varPara.lg_fff[i][(int)exitGas[2]/300+num22]);
//                }

//                System.out.println("???" + (i+1) + "???U????????????????????????" + varPara.num/24.0 + "h????????????" + exitGas[2] + "???");
//                varPara.dMg_p[2]=Ppaiqi;
//                varPara.dMg_p[3]=Lpaiqi;
//                varPara.dMg_p[4][0]=exitGas[2];
                //System.out.println("???" + (i+1) + "???U????????????????????????" + varPara.num/12.0 + "h????????????" + exitGas[2] + "???");
//                varPara.ssr[i][0]=1;   //??????????????????????????????????????????
                //t_record=t_record+t3[i];
                break;
            }




        }



        return exitGas;
    }
    public double [] exitGas_stop(int numV, int i,int num,double Mg,double Pgk,double lgk){

        Pipeline pipeLine = pipeLines.get(0);
        Oil oil = oils.get(0);
        double D=pipeLine.getDiameter()-2*pipeLine.getThinkness();
        double r=D/2;
        double A=Math.PI*D*D/4.0;
        double []exitGas=new double[5];
        double []Ppaiqi=new double[varPara.k+1];
        double []Lpaiqi=new double[varPara.k+1];
        double []rhoV=new double[varPara.k+1];
        double []PCR=new double[varPara.k+1];
        double []zlll=new double[varPara.k+1];
        double []MG=new double[varPara.k+1];
        double []his=new double[varPara.k+1];
        if (Mg<1.2*1.206*A*(varPara.line_l[i][2]-varPara.line_l[i][1]) || lgk<1.2*(varPara.line_l[i][2]-varPara.line_l[i][1]) || Pgk<150000){
            Mg=1.2*1.206*A*(varPara.line_l[i][2]-varPara.line_l[i][1]);
            lgk=1.2*(varPara.line_l[i][2]-varPara.line_l[i][1]);
            Pgk=350000;
        }
        double rhoe,ue,Mae,Te,deth,eTime=0.2,Flag=0;
        int t_record=0;
        Lpaiqi[varPara.num]=lgk;
        MG[varPara.num]=Mg;
        Ppaiqi[varPara.num]=Pgk;

        for (int iter = varPara.num; iter < ((int)varPara.T*12*2+2); iter++){

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

            zlll[iter] = rhoe*ue*(Math.PI*Math.pow(0.1,2)/4.0);///////////////////////////////////

            //zlll[iter] = rhoe*ue*(Math.PI*Math.pow(stations.get(StationsNum).getValves().get(0).getValveD(),2)/4.0);
            MG[iter+1] = MG[iter] - zlll[iter]*1500*varPara.deltaT;


            varPara.dMg_p_stop[numV][iter+1]=varPara.dMg_p[numV][iter]+zlll[iter]*1500*varPara.deltaT;


            Ppaiqi[iter+1] = Ppaiqi[iter]-(Pgk*(zlll[iter]*1500*varPara.deltaT)/Mg);

            Lpaiqi[iter+1] = Lpaiqi[iter]-(lgk*(zlll[iter]*1500*varPara.deltaT)/Mg);

//            if (Double.isNaN(zlll[iter])){
//                if (varPara.dMg_p[numV][iter]<=Mg){
//                    varPara.dMg_p[numV][iter+1]=varPara.dMg_p[numV][iter]+0.01*(Mg-varPara.dMg_p[numV][iter]);
//                }else{
//                    varPara.dMg_p[numV][iter+1]=0;
//                }
//            }
//                System.out.println("varPara.Pgk["+i+"]="+Pgk);
//                varPara.Pgk[i] = Ppaiqi[iter];
//                System.out.println("varPara.Pgk["+i+"]="+varPara.Pgk[i]);
//                System.out.println("varPara.lgk["+i+"]="+lgk);
//                varPara.lgk[i] = Lpaiqi[iter+1];
//                System.out.println("varPara.lgk["+i+"]="+varPara.lgk[i]);
//
            if ((Ppaiqi[iter+1] - conPara.p0) < 1 || Lpaiqi[iter+1] < 5){

                if (Ppaiqi[iter]>0){
//                    System.out.println("varPara.Pgk["+i+"]="+Pgk);
                    varPara.Pgk[i] = Ppaiqi[iter];
//                    System.out.println("varPara.Pgk["+i+"]="+varPara.Pgk[i]);
                }else{
                    varPara.Pgk[i]=101325;
                }
                if (Ppaiqi[iter]>0){
//                    System.out.println("varPara.lgk["+i+"]="+lgk);
                    varPara.lgk[i] = Lpaiqi[iter+1];
//                    System.out.println("varPara.lgk["+i+"]="+varPara.lgk[i]);
                }else{
                    varPara.lgk[i]=5;
                }
//
                exitGas[2] = (iter-varPara.num)*1500*eTime;
                for (int num22=num;num22<varPara.lg_fff[i].length-(int)exitGas[2]/300-1;num22++){
                    varPara.lg_fff[i][(int)exitGas[2]/300+num22]=Lpaiqi[iter+1];
//                    System.out.println("varPara.lg_fff["+i+"]["+((int)exitGas[2]/300+num22)+"]="+varPara.lg_fff[i][(int)exitGas[2]/300+num22]);
                }

                System.out.println("???" + (i+1) + "???U????????????????????????" + varPara.num/24.0 + "h????????????" + exitGas[2] + "???");
//                varPara.dMg_p[2]=Ppaiqi;
//                varPara.dMg_p[3]=Lpaiqi;
//                varPara.dMg_p[4][0]=exitGas[2];
                //System.out.println("???" + (i+1) + "???U????????????????????????" + varPara.num/12.0 + "h????????????" + exitGas[2] + "???");
                varPara.ssr[i][0]=1;   //??????????????????????????????????????????
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
     * ???????????????????????????????????????
     * @param Cv ??????????????????????????????
     * @param dP ?????????????????????
     * @param Q1 ?????????
     * @return ?????????
     */
    public double valveCal(double Cv,double dP,double Q1){
        double Q2=varPara.Qh;
        if (Cv>=10000){
            Q2=Q1*pow(1-dP/(Cv/1000),0.5);
        }else if (Cv<10000){
            Q2=Q1*pow(1-dP/((Cv+10000)/1000),0.5);
        }
        return Q2;
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


    public List<Pipeline> getPipeLines() {
        return pipeLines;
    }

    public void setPipeLines(List<Pipeline> pipeLines) {
        this.pipeLines = pipeLines;
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




