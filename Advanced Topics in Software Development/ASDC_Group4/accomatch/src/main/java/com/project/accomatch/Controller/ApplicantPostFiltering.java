package com.project.accomatch.Controller;

import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Service.ApplicantPostFilterService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/applicant")
public class ApplicantPostFiltering {

    Logger logger = LoggerClass.getLogger();
    @Autowired
    private ApplicantPostFilterService applicantPostFilterService;

    /**
     * Endpoint to filter posts based on applicant preferences.
     *
     * @param jsonMap A map containing applicant preferences as key-value pairs.
     *                The keys in the map are "gender_pref", "food_pref", "age", and "room_type".
     *                The "age" and "room_type" values should be single strings.
     * @return A list of Posts that match the given applicant preferences.
     * @author Yogish Honnadevipura Gopalakrishna
     */
    @PostMapping("/posts/filter")
    public List<Posts> filter(@RequestBody Map<String, String> jsonMap){
        logger.info("filter controller active");
        String[] gender_pref = jsonMap.get("gender_pref").split(",");
        String[] food_pref = jsonMap.get("food_pref").split(",");
        String age = jsonMap.get("age");
        String room_type = jsonMap.get("room_type");
        return applicantPostFilterService.filterPost(gender_pref, food_pref, age, room_type);
    }
}
