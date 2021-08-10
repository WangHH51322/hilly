package cn.edu.cup.hilly.calculate.hilly.large;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;

import java.util.ArrayList;
import java.util.List;


@IOElement(name = "variableParameter")
public class Varpara {

    double deltaT=0.2;     //压缩时间步长0.2s

    double T=150;           //模拟总时长，h

    @InputField(name = "designFlow", unit = "m3/h")
    double Qh;           //设计投产流量，m3/h

    @InputField(name = "waterFillingLength", unit = "km")
    double waterLength;           //充水长度，m3/h

    @InputField(name = "pressureMode", unit = "")
    int Pm;           //压力模式，0，不开启指定压力，1，开启指定压力

    @InputField(name = "highOpen", unit = "")
    int highOpen;           //高点排气模式，0，不开启，1，开启

    @InputField(name = "putPigFlag", unit = "")
    int pigFlag;        //投放清管器的标记

    double deltaX = 500;    //其他流动时的空间步长1300m
    double hl;    //

    int i;                 //大落差段数,实际段数加一，再加上固定点数，因为数组从零开始，需要与高程、里程数组配合

    int k=0;            //模拟时长，300002*0.2=60000s=16.6h

    int kk = 1;            //k的备份
    int kkstop = 1;            //停输时刻的备份
    int rrstop = 1;            //停输时刻的备份
    int numstop = 1;            //停输时刻的备份
    int stopT = 1;            //停输时步数
    int ii = 1;            //i的备份


    int kt;            //模拟总时步的计算
    double Lt;            //压降计算的水头标计长度
    double Lt_new;            //压降计算的水头标计长度

    double sumTest;
    double[][] Y;          //矩阵计算历史数据存储
    double[][] Pg_his;     //气体压力  历史数据
    double[][] Deng_his;     //气体密度  历史数据
    double[][] lg_his;     //气段长度  历史数据
    double[][] h2_his;     //上坡液位  历史数据
    double[][] h1_his;     //下坡液位  历史数据

    double[][] dMg_out_his;      //净流出  历史数据
    double[][] dMg_in_his;      //净流入气体质量  历史数据
    double[][] backPressure_his;       //背压 历史数据
    double[][] M_his;       //气段质量，历史数据

    double[] hgs_k;       //气段尾部截面含气率1

    double[] hgw_k;       //气段尾部截面含气率2
    double[] hl_k;       //气段截面持液率

    double[] MM;      //气段质量；

    //有内存限制，不能太长

    double[] delta;     //每个下坡段的液相截面积
    double[] Sl;        //每个下坡段的液相湿周
    double[] Al;        //每个下坡段的液相湿周
    double[] vll;       //液相流速

    double[] t2;     //粗算气相积累时间，，也就是液相从高点到低点的时间
    double[] gasRatio;//初始截面含气率计算
    double[] V0;     //初始积气段的气体体积
    double[] M0;     //初始积气段的气体质量，密度取1.205kg/m3
    double[] h1k;    //下坡段初始液位高度
    double[] h2k;    //上坡段初始液位高度
    double[] Pgk;    //初始气相压力，大气压
    double[] Hgk;         //初始截面含气率
    double[] uk;          //水头在上坡段的前进速度
    double[] lgk;         //初始气段长度，即下坡段长度
    double[] dMg_in;     //本段净流入气体质量
    double[] dMg_out;    //净流出气体质量
    double[] MGk;      //下坡段的气体质量，，??排气后
    double[] Dengk;      //下坡段的气体密度


    double[] Qh1k;    //下坡段初始液位高度
    double[] Qh2k;    //上坡段初始液位高度
    double[] QPgk;    //初始气相压力，大气压
    double[] QHgk;         //初始截面含气率
    double[] Quk;          //水头在上坡段的前进速度
    double[] Qlgk;         //初始气段长度，即下坡段长度
    double[] QdMg_in;     //本段净流入气体质量
    double[] QdMg_out;    //净流出气体质量
    double[] QMGk;      //下坡段的气体质量，，??排气后
    double[] QDengk;      //下坡段的气体密度

