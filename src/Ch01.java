import java.util.*;
import java.util.stream.Collectors;

class StepCh01 {

    public static List<List<String>> processKey(Integer index) {
        String key = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
        String[] keyChar = key.split(",");
        List<String> keyCharList = Arrays.stream(keyChar).collect(Collectors.toList());

        List<String> aKey = Collections.unmodifiableList(keyCharList);;
        List<String> bKey = new ArrayList<>();

        if (index == 0) {
            bKey = keyCharList;
        } else if (index > 0) {
            List<String> tempLastKey = new ArrayList<>();
            for (int i = 0; i < index; i++) {
                tempLastKey.add(keyCharList.get(i));
            }

            for (int z = index; z < keyCharList.size(); z++) {
                bKey.add(keyCharList.get(z));
            }
            bKey.addAll(tempLastKey);

        } else {
            List<String> tempBKey = keyCharList.subList(0, keyCharList.size()-1 + index+1);
            List<String> tempFirstKey = keyCharList.subList(keyCharList.size()-1 + index+1, keyCharList.size());
            Queue<String> queueKey = new LinkedList<>(tempFirstKey);

            queueKey.addAll(tempBKey);
            bKey = queueKey.stream().collect(Collectors.toList());

        }

        List<List<String>> endOfKey = new ArrayList<>();
        endOfKey.add(aKey);
        endOfKey.add(bKey);
        return endOfKey;

    }
    public static String encryption(String plaintext) {
        List<String> partPlainText= plaintext.chars().mapToObj(ch -> (char) ch)
                .map(character -> character.toString())
                .collect(Collectors.toList());

        List<List<String>> key = processKey(2);

        List<Integer> storeIndex = new ArrayList<>();
        for (int i = 0; i < partPlainText.size(); i++) {
            if (!partPlainText.get(i).equals(" ")) {
                storeIndex.add(key.get(0).indexOf(partPlainText.get(i).toUpperCase()));
            } else {
                storeIndex.add(-1);
            }
        }

        List<String> partChiper = new ArrayList<>();
        for(Integer realIndexKey : storeIndex) {
            if (!realIndexKey.equals(-1)) {
                String partOfKey = key.get(1).get(realIndexKey);
                partChiper.add(partOfKey);
            } else {
                partChiper.add("X");
            }
        }

        return String.join("", partChiper);
    }

    public static String decryption(String chipertext) {
        List<String> partChiperText= chipertext.chars().mapToObj(ch -> (char) ch)
                .map(character -> character.toString())
                .collect(Collectors.toList());

        List<List<String>> key = processKey(2);
        List<Integer> storeIndex = new ArrayList<>();
        for (int i = 0; i < partChiperText.size(); i++) {
            if (!partChiperText.get(i).equals("X")) {
                storeIndex.add(key.get(1).indexOf(partChiperText.get(i).toUpperCase()));
            } else {
                storeIndex.add(-1);
            }
        }

        List<String> partPlain = new ArrayList<>();
        for(Integer realIndexKey : storeIndex) {
            if (!realIndexKey.equals(-1)) {
                String partOfKey = key.get(0).get(realIndexKey);
                partPlain.add(partOfKey);
            } else {
                partPlain.add(" ");
            }
        }

        return String.join("", partPlain);
    }
}

public class Ch01 {
    public static void main(String[] args) {

        List<List<String>> lists = StepCh01.processKey(10);
        System.out.println(lists.toString());

        String en = StepCh01.encryption("Okaerinasai Gohan ni suru ofuro ni suru sore tomo wa ta shi");
        System.out.println(en);

        String dec = StepCh01.decryption(en);
        System.out.println(dec);


    }
}
