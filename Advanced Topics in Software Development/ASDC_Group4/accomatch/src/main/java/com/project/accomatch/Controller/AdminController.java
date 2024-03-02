package com.project.accomatch.Controller;

import com.project.accomatch.Exception.InvalidPostStatusException;
import com.project.accomatch.Exception.PostNotFoundException;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Service.AdminInterface;
import com.project.accomatch.Service.DashboardInterface;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {

    Logger logger = LoggerClass.getLogger();

    @Autowired
    AdminInterface adminInterface;
    @Autowired
    private DashboardInterface dashboardService;

    /**
     * Verifies a single ad
     * @author Yogish Honnadevipura Gopalakrishna
     * @return Success if verified or else Error
     */
    @PostMapping("/verify/one")
    public String verifySingleAd(@RequestBody Map<String, String> posts){
        try{
            if(posts == null){
                throw new PostNotFoundException("Posts object cannot be null");
            }
            Posts post=new Posts();
            post.setVerified(Integer.parseInt(posts.get("isVerified")));
            post.setLeaseholderApplicationId(Integer.parseInt(posts.get("leaseholderApplicationId")));
            logger.info("single Ad verification controller active");
            return adminInterface.VerifyOneAd(post);
        }catch (PostNotFoundException p){
            return p.getMessage();
        }
    }

    /**
     * Verifies all unverified ads
     * @author Yogish Honnadevipura Gopalakrishna
     * @return Success if verified all or else Error
     */
    @PostMapping("/verify/all")
    public String verifyAllAd(@RequestBody Posts posts){
        try{
            if(posts == null){
                throw new PostNotFoundException("Posts cannot be null");
            }
            logger.info("All Ad verification controller active");
            return adminInterface.VerifyAllAd(posts);
        }catch (PostNotFoundException p){
            return p.getMessage();
        }
    }

    /**
     * Retrieves the list of posts for Admin.
     * @return The list of posts.
     */
    @GetMapping("/get/list/post")
    public List<Posts> getListOfPosts()  {
        logger.info("Getting the list of posts for Admin.");
        return dashboardService.getListOfPosts();
    }

    /**
     * Retrieves the list of posts for Admin based on status.
     * @param map The request body containing the status.
     * @return The list of posts.
     */
    @PostMapping("/get/list/postbystatus")
    public List<Posts> getListOfPostsByStatus(@RequestBody HashMap<String, String> map) {
        int status;
        try {
            status = Integer.parseInt(map.get("status"));
        } catch (NumberFormatException e) {
            logger.error("Invalid post status value: {}", e.getMessage());
            throw new InvalidPostStatusException("Invalid post status value");
        }

        logger.info("Getting the list of posts for Admin based on status: {}", status);
        return dashboardService.getListOfPostsByStatus(status);
    }
}
