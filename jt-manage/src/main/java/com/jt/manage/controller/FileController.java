package com.jt.manage.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileService;

@Controller
public class FileController {
	@Autowired
	private FileService fileService;
	@RequestMapping("/file")
	public String file(MultipartFile pic) throws Exception{
		File file = new File("D:/Java/file");
		if(!file.exists()){
			file.mkdirs();
		}
		String fileName = pic.getOriginalFilename();
		pic.transferTo(new File("D:/Java/file/"+fileName));
		return "redirect:/file.jsp";
	}
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult fileUpload(MultipartFile uploadFile){
		return fileService.fileUpload(uploadFile);
	}
	
}
