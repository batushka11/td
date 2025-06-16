package transmisja;

import java.util.LinkedList;

public class Hamming7_4 {
    public static int[] encode(int[] bits){
        LinkedList<Integer> res = new LinkedList<>();
        for (int i = 0; i < bits.length; i+=4) {
            int x1 = bits[i] ^ bits[i+1] ^ bits[i+3];
            int x2 = bits[i] ^ bits[i+2] ^ bits[i+3];
            int x4 = bits[i+1] ^ bits[i+2] ^ bits[i+3];
            res.add(x1);
            res.add(x2);
            res.add(bits[i]);
            res.add(x4);
            res.add(bits[i+1]);
            res.add(bits[i+2]);
            res.add(bits[i+3]);
//            System.out.print("4 bity: ");
//            for (int j = 0; j < 4; j++) {
//                System.out.print(bits[i+j] + " ");
//            }
//            System.out.println();
//            System.out.print("7 bitow: ");
//            for (int j = 0; j < 7; j++) {
//                System.out.print(res.get(i+j) + " ");
//            }
//            System.out.println();
        }
        int[] result = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            result[i] = res.get(i);
        }
        return result;
    }

    public static int[] decode(int[] output){
        int[] result = new int[output.length / 7 * 4];
        for (int i = 0; i < output.length; i+=7) {
            int x1_ = output[i+2] ^ output[i+4] ^ output[i+6];
            int x2_ = output[i+2] ^ output[i+5] ^ output[i+6];
            int x4_ = output[i+4] ^ output[i+5] ^ output[i+6];
            int x1 = output[i] ^ x1_;
            int x2 = output[i+1] ^ x2_;
            int x4 = output[i+3] ^ x4_;
            int S = x1 + x2 * 2 + x4 * 4;
            if(S != 0){
                if(output[i+S-1] == 0){
                    output[i+S-1] = 1;
                }
                else {
                    output[i+S-1] = 0;
                }

            }
            int base = (i / 7) * 4;
            result[base]     = output[i + 2];
            result[base + 1] = output[i + 4];
            result[base + 2] = output[i + 5];
            result[base + 3] = output[i + 6];
        }
        return result;
    }

}
