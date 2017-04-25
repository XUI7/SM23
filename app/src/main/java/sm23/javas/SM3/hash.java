package sm23.javas.SM3;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by XUI on 2017/4/23.
 */


/**
 *
 * @author XUI
 *散列部分
 */
public class hash {
    param pa = new param();
    CompressFunc CF = new CompressFunc();


    public byte[] hash(byte[] m) throws IOException
    {
        padding pad = new padding();
        byte[] M = pad.padding(m);
        int n = (M.length * 8)/512; //迭代次数
        byte[] B;
        byte[] VI = pa.IV.toByteArray();//获取初始变量
        byte[] VII = null;
        for(int i = 0; i<n; i++)
        {
            B = Arrays.copyOfRange(M, i * 64, (i + 1) * 64);
            VII = CF.cf(VI,B);
            VI = VII;
            System.out.println("共" + n + "轮压缩，" + "第" + i +"轮压缩后的值:" + pa.byte2string(VII));
        }
        return VII;
    }

}
