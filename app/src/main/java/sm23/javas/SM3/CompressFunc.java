package sm23.javas.SM3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by XUI on 2017/4/23.
 */

public class CompressFunc {
    static param pa = new param();
    private static char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F'};//定义寄存器

    public static byte[] toByteArray(int i) //官方文档定义了大端小端存储，注意赋值顺序
    {
        byte[] byteArray = new byte[4];
        byteArray[0] = (byte) (i >>> 24);
        byteArray[1] = (byte) ((i & 0xFFFFFF) >>> 16);
        byteArray[2] = (byte) ((i & 0xFFFF) >>> 8);
        byteArray[3] = (byte) (i & 0xFF);
        return byteArray;
    }


    public static byte[] toByteArray(int a, int b, int c, int d, int e, int f,
                                     int g, int h) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(32);
        baos.write(toByteArray(a));
        baos.write(toByteArray(b));
        baos.write(toByteArray(c));
        baos.write(toByteArray(d));
        baos.write(toByteArray(e));
        baos.write(toByteArray(f));
        baos.write(toByteArray(g));
        baos.write(toByteArray(h));
        return baos.toByteArray();
    }
    private static int toInteger(byte[] source, int index)
    {
        StringBuilder valueStr = new StringBuilder("");
        for (int i = 0; i < 4; i++)
        {
            valueStr.append(chars[(byte) ((source[index * 4 + i] & 0xF0) >> 4)]);
            valueStr.append(chars[(byte) (source[index * 4 + i] & 0x0F)]);
        }
        return Long.valueOf(valueStr.toString(), 16).intValue();

    }


    public static byte[] cf(byte[] vi, byte[] b) throws IOException
    {
        //对b进行扩展
        int[] w = new int[68];
        int[] ww = new int[64];
        //0--15
        for(int i=0;i<16;i++)
        {
            w[i] = toInteger(b, i);
        }
        //16--67
        for(int i=16;i<68;i++)
        {
            w[i] = pa.P1(w[i - 16] ^ w[i - 9] ^ Integer.rotateLeft(w[i - 3], 15))
                    ^ Integer.rotateLeft(w[i - 13], 7) ^ w[i - 6];
        }
        //w'
        for(int i=0;i<64;i++)
        {
            ww[i] = w[i] ^ w[i + 4];
        }

        int A,B,C,D,E,F,G,H;//字寄存器
        A = toInteger(vi, 0);
        B = toInteger(vi, 1);
        C = toInteger(vi, 2);
        D = toInteger(vi, 3);
        E = toInteger(vi, 4);
        F = toInteger(vi, 5);
        G = toInteger(vi, 6);
        H = toInteger(vi, 7);

        int SS1,SS2,TT1,TT2;
        for (int i = 0; i < 64; i++)
        {
            SS1 = Integer.rotateLeft
                    (Integer.rotateLeft(A, 12)+E+Integer.rotateLeft(pa.Tj(i), i), 7);
            SS2 = SS1 ^ Integer.rotateLeft(A, 12);
            TT1 = pa.FF(A, B, C, i)+D+SS2+ww[i];
            TT2 = pa.GG(E, F, G, i)+H+SS1+w[i];
            D = C;
            C = Integer.rotateLeft(B, 9);
            B = A;
            A = TT1;
            H = G;
            G = Integer.rotateLeft(F, 19);
            F = E;
            E = pa.P0(TT2);
        }
        byte[] A_H = toByteArray(A,B,C,D,E,F,G,H);
        for(int i=0;i<A_H.length;i++)
        {
            A_H[i] = (byte) (A_H[i] ^ vi[i]);
        }

        return A_H;

    }

}
