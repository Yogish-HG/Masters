package com.project.accomatch.Service;

import com.project.accomatch.Model.LeaseHolderModel;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface LeaseHolderService {
    String createAD(Map<String,Object> requestBody);
}
