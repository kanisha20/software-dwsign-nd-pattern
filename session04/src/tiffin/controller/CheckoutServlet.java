package tiffin.controller;
import javax.servlet.ServletException; import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*; import java.io.IOException; import java.util.Map;

/** CheckoutServlet — same as Q3 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/checkout_step1.jsp").forward(req, resp);
    }
    @Override @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String step = req.getParameter("step");
        if ("2".equals(step)) {
            String name = req.getParameter("customerName");
            if(name==null||name.trim().isEmpty()){req.setAttribute("error","Name empty!");req.getRequestDispatcher("/WEB-INF/views/checkout_step1.jsp").forward(req,resp);return;}
            if(name.trim().length()>40){req.setAttribute("error","Name under 40 chars!");req.getRequestDispatcher("/WEB-INF/views/checkout_step1.jsp").forward(req,resp);return;}
            req.setAttribute("customerName",name.trim()); req.getRequestDispatcher("/WEB-INF/views/checkout_step2.jsp").forward(req,resp);
        } else if ("3".equals(step)) {
            String name=req.getParameter("customerName"), slot=req.getParameter("slot");
            if(name==null||name.trim().isEmpty()||name.trim().length()>40){req.setAttribute("error","Invalid name!");req.getRequestDispatcher("/WEB-INF/views/checkout_step1.jsp").forward(req,resp);return;}
            if(slot==null||slot.trim().isEmpty()){req.setAttribute("error","Select slot!");req.setAttribute("customerName",name.trim());req.getRequestDispatcher("/WEB-INF/views/checkout_step2.jsp").forward(req,resp);return;}
            HttpSession s=req.getSession(); Map<Integer,Integer> cart=(Map<Integer,Integer>)s.getAttribute("cart"); double total=0;
            if(cart!=null&&!cart.isEmpty()){tiffin.dao.TiffinPlanDao dao=new tiffin.dao.TiffinPlanDaoImpl(getServletContext().getInitParameter("jdbcUrl"),getServletContext().getInitParameter("dbUser"),getServletContext().getInitParameter("dbPassword"));
            for(Map.Entry<Integer,Integer> e:cart.entrySet()){tiffin.model.TiffinPlan p=dao.findById(e.getKey());if(p!=null)total+=p.getPricePerDay()*e.getValue();}}
            req.setAttribute("customerName",name.trim()); req.setAttribute("slot",slot); req.setAttribute("cartTotal",total);
            req.getRequestDispatcher("/WEB-INF/views/checkout_confirm.jsp").forward(req,resp);
        } else { resp.sendRedirect(req.getContextPath()+"/checkout?step=1"); }
    }
}
