package com.pro.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pro.ProductDispayApplication;
import com.pro.entity.UploadFile;
import com.pro.service.UploadFileService;

@RestController
public class FileUploadController {
	
	private static String path=ProductDispayApplication.class.getResource("").getPath();
    private static String TEMP_PATH = path.substring(1,path.indexOf("classes"))+"tempFile/";

    @Autowired
    private UploadFileService uploadFileservice;
 
    @RequestMapping("/upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) {

    	Map<String, Object> resultMap=new HashMap<String, Object>();
    	String msg="上传失败，请选择文件";
        if (file.isEmpty()) {
        	resultMap.put("msg", msg);
            return resultMap;
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        String filePath = TEMP_PATH;
        File dirPath=new File(filePath);//判断是否有这个文件夹
        if(!dirPath.exists()){//没有创建
        	dirPath.mkdir();
		}
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            System.out.println(file);
            System.out.println(file.getSize());
            System.out.println();
            msg="上传成功";
            resultMap.put("imagePath",fileName);
            resultMap.put("msg",msg);
            
            //保存file
            UploadFile imgFile=new UploadFile();
            imgFile.setFileName(fileName);
            imgFile.setFileUrl(filePath);
            imgFile.setFileSize(file.getSize());
            imgFile.setFileType(file.getContentType());
            uploadFileservice.add(imgFile);
            resultMap.put("imgFileId",imgFile.getId());
            return resultMap;
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return resultMap;
    }
}