    double[][] slopeD;      //下坡段高程里程比的绝对值
    double[][] line_l;      //沿线里程，接收一维里程数组转化为三点式里程
    double[][] line_d;      ///沿线高程，接收一维高程数组转化为三点式高程
    double[][] line_lll;      ///全线沿线高程，接收一维高程数组转化为三点式高程
    double[][] line_ddd;      ///全线沿线里程，接收一维高程数组转化为三点式高程
    double[][] slopeU;      //上坡段高程里程比的绝对值

    double[] waterHeadLocation;     //水头位置,在不同时刻水头所到达的位置，用里程表示，单位m
    double[] oilHeadLocation;     //水头位置,在不同时刻水头所到达的位置，用里程表示，单位m
    double[] lf;     //分层流长度
    double[] lfk;     //各段的分层流长度
    double[] lp;     //气泡流长度
    double[] lpk;     //各段的气泡流长度

    double[] Time;                   //统筹时步与时间，单位，小时，hr
    double[] startPressure;          //起点压头，随水头位置变化而变化
    double[][] flowPressure;          //全过程，全地形的动压
    int[][] ssr;
    double[] backPressure;    //各管段的背压
    double[] pre_back;    //破碎前的背压

    double[] f_i;         //相间摩阻系数
    double[] f_l;         //液壁摩阻系数
    int numV=0;//
    double[][] dMg_p;         //排气参数存储
    double[][] dMg_pk;         //排气参数存储
    double[][] dMg_p_stop;    //排气参数存储
    double dMg_sum=0;         //清管器推气量
    double[][] maxThree;         //最长的三个积气段数据存储

    double[] Hf_f;         //各段的分层流的摩阻水力损失
    double[] Hf_j;          //各段的局部摩阻损失
    double[] Hfk_f;         //各段分层流的摩阻水力损失
    double[] Hfk_f_oil;         //各段分层流的摩阻水力损失,油段
    double[] Hfk_f1;         //各段分层流的摩阻水力损失，不包含位能
    double[] Hfk_f1_oil;         //各段分层流的摩阻水力损失，不包含位能,油段
    double[] Hgbk;         //各段分层流的摩阻水力损失
    double[] Hf_b;         //气泡流的摩阻水力损失
    double[][] HFB;         //
    double[][] HFB1;         //
    double[][] HFBU;         //
    double[][] HFBU1;         //
    double[][] HFF;         //
    double[][] HFF1;         //
    double[] Hfk_b;         //各段气泡流的摩阻水力损失
    double[] Hfk_lbz_U;         //满管上坡的摩阻水力损失
    double[] Hfk_lbz_D;         //满管下坡的摩阻水力损失
    double[] Hfk_b_oil;         //各段气泡流的摩阻水力损失,油段
    double[] Hfk_b1;         //各段气泡流的摩阻水力损失，不包含位能
    double[] Hfk_b1_oil;         //各段气泡流的摩阻水力损失，不包含位能,油段
    double[] Hfk_bU;         //各段气泡流的摩阻水力损失
    double[] Hfk_bU_oil;         //各段气泡流的摩阻水力损失，油段
    double[] Hfk_bU1;         //各段气泡流的摩阻水力损失，不包含位能
    double[] Hfk_bU1_oil;         //各段气泡流的摩阻水力损失，不包含位能,油段
    double[] Hfk_dU;         //各段段塞流的摩阻水力损失
    double[] Hf;           //全线总摩阻水力损失
    double[] Hfk;           //各段总摩阻水力损失
    double[] Hfk_bb;           //各段总摩阻水力损失
    double[] Hfk_ff;           //各段总摩阻水力损失
    double[] Hfk_jj;

    double[][] dPL;             //全线压力，pressure
    double[][] dHL;             //全线水头，Head
    double[][] dHL_new;             //全线水头，Head
    double checkP=0;             //监测超压的标记

    double[][] lg_f;
    double[][] lp_b;
    double[][] lp_bU;

    double[] l0;         //积气段尾部长度
    int[] flag00;        //水力排气结束的标志
    int[] flag01;        //清管器导致水力紊乱的标志
    double wL=0;  //全线泵站总扬程
    int num;        //
    int []pigNum;        //清管器时间计数



