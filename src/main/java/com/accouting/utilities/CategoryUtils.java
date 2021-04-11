package com.accouting.utilities;


import com.accouting.model.Category;

import java.util.List;

public class CategoryUtils {
    public static Category accessLastCategory(List<Category> categories) {
        return categories.get(categories.size()-1);
    }

    public static int getSelectedCategoryId(Object selectedItem) {

        if (selectedItem != null)
        {
            String[] categoryData = selectedItem.toString().split(": ");

            return Integer.parseInt(categoryData[0]);
        }
        return -1;
    }


}
