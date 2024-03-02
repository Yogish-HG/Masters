package com.project.accomatch.Service.Implementation;

import com.project.accomatch.Model.Posts;
import com.project.accomatch.Repository.AdminRepositoryInterface;
import com.project.accomatch.Service.AdminInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminImplementation implements AdminInterface {

    @Autowired
    AdminRepositoryInterface adminRepository;

    /**
     * Verifies a single advertisement post by updating its verification status in the database.
     * @author Yogish Honnadevipura Gopalakrishna
     * @param posts The Posts object representing the advertisement post to be verified.
     * @return A string message indicating the status of the verification process ("Success" or "Error").
     */
    @Override
    public String VerifyOneAd(Posts posts) {
        return adminRepository.verifyOneAd(posts);
    }

    /**
     * Verifies all advertisement posts by updating their verification status in the database.
     * @author Yogish Honnadevipura Gopalakrishna
     * @param posts The Posts object representing the advertisement posts to be verified.
     * @return A string message indicating the status of the verification process ("Success" or "Error").
     */
    @Override
    public String VerifyAllAd(Posts posts) {
        return adminRepository.verifyAllAd(posts);
    }

}
