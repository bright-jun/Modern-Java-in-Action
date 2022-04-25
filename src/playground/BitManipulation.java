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
        String s_i_30 = Integer.toBinaryString((int)i_30);
        long i_31 = 1<<31;
        String s_i_31 = Integer.toBinaryString((int)i_31);
        long i_32 = 1<<32;
        String s_i_32 = Integer.toBinaryString((int)i_32);
        long i_33 = 1<<33;
        String s_i_33 = Integer.toBinaryString((int)i_33);

        long l_30 = ((long)1)<<30;
        String s_l_30 = Long.toBinaryString(l_30);
        long l_31 = ((long)1)<<31;
        String s_l_31 = Long.toBinaryString(l_31);
        long l_32 = ((long)1)<<32;
        String s_l_32 = Long.toBinaryString(l_32);
        long l_33 = ((long)1)<<33;
        String s_l_33 = Long.toBinaryString(l_33);
        long l_62 = ((long)1)<<62;
        String s_l_62 = Long.toBinaryString(l_62);
        long l_63 = ((long)1)<<63;
        String s_l_63 = Long.toBinaryString(l_63);
        long l_63_1 = (((long)1)<<63)-1;
        String s_l_63_1 = Long.toBinaryString(l_63_1);
        long l_64 = ((long)1)<<64;
        String s_l_64 = Long.toBinaryString(l_64);

        long realL_30 = 1L<<30;
        long realL_31 = 1L<<31;
        long realL_32 = 1L<<32;
        long realL_33 = 1L<<33;

        return;
    }
}
