package contentsstudio.kr.calculator2;

/**
 * Created by junsuk on 16. 4. 22..
 */
public class Calculator {
    enum TYPE {
        TOTAL, MONEY, VAT
    }

    private double total;
    private double money;
    private double vat;

    public Calculator() {
    }

    public Calculator(TYPE type, double price) {
        calc(type, price);
    }

    private void calc(TYPE type, double price) {
        switch (type) {
            case TOTAL:
                total = price;
                money = total / 1.10;
                vat = total - money;
                break;
            case MONEY:
                money = price;
                vat = money * 0.1;
                total = money + vat;
                break;
            case VAT:
                vat = price;
                money = vat * 10;
                total = money + vat;
                break;
        }
    }

    public double getMoney() {
        return money;
    }

    public double getTotal() {
        return total;
    }

    public double getVat() {
        return vat;
    }

    public void setMoney(double money) {
        calc(TYPE.MONEY, money);
    }

    public void setTotal(double total) {
        calc(TYPE.TOTAL, total);
    }

    public void setVat(double vat) {
        calc(TYPE.VAT, vat);
    }
}
