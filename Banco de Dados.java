@WebServlet("/login")
public class LoginServlet extends HttpServlet {
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        UserDao userDao = new UserDao();
        User user = userDao.findByUsername(username);
        
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("topics.jsp");
        } else {
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String url = "jdbc:postgresql://localhost:5432/mydatabase";
    private static final String user = "myusername";
    private static final String password = "mypassword";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}

try {
    Connection conn = Database.getConnection();
    // Use a conexão para executar queries no banco de dados
} catch (SQLException ex) {
    ex.printStackTrace();
}

CREATE TABLE usuario
(
  login text NOT NULL,
  email text,
  nome text,
  senha text,
  pontos integer,
  CONSTRAINT usuario_pkey PRIMARY KEY (login)
);

CREATE SEQUENCE topico_id_topico_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE topico
(
  id_topico integer NOT NULL DEFAULT nextval('topico_id_topico_seq'::regclass),
  titulo text,
  conteudo text,
  login_usuario text,
  CONSTRAINT topico_pkey PRIMARY KEY (id_topico),
  CONSTRAINT topico_login_usuario_fkey FOREIGN KEY (login_usuario)
      REFERENCES usuario (login) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE comentario_id_comentario_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE comentario
(
  id_comentario integer NOT NULL DEFAULT nextval('comentario_id_comentario_seq'::regclass),
  texto text,
  login_usuario text,
  id_topico integer,
  CONSTRAINT comentario_pkey PRIMARY KEY (id_comentario),
  CONSTRAINT comentario_login_usuario_fkey FOREIGN KEY (login_usuario)
      REFERENCES usuario (login) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT comentario_id_topico_fkey FOREIGN KEY (id_topico)
      REFERENCES topico (id_topico) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
