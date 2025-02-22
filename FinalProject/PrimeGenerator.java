/*
A library of simple number-theoretic functions underlying the full RSA implementation.
This class contains functions to generate random primes as well as random relatively prime
keys.
 */
import java.util.Random;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.*;

//import static java.math.BigInteger.TWO;
public class PrimeGenerator {


  private BigInteger seed;
      boolean isPrime; 
      private int numBits;
      private int maxNumBits = 512;
      private int minNumBits = 16;
      private int bitRange = maxNumBits - minNumBits;
      //private Vector<Boolean> sieve;
      private int millerRabinIterations =10000;
      static ArrayList<Integer> smallPrimes =  new ArrayList<Integer>(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223, 1229, 1231, 1237, 1249, 1259, 1277, 1279, 1283, 1289, 1291, 1297, 1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367, 1373, 1381, 1399, 1409, 1423, 1427, 1429, 1433, 1439, 1447, 1451, 1453, 1459, 1471, 1481, 1483, 1487, 1489, 1493, 1499, 1511, 1523, 1531, 1543, 1549, 1553, 1559, 1567, 1571, 1579, 1583, 1597, 1601, 1607, 1609, 1613, 1619, 1621, 1627, 1637, 1657, 1663, 1667, 1669, 1693, 1697, 1699, 1709, 1721, 1723, 1733, 1741, 1747, 1753, 1759, 1777, 1783, 1787, 1789, 1801, 1811, 1823, 1831, 1847, 1861, 1867, 1871, 1873, 1877, 1879, 1889, 1901, 1907, 1913, 1931, 1933, 1949, 1951, 1973, 1979, 1987, 1993, 1997, 1999, 2003, 2011, 2017, 2027, 2029, 2039, 2053, 2063, 2069, 2081, 2083, 2087, 2089, 2099, 2111, 2113, 2129, 2131, 2137, 2141, 2143, 2153, 2161, 2179, 2203, 2207, 2213, 2221, 2237, 2239, 2243, 2251, 2267, 2269, 2273, 2281, 2287, 2293, 2297, 2309, 2311, 2333, 2339, 2341, 2347, 2351, 2357, 2371, 2377, 2381, 2383, 2389, 2393, 2399, 2411, 2417, 2423, 2437, 2441, 2447, 2459, 2467, 2473, 2477, 2503, 2521, 2531, 2539, 2543, 2549, 2551, 2557, 2579, 2591, 2593, 2609, 2617, 2621, 2633, 2647, 2657, 2659, 2663, 2671, 2677, 2683, 2687, 2689, 2693, 2699, 2707, 2711, 2713, 2719, 2729, 2731, 2741, 2749, 2753, 2767, 2777, 2789, 2791, 2797, 2801, 2803, 2819, 2833, 2837, 2843, 2851, 2857, 2861, 2879, 2887, 2897, 2903, 2909, 2917, 2927, 2939, 2953, 2957, 2963, 2969, 2971, 2999, 3001, 3011, 3019, 3023, 3037, 3041, 3049, 3061, 3067, 3079, 3083, 3089, 3109, 3119, 3121, 3137, 3163, 3167, 3169, 3181, 3187, 3191, 3203, 3209, 3217, 3221, 3229, 3251, 3253, 3257, 3259, 3271, 3299, 3301, 3307, 3313, 3319, 3323, 3329, 3331, 3343, 3347, 3359, 3361, 3371, 3373, 3389, 3391, 3407, 3413, 3433, 3449, 3457, 3461, 3463, 3467, 3469, 3491, 3499, 3511, 3517, 3527, 3529, 3533, 3539, 3541, 3547, 3557, 3559, 3571, 3581, 3583, 3593, 3607, 3613, 3617, 3623, 3631, 3637, 3643, 3659, 3671, 3673, 3677, 3691, 3697, 3701, 3709, 3719, 3727, 3733, 3739, 3761, 3767, 3769, 3779, 3793, 3797, 3803, 3821, 3823, 3833, 3847, 3851, 3853, 3863, 3877, 3881, 3889, 3907, 3911, 3917, 3919, 3923, 3929, 3931, 3943, 3947, 3967, 3989, 4001, 4003, 4007, 4013, 4019, 4021, 4027, 4049, 4051, 4057, 4073, 4079, 4091, 4093, 4099, 4111, 4127, 4129, 4133, 4139, 4153, 4157, 4159, 4177, 4201, 4211, 4217, 4219, 4229, 4231, 4241, 4243, 4253, 4259, 4261, 4271, 4273, 4283, 4289, 4297, 4327, 4337, 4339, 4349, 4357, 4363, 4373, 4391, 4397, 4409, 4421, 4423, 4441, 4447, 4451, 4457, 4463, 4481, 4483, 4493, 4507, 4513, 4517, 4519, 4523, 4547, 4549, 4561, 4567, 4583, 4591, 4597, 4603, 4621, 4637, 4639, 4643, 4649, 4651, 4657, 4663, 4673, 4679, 4691, 4703, 4721, 4723, 4729, 4733, 4751, 4759, 4783, 4787, 4789, 4793, 4799, 4801, 4813, 4817, 4831, 4861, 4871, 4877, 4889, 4903, 4909, 4919, 4931, 4933, 4937, 4943, 4951, 4957, 4967, 4969, 4973, 4987, 4993, 4999, 5003, 5009, 5011, 5021, 5023, 5039, 5051, 5059, 5077, 5081, 5087, 5099, 5101, 5107, 5113, 5119, 5147, 5153, 5167, 5171, 5179, 5189, 5197, 5209, 5227, 5231, 5233, 5237, 5261, 5273, 5279, 5281, 5297, 5303, 5309, 5323, 5333, 5347, 5351, 5381, 5387, 5393, 5399, 5407, 5413, 5417, 5419, 5431, 5437, 5441, 5443, 5449, 5471, 5477, 5479, 5483, 5501, 5503, 5507, 5519, 5521, 5527, 5531, 5557, 5563, 5569, 5573, 5581, 5591, 5623, 5639, 5641, 5647, 5651, 5653, 5657, 5659, 5669, 5683, 5689, 5693, 5701, 5711, 5717, 5737, 5741, 5743, 5749, 5779, 5783, 5791, 5801, 5807, 5813, 5821, 5827, 5839, 5843, 5849, 5851, 5857, 5861, 5867, 5869, 5879, 5881, 5897, 5903, 5923, 5927, 5939, 5953, 5981, 5987, 6007, 6011, 6029, 6037, 6043, 6047, 6053, 6067, 6073, 6079, 6089, 6091, 6101, 6113, 6121, 6131, 6133, 6143, 6151, 6163, 6173, 6197, 6199, 6203, 6211, 6217, 6221, 6229, 6247, 6257, 6263, 6269, 6271, 6277, 6287, 6299, 6301, 6311, 6317, 6323, 6329, 6337, 6343, 6353, 6359, 6361, 6367, 6373, 6379, 6389, 6397, 6421, 6427, 6449, 6451, 6469, 6473, 6481, 6491, 6521, 6529, 6547, 6551, 6553, 6563, 6569, 6571, 6577, 6581, 6599, 6607, 6619, 6637, 6653, 6659, 6661, 6673, 6679, 6689, 6691, 6701, 6703, 6709, 6719, 6733, 6737, 6761, 6763, 6779, 6781, 6791, 6793, 6803, 6823, 6827, 6829, 6833, 6841, 6857, 6863, 6869, 6871, 6883, 6899, 6907, 6911, 6917, 6947, 6949, 6959, 6961, 6967, 6971, 6977, 6983, 6991, 6997, 7001, 7013, 7019, 7027, 7039, 7043, 7057, 7069, 7079, 7103, 7109, 7121, 7127, 7129, 7151, 7159, 7177, 7187, 7193, 7207, 7211, 7213, 7219, 7229, 7237, 7243, 7247, 7253, 7283, 7297, 7307, 7309, 7321, 7331, 7333, 7349, 7351, 7369, 7393, 7411, 7417, 7433, 7451, 7457, 7459, 7477, 7481, 7487, 7489, 7499, 7507, 7517, 7523, 7529, 7537, 7541, 7547, 7549, 7559, 7561, 7573, 7577, 7583, 7589, 7591, 7603, 7607, 7621, 7639, 7643, 7649, 7669, 7673, 7681, 7687, 7691, 7699, 7703, 7717, 7723, 7727, 7741, 7753, 7757, 7759, 7789, 7793, 7817, 7823, 7829, 7841, 7853, 7867, 7873, 7877, 7879, 7883, 7901, 7907, 7919, 7927, 7933, 7937, 7949, 7951, 7963, 7993, 8009, 8011, 8017, 8039, 8053, 8059, 8069, 8081, 8087, 8089, 8093, 8101, 8111, 8117, 8123, 8147, 8161, 8167, 8171, 8179, 8191, 8209, 8219, 8221, 8231, 8233, 8237, 8243, 8263, 8269, 8273, 8287, 8291, 8293, 8297, 8311, 8317, 8329, 8353, 8363, 8369, 8377, 8387, 8389, 8419, 8423, 8429, 8431, 8443, 8447, 8461, 8467, 8501, 8513, 8521, 8527, 8537, 8539, 8543, 8563, 8573, 8581, 8597, 8599, 8609, 8623, 8627, 8629, 8641, 8647, 8663, 8669, 8677, 8681, 8689, 8693, 8699, 8707, 8713, 8719, 8731, 8737, 8741, 8747, 8753, 8761, 8779, 8783, 8803, 8807, 8819, 8821, 8831, 8837, 8839, 8849, 8861, 8863, 8867, 8887, 8893, 8923, 8929, 8933, 8941, 8951, 8963, 8969, 8971, 8999, 9001, 9007, 9011, 9013, 9029, 9041, 9043, 9049, 9059, 9067, 9091, 9103, 9109, 9127, 9133, 9137, 9151, 9157, 9161, 9173, 9181, 9187, 9199, 9203, 9209, 9221, 9227, 9239, 9241, 9257, 9277, 9281, 9283, 9293, 9311, 9319, 9323, 9337, 9341, 9343, 9349, 9371, 9377, 9391, 9397, 9403, 9413, 9419, 9421, 9431, 9433, 9437, 9439, 9461, 9463, 9467, 9473, 9479, 9491, 9497, 9511, 9521, 9533, 9539, 9547, 9551, 9587, 9601, 9613, 9619, 9623, 9629, 9631, 9643, 9649, 9661, 9677, 9679, 9689, 9697, 9719, 9721, 9733, 9739, 9743, 9749, 9767, 9769, 9781, 9787, 9791, 9803, 9811, 9817, 9829, 9833, 9839, 9851, 9857, 9859, 9871, 9883, 9887, 9901, 9907, 9923, 9929, 9931, 9941, 9949, 9967, 9973));
       
      public static BigInteger zero = new BigInteger("0");
      public static BigInteger one = new BigInteger("1");
      public static BigInteger two = new BigInteger("2");
      public static BigInteger three = new BigInteger("3");
      public BigInteger four = new BigInteger("4");
      public BigInteger five = new BigInteger("5");
      public BigInteger six = new BigInteger("6");
      public BigInteger seven = new BigInteger("7");
      public BigInteger eight = new BigInteger("8");
      public BigInteger nine = new BigInteger("9");
      public BigInteger ten = new BigInteger("10");
      public BigInteger error = new BigInteger("-1");
      public BigDecimal pointFive = new BigDecimal("0.5");
      public BigDecimal zeroDec = new BigDecimal("0");
      public MathContext defaultContext = new MathContext(10); //10 decimal digits
      public BigInteger nextPrimeOffset = new BigInteger("2147483647");
  //boolean[] sieve;
        
        public int generateNumBits() {
            int numBits = (int)(Math.random() * bitRange) + minNumBits;
            return numBits;
        }
        public static BigInteger modulus(BigInteger p, BigInteger q) {
        BigInteger quotient = p.divide(q); //stays as int
        BigInteger remainder = p.subtract(quotient.multiply(q));
        return remainder;

       }
        public int generateNumBitsInRange(int lower, int upper) {
            int numBits = (int)(Math.random() * (upper - lower)) + lower;
            return numBits;
         }
        public PrimeGenerator(BigInteger s) {
          this.seed = s;
          this.numBits = generateNumBits();
                      
          //this.sieve = new boolean[s - 2];
        }
        public static BigInteger uniformRandom(BigInteger lowerLimit, BigInteger upperLimit) {
           int maxBits = upperLimit.bitLength();
           Random randNumSeed = new Random();
           BigInteger toReturn;
           do {
               toReturn = new BigInteger(maxBits, randNumSeed);
               randNumSeed = new Random();
           } while (toReturn.compareTo(lowerLimit) < 0 && toReturn.compareTo(upperLimit) > 0);
           
           return toReturn;
        }
        
        public PrimeGenerator(Random r) {
            this.seed = BigInteger.valueOf(r.nextInt());
            this.numBits = Integer.numberOfLeadingZeros(seed.intValue());
        }
        public PrimeGenerator() {
            Random r = new Random();
            this.seed = BigInteger.valueOf(r.nextInt());
            this.numBits = Integer.numberOfLeadingZeros(seed.intValue());
        }
       public BigInteger getSeed() {
            return this.seed;
        }
       public String toByteString(BigInteger toComplement) {
          String byteStr = "";
          while (!toComplement.equals(zero)) {
              BigInteger testNum = toComplement.mod(two);
              if (testNum.equals(one)){
                  byteStr = "1" + byteStr;
              }
              else {
                  byteStr = "0" + byteStr;
              }
              toComplement = toComplement.divide(two);
          }
          return byteStr;
       }
       public static BigInteger modExp(BigInteger argument, BigInteger exponent, BigInteger mods) {
           BigInteger a = argument;
           BigInteger e = exponent;
           //BigInteger m = mods;
           BigInteger y = one;
           while (!e.equals(zero)) {
               if (isEven(e)) {
                   a = a.multiply(a).mod(mods);
                   e = e.divide(two);               
               }
               else {
                   // System.out.println(mods);
                    y = a.multiply(y).mod(mods);
                    e = e.subtract(one);
               }
           
           }
           return y;
       }
                
          //not very efficient for large powers of two
          public int largestPowerOfTwo(BigInteger nValue) {   
                    int current_exponent = 0;                    
                        while (isEven(nValue)) {
                            current_exponent += 1;
                            nValue = nValue.divide(two);
                        }
                        return current_exponent; //only needs to be an intege  
          }

                //odd coefficient for miller rabin 
          public BigInteger oddCoefficient(int powerOfTwo, BigInteger nValue) {
                  //  int nVal = n.intValue();
                    int expOfTwo =  (int)(Math.pow(2, (double)powerOfTwo)); //integer of 2^n
                    BigInteger bigIntExpOfTwo = new BigInteger(expOfTwo + "");
                    BigInteger result = nValue.divide(bigIntExpOfTwo);
                   // return BigInteger.valueOf(nVal);
                    return new BigInteger(""+result);
                    
          }
          public BigInteger exponentiate(BigInteger argument, BigInteger exponent) {
              if (lessThan(exponent,zero)) {
                  return exponentiate(one.divide(argument), exponent);
              }
              else if (exponent.equals(zero)) {
                  return one;
              }
              else if (isEven(exponent)) {
                  return exponentiate(argument.multiply(argument), exponent.divide(two));
              }
              else {
                  return argument.multiply(exponentiate(argument.multiply(argument),(exponent.subtract(one)).divide(two)));
              }
          }
          public static boolean isDivisibleBySmallPrime(BigInteger testPrime) {
              for (int i = 0; i < smallPrimes.size(); i++) {
                  if ((testPrime.mod(BigInteger.valueOf(smallPrimes.get(i)))).equals(zero) && !testPrime.equals(BigInteger.valueOf(smallPrimes.get(i)))) {
                      return true;
                  }
              }
              return false; //not divisible by any small prime
          }
       
       public BigInteger generateRandomOddOfSize(int numBits) {
           Random rand = new Random();         
           BigInteger prospectiveOdd = new BigInteger(numBits, rand);
           if (isEven(prospectiveOdd)) {
               prospectiveOdd =prospectiveOdd.add(one); //if it's even, add one 
           }
           return prospectiveOdd;
       }
       public BigInteger generateRandomEven() {
           Random rand = new Random();         
           BigInteger prospectiveEven = new BigInteger(numBits, rand);
           if (isEven(prospectiveEven)) {
               return prospectiveEven;
           }
           return prospectiveEven.add(one);
       }
       public BigInteger generateRandomOdd() {
           int numBits = generateNumBits();
           Random rand = new Random();         
           BigInteger prospectiveOdd = new BigInteger(numBits, rand);
           if (isEven(prospectiveOdd)) {
               prospectiveOdd =prospectiveOdd.add(one); //if it's even, add one to make it odd
           }
           return prospectiveOdd;
       }
        public BigInteger generateRandomOddOfBitSize(int n) {      
           Random rand = new Random();         
           BigInteger prospectiveOdd = new BigInteger(n, rand);
           if (isEven(prospectiveOdd)) {
               prospectiveOdd =prospectiveOdd.add(one); //if it's even, add one to make it odd
           }
           return prospectiveOdd;
       }
       public BigInteger generateOddInRange(BigInteger upperLimit) {
           Random rand = new Random();         
           BigInteger prospectiveOdd = generateRandomOddOfBitSize(upperLimit.bitCount());
           //System.out.println("should have bit count of"  + upperLimit.bitCount());
           while (prospectiveOdd.compareTo(upperLimit) >= 0) {
               prospectiveOdd.subtract(two);
           }
           return prospectiveOdd;
       }


       public BigInteger bigIntEucDiv(BigInteger p, BigInteger q) {
        BigInteger quotient = p.divide(q); //stays as int
       // BigInteger remainder = p.subtract(quotient);
        return quotient;
       }
       
       public static boolean isEven(BigInteger testEven) {
           return (modulus(testEven,two).equals(zero) && !testEven.equals(two));
       }

     
       public static  boolean divisibleByFive(BigInteger testPrime) {
           if (testPrime.equals(BigInteger.valueOf(5))) return false;
           String number = testPrime.toString();
           char finalDigit = number.charAt(number.length() - 1);
           return (finalDigit == 53 || finalDigit == 48);
       }
       public static boolean divisibleByThree(BigInteger testPrime) {
           if (testPrime.equals(BigInteger.valueOf(3))) return false;
           String number = testPrime.toString();
           Integer sum = 0;
           for (int i = 0; i < number.length(); i++) {
               sum += Character.getNumericValue(number.charAt(i));
           }
           return (sum % 3 == 0);
       }
       public static boolean divisibleByNine(BigInteger testPrime) {
           if (testPrime.equals(BigInteger.valueOf(9))) return false;
           String number = testPrime.toString();
           Integer sum = 0;
           for (int i = 0; i < number.length(); i++) {
               sum += Character.getNumericValue(number.charAt(i));
           }
           return (sum % 9 == 0);
       }
       public static  boolean divisibleByEleven(BigInteger testPrime) {
           if (testPrime.equals(BigInteger.valueOf(11))) return false;
           String number = testPrime.toString();
           Integer sum = 0;
           for (int i = 0; i < number.length(); i++) {
               sum += (Character.getNumericValue(number.charAt(i))*(int)Math.pow(-1,i));
           }
           return (sum % 11 == 0);  
       }
       public BigInteger inverseModuloQ(BigInteger p, BigInteger q) {
        BigInteger temp;
        BigInteger t = zero;
        BigInteger new_t = one;
        BigInteger r = q;
        BigInteger new_r = p;
        BigInteger quotient;
        while (!new_r.equals(zero)) {
              quotient = bigIntEucDiv(r, new_r);
              temp = new_t;
              new_t = t.subtract(quotient.multiply(temp));    
              t = temp;
              
              temp = new_r;
              new_r = r.subtract(quotient.multiply(temp));    
              r = temp;
              
        }
        if (greaterThan(r,one)) {
          return error;
        }
        if (lessThan(t,zero)) {
          t.add(t).add(q); //get least positive residue
        }
        return t;
       }
       	//to_return 0 is s, 1 is t, 2 is GCD
	static BigInteger[] extended_Euclidean(final BigInteger _a, final BigInteger _b)
	{
		BigInteger[] to_return = new BigInteger[3];
		BigInteger old_r = _a;
		BigInteger r = _b;
		BigInteger s = new BigInteger("0");
		BigInteger t = new BigInteger("1");
		BigInteger old_s = new BigInteger("1");
		BigInteger old_t = new BigInteger("0");
		while(!r.equals(BigInteger.ZERO))
		{
			BigInteger quotient = old_r.divide(r);
			BigInteger buff = r;
			r = old_r.subtract(quotient.multiply(buff));
			old_r = buff;
			buff = s;
			s = old_s.subtract(quotient.multiply(buff));
			old_s = buff;
			buff = t;
			t = old_t.subtract(quotient.multiply(buff));
			old_t = buff;
		}

		to_return[0] = old_s;
		to_return[1] = old_t;
		to_return[2] = old_r;
		return to_return;
	}

        	public static BigInteger modInverse(BigInteger a, BigInteger b) throws ArithmeticException
	{
		BigInteger[] result = extended_Euclidean(a, b);
		if (result[2].equals(BigInteger.ONE))
		{
			return result[0].mod(b);
		}
		else
		{
			throw new ArithmeticException("GCD does not equal 1");
		}
	}
       public BigInteger successiveSquareOf(BigInteger base, BigInteger modulus) {
           return modExp(base,two,modulus);
       }
       
       public boolean lessThan(BigInteger argA, BigInteger argB) {
           return (argA.compareTo(argB) < 0);
       }
        public boolean greaterThan(BigInteger argA, BigInteger argB) {
           return (argA.compareTo(argB) > 0);
       }
         public boolean lessThanOrEqualTo(BigInteger argA, BigInteger argB) {
           return (argA.compareTo(argB) <= 0);
       }
         public boolean greaterThanOrEqualTo(BigInteger argA, BigInteger argB) {
           return (argA.compareTo(argB) >= 0);
       }

         public String bytesToHex(byte[] bytes) { //converts byte array to Hex string 
		
            return String.format("%040x", new BigInteger(1, bytes));
	}
	
	public String fromHex(String hex) { //designed to convert hex string to message string
	    StringBuilder str = new StringBuilder();
	    for (int i = 0; i < hex.length(); i+=2) {
	        str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
	    }
	    return str.toString();
	}
         

       
      
	public boolean millerRabinIsPrime(BigInteger n, int k) {
			if (n.compareTo(three) < 0)
			return true;
		int s = 0;
		BigInteger d = n.subtract(one);
		while (d.mod(two).equals(zero)) {
			s++;
			d = d.divide(two);
		}
		for (int i = 0; i < k; i++) {
			BigInteger a = uniformRandom(two, n.subtract(one));
			BigInteger x = a.modPow(d, n);
			if (x.equals(one) || x.equals(n.subtract(one)))
				continue;
			int r = 1;
			for (; r < s; r++) {
				x = x.modPow(two, n);
				if (x.equals(one))
					return false;
				if (x.equals(n.subtract(one)))
					break;
			}
			if (r == s) // None of the steps made x equal n-1.
				return false;
		}
		return true;
	}


       public boolean smallIsPrime(BigInteger testPrime) {
           if (isEven(testPrime) && !testPrime.equals(two)) {
               return false;
           }
           else if (testPrime.mod(three).equals(zero) && !testPrime.equals(three)) {
               return false;
           }
           else if (testPrime.equals(zero) || testPrime.equals(one)) {
               return false;
           }
           else if (testPrime.mod(five).equals(zero) && !testPrime.equals(five)) {
               return false;
           }
           else if (testPrime.mod(seven).equals(zero) && !testPrime.equals(seven)) {
               return false;
           }
                  
           else {
               return true;
           }
       }

	public boolean isCompositeWitness(BigInteger base, BigInteger modulus) {
		// int localNumIterations = millerRabinIterations;
		BigInteger modulusMinusOne = modulus.subtract(one);
		//if (!initialIsPrime(base,modulus)) {
		boolean isComposite;
		boolean isPossiblePrime;

		//decompose n - 1
		int powerOfTwo = largestPowerOfTwo(modulusMinusOne);
		BigInteger oddCoefficient = oddCoefficient(powerOfTwo,modulusMinusOne);
		BigInteger oldTestWitness = modExp(base,oddCoefficient,modulus);
		isPossiblePrime = (oldTestWitness.equals(one));
		if (isPossiblePrime) {
			return false;
		}
		witnessLoop:
			while (powerOfTwo != 0) {  
				powerOfTwo--;
				BigInteger newTestWitness = successiveSquareOf(oldTestWitness,modulus);
				isComposite = newTestWitness.equals(one); //case a^2n congr to 1 mod m
				isPossiblePrime = (oldTestWitness.equals(modulusMinusOne) || oldTestWitness.equals(one));

				//isComposite = !testWitness.equals(one);
				if (isComposite && !isPossiblePrime) return isComposite;        
				else oldTestWitness = newTestWitness;            
			}
		return !oldTestWitness.equals(one);
		//return !isPossiblePrime; 
		//return isComposite;
	}

          
      public BigInteger generatePrimeOfSize(int numBits) {
         // Random primeGeneratorSeed = new Random();
          if (numBits < 32) {
              return error;
          }
          BigInteger potentialPrime = generateRandomOddOfSize(numBits);
          while(!isProbablyPrime(potentialPrime)) {
              potentialPrime = potentialPrime.add(two); //time-save; don't test evens
          
          }
          return potentialPrime;
      }
      
      public BigInteger generatePrime() {
          int numBits = generateNumBits();
          BigInteger potentialPrime = generateRandomOdd();
          while(!isProbablyPrime(potentialPrime)) {
              potentialPrime = potentialPrime.add(two); //time-save; don't test evens          
          }
          return potentialPrime; 
      }
       //for safety, make sure upper and lower lims are at least 100 apart
      public BigInteger generatePrimeInRange(BigInteger upperLimit) {
            BigInteger potentialPrime = generateOddInRange(upperLimit);
            while(!isProbablyPrime(potentialPrime)&& lessThan(potentialPrime,upperLimit)) {
                potentialPrime = potentialPrime.add(two); //time-save; don't test evens
            }
            return potentialPrime;
      }
      

  public static boolean witnessTest(BigInteger n, BigInteger n1, BigInteger d, int s, int iter)
  {
    
    Random r = new Random(); //prepare randoms
    
    for(int i = 0; i < iter; i++) //1000 iterations
    {
      //int a = r.nextInt((n.intValue()-2 - 2)+1) + 2; //[2, n-2]
        
      //BigInteger A = new BigInteger(Integer.toString(a));
        BigInteger A = uniformRandom(two,n.subtract(two));
      BigInteger x = modExp(A, d, n);
      
      if(x.equals(BigInteger.ONE) || x.equals(n1))
        return witnessTest(n, n1, d, s, iter-i-1); //ok, move on
      
      for(int j = 0; j < s-1; j++)
      {
        x = modExp(x, new BigInteger("2"), n); //sucessive square
       // System.out.println(x + " is possibly composite; it's the successive square");
        if(x.equals(BigInteger.ONE)) return false;
        if(x.equals(n1)) return witnessTest(n, n1, d, s, iter-i-1);
      }
      return false; //composite
    }
    return true; //probably prime
  }

            public static byte[] trim(byte[] bytes)
          {
        	  int i = bytes.length - 1;
        	  while (i >= 0 && bytes[i] == 0)
        	  {
        		  --i;
        	  }

        	  return Arrays.copyOf(bytes, i + 1);
          }
  // Miller-Rabin implementation for primality test: Katherine Davis and Sarah Sahibzada (minor debug)
  public static boolean isProbablyPrime(BigInteger n)
  {
      
   /* if (divisibleByThree(n) || divisibleByFive(n) || isEven(n) || divisibleByEleven(n)) {
        return false;
    }*/
    /*if (isDivisibleBySmallPrime(n)) {
        return false; //not divisible by a small prime
    }*/
    
    
    BigInteger n1 = n.subtract(BigInteger.ONE); //n-1
    
    int s = 0;
    BigInteger two = new BigInteger("2");
    BigInteger d = n1;
    
    while(d.mod(two).equals(BigInteger.ZERO)) //divisible by 2
    {
      d = d.divide(two);
      s += 1; //increment
    }
    // n1 = 2^s * d
    
    return witnessTest(n, n1, d, s, 1000);
  }

      

    public BigInteger randomCoprimeTo(BigInteger upperLimit) {
      //from one to upper limit; in RSA, this will be phi(pq)
      int numBits = upperLimit.bitCount();
      Random r = new Random();
      BigInteger potentialCoprime = new BigInteger(numBits, r);
      while (potentialCoprime.compareTo(upperLimit) >= 0) {
          r = new Random();
          potentialCoprime = new BigInteger(numBits, r);
      }
      while (!isBigIntRelPrime(potentialCoprime,upperLimit)) {
          //System.out.println("testing");
          potentialCoprime = potentialCoprime.add(one);
      }
      return potentialCoprime;
      }
    public BigInteger toBigInt(String byteString)
	{
		BigInteger result = new BigInteger(byteString, 2);

		return result;
	}

      //for keys
       public boolean isIntRelPrime(int a, int b) {
           return (intGCD(a,b) == 1);
       }
       public boolean isBigIntRelPrime(BigInteger a, BigInteger b) {         
           return (bigIntGCD(a,b).equals(one));
       }
       public BigDecimal smallSquare(BigDecimal smallBase) {
           return smallBase.multiply(smallBase);
       }
       public BigDecimal smallDerivOfSquare(BigDecimal smallBase) {
           return smallBase.multiply(new BigDecimal(two));
       }
       
       //to facilitate sqroot
       public BigInteger squareBigInt(BigInteger toSquare) {
           return toSquare.multiply(toSquare);
       }
       public BigInteger twoTimesBigInt(BigInteger toDouble) {
           return two.multiply(toDouble);
       }
       public BigInteger newtonRaphsonSquareRoot(BigInteger currentBase) {
           int terminationCondition;
           /*
            "Speed up the algorithm by proper selection of an initial approx
           As a square root has 2x less digits as orig value the below heuristic is ok
           http://stackoverflow.com/questions/13649703/square-root-of-bigdecimal-in-java
           **NEWTON RAPHSON IMPLEMENT IS MINE!! THE HEURISTIC IS NOT!!!**
           */
           BigInteger initApprox = two.pow(currentBase.bitLength()/2); //our approx
           BigInteger init = currentBase;
           BigInteger newApprox = initApprox; //x_n+1
           BigInteger prevApprox = currentBase; //x_n
           do {
               newApprox  = prevApprox.subtract(squareBigInt(prevApprox).divide(twoTimesBigInt(prevApprox))); //mine
               terminationCondition = prevApprox.compareTo(newApprox);
               prevApprox = newApprox; //to start the loop over        
           } while (terminationCondition != 0);
           return newApprox;
       }

       /*public BigInteger squareRootInteger(BigInteger integerTest) {
       
       }*/
       //sieve of atkin, a second primality test (for keys)
       //note that we want to set a lower limit for the added security of
       //starting with a large public key like 65537
     /*  public BigInteger sieveOfAtkin(BigInteger lowerLimit) {
           Vector<Boolean> sieveVector;
           
           /*if (upperLimit.bitCount() >= 31) {
                sieveVector = new Vector<Boolean>(2147483647);
                sieveVector = fillVector(sieveVector,false);
                
           }
           else {
               sieveVector = new Vector<Boolean>((int)Math.pow(2, upperLimit.bitCount()));
               sieveVector = fillVector(sieveVector,false);
           }
           BigInteger limitSqrt =  newtonRaphsonSquareRoot(upperLimit);
           */
           
       
       
       //using bitwise operations instead
       //sieve of eratosthenes for 
   
       public int intGCD(int p, int q) {
           int remainder; //remainder in successive division in euclidean alg
           int temp; //placeholder value for swap ops
           //swap p and q to ensure that p > q
           if (p < q) {
               temp = p;
               p = q;
               q = temp;
           }           
           else if (p == q) {
               return p;
           }    
           else {
              while (q != 0) {
                    temp = q;
                    q = p % q;
                    p = temp;
                }
           }  
           return p;
       }
       
       public BigInteger bigIntGCD(BigInteger p, BigInteger q) {
           BigInteger remainder;
           BigInteger temp;
           if (p.equals(q)) {
               return p;
           }
           
           else if (p.compareTo(q) < 0) { //p needs to be the bigger one
               temp = p;
               p = q;
               q = temp;   
           }
           if (q.equals(zero)) {
             return p;               
           }
           else {
               return bigIntGCD(q, p.mod(q));
           }
       }   
       /*
        ONLY FOR PRIMES RN
       */
       public BigInteger phiOfPrimes(BigInteger prime1, BigInteger prime2) {
           return prime1.subtract(one).multiply(prime2.subtract(one));
       }
                   
  
}