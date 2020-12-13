package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public Optional<User> getUserByCar(Car car) {
      TypedQuery<User> query = sessionFactory.getCurrentSession()
              .createQuery("from User as user where user.car.model = :model and user.car.series = :series");

      query.setParameter("model", car.getModel());
      query.setParameter("series", car.getSeries());

      List<User> users = query.getResultList();

      User user = null;

      if (!users.isEmpty()) {
         user = users.get(0);
      }

      return Optional.ofNullable(user);
   }

}
