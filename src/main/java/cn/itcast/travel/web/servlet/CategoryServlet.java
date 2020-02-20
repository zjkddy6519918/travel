package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/category/*")
public class CategoryServlet extends BaseServlet {

    CategoryService service = new CategoryServiceImpl();

    /**
     * 查询所有菜单
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=utf-8");

        List<Category> categoryList = service.findAll();
        sendObject(response, categoryList);
    }

    public void bak(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
