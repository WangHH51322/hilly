package cn.edu.cup.hilly.dataSource.utils.excelToJson;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class StrToJson {

    /**
     filePath:Json文件保存的绝对路径
     fileName:Json文件的文件名,如 test.json
     fileContent:使用FastJson将对象或数组转换为后的String字符串
     */
    public static void work(String filePath,String fileName,String fileContent) {
        File file=new File(filePath);
        if (!file.exists()) { //所以在这里必须提前创建好文件夹
            file.mkdirs();
        }
        Path ConfPath = Paths.get(filePath, fileName);
        try {
            if (!Files.exists(ConfPath)){
                Files.createFile(ConfPath);
            }
        } catch (Exception e) {
            System.out.println("创建配置文件失败");
        }
        try {
            Files.write(ConfPath, fileContent.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
        } catch (Exception ex) {
            System.out.println("写入配置文件失败");
        }
    }

}
