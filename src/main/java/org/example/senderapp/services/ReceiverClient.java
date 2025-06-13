package org.example.senderapp.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "receiverClient", url = "http://localhost:1214")
public interface ReceiverClient {

    @PostMapping(value = "/api/receive", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String sendFile(@RequestPart("file") MultipartFile file);
}


