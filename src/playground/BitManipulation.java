package playground;

public class BitManipulation {
    public static void main(String[] args) {
        long l = 1;
        long realL = 1L;
        int i = 1;

        l <<= 30;
        realL <<= 30;
        i <<= 30;

        l <<= 1;
        realL <<= 1;
        i <<= 1;

        l <<= 1;
        realL <<= 1;
        i <<= 1;

        l <<= 1;
        realL <<= 1;
        i <<= 1;


        long i_30 = 1<<30;
        long i_31 = 1<<31;
        long i_32 = 1<<32;
        long i_33 = 1<<33;

        long l_30 = ((long)1)<<30;
        long l_31 = ((long)1)<<31;
        long l_32 = ((long)1)<<32;
        long l_33 = ((long)1)<<33;

        long realL_30 = 1L<<30;
        long realL_31 = 1L<<31;
        long realL_32 = 1L<<32;
        long realL_33 = 1L<<33;

        return;
    }
}
