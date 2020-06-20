/**
 * Java's primitive data types int and long can represent the numbers
 * in the following range.
 * <p>
 * Integer: (-2147483648, 2147483647)
 * Long Integer: (-9223372036854775808, 9223372036854775807)
 * <p>
 * We want to represent numbers that larger than 9223372036854775807.
 * One idea is to use an array to store each digit of the big number.
 * For example: 9223372036854775807 is stored in an array as
 * [9,2,2,3,3,7,2,0,3,6,8,5,4,7,7,5,8,0,7].
 * <p>
 * <p>
 * In this project, you will implement the BigInteger class, which can
 * store any size integer and perform basic arithmetic operations on the
 * number.
 */


import java.util.ArrayList;
import java.util.Arrays;

public class BigInteger {
    
    public static void main(String[] args) {
        BigInteger b = new BigInteger("11234");
        BigInteger b1 = new BigInteger("-12345");
        System.out.println(b.sub(b1));
        b = new BigInteger("1000000000000000000");
        b1 = new BigInteger("999999999999999999");
        System.out.println(b.sub(b1));
        System.out.println(b.add(b1));
    }

    private static final boolean POSITIVE = true;
    private static final boolean NEGATIVE = false;

    /**
     * // this array stores the number. Each digit of the number is an
     * element of the array
     */

    public final int[] number;

    /**
     * sign of this BigInteger. POSITIVE or NEGATIVE
     */

    public final boolean sign;

    /**
     * Default constructor, creates a BigInteger object whose value is 0.
     * and whose sign is positive
     * <p>
     * This constructor will assign sign of the BigInteger
     * and will assign BigInteger array called number to 0
     */

    public BigInteger() {

        number = new int[1];
        this.sign = POSITIVE;
    }

    /**
     * Constructor, creates a BigInteger object with given sign and given array of numbers
     *
     * @param sign  of a number
     * @param array of integers
     *              This constructor will assign sign of the BigInteger
     *              and will copy all elements of the given array into BigInteger array called number
     */

    public BigInteger(boolean sign, int[] array) {
        number = array;
        this.sign = sign;
    }

    /**
     * Create a BigInteger object using the number given as string.
     * If the string starts with "-", it indicates, the number is negative.
     * in this case assign sign to negative and store all numbers from  a String in number array
     * If the string starts with "+", or a digit, the number is positive.
     * in this case assign sign to positive and store all numbers from  a String in number array
     * If the string is null or empty, the number is zero. zero is a positive number.
     * in this case assign sign to positive and store 0 in number array
     * If string contains non-digit characters, throw IllegalArgumentException
     * in this case use the following syntax to throw an exception:
     * throw new IllegalArgumentException();
     *
     * @param strNumber: the number in string format.
     *           For example:
     *           BigInteger b1 = new BigInteger("1234567890");
     *           b1 is a BigInteger with value of 1234567890 stored in number array
     *           BigInteger b2 = new BigInteger("-1234567");
     *           b1 is a BigInteger with value of -1234567 stored in number array
     *           BigInteger b3 = new BigInteger("+123");
     *           b3 is a BigInteger with value of +123 stored in number array
     *           BigInteger b3 = new BigInteger("+123xyz");
     *           throw IllegalArgumentException
     */

    public BigInteger(String strNumber) {
        if (strNumber.isEmpty()) {
            number = new int[1];
            this.sign = POSITIVE;
        } else if (strNumber.startsWith("-")) {
            this.sign = NEGATIVE;
            int[] num = new int[strNumber.length() - 1];//19

            for (int i = 1; i < strNumber.length(); i++) {
                if (Character.isDigit(strNumber.charAt(i))) {
                    num[i - 1] = Integer.parseInt("" + strNumber.charAt(i));
                } else {
                    throw new IllegalArgumentException();
                }
            }
            number = num;


        } else if (strNumber.startsWith("+")) {
            this.sign = POSITIVE;
            int[] num = new int[strNumber.length() - 1];
            for (int i = 1; i < strNumber.length(); i++) {
                if (Character.isDigit(strNumber.charAt(i))) {
                    num[i - 1] = Integer.parseInt("" + strNumber.charAt(i));
                } else {
                    throw new IllegalArgumentException();
                }
            }
            number = num;

        } else {
            this.sign = POSITIVE;
            int[] num = new int[strNumber.length()];
            for (int i = 0; i < strNumber.length(); i++) {
                if (Character.isDigit(strNumber.charAt(i))) {
                    num[i] = Integer.parseInt("" + strNumber.charAt(i));
                } else {
                    throw new IllegalArgumentException();
                }
            }
            number = num;
        }
    }

