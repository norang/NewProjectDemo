package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.File;
import com.example.demo.service.FileService;

@Controller
public class FileController{
	
	@Autowired
	private FileService fileService;
    
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
    public String registration(Model model) {
		
		model.addAttribute("fileList", fileService.findAll());

        return "uploadFile";
    }
	
	
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile file, Model model) throws IOException {
    	
    	String[] fileType = file.getContentType().split("/");
    	long maxFileSize = 1024*90; // KB
    	
    	if (!fileType[0].equalsIgnoreCase("image")) {
    		model.addAttribute("error", "Please select a photo!");
    	}else if (maxFileSize < file.getSize()) {
    		model.addAttribute("error", "Please select a photo in 90 KB!");
    	}else {
    		File fileInput = new File(file.getOriginalFilename(), fileType[1], file.getBytes());
        	fileService.save(fileInput);
        	model.addAttribute("message", "Image uploaded successfully.");
    	}
    	
    	model.addAttribute("fileList", fileService.findAll());

        return "uploadFile";
    }
    
    @RequestMapping(value="/uploadFile/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> view(@PathVariable("id") String id){
    	
    	byte[] image = fileService.findById(id).get().getPic();
    	
    	return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }
    
    
    
}
