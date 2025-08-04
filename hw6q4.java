import java.math.BigInteger;
import java.io.*;

public class hw6q4 {
    
    final static BigInteger one = BigInteger.ONE;
    public static void main(String[] args)
    {
        BigInteger q = new BigInteger("310000037");           //find prime number
        BigInteger g = new BigInteger("52216224");            //generator
        BigInteger Ya = new BigInteger("32298658");           //public key

        BigInteger x = one;                                       //init x for a brute force apporch to find private key(I known its slow but it still works for this question)
        while (true){
            if (g.modPow(x, q).equals(Ya)){                       //if the generator to the power of x is equal to the public key, we found the private key
                System.out.println(x);
                break;
            }
            x = x.add(one);                                       //g to the power of x  mod q =/ public key, so increase x by 1
        }

        //Read the file line by line
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));        //text file with input
            while ((line = br.readLine()) != null) {
                String[] nums = line.split(" ");                                            //trim out spaces
                for (int i =0; i< nums.length; i+=2) {                                            //increment by 2 for each pair
                    BigInteger c1 = new BigInteger(nums[i]);                                
                    BigInteger c2 = new BigInteger(nums[i+1]);
                    BigInteger K = c1.modPow(x, q);                                               //get the K value by get teh value of c1 ^x and modding by q
                    K= K.modInverse(q);                                                           //get the inverse of k mod q
                    BigInteger M = K.multiply(c2);                                                //get the message by multiplying k by c2
                    M = M.mod(q);                                                                 //mod the message to be under q
                    System.out.print(decrypt(M)+" ");                                             //print the decrypted message
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }

   public static String decrypt(BigInteger m){
        BigInteger base = new BigInteger("26");                 //26 letters, 26 base
        char[]plaintext = new char[6];                              //as described in the question there is 6 chars in each int

        for (int i=5;i>=0;i--)                                      //get the chunks or blocks for the decryption
        {
            BigInteger tmp = m.mod(base);                           //a temp value for the message based on the base mod
            plaintext[i] = (char)(tmp.intValue()+'a');              //convert the temp value to a char
            m = m.divide(base);                                     //divide the message by the base
        }
        return new String(plaintext);
    }
    
}
    