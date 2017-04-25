package sm23.javas.SM3;
import java.math.BigInteger;


/**
 * Created by XUI on 2017/4/23.
 */

public class param
{
    //IV--初始值
    static String iv = "7380166f4914b2b9172442d7da8a0600a96f30bc163138aae38dee4db0fb0e4e";
    public static final BigInteger IV = new BigInteger(iv,16);

    //Tj--常量
    public static final Integer Tj0 = Integer.valueOf("79cc4519", 16);
    public static final Integer Tj16 = Integer.valueOf("7a879d8a", 16);
    public static int Tj (int j)
    {
        if (j >= 0 && j <= 15)
        {
            return Tj0.intValue();//以int类型返回该数值
        } else if (j >= 16 && j <= 63)
        {
            return Tj16.intValue();
        } else
        {
            throw new RuntimeException("data invalid");
        }

    }

    //布尔函数
    public static Integer FF(Integer x, Integer y, Integer z, int j)
    {
        if (j >= 0 && j <= 15)
        {//0≤j≤15时FF布尔运算
            return Integer.valueOf(x.intValue() ^ y.intValue() ^ z.intValue());
        }
        else if (j >= 16 && j <= 63)
        {
            return Integer.valueOf((x.intValue() & y.intValue())
                    | (x.intValue() & z.intValue())
                    | (y.intValue() & z.intValue()));
        }
        else
        {
            throw new RuntimeException("data invalid");
        }
    }

    public static Integer GG(Integer x, Integer y, Integer z, int j)
    {
        if (j >= 0 && j <= 15)
        {
            return Integer.valueOf(x.intValue() ^ y.intValue() ^ z.intValue());
        }
        else if (j >= 16 && j <= 63)
        {
            return Integer.valueOf((x.intValue() & y.intValue())
                    | (~x.intValue() & z.intValue()));
        }
        else
        {
            throw new RuntimeException("data invalid");
        }
    }

    //置换函数
    public static Integer P0(Integer x)
    {
        return Integer.valueOf(
                x.intValue()
                        ^ Integer.rotateLeft(x.intValue(), 9)
                        ^ Integer.rotateLeft(x.intValue(), 17));
    }

    public static Integer P1(Integer x)
    {
        return Integer.valueOf(x.intValue()
                ^ Integer.rotateLeft(x.intValue(), 15)
                ^ Integer.rotateLeft(x.intValue(), 23));
    }

    public static String byte2string(byte [] buffer)
    {
        String h = "";
        for(int i = 0; i < buffer.length; i++)
        {
            String temp = Integer.toHexString(buffer[i] & 0xFF);
            if(temp.length() == 1)
            {
                temp = "0" + temp;
            }
            h = h + temp;
        }
        return h;
    }
}
