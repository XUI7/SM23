package sm23.javas.SM2;

import java.math.BigInteger;
import java.security.*;
import org.bouncycastle.*;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.ECFieldElement.Fp;

/**
 * Created by XUI on 2017/4/23.
 */

public class param
{
    //sm2参数
    public final BigInteger sm2_p;
    public final BigInteger sm2_a;
    public final BigInteger sm2_b;
    public final BigInteger sm2_n;
    public final BigInteger sm2_Gx;
    public final BigInteger sm2_Gy;
    //ecc曲线参数
    public final BigInteger ecc_n;
    public final ECFieldElement ecc_gx;
    public final ECFieldElement ecc_gy;
    public final ECCurve ecc_curve;
    public final ECPoint ecc_g;
    public final ECDomainParameters ecc_spec;
    public final ECKeyGenerationParameters ecc_ecgenparam;
    public final ECKeyPairGenerator ecc_kpg;
    public param()
    {
        //sm2参数赋值
        this.sm2_p = new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF",16);
        this.sm2_a = new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC",16);
        this.sm2_b = new BigInteger("28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93",16);
        this.sm2_n = new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123",16);
        this.sm2_Gx = new BigInteger("32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7",16);
        this.sm2_Gy = new BigInteger("BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0",16);
        //官方测试数据
//		this.sm2_p = new BigInteger("8542D69E4C044F18E8B92435BF6FF7DE457283915C45517D722EDB8B08F1DFC3",16);
//		this.sm2_a = new BigInteger("787968B4FA32C3FD2417842E73BBFEFF2F3C848B6831D7E0EC65228B3937E498",16);
//		this.sm2_b = new BigInteger("63E4C6D3B23B0C849CF84241484BFE48F61D59A5B16BA06E6E12D1DA27C5249A",16);
//		this.sm2_n = new BigInteger("8542D69E4C044F18E8B92435BF6FF7DD297720630485628D5AE74EE7C32E79B7",16);
//		this.sm2_Gx = new BigInteger("421DEBD61B62EAB6746434EBC3CC315E32220B3BADD50BDC4C4E6C147FEDD43D",16);
//		this.sm2_Gy = new BigInteger("0680512BCBB42C07D47349D2153B70C4E5D7FDFCBFA36EA1A85841B9E46E09A2",16);

        //定义ecc曲线
        this.ecc_n = sm2_n;
        this.ecc_gx = new Fp(sm2_p, sm2_Gx);
        this.ecc_gy = new Fp(sm2_p, sm2_Gy);
        this.ecc_curve = new ECCurve.Fp(sm2_p, sm2_a, sm2_b);
        this.ecc_g = new ECPoint.Fp(ecc_curve, ecc_gx, ecc_gy);
        this.ecc_spec = new ECDomainParameters(ecc_curve, ecc_g, ecc_n);
        this.ecc_ecgenparam = new ECKeyGenerationParameters(ecc_spec, new SecureRandom());
        this.ecc_kpg = new ECKeyPairGenerator();
        this.ecc_kpg.init(ecc_ecgenparam);
    }
}
