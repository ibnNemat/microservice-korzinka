package uz.nt.productservice.errors.exceptions;

public class DataExceptions {

    public static Enable enable(){
        return new Enable();
    }

    public static Disable disable(){
        return new Disable();
    }

    public static class Enable extends RuntimeException{
        public Enable() {
            super("Data is already exists!");
        }
    }

    public static class Disable extends RuntimeException{
        public Disable() {
            super("Data is not found!");
        }
    }
}
