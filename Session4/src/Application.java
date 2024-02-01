import com.ra.entity.Category;
import com.ra.service.CategoryService;
import com.ra.service.impl.CategoryServiceImpl;
import com.ra.util.MySQLConnect;

import java.sql.Connection;

public class Application {
    public static void main(String[] args) {
//        Connection conn = MySQLConnect.open();
//        if(conn != null){
//            System.out.println("Kết nối thành công");
//        }
//        else{
//            System.out.println("Kết nối thất bại");
//        }
        CategoryService categoryService = new CategoryServiceImpl();
        Category category = new Category();
        category.setCategoryName("Shoes");
        category.setDescription("Shoes for men");
        category.setParentCategoryID(2);
        categoryService.insert(category);

        System.out.println("new id = "+category.getCategoryID());
        for(Category category1 : categoryService.findAll()){
            System.out.println(category1.getCategoryID() +":"+category1.getCategoryName());
        }
//        for(Category category : categoryService.findByName("a")){
//            System.out.println(category.getCategoryID() +":"+category.getCategoryName());
//        }
    }
}
