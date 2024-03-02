package com.project.accomatch.Model;

import java.util.ArrayList;
import java.util.Date;

public interface HouseSeekerModelBuilder {
    HouseSeekerModelBuilder housekeeperApplicationId(int housekeeper_application_Id);
    HouseSeekerModelBuilder name(String name);

    HouseSeekerModelBuilder otherPreferences(String other_preferences);

    HouseSeekerModelBuilder createdAt(Date created_At);

    HouseSeekerModelBuilder updatedAt(Date updated_At);

    HouseSeekerModelBuilder foodPreferences(ArrayList<String> food_preferences);

    HouseSeekerModelBuilder genderPreferences(ArrayList<String> gender_preferences);

    HouseSeekerModel build();
}