    int pigflag1 = 0;        //清管器1标记
    int Vflag1 = 0;        //排气阀1标记
    int []pigflag2;        //清管器1停留的标记
    int []pigTflag;        //清管器1停留的标记
    int flag0,flag1,flag11;        //
    double ipj;        //

    int startPumpFlag=0;        //启泵的标志
    int startPumpFlag111=0;        //启泵的标志
    int stopPumpFlag=0;        //停泵的标志
    int [][]startPumpFlag1;        //调整变频泵的标志
    double [][]pumpRev;        //调整变频泵的标志,起始变频泵转速
    double [][]a;
    double [][]Hss;
    double []Hpump;
    double []stationLLL;
    int times=0;
    int ok=0;
    double []Hd;
    //清管器相关参数
    double [][]pigV;//清管器速度
    double [][]pigL;//清管器位置
    double [][]pigZ;//清管器位置对应高程
    double []Mg;//积气总质量随时间变化，时步为2.5min
    double [][]allLine;//全线参数储存输出
    double [][]allLineFP;//全线流型随时间变化曲线
    double [][]allLineDP;//全线摩阻压降
    double [][]allLineQ;//里程和流量对应的数组，用于分输
    double [][]allLineLbz;//全线反算列宾宗系数b

    double [][] allLineStaticP;//全线静压
//    double []staV={164.2,251.2,280.2,300.2,328.2,378.2};   //安保排气点位置
//    double []staV={175.2,225.2,262.2,315.2,370.2,478.2};   //中缅排气点位置
//    double []staV;   //中缅排气点位置

    double []Kv={1.75,3.83,8.38,18.32,40.1,87.62,192.6,419,916.2,2003.4,2000000};   //对应开度，0-100%
    //double [] stationListL;//站点列表里程
    //double [] stationListZ;//站点列表高程

    List stationListL = new ArrayList();
    List stationListZ = new ArrayList();
    List allLineLbzBs;
    List staV  = new ArrayList();
    List allQ;//流量随时间变化曲线
    List pumpSta;//泵站列表
    int p1=0;//,水头过泵站的过站记录
    int p=0;//,水头过泵站的过站记录

    //泵相关
    double Hs;//随启泵条件变化的出站压力，压降计算处使用




    double [][]lg_fff;//300s一步的时步分层流长度数据
    double []waterL;//300s一步的水头位置
    double []gasRa;//气体比例
    double []oilL;//300s一步的油头位置
    double []L;//地形数据，0.5km一段

