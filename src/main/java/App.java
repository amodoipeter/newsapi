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

//    private static Sql2oNewsDao newsDao;
//     private static Sql2oDepartmentDao dptDao;
//     private static Sql2oUserDao userDao;
      //public static Sql2o sql2o;
//     public static Gson gson = new Gson();
//     public Connection conn;

    public static void main(String[] args) {
        Sql2oNewsDao newsDao;
        Sql2oDepartmentDao dptDao;
        Sql2oUserDao userDao;
        Gson gson = new Gson();
        Connection conn;

        Sql2o sql2o= new Sql2o("jdbc:postgresql://localhost:5432/news","moringa","Ap@33358371");


       // String connectionString = "jdbc:postgresql:~/news.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
       // Sql2o sql2o = new Sql2o(connectionString, "moringa", "Ap@33358371");

        newsDao = new Sql2oNewsDao(sql2o);
        dptDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();


        post("/Departments/new", "application/json", (req,res)->{
            Department dpt = gson.fromJson(req.body(),Department.class);

            dptDao.addDepartment(dpt);
            res.status(201);
            res.type("application/json");
            res.redirect("/departments");
            return gson.toJson(dpt);
        });
        post("/Users/new", "application/json", (req,res)->{
            User user = gson.fromJson(req.body(), User.class);

            userDao.addUser(user);
            res.status(201);
            res.type("application/json");

            res.redirect("/users");
            return gson.toJson(user);
        });
        post("/News/new", "application/json", (req,res)->{
            News news = gson.fromJson(req.body(), News.class);

            newsDao.addGeneralNews(news);
            res.status(201);
            res.type("application/json");
            res.redirect("/news/general");
            return gson.toJson(news);
        });
        post("/DepartmentNews/new", "application/json", (req,res)->{
            DepartmentNews dnews = gson.fromJson(req.body(), DepartmentNews.class);

            newsDao.addDepartmentNews(dnews);
            res.status(201);
            res.type("application/json");

            res.redirect("/news/department");
            return gson.toJson(dnews);
        });

        get("/users", (req,res)-> gson.toJson(userDao.getAllUsers()));
        get("/departments", (req,res)-> gson.toJson(dptDao.getDepartmentWithUserCount()));
        get("/users/:id",(req,res)->{
            int user_id = Integer.parseInt(req.params("id"));
            return gson.toJson(userDao.findUserById(user_id));
        });
        get("/departments/:id",(req,res)->{
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(dptDao.findDepartmentById(dpt_id));
        });
        get("/departments/:id/users",(req,res)->{
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(dptDao.getDepartmentUsersById(dpt_id));
        });
        get("/departments/:id/news",(req,res)->{
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(dptDao.getDepartmentNewsById(dpt_id));
        });
        get("/news", (req,res)-> gson.toJson(newsDao.getAllNews()));
        get("/news/general", (req,res)-> gson.toJson(newsDao.getGeneralNews()));
        get("/news/department", (req,res)-> gson.toJson(newsDao.getDepartmentNews()));

    }
}
