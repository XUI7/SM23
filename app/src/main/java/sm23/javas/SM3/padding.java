package sm23.javas.SM3;

import java.io.IOException;
import sm23.javas.SM3.param.*;

/**
 * Created by XUI on 2017/4/23.
 */

/**
 * java源码中注释
 * @author threads.xui
 *填充过程为：假设消息m 的长度为l 比特。首先将比特“1”添加到消息的末尾，
 *再添加k 个“0”，k是满 足l + 1 + k ≡ 448mod512 的最小的非负整数。
 *然后再添加一个64位比特串，该比特串是长度l的二进 制表示。填充后的消息m′
 *的比特长度为512的倍数。
 *需要注意的是如果一开始l长度就是512的倍数依然需要进行填充
 */

public class padding
{
    static param pa = new param();
    //byte[]拼接
    public static byte[] joint(byte[] a,byte[]b)
    {
        byte[] c = new byte[a.length+b.length];
        System.arraycopy(a,0,c,0,a.length);
        System.arraycopy(b,0,c,a.length,b.length);
        return c;
    }

    //获取消息长度的二进制表示来填充最后64 bite位
    public static byte[] last(long l)
    {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++)
        {
            bytes[i] = (byte) (l >>> ((7 - i) * 8));
        }
        return bytes;
    }


    public static byte[] padding(byte[] m)throws IOException
    {
        if (m.length >= 0x2000000000000000l) //最大长度限制
        {
            throw new RuntimeException("消息过长，无法处理");
        }
        System.out.println("原始消息：" + pa.byte2string(m));
        long l = m.length * 8; //消息长度
        long k = 448 - (l + 1) % 512;
        if (k < 0)
        {
            k = k + 512;
        }

        byte[] M = m;
        final byte[] one = {(byte) 0x80};
        M = joint(M,one);
        final byte[] zero = {(byte) 0x00};
        long i = k - 7;
        while (i > 0)
        {
            M = joint(M, zero);//填充0
            i -= 8;
        }
        M = joint(M, last(l));
        System.out.println("填充之后的消息：" + pa.byte2string(M));

        return M;
    }








//	public static void main(String[] args) throws IOException
//	{
//		// TODO Auto-generated method stub
//		param pa = new param();
//		//拼接测试
////		byte[] a = new byte[]{0x12,0x13,0x14};
////		byte[] b = new byte[]{0x22,0x23,0x24};
////		byte[] c = joint(a, b);
//////		for(byte bbb:c) {
//////        	System.out.println(bbb);
//////        }
////		String A = pa.byte2hex(a);
////		String B = pa.byte2hex(b);
////		String C = pa.byte2hex(c);
////		System.out.println(A);
////		System.out.println(B);
////		System.out.println(C);
//
//		//填充测试--测试通过
//		byte[] source = "abc".getBytes();
//		byte[] padend = padding(source);
//		String test = param.byte2hex(padend);
//		System.out.println(test);
//
//
//
//	}
}
