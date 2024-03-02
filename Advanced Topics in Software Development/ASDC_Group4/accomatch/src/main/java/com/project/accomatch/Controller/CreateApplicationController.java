package com.project.accomatch.Controller;

import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Service.Implementation.CreateApplicationFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/application")
public class CreateApplicationController {
    @Autowired
    private CreateApplicationFactory createApplicationService;
    Logger logger = LoggerClass.getLogger();

    /**
     * Creating Applications
     * @author Bhargav Kanodiya / Ramandeep kaur
     * @param requestBody-- body of parameters to create application
     * @return -- Status of creation
     */
    @PostMapping("/create")
    public String createAD(@RequestBody Map<String, Object> requestBody) {
        try {
            return createApplicationService.createAD(requestBody);
        } catch (Exception e) {
            logger.error("An error occurred while creating the application: {}", e.getMessage());
            return e.getMessage();
        }
    }

}
