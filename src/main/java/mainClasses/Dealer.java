package mainClasses;

import java.util.Map;

public class Dealer extends User{
    private double supply;
    private double total_profit;
    private double owed_amount;
    public Dealer(Map<String, String> params){
        super(params);
        this.setSupply(Double.parseDouble(params.get("supply")));
    }
    public double getSupply() {
        return supply;
    }
    public double getTotal_profit() {
        return total_profit;
    }
    public double getOwed_amount() {
        return owed_amount;
    }


    public void setSupply(double supply) {
        this.supply = supply;
    }
    public void setTotal_profit(double total_profit) {
        this.total_profit = total_profit;
    }
    public void setOwed_amount(double owed_amount) {
        this.owed_amount = owed_amount;
    }

}
