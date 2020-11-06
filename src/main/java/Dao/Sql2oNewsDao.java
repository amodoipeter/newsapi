package Dao;

import models.DepartmentNews;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

public class Sql2oNewsDao implements NewsDao {


    private final Sql2o sql2o;
    public static final String GENERAL_NEWS="general";
    public static final String DEPARTMENT_NEWS="department";

    public Sql2oNewsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<News> getAllNews() {

        List<News> news = new ArrayList<>();
        news.addAll(getGeneralNews());
        news.addAll(getDepartmentNews());
        return news;
    }

    @Override
    public List<News> getGeneralNews() {
        String sql = "select * from news where type=:type";
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("type",GENERAL_NEWS)
                    .executeAndFetch(News.class);
        }

    }

    @Override
    public List<DepartmentNews> getDepartmentNews() {
        String sql = "select * from news where type=:type";
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("type",DEPARTMENT_NEWS)
                    .executeAndFetch(DepartmentNews.class);
        }
    }

    @Override
    public void addGeneralNews(News news) {
        String sql = "insert into news (userId,type,content,postdate) values (:userId,:type,:content,now()) ";
        try(org.sql2o.Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql,true)
                    .addParameter("userId",news.getUserId())
                    .addParameter("type",news.getType())
                    .addParameter("content",news.getContent())
                    .executeUpdate().getKey();
            news.setId(id);
        }
    }

    @Override
    public void addDepartmentNews(DepartmentNews dptNews) {
        String sql =" insert into news (userId,type,content,postdate,departmentId) values (:userId,:type,:content,now(),:departmentId) ";
        try(org.sql2o.Connection con = sql2o.open()){
            int id = (int)  con.createQuery(sql,true)
                    .addParameter("userId", dptNews.getUserId())
                    .addParameter("type",dptNews.getType())
                    .addParameter("content",dptNews.getContent())
                    .addParameter("departmentId",dptNews.getDepartmentId())
                    .executeUpdate().getKey();
            dptNews.setId(id);
        }
    }

    @Override
    public News findGeneralNewsById(int id) {
        String sql = " select * from news where type=:type and id=:id";
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("type",GENERAL_NEWS)
                    .addParameter("id",id)
                    .executeAndFetchFirst(News.class);
        }

    }

    @Override
    public DepartmentNews findDepartmentNewsById(int id) {
        String sql = " select * from news where type=:type and id=:id";
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("type",DEPARTMENT_NEWS)
                    .addParameter("id",id)
                    .executeAndFetchFirst(DepartmentNews.class);
        }
    }

    @Override
    public void updateGeneralNews(News news, int userId, String content) {
        String sql = "update news set (userId, content) = (:userId, :content)  where id=:id ";
        try(org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userId",userId)
                    .addParameter("content",content)
                    .addParameter("id",news.getId())
                    .executeUpdate();
            news.setUserId(userId);
            news.setContent(content);
        }

    }

    @Override
    public void updateDepartmentNews(DepartmentNews dptNews, int userId, String content, int departmentId) {
        String sql = "update news set (userId, content,departmentId) = (:userId,  :content,:departmentId)  where id=:id ";
        try(org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userId",userId)
                    .addParameter("content",content)
                    .addParameter("departmentId",departmentId)
                    .addParameter("id",dptNews.getId())
                    .executeUpdate();
            dptNews.setUserId(userId);
            dptNews.setContent(content);
            dptNews.setDepartmentId(departmentId);
        }
    }

    @Override
    public void clearAllNews() {
        String sql="delete from news";
        try(org.sql2o.Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }
    }

    @Override
    public void clearGeneralNews() {
        String sql="delete from news where type = :type";
        try(org.sql2o.Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("type",GENERAL_NEWS)
                    .executeUpdate();
        }
    }

    @Override
    public void clearDepartmentNews() {
        String sql="delete from news where type = :type";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("type",DEPARTMENT_NEWS)
                    .executeUpdate();
        }
    }
}
