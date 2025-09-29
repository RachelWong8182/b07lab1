import java.io.File;

public class Driver {
    public static void main(String [] args) {
        // Original tests
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,0,0,5};
        Polynomial p1 = new Polynomial(c1);
        double [] c2 = {0,-2,0,0,-9};
        Polynomial p2 = new Polynomial(c2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
        
        // Test multiply method
        Polynomial m = p1.multiply(p2);
        System.out.println("m(2) = " + m.evaluate(2));
        
        // Test two-array constructor
        double[] coeffs = {8,-1,6};
        int[] exps = {0,3,4};
        Polynomial p3 = new Polynomial(coeffs, exps);
        System.out.println(p3);
        
        // Test file operations
        try {
            p3.saveToFile("poly.txt");
            File file = new File("poly.txt");
            Polynomial p4 = new Polynomial(file);
            System.out.println(p4);
        } catch(Exception e) {
            System.out.println("File error");
        }
    }
}


