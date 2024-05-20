package com.example.testsenderemail.controller;

import com.example.testsenderemail.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@Slf4j
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping(value = "/simple-email/{user-email}")
    public @ResponseBody ResponseEntity<String> sendSimpleEmail(@PathVariable("user-email") String email) {

        log.info("EMAIL - {}", email);
        try {
            emailService.sendSimpleEmail(email, "Welcome", "TEST MAIL - Пландер Пландер ");
        } catch (MailException mailException) {
            log.error("Error while sending out email - {}", mailException.getMessage());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }

    @GetMapping(value = "/simple-order-email/{user-email}")
    public @ResponseBody ResponseEntity<String> sendEmailAttachment(@PathVariable("user-email") String email) {

        try {
            emailService.sendEmailWithAttachment(email, "Order Confirmation", "Thanks for your recent order",
                    "classpath:purchase_order.pdf");
        } catch (MessagingException | FileNotFoundException mailException) {
            log.error("Error while sending out email - {}", mailException.getMessage());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Please check your inbox for order confirmation", HttpStatus.OK);
    }
}
