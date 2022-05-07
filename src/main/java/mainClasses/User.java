package mainClasses;

import java.util.Map;

public class User {
    int account_id;
    String username;

    public User(Map<String, String> params){
        if(params.get("username")!=null)
            this.setUsername(params.get("username"));

        if(params.get("account_id")!=null)
            this.setAccountId(Integer.parseInt(params.get("account_id")));
    }

    public int getAccountId() {
        return account_id;
    }
    public String getUsername() {
        return username;
    }

    public void setAccountId(int accountId) {
        this.account_id = accountId;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "account_id=" + account_id +
                ", username='" + username + '\'' +
                '}';
    }
}
