import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataPerson {
    private final String login;
    private final String password;
    private final String status;

    public DataPerson(String login, String password, String status) {
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}