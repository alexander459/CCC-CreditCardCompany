package mainClasses;

import java.util.Map;

public class Employee {
    private int employee_id;
    private int account_id;
    String employee_name;

    public Employee(Map<String, String> params){
        this.setEmployee_name(params.get("employee_name"));
        this.setAccount_id(Integer.parseInt(params.get("account_id")));
    }

    public int getEmployee_id() {
        return employee_id;
    }
    public int getAccount_id(){
        return this.account_id;
    }
    public String getEmployee_name() {
        return employee_name;
    }


    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
    public void setAccount_id(int account_id){
        this.account_id = account_id;
    }
    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }
}
