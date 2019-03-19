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

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置响应内容类型
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try (PrintWriter out = response.getWriter()) {

            //获得请求中传来的用户名和密码
            String accountNumber = request.getParameter("AccountNumber").trim();
            String password = request.getParameter("Password").trim();

            //密码验证结果
            Boolean verifyResult = verifyLogin(accountNumber, password);

            Map<String, String> params = new HashMap<>();


            if (verifyResult) {
                params.put("Result", "success");
            } else {
                params.put("Result", "failed");
            }
            Gson gson = new Gson();
            String s = gson.toJson(params);

            out.write(s);
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    private Boolean verifyLogin(String userName, String password) {
        User user = UserDAO.queryUser(userName);
        //账户密码验证
        return (null != user) && password.equals(user.getPassword());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
//
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet LoginServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
}
