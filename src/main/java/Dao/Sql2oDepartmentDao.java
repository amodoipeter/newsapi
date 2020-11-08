package Dao;

import models.Department;
import models.DepartmentNews;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.stream.Collectors;

public class Sql2oDepartmentDao implements DepartmentDao {

    private final Sql2o sql2o;
    private final Dao.Sql2oUserDao userDao;
    private final Sql2oNewsDao newsDao;

    public Sql2oDepartmentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
        this.userDao = new Dao.Sql2oUserDao(sql2o);
        this.newsDao = new Sql2oNewsDao(sql2o);

    }

    @Override
    public List<Department> getAllDepartments() {
        String sql ="SELECT * FROM departments";
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .executeAndFetch(Department.class);
        }

    }

    @Override
    public List<User> getDepartmentUsersById(int id) {
        return userDao.getAllUsers().stream()
                .filter(user -> id == user.getDepartmentId())
                .collect(Collectors.toList());
    }

    @Override
    public List<DepartmentNews> getDepartmentNewsById(int id) {
        return newsDao.getDepartmentNews().stream()
                .filter(dpt->dpt.getDepartmentId()==id)
                .collect(Collectors.toList());
    }

    @Override
    public void addDepartment(Department department) {
        String sql = "INSERT INTO departments (name,description) values (:name,:description) ";
        try(org.sql2o.Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql,true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);
        }
    }

    @Override
    public Department findDepartmentById(int id) {
        String sql ="SELECT * FROM departments where id=:id";
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Department.class);
        }

    }

    @Override
    public void updateDepartment(Department department, String name, String description) {
        String sql ="UPDATE departments set (name, description) = (:name, :description) ";
        try(org.sql2o.Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name",name)
                    .addParameter("description",description)
                    .executeUpdate();
            department.setName(name);
            department.setDescription(description);
        }
    }

    public List<Department.DepartmentWithUserCount> getDepartmentWithUserCount(){
        return getAllDepartments().stream()
                .map(dpt->
                        new Department.DepartmentWithUserCount(
                                dpt.getId(),
                                dpt.getName(),
                                dpt.getDescription(),
                                getDepartmentUsersById(dpt.getId()).size()
                        )).collect(Collectors.toList());
    }

    @Override
    public void clearAllDepartments() {
        String sql =" DELETE FROM departments";
        try(Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }
    }
}
