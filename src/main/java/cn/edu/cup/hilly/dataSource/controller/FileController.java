package cn.edu.cup.hilly.dataSource.controller;

import cn.edu.cup.hilly.calculate.hilly.large.ExcelData;
import cn.edu.cup.hilly.dataSource.model.ExcelFile;
import cn.edu.cup.hilly.dataSource.service.FileService;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author CodeChap
 * @date 2021-05-21 16:24
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    @PostMapping("/upload")
    @ResponseBody
    public RespBean fileUpload(@RequestParam("file")MultipartFile file, HttpServletRequest req, @RequestParam("id") String id) {
        if (file.isEmpty()) {
            return RespBean.error("文件为空");
        }
        //1.准备文件夹
        String format = sdf.format(new Date());
        String realPath = req.getServletContext().getRealPath("/") + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //2.准备文件名
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        //3.存储
        try {
            File fileAfter = new File(folder, newName);
            file.transferTo(fileAfter);
//            //4.组装 url
//            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + format + newName;
//            return url;
            //数据转换
            double[][] locations = ExcelData.Graphic(fileAfter, "location");
            int inum = ExcelData.inum;
            ExcelFile file1 = fileService.save(locations, id, inum);
            return RespBean.ok("上传成功",file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RespBean.error("请检查sheet名称");
    }
}
