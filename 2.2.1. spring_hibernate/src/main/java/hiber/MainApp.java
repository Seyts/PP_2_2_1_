package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      // Создаем пользователей и машины
      User user1 = new User("Иван", "Иванов", "user1@mail.ru", new Car("BMW", 5));
      User user2 = new User("Петр", "Петров", "user2@mail.ru", new Car("Audi", 3));
      User user3 = new User("Сергей", "Сергеев", "user3@mail.ru", new Car("Mercedes", 7));

      // Добавляем пользователей в базу данных
      userService.add(user1);
      userService.add(user2);
      userService.add(user3);

      // Выводим всех пользователей в консоль
      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car = " + user.getCar());
         System.out.println();
      }

      // Поиск пользователя по модели и серии машины
      String model = "BMW";
      int series = 5;
      List<User> usersByCar = userService.getUserByCar(model, series);
      if (usersByCar.isEmpty()) {
         System.out.println("Пользователь не найден");
      } else {
         for (User user : usersByCar) {
            System.out.println("Найден пользователь: " + user.getFirstName() + " " + user.getLastName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Car: " + user.getCar().getModel() + " {серия = " + user.getCar().getSeries() + "}");
         }
      }

      context.close();
   }
}