package uz.nt.cashbackservice.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionCashBackHandler {

    public static CashBackId cashBackId() {
        return new CashBackId();
    }

    public static UserId userID() {
        return new UserId();
    }

    public static CashBackCardId cashBackCardId() {
        return new CashBackCardId();
    }

    public static class CashBackId extends Throwable {
        public CashBackId() {
            super("Cash Back Id is Null!");
        }
    }


    public static class UserId extends Throwable {
        public UserId() {
            super("UserId is Null!");
        }
    }

    public static class CashBackCardId extends Throwable {
        public CashBackCardId() {
            super("CashBackCardId is Null!");
        }
    }
}