    public void setArr(){

        this.k = (int)(T*2*5*3600/300)+2202;      //300步一输出的存储序号
        this.kt = (int) Math.rint(T*2* 3600 / deltaT);            //模拟总时步的计算

        this.pigV = new double[10][getK()];
        this.pigNum = new int[10];
        this.pigTflag = new int[10];
        this.pigflag2 = new int[10];
        this.dMg_p = new double[15][(int)T*12*2+2+1];
        this.dMg_pk = new double[15][k];
        this.dMg_p_stop = new double[15][(int)T*12*2+2+1];
        this.maxThree = new double[3][2];
        this.flag01 = new int[getI()];


        this.pigL = new double[10][getK()];
        this.Mg = new double[getK()];
        this.gasRa = new double[getK()];
        this.pigZ = new double[10][getK()];
        this.allLine = new double[5][getK()];
        this.allLineFP = new double[(int)T*12*2+3000+2+1][1300];
        this.allLineStaticP = new double[(int)T*12*2+2+1][1300];
        this.allLineLbz = new double[(int)T*12*2+2+1][1300];
        this.allLineDP = new double[(int)T*12*2+2+1][1300];
        this.allLineLbzBs = new ArrayList<List<Double>>();
        this.staV = new ArrayList<List<Double>>();
        this.allQ = new ArrayList<List<Double>>();
        this.allLineQ = new double[10][2];//一个分输点，对应一个新流量，此处为小时流量
        this.pumpSta = new ArrayList<List<Double>>();

        this.Hss=new double[getK()][100];
        this.Hs=80*9.81*1000;//初始值为首站进站压力
        this.a= new double[100][3];
        this.Hpump=new double[100];
        this.Hd=new double[100];
        this.stationLLL=new double[100];





        this.Y= new double[6][getK() + 1];          //矩阵计算历史数据存储
        this.Pg_his = new double[getI()][getK()];     //气体压力  历史数据
        this.Deng_his = new double[getI()][getK()];     //气体密度  历史数据
        this.lg_his = new double[getI()][getK()];     //气段长度  历史数据
        this.h2_his = new double[getI()][getK()];     //上坡液位  历史数据
        this.h1_his = new double[getI()][getK()];     //上坡液位  历史数据

        this.dMg_out_his = new double[i][k];      //净流出  历史数据
        this.dMg_in_his = new double[i][k];      //净流入气体质量  历史数据
        this.backPressure_his = new double[i][k];       //背压 历史数据
        this.M_his = new double[i][k];       //气段质量，历史数据

        this.hgs_k = new double[i];       //气段尾部截面含气率1

        this.hgw_k = new double[i];       //气段尾部截面含气率2
        this.hl_k = new double[i];       //气段截面持液率

        this.MM = new double[i];      //气段质量；

        //有内存限制，不能太长

        this.delta = new double[i];     //每个下坡段的液相截面积
        this.Sl = new double[i];        //每个下坡段的液相湿周
        this.Al = new double[i];        //每个下坡段的液相湿周
        this.vll = new double[i];       //液相流速

        this.t2 = new double[i];     //粗算气相积累时间，，也就是液相从高点到低点的时间
        this.gasRatio = new double[i];//初始截面含气率计算
        this.V0 = new double[i];     //初始积气段的气体体积
        this.M0 = new double[i];     //初始积气段的气体质量，密度取1.205kg/m3
        this.h1k = new double[i];    //下坡段初始液位高度
        this.h2k = new double[i];    //上坡段初始液位高度
        this.Pgk = new double[i];    //初始气相压力，大气压
        this.Hgk = new double[i];         //初始截面含气率
        this.uk = new double[i];          //水头在上坡段的前进速度
        this.lgk = new double[i];         //初始气段长度，即下坡段长度
        this.dMg_in = new double[i];     //本段净流入气体质量
        this.dMg_out = new double[i];    //净流出气体质量
        this.MGk = new double[i];      //下坡段的气体质量，，??排气后
        this.Dengk = new double[i];      //下坡段的气体质量，，??排气后

        this.Qh1k = new double[i];    //下坡段初始液位高度
        this.Qh2k = new double[i];    //上坡段初始液位高度
        this.QPgk = new double[i];    //初始气相压力，大气压
        this.QHgk = new double[i];         //初始截面含气率
        this.Quk = new double[i];          //水头在上坡段的前进速度
        this.Qlgk = new double[i];         //初始气段长度，即下坡段长度
        this.QdMg_in = new double[i];     //本段净流入气体质量
        this.QdMg_out = new double[i];    //净流出气体质量
        this.QMGk = new double[i];      //下坡段的气体质量，，??排气后
        this.QDengk = new double[i];      //下坡段的气体质量，，??排气后


        this.slopeD = new double[i][1];      //下坡段的气体质量，，??排气后
        this.line_l = new double[i][4];      //里程
        this.line_d = new double[i][4];      //高程
        this.line_ddd = new double[200][4];      //总高程
        this.line_lll = new double[200][4];      //总里程
        this.slopeU = new double[i][1];      //下坡段的气体质量，，??排气后

        this.waterHeadLocation = new double[k];     //水头位置,在不同时刻水头所到达的位置，用里程表示，单位m
        this.oilHeadLocation = new double[k];     //水头位置,在不同时刻水头所到达的位置，用里程表示，单位m
        this.lf = new double[k];     //分层流长度
        this.lfk = new double[k];     //各段的分层流长度
        this.lp = new double[k];     //气泡流长度
        this.lpk = new double[k];     //各段的气泡流长度

        this.Time = new double[k];                   //统筹时步与时间，单位，小时，hr
        this.startPressure = new double[k];          //起点压头，随水头位置变化而变化
        this.flowPressure = new double[k][1];          //全过程，全地形的动压
        this.ssr = new int[i][2];
        this.startPumpFlag1 = new int[100][100];
        this.pumpRev = new double[100][100];

        this.backPressure = new double[i + 1]; //各管段的背压
        this.pre_back = new double[i + 1];     //破碎前的背压

        this.f_i = new double[i];         //相间摩阻系数
        this.f_l = new double[i];         //液壁摩阻系数
        this.Hf_f = new double[k];         //分层流的摩阻水力损失
        this.HFB = new double[i][k];         //分层流的摩阻水力损失
        this.HFB1 = new double[i][k];         //分层流的摩阻水力损失
        this.HFBU = new double[i][k];         //分层流的摩阻水力损失
        this.HFBU1 = new double[i][k];         //分层流的摩阻水力损失
        this.HFF = new double[i][k];         //分层流的摩阻水力损失
        this.HFF1 = new double[i][k];         //分层流的摩阻水力损失
        this.Hfk_f = new double[i];         //各段分层流的摩阻水力损失
        this.Hfk_lbz_D = new double[i];         //各段的摩阻水力损失
        this.Hfk_lbz_U = new double[i];         //各段的摩阻水力损失
        this.Hfk_f_oil = new double[i];         //各段分层流的摩阻水力损失
        this.Hfk_f1_oil = new double[i];         //各段分层流的摩阻水力损失
        this.Hfk_f1 = new double[i];         //各段分层流的摩阻水力损失
        this.Hgbk = new double[i];         //各段分层流的摩阻水力损失
        this.Hf_b = new double[k];         //气泡流的摩阻水力损失
        this.Hfk_bU = new double[i];         //
        this.Hfk_bU1_oil = new double[i];         //
        this.Hfk_bU_oil = new double[i];         //
        this.Hfk_bU1 = new double[i];         //
        this.Hfk_dU = new double[i];         //
        this.Hfk_b = new double[i];         //各段气泡流的摩阻水力损失
        this.Hfk_b1_oil = new double[i];         //各段气泡流的摩阻水力损失
        this.Hfk_b_oil = new double[i];         //各段气泡流的摩阻水力损失
        this.Hfk_b1 = new double[i];         //各段气泡流的摩阻水力损失
        this.Hf = new double[k];           //全线总摩阻水力损失
        this.Hfk = new double[i];           //各段总摩阻水力损失

        this.Hfk_bb = new double[i];           //各段总摩阻水力损失
        this.Hfk_ff = new double[i];           //各段总摩阻水力损失
        this.Hfk_jj = new double[i];
        this.Hf_j = new double[k];              //局部阻力

        this.dPL=new double[(int)T*12*2+2+1][1300];       //全局压力
        this.dHL=new double[(int)T*12*2+2+1][1300];       //全局压力
        this.dHL_new=new double[(int)T*12*2+2+1][1300];       //全局压力
        this.lg_f = new double[i][k];   //各段分层流实时长度
        this.lp_b = new double[i][k];   //气泡流和气团流实时长度，下坡段部分
        this.lp_bU = new double[i][k];  //气泡流和气团流实时长度，上坡段部分
        this.lg_fff = new double[i][(int)T*2*12+2];//3000s一步的时步分层流长度数据
        this.waterL = new double[(int)T*2*12+3000+2];//
        this.oilL = new double[(int)T*2*12+3000+2];//

        this.l0 = new double[i + 1];      //积气段尾部长度
        this.flag00 = new int[i];        //水力排气结束的标志
    }


    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getKk() {
        return kk;
    }

