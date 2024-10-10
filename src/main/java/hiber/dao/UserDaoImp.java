package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

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
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public List<User> getUserByCarModelAndSeries(String carModel, int series) {
        String hql = "SELECT u FROM User u JOIN u.car  c WHERE c.model = :model AND c.series = :series";
        Session currentSession = sessionFactory.getCurrentSession();
        final Query<User> userQuery = currentSession.createQuery(hql, User.class)
                .setParameter("model", carModel)
                .setParameter("series", series);
        final List<User> resultList = userQuery.getResultList();
        return resultList;
    }
}
