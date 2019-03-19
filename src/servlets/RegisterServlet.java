package servlets;

import com.google.gson.Gson;
import data_access_object.UserDAO;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try (PrintWriter out = response.getWriter()) {

            //获得请求中传来的用户名和密码
            String userName = request.getParameter("UserName").trim();
            String password = request.getParameter("Password").trim();
            String userId = request.getParameter("UserId").trim();

            //密码验证结果
            int result = registUser(userName,password,userId);

            Map<String, String> params = new HashMap<>();


            switch (result){

                case 2:
                    params.put("Result","注册失败");
                    break;
                case 1:
                    params.put("Result","用户名已存在");
                    break;
                case 0:
                    params.put("Result","注册成功");
                    break;

            }
            Gson gson = new Gson();
            String s = gson.toJson(params);
            out.write(s);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private int registUser(String userName, String password, String userId) {
        User user = UserDAO.queryUser(userName);
        if(user != null)
            return 1;
        user = UserDAO.insertUser(userName,password,userId);
        if(user == null)
            return 2;
        return  0;
    }

}
