package uz.nt.productservice.errors.exceptions;


public class PaginationExceptions{

    public static Page getPage(){
        return new Page();
    }

    public static Size getSize(){
        return new Size();
    }


    public static class Page extends RuntimeException{

        public Page() {
            super("Parameter \"Size\" is null or equals to zero(0).");
        }
    }

    public static class Size extends RuntimeException{

        public Size() {
            super("Parameter \"Size\" is null or equals to zero(0).");
        }

    }
}
