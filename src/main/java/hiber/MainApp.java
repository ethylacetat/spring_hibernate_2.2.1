package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      Random rnd = new Random(System.nanoTime());

      Car userCar;
      userCar = new Car("Lada", rnd.nextInt(Integer.MAX_VALUE));
      userService.add(new User("User1", "Lastname1", "user1@mail.ru", userCar));

      userCar = new Car("BMW", rnd.nextInt(Integer.MAX_VALUE));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru", userCar));

      userCar = new Car("Belaz", rnd.nextInt(Integer.MAX_VALUE));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru", userCar));

      userCar = new Car("Mini", rnd.nextInt(Integer.MAX_VALUE));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru", userCar));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }

      System.out.println();

      Optional<User> optionalUser = userService.getUserByCar(new Car("Mini", 832689763));
      if (optionalUser.isPresent()) {
         User user = optionalUser.get();
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());
         System.out.println("Car = " + user.getCar().getModel() + ":" + user.getCar().getSeries());
      } else {
         System.out.println("User not found");
      }

      context.close();
   }
}
