package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    
	
	@PreAuthorize("hasRole('ROLE_CON')")
	@RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
    public String uploadFile(Model model, String updateSuccess, String uploadSuccess) {
		

		if (updateSuccess != null)
			model.addAttribute("message", "Image updated successfully.");
		else if (uploadSuccess != null)
			model.addAttribute("message", "Image uploaded successfully.");
		
		model.addAttribute("uploadForm", new File());
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
    	}
    	
        return "redirect:/uploadFile?uploadSuccess";
    }
    
    @RequestMapping(value = "/uploadFile/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute("fileForm") File fileForm, Model model) throws IOException {
    	fileService.save(fileForm);
        return "redirect:/uploadFile?updateSuccess";
    }
    
    
    @RequestMapping(value="/uploadFile/view/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> view(@PathVariable("id") String id){
    	
    	byte[] image = fileService.findById(id).get().getPic();
    	
    	return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }
    
    @RequestMapping(value="/uploadFile/viewDetail/{id}")
    @ResponseBody
	public File  view(@PathVariable("id") String id, Model model){
	  	
    	Optional<File> file = fileService.findById(id);
        
        return file.get();
	}
    
    @RequestMapping(value="/uploadFile/delete/{id}")
    public String delete(@PathVariable("id") String id, Model model){
    	model.addAttribute("message", "Image deleted successfully.");
    	fileService.delete(Long.parseLong(id));
    	return "redirect:/uploadFile";
    }
    
    
    @ModelAttribute("fileList")
    public List<File> getFileList() {
        return fileService.findAll();
    }

}
