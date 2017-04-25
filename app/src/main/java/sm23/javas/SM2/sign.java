package sm23.javas.SM2;

import java.io.IOException;
import java.math.BigInteger;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;
import sm23.javas.SM3.*;

/**
 * Created by XUI on 2017/4/23.
 */

public class sign {
    static sm23.javas.SM3.param pa3 = new sm23.javas.SM3.param();
    static sm23.javas.SM3.hash ha = new sm23.javas.SM3.hash();
    static sm23.javas.SM2.param pa2 = new param();

    /**
     *  bigint  to  byte[]
     */
    public static byte[] bigint2bytes(BigInteger n)
    {
        byte tmpd[] = (byte[])null;
        if(n == null)
        {
            return null;
        }

        if(n.toByteArray().length == 33)
        {
            tmpd = new byte[32];
            System.arraycopy(n.toByteArray(), 1, tmpd, 0, 32);
        }
        else if(n.toByteArray().length == 32)
        {
            tmpd = n.toByteArray();
        }
        else
        {
            tmpd = new byte[32];
            for(int i = 0; i < 32 - n.toByteArray().length; i++)
            {
                tmpd[i] = 0;
            }
            System.arraycopy(n.toByteArray(), 0, tmpd, 32 - n.toByteArray().length, n.toByteArray().length);
        }
        return tmpd;
    }

    /**
     * 字符串拼接
     */
    public static byte[] joint(byte[] a,byte[]b)
    {
        byte[] c = new byte[a.length+b.length];
        System.arraycopy(a,0,c,0,a.length);
        System.arraycopy(b,0,c,a.length,b.length);
        return c;
    }

    /**
     * int 转 byte[]
     */
    public static byte[] int2Bytes(int value)
    {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24) & 0xFF);
        src[1] = (byte) ((value>>16)& 0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     *计算ZA
     */
    public static byte[] getZa(byte[] IDA, ECPoint PA) throws IOException
    {
//		SM3.hash ha = new hash();
        byte[] za,hza;
        int len = IDA.length * 8;
        za = new byte[2];
        za[0] = (byte) (len>>8);
        za[1] = (byte)(len);
        System.out.println("ENTLA：" + pa3.byte2string(za));
        System.out.println();
        za = joint(za,IDA);
        za = joint(za, bigint2bytes(pa2.sm2_a));
        za = joint(za, bigint2bytes(pa2.sm2_b));
        za = joint(za, bigint2bytes(pa2.sm2_Gx));
        za = joint(za, bigint2bytes(pa2.sm2_Gy));
        za = joint(za, bigint2bytes(PA.getX().toBigInteger()));
        za = joint(za, bigint2bytes(PA.getY().toBigInteger()));
        System.out.println("签名者信息拼接：" + pa3.byte2string(za));
        System.out.println();
        hza = ha.hash(za);
        //System.out.println("Za的值为："+ pa3.byte2hex(hza));
        return hza;
    }

    public static boolean testmes(byte[] a, byte[] b, byte[] c)
    {
        if(a == null || a.length == 0)
        {
            System.out.println("请提供个人ID");
            return false;
        }
        if (b == null || b.length == 0)
        {
            System.out.println("请提供私钥来签名");
            return false;
        }
        if (c == null || c.length == 0)
        {
            System.out.println("请输入需要签名的消息");
            return false;
        }
        return true;
    }


    public static BigInteger[] ToSign(byte[] userID, byte[] dA, byte[] M) throws IOException
    {
        //判断输入数据是否正确
        if(!testmes(userID, dA, M))
        {
            System.out.println("please check your massages!");
            return null;
        }
        //初始化用户A的原始数据
        BigInteger id_a = new BigInteger(userID);
        System.out.println("用户ID为："+id_a.toString());
        System.out.println();
        System.out.println("ASCII编码记IDA：" + pa3.byte2string(userID));
        System.out.println();
        BigInteger da = new BigInteger(dA);
        System.out.println("私钥dA："+da.toString(16));
        System.out.println();
        byte[] m = M;
        System.out.println("待签名的消息为："+ new BigInteger(m).toString(16));
        System.out.println();
        //椭圆曲线参数
        sm23.javas.SM2.param sm2pa = new sm23.javas.SM2.param();
        //用户A的公钥（即点pa = [da]G = （xa,ya))
        ECPoint pa = sm2pa.ecc_g.multiply(da);
        System.out.println("公钥Pa-xa：" + pa.getX().toBigInteger().toString(16));
        System.out.println();
        System.out.println("公钥Pa-ya：" + pa.getY().toBigInteger().toString(16));
        System.out.println();
        //获取ZA
        byte[] za = getZa(userID, pa);
        System.out.println("za散列值：" + pa3.byte2string(za));
        System.out.println();
        //求取M_
        sm23.javas.SM3.padding jo = new sm23.javas.SM3.padding();
        byte[] m_ = jo.joint(za, m );
        System.out.println("M_：" + pa3.byte2string(m_));
        System.out.println();
        //求取e
        byte[] e = ha.hash(m_);
        System.out.println("e：" + pa3.byte2string(e));
        System.out.println();
        //计算r、s
        BigInteger k;
        ECPoint x1y1;
        BigInteger r = new BigInteger(1,e);
        BigInteger s;
        do
        {
            do	//计算r
            {
                //随机数k
                AsymmetricCipherKeyPair keypair = pa2.ecc_kpg.generateKeyPair();
                ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) keypair.getPrivate();
                ECPublicKeyParameters ecpub = (ECPublicKeyParameters) keypair.getPublic();
                k = ecpriv.getD();
                //椭圆曲线点（x1,y1)
                x1y1 = ecpub.getQ();

                //官方测试
//				String ks = "6CB28D99385C175C94F94E934817663FC176D925DD72B727260DBAAE1FB2F96F";
//				k = new BigInteger(ks,16);
                System.out.println("随机数k：" + k.toString(16));
                System.out.println();
//				x1y1 = pa2.ecc_g.multiply(k);
                System.out.println("椭圆曲线点(x1 ,y1 )=[k]G--坐标x1：" + x1y1.getX().toBigInteger().toString(16));
                System.out.println("椭圆曲线点(x1 ,y1 )=[k]G--坐标y1：" + x1y1.getY().toBigInteger().toString(16));
                System.out.println();
                r = r.add(x1y1.getX().toBigInteger());
                r = r.mod(pa2.ecc_n);
            }while(r.equals(BigInteger.ZERO) || r.add(k).equals(pa2.ecc_n));
            System.out.println("r：" + r.toString(16));
            //计算s
            BigInteger s1 = da.add(BigInteger.ONE);
            s1 = s1.modInverse(pa2.ecc_n);
            BigInteger s2 ;
            s2 = r.multiply(da);
            s2 = k.subtract(s2).mod(pa2.ecc_n);
            s = s1.multiply(s2).mod(pa2.ecc_n);
        }while(s.equals(BigInteger.ZERO));
        System.out.println("s：" + s.toString(16));
        BigInteger mmm = new BigInteger(M);
        BigInteger[] result = new BigInteger[]{mmm,r,s};
        return result;
    }

}
