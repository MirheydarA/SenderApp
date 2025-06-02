package org.example.senderapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.senderapp.ReceiverClient;

@Controller
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private ReceiverClient receiverClient;

    @GetMapping("/upload")
    public String showUploadForm() {
        return "uploadFile";
    }

    @PostMapping("/send")
    public String fileUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute( "message","File is missing!");
            return "/uploadFile";
        }

        String response = receiverClient.sendFile(file);
        model.addAttribute("message", response);
        System.out.println(response);
        return "uploadFile";
    }
}



