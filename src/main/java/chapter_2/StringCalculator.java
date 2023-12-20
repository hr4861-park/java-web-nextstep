package chapter_2;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    /**
     * 요구 사항
     *
     * <p>1. 빈 문자열 혹은 null 을 입력할 경우 0을 반환 <br>
     * 2. 숫자 하나를 문자열로 입력할 경우, 해당 숫자를 반환 <br> 3. 숫자 두개를 쉼표(, ) 구분자로 입력하면 두 숫자의 합을 반환 <br> 4. 쉼표 외에 콜론(:) 도 사용 가능 <br> 5.
     * // 와 \n 사이에 커스텀 구분자 지정 가능 <br> 6. 음수 전달시 RuntimeException 예외 처리
     *
     * @param text
     * @return
     */
    int add(final String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        final String[] values = this.split(text);
        final int[] converted = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            final int number = Integer.parseInt(values[i]);
            if (number < 0) {
                throw new RuntimeException();
            }
            converted[i] = number;
        }
        return sum(converted);

    }

    private String[] split(final String text) {
        final Matcher matcher = Pattern.compile("//(.)\n(.*)").matcher(text);
        if (matcher.find()) {
            final String delimeter = matcher.group(1);
            return matcher.group(2).split(delimeter);
        }

        return text.split("[,:]");
    }

    private int sum(final int... values) {
        return Arrays.stream(values).sum();
    }
}
