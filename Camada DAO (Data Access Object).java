public class UserDao {
    private Connection connection;
    
    public UserDao(Connection connection) {
        this.connection = connection;
    }
    
    public User findByLogin(String login) throws SQLException {
        String sql = "SELECT * FROM users WHERE login = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLogin(resultSet.getString("login"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                }
            }
        }
        
        return null;
    }
    
    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO users (name, login, email, password) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.executeUpdate();
        }
    }
}
