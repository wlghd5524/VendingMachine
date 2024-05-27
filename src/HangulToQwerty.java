import java.util.HashMap;
import java.util.Map;

//한글을 영어 쿼티 자판으로 변환하는 클래스
public class HangulToQwerty {
    static final Map<Character, Character> hangulToQwertyMap = new HashMap<>();
    static {
        // 자음
        hangulToQwertyMap.put('ㄱ', 'r');
        hangulToQwertyMap.put('ㄲ', 'R');
        hangulToQwertyMap.put('ㄴ', 's');
        hangulToQwertyMap.put('ㄷ', 'e');
        hangulToQwertyMap.put('ㄸ', 'E');
        hangulToQwertyMap.put('ㄹ', 'f');
        hangulToQwertyMap.put('ㅁ', 'a');
        hangulToQwertyMap.put('ㅂ', 'q');
        hangulToQwertyMap.put('ㅃ', 'Q');
        hangulToQwertyMap.put('ㅅ', 't');
        hangulToQwertyMap.put('ㅆ', 'T');
        hangulToQwertyMap.put('ㅇ', 'd');
        hangulToQwertyMap.put('ㅈ', 'w');
        hangulToQwertyMap.put('ㅉ', 'W');
        hangulToQwertyMap.put('ㅊ', 'c');
        hangulToQwertyMap.put('ㅋ', 'z');
        hangulToQwertyMap.put('ㅌ', 'x');
        hangulToQwertyMap.put('ㅍ', 'v');
        hangulToQwertyMap.put('ㅎ', 'g');
        // 모음
        hangulToQwertyMap.put('ㅏ', 'k');
        hangulToQwertyMap.put('ㅐ', 'o');
        hangulToQwertyMap.put('ㅑ', 'i');
        hangulToQwertyMap.put('ㅒ', 'O');
        hangulToQwertyMap.put('ㅓ', 'j');
        hangulToQwertyMap.put('ㅔ', 'p');
        hangulToQwertyMap.put('ㅕ', 'u');
        hangulToQwertyMap.put('ㅖ', 'P');
        hangulToQwertyMap.put('ㅗ', 'h');
        hangulToQwertyMap.put('ㅛ', 'y');
        hangulToQwertyMap.put('ㅜ', 'n');
        hangulToQwertyMap.put('ㅠ', 'b');
        hangulToQwertyMap.put('ㅡ', 'm');
        hangulToQwertyMap.put('ㅣ', 'l');
    }

    public static String convertHangulToQwerty(String hangul) {
        StringBuilder qwerty = new StringBuilder();
        for (char ch : hangul.toCharArray()) {
            if (hangulToQwertyMap.containsKey(ch)) {
                qwerty.append(hangulToQwertyMap.get(ch));
            } else {
                qwerty.append(ch); // 만약 한글이 아닌 문자가 들어오면 그대로 추가
            }
        }
        return qwerty.toString();
    }

}
