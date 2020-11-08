
package Dao;

import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oUserDao implements UserDao {

    private final Sql2o sql2o;
    public Sql2oUserDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<User> getAllUsers() {
        String sql="select * from users";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql).executeAndFetch(User.class);
        }

    }

    @Override
    public void addUser(User user) {
        String sql ="insert into users (name, position, role, departmentId) values (:name, :position, :role, :departmentId)";
        try(org.sql2o.Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql,true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setId(id);
        }
    }

    @Override
    public User findUserById(int id) {
        String sql ="select * from users where id = :id ";
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(sql).addParameter("id",id).executeAndFetchFirst(User.class);
        }
    }

    @Override
    public void updateUser(User user, String name, String position, String role, int departmentId) {
        String sql = "update users set(name,position,role,departmentId) = (:name,:position,:role,:departmentId) where id= :id ";
        try(org.sql2o.Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name",name)
                    .addParameter("position",position)
                    .addParameter("role",role)
                    .addParameter("departmentId",departmentId)
                    .addParameter("id",user.getId())
                    .executeUpdate();

            user.setName(name);
            user.setPosition(position);
            user.setRole(role);
            user.setDepartmentId(departmentId);
        }
    }

    @Override
    public void clearAllUsers() {
        String sql = "delete from users";
        try(org.sql2o.Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }
    }
}