    /**
     * Create a BigInteger object using another another BigInteger.
     * This constructor will take another BigInteger as a parameter and will copy all the values into
     * this BigInteger and assign the sign
     *
     * @param another: BigInteger object
     *                 BigInteger b = new BigInteger("1234567890")
     *                 <p>
     *                 Create a new BigInteger object using b
     *                 BigInteger b2 = new BigInteger(b)
     */

    public BigInteger(BigInteger another) {

        number = another.number;
        this.sign = another.sign;
    }




    /**
     * Adds two BigIntegers, and creates a new BigInteger with the result of the addition
     * <p>
     * IMPORTANT: Pay attention to the integer sign and length
     * When two positive values are added, the result is positive (2+2=4)
     * When one positive and one negative numbers are added,
     * the result can be negative (-4 + 2 = -2) or positive (-2 + 3 = 1)
     * When two negative values are added, the result is negative (-2 + -2 = -4)
     * When both numbers are zeroes, the result is zero as well
     * <p>
     * Two add two big integers, we cad add each digit at the same index
     * from the two arrays. For example:
     * a:[1,2,3,4]
     * b:[5,6,7,8]
     * a+b is
     * [6,9,1,2]
     *
     * @param b: BigInteger to be added to this BigInteger Object
     * @return: a new BigInteger object, whose value is the sum of this and b, two BigInteger
     * objects
     */


    public BigInteger add(BigInteger b) {
        boolean comp = Arrays.compare(this.toArray(), b.toArray()) >= 0 ? this.sign : b.sign;

        ArrayList<Integer> aList = new ArrayList<>();
        if (this.sign ^ b.sign) {
            int[] a1 = Arrays.compare(this.toArray(), b.toArray()) >= 0 ? this.number : b.number;
            int[] b1 = Arrays.compare(this.toArray(), b.toArray()) < 0 ? this.number : b.number;
            if (a1.length < b1.length) {
                int[] a2 = a1;
                a1 = b1;
                b1 = a2;
                comp = !comp;
            }
            for (int i : a1) {
                aList.add(i);
            }
            for (int i = 0; i < b1.length; i++) {
                aList = subbA(aList, b1[b1.length - 1 - i], aList.size() - 1 - i);
            }
            int[] result = new int[aList.size()];

            for (int i = 0; i < aList.size(); i++) {
                result[i] = aList.get(i);
            }
            return result.length == 0 ? new BigInteger() : new BigInteger(comp, result);
        }
        if (this.sign && b.sign) {
            for (int i : this.number.length >= b.number.length ? this.number : b.number) {
                aList.add(i);
            }
            if (this.number.length >= b.number.length) {
                for (int i = 0; i < b.number.length; i++) {
                    aList = addA(aList, b.number[b.number.length - 1 - i], aList.size() - 1 - i);
                }
            } else {
                for (int i = 0; i < this.number.length; i++) {
                    aList = addA(aList, this.number[this.number.length - 1 - i], aList.size() - 1 - i);
                }
            }
            int[] aaa = new int[aList.size()];
            for (int i = 0; i < aList.size(); i++) {
                aaa[i] = aList.get(i);
            }
            return new BigInteger(true, aaa);
        } else if ((!this.sign) && (!b.sign)) {
            for (int i : this.number.length >= b.number.length ? this.number : b.number) {
                aList.add(i);
            }
            if (this.number.length >= b.number.length) {
                for (int i = 0; i < b.number.length; i++) {
                    aList = subbA(aList, b.number[b.number.length - 1 - i], aList.size() - 1 - i);
                }
            } else {
                for (int i = 0; i < this.number.length; i++) {
                    aList = addA(aList, this.number[this.number.length - 1 - i], aList.size() - 1 - i);
                }
            }
            int[] result = new int[aList.size()];
            for (int i = 0; i < aList.size(); i++) {
                result[i] = aList.get(i);
            }
            return result.length == 0 ? new BigInteger() : new BigInteger(false, result);

        } else {
            return null;
        }
    }