    public void setKk(int kk) {
        this.kk = kk;
    }

    public int getIi() {
        return ii;
    }

    public void setIi(int ii) {
        this.ii = ii;
    }

    public int getKt() {
        return kt;
    }

    public void setKt(int kt) {
        this.kt = kt;
    }

    public double[][] getY() {
        return Y;
    }

    public void setY(double[][] y) {
        Y = y;
    }

    public double[][] getPg_his() {
        return Pg_his;
    }

    public void setPg_his(double[][] pg_his) {
        Pg_his = pg_his;
    }

    public double[][] getDeng_his() {
        return Deng_his;
    }

    public void setDeng_his(double[][] deng_his) {
        Deng_his = deng_his;
    }

    public double[][] getLg_his() {
        return lg_his;
    }

    public void setLg_his(double[][] lg_his) {
        this.lg_his = lg_his;
    }

    public double[][] getH2_his() {
        return h2_his;
    }

    public void setH2_his(double[][] h2_his) {
        this.h2_his = h2_his;
    }

    public double[][] getdMg_out_his() {
        return dMg_out_his;
    }

    public void setdMg_out_his(double[][] dMg_out_his) {
        this.dMg_out_his = dMg_out_his;
    }

    public double[][] getdMg_in_his() {
        return dMg_in_his;
    }

