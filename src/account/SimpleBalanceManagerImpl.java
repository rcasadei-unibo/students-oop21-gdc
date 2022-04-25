package account;

import java.io.PrintWriter;

import org.json.simple.JSONObject;

public class SimpleBalanceManagerImpl implements SimpleBalanceManager {

    
    private SimpleAccountManager username;
    
    public SimpleBalanceManagerImpl(SimpleAccountManager username) {
        this.username = username;
    }
    
    
    
    @Override
    public boolean deposit(final double amount) {
        final double newbalance = getBalance() + amount;
        final JSONObject jo = Utility.getJsonObject(this.username.getUsr());
        jo.replace("Saldo", newbalance);
        Utility.writeOnFile(this.username.getUsr(), jo);
        return true;
    }

    @Override
    public boolean withdraw(final double amount) {
        final double newbalance = getBalance() - amount;
        if (newbalance >= 0) {
            changeBalance(newbalance);
            return true;
        } else {
            //Impossibile ritirare "amount", non si dispone di tale cifra;
            return false;
        }
    }
    
    @Override
    public boolean changeBalance(final double balancenew) {
        final JSONObject jo = Utility.getJsonObject(this.username.getUsr());
        jo.replace("Saldo", balancenew);
        Utility.writeOnFile(this.username.getUsr(), jo);
        return true;
    }
    
    @Override
    public double getBalance() { 
        return Double.parseDouble(String.valueOf((Utility.getJsonObject(this.username.getUsr())).get(Fields.BALANCE)));
    }
    
    
}