    public BigInteger sub(BigInteger b) {
        String largerNumberNoSign = "";
        largerNumberNoSign += this.compareToNoSign(b) + "," + this.sign + "," + b.sign;
        ArrayList<Integer> aList = new ArrayList<>();
        int[] result ;

        switch(largerNumberNoSign){
            case "1,true,true": for(int i: this.number){aList.add(i);};
                for(int i=0; i<b.number.length;i++){aList = subbA(aList,b.number[b.number.length-1-i],aList.size()-1-i);};
                result = new int[aList.size()];
                for(int i=0; i<aList.size();i++){result[i]=aList.get(i);};
                return new BigInteger(true,result);
            case "1,true,false":for(int i: this.number){aList.add(i);};
                for(int i=0; i<b.number.length;i++){aList = addA(aList,b.number[b.number.length-1-i],aList.size()-1-i);};
                result = new int[aList.size()];
                for(int i=0; i<aList.size();i++){result[i]=aList.get(i);};
                return new BigInteger(true,result);
            case "1,false,true":for(int i: this.number){aList.add(i);};
                for(int i=0; i<b.number.length;i++){aList = addA(aList,b.number[b.number.length-1-i],aList.size()-1-i);};
                result = new int[aList.size()];
                for(int i=0; i<aList.size();i++){result[i]=aList.get(i);};
                return new BigInteger(false,result);
            case "1,false,false":for(int i: this.number){aList.add(i);};
                for(int i=0; i<b.number.length;i++){aList = subbA(aList,b.number[b.number.length-1-i],aList.size()-1-i);};
                result = new int[aList.size()];
                for(int i=0; i<aList.size();i++){result[i]=aList.get(i);};
                return new BigInteger(false,result);
            case "-1,true,true":for(int i: b.number){aList.add(i);};
                for(int i=0; i<this.number.length;i++){aList= subbA(aList,this.number[this.number.length-1-i],aList.size()-1-i);};
                result = new int[aList.size()];
                for(int i=0; i<aList.size();i++){result[i]=aList.get(i);};
                return new BigInteger(false,result);
            case "-1,true,false":for(int i: b.number){aList.add(i);};
                for(int i=0; i<this.number.length;i++){aList= addA(aList,this.number[this.number.length-1-i],aList.size()-1-i);};
                result = new int[aList.size()];
                for(int i=0; i<aList.size();i++){result[i]=aList.get(i);};
                return new BigInteger(true,result);
            case "-1,false,true":for(int i: b.number){aList.add(i);};
                for(int i=0; i<this.number.length;i++){aList= addA(aList,this.number[this.number.length-1-i],aList.size()-1-i);};
                result = new int[aList.size()];
                for(int i=0; i<aList.size();i++){result[i]=aList.get(i);};
                return new BigInteger(false,result);
            case "-1,false,false":for(int i: b.number){aList.add(i);};
                for(int i=0; i<this.number.length;i++){aList= subbA(aList,this.number[this.number.length-1-i],aList.size()-1-i);};
                result = new int[aList.size()];
                for(int i=0; i<aList.size();i++){result[i]=aList.get(i);};
                return new BigInteger(true,result);
            case "0,true,true":return new BigInteger();
            case "0,true,false":for(int i: this.number){aList.add(i);};
                for(int i=0; i<b.number.length;i++){aList = addA(aList,b.number[b.number.length-1-i],aList.size()-1-i);};
                result = new int[aList.size()];
                for(int i=0; i<aList.size();i++){result[i]=aList.get(i);};
                return new BigInteger(true,result);
            case "0,false,true":for(int i: this.number){aList.add(i);};
                for(int i=0; i<b.number.length;i++){aList = addA(aList,b.number[b.number.length-1-i],aList.size()-1-i);};
                result = new int[aList.size()];
                for(int i=0; i<aList.size();i++){result[i]=aList.get(i);};
                return new BigInteger(false,result);
            case "0,false,false":return new BigInteger();
            default: return new BigInteger();
        }
    }

    private ArrayList<Integer> addA(ArrayList<Integer> aList, int carryOver, int index) {
        if (index >= 0) {
            if (carryOver + aList.get(index) < 10) {
                {
                    aList.set(index, carryOver + aList.get(index));
                    return aList;
                }
            } else {
                if (index == 0) {
                    carryOver = carryOver + aList.get(index);
                    aList.set(index, carryOver % 10);
                    aList.add(0, 1);
                    return aList;
                } else {
                    carryOver = carryOver + aList.get(index);
                    aList.set(index, carryOver % 10);
                    return addA(aList, 1, index - 1);
                }
            }
        } else {
            return aList;
        }
    }