    public void setdMg_in_his(double[][] dMg_in_his) {
        this.dMg_in_his = dMg_in_his;
    }

    public double[][] getBackPressure_his() {
        return backPressure_his;
    }

    public void setBackPressure_his(double[][] backPressure_his) {
        this.backPressure_his = backPressure_his;
    }

    public double[][] getM_his() {
        return M_his;
    }

    public void setM_his(double[][] m_his) {
        M_his = m_his;
    }

    public double[] getHgs_k() {
        return hgs_k;
    }

    public void setHgs_k(double[] hgs_k) {
        this.hgs_k = hgs_k;
    }

    public double[] getHgw_k() {
        return hgw_k;
    }

    public void setHgw_k(double[] hgw_k) {
        this.hgw_k = hgw_k;
    }

    public double[] getHl_k() {
        return hl_k;
    }

    public void setHl_k(double[] hl_k) {
        this.hl_k = hl_k;
    }

    public double[] getMM() {
        return MM;
    }

    public void setMM(double[] MM) {
        this.MM = MM;
    }

    public double[] getDelta() {
        return delta;
    }

    public void setDelta(double[] delta) {
        this.delta = delta;
    }

    public double[] getSl() {
        return Sl;
    }

    public void setSl(double[] sl) {
        Sl = sl;
    }

    public double[] getAl() {
        return Al;
    }

    public void setAl(double[] al) {
        Al = al;
    }

    public double[] getVll() {
        return vll;
    }

    public void setVll(double[] vll) {
        this.vll = vll;
    }

    public double[] getT2() {
        return t2;
    }

    public void setT2(double[] t2) {
        this.t2 = t2;
    }

    public double[] getGasRatio() {
        return gasRatio;
    }

    public void setGasRatio(double[] gasRatio) {
        this.gasRatio = gasRatio;
    }

    public double[] getV0() {
        return V0;
    }

    public void setV0(double[] v0) {
        V0 = v0;
    }

    public double[] getM0() {
        return M0;
    }

    public void setM0(double[] m0) {
        M0 = m0;
    }

    public double[] getH1k() {
        return h1k;
    }

    public void setH1k(double[] h1k) {
        this.h1k = h1k;
    }

    public double[] getH2k() {
        return h2k;
    }

    public void setH2k(double[] h2k) {
        this.h2k = h2k;
    }

    public double[] getPgk() {
        return Pgk;
    }

    public void setPgk(double[] pgk) {
        Pgk = pgk;
    }

    public double[] getHgk() {
        return Hgk;
    }

    public void setHgk(double[] hgk) {
        Hgk = hgk;
    }

    public double[] getUk() {
        return uk;
    }

    public void setUk(double[] uk) {
        this.uk = uk;
    }

    public double[] getLgk() {
        return lgk;
    }

    public void setLgk(double[] lgk) {
        this.lgk = lgk;
    }

    public double[] getdMg_in() {
        return dMg_in;
    }

    public void setdMg_in(double[] dMg_in) {
        this.dMg_in = dMg_in;
    }

    public double[] getdMg_out() {
        return dMg_out;
    }

    public void setdMg_out(double[] dMg_out) {
        this.dMg_out = dMg_out;
    }

