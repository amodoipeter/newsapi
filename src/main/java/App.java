import Dao.Sql2oDepartmentDao;
import Dao.Sql2oNewsDao;
import Dao.Sql2oUserDao;
import com.google.gson.Gson;
import models.Department;
import models.DepartmentNews;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class App {
    private static Connection con;

    public static void main(String[] args) {
        Sql2oDepartmentDao departmentDao;
        Sql2oNewsDao newsDao;
        Sql2oUserDao userDao;
        Gson gson = new Gson();
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news", "moringa", "Ap@33358371");

        newsDao = new Sql2oNewsDao(sql2o);
        departmentDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        con = sql2o.open();

        staticFileLocation("/public");

        get("/", (req, res) -> {
            res.redirect("index.hbs");
            return null;
        });

        post("/Departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.addDepartment(department);
            res.status(201);
            res.type("application/json");
            res.redirect("/departments");
            return gson.toJson(department);
        });
        get("/departments", (req, res) -> {
            return gson.toJson(departmentDao.getDepartmentWithUserCount());
        });
        get("/users", (req, res) -> {
            return gson.toJson(userDao.getAllUsers());
        });
        get("/users/:id", (req, res) -> {
            int user_id = Integer.parseInt(req.params("id"));
            return gson.toJson(userDao.findUserById(user_id));
        });
        get("/departments/:id", (req, res) -> {
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentDao.findDepartmentById(dpt_id));
        });
        get("/departments/:id/users", (req, res) -> {
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentDao.getDepartmentUsersById(dpt_id));
        });
        get("/departments/:id/news", (req, res) -> {
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentDao.getDepartmentNewsById(dpt_id));
        });
        get("/news", (req, res) -> {
            return gson.toJson(newsDao.getAllNews());
        });
        get("/news/general", (req, res) -> {
            return gson.toJson(newsDao.getGeneralNews());
        });
        get("/news/department", (req, res) -> {
            return gson.toJson(newsDao.getDepartmentNews());
        });

        post("/Users/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);

            userDao.addUser(user);
            res.status(201);
            res.type("application/json");
            res.redirect("/users");
            return gson.toJson(user);
        });
        post("/News/new", "application/json", (req, res) -> {
            News news = gson.fromJson(req.body(), News.class);

            newsDao.addGeneralNews(news);
            res.status(201);
            res.type("application/json");
            res.redirect("/news/general");
            return gson.toJson(news);
        });
        post("/DepartmentNews/new", "application/json", (req, res) -> {
            DepartmentNews dnews = gson.fromJson(req.body(), DepartmentNews.class);
            newsDao.addDepartmentNews(dnews);
            res.status(201);
            res.type("application/json");
            res.redirect("/news/department");
            return gson.toJson(dnews);
        });
    }
}