    private ArrayList<Integer> subbA(ArrayList<Integer> aList, int carryOver, int index) {

        if (aList.size() == 0 | index < 0) {
            return aList;
        } else {
            carryOver = aList.get(index) - carryOver;
        }
        if (index == 0) {
            if (carryOver == 0) {
                aList.remove(0);
                return subbA(aList, 0, 0);
            } else {

                aList.set(0, carryOver);
                return aList;
            }
        } else if (index > 0) {
            if ((carryOver) < 0) {

                aList.set(index, (10 + carryOver));
                return subbA(aList, 1, index - 1);
            } else {
                aList.set(index, carryOver);
                return aList;
            }
        } else {
            return aList;
        }

    }

    /**
     * Subtracts BigInteger b from this BigInteger, and creates a new BigInteger with
     * the result of the subtraction.
     *
     * @param b: BigInteger to be subtracted from this BigInteger Object
     * @return: a new BigInteger object
     */

       /**   this bigger b smaller & this. positive b.positive ==>> 1,true,true     this. subbA b.    true    this.sign
             this bigger b smaller & this. positive b.negative ==>> 1,true,false    this. addA b.     true    this.sign
             this bigger b smaller & this. negative b.positive ==>> 1,false,true    this. addA b.     false   this.sign
             this bigger b smaller & this. negative b.negative ==>> 1,false,false   this. subbA b.    false   this.sign

             this smaller b bigger & this. positive b.positive ==>> -1,true,true    b. subbA this.    false   !b.sign
             this smaller b bigger & this. positive b.negative ==>> -1,true,false   b. addA this.     true    !b.sign
             this smaller b bigger & this. negative b.positive ==>> -1,false,true   b. addA this.     false   !b.sign
             this smaller b bigger & this. negative b.negative ==>> -1,false,false  b. subbA this.    true    !b.sign

             this equal to b       & this. positive b.positive ==>> 0,true,true     this. subbA b     true    new BigInteger()
             this equal to b       & this. positive b.negative ==>> 0,true,false    this. addA b      true    new BigInteger()
             this equal to b       & this. negative b.positive ==>> 0,false,true    this. addA b      false   new BigInteger()
             this equal to b       & this. neagtive b.negative ==>> 0,false,false   this. subbA b     false   new BigInteger()
*/
    public String toStringNoSign() {
        if (this.toString().startsWith("-") | this.toString().startsWith("+")) {
            return this.toString().substring(1);
        } else {
            return this.toString();
        }
    }


    public int compareToNoSign(BigInteger b) {
        if (this.number.length > b.number.length) {
            return 1;
        } else if (this.number.length == b.number.length) {

            return this.toStringNoSign().compareTo(b.toStringNoSign()) == 0 ?
                    0 : this.toStringNoSign().compareTo(b.toStringNoSign()) > 0 ? 1 : -1;
        }else{
            return -1;
        }
    }

    @Override
    public boolean equals(Object b) {
        return this.compareTo((BigInteger) b) == 0;
    }

    /**
     * Compares this BigInteger with the specified BigInteger.
     *
     * @param b: BigInteger to which this BigInteger is to be compared.
     * @return: -1, 0 or 1 as this BigInteger is numerically less than,
     * equal to, or greater than b
     */

    public int compareTo(BigInteger b) {
        int i = Arrays.compare(this.toArray(), b.toArray()) + this.toString().compareTo(b.toString());
        //TODO
        return i > 0 ? 1 : i == 0 ? 0 : -1;
    }

    /**
     * Returns an int array containing this BigInteger.
     * The array will contain each digit of this BigInteger as an element
     *
     * @return: an int array containing this BigInteger, ignore the sign
     */

    public int[] toArray() {
        return this.number;
    }

    /**
     * Check if the BigInteger is zero
     *
     * @return true if BigInteger value is zero. False otherwise
     */

    public boolean isZero() {
        return this.number.length >= 0 ? true : false;
    }

    /**
     * Returns the length of this BigInteger. length is the number of digits in a BigInteger
     *
     * @return: length of this integer
     */

    public int length() {
        //TODO
        return this.number.length;
    }

    /**
     * Returns the sign of this BigInteger.
     *
     * @return: sign of this integer
     */

    public boolean sign() {
        return this.sign;
    }

    /**
     * returns the the BigInteger in string format. If the number is
     * negative, return string must start with a "-"
     *
     * @return BigInteger in a String format
     */

    public String toString() {
        String result = "";
        if (this.sign) {
            result += "";
        } else {
            result += "-";
        }
        for (int i : this.number) {
            result += i;
        }
        return result;
    }

}
