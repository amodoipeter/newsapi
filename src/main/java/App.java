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

import static spark.Spark.get;
import static spark.Spark.post;

public class App {

<<<<<<< HEAD
=======
    private static Object JobDetail;
>>>>>>> 1a24da622448f14a147ff96f4b2724c3de6c81c4
    public static void main(String[] args) {
        Sql2oDepartmentDao DepartmentDao;
        Sql2oNewsDao NewsDao;
        Sql2oUserDao UserDao;
        Connection conn;
        Gson gson =new Gson();

        Sql2o sql2o= new Sql2o("jdbc:postgresql://localhost:5432/news","moringa","Ap@33358371");

        Sql2oDepartmentDao sql2oDepartmentDao = new Sql2oDepartmentDao(sql2o);
        NewsDao = new Sql2oNewsDao(sql2o);
        UserDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();

        get("/",(req,res)->{
            res.redirect("index.hbs"); return null;
        });

        post("/Department/new", "application/json",(req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            Sql2oDepartmentDao.add(department);
            res.status(201);
            res.type("application/json");
            return gson.toJson(department);
        });
        get("/Department", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(sql2oDepartmentDao.getAllDepartments());//send it back to be displayed
        });

        post("/News/new", "application/json",(req, res) -> {
            News news = gson.fromJson(req.body(), News.class);
            NewsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });
        get("/News", "application/json", (req, res) -> {
            return gson.toJson(NewsDao.getAll());//send it back to be displayed
        });

        post("/User/new", "application/json",(req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            UserDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });
        get("/User", "application/json", (req, res) -> {
            return gson.toJson(UserDao.getAllUsers());//send it back to be displayed
        });

        post("/DepartmentNews/new", "application/json",(req, res) -> {
            DepartmentNews departmentnews = gson.fromJson(req.body(), DepartmentNews.class);
            NewsDao.addDepartmentNews(departmentnews);
            res.status(201);
            return gson.toJson(departmentnews);
        });
        get("/DepartmentNews", "application/json", (req, res) -> {
            return gson.toJson(NewsDao.findDepartmentNewsById());//send it back to be displayed
        });

        get("/Users/:id",(req,res)->{
            int user_id = Integer.parseInt(req.params("id"));
            return gson.toJson(UserDao.findUserById(user_id));
        });

        get("/Departments/:id",(req,res)->{
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(sql2oDepartmentDao.findDepartmentById(dpt_id));
        });

        get("/Departments/:id/users",(req,res)->{
           int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(sql2oDepartmentDao.getDepartmentUsersById(dpt_id));
        });
        get("/Departments/:id/news",(req,res)->{
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(sql2oDepartmentDao.getDepartmentNewsById(dpt_id));
        });

        get("/News", (req,res)-> gson.toJson(NewsDao.getAllNews()));
        get("/News/general", (req,res)-> gson.toJson(NewsDao.getGeneralNews()));
        get("/News/department", (req,res)-> gson.toJson(NewsDao.getDepartmentNews()));

    }
}
