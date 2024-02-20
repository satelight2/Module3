import model.entity.ProductEntity;
import respository.IRepository;
import respository.Repository;

public class Application {
    public static void main(String[] args) {
        IRepository<ProductEntity, String> productRepository = new Repository<>();

//        for(ProductEntity product : productRepository.findAll(ProductEntity.class)) {
//            System.out.println(product.getId()+": "+product.getName());
//        }
//        ProductEntity product = productRepository.findById("newid", ProductEntity.class);
//        System.out.println(product.getId()+": "+product.getName());
//        product.setName("product 5 updated");
//        productRepository.edit(product);
        productRepository.remove("newid", ProductEntity.class);

//        ProductEntity product1 = new ProductEntity("newid","product 5",1,"image","listImage",1000f,900f,"description","keyword","content",true);
//        productRepository.add(product1);
        // ProductEntity product2 = new ProductEntity("newid2","product 5",1,"image","listImage",1000f,900f,"description","keyword","content",true);
    }
}
