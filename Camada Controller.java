@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDao userDao;
    
    @Override
    public void init() throws ServletException {
        super.init();
        
        try {
            Connection connection = // create database connection
            userDao = new UserDao(connection);
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize LoginServlet", e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        
        try {
            User user = userDao.findByLogin(login);
            
            if (user != null && user.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/topics.jsp");
            } else {
                request.setAttribute("errorMessage", "Usuário ou senha inválidos");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Unable to login user", e);
        }
    }
}
