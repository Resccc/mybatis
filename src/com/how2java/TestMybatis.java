package com.how2java;
  
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
 
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
 
import com.how2java.pojo.Category;
import com.how2java.pojo.Product;
import com.how2java.pojo.Order;
import com.how2java.pojo.OrderItem;
  
public class TestMybatis {
  
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
  
        /*List<Category> cs = session.selectList("listCategory");
        for (Category c : cs) {
            System.out.println(c);
            List<Product> ps = c.getProducts();
            for (Product p : ps) {
                System.out.println("\t"+p);
            }
        }*/
        
        //Category c=new Category();
        //c.setId(1);
        
		/*
		 * Product p1= session.selectOne("selectOne",1); p1.setName("product a");
		 * p1.setPrice((float) 87); session.update("updateProduct_cid", p1);
		 * 
		 * List<Product> ps = session.selectList("listProduct"); for (Product p : ps) {
		 * System.out.println(p+" 对应的分类是 \t "+ p.getCategory()); }
		 */
        
        deleteOrderItem(session);
        listOrder(session);
        
        session.commit();
        session.close();
        
        
  
    }
    
    private static void deleteOrderItem(SqlSession session) {
    	Order o=session.selectOne("getOrder",1);
    	Product p5 = session.selectOne("selectOne",5);
        OrderItem oi = new OrderItem();
        oi.setProduct(p5);
        oi.setOrder(o);
        session.delete("deleteOrderItem", oi);
    }
    
    private static void addOrderItem(SqlSession session) {
    	Order o=session.selectOne("getOrder",1);
    	Product  p5=session.selectOne("selectOne",5);
    	OrderItem oi =new OrderItem();
    	oi.setProduct(p5);
    	oi.setOrder(o);
    	oi.setNumber(200);
    	session.insert("addOrderItem", oi);

    	
    }
    
    private static void listOrder(SqlSession session) {
        List<Order> os = session.selectList("listOrder");
        for (Order o : os) {
            System.out.println(o.getCode());
            List<OrderItem> ois= o.getOrderItems();
            for (OrderItem oi : ois) {
                System.out.format("\t%s\t%f\t%d%n", oi.getProduct().getName(),oi.getProduct().getPrice(),oi.getNumber());
            }
        }
    }
    
}