    public double[] getMGk() {
        return MGk;
    }

    public void setMGk(double[] MGk) {
        this.MGk = MGk;
    }

    public double[] getDengk() {
        return Dengk;
    }

    public void setDengk(double[] dengk) {
        Dengk = dengk;
    }

    public double[][] getSlopeD() {
        return slopeD;
    }

    public void setSlopeD(double[][] slopeD) {
        this.slopeD = slopeD;
    }

    public double[][] getLine_l() {
        return line_l;
    }

    public void setLine_l(double[][] line_l) {
        this.line_l = line_l;
    }

    public double[][] getLine_d() {
        return line_d;
    }

    public void setLine_d(double[][] line_d) {
        this.line_d = line_d;
    }

    public double[][] getSlopeU() {
        return slopeU;
    }

    public void setSlopeU(double[][] slopeU) {
        this.slopeU = slopeU;
    }

    public double[] getWaterHeadLocation() {
        return waterHeadLocation;
    }

    public void setWaterHeadLocation(double[] waterHeadLocation) {
        this.waterHeadLocation = waterHeadLocation;
    }

    public double[] getLf() {
        return lf;
    }

    public void setLf(double[] lf) {
        this.lf = lf;
    }

    public double[] getLfk() {
        return lfk;
    }

    public void setLfk(double[] lfk) {
        this.lfk = lfk;
    }

    public double[] getLp() {
        return lp;
    }

    public void setLp(double[] lp) {
        this.lp = lp;
    }

    public double[] getLpk() {
        return lpk;
    }

    public void setLpk(double[] lpk) {
        this.lpk = lpk;
    }

    public double[] getTime() {
        return Time;
    }

    public void setTime(double[] time) {
        Time = time;
    }

    public double[] getStartPressure() {
        return startPressure;
    }

    public void setStartPressure(double[] startPressure) {
        this.startPressure = startPressure;
    }

    public double[][] getFlowPressure() {
        return flowPressure;
    }

    public void setFlowPressure(double[][] flowPressure) {
        this.flowPressure = flowPressure;
    }

    public int[][] getSsr() {
        return ssr;
    }

    public void setSsr(int[][] ssr) {
        this.ssr = ssr;
    }

    public double[] getBackPressure() {
        return backPressure;
    }

    public void setBackPressure(double[] backPressure) {
        this.backPressure = backPressure;
    }

    public double[] getPre_back() {
        return pre_back;
    }

    public void setPre_back(double[] pre_back) {
        this.pre_back = pre_back;
    }

    public double[] getF_i() {
        return f_i;
    }

    public void setF_i(double[] f_i) {
        this.f_i = f_i;
    }

    public double[] getF_l() {
        return f_l;
    }

    public void setF_l(double[] f_l) {
        this.f_l = f_l;
    }

    public double[] getHf_f() {
        return Hf_f;
    }

    public void setHf_f(double[] hf_f) {
        Hf_f = hf_f;
    }

    public double[] getHfk_f() {
        return Hfk_f;
    }

    public void setHfk_f(double[] hfk_f) {
        Hfk_f = hfk_f;
    }

    public double[] getHgbk() {
        return Hgbk;
    }

    public void setHgbk(double[] hgbk) {
        Hgbk = hgbk;
    }

    public double[] getHf_b() {
        return Hf_b;
    }

    public void setHf_b(double[] hf_b) {
        Hf_b = hf_b;
    }

    public double[] getHfk_b() {
        return Hfk_b;
    }

    public void setHfk_b(double[] hfk_b) {
        Hfk_b = hfk_b;
    }

    public double[] getHf() {
        return Hf;
    }

    public void setHf(double[] hf) {
        Hf = hf;
    }

    public double[] getHfk() {
        return Hfk;
    }

    public void setHfk(double[] hfk) {
        Hfk = hfk;
    }

    public double[] getL0() {
        return l0;
    }

    public void setL0(double[] l0) {
        this.l0 = l0;
    }

    public int[] getFlag00() {
        return flag00;
    }

    public void setFlag00(int[] flag00) {
        this.flag00 = flag00;
    }
}
