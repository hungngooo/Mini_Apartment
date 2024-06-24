package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Services.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class EmailTestingController {
    private final EmailService emailService;

    public EmailTestingController(EmailService emailService) {
        this.emailService = emailService;
    }
    @GetMapping("/send")
    public String sendMailTest() {
        emailService.sendMail("hungngongocfpt@gmail.com","Email Testing from SpringBoot","This is a test Email");
        return "Email test sent successfully";
    }
}
