package com.project.controller.business;


import com.project.service.business.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @DeleteMapping("/db-reset")
    public ResponseEntity<String> deleteAllAdverts() {
        String message=settingsService.resetDatabase();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
