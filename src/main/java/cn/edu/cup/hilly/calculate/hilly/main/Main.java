package cn.edu.cup.hilly.calculate.hilly.main;

import cn.edu.cup.base.CommonProvider;
import cn.edu.cup.hilly.calculate.hilly.large.Project;

public class Main {
    public static void main(String[] args) {

        System.out.println("通过简单管道计算演示通用输入包的使用...");

        Main main = new Main();
        main.run();
    }

    private void run() {

        // 获取当前文件夹位置
        String base = System.getProperty("user.dir") + "/config";
        // 创建数据提供者
        CommonProvider commonProvider = new CommonProvider(base);
        // 加载数据
        commonProvider.loadFromFile();

        // 显示输出当前的数据。初次运行这里应该显示不出来任何内容。
        System.out.println(commonProvider.getDataMap().toString());

        // 实例化工程对象
        Project project;
        project = new Project();

        // 关键的语句：给工程对象的各个属性赋值。
        commonProvider.startDataRequirementProcess(project);

        // 开始的时候是没有数据的，这里输出一个提示。
        if (commonProvider.isNeedUpdate()) {
            System.out.printf("请更新基础数据文件 %s ...\n", commonProvider.getDataFileName());
        } else {
            System.out.println("成功获取数据，执行计算任务:");
            System.out.println("实际进行工程计算....");
            project.run();
        }

        // 如果需要更新，在这里生成具体的数据文件。
        commonProvider.saveToFile();
    }


}
