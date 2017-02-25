package ESC.Week5;

import java.math.BigInteger;

/**
 * Created by HanWei on 20/2/2017.
 */
public class FactorPrime {

    public static void main(String[] args) {
        System.out.println(isSemiPrime(new BigInteger("4294967297")));
        System.out.println(isSemiPrime(new BigInteger("1127451830576035879")));
        System.out.println(isSemiPrime(new BigInteger("160731047637009729259688920385507056726966793490579598495689711866432421212774967029895340327197901756096014299132623454583177072050452755510701340673282385647899694083881316194642417451570483466327782135730575564856185546487053034404560063433614723836456790266457438831626375556854133866958349817172727462462516466898479574402841071703909138062456567624565784254101568378407242273207660892036869708190688033351601539401621576507964841597205952722487750670904522932328731530640706457382162644738538813247139315456213401586618820517823576427094125197001270350087878270889717445401145792231674098948416888868250143592026973853973785120217077951766546939577520897245392186547279572494177680291506578508962707934879124914880885500726439625033021936728949277390185399024276547035995915648938170415663757378637207011391538009596833354107737156273037494727858302028663366296943925008647348769272035532265048049709827275179381252898675965528510619258376779171030556482884535728812916216625430187039533668677528079544176897647303445153643525354817413650848544778690688201005274443717680593899")));
    }

    public static String isSemiPrime(BigInteger number) {
        BigInteger den = number.divide(BigInteger.valueOf(2));
        BigInteger num;
        if (den.multiply(BigInteger.valueOf(2)).equals(number)) {
            return "Two primes of " + number.toString() + " are " + 2 + " and " + den.toString();
        }
        for (long i = 3; i < sqrtBigInt(number).longValue() / 2; i += 2) {
            System.out.println(i);
            den = BigInteger.valueOf(i);
            num = number.divide(den);
            if (num.multiply(den).equals(number)) {
                return "Two primes of " + number.toString() + " are " + num.toString() + " and " + den.toString();
            }
        }
        return "No such primes exist.";
    }

    static BigInteger sqrtBigInt(BigInteger n) {
        BigInteger a = BigInteger.ONE;
        BigInteger b = n.shiftRight(5).add(BigInteger.valueOf(8));
        while (b.compareTo(a) >= 0) {
            BigInteger mid = a.add(b).shiftRight(1);
            if (mid.multiply(mid).compareTo(n) > 0) {
                b = mid.subtract(BigInteger.ONE);
            } else {
                a = mid.add(BigInteger.ONE);
            }
        }
        return a.subtract(BigInteger.ONE);
    }